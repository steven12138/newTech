package newTech.demo.Service;

import newTech.demo.DTO.SignUpDTO;
import newTech.demo.DTO.response;

public interface SignUpService {
    public response<SignUpDTO> getSignUpStatus(int id);

    public response<SignUpDTO> getExamHistory(int id);

    public response<Integer> getStep(int id);

    public response<Object> SignUp(int id, SignUpDTO status);

    public response<Object> setStep(int id, int step);

    public response<Object> getMsg();
}
