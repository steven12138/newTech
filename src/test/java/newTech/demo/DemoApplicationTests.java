package newTech.demo;

import newTech.demo.Utils.jwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    jwtUtils jwt;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    void encoder() {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }

}
