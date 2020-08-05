package edu.sharif.periodtracker.ui.add;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import edu.sharif.periodtracker.R;
import edu.sharif.periodtracker.database.model.DailyStatus;
import edu.sharif.periodtracker.database.model.MoodType;
import edu.sharif.periodtracker.database.model.PainType;
import edu.sharif.periodtracker.database.model.ToolType;
import edu.sharif.periodtracker.database.repository.DailyStatusRepository;
import edu.sharif.periodtracker.libs.DateConverter;
import edu.sharif.periodtracker.ui.calendar.CalendarFragment;

public class SaveInfoDialog extends AppCompatActivity {
    public static final String KEY_DATE_EDIT = "KEY_DATE_EDIT";
    private static final String MOOD = "MOOD";
    private static final String TOOL = "TOOL";
    private static final String PAIN = "PAIN";
    private boolean isInEditMood = false;
    private Toolbar toolbar;
    private List<ImageView> listMoodImages = new ArrayList<>();
    private List<ImageView> listPainImages = new ArrayList<>();
    private List<ImageView> listToolImages = new ArrayList<>();
    private Switch periodSwitch;
    private DailyStatus dailyStatus = new DailyStatus();
    private Drawable highlight;
    private LiveData<DailyStatus> dailyStatusLiveData;
    private DailyStatusRepository dailyStatusRepo;
    private DateTime today , yesterday;
    private Boolean yesterday_period;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_info_dialog_layout);
        getViewFromXml();
        getResource();
        getBundle();
        initialView();
        controller();
    }


    private void initialView() {
        dailyStatusRepo = new DailyStatusRepository(this);
        dailyStatusRepo.getCurrentStatus(dailyStatus.getDate()).observe(this, new Observer<DailyStatus>() {
            @Override
            public void onChanged(@Nullable DailyStatus status) {
                if (status != null) {
                    dailyStatus = status;
                    isInEditMood = true;

                    int selectedMoodIndex = dailyStatus.getMood() != null ? dailyStatus.getMood().getId() : -1;
                    int selectedPainIndex = dailyStatus.getPain() != null ? dailyStatus.getPain().getId() : -1;
                    int selectedToolIndex = dailyStatus.getTool() !=null ? dailyStatus.getTool().getId() : -1;
                    periodSwitch.setChecked(dailyStatus.isIs_period());

                    refreshViewMood(selectedMoodIndex, listMoodImages, MOOD);
                    refreshViewMood(selectedPainIndex, listPainImages, PAIN);
                    refreshViewMood(selectedToolIndex, listToolImages, TOOL);
                }
                else{
                    isInEditMood = false;
                }
            }
        });
    }

    private void saveChanges() {
        if (isInEditMood){
            dailyStatusRepo.updateCurrentStatus(dailyStatus);
        }
        else {
            dailyStatusRepo.insertStatus(dailyStatus);
        }

    }

    private void configureBackButton() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
                onBackPressed();
            }
        });
    }

    private void refreshViewMood(int index, List<ImageView> list, String propertyType) {
        for (int i = 0; i < list.size(); i++) {
            if (i == index) {
                if (list.get(i).getBackground() == null){
                    list.get(i).setBackground(highlight);
                    setObjectData(index, propertyType);
                }
                else {
                    list.get(i).setBackground(null);
                    setObjectData(-1, propertyType);
                }

            } else {
                list.get(i).setBackground(null);
            }
        }
    }

    private void setObjectData(int index, String propertyType) {

        switch (propertyType){
            case MOOD:
                dailyStatus.setMood(MoodType.getTypeFromId(index));
                break;
            case TOOL:
                dailyStatus.setTool(ToolType.getTypeFromId(index));
                break;
            case PAIN:
                dailyStatus.setPain(PainType.getTypeFromId(index));
                break;
        }
    }

    private void imagesRowController(final List<ImageView> list, final String propertyType ){
        for (int i = 0; i < list.size(); i++) {
            final int index = i;
            list.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refreshViewMood(index, list, propertyType);
                }
            });
        }
    }


    private void controller() {
        configureBackButton();
        imagesRowController(listMoodImages, MOOD);
        imagesRowController(listPainImages, PAIN);
        imagesRowController(listToolImages, TOOL);

        periodSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    dailyStatus.setIs_period(true);
                }
                else {
                    dailyStatus.setIs_period(false);
                }
            }
        });
    }

    private void getBundle() {
        //Get dailyStatus object with corresponding date
        String dateInString = getIntent().getStringExtra(KEY_DATE_EDIT);
        today = DateConverter.stringToDate(dateInString);
        if (dailyStatus == null){
            dailyStatus = new DailyStatus();
        }
        yesterday = today.minusDays(1);
        Log.i("_________", "today is:" + DateConverter.dateToString(today) + " yesterday was: " + DateConverter.dateToString(yesterday));
        dailyStatus.setDate(today);
    }

    private void getViewFromXml() {
        toolbar = findViewById(R.id.toolbar);

        listMoodImages.add((ImageView) findViewById(R.id.mood1));
        listMoodImages.add((ImageView) findViewById(R.id.mood2));
        listMoodImages.add((ImageView) findViewById(R.id.mood3));
        listMoodImages.add((ImageView) findViewById(R.id.mood4));

        listPainImages.add((ImageView) findViewById(R.id.pain1));
        listPainImages.add((ImageView) findViewById(R.id.pain2));
        listPainImages.add((ImageView) findViewById(R.id.pain3));
        listPainImages.add((ImageView) findViewById(R.id.pain4));

        listToolImages.add((ImageView) findViewById(R.id.tool1));
        listToolImages.add((ImageView) findViewById(R.id.tool2));
        listToolImages.add((ImageView) findViewById(R.id.tool3));
        listToolImages.add((ImageView) findViewById(R.id.tool4));

        periodSwitch = findViewById(R.id.perios_switch);
    }

    private void getResource() {
        highlight = getResources().getDrawable(R.drawable.selected_image);
    }

}
