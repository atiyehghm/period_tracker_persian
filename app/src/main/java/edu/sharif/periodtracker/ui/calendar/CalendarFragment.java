package edu.sharif.periodtracker.ui.calendar;

import android.content.Intent;
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
import com.mohamadian.persianhorizontalexpcalendar.enums.PersianViewPagerType;
import com.mohamadian.persianhorizontalexpcalendar.model.CustomGradientDrawable;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.chrono.PersianChronologyKhayyam;

import java.util.ArrayList;
import java.util.List;

import edu.sharif.periodtracker.MainActivity;
import edu.sharif.periodtracker.MyApplication;
import edu.sharif.periodtracker.R;
import edu.sharif.periodtracker.database.model.DailyStatus;
import edu.sharif.periodtracker.database.repository.DailyStatusRepository;
import edu.sharif.periodtracker.libs.DateConverter;
import edu.sharif.periodtracker.ui.add.SaveInfoDialog;

public class CalendarFragment extends Fragment implements EditInfoDialog.EditInfoDialogListener {
    private PersianHorizontalExpCalendar persianHorizontalExpCalendar;
    private CalendarViewModel reportViewModel;
    private Button editInfo;
    private Button actualPeriod;
    private Integer cycle, period;
    private static Chronology perChr = PersianChronologyKhayyam.getInstance(DateTimeZone.forID("Asia/Tehran"));
    private DateTime lastPeriod;
    private DailyStatusRepository dailyStatusRepo;
    private int defaultCycleLength = 28;
    private int defaultPeriodLength = 7;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //default values
        period = defaultPeriodLength;
        cycle = defaultCycleLength;
        lastPeriod = new DateTime(1300, 1, 1, 0, 0, 0, perChr);
        reportViewModel = ViewModelProviders.of(this).get(CalendarViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        persianHorizontalExpCalendar = root.findViewById(R.id.persianCalendar);

        persianHorizontalExpCalendar
                .setPersianHorizontalExpCalListener(new PersianHorizontalExpCalendar.PersianHorizontalExpCalListener() {
                    @Override
                    public void onCalendarScroll(DateTime dateTime) {
                    }

                    @Override
                    public void onDateSelected(DateTime dateTime) {
                        String value= DateConverter.dateToString(dateTime);
                        Intent i = new Intent(MyApplication.getContext(), SaveInfoDialog.class);
                        i.putExtra(SaveInfoDialog.KEY_DATE_EDIT, value);
                        startActivity(i);
                        Log.i("^^^^^^^^^", "this is after activity");
                    }

                    @Override
                    public void onChangeViewPager(PersianViewPagerType persianViewPagerType) {

                    }

                });
        editInfo = root.findViewById(R.id.edit_info);
        actualPeriod = root.findViewById(R.id.actualPeriod);
        actualPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openActualPeriodDialog();
                markPeriodDate();
                predictPeriodDays(false);
            }
        });
        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditInfoDialog();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        markPeriodDate();
        predictPeriodDays(false);
    }

    private void markPeriodDate() {
        dailyStatusRepo = new DailyStatusRepository(this.getContext());
        dailyStatusRepo.getAllStatus(true).observe(getViewLifecycleOwner(), new Observer<List<DailyStatus>>() {
            @Override
            public void onChanged(List<DailyStatus> dailyStatuses) {
                if(dailyStatuses != null){
                    String temp = dailyStatuses.get(0).getDate().toString();
                    //Log.i("$$$$$$$$", temp);
                    for(int i = 0; i < dailyStatuses.size(); i++){
                        persianHorizontalExpCalendar.markDate(dailyStatuses.get(i).getDate(), new CustomGradientDrawable(GradientDrawable.RECTANGLE, Color.parseColor("#35a677bd"))
                                                .setstroke(1, Color.parseColor("#a677bd"))).updateMarks();
                    }

                }
            }
        });
    }

    private void openEditInfoDialog() {
        EditInfoDialog editInfoDialog = EditInfoDialog.getInstance(CalendarFragment.this, this.cycle, this.period);
        editInfoDialog.show(getActivity().getSupportFragmentManager(), "Edit info Dialog");
    }

