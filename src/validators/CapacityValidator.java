package validators;

import models.Section;
import utilities.*;


public class CapacityValidator {
    public Result<Void> validate(Section section) {
        if (section.isFull()) {
            return Result.fail("Section is full (capacity: " + section.getCapacity() + ")");
        }
        return Result.ok(null);
    }
}