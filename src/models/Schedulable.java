package models;

import java.util.List;

interface Schedulable {
    List<TimeSlot> getMeetingTimes();
}