package validators;

import models.*;
import utilities.*;

import java.util.*;

public class ScheduleConflictChecker {
    public Result<Void> checkConflicts(Student student, Section newSection, String term) {
        ArrayList<Section> currentSections = new ArrayList<>();
        //Check all active sections
        for (Enrollment enrollment : student.getCurrentEnrollments()) {
            Section existingSection = enrollment.getSection();
            if (enrollment.getStatus() == "ENROLLED" &&
                    existingSection.getTerm().equals(term)) {

                currentSections.add(existingSection);
            }
        }
        //Now check if cuurent sections conflict with the new ones
        for (Section existing : currentSections) {
            if (hasTimeConflict(existing, newSection)) {
                return Result.fail("Time conflict with " +
                        existing.getCourse().getCode() + " (" + existing.getId() + ")");
            }
        }
        return Result.ok(null);
    }

    //Just supporting function to check time condlicts
    private boolean hasTimeConflict(Section s1, Section s2) {
        for (TimeSlot t1 : s1.getMeetingTimes()) {
            for (TimeSlot t2 : s2.getMeetingTimes()) {
                if (t1.overlapsWith(t2)) {
                    return true;
                }
            }
        }
        return false;
    }
}
