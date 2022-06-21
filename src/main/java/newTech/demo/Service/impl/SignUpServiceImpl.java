package newTech.demo.Service.impl;

import newTech.demo.DTO.SignUpDTO;
import newTech.demo.DTO.response;
import newTech.demo.DTO.returnCode;
import newTech.demo.Module.Data.Account;
import newTech.demo.Module.Data.Setting;
import newTech.demo.Module.Data.UserForbidden;
import newTech.demo.Module.Data.repository.AccountRepository;
import newTech.demo.Module.Data.repository.SettingRepository;
import newTech.demo.Service.SignUpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Resource
    AccountRepository accountRepo;

    @Resource
    SettingRepository settingRepo;

    @Override
    public response<SignUpDTO> getSignUpStatus(int id) {
        Optional<Account> record_opt = accountRepo.findById(id);
        if (record_opt.isPresent()) {
            Account record = record_opt.get();
            return new response<>(returnCode.success, new SignUpDTO(record.getIs_tech(), record.getIs_phy()));
        }
        return new response<>(returnCode.UnknownRecord, null);
    }

    @Override
    public response<SignUpDTO> getExamHistory(int id) {
        Optional<Account> record_opt = accountRepo.findById(id);
        if (record_opt.isPresent()) {
            Account record = record_opt.get();
            UserForbidden userForbidden = record.getUserForbidden();
            if (Objects.isNull(userForbidden)) {
                return new response<>(returnCode.success, new SignUpDTO(false, false));
            }
            return new response<>(returnCode.success, new SignUpDTO(record.getUserForbidden().isTech(), record.getUserForbidden().isPhy()));
        }
        return new response<>(returnCode.UnknownRecord, null);
    }

    @Override
    public response<Integer> getStep(int id) {
        Optional<Account> record_opt = accountRepo.findById(id);
        if (record_opt.isPresent()) {
            Account record = record_opt.get();
            return new response<>(returnCode.success, record.getStep());
        }
        return new response<>(returnCode.UnknownRecord, null);
    }

    @Override
    public response<Object> SignUp(int id, SignUpDTO status) {
        Optional<Setting> setting_opt = settingRepo.findById(1);
        if (setting_opt.isPresent()) {
            Setting setting = setting_opt.get();
            long st_time = setting.getSt_time();
            long ed_time = setting.getEd_time();
            long now = System.currentTimeMillis();
            if (!(now >= st_time && now <= ed_time) && !setting.isForce_open()) {
                return new response<>(returnCode.SystemOff, null);
            }
        } else {
            return new response<>(returnCode.SystemOff, null);
        }

        Optional<Account> record_opt = accountRepo.findById(id);
        if (record_opt.isPresent()) {
            Account record = record_opt.get();
            UserForbidden forbidden = record.getUserForbidden();
            if (!Objects.isNull(forbidden)) {
                if (forbidden.isPhy() && forbidden.isTech())
                    return new response<>(returnCode.BothPassed, null);
                if (forbidden.isTech() && status.isTech())
                    return new response<>(returnCode.TechPassed, null);
                if (forbidden.isPhy() && status.isPhy())
                    return new response<>(returnCode.PhyPassed, null);
            }
            record.set_tech(status.isTech());
            record.set_phy(status.isPhy());
            record.setStep(2);
            try {
                accountRepo.save(record);
                return new response<>(returnCode.success, null);
            } catch (Exception e) {
                return new response<>(returnCode.UnknownError, e.getMessage());
            }
        }
        return new response<>(returnCode.UnknownRecord, null);
    }

    @Override
    public response<Object> setStep(int id, int step) {
        Optional<Account> record_opt = accountRepo.findById(id);
        if (record_opt.isPresent()) {
            Account record = record_opt.get();
            record.setStep(step);
            accountRepo.save(record);
            return new response<>(returnCode.success, null);
        }
        return new response<>(returnCode.UnknownRecord, null);
    }

    @Override
    public response<Object> getMsg() {
        Optional<Setting> s = settingRepo.findById(1);
        return s.<response<Object>>map(setting -> new response<>(returnCode.success, setting.getError_msg())).orElseGet(() -> new response<>(returnCode.UnknownRecord, null));
    }
}
