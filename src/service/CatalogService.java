package service;

import models.*;
import repository.InMemoryRepository;

import java.util.*;

import utilities.Result;

public class CatalogService {
    private InMemoryRepository<Course> courseRepo;
    private InMemoryRepository<Section> sectionRepo;
    private InMemoryRepository<Person> personRepo;

    public CatalogService(InMemoryRepository<Course> courseRepo, InMemoryRepository<Section> sectionRepo, InMemoryRepository<Person> personRepo) {
        this.courseRepo = courseRepo;
        this.sectionRepo = sectionRepo;
        this.personRepo = personRepo;
    }

    public List<Section> searchSections(String query) {
        String q = query.toLowerCase();
        List<Section> results = new ArrayList<>();
        List<Section> allSections = sectionRepo.findAll();

        for (Section s : allSections) {
            if (s.getCourse().getCode().toLowerCase().contains(q) || s.getCourse().getTitle().toLowerCase().contains(q) ||
                    s.getId().toLowerCase().contains(q)) {
                results.add(s);
            }
        }

        return results;
    }

    public Result<Course> createCourse(String code, String title, int credits) {
        if (courseRepo.findById(code).isPresent()) {
            return Result.fail("Course with code " + code + " already exists");
        }

        if (credits <= 0) {
            return Result.fail("Credits must be positive");
        }

        Course course = new Course(code, title, credits);
        courseRepo.save(course, code);

        return Result.ok(course);
    }

    public Result<Section> createSection(String sectionId, String courseCode, String term, int capacity) {
        Optional<Course> courseOpt = courseRepo.findById(courseCode);
        if (!courseOpt.isPresent()) {
            return Result.fail("Course " + courseCode + " not found");
        }

        if (sectionRepo.findById(sectionId).isPresent()) {
            return Result.fail("Section ID " + sectionId + " already exists");
        }

        if (capacity <= 0) {
            return Result.fail("Capacity must be positive");
        }

        Section section = new Section(sectionId, courseOpt.get(), term, capacity);
        sectionRepo.save(section, sectionId);

        return Result.ok(section);
    }

    public Result<Void> assignInstructor(String sectionId, String instructorId) {
        Optional<Section> sectionOpt = sectionRepo.findById(sectionId);
        if (!sectionOpt.isPresent()) {
            return Result.fail("Section " + sectionId + " not found");
        }

        Optional<Person> personOpt = personRepo.findById(instructorId);
        if (!personOpt.isPresent() || !(personOpt.get() instanceof Instructor)) {
            return Result.fail("Instructor " + instructorId + " not found");
        }

        Section section = sectionOpt.get();
        Instructor instructor = (Instructor) personOpt.get();

        if (section.getInstructor() != null) {
            section.getInstructor().getAssignedSections().remove(section);
        }
        List<Section> assignedSections = instructor.getAssignedSections();
        for (Section existingSection : assignedSections) {
            // Only check sections in the same term
            if (existingSection.getTerm().equals(section.getTerm())) {
                // Check if time slots conflict
                List<TimeSlot> times1 = existingSection.getMeetingTimes();
                List<TimeSlot> times2 = section.getMeetingTimes();
                for (TimeSlot t1 : times1) {
                    for (TimeSlot t2 : times2) {
                        if (t1.overlapsWith(t2)) {
                            return Result.fail("Instructor has a time conflict with section " +
                                    existingSection.getId() + " in term " + section.getTerm());
                        }
                    }
                }
            }
        }
        section.setInstructor(instructor);

        return Result.ok(null);
    }
}