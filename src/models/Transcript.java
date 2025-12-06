package models;

import java.util.*;

public class Transcript {
    private List<TranscriptEntry> entries;

    public Transcript() {
        this.entries = new ArrayList<>();
    }

    public List<TranscriptEntry> getEntries() { return entries; }

    public void addEntry(TranscriptEntry entry) {
        entries.add(entry);
    }

    public double calculateGPA() {
        if (entries.isEmpty()) {
            return 0.0;
        }

        double totalPoints = 0;
        int totalCredits = 0;

        for (TranscriptEntry entry : entries) {
            Grade grade = entry.getGrade();
            //I don't know if we count D+ and less, so I only check if grade Fx or F
            if (grade != Grade.Fx && grade != Grade.F) {
                totalPoints += grade.getGradePoints() * entry.getCredits();
                totalCredits += entry.getCredits();
            }
        }

        return totalCredits > 0 ? totalPoints / totalCredits : 0.0;
    }
    public boolean hasCompletedCourse(String courseCode) {
        for (TranscriptEntry entry : entries) {
            if (entry.getCourseCode().equals(courseCode)) {
                Grade grade = entry.getGrade();
                return grade.equals(Grade.A) || grade.equals(Grade.B) || grade.equals(Grade.C);
            }
        }
        return false;
    }

    public int getEarnedCredits() {
        int result = 0;
        for (TranscriptEntry entry : entries) {
            if (entry.getGrade() != Grade.F && entry.getGrade() != Grade.Fx) {
                result += entry.getCredits();
            }
        }
        return result;
    }
}

