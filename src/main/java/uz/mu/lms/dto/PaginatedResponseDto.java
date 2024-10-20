package uz.mu.lms.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponseDto<T>  {

    private Integer code;
    private String message;
    private T data;
    private boolean success;
    private int page;
    private int size;
}
