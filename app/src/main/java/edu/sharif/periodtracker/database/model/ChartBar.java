package edu.sharif.periodtracker.database.model;

public class ChartBar {
    private String label;
    private int cycleLength;
    private int periodLength;

    public ChartBar(String label, int cycleLength, int periodLength) {
        this.label = label;
        this.cycleLength = cycleLength;
        this.periodLength = periodLength;
    }

    public String getLabel() {
        return label;
    }

    public int getCycleLength() {
        return cycleLength;
    }

    public int getPeriodLength() {
        return periodLength;
    }

    @Override
    public String toString() {
        return "ChartBar{" +
                "label='" + label + '\'' +
                ", cycleLength=" + cycleLength +
                ", periodLength=" + periodLength +
                '}';
    }
}
