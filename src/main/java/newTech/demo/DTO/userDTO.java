package newTech.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class userDTO {
    private boolean is_phy;
    private boolean is_tech;
    private String realName;
    private String username;
    private String password;
    private String sid;
    private String eid;
    private boolean is_admin;
    private int maxCredit;
}
