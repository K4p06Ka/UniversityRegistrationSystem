package models;

public enum Grade {
    //Not sure, that we have the same grade system, but I just found a table in internet
    A(4.0), A_MINUS(3.7), B_PLUS(3.3), B(3.0), B_MINUS(2.7),
    C_PLUS(2.3), C(2.0), C_MINUS(1.7), D_PLUS(1.3), D(1.0), F(0.0), Fx(0.0);

    private final double gradePoints;

    Grade(double gradePoints) {
        this.gradePoints = gradePoints;
    }

    public double getGradePoints() {
        return gradePoints;
    }

    public boolean isPassingGrade() {
        return gradePoints >= 2.0 && this != Fx;
    }
}