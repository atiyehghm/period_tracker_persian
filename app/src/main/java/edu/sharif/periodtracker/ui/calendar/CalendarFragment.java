package edu.sharif.periodtracker.ui.calendar;

import android.os.Bundle;
<<<<<<< HEAD
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
=======
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
>>>>>>> ead0fb76051021cde2c8c16fcc3da56a952c416e
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

<<<<<<< HEAD
import com.mohamadian.persianhorizontalexpcalendar.PersianHorizontalExpCalendar;
import com.mohamadian.persianhorizontalexpcalendar.enums.PersianViewPagerType;

import org.joda.time.DateTime;

import edu.sharif.periodtracker.R;

public class CalendarFragment extends Fragment implements EditInfoDialog.EditInfoDialogListener {
    private PersianHorizontalExpCalendar persianHorizontalExpCalendar;
    private CalendarViewModel reportViewModel;
    private Button editInfo;
    private Integer cycle, period;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        reportViewModel = ViewModelProviders.of(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        persianHorizontalExpCalendar = root.findViewById(R.id.persianCalendar);
        editInfo = root.findViewById(R.id.edit_info);

        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
=======
import edu.sharif.periodtracker.R;

public class CalendarFragment extends Fragment {

    private CalendarViewModel reportViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reportViewModel =
                ViewModelProviders.of(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        final TextView textView = root.findViewById(R.id.text_calendar);
        reportViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
>>>>>>> ead0fb76051021cde2c8c16fcc3da56a952c416e
            }
        });
        return root;
    }
<<<<<<< HEAD

    private void openDialog() {
        EditInfoDialog editInfoDialog = EditInfoDialog.getInstance(CalendarFragment.this);
        editInfoDialog.show(getActivity().getSupportFragmentManager(), "Edit info Dialog");
    }

    @Override
    public void ApplyInfo(Integer cycle, Integer period) {
        this.cycle = cycle;
        this.period = period;
        Log.i("******** in fragment", "the cycle and period is:" + this.cycle.toString() + "--" + this.period.toString());
    }
=======
>>>>>>> ead0fb76051021cde2c8c16fcc3da56a952c416e
}