package uz.mu.lms.dto;

public record NewsDto (

    Integer id,
    String title,
    String body,
    String location,
    String eventTime,
    String imageUrl
)
{}
