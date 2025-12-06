# University Course Registration System (Mykyta Korobko 240201342)


## What This Project Does

Basically, it's like a mini version of the course registration system we use at our university. Every user has a role and can can do something with the domain classes

- **Students** can search for courses, enroll/drop sections, and check their transcripts
- **Instructors** can view their assigned classes and post grades
- **Admins** can create courses, sections, and assign instructors

it validates everything like checking if you have the prerequisites, if the class is full, or if there's a schedule conflict.

## Project Structure

I organized everything into packages to keep it clean:

```
src/
├── models/              # All the data classes
│   ├── Person.java      # Base class for everyone
│   ├── Student.java
│   ├── Instructor.java
│   ├── Admin.java
│   ├── Course.java
│   ├── Section.java
│   ├── Enrollment.java
│   ├── Transcript.java
│   ├── TranscriptEntry.java
│   ├── TimeSlot.java
│   ├── Grade.java       # Enum with all grades
│   └── EnrollmentStatus.java
│
├── repository/          # Data storage
│   └── InMemoryRepository.java  # Generic repository
│
├── services/            # Business logic
│   ├── RegistrationService.java
│   ├── CatalogService.java
│   ├──GradingService.java
│   └──UniversityRegistrationApp.java  # Main application
│
├── validators/          # Validation classes
│   ├── PrerequisiteValidator.java
│   ├── CapacityValidator.java
│   └── ScheduleConflictChecker.java
│
├── utilities/                # Helper classes
│   └── Result.java      # For handling success/failure
└──
```

## How to Build and Run

### Requirements
- Java 11 or higher

### Manual Compilation (if you don't have Maven)

```bash
# Compile all Java files
javac -d bin src/**/*.java

# Run the application
java -cp bin ui.UniversityRegistrationApp
```

## How to Use It

### 1. Login
When you start the app, it asks for your user ID. Here are the test accounts I created:

**Students:**
- `S001` - Alice (has CS101 completed)
- `S002` - Bob (no courses yet)
- `S003` - Carol (has MATH101 completed)
- `S004` - David (has CS101 and CS201 completed)
- `S005` - Eve (has MATH101 and MATH201 completed)
- `S006` - Frank (no courses yet)

**Instructors:**
- `I001` - Shahram Taheri (teaches CS courses)
- `I002` - Halil Özmen (teaches CS courses)
- `I003` - Veli Shakhmurov (teaches Math courses)
- `I004` - Ekaterina Chicherina (teaches english courses)
- `I005` - Zahra GOLRIZKHATAMI (teaches digital systems courses)

**Admin:**
- `A001` - Admin User

Just type the ID and press enter. Type `exit` to quit.

### 2. Student Menu

After logging in as a student, you get these options:

1. **Search courses** - Type part of a course code or title to find sections
2. **View my schedule** - See what you're enrolled in for a specific term
3. **Enroll in section** - Add a section (validates prerequisites, capacity, and conflicts)
4. **Drop section** - Remove a section from your schedule
5. **View transcript and GPA** - See your grades and calculated GPA
6. **Logout**

### 3. Instructor Menu

1. **View my sections** - See all classes you're teaching
2. **View section roster** - See who's enrolled in a specific section
3. **Post grade** - Assign a grade to a student
4. **Logout**

### 4. Admin Menu

1. **Create course** - Add a new course to the catalog
2. **Create section** - Add a new section for a course
3. **Add time slot to section** - Add meeting times to a section
4. **Delete time slot from section** - Remove meeting times from a section
5. **Assign instructor** - Assign an instructor to a section
6. **View all sections** - See everything in the system
7. **Add new student** - Create a new student account
8. **Add new instructor** - Create a new instructor account
9. **Add new admin** - Create a new admin account
10. **View all users** - See all students, instructors, and admins
11. **Logout**

## Demo Scenarios

Let me show you some example scenarios that demonstrate the validation rules:

### Scenario 1: Adding a student as an Admin 

```
Login as: A001 (Admin User)
Menu choice: 5 (Add new student)
(We don't need to write an id number ,because it generates automatically with fucntion getNextId)
Name: Mykyta Korobko
Email: mykyta.korobko@std.antalya.edu.tr
Major: Computer Engineering
Result: Success: Student S007 added.

```
![Scenario 1.png](screenshots/Scenario%201.png)

```
We created a new student and if we check it with the method view all users we'll see him
```
![Scenario 1 cont.png](screenshots/Scenario%201%20cont.png)


### Scenario 2: Complete Enrollment Workflow (Prerequisites Chain)

