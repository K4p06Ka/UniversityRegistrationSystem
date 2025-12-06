package validators;

import models.*;
import utilities.*;

import java.util.List;


public class PrerequisiteValidator {

    public Result<Void> validate(Transcript transcript, Course course) {
        if (course.getPrerequisites().isEmpty()) {
            return Result.ok(null);
        }
        List<TranscriptEntry> allEntries = transcript.getEntries();

        //Loop through all required prerequisites for the course
        for (String requiredCode : course.getPrerequisites()) {
            boolean hasPassed = false;
            //Check the student's entire transcript
            for (TranscriptEntry entry : allEntries) {
                if (entry.getCourseCode().equals(requiredCode)) {
                    if (entry.getGrade() != null && entry.getGrade().isPassingGrade()) {
                        hasPassed = true;
                        break;
                    }
                }
            }

            if (!hasPassed) {
                return Result.fail("Missing or failed prerequisite: " + requiredCode);
            }
        }
        return Result.ok(null);
    }
}