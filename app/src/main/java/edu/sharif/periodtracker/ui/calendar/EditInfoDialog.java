package edu.sharif.periodtracker.ui.calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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

public class EditInfoDialog extends AppCompatDialogFragment {
    private EditText editCycle;
    private EditText editPeriod;
    private NumberPicker lastPeriodYear, lastPeriodMonth , lastPeriodDay;
    private static DateTime lastPeriod;
    private Chronology perChr = PersianChronologyKhayyam.getInstance(DateTimeZone.forID("Asia/Tehran"));
    private static Integer period, cycle;

    public interface EditInfoDialogListener extends Serializable {
        void ApplyInfo(Integer cycle, Integer period, DateTime lastPeriod);
    }

    private EditInfoDialogListener editInfoDialogListener;

    public static EditInfoDialog getInstance(EditInfoDialogListener dialogInterface, Integer defaultcycle, Integer defaultperiod) {
        EditInfoDialog editInfoDialog = new EditInfoDialog();
        cycle = defaultcycle;
        period = defaultperiod;
        // set fragment arguments
        Bundle args = new Bundle();
        args.putSerializable("dialogInterface", dialogInterface);
        editInfoDialog.setArguments(args);

        return editInfoDialog;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.editinfo_dialog_layout, null );
        editInfoDialogListener = (EditInfoDialogListener) getArguments().getSerializable("dialogInterface");
        builder.setView(view).setTitle(R.string.editInfo).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
        .setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cycle = Integer.parseInt(editCycle.getText().toString());
                period = Integer.parseInt(editPeriod.getText().toString());
                int year = lastPeriodYear.getValue();
                int month = lastPeriodMonth.getValue();
                int day = lastPeriodDay.getValue();
                lastPeriod = new DateTime(year, month, day, 0,0,0,0,perChr);
                Log.i("*************", "the cycle and period is:" + year + "--" + month );
                editInfoDialogListener.ApplyInfo(cycle, period, lastPeriod);
            }
        });
        editCycle = view.findViewById(R.id.cycle);
        editCycle.setText(cycle.toString());
        editPeriod = view.findViewById(R.id.period);
        editPeriod.setText(period.toString());
        lastPeriodYear = view.findViewById(R.id.lastPeriodYear);
        lastPeriodMonth = view.findViewById(R.id.lastPeriodMonth);
        lastPeriodDay = view.findViewById(R.id.lastPeriodDay);
        lastPeriodDay.setMinValue(1); lastPeriodDay.setMaxValue(31);
        lastPeriodMonth.setMinValue(1); lastPeriodMonth.setMaxValue(12);
        lastPeriodYear.setMinValue(1396); lastPeriodYear.setMaxValue(1410);

        return builder.create();
    }

}
