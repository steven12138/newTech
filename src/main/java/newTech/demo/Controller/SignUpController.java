package newTech.demo.Controller;

import newTech.demo.DTO.SignUpDTO;
import newTech.demo.DTO.response;
import newTech.demo.DTO.stepDTO;
import newTech.demo.Service.SignUpService;
import newTech.demo.Utils.jwtUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@PreAuthorize("hasAuthority('student')")
public class SignUpController {

    @Resource
    jwtUtils jwtUtils;

    @Resource
    SignUpService signUpService;

    private int getId(String header) {
        return (int) jwtUtils.getTokenInfo(header.split(" ")[1]).get("id");
    }

    @GetMapping("SignUp")
    public response<SignUpDTO> getSignUpStatus(@RequestHeader(name = "Authorization") String token) {
        return signUpService.getSignUpStatus(getId(token));
    }

    @GetMapping("examHistory")
    public response<SignUpDTO> getExamHistory(@RequestHeader(name = "Authorization") String token) {
        return signUpService.getExamHistory(getId(token));
    }

    @GetMapping("step")
    public response<Integer> getStep(@RequestHeader(name = "Authorization") String token) {
        return signUpService.getStep(getId(token));
    }

    @PostMapping("SignUp")
    public response<Object> SignUp(@RequestHeader(name = "Authorization") String token, @RequestBody SignUpDTO status) {
        return signUpService.SignUp(getId(token), status);
    }

    @PostMapping("step")
    public response<Object> setStep(@RequestHeader(name = "Authorization") String token, @RequestBody stepDTO step) {
        return signUpService.setStep(getId(token), step.getStep());
    }
}
