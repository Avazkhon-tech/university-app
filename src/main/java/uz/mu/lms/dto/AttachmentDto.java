package uz.mu.lms.dto;

public record AttachmentDto(

    Integer id,

    String filename,

    String fileType,

    String contentUrl
) {}
