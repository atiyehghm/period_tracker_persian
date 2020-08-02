package edu.sharif.periodtracker.database.model;

public enum PainType {
    OK(0),
    ABDOMINAL_PAIN(1),
    HEADACHE(2),
    BACK_PAIN(3),
    BREAST_PAIN(4);
    final int id;
    PainType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
