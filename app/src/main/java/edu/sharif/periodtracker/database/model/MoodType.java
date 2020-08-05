package edu.sharif.periodtracker.database.model;

import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import edu.sharif.periodtracker.MyApplication;
import edu.sharif.periodtracker.R;

public enum MoodType {
    NONE(-1, "", null),
    NORMAL(0, MyApplication.getContext().getString(R.string.mood1), ContextCompat.getDrawable(MyApplication.getContext(), R.drawable.normal)),
    HAPPY(1, MyApplication.getContext().getString(R.string.mood2), ContextCompat.getDrawable(MyApplication.getContext(), R.drawable.happy)),
    SAD(2, MyApplication.getContext().getString(R.string.mood3), ContextCompat.getDrawable(MyApplication.getContext(), R.drawable.sad)),
    ANGRY(3, MyApplication.getContext().getString(R.string.mood4), ContextCompat.getDrawable(MyApplication.getContext(), R.drawable.angry));

    private int id;
    private String name;
    private Drawable pic;

    MoodType(int id, String name, Drawable pic) {
        this.id = id;
        this.name = name;
        this.pic = pic;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Drawable getPic() {
        return pic;
    }

    public static MoodType getTypeFromId(int id) {
        if (id == NONE.id) {
            return NONE;
        } else if (id == NORMAL.id) {
            return NORMAL;
        } else if (id == HAPPY.id) {
            return HAPPY;
        } else if (id == SAD.id) {
            return SAD;
        } else if (id == ANGRY.id) {
            return ANGRY;
        } else {
            return NONE;
        }
    }
}
