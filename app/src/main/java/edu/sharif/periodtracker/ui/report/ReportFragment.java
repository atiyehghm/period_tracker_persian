package edu.sharif.periodtracker.ui.report;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.List;
import edu.sharif.periodtracker.R;
import edu.sharif.periodtracker.database.model.DailyStatus;
import edu.sharif.periodtracker.database.repository.DailyStatusRepository;
import edu.sharif.periodtracker.ui.calendar.CalendarFragment;

import static org.joda.time.Days.daysBetween;

public class ReportFragment extends Fragment {
    private ArrayList<DateTime> startDays = new ArrayList<>();
    private ArrayList<DateTime> endDays = new ArrayList<>();
    private ReportViewModel reportViewModel;
    private DailyStatusRepository dailyStatusRepo;
    private List<DailyStatus> dailySts;
    private ArrayList<Integer> cycles = new ArrayList<>();
    private ArrayList<Integer> periods = new ArrayList<>();
    private Float avgCycle, avgPeriod;
    private BarChart stackedChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntries;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reportViewModel =
                ViewModelProviders.of(this).get(ReportViewModel.class);
        View root = inflater.inflate(R.layout.fragment_report, container, false);
        reportViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        dailyStatusRepo = new DailyStatusRepository(this.getContext());
        dailyStatusRepo.getAllStatus(true).observe(getViewLifecycleOwner(), new Observer<List<DailyStatus>>() {
            @Override
            public void onChanged(List<DailyStatus> dailyStatuses) {
                if(dailyStatuses != null){
                    String temp = dailyStatuses.get(0).getDate().toString();
                    findStartDays(dailyStatuses);
                }
            }
        });
        stackedChart = root.findViewById(R.id.chart);
//        getEntries();
//        barDataSet = new BarDataSet(barEntries, "");
//        barData = new BarData(barDataSet);
//        stackedChart.setData(barData);
//        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
//        barDataSet.setValueTextColor(Color.BLACK);
//        barDataSet.setValueTextSize(18f);
        return root;
    }


    private void getEntries() {
        barEntries = new ArrayList<>();
        for(int i = 0; i < cycles.size(); i++){
            barEntries.add(new BarEntry((float)i, cycles.get(i)));
        }
        barDataSet = new BarDataSet(barEntries, "");
        barData = new BarData(barDataSet);
        stackedChart.setData(barData);
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(18f);
    }


    void findStartDays(List<DailyStatus> dailyStatuses){
        DailyStatus start = dailyStatuses.get(0);
        startDays.add(start.getDate());
        for (int i = 1; i < dailyStatuses.size(); i++) {
            DailyStatus sd = dailyStatuses.get(i);
            if(sd.getDate().minusDays(10).compareTo(start.getDate()) > 0)
            {
                startDays.add(sd.getDate());
                endDays.add(dailyStatuses.get(i - 1).getDate());
                start = sd;
            }
        }
        endDays.add(dailyStatuses.get(dailyStatuses.size() -1).getDate());
        calculatingPeriods();
        calculatingCycles();
    }

    private void calculatingCycles(){
        avgCycle = Float.valueOf(0);
        for(int i = 1; i< startDays.size(); i++){
            Integer cycle = calculateDifference(startDays.get(i-1), startDays.get(i));
            cycles.add(cycle + 1);
            avgCycle += Float.valueOf(cycle+1);
        }
        avgCycle = avgCycle / cycles.size();
        getEntries();

    }

    private void calculatingPeriods(){
        avgPeriod = Float.valueOf(0);
        for(int i = 0; i< startDays.size(); i++){
            Integer period = calculateDifference(startDays.get(i), endDays.get(i));
            periods.add(period + 1);
            avgPeriod += Float.valueOf(period+1);
        }
        avgPeriod = avgPeriod / periods.size();
    }

    private int calculateDifference(DateTime date1, DateTime date2){
        int diff = Days.daysBetween(date1, date2).getDays();
        return diff;
    }
}