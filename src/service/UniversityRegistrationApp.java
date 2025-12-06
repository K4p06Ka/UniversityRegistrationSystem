package service;

import models.*;
import repository.InMemoryRepository;
import utilities.Result;

import javax.swing.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class UniversityRegistrationApp {
    private Scanner scanner;
    private RegistrationService registrationService;
    private CatalogService catalogService;
    private GradingService gradingService;
    private InMemoryRepository<Person> personRepo;
    private InMemoryRepository<Section> sectionRepo;
    private InMemoryRepository<Course> courseRepo;

    private Person currentUser;

    public UniversityRegistrationApp() {
        scanner = new Scanner(System.in);

        personRepo = new InMemoryRepository<>();
        sectionRepo = new InMemoryRepository<>();
        courseRepo = new InMemoryRepository<>();

        registrationService = new RegistrationService(personRepo, sectionRepo);
        catalogService = new CatalogService(courseRepo, sectionRepo, personRepo);
        gradingService = new GradingService(personRepo, sectionRepo);

        loadSampleData();
    }

    private void loadSampleData() {
        Course cs101 = new Course("CS101", "Intro to Programming", 3);
        Course cs201 = new Course("CS201", "Data Structures", 3);
        cs201.addPrerequisite("CS101");
        Course cs301 = new Course("CS301", "Algorithms", 3);
        cs301.addPrerequisite("CS201");
        Course math101 = new Course("MATH101", "Calculus I", 4);
        Course math201 = new Course("MATH201", "Calculus II", 4);
        math201.addPrerequisite("MATH101");
        Course ace101 = new Course("ACE101", "English Composition", 3);
        Course ee2011 = new Course("EE2011", "Digital Systems" , 3);

        courseRepo.save(cs101, cs101.getCode());
        courseRepo.save(cs201, cs201.getCode());
        courseRepo.save(cs301, cs301.getCode());
        courseRepo.save(math101, math101.getCode());
        courseRepo.save(math201, math201.getCode());
        courseRepo.save(ace101, ace101.getCode());
        courseRepo.save(ee2011, ee2011.getCode());

        Instructor prof1 = new Instructor("I001", "Shahram Taheri", "shahram.taheri@antalya.edu.tr", "CS");
        Instructor prof2 = new Instructor("I002", "Halil Özmen", "halil.ozmen@antalya.edu.tr", "CS");
        Instructor prof3 = new Instructor("I003", "Veli Shakhmurov", "veli.sahmurov@antalya.edu.tr", "MATH");
        Instructor prof4 = new Instructor("I004", "Ekaterina Chicherina", "ekaterina.chicherina@antalya.edu.tr", "ACE");
        Instructor prof5 = new Instructor("I005","Zahra GOLRIZKHATAMI" , "z.golrizkhatami@antalya.edu.tr", "EE");

        personRepo.save(prof1, prof1.getId());
        personRepo.save(prof2, prof2.getId());
        personRepo.save(prof3, prof3.getId());
        personRepo.save(prof4,prof4.getId());
        personRepo.save(prof5, prof5.getId());

        Section cs101_01 = new Section("CS101-01", cs101, "Fall2024", 200);
        cs101_01.addMeetingTime(new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 15), "A2-89/90"));
        cs101_01.addMeetingTime(new TimeSlot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(10, 15), "CS-101"));
        cs101_01.setInstructor(prof1);

        Section cs101_02 = new Section("CS101-02", cs101, "Fall2024", 200);
        cs101_02.addMeetingTime(new TimeSlot(DayOfWeek.TUESDAY, LocalTime.of(14, 0), LocalTime.of(15, 15), "B1-18"));
        cs101_02.addMeetingTime(new TimeSlot(DayOfWeek.THURSDAY, LocalTime.of(14, 0), LocalTime.of(15, 15), "B2-18"));
        cs101_02.setInstructor(prof2);

        Section cs201_01 = new Section("CS201-01", cs201, "Fall2024", 200);
        cs201_01.addMeetingTime(new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(11, 0), LocalTime.of(12, 15), "B1-18"));
        cs201_01.addMeetingTime(new TimeSlot(DayOfWeek.WEDNESDAY, LocalTime.of(11, 0), LocalTime.of(12, 15), "B1-18"));
        cs201_01.setInstructor(prof1);

        Section math101_01 = new Section("MATH101-01", math101, "Fall2024", 190);
        math101_01.addMeetingTime(new TimeSlot(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(10, 15), "A2-91/92"));
        math101_01.addMeetingTime(new TimeSlot(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(10, 15), "A2-91/92"));
        math101_01.setInstructor(prof3);

        Section ace101_01 = new Section("ACE101-01", ace101, "Fall2024", 25);
        ace101_01.addMeetingTime(new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(13, 0), LocalTime.of(14, 15), "ACE-101"));
        ace101_01.addMeetingTime(new TimeSlot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(14, 15), "ACE-101"));
        ace101_01.setInstructor(prof4);

        sectionRepo.save(cs101_01, cs101_01.getId());
        sectionRepo.save(cs101_02, cs101_02.getId());
        sectionRepo.save(cs201_01, cs201_01.getId());
        sectionRepo.save(math101_01, math101_01.getId());
        sectionRepo.save(ace101_01, ace101_01.getId());


        Section cs101_03 = new Section("CS101-03", cs101, "Spring2025", 200);
        cs101_03.addMeetingTime(new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 15), "B1-18"));
        cs101_03.setInstructor(prof1);

        Section cs201_02 = new Section("CS201-02", cs201, "Spring2025", 200);
        cs201_02.addMeetingTime(new TimeSlot(DayOfWeek.TUESDAY, LocalTime.of(13, 0), LocalTime.of(14, 15), "B2-18"));
        cs201_02.setInstructor(prof2);

        Section cs301_01 = new Section("CS301-01", cs301, "Spring2025", 190);
        cs301_01.addMeetingTime(new TimeSlot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(10, 15), "B1-18"));
        cs301_01.setInstructor(prof1);

        Section math201_01 = new Section("MATH201-01", math201, "Spring2025", 200);
        math201_01.addMeetingTime(new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(11, 0), LocalTime.of(12, 15), "A2-89/90"));
        math201_01.setInstructor(prof3);

        Section ace101_02 = new Section("ENG101-02", ace101, "Spring2025", 25);
        ace101_02.addMeetingTime(new TimeSlot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 0), LocalTime.of(16, 15), "B2-09"));
        ace101_02.setInstructor(prof4);

        Section ee2011_01 = new Section("EE2011-01", ee2011, "Spring2025", 200);
        ee2011_01.addMeetingTime(new TimeSlot(DayOfWeek.TUESDAY,LocalTime.of(9,0),LocalTime.of(11,0), "B1-18"));
        ee2011_01.setInstructor(prof5);

        sectionRepo.save(cs101_03, cs101_03.getId());
        sectionRepo.save(cs201_02, cs201_02.getId());
        sectionRepo.save(cs301_01, cs301_01.getId());
        sectionRepo.save(math201_01, math201_01.getId());
        sectionRepo.save(ace101_02, ace101_02.getId());
        sectionRepo.save(ee2011_01, ee2011_01.getId());


        Student alice = new Student("S001", "Alice Brown", "alice@uni.edu", "Computer Science");
        alice.addTranscriptEntry(new TranscriptEntry("CS101", "Spring2024", 3, Grade.A));

        Student bob = new Student("S002", "Bob Davis", "bob@uni.edu", "Computer Science");

        Student carol = new Student("S003", "Carol Miller", "carol@uni.edu", "Mathematics");
        carol.addTranscriptEntry(new TranscriptEntry("MATH101", "Spring2024", 4, Grade.B));

        Student david = new Student("S004", "David Wilson", "david@uni.edu", "Computer Science");
        david.addTranscriptEntry(new TranscriptEntry("CS101", "Spring2024", 3, Grade.B));
        david.addTranscriptEntry(new TranscriptEntry("CS201", "Fall2024", 3, Grade.A));

        Student eve = new Student("S005", "Eve Martinez", "eve@uni.edu", "Mathematics");
        eve.addTranscriptEntry(new TranscriptEntry("MATH101", "Spring2024", 4, Grade.A));
        eve.addTranscriptEntry(new TranscriptEntry("MATH201", "Fall2024", 4, Grade.B_PLUS));

        Student frank = new Student("S006", "Frank Lee", "frank@uni.edu", "Computer Science");

        personRepo.save(alice, alice.getId());
        personRepo.save(bob, bob.getId());
        personRepo.save(carol, carol.getId());
        personRepo.save(david, david.getId());
        personRepo.save(eve, eve.getId());
        personRepo.save(frank, frank.getId());

        Admin admin = new Admin("A001", "Admin User", "admin@uni.edu");
        personRepo.save(admin, admin.getId());
    }

    public void start() {
        System.out.println("=== University Course Registration System ===\n");

        while (true) {
            if (currentUser == null) {
                login();
            } else {
                showMainMenu();
            }
        }
    }

    private void login() {
        System.out.println("Enter your user ID (or 'exit' to quit):");
        System.out.print("> ");
        String id = scanner.nextLine().trim();

        if (id.equalsIgnoreCase("exit")) {
            System.out.println("Goodbye!");
            System.exit(0);
        }

        Optional<Person> person = personRepo.findById(id);
        if (person.isPresent()) {
            currentUser = person.get();
            System.out.println("\nWelcome, " + currentUser.getName() + " (" + currentUser.role() + ")\n");
        } else {
            System.out.println("User not found.");
            System.out.println("Try: S001, S002, S003, S004, S005, S006, I001, I002, I003, A001\n");
        }
    }

    private void showMainMenu() {
        if (currentUser instanceof Student) {
            studentMenu();
        } else if (currentUser instanceof Instructor) {
            instructorMenu();
        } else if (currentUser instanceof Admin) {
            adminMenu();
        }
    }

    private void studentMenu() {
        Student student = (Student) currentUser;
        System.out.println("\n--- Student Menu ---");
        System.out.println("1. Search courses");
        System.out.println("2. View my schedule");
        System.out.println("3. Enroll in section");
        System.out.println("4. Drop section");
        System.out.println("5. View transcript and GPA");
        System.out.println("6. Logout");
        System.out.print("Choice: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                searchCourses();
                break;
            case "2":
                viewSchedule(student);
                break;
            case "3":
                enrollInSection(student);
                break;
            case "4":
                dropSection(student);
                break;
            case "5":
                viewTranscript(student);
                break;
            case "6":
                currentUser = null;
                System.out.println("Logged out.\n");
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    private void searchCourses() {
        System.out.print("Enter search term (code, title, or section ID): ");
        String query = scanner.nextLine().trim();

        List<Section> results = catalogService.searchSections(query);

        if (results.isEmpty()) {
            System.out.println("No sections found.");
        } else {
            System.out.println("\nSearch results:");
            for (Section s : results) {
                System.out.println("  " + s.getId() + " - " +
                        s.getCourse().getCode() + ": " + s.getCourse().getTitle() +
                        " (" + s.getCourse().getCredits() + " credits) - " +
                        s.getTerm() + " - Enrolled: " + s.getEnrolledStudents() + "/" + s.getCapacity());

                List<TimeSlot> times = s.getMeetingTimes();
                for (TimeSlot ts : times) {
                    System.out.println("      " + ts);
                }
            }
        }
    }

    private void viewSchedule(Student student) {


        List<Section> schedule = registrationService.listSchedule(student.getId());

        if (schedule.isEmpty()) {
            System.out.println("No courses");
        } else {
            System.out.println("\nYour schedule for :");
            for (Section s : schedule) {
                System.out.println("  " + s.getId() + " - " + s.getCourse().getTitle());
                List<TimeSlot> times = s.getMeetingTimes();
                for (TimeSlot ts : times) {
                    System.out.println("      " + ts);
                }
            }
        }
    }

    private void enrollInSection(Student student) {
        System.out.print("Enter section ID: ");
        String sectionId = scanner.nextLine().trim();

        Result<Enrollment> result = registrationService.enroll(student.getId(), sectionId);

        if (result.isOk()) {
            System.out.println("SUCCESS: Enrolled in " + sectionId);
        } else {
            System.out.println("FAILED: " + result.getError());
        }
    }

    private void dropSection(Student student) {
        System.out.print("Enter section ID: ");
        String sectionId = scanner.nextLine().trim();

        Result<Void> result = registrationService.drop(student.getId(), sectionId);

        if (result.isOk()) {
            System.out.println("SUCCESS: Dropped " + sectionId);
        } else {
            System.out.println("FAILED: " + result.getError());
        }
    }

    private void viewTranscript(Student student) {
        Transcript transcript = student.getTranscript();
        List<TranscriptEntry> entries = transcript.getEntries();
        if (entries.isEmpty()) {
            System.out.println("No transcript entries.");
            return;
        }
        System.out.println("\n--- Transcript for " + student.getName() + " ---");

        int earnedCredits = 0;
        int attemptedCredits = 0;

        for (TranscriptEntry entry : entries) {
            System.out.println(
                    entry.getCourseCode() + " - "
                            + entry.getTerm() + " - "
                            + entry.getCredits() + " credits - Grade: "
                            + entry.getGrade()
            );

            attemptedCredits += entry.getCredits();

            if (entry.getGrade() != Grade.F && entry.getGrade() != Grade.Fx) {
                earnedCredits += entry.getCredits();
            }
        }
        double gpa = gradingService.computeGPA(student.getId());

        System.out.printf("\nGPA: %.2f\n", gpa);
        System.out.println("Earned Credits: " + earnedCredits);
        System.out.println("Attempted Credits: " + attemptedCredits);
    }

    private void instructorMenu() {
        Instructor instructor = (Instructor) currentUser;
        System.out.println("\n--- Instructor Menu ---");
        System.out.println("1. View my sections");
        System.out.println("2. View section roster");
        System.out.println("3. Post grade");
        System.out.println("4. Logout");
        System.out.print("Choice: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                viewInstructorSections(instructor);
                break;
            case "2":
                viewRoster();
                break;
            case "3":
                postGrade(instructor);
                break;
            case "4":
                currentUser = null;
                System.out.println("Logged out.\n");
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    private void viewInstructorSections(Instructor instructor) {
        List<Section> sections = instructor.getAssignedSections();

        if (sections.isEmpty()) {
            System.out.println("No assigned sections.");
        } else {
            System.out.println("\nYour sections:");
            for (Section s : sections) {
                System.out.println("  " + s.getId() + " - " + s.getCourse().getTitle() +
                        " - Enrolled: " + s.getEnrolledStudents() + "/" + s.getCapacity());
            }
        }
    }

    private void viewRoster() {
        System.out.print("Enter section ID: ");
        String sectionId = scanner.nextLine().trim();

        Optional<Section> sectionOpt = sectionRepo.findById(sectionId);
        if (!sectionOpt.isPresent()) {
            System.out.println("Section not found.");
            return;
        }

        Section section = sectionOpt.get();
        List<Enrollment> roster = section.getRoster();

        if (roster.isEmpty()) {
            System.out.println("No students enrolled.");
        } else {
            System.out.println("\nRoster for " + sectionId + ":");
            for (Enrollment e : roster) {
                if (e.getStatus() == "ENROLLED") {
                    System.out.println("  " + e.getStudent().getId() + " - " +
                            e.getStudent().getName());
                }
            }
        }
    }

    private void postGrade(Instructor instructor) {
        System.out.print("Section ID: ");
        String sectionId = scanner.nextLine().trim();
        System.out.print("Student ID: ");
        String studentId = scanner.nextLine().trim();
        System.out.print("Grade (A, A_MINUS, B_PLUS, B, B_MINUS, C_PLUS, C, C_MINUS, D_PLUS, D, F, Fx) ");
        String gradeStr = scanner.nextLine().trim().toUpperCase();

        Grade grade;
        try {
            grade = Grade.valueOf(gradeStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid grade.");
            return;
        }

        Result<Void> result = gradingService.postGrade(
                instructor.getId(), sectionId, studentId, grade);

        if (result.isOk()) {
            System.out.println("SUCCESS: Grade posted");
        } else {
            System.out.println("FAILED: " + result.getError());
        }
    }

    private void adminMenu() {
        System.out.println("\n--- Admin Menu ---");
        System.out.println("1. Create course");
        System.out.println("2. Create section");
        System.out.println("3. Add time slot to section");
        System.out.println("4. Delete time slot from section");
        System.out.println("5. Assign Instructor");
        System.out.println("6. View all sections");
        System.out.println("7. Add new student");
        System.out.println("8. Add new instructor");
        System.out.println("9. Add new admin");
        System.out.println("10. View all users");
        System.out.println("11. Logout");
        System.out.print("Choice: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                createCourse();
                break;
            case "2":
                createSection();
                break;
            case "3":
                addTimeSlotToSection();
                break;
            case "4":
                deleteTimeSlotInSection();
                break;
            case "5":
                assignInstructor();
                break;
            case "6":
                viewAllSections();
                break;
            case "7":
                addNewStudent();
                break;
            case "8":
                addNewInstructor();
                break;
            case "9":
                addNewAdmin();
                break;
            case "10":
                viewAllUsers();
                break;
            case "11":
                currentUser = null;
                System.out.println("Logged out.\n");
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }


    private String getNextId(String prefix) {
        int maxIdNumber = 0;
        for (Person person : personRepo.findAll()) {
            String id = person.getId();
            if (id.startsWith(prefix)) {
                try {
                    //Extract the numeric part of the ID (e.g., "001" from "S001")
                    String numberStr = id.substring(prefix.length());
                    int currentIdNumber = Integer.parseInt(numberStr);
                    //Find the maximum number encountered
                    if (currentIdNumber > maxIdNumber) {
                        maxIdNumber = currentIdNumber;
                    }
                } catch (NumberFormatException e) {
                    // Ignore IDs that don't follow the expected numeric pattern (e.g., "S-X")
                }
            }
        }

        int nextIdNumber = maxIdNumber + 1;
        //Format the new number back into the "00X" string format
        //This assumes IDs are zero-padded to 3 digits (e.g., 001, 010, 100)
        return prefix + String.format("%03d", nextIdNumber);
    }
    private void createCourse() {
        System.out.println("\n--- Create Course ---");
        System.out.print("Course Code (e.g., CS401): ");
        String code = scanner.nextLine().trim().toUpperCase();
        System.out.print("Course Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Credits: ");
        int credits;
        try {
            credits = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid credit amount.");
            return;
        }

        Result<Course> result = catalogService.createCourse(code, title, credits);

        if (result.isOk()) {
            System.out.println("SUCCESS: Course " + code + " created.");
        } else {
            System.out.println("FAILED: " + result.getError());
        }
    }

    private void createSection() {
        System.out.println("\n--- Create Section ---");
        System.out.print("Section ID (e.g., CS401-01): ");
        String sectionId = scanner.nextLine().trim().toUpperCase();
        System.out.print("Course Code (must exist): ");
        String courseCode = scanner.nextLine().trim().toUpperCase();
        System.out.print("Term (e.g., Spring2025): ");
        String term = scanner.nextLine().trim();
        System.out.print("Capacity: ");
        int capacity;
        try {
            capacity = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid capacity amount.");
            return;
        }

        Result<Section> result = catalogService.createSection(sectionId, courseCode, term, capacity);

        if (result.isOk()) {
            System.out.println("SUCCESS: Section " + sectionId + " created.");
        } else {
            System.out.println("FAILED: " + result.getError());
        }
    }

    private void assignInstructor() {
        System.out.println("\n--- Assign Instructor ---");
        System.out.print("Section ID: ");
        String sectionId = scanner.nextLine().trim().toUpperCase();
        System.out.print("Instructor ID (I###): ");
        String instructorId = scanner.nextLine().trim().toUpperCase();

        Result<Void> result = catalogService.assignInstructor(sectionId, instructorId);

        if (result.isOk()) {
            System.out.println("SUCCESS: Instructor " + instructorId + " assigned to " + sectionId);
        } else {
            System.out.println("FAILED: " + result.getError());
        }
    }
    
    private void addTimeSlotToSection() {
        System.out.print("Section ID: ");
        String sectionId = scanner.nextLine().trim();

        Optional<Section> sectionOpt = sectionRepo.findById(sectionId);
        if (!sectionOpt.isPresent()) {
            System.out.println("FAILED: Section not found.");
            return;
        }

        Section section = sectionOpt.get();

        System.out.println("\nCurrent meeting times for " + sectionId + ":");
        List<TimeSlot> currentTimes = section.getMeetingTimes();
        if (currentTimes.isEmpty()) {
            System.out.println("No meeting times set yet.");
        } else {
            for (int i = 0; i < currentTimes.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + currentTimes.get(i));
            }
        }

        System.out.println("\n--- Add New Time Slot ---");
        System.out.print("Day of week (e.g., MONDAY, FRIDAY): ");
        String dayStr = scanner.nextLine().trim().toUpperCase();
        System.out.print("Start time (HH:MM, e.g., 09:00): ");
        String startTimeStr = scanner.nextLine().trim();
        System.out.print("End time (HH:MM, e.g., 10:15): ");
        String endTimeStr = scanner.nextLine().trim();
        System.out.print("Room: ");
        String room = scanner.nextLine().trim();

        try {
            DayOfWeek day = DayOfWeek.valueOf(dayStr); // Throws IllegalArgumentException if day is invalid
            LocalTime startTime = LocalTime.parse(startTimeStr); // Throws DateTimeParseException
            LocalTime endTime = LocalTime.parse(endTimeStr);     // Throws DateTimeParseException

            if (!startTime.isBefore(endTime)) {
                System.out.println("❌ FAILED: Start time must be before end time.");
                return;
            }

            TimeSlot timeSlot = new TimeSlot(day, startTime, endTime, room);
            section.addMeetingTime(timeSlot);

            System.out.println("SUCCESS: Time slot added - " + timeSlot);

        } catch (IllegalArgumentException e) {
            System.out.println("FAILED: Invalid day of week. Please use MONDAY, TUESDAY, etc.");
            return;
        } catch (DateTimeParseException e) {
            System.out.println("FAILED: Invalid time format. Please use HH:MM (e.g., 14:30).");
            return;
        }

        System.out.print("Add another time slot? (yes/no): ");
        String another = scanner.nextLine().trim().toLowerCase();
        if (another.equals("yes") || another.equals("y")) {
            addTimeSlotToSection();
        }
    }

    private void deleteTimeSlotInSection() {
        System.out.println("\n--- Delete Section Time Slot ---");
        System.out.print("Enter Section ID: ");
        String sectionId = scanner.nextLine().trim();

        Optional<Section> sectionOpt = sectionRepo.findById(sectionId);
        if (!sectionOpt.isPresent()) {
            System.out.println("FAILED: Section not found");
            return;
        }

        Section section = sectionOpt.get();
        List<TimeSlot> currentTimes = section.getMeetingTimes();

        if (currentTimes.isEmpty()) {
            System.out.println("No time slots to delete for this section.");
            return;
        }

        System.out.println("Current meeting times:");
        for (int i = 0; i < currentTimes.size(); i++) {
            // Display index starting at 1 for user friendliness
            System.out.println((i + 1) + ". " + currentTimes.get(i));
        }
        System.out.print("Enter the number of the time slot to delete: ");
        String input = scanner.nextLine().trim();

        try {
            int index = Integer.parseInt(input);

            // Validate index is within bounds (1 to size)
            if (index < 1 || index > currentTimes.size()) {
                System.out.println("FAILED: Invalid number selected.");
                return;
            }
            TimeSlot removed = currentTimes.remove(index - 1);
            System.out.println("SUCCESS: Removed " + removed);
        } catch (NumberFormatException e) {
            System.out.println("FAILED: Please enter a valid number.");
        }
    }

    private void viewAllSections() {
        List<Section> allSections = sectionRepo.findAll();
        if (allSections.isEmpty()) {
            System.out.println("No sections loaded.");
            return;
        }

        System.out.println("\n--- All Sections in System ---");
        for (Section s : allSections) {
            String instructorName = s.getInstructor() != null ?
                    s.getInstructor().getName() :
                    "Unassigned";

            System.out.println("  " + s.getId() + " - " +
                    s.getCourse().getCode() + " (" + s.getTerm() +
                    ") | Instructor: " + instructorName +
                    " | Capacity: " + s.getEnrolledStudents() + "/" + s.getCapacity());
        }
    }
    private void addNewStudent() {
        System.out.println("\n--- Add New Student ---");
        String id = getNextId("S");

        System.out.println("Generated ID: " + id);

        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Major: ");
        String major = scanner.nextLine().trim();

        Student newStudent = new Student(id, name, email, major);
        personRepo.save(newStudent, id);
        System.out.println("Success: Student " + id + " added.");
    }

    private void addNewInstructor() {
        System.out.println("\n--- Add New Instructor ---");
        String id = getNextId("I");

        System.out.println("Generated ID: " + id);

        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Department: ");
        String dept = scanner.nextLine().trim();

        Instructor newInstructor = new Instructor(id, name, email, dept);
        personRepo.save(newInstructor, id);
        System.out.println("Success: Instructor " + id + " added.");
    }

    private void addNewAdmin() {
        System.out.println("\n--- Add New Admin ---");
        String id = getNextId("A");

        System.out.println("Generated ID: " + id);

        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        Admin newAdmin = new Admin(id, name, email);
        personRepo.save(newAdmin, id);
        System.out.println("Success: Admin " + id + " added.");
    }

    private void viewAllUsers() {
        System.out.println("\n--- All Users in System ---");
        List<Person> allUsers = personRepo.findAll();

        // Sorting for better display (optional)
        allUsers.sort(Comparator.comparing(Person::getId));
        for (Person p : allUsers) {
            String details = String.format("[%s] %s (%s) - %s",
                    p.role(), p.getName(), p.getId(), p.getEmail());
            System.out.println(details);
        }
    }
}




