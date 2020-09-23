package com.app;

public class Service {

    private final String serviceLine;
    private final boolean isPosh;
    private final int departure, arrival;

    private int parseTime(String time) {
        String[] tokens = time.split(":");
        return Integer.parseInt(tokens[0]) * 60 + Integer.parseInt(tokens[1]);
    }

    public Service(String serviceLine) {
        this.serviceLine = serviceLine;
        String[] tokens = serviceLine.split(" ");
        isPosh = tokens[0].equals("Posh");
        departure = parseTime(tokens[1]);
        int arrival = parseTime(tokens[2]);
        if (arrival > departure) this.arrival = arrival;
        else this.arrival = arrival + 24 * 60;

    }

    public String toString() {
        return serviceLine;
    }

    public boolean isPosh() {
        return isPosh;
    }

    public int getDeparture() {
        return departure;
    }

    public int getArrival() {
        return arrival;
    }

    public boolean isLongerThanHour() {
        return arrival - departure > 60;
    }
}
