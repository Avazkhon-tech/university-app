package uz.mu.lms.projection;

import java.time.LocalDateTime;
import java.util.List;


public interface StudentExamsProjection {

    String getCourseTitle();

    String getExamType();

    String getRoomNumber();

    LocalDateTime getExamDateTime();

    String getTeacherNames();

    Integer getExamFee();
}

