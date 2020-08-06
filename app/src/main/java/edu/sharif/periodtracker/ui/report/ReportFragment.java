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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import edu.sharif.periodtracker.R;
import edu.sharif.periodtracker.database.model.ChartBar;
import edu.sharif.periodtracker.database.model.DailyStatus;
import edu.sharif.periodtracker.database.repository.DailyStatusRepository;
import edu.sharif.periodtracker.libs.DateConverter;
import edu.sharif.periodtracker.ui.calendar.CalendarFragment;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;
import static org.joda.time.Days.daysBetween;

public class ReportFragment extends Fragment {
    private ReportViewModel reportViewModel;
    private DailyStatusRepository dailyStatusRepo;
    private Float avgCycle, avgPeriod;
    private BarChart stackedChart;
    private ArrayList<ChartBar> chartData = new ArrayList<ChartBar>();
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntries;

    private int defaultCycleLength = 28;
    private int defaultPeriodLength = 7;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        reportViewModel =
//                ViewModelProviders.of(this).get(ReportViewModel.class);
        View root = inflater.inflate(R.layout.fragment_report, container, false);
//        reportViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//            }
//        });
        dailyStatusRepo = new DailyStatusRepository(this.getContext());
        dailyStatusRepo.getAllStatus(true).observe(getViewLifecycleOwner(), new Observer<List<DailyStatus>>() {
            @Override
            public void onChanged(List<DailyStatus> dailyStatuses) {
                if(dailyStatuses != null){
                    ArrayList<ArrayList<DateTime>> groups = new ArrayList<ArrayList<DateTime>>();
                    ArrayList<DateTime> group1 = new ArrayList<>();
                    group1.add(dailyStatuses.get(0).getDate());
                    groups.add(group1);
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
//                    for(ArrayList<DateTime> gr : groups){
//                        for (DateTime dt : gr){
//                            System.out.println("DATE_GROUP " +  DateConverter.dateToString(dt));
//                        }
//                        System.out.println("----------------");
//                    }
                    initialChartData(groups);
                    getEntries();
                }
            }
        });
        stackedChart = root.findViewById(R.id.chart);
        return root;
    }

    private void initialChartData(ArrayList<ArrayList<DateTime>> groups) {

        for (int i=0; i< groups.size(); i++){
            ArrayList<DateTime> currentGroup = groups.get(i);
            //period length in Current group
            DateTime currentGroupFirstDay = currentGroup.get(0);
            DateTime currentGroupLastDay = currentGroup.get(currentGroup.size() - 1);
            int currentGroupPeriodLength = Days.daysBetween(currentGroupFirstDay, currentGroupLastDay).getDays() + 1;
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
            String currentGroupLabel = DateConverter.dateToString(currentGroupFirstDay);
            ChartBar chartBarData = new ChartBar(currentGroupLabel, currentGroupCycleLength, currentGroupPeriodLength);
            chartData.add(chartBarData);

        }
//        for (ChartBar chartBar : chartData){
//            System.out.println("Group: " + chartBar.toString());
//        }
    }


    private void getEntries() {
        barEntries = new ArrayList<>();
        final String[] labels = new String[chartData.size()];
        for (int i=0; i< chartData.size(); i++){
            BarEntry barEntry = new BarEntry(
                    (float)i,
                    new float[]{chartData.get(i).getPeriodLength(), chartData.get(i).getCycleLength()}
                    );
            barEntries.add(barEntry);
            labels[i] = chartData.get(i).getLabel();
        }

        xAxisConfiguration(labels);
        legendsConfiguration();
        barDataSet = new BarDataSet(barEntries, "");
        barData = new BarData(barDataSet);
        stackedChart.setData(barData);
        stackedChart.getDescription().setEnabled(false);
        barDataSet.setColors(getColors());
        barDataSet.setValueTextSize(15f);
    }

    private void legendsConfiguration() {
        Legend legends = stackedChart.getLegend();
        LegendEntry lnPeriodLength = new LegendEntry();
        lnPeriodLength.label = "طول پریود";
        lnPeriodLength.formColor = getColors()[0];
        LegendEntry lnCycleLength = new LegendEntry();
        lnCycleLength.label = "طول دوره";
        lnCycleLength.formColor = getColors()[1];
        legends.setCustom(Arrays.asList(lnPeriodLength, lnCycleLength));
    }

    private void xAxisConfiguration(final String[] labels) {
        XAxis xAxis = stackedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12f);
        xAxis.setLabelRotationAngle(-90);
        xAxis.setGranularity(1f);
        stackedChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

    }

    private int[] getColors() {

        // have as many colors as stack-values per entry
        int[] colors = new int[2];

        System.arraycopy(new int[]{rgb("#FFFFAAB9"), rgb("#FF70a5e0")}, 0, colors, 0, 2);

        return colors;
    }

}