package uz.mu.lms.dto;

public record PhysicalBookDto(

    Integer id,

    String title,

    String author,

    String description,

    String isbn,

    Integer bookCategoryId,

    Integer quantity,

    AttachmentDto bookCoverImage
) {}
