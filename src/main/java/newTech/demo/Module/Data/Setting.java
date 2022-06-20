package newTech.demo.Module.Data;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "setting")
public class Setting {

    @Id
    @Column(name = "id")
    int id;

    @Column(name = "st_time")
    private long st_time;

    @Column(name = "ed_time")
    private long ed_time;

    @Column(name = "strategy")
    private boolean strategy;

    @Column(name = "force_open")
    private boolean force_open;

    @Column(name = "info_error_msg")
    private String error_msg;
}
