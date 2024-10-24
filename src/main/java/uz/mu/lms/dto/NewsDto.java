package uz.mu.lms.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {

    private Long id;
    private String title;
    private String content;
    private String location;
    private String eventTime;
    private String contentUrl;
}
