package edu.sharif.periodtracker.database.model;

import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import edu.sharif.periodtracker.MyApplication;
import edu.sharif.periodtracker.R;

public enum PainType {
    NONE(-1, "", null),
    ABDOMINAL_PAIN(0, MyApplication.getContext().getString(R.string.pain1), ContextCompat.getDrawable(MyApplication.getContext(), R.drawable.abdominal)),
    HEADACHE(1, MyApplication.getContext().getString(R.string.pain2), ContextCompat.getDrawable(MyApplication.getContext(), R.drawable.head)),
    BACK_PAIN(2, MyApplication.getContext().getString(R.string.pain3), ContextCompat.getDrawable(MyApplication.getContext(), R.drawable.back)),
    BREAST_PAIN(3, MyApplication.getContext().getString(R.string.pain4), ContextCompat.getDrawable(MyApplication.getContext(), R.drawable.breast));
    private int id;
    private String name;
    private Drawable pic;

    PainType(int id, String name, Drawable pic) {
        this.id = id;
        this.name = name;
        this.pic = pic;
    }

    PainType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Drawable getPicId() {
        return pic;
    }

    public static PainType getTypeFromId(int id) {
        if (id == NONE.id) {
            return NONE;
        } else if (id == ABDOMINAL_PAIN.id) {
            return ABDOMINAL_PAIN;
        } else if (id == HEADACHE.id) {
            return HEADACHE;
        } else if (id == BACK_PAIN.id) {
            return BACK_PAIN;
        } else if (id == BREAST_PAIN.id) {
            return BREAST_PAIN;
        } else {
            return NONE;
        }
    }
}
