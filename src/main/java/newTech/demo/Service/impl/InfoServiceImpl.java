package newTech.demo.Service.impl;

import newTech.demo.DTO.response;
import newTech.demo.DTO.returnCode;
import newTech.demo.Module.Data.Setting;
import newTech.demo.Module.Data.repository.AccountRepository;
import newTech.demo.Module.Data.repository.SettingRepository;
import newTech.demo.Service.InfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class InfoServiceImpl implements InfoService {

    @Resource
    SettingRepository settingRepo;

    @Resource
    AccountRepository accountRepo;

    @Override
    public response<Object> getStatus() {
        Optional<Setting> setting_opt = settingRepo.findById(1);
        if (setting_opt.isPresent()) {
            Setting setting = setting_opt.get();
            long st_time = setting.getSt_time();
            long ed_time = setting.getEd_time();
            long now = System.currentTimeMillis();
            if (!(now >= st_time && now <= ed_time) && !setting.isForce_open()) {
                return new response<>(returnCode.success, false);
            }
            return new response<>(returnCode.success, true);
        }
        return new response<>(returnCode.success, false);
    }

    @Override
    public response<Object> getNum() {
        try {
            int num = accountRepo.countAccountsByIs_techIsTrueOrIs_phyIsTrue();
            int all = accountRepo.countAccountByIs_adminIsFalse();
            return new response<>(returnCode.success, num + " / " + all);
        } catch (Exception e) {
            return new response<>(returnCode.UnknownError, e.getMessage() + " " + e.getClass().getName());
        }
    }


}