//    private void openActualPeriodDialog(){
//        ActualPeriodDialog actualPeriodDialog = ActualPeriodDialog.getInstance(CalendarFragment.this);
//        actualPeriodDialog.show(getActivity().getSupportFragmentManager(), "Actual Period Dialog");
//    }

    @Override
    public void ApplyInfo(Integer cycle, Integer period, DateTime lastPeriod) {
        this.cycle = cycle;
        this.period = period;
        this.lastPeriod = lastPeriod;
        Log.i("******** in calfragment", "the cycle and period is:" + this.cycle.toString() + "--" + this.period.toString());
        predictPeriodDays(true);
    }

    public void predictPeriodDays(Boolean initVar) {
        if(initVar == true) {
            for (int i = 0; i < this.period; i++) {
                this.persianHorizontalExpCalendar
                        .markDate(new DateTime(this.lastPeriod.getYear(), this.lastPeriod.getMonthOfYear(), this.lastPeriod.getDayOfMonth(), 0, 0, perChr).plusDays(i),
                                new CustomGradientDrawable(GradientDrawable.RECTANGLE, Color.parseColor("#35a677bd"))
                                        .setstroke(1, Color.parseColor("#a677bd")));
            }
            //for all the months predict period time
            DateTime startDate = this.lastPeriod;
            for (int i = 1; i < 12; i++) {
                startDate = new DateTime(startDate.getYear(), startDate.getMonthOfYear(), startDate.getDayOfMonth(), 0, 0, 0, perChr).plusDays(cycle);
                for (int j = 0; j < this.period; j++) {
                    this.persianHorizontalExpCalendar
                            .markDate(new DateTime(startDate.getYear(), startDate.getMonthOfYear(), startDate.getDayOfMonth(), 0, 0, perChr).plusDays(j),
                                    new CustomGradientDrawable(GradientDrawable.RECTANGLE, Color.parseColor("#ff3399"))
                                            .setViewLayoutSize(ViewGroup.LayoutParams.MATCH_PARENT, 10)
                                            .setViewLayoutGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM)
                                            .setcornerRadius(5)
                                            .setTextColor(Color.parseColor("#FF66B2")));
                }

            }
        }else{
            dailyStatusRepo.getAllStatus(true).observe(getViewLifecycleOwner(), new Observer<List<DailyStatus>>() {
                @Override
                public void onChanged(List<DailyStatus> dailyStatuses) {
                    if(dailyStatuses != null){
                        ArrayList<ArrayList<DateTime>> groups = new ArrayList<ArrayList<DateTime>>();
                        ArrayList<DateTime> group1 = new ArrayList<>();
                        if(dailyStatuses.size() > 0){
                            group1.add(dailyStatuses.get(0).getDate());
                            groups.add(group1);
                        }
                        for (int i = 1; i< dailyStatuses.size(); i++){
                            DateTime next = dailyStatuses.get(i).getDate();
                            DateTime current = dailyStatuses.get(i-1).getDate();
                            int diff = Days.daysBetween(current, next).getDays();
                            boolean isNewGroup = diff > 1;
                            if (isNewGroup){
                                groups.add(new ArrayList<DateTime>());
                            }
                            groups.get(groups.size() - 1).add(next);
                        }
                        calculateAvgValues(groups);
                    }
                }
            });
        }
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
        Log.i("AVGGGGGGGGGGG", "the avg period" + period + "avg cycle" + cycle);
        DateTime startDate = groups.get(groups.size() - 1).get(0);
        for (int i = 1; i < 6; i++) {
            startDate = startDate.plusDays(cycle);
            for (int j = 0; j < period; j++) {
                this.persianHorizontalExpCalendar
                        .markDate(new DateTime(startDate.getYear(), startDate.getMonthOfYear(), startDate.getDayOfMonth(), 0, 0, perChr).plusDays(j),
                                new CustomGradientDrawable(GradientDrawable.RECTANGLE, Color.parseColor("#ff3399"))
                                        .setViewLayoutSize(ViewGroup.LayoutParams.MATCH_PARENT, 10)
                                        .setViewLayoutGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM)
                                        .setcornerRadius(5)
                                        .setTextColor(Color.parseColor("#FF66B2")));
            }
        }
    }


}