package newTech.demo.Controller;

import newTech.demo.DTO.response;
import newTech.demo.DTO.settingDTO;
import newTech.demo.Module.Data.Setting;
import newTech.demo.Service.SettingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@PreAuthorize("hasAuthority('admin')")
public class settingsController {

    @Resource
    SettingService settingService;

    @GetMapping("setting")
    response<Setting> getSettings() {
        System.out.println("Get_Settings");
        return settingService.getSetting();
    }

    @PostMapping("modifySetting")
    response<Object> modifySetting(@RequestBody settingDTO settingDTO) {
        return settingService.modifySetting(settingDTO);
    }
}
