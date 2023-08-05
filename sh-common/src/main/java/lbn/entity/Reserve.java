package lbn.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Reserve implements Serializable {
    public static final String ORDERTYPE_TELEPHONE = "电话预约";
    public static final String ORDERTYPE_WEIXIN = "微信预约";
    public static final String ORDERSTATUS_YES = "已到诊";
    public static final String ORDERSTATUS_NO = "未到诊";
    /** 主键 */
    private Integer id;
    /** 会员id */
    private Integer memberId;
    /** 预约日期 */
    private Date orderDate;
    /** 预约类型 电话预约/微信预约 */
    private String orderType;
    /** 预约状态（是否到诊） */
    private String orderStatus;
    /** 体检套餐id */
    private Integer setmealId;
}