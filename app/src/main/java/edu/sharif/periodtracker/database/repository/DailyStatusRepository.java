package edu.sharif.periodtracker.database.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import org.joda.time.DateTime;

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
        status.setIsPeriod(isPeriod);


        if(isPeriod) {
            status.setIsPeriod(true);
        } else status.setIsPeriod(false);
        if(mood != null) {
            status.setMood(mood);
        } else status.setMood(MoodType.NONE);
        if(pain != null) {
            status.setPain(pain);
        } else status.setPain(PainType.NO_PAIN);
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

    public LiveData<List<DailyStatus>> getAllStatus() {
        return dailyStatusDatabase.dao().fetchAllDailyStatus();
    }

    public LiveData<DailyStatus> getCurrentStatus(DateTime dateTime) {
        String date = DateConverter.dateToString(dateTime);
        return dailyStatusDatabase.dao().getStatus(date);
    }

}
