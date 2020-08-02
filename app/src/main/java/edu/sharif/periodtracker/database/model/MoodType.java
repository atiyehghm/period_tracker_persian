package edu.sharif.periodtracker.database.model;

public enum MoodType {
    NORMAL(0),
    HAPPY(1),
    SAD(2),
    ANGRY(3);

    final int id;
    MoodType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
