package models;


import java.util.*;

public class Section implements Schedulable {
    private String id;
    private Course course;
    private String term;
    private Instructor instructor;
    private int capacity;
    private List<TimeSlot> meetingTimes;
    private List<Enrollment> roster;

    public Section(String id, Course course, String term, int capacity) {
        this.id = id;
        this.course = course;
        this.term = term;
        this.capacity = capacity;
        this.meetingTimes = new ArrayList<>();
        this.roster = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public String getTerm() {
        return term;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<Enrollment> getRoster() {
        return roster;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
        instructor.getAssignedSections().add(this);
    }

    public void addMeetingTime(TimeSlot timeSlot) {
        meetingTimes.add(timeSlot);
    }

    @Override
    public List<TimeSlot> getMeetingTimes() {
        return new ArrayList<>(meetingTimes);
    }

    public List<Enrollment> getEnrolledStudents() {
        ArrayList<Enrollment> enrolledStudents = new ArrayList<>();
        for (Enrollment j : roster){
            if (j.getStatus()=="ENROLLED"){
                enrolledStudents.add(j);
            }
        }
        return enrolledStudents;

    }

    public boolean isFull() {
        return getEnrolledStudents().size() >= capacity;
    }

}
