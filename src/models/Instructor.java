package models;

import java.util.*;

public class Instructor extends Person {
    private String department;
    private List<Section> assignedSections;


    public Instructor(String id, String name, String email, String department) {
        super(id, name, email);
        this.department = department;
        this.assignedSections = new ArrayList<>();
    }

    @Override
    public String role() {
        return "INSTRUCTOR";
    }

    public String getDepartment() { return department; }
    public List<Section> getAssignedSections() { return assignedSections; }
}

