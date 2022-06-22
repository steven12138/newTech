package newTech.demo.Service;

import newTech.demo.DTO.response;
import newTech.demo.Module.Data.Setting;

public interface SettingService {
    response<Setting> getSetting();

    response<Object> modifySetting(Setting s);
}
