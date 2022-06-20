package newTech.demo.Service.impl;

import newTech.demo.DTO.*;
import newTech.demo.Module.Data.Account;
import newTech.demo.Module.Data.Setting;
import newTech.demo.Module.Data.repository.AccountRepository;
import newTech.demo.Module.Data.repository.SettingRepository;
import newTech.demo.Service.LoginService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private newTech.demo.Utils.jwtUtils jwtUtils;
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;
    @Resource
    private AccountRepository accountRepo;
    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private SettingRepository settingRepo;

    @Override
    public response<LoginDTO> login(User user) {
        Account target = accountRepo.findAccountByUsername(user.getUsername());
        if (Objects.isNull(target)) {
            return new response<LoginDTO>(returnCode.wrongUsernameOrPassword, null);
        }

        Optional<Setting> setting_opt = settingRepo.findById(1);
        if (setting_opt.isPresent()) {
            Setting setting = setting_opt.get();
            long st_time = setting.getSt_time();
            long ed_time = setting.getEd_time();
            long now = System.currentTimeMillis();
            if (!(now >= st_time && now <= ed_time) && !setting.isForce_open() && !target.is_admin()) {
                return new response<>(returnCode.SystemOff, null);
            }
        } else if (!target.is_admin()) {
            return new response<>(returnCode.SystemOff, null);
        }

        if (!passwordEncoder.matches(user.getPassword(), target.getPassword())) {
            return new response<LoginDTO>(returnCode.wrongUsernameOrPassword, null);
        }

        String token = jwtUtils.generateToken(target);

        redisTemplate.opsForValue().set("login" + target.getId(), token, Duration.ofMillis(jwtUtils.getExpired_time()));

        return new response<LoginDTO>(
                returnCode.success,
                new LoginDTO(token)
        );
    }

    @Override
    public response<Object> logout(String token) {
        Integer id = jwtUtils.getId(token);
        if (Boolean.FALSE.equals(redisTemplate.delete("login" + id))) {
            return new response<Object>(returnCode.unKnownError, null);
        }
        return new response<Object>(
                returnCode.success,
                null
        );
    }

    @Override
    public response<Object> modifyPassword(String token, ModifyPasswordDTO modifyPasswordDTO) {
        Integer id = jwtUtils.getId(token);
        Account target = accountRepo.findById(id).get();
        if (passwordEncoder.matches(modifyPasswordDTO.getOldPassword(), target.getPassword())) {
            target.setPassword(passwordEncoder.encode(modifyPasswordDTO.getNewPassword()));
            accountRepo.save(target);
            return new response<>(returnCode.success, null);
        }

        redisTemplate.delete("login" + id);

        return new response<>(returnCode.PasswordNotMatch, null);
    }
}
