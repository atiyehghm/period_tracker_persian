package edu.sharif.periodtracker.libs;

import android.annotation.SuppressLint;
import android.net.ParseException;

import androidx.room.TypeConverter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateConverter {

    static DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd");

    @TypeConverter
    public static DateTime fromTimestamp(String value) {
        if (value != null) {
            try {
                return df.parseDateTime(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return null;
        }
    }

    @TypeConverter
    public static String dateToTimestamp(DateTime value) {
        return value == null ? null : value.toString(df);
    }
}