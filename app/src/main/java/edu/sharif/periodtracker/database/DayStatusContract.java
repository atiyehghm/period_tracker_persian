package edu.sharif.periodtracker.database;

import android.provider.BaseColumns;

public final class DayStatusContract {
    private DayStatusContract() {}

    public static class StatusEntry implements BaseColumns {
        public static final String TABLE_NAME = "status_table";
        public static final String ID = "ID";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_PAIN = "pain";
        public static final String COLUMN_NAME_MOOD = "mood";
    }
}
