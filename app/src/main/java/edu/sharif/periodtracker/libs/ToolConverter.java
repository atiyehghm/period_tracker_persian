package edu.sharif.periodtracker.libs;

import androidx.room.TypeConverter;

import edu.sharif.periodtracker.database.model.ToolType;

public class ToolConverter {
    @TypeConverter
    public static int fromToolTypeToInt(ToolType value) {
        return value.ordinal();
    }

    @TypeConverter
    public static ToolType fromIntToToolType(int value) {
        return (ToolType.values()[value]);
    }
}
