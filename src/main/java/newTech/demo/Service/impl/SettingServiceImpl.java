package newTech.demo.Service.impl;

import newTech.demo.DTO.response;
import newTech.demo.DTO.returnCode;
import newTech.demo.Module.Data.Setting;
import newTech.demo.Module.Data.repository.SettingRepository;
import newTech.demo.Service.SettingService;
import org.springframework.beans.BeanUtils;
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
    public response<Object> modifySetting(Setting s) {
        try {
            Setting record = settingRepo.findFirstById(1);
            BeanUtils.copyProperties(s, record);
            settingRepo.save(record);
            return new response<>(returnCode.success, null);
        } catch (Exception e) {
            return new response<>(returnCode.UnknownError, e.getMessage());
        }
    }
}
