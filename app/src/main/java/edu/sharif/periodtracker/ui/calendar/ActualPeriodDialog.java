package edu.sharif.periodtracker.ui.calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.PersianChronologyKhayyam;

import java.io.Serializable;

import edu.sharif.periodtracker.R;

public class ActualPeriodDialog extends AppCompatDialogFragment {
    private NumberPicker actualPeriodStartYear, actualPeriodStartMonth , actualPeriodStartDay;
    private NumberPicker actualPeriodEndYear, actualPeriodEndMonth , actualPeriodEndDay;
    private static DateTime startDate, endDate;
    private Chronology perChr = PersianChronologyKhayyam.getInstance(DateTimeZone.forID("Asia/Tehran"));
    private static Integer period, cycle;

    public interface ActualPeriodDialogListener extends Serializable {
        void ApplyDate(DateTime startDate, DateTime endDate);
    }

    private ActualPeriodDialog.ActualPeriodDialogListener actualPeriodDialogListener;

    public static ActualPeriodDialog getInstance(ActualPeriodDialogListener dialogInterface) {
        ActualPeriodDialog actualPeriodDialog = new ActualPeriodDialog();
        Bundle args = new Bundle();
        args.putSerializable("dialogInterface", dialogInterface);
        actualPeriodDialog.setArguments(args);

        return actualPeriodDialog;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.actualperiod_dialog_layout, null );
        actualPeriodDialogListener = (ActualPeriodDialogListener) getArguments().getSerializable("dialogInterface");
        builder.setView(view).setTitle(R.string.actualPeriodTitle).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    //toast kon ke zaman pishini shode set mishe
            }
        })
                .setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = actualPeriodStartYear.getValue();
                        int month = actualPeriodStartMonth.getValue();
                        int day = actualPeriodStartDay.getValue();
                        startDate = new DateTime(year, month, day, 0,0,0,0,perChr);
                        year = actualPeriodEndYear.getValue();
                        month = actualPeriodEndMonth.getValue();
                        day = actualPeriodEndDay.getValue();
                        endDate = new DateTime(year, month, day, 0,0,0,0,perChr);
                        Log.i("*************", "the cycle and period is:" + year + "--" + month );
                        actualPeriodDialogListener.ApplyDate(startDate, endDate);
                    }
                });
        actualPeriodStartYear = view.findViewById(R.id.actualPeriodStartYear);
        actualPeriodStartMonth = view.findViewById(R.id.actualPeriodStartMonth);
        actualPeriodStartDay = view.findViewById(R.id.actualPeriodStartDay);
        actualPeriodEndYear = view.findViewById(R.id.actualPeriodEndYear);
        actualPeriodEndMonth = view.findViewById(R.id.actualPeriodEndMonth);
        actualPeriodEndDay = view.findViewById(R.id.actualPeriodEndDay);
        actualPeriodStartDay.setMinValue(1); actualPeriodStartDay.setMaxValue(31);
        actualPeriodEndDay.setMinValue(1); actualPeriodEndDay.setMaxValue(31);
        actualPeriodStartMonth.setMinValue(1); actualPeriodStartMonth.setMaxValue(12);
        actualPeriodEndMonth.setMinValue(1); actualPeriodEndMonth.setMaxValue(12);
        actualPeriodStartYear.setMinValue(1396); actualPeriodStartYear.setMaxValue(1410);
        actualPeriodEndYear.setMinValue(1396); actualPeriodEndYear.setMaxValue(1410);

        return builder.create();
    }

}
