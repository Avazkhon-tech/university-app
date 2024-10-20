package uz.mu.lms.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

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

    private String imageUrl = getImageUrl() + id;

    public String getImageUrl() {
        if (id != null) {
            return "http://hostaddr/api/image/" + id;
        }
        return null;
    }
}
