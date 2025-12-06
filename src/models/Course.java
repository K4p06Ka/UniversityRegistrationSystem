package models;

import java.util.*;

public class Course {
    private String code;
    private String title;
    private int credits;
    private List<String> prerequisites;

    public Course(String code, String title, int credits) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.prerequisites = new ArrayList<>();
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public List<String> getPrerequisites() { return prerequisites; }

    //Only Course code will be added in prerequisites
    //then we can see it in repositories
    public void addPrerequisite(String courseCode) {
        prerequisites.add(courseCode);
    }
}
