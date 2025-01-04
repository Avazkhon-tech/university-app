package uz.mu.lms.dto;

import java.util.List;

public record CourseMaterialDto(

    Integer id,

    String title,

    List<AttachmentDto> attachments
)
{}
