package uz.mu.lms.projection;

import uz.mu.lms.model.GradingScale;

public interface CourseGradeProjection {

    Integer getAttendancePresent();

    Integer getAttendanceTotal();

    Integer getProgress();

    Integer getMidterm();

    Integer getFinal();
}
