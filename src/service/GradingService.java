package service;

import models.*;
import repository.InMemoryRepository;
import utilities.Result;
import java.util.*;


public class GradingService {
    private InMemoryRepository<Person> personRepo;
    private InMemoryRepository<Section> sectionRepo;

    public GradingService(InMemoryRepository<Person> personRepo,
                          InMemoryRepository<Section> sectionRepo) {
        this.personRepo = personRepo;
        this.sectionRepo = sectionRepo;
    }

    public Result<Void> postGrade(String instructorId, String sectionId,
                                  String studentId, Grade grade) {
        Optional<Person> instructorOpt = personRepo.findById(instructorId);
        if (!instructorOpt.isPresent() || !(instructorOpt.get() instanceof Instructor)) {
            return Result.fail("Instructor " + instructorId + " not found");
        }

        Optional<Section> sectionOpt = sectionRepo.findById(sectionId);
        if (!sectionOpt.isPresent()) {
            return Result.fail("Section " + sectionId + " not found");
        }

        Section section = sectionOpt.get();

        if (section.getInstructor() == null ||
                !section.getInstructor().getId().equals(instructorId)) {
            return Result.fail("Instructor is not assigned to this section");
        }

        Enrollment enrollment = null;
        for (Enrollment e : section.getRoster()) {
            if (e.getStudent().getId().equals(studentId)) {
                enrollment = e;
                break;
            }
        }

        if (enrollment == null) {
            return Result.fail("Student " + studentId + " is not enrolled in section " + sectionId);
        }

        if (enrollment.getStatus() != "ENROLLED") {
            return Result.fail("Student is not actively enrolled");
        }

        enrollment.setGrade(grade);

        if (grade != null) {
            TranscriptEntry entry = new TranscriptEntry(
                    section.getCourse().getCode(),
                    section.getTerm(),
                    section.getCourse().getCredits(),
                    grade
            );
            enrollment.getStudent().addTranscriptEntry(entry);
        }

        return Result.ok(null);
    }

    public double computeGPA(String studentId) {
        Optional<Person> personOpt = personRepo.findById(studentId);
        if (!personOpt.isPresent() || !(personOpt.get() instanceof Student)) {
            return 0.0;
        }

        Student student = (Student) personOpt.get();
        return student.getTranscript().calculateGPA();
    }
}
