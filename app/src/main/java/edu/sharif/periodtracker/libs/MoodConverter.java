package edu.sharif.periodtracker.libs;

import androidx.room.TypeConverter;

import edu.sharif.periodtracker.database.model.MoodType;

public class MoodConverter {
    @TypeConverter
    public static int fromMoodTypeToInt(MoodType value) {
        return value.ordinal();
    }

    @TypeConverter
    public static MoodType fromIntToMoodType(int value) {
        return (MoodType.values()[value]);
    }
}
