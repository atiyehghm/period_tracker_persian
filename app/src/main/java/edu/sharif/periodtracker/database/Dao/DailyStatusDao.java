package edu.sharif.periodtracker.database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.joda.time.DateTime;

import java.util.List;

import edu.sharif.periodtracker.database.model.DailyStatus;

@androidx.room.Dao
public interface DailyStatusDao {

    @Insert
    Long insertStatus(DailyStatus dailyStatus);

    @Query("SELECT * FROM DailyStatus WHERE is_period =:tp ORDER BY date asc")
    LiveData<List<DailyStatus>> fetchAllDailyStatus(Boolean tp);

    @Query("SELECT * FROM DailyStatus WHERE date =:dateTime")
    LiveData<DailyStatus> getStatus(String dateTime);

    @Update
    void updateStatus(DailyStatus dailyStatus);

    @Query("UPDATE DailyStatus SET is_end =:end_bool WHERE date =:dateTime")
    void updateEnd(String dateTime, Boolean end_bool);
}
