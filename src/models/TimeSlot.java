package models;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class TimeSlot implements Comparable<TimeSlot> {
    private DayOfWeek dayOfWeek;
    private LocalTime start;
    private LocalTime end;
    private String room;

    public TimeSlot(DayOfWeek dayOfWeek, LocalTime start, LocalTime end, String room) {
        this.dayOfWeek = dayOfWeek;
        this.start = start;
        this.end = end;
        this.room = room;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getStartTime() {
        return start;
    }

    public LocalTime getEndTime() {
        return end;
    }

    public String getRoom() {
        return room;
    }

    @Override
    public int compareTo(TimeSlot other) {
        int dayCompare = this.dayOfWeek.compareTo(other.dayOfWeek);
        if (dayCompare != 0) return dayCompare;
        return this.start.compareTo(other.start);
    }

    public boolean overlapsWith(TimeSlot other) {
        if (!this.dayOfWeek.equals(other.dayOfWeek)) {
            return false;
        }
        return this.start.compareTo(other.end) < 0 &&
                other.start.compareTo(this.end) < 0;
    }

    @Override
    public String toString() {
        return dayOfWeek + " " + start + "-" + end + " in " + room;
    }
}