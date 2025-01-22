package uz.mu.lms.projection;

public interface AttendanceSummaryProjection {

    String getCourseTitle();

    Integer getTotalLessons();

    Integer getAbsentCount();

    Integer getPercentage();
}
