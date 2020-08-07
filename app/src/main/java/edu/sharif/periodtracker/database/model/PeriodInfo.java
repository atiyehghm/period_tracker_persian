package edu.sharif.periodtracker.database.model;

import org.joda.time.DateTime;

public class PeriodInfo {
    private int id;
    private int cycle;
    private int period;
    private DateTime lastperiod;

    public PeriodInfo(int cycle, int period, DateTime lastperiod) {
        this.cycle = cycle;
        this.period = period;
        this.lastperiod = lastperiod;
    }

    public int getCycle() {
        return cycle;
    }

    public int getPeriod() {
        return period;
    }

    public DateTime getLastperiod() {
        return lastperiod;
    }
}
