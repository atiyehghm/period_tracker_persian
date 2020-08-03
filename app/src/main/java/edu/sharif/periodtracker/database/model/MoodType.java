package edu.sharif.periodtracker.database.model;

import edu.sharif.periodtracker.MyApplication;
import edu.sharif.periodtracker.R;

public enum MoodType {
    NONE(0),
    NORMAL(1 , MyApplication.getContext().getString(R.string.mood1), R.drawable.normal ),
    HAPPY(2, MyApplication.getContext().getString(R.string.mood2), R.drawable.happy),
    SAD(3, MyApplication.getContext().getString(R.string.mood3), R.drawable.sad),
    ANGRY(4, MyApplication.getContext().getString(R.string.mood4), R.drawable.angry);

    final int id;
    private String name;
    private int picId;

    MoodType(int id, String name, int picId) {
        this.id = id;
        this.name = name;
        this.picId = picId;
    }

    MoodType(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPicId() {
        return picId;
    }
}
