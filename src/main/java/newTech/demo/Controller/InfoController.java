package newTech.demo.Controller;

import newTech.demo.DTO.response;
import newTech.demo.Service.SignUpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController()
@RequestMapping("/info")
public class InfoController {


    @Resource
    SignUpService signUpService;

    @GetMapping("/error")
    public response<Object> getErrorInfo() {
        return signUpService.getMsg();
    }


}
