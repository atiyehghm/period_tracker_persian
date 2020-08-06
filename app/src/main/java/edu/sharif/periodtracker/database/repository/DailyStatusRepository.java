package edu.sharif.periodtracker.database.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.List;

import edu.sharif.periodtracker.database.DataBase;
import edu.sharif.periodtracker.database.model.DailyStatus;
import edu.sharif.periodtracker.database.model.MoodType;
import edu.sharif.periodtracker.database.model.PainType;
import edu.sharif.periodtracker.database.model.ToolType;
import edu.sharif.periodtracker.libs.DateConverter;

public class DailyStatusRepository {
    private String DB_NAME = "db_daily_status";


    private DataBase dailyStatusDatabase;
    public DailyStatusRepository(Context context) {
        dailyStatusDatabase = Room.databaseBuilder(context, DataBase.class, DB_NAME).build();
    }


    public void insertStatus(DateTime date, boolean isPeriod) {

        insertStatus(date, isPeriod, null, null, null );
    }

    public void insertStatus(DateTime date,
                            boolean isPeriod,
                            PainType pain,
                            MoodType mood,
                            ToolType tool
                           ) {

        DailyStatus status = new DailyStatus();
        status.setDate(date);
        status.setMood(mood);
        status.setPain(pain);
        status.setTool(tool);
        status.setIs_period(isPeriod);


        if(isPeriod) {
            status.setIs_period(true);
        } else status.setIs_period(false);
        if(mood != null) {
            status.setMood(mood);
        } else status.setMood(MoodType.NONE);
        if(pain != null) {
            status.setPain(pain);
        } else status.setPain(PainType.NONE);
        if(tool != null) {
            status.setTool(tool);
        } else status.setTool(ToolType.NONE);

        insertStatus(status);
    }

    public void insertStatus(final DailyStatus status) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dailyStatusDatabase.dao().insertStatus(status);
                return null;
            }
        }.execute();
    }

    public LiveData<List<DailyStatus>> getAllStatus(Boolean tp) {
        return dailyStatusDatabase.dao().fetchAllDailyStatus(tp);
    }


    public LiveData<DailyStatus> getCurrentStatus(DateTime dateTime) {
        String date = DateConverter.dateToString(dateTime);
        return dailyStatusDatabase.dao().getStatus(date);
    }

    public void updateCurrentStatus(final DailyStatus status) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dailyStatusDatabase.dao().updateStatus(status);
                return null;
            }
        }.execute();
    }

    public void updateEndStatus(final String dateTime, final Boolean end) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dailyStatusDatabase.dao().updateEnd(dateTime, end);
                return null;
            }
        }.execute();
    }
}
