package models;

public class TranscriptEntry {
    private String courseCode;
    private String term;
    private int credits;
    private Grade grade;

    public TranscriptEntry(String courseCode, String term, int credits, Grade grade) {
        this.courseCode = courseCode;
        this.term = term;
        this.credits = credits;
        this.grade = grade;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTerm() {
        return term;
    }

    public int getCredits() {
        return credits;
    }

    public Grade getGrade() {
        return grade;
    }
}
