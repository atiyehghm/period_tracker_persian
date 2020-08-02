package edu.sharif.periodtracker.database.model;

public enum ToolType {
    NONE(0),
    PAD(1),
    TAMPON(2),
    CUP(3),
    PANTY_LINER(4);
    final int id;
    ToolType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
