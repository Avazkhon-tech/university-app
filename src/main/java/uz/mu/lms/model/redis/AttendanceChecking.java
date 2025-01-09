package uz.mu.lms.model.redis;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(timeToLive = 3 * 60)
@ToString
public class AttendanceChecking {

    @Id
    private UUID id;
    private Integer lessonId;

}
