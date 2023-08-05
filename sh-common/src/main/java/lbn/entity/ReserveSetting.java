package lbn.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ReserveSetting implements Serializable {

    /** 主键 */
    private Integer id;
    /** 预约设置日期 */
    private Date orderDate;
    /** 可预约人数 */
    private int number;
    /** 已预约人数 */
    private int reservations;

}
