package edu.sharif.periodtracker.database.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

import edu.sharif.periodtracker.libs.DateConverter;
import edu.sharif.periodtracker.libs.MoodConverter;
import edu.sharif.periodtracker.libs.PainConverter;
import edu.sharif.periodtracker.libs.ToolConverter;

@Entity(indices = {@Index(value = "date",
        unique = true)})
public class DailyStatus{

    @PrimaryKey(autoGenerate = true)
    private int id;

    @TypeConverters({DateConverter.class})
    @NonNull
    private DateTime date;

    @TypeConverters({PainConverter.class})
    @ColumnInfo(defaultValue = "0")
    private PainType pain;

    @TypeConverters({MoodConverter.class})
    private MoodType mood;

    @TypeConverters({ToolConverter.class})
    @ColumnInfo(defaultValue = "0")
    private ToolType tool;

    @ColumnInfo(defaultValue = "false")
    private boolean is_period;

    @ColumnInfo(defaultValue = "false")
    private boolean is_start;

    @ColumnInfo(defaultValue = "false")
    private boolean is_end;

    public boolean isIs_start() {
        return is_start;
    }

    public void setIs_start(boolean is_start) {
        this.is_start = is_start;
    }

    public boolean isIs_end() {
        return is_end;
    }

    public void setIs_end(boolean is_end) {
        this.is_end = is_end;
    }

    public DailyStatus() {
        date = new DateTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DateTime getDate() {
        return date;
    }

    @NonNull
    public void setDate(DateTime date) {
        this.date = date;
    }


    public PainType getPain() {
        return pain;
    }

    public void setPain(PainType pain) {
        this.pain = pain;
    }

    public MoodType getMood() {
        return mood;
    }

    public void setMood(MoodType mood) {
        this.mood = mood;
    }

    public ToolType getTool() {
        return tool;
    }

    public void setTool(ToolType tool) {
        this.tool = tool;
    }

    public boolean isIs_period() {
        return is_period;
    }

    public void setIs_period(boolean is_period) {
        this.is_period = is_period;
    }
}
