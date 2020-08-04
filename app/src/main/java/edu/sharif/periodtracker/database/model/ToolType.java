package edu.sharif.periodtracker.database.model;

import edu.sharif.periodtracker.MyApplication;
import edu.sharif.periodtracker.R;

public enum ToolType {
    NONE(0, "", 0),
    PAD(1, MyApplication.getContext().getString(R.string.tool1), R.drawable.pad),
    TAMPON(2, MyApplication.getContext().getString(R.string.tool2), R.drawable.tampon),
    CUP(3, MyApplication.getContext().getString(R.string.tool3), R.drawable.cup),
    PANTY_LINER(4, MyApplication.getContext().getString(R.string.tool4), R.drawable.panty_liner);
    private int id;
    private String name;
    private int picId;

    ToolType(int id, String name, int picId) {
        this.id = id;
        this.name = name;
        this.picId = picId;
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
