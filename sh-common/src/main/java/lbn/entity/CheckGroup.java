package lbn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("t_check_group")
public class CheckGroup implements Serializable {
    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /** 编码 */
    private String code;
    /** 名称 */
    private String name;
    /** 助记码 */
    // @TableField("helpCode")
    private String helpCode;
    /** 适用性别 */
    private String sex;
    /** 简介 */
    private String remark;
    /** 注意事项 */
    private String attention;

    /** 一个检查组合包含多个检查项 */
    @TableField(exist = false)
    private List<CheckItem> checkItems;
}
