package newTech.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModifyPasswordDTO {
    private String oldPassword;
    private String newPassword;
}
