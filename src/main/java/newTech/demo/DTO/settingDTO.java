package newTech.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class settingDTO {
    private long st_time;
    private long ed_time;
    private boolean force_open;
    private boolean strategy;
}
