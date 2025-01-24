package uz.mu.lms.projection;

import java.time.LocalDateTime;


public interface StudentExamsProjection {

    String getCourseTitle();

    String getExamType();

    String getRoomNumber();

    LocalDateTime getExamDateTime();

    String getTeacherFullName();

    Integer getExamFee();
}

