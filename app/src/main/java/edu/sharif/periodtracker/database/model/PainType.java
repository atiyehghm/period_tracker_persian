package edu.sharif.periodtracker.database.model;

import edu.sharif.periodtracker.MyApplication;
import edu.sharif.periodtracker.R;

public enum PainType {
    NO_PAIN(1, "", 0),
    ABDOMINAL_PAIN(1, MyApplication.getContext().getString(R.string.pain1), R.drawable.abdominal),
    HEADACHE(1, MyApplication.getContext().getString(R.string.pain2), R.drawable.head),
    BACK_PAIN(1, MyApplication.getContext().getString(R.string.pain3), R.drawable.back),
    BREAST_PAIN(1, MyApplication.getContext().getString(R.string.pain4), R.drawable.breast);
    private int id;
    private String name;
    private int picId;

    PainType(int id, String name, int picId) {
        this.id = id;
        this.name = name;
        this.picId = picId;
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

    public int getPicId() {
        return picId;
    }
}
