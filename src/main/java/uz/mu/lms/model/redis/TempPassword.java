package uz.mu.lms.model.redis;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(timeToLive = 3 * 60)
@ToString
public class TempPassword {

    @Id
    private Integer userId;
    private String password;
}
