package edu.sharif.periodtracker.ui.add;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.joda.time.DateTime;

import java.util.List;

import edu.sharif.periodtracker.MainActivity;
import edu.sharif.periodtracker.MyApplication;
import edu.sharif.periodtracker.R;
import edu.sharif.periodtracker.database.model.DailyStatus;
import edu.sharif.periodtracker.database.model.MoodType;
import edu.sharif.periodtracker.database.model.PainType;
import edu.sharif.periodtracker.database.model.ToolType;
import edu.sharif.periodtracker.database.repository.DailyStatusRepository;

public class AddFragment extends Fragment {

    private AddViewModel addViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addViewModel =
                ViewModelProviders.of(this).get(AddViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add, container, false);
        DailyStatusRepository dailyStatusRepository = new DailyStatusRepository(MyApplication.getContext());

//        DateTime today = new DateTime();
//        dailyStatusRepository.insertStatus(today, true, PainType.ABDOMINAL_PAIN, MoodType.ANGRY, ToolType.PAD);
//
//        dailyStatusRepository.getCurrentStatus(today).observe(getViewLifecycleOwner(), new Observer<DailyStatus>() {
//            @Override
//            public void onChanged(@Nullable DailyStatus status) {
//                if (status != null) {
////                    for(DailyStatus status : allStatus) {
//                        System.out.println("-----------------------");
//                        System.out.println(status.getMood().name());
//                        Log.i("DB", status.getMood().name());
//                        System.out.println(status.getDate());
//                        System.out.println(status.getPain().getPicId());
//                        System.out.println(status.isPeriod());
////                    }
//                }
//            }
//        });

//        final TextView textView = root.findViewById(R.id.text_add);
//        addViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}