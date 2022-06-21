package newTech.demo.Module.Data;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
@ExcelTarget("UserInfo")
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Excel(name = "用户名")
    @Column(name = "username", unique = true)
    private String username;

    @Excel(name = "姓名")
    @Column(name = "realname")
    private String realName;

    @Column(name = "password")
    private String password;

    @Excel(name = "信息技术报名", replace = {"1_true", "0_false"})
    @Column(name = "is_tech")
    private boolean is_tech;

    @Excel(name = "通用技术报名", replace = {"1_true", "0_false"})
    @Column(name = "is_phy")
    private boolean is_phy;

    @Column(name = "step")
    private int step;

    @Column(name = "is_admin")
    private boolean is_admin;

    @Excel(name = "学考号")
    @Column(name = "eid")
    private String eid;

    @Excel(name = "学号")
    @Column(name = "sid", unique = true)
    private String sid;

    @Column(name = "max_credit")
    private int maxCredit;

    @JoinColumn(name = "forbidden_id")
    @OneToOne
    private UserForbidden userForbidden;

    public boolean getIs_tech() {
        return is_tech;
    }

    public boolean getIs_phy() {
        return is_phy;
    }
}
