package edu.sharif.periodtracker.libs;

import androidx.room.TypeConverter;

import edu.sharif.periodtracker.database.model.PainType;

public class PainConverter {

    @TypeConverter
    public static int fromPainTypeToInt(PainType value) {
        if (value != null){
            return value.getId();
        }
        else {
            return -1;
        }
    }

    @TypeConverter
    public static PainType fromIntToPainType(int value) {
        return (PainType.getTypeFromId(value));
    }
}
