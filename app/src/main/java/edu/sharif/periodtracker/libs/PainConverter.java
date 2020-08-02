package edu.sharif.periodtracker.libs;

import androidx.room.TypeConverter;

import edu.sharif.periodtracker.database.model.PainType;

public class PainConverter {

    @TypeConverter
    public static int fromPainTypeToInt(PainType value) {
        return value.ordinal();
    }

    @TypeConverter
    public static PainType fromIntToPainType(int value) {
        return (PainType.values()[value]);
    }
}