```
Now lets try to enroll on some courses as an enrolled student

Login as: S007 
   Try to enroll in CS201-01 
   Result: FAILED - Missing prerequisite: CS101

   Enroll in CS101-01 (Intro to Programming)
   Result: SUCCESS - No prerequisites needed!
```
![Scenario 2.png](screenshots/Scenario%202.png)
```
   Login as: I001 (Shahram Taheri)
   Post grade for Mykyta in CS101-01: Grade B
   Result: SUCCESS - Mykyta now has CS101 completed
```
![Scenario 2 cont.png](screenshots/Scenario%202%20cont.png)
```
   Login as: S007
   Now enroll in CS201-01
   Result: SUCCESS - Has CS101 prerequisite with grade B (passing)!
```
![Scenario 2 conmt1.png](screenshots/Scenario%202%20conmt1.png)
```
   Login as: I001 (Shahram Taheri)
   Post grade for Mykyta in CS201-01: Grade F
   Result: SUCCESS
```
![Scenario 2 konjsd.png](screenshots/Scenario%202%20konjsd.png)
```
   Login as: S007 
   Now enroll in CS301-01
   Result: FAILED: Missing or failed prerequisite: CS201
   Because Mykyta has grade F for the CS301-01 ,so this course consider failed

Mykyta's final transcript:
  CS101 - Fall2024 - 3 credits - Grade: B
  CS201 - Fall2024 - 3 credits - Grade: F
GPA: 3.00
```
![Scenario 2 cont3.png](screenshots/Scenario%202%20cont3.png)

### Scenario 3: Creating a new section as an admin and assigning instructor

```
Now lets try to create section

Login as: A001
   Menu choice: 1 (Create course)
   Course Code : CS405
   Course Title: Database Systems
   Credits: 4
   Result: SUCCESS: Course CS405 created.
```
![Scenario 3.png](screenshots/Scenario%203.png)
```
Now creating a section

    Menu choice: 2 (Create section)
    Section ID : CS405-01
    Course Code (must exist): CS405
    Term : Spring2025
    Capacity: 190
    Result: SUCCESS: Section CS405-01 created.
```
![Scenario 3 cont.png](screenshots/Scenario%203%20cont.png)

```
Adding timeslot for section

    Menu Choice: 3 (Add time slot to section)
    Section ID: CS405-01
    Day of week : TUESDAY
    Start time : 12:00
    End time : 13:00
    Room: B2-29
    Result: SUCCESS: Time slot added - TUESDAY 12:00-13:00 in B2-29
    Add another time slot? (yes/no): no
```
![Scenario 3 kinjhs.png](screenshots/Scenario%203%20kinjhs.png)
```
And assigning instructor 
    Menu Choice: 5 (Assign Instructor)
    Section ID: CS405-01
    Instructor ID : I002
    Result: SUCCESS: Instructor I002 assigned to CS405-01
    and now if wee see all the sections we'll see our CS405-01 with the instructor that we've assigned
```
![Scenario 3 cont2.png](screenshots/Scenario%203%20cont2.png)
## Key Features I Implemented

### OOP Concepts Used

1. **Inheritance**
    - `Person` → `Student`, `Instructor`, `Admin`
    - Shows different user types with shared properties

2. **Polymorphism**
    - `role()` method is overridden in each subclass
    - Different behavior for different user types

3. **Interfaces**
    - `Schedulable` interface for anything with meeting times
    - Shows contracts that classes must follow

4. **Generics**
    - `InMemoryRepository<T>` works with any entity type
    - `Result<T>` wraps success/failure for any return type
    - Type-safe code that's reusable

### Business Rules I Enforced

1. **Prerequisite Checking**
    - Must have completed prerequisite courses with grade C or better
    - Example: Need CS101 to take CS201

2. **Capacity Management**
    - Sections have enrollment limits
    - Can't enroll if section is full

3. **Schedule Conflict Detection**
    - Can't enroll in two sections that meet at the same time
    - Uses the formula: `startA < endB && startB < endA` (same day)

4. **GPA Calculation**
    - Weighted by credits: `(grade_points × credits) / total_credits`
    - Excludes I (Incomplete) and W (Withdrawal) grades

## Sample Data Loaded

The system comes with:
- **6 courses** with prerequisite chains (CS101→CS201→CS301, MATH101→MATH201, ENG101)
- **12 sections** across Fall2024 and Spring2025 terms
- **6 students** with various transcript histories
- **5 instructors** assigned to sections
- **1 admin** for system management

## Design Decisions

### Why I used `Map<String, TranscriptEntry>` in Student
I store transcript entries in a HashMap with course codes as keys. This makes it super fast to check if a student has taken a specific course just O(1) lookup instead of looping through a list.

### Why I used the Result<T> pattern
Instead of throwing exceptions for expected failures (like "course full" or "missing prerequisite"), I return a Result object. This makes it clear when something can fail and forces you to handle the error case.

### Why I separated validators
I put each validation rule in its own class:
- `PrerequisiteValidator`
- `CapacityValidator`
- `ScheduleConflictChecker`

This follows the Single Responsibility Princple - each class does one thing well, and if requirements change, I only need to modify one class.

### Repository Pattern
The `InMemoryRepository<T>` is generic and works with any entity. Right now it stores everything in a HashMap, but I could easily swap it for database storage later without changing any of the service code.

## Known Limitations

- Everything is stored in memory - data disappears when you close the app
- No persistent storage (could add JSON/file storage later)
- No waitlist feature (could be added)
- Admin can't override prerequisites or capacity (mentioned in requirements but not implemented)
- No billing/tuition calculation




**Note:** Type `exit` at the login prompt to quit the application.