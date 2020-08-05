package edu.sharif.periodtracker.database.model;

import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import java.lang.annotation.Target;

import edu.sharif.periodtracker.MyApplication;
import edu.sharif.periodtracker.R;

public enum ToolType {
    NONE(-1, "", null),
    PAD(0, MyApplication.getContext().getString(R.string.tool1), ContextCompat.getDrawable(MyApplication.getContext(), R.drawable.pad)),
    TAMPON(1, MyApplication.getContext().getString(R.string.tool2), ContextCompat.getDrawable(MyApplication.getContext(), R.drawable.tampon)),
    CUP(2, MyApplication.getContext().getString(R.string.tool3), ContextCompat.getDrawable(MyApplication.getContext(), R.drawable.cup)),
    PANTY_LINER(3, MyApplication.getContext().getString(R.string.tool4), ContextCompat.getDrawable(MyApplication.getContext(), R.drawable.panty_liner));
    private int id;
    private String name;
    private Drawable pic;

    ToolType(int id, String name, Drawable pic) {
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

    public Drawable getPicId() {
        return pic;
    }

    public static ToolType getTypeFromId(int id) {
        if (id == NONE.id) {
            return NONE;
        } else if (id == PAD.id) {
            return PAD;
        } else if (id == TAMPON.id) {
            return TAMPON;
        } else if (id == CUP.id) {
            return CUP;
        } else if (id == PANTY_LINER.id) {
            return PANTY_LINER;
        } else {
            return NONE;
        }
    }
}
