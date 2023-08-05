package lbn.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Setmeal implements Serializable {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 套餐名称
     */
    private String name;
    /**
     * 套餐编码
     */
    private String code;
    /**
     * 套餐助记码
     */
    private String helpCode;
    /**
     * 套餐适用性别：0不限 1男 2女
     */
    private String sex;
    /**
     * 套餐适用年龄
     */
    private String age;
    /**
     * 套餐价格
     */
    private Float price;
    /**
     * 备注
     */
    private String remark;
    /**
     * 注意事项
     */
    private String attention;
    /**
     * 套餐对应图片存储路径
     */
    private String img;
    /**
     * 体检套餐对应的检查组，多对多关系
     */
    @TableField(exist = false)
    private List<CheckGroup> checkGroups;
}