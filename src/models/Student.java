package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends Person {
    private String major;
    private Transcript transcript;
    private List<Enrollment> currentEnrollments;

    public Student(String id, String name, String email, String major) {
        super(id, name, email);
        this.major = major;
        this.transcript = new Transcript();
        this.currentEnrollments = new ArrayList<>();
    }


    @Override
    public String role() {
        return "STUDENT";
    }

    public String getMajor() { return major; }
    public Transcript getTranscript() { return transcript; }
    public List<Enrollment> getCurrentEnrollments() { return currentEnrollments; }

    public void addTranscriptEntry(TranscriptEntry entry) {
        transcript.addEntry(entry);
    }
}