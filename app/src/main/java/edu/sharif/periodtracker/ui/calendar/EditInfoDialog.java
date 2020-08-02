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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.io.Serializable;

import edu.sharif.periodtracker.R;

public class EditInfoDialog extends AppCompatDialogFragment {
    private EditText editCycle;
    private EditText editPeriod;
    private EditText lastDate;
    public interface EditInfoDialogListener extends Serializable {
        void ApplyInfo(Integer cycle, Integer period);
    }

    private EditInfoDialogListener editInfoDialogListener;

    public static EditInfoDialog getInstance(EditInfoDialogListener dialogInterface) {
        EditInfoDialog editInfoDialog = new EditInfoDialog();

        // set fragment arguments
        Bundle args = new Bundle();
        args.putSerializable("dialogInterface", dialogInterface);
        editInfoDialog.setArguments(args);

        return editInfoDialog;
    }


//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        try {
//            editInfoDialogListener = (EditInfoDialogListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString()
//                    + " must implement EditInfoDialogListener");
//        }
//    }

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
                Integer cycle = Integer.parseInt(editCycle.getText().toString());
                Integer period = Integer.parseInt(editPeriod.getText().toString());
                Log.i("*************", "the cycle and period is:" + cycle.toString() + "--" + period.toString());
                editInfoDialogListener.ApplyInfo(cycle, period);
            }
        });
        editCycle = view.findViewById(R.id.cycle);
        editPeriod = view.findViewById(R.id.period);
        lastDate = view.findViewById(R.id.lastDate);

        return builder.create();
    }

}
