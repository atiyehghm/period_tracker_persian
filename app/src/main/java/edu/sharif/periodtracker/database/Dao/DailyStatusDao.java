package edu.sharif.periodtracker.database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.sharif.periodtracker.database.model.DailyStatus;

@androidx.room.Dao
public interface DailyStatusDao {

    @Insert
    Long insertDailyStatus(DailyStatus dailyStatus);

    @Query("SELECT * FROM DailyStatus ORDER BY date desc")
    LiveData<List<DailyStatus>> fetchAllDailyStatus();
}
