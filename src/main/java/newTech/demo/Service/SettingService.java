package newTech.demo.Service;

import newTech.demo.DTO.response;
import newTech.demo.DTO.settingDTO;
import newTech.demo.Module.Data.Setting;

public interface SettingService {
    public response<Setting> getSetting();

    public response<Object> modifySetting(settingDTO s);
}
