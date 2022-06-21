package newTech.demo.Controller;

import newTech.demo.DTO.response;
import newTech.demo.Service.InfoService;
import newTech.demo.Service.SignUpService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController()
@RequestMapping("/info")
public class InfoController {

    @Resource
    SignUpService signUpService;

    @Resource
    InfoService infoService;

    @GetMapping("/error")
    public response<Object> getErrorInfo() {
        return signUpService.getMsg();
    }

    @GetMapping("/status")
    public response<Object> getStatus() {
        return infoService.getStatus();
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/num")
    public response<Object> getNum() {
        return infoService.getNum();
    }
}
