package edu.sharif.periodtracker.libs;

import androidx.room.TypeConverter;

import edu.sharif.periodtracker.database.model.ToolType;

public class ToolConverter {
    @TypeConverter
    public static int fromToolTypeToInt(ToolType value) {
        if (value != null){
            return value.getId();
        }
        else {
            return -1;
        }
    }

    @TypeConverter
    public static ToolType fromIntToToolType(int value) {
        return (ToolType.getTypeFromId(value));
    }
}
