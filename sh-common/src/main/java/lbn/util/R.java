package lbn.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class R implements Serializable {

    /** 执行结果，true：成功，false：失败 */
    private boolean flag;

    /** 执行失败信息 */
    private String message;

    /** 响应数据 */
    private Object data;

    public R(boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    public R(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }
}