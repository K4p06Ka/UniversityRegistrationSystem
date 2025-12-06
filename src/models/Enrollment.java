package models;

public class Enrollment {
    private Student student;
    private Section section;
    private String status;
    private Grade grade;

    public Enrollment(Student student, Section section) {
        this.student = student;
        this.section = section;
        this.status = "ENROLLED";
    }

    public Student getStudent() {
        return student;
    }

    public Section getSection() {
        return section;
    }

    public String getStatus() {
        return status;
    }

    public Grade getGrade() {
        return grade;
    }


    public void setStatus(String status) {
        //I check if status value is valid like this to avoid
        //complicating of the project with enum
        if (status.equals("DROPPED") || status.equals("WAITLISTED")) {
            this.status = status;

        } else {
            System.out.println("Invalid status value, status only can be ENROLLED|DROPPED|WAITLISTED ");
        }
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}