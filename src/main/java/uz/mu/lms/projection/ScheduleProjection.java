package uz.mu.lms.projection;

import java.time.LocalTime;

public interface ScheduleProjection {

    String getDayOfWeek();

    LocalTime getStartTime();

    LocalTime getEndTime();

    String getCourseTitle();

    String getRoomNumber();

    String getLessonType();

    String getTeacherFullName();
}
