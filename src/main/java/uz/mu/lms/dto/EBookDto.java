package uz.mu.lms.dto;

public record EBookDto(

    Integer id,

    String title,

    String author,

    String description,

    String isbn,

    Integer bookCategoryId,

    AttachmentDto bookFile,

    AttachmentDto bookCoverImage
) {}
