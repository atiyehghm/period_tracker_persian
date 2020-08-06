package edu.sharif.periodtracker.libs;

import android.annotation.SuppressLint;
import android.net.ParseException;

import androidx.room.TypeConverter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import edu.sharif.periodtracker.MainActivity;

public class DateConverter {

    static DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd").withChronology(MainActivity.perChr);

    @TypeConverter
    public static DateTime stringToDate(String value) {
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
    public static String dateToString(DateTime value) {
        return value == null ? null : value.toString(df);
    }
}