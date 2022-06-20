package newTech.demo.Controller;

import newTech.demo.DTO.*;
import newTech.demo.Service.LoginService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("user")
public class userController {

    @Resource
    LoginService loginService;

    @PostMapping("login")
    response<LoginDTO> login(@RequestBody User user) {
        return loginService.login(user);
    }

    @PostMapping("logout")
    response<Object> login(@RequestHeader(name = "Authorization", required = true) String authorization) {
        return loginService.logout(authorization.split(" ")[1]);
    }

    @PostMapping("modifyPassword")
    response<Object> modifyPassword(
            @RequestHeader(name = "Authorization", required = true) String authorization,
            @RequestBody ModifyPasswordDTO modifyPasswordDTO
    ) {
        return loginService.modifyPassword(authorization.split(" ")[1], modifyPasswordDTO);
    }

    @GetMapping("checkLogin")
    response<Object> checkLogin() {
        return new response<>(returnCode.success, null);
    }
}
