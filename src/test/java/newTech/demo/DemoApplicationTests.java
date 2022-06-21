package newTech.demo;

import newTech.demo.Module.Data.Account;
import newTech.demo.Module.Data.repository.AccountRepository;
import newTech.demo.Utils.jwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    jwtUtils jwt;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private AccountRepository accountRepo;

    @Test
    void encoder() {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }

    @Test
    @Commit
    @Transactional
    void edit() {
        Account record = accountRepo.findAccountBySid("2224111");
        record.getUserForbidden().setPhy(true);
        accountRepo.save(record);
    }

}
