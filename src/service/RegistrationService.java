package service;

import models.*;
import repository.InMemoryRepository;
import utilities.Result;
import validators.CapacityValidator;
import validators.PrerequisiteValidator;
import validators.ScheduleConflictChecker;

import java.util.*;

public class RegistrationService {
    private InMemoryRepository<Person> personRepo;
    private InMemoryRepository<Section> sectionRepo;
    private PrerequisiteValidator prereqValidator;
    private CapacityValidator capacityValidator;
    private ScheduleConflictChecker conflictChecker;


    public RegistrationService(InMemoryRepository<Person> personRepo, InMemoryRepository<Section> sectionRepo) {
        this.personRepo = personRepo;
        this.sectionRepo = sectionRepo;
        this.prereqValidator = new PrerequisiteValidator();
        this.capacityValidator = new CapacityValidator();
        this.conflictChecker = new ScheduleConflictChecker();
    }


    public Result<Enrollment> enroll(String studentId, String sectionId) {
        // Find student using repository
        Optional<Person> personOpt = personRepo.findById(studentId);
        if (!personOpt.isPresent() || !(personOpt.get() instanceof Student)) {
            return Result.fail("Student not found");
        }
        Student student = (Student) personOpt.get();
        Optional<Section> sectionOpt = sectionRepo.findById(sectionId);
        if (!sectionOpt.isPresent()) {
            return Result.fail("Section not found");
        }

        Section section = sectionOpt.get();

        // Validate prerequisites
        Result<Void> prereqResult = prereqValidator.validate(student.getTranscript(), section.getCourse());
        if (!prereqResult.isOk()) {
            return Result.fail(prereqResult.getError());
        }

        // Validate capacity
        Result<Void> capacityResult = capacityValidator.validate(section);
        if (!capacityResult.isOk()) {
            return Result.fail(capacityResult.getError());
        }

        // Validate no schedule conflicts
        Result<Void> conflictResult = conflictChecker.checkConflicts(
                student, section, section.getTerm());
        if (!conflictResult.isOk()) {
            return Result.fail(conflictResult.getError());
        }

        // All validations passed
        Enrollment enrollment = new Enrollment(student, section);
        section.getRoster().add(enrollment);
        student.getCurrentEnrollments().add(enrollment);

        return Result.ok(enrollment);
    }

    public Result<Void> drop(String studentId, String sectionId) {
        // Find student using generic repository
        Optional<Person> personOpt = personRepo.findById(studentId);
        if (!personOpt.isPresent() || !(personOpt.get() instanceof Student)) {
            return Result.fail("Student not found");
        }

        Student student = (Student) personOpt.get();

        // Find the enrollment to drop
        Enrollment toRemove = null;
        for (Enrollment e : student.getCurrentEnrollments()) {
            if (e.getSection().getId().equals(sectionId) &&
                    e.getStatus().equals("ENROLLED")) {
                toRemove = e;
                break;
            }
        }

        if (toRemove == null) {
            return Result.fail("Not enrolled in this section");
        }

        // Mark as dropped
        toRemove.setStatus("DROPPED");
        return Result.ok(null);
    }

    public List<Section> listSchedule(String studentId) {
        Optional<Person> personOpt = personRepo.findById(studentId);
        if (personOpt.isEmpty() || !(personOpt.get() instanceof Student)) {
            return new ArrayList<>();
        }
        Student student = (Student) personOpt.get();
        List<Section> schedule = new ArrayList<>();
        for (Enrollment enrollment : student.getCurrentEnrollments()) {
            String status = enrollment.getStatus();
            Section section = enrollment.getSection();

            boolean isEnrolled = status.equals("ENROLLED");

            if (isEnrolled) {
                schedule.add(section);
            }
        }
        return schedule;
    }
}