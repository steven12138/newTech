package newTech.demo.Service.impl;

import newTech.demo.DTO.response;
import newTech.demo.DTO.returnCode;
import newTech.demo.DTO.settingDTO;
import newTech.demo.Module.Data.Setting;
import newTech.demo.Module.Data.repository.SettingRepository;
import newTech.demo.Service.SettingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class SettingServiceImpl implements SettingService {

    @Resource
    private SettingRepository settingRepo;

    @Override
    public response<Setting> getSetting() {
        Optional<Setting> s = settingRepo.findById(1);
        System.out.println("get_service");
        return s.map(setting -> new response<>(returnCode.success, setting))
                .orElseGet(() -> new response<>(returnCode.UnknownError, null));
    }

    @Override
    public response<Object> modifySetting(settingDTO s) {
        try {
            Setting record = settingRepo.findFirstById(1);
            record.setEd_time(s.getEd_time());
            record.setSt_time(s.getSt_time());
            record.setForce_open(s.isForce_open());
            record.setStrategy(s.isStrategy());
            settingRepo.save(record);
            return new response<>(returnCode.success, null);
        } catch (Exception e) {
            return new response<>(returnCode.UnknownError, e.getMessage());
        }
    }
}
