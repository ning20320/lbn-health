package lbn.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Member implements Serializable {
    /** 主键 */
    private Integer id; //
    /** 档案号 */
    private String fileNumber;
    /** 姓名 */
    private String name;
    /** 性别 */
    private String sex;
    /** 身份证号 */
    private String idCard;
    /** 手机号 */
    private String phoneNumber;
    /** 注册时间 */
    private Date regTime;
    /** 登录密码 */
    private String password;
    /** 邮箱 */
    private String email;
    /** 出生日期 */
    private Date birthday;
    /** 备注 */
    private String remark;
}
