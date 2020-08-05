package edu.sharif.periodtracker.libs;

import androidx.room.TypeConverter;

import edu.sharif.periodtracker.database.model.MoodType;

public class MoodConverter {
    @TypeConverter
    public static int fromMoodTypeToInt(MoodType value) {
        if (value != null){
            return value.getId();
        }
        else {
            return -1;
        }
    }

    @TypeConverter
    public static MoodType fromIntToMoodType(int value) {
        return (MoodType.getTypeFromId(value));
    }
}
