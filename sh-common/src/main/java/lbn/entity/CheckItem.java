package lbn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class CheckItem implements Serializable {
    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /** 项目编码 */
    private String code;
    /** 项目名称 */
    private String name;
    /** 适用性别 */
    private String sex;
    /** 适用年龄（范围），例如：20-50 */
    private String age;
    /** 价格 */
    private Float price;
    /** 检查项类型，分为检查和检验两种类型 */
    private String type;
    /** 项目说明 */
    private String remark;
    /** 注意事项 */
    private String attention;
}
