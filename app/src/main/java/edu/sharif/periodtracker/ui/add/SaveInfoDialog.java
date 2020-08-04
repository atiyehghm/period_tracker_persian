package edu.sharif.periodtracker.ui.add;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.sharif.periodtracker.R;
import edu.sharif.periodtracker.database.model.DailyStatus;
import edu.sharif.periodtracker.database.model.MoodType;

public class SaveInfoDialog extends AppCompatActivity {
    public static final String KEY_ID_EDIT = "key_id_edit";
    private Toolbar toolbar;
    private List<ImageView> listMoodImages = new ArrayList<>();
    private DailyStatus dailyStatus = new DailyStatus();
    private Drawable highlight;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_info_dialog_layout);
        getViewFromXml();
        getResource();
        getBundle();
        configureBackButton();
        controller();
        initialView();
    }

    private void getBundle() {
        int id = getIntent().getIntExtra(KEY_ID_EDIT, -1);

        if (dailyStatus == null){
            dailyStatus = new DailyStatus();
//            dailyStatus
        }

    }

    private void initialView() {
        refreshViewMood();
    }


    private void configureBackButton() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getViewFromXml() {
        toolbar = findViewById(R.id.toolbar);
        listMoodImages.add((ImageView) findViewById(R.id.mood1));
        listMoodImages.add((ImageView) findViewById(R.id.mood2));
        listMoodImages.add((ImageView) findViewById(R.id.mood3));
        listMoodImages.add((ImageView) findViewById(R.id.mood4));
    }

    private void controller() {
        for (int i = 0; i < listMoodImages.size(); i++) {
            final int ii = i;
            listMoodImages.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickItemMood(ii);
                }
            });
        }
    }

    private void clickItemMood(int index) {
        dailyStatus.setMood(MoodType.getMoodTypeFromId(index));
        refreshViewMood();
    }

    private void refreshViewMood() {
        for (int i = 0; i < listMoodImages.size(); i++) {
            if (i == dailyStatus.getMood().getId()) {
                listMoodImages.get(i).setBackground(highlight);
            } else {
                listMoodImages.get(i).setBackground(null);
            }
        }
    }

//    public void onClick(View v, int[] imagesViewArr) {
//        ImageView imageView;
//        final String PACKAGE_ID = "edu.sharif.periodtracker:id/";
//        for (int imgId : imagesViewArr) {
//            String tmp = getResources().getResourceName(imgId);
//            if (tmp != null && !tmp.startsWith(PACKAGE_ID)) {
//                if (v.getId() == imgId) {
//                    imageView = findViewById(imgId);
//
//                }
//            }
//        }
//
//    }


    private void getResource() {
        highlight = getResources().getDrawable(R.drawable.selected_image);
    }

}
