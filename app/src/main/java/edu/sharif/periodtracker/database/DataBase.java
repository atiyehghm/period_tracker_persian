package edu.sharif.periodtracker.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import edu.sharif.periodtracker.database.Dao.DailyStatusDao;
import edu.sharif.periodtracker.database.model.DailyStatus;

@Database(entities = {DailyStatus.class}, version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {
    public abstract DailyStatusDao dao();
}
