package uz.mu.lms.projection;

public interface CourseWithTeacherProjection {

    Integer getId();
    String getTitle();
    String getDescription();
    String getTaughtLanguage();
    String getDepartmentName();

    String getTeacherName();
    String getTeacherEmail();  // You can add more properties as needed

    String getTeacherAssistantName();
    String getTeacherAssistantEmail();
}
