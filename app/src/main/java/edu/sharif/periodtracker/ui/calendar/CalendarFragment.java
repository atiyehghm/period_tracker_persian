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

public class CalendarFragment extends Fragment implements EditInfoDialog.EditInfoDialogListener, ActualPeriodDialog.ActualPeriodDialogListener {
    private PersianHorizontalExpCalendar persianHorizontalExpCalendar;
    private CalendarViewModel reportViewModel;
    private Button editInfo;
    private Button actualPeriod;
    private Integer cycle, period;
    private Chronology perChr = PersianChronologyKhayyam.getInstance(DateTimeZone.forID("Asia/Tehran"));
    private DateTime lastPeriod;
    private DailyStatusRepository dailyStatusRepo;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //default values
        period = 7; cycle = 28; lastPeriod = new DateTime(1300, 1, 1, 0, 0, 0, perChr);
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
        Log.i("******** in fragment", "the cycle and period is:" + this.cycle.toString() + "--" + this.period.toString());
        markPeriodDays();
    }

    public void markPeriodDays() {
        // for this month
        for(int i = 0; i < this.period; i++){
            //from here you can change period highlight color
            this.persianHorizontalExpCalendar
                    .markDate(new DateTime(this.lastPeriod.getYear(), this.lastPeriod.getMonthOfYear(), this.lastPeriod.getDayOfMonth(), 0, 0, perChr).plusDays(i),
                            new CustomGradientDrawable(GradientDrawable.RECTANGLE, Color.parseColor("#35a677bd"))
                                    .setstroke(1, Color.parseColor("#a677bd")));
        }
        //for all the months predict period time
        DateTime startDate = this.lastPeriod;
        for(int i = 1; i < 12; i++){
            startDate = new DateTime(startDate.getYear(), startDate.getMonthOfYear(), startDate.getDayOfMonth(), 0 ,0,0 , perChr).plusDays(cycle);
            for(int j = 0; j < this.period; j++) {
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

    @Override
    public void ApplyDate(DateTime startDate, DateTime endDate) {

    }


}