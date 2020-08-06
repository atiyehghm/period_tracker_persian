package edu.sharif.periodtracker.ui.home;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mohamadian.persianhorizontalexpcalendar.PersianHorizontalExpCalendar;
import com.mohamadian.persianhorizontalexpcalendar.model.CustomGradientDrawable;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.chrono.PersianChronologyKhayyam;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import edu.sharif.periodtracker.R;
import edu.sharif.periodtracker.database.model.DailyStatus;
import edu.sharif.periodtracker.database.repository.DailyStatusRepository;
import edu.sharif.periodtracker.ui.calendar.CalendarFragment;
import edu.sharif.periodtracker.ui.calendar.EditInfoDialog;

public class HomeFragment extends Fragment implements EditInfoDialog.EditInfoDialogListener {
    private Button editInfo;
    private HomeViewModel homeViewModel;
    private int defaultCycleLength = 28;
    private int defaultPeriodLength = 7;
    private Integer cycle, period;
    private DateTime lastPeriod;
    private Chronology perChr = PersianChronologyKhayyam.getInstance(DateTimeZone.forID("Asia/Tehran"));
    private DailyStatusRepository dailyStatusRepo;
    private DateTime nextPeriod;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        period = defaultPeriodLength;
        cycle = defaultCycleLength;
        lastPeriod = new DateTime(1300, 1, 1, 0, 0, 0, perChr);

        View root = inflater.inflate(R.layout.fragment_home, null);
        //editInfo = root.findViewById(R.id.home_editInfo);
//        editInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //openEditInfoDialog();
//                predictPeriodDays();
//
//            }
//        });
        TextView t1 = root.findViewById(R.id.text1);
        t1.setText("میانگین طول دوره: " + cycle);
        TextView t2 = root.findViewById(R.id.text2);
        t2.setText("میانگین طول پریود: " + period);
        return root;
    }

    private void openEditInfoDialog() {
        EditInfoDialog editInfoDialog = EditInfoDialog.getInstance(HomeFragment.this, this.cycle, this.period);
        editInfoDialog.show(getActivity().getSupportFragmentManager(), "Edit info Dialog");
    }

    @Override
    public void ApplyInfo(Integer cycle, Integer period, DateTime lastPeriod) {
        this.cycle = cycle;
        this.period = period;
        this.lastPeriod = lastPeriod;
        Log.i("******** home fragment", "the cycle and period is:" + this.cycle.toString() + "--" + this.period.toString());
        //predictPeriodDays(true);
    }

    public void predictPeriodDays() {
        dailyStatusRepo = new DailyStatusRepository(this.getContext());
        dailyStatusRepo.getAllStatus(true).observe(getViewLifecycleOwner(), new Observer<List<DailyStatus>>() {
            @Override
            public void onChanged(List<DailyStatus> dailyStatuses) {
                if (dailyStatuses != null) {
                    ArrayList<ArrayList<DateTime>> groups = new ArrayList<ArrayList<DateTime>>();
                    ArrayList<DateTime> group1 = new ArrayList<>();
                    if (dailyStatuses.size() > 0) {
                        group1.add(dailyStatuses.get(0).getDate());
                        groups.add(group1);
                    }
                    for (int i = 1; i < dailyStatuses.size(); i++) {
                        DateTime next = dailyStatuses.get(i).getDate();
                        DateTime current = dailyStatuses.get(i - 1).getDate();
                        int diff = Days.daysBetween(current, next).getDays();
                        boolean isNewGroup = diff > 1;
                        if (isNewGroup) {
                            groups.add(new ArrayList<DateTime>());
                        }
                        groups.get(groups.size() - 1).add(next);
                    }
                    calculateAvgValues(groups);
                }
            }
        });

    }



    private void calculateAvgValues(ArrayList<ArrayList<DateTime>> groups) {
        float periodAvg = 0;
        float cycleAvg = 0;
        for (int i=0; i< groups.size(); i++){
            ArrayList<DateTime> currentGroup = groups.get(i);
            //period length in Current group
            DateTime currentGroupFirstDay = currentGroup.get(0);
            DateTime currentGroupLastDay = currentGroup.get(currentGroup.size() - 1);
            int currentGroupPeriodLength = Days.daysBetween(currentGroupFirstDay, currentGroupLastDay).getDays() + 1;
            periodAvg += currentGroupPeriodLength;
            //cycle length between 2 groups
            int currentGroupCycleLength;
            if (i != groups.size() - 1) {
                ArrayList<DateTime> nextGroup = groups.get(i + 1);
                DateTime nextGroupFirstDay = nextGroup.get(0);
                currentGroupCycleLength = Days.daysBetween(currentGroupFirstDay, nextGroupFirstDay).getDays() + 1;
            }
            else {
                currentGroupCycleLength = defaultCycleLength;
            }
            cycleAvg += currentGroupCycleLength;
        }
        periodAvg = periodAvg/groups.size();
        cycleAvg = cycleAvg/(groups.size() - 1);
        period = (int) periodAvg;
        cycle = (int) cycleAvg;
        ArrayList<DateTime>  currentGroup = groups.get(groups.size() -1);
        DateTime currentGroupLastDay = currentGroup.get(0);
        nextPeriod = currentGroupLastDay.plusDays(cycle);
        Log.i("AVGGGGGGGGGGG", "the avg period" + period + "avg cycle" + cycle);

        }


}