package newTech.demo.Service;

import newTech.demo.DTO.LoginDTO;
import newTech.demo.DTO.ModifyPasswordDTO;
import newTech.demo.DTO.User;
import newTech.demo.DTO.response;

public interface LoginService {
    response<LoginDTO> login(User user);

    response<Object> logout(String s);

    response<Object> modifyPassword(String s, ModifyPasswordDTO modifyPasswordDTO);
}
