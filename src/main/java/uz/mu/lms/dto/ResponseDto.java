package uz.mu.lms.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {

    private Integer code;
    private String message;
    private T data;
    private boolean success;
}