package lbn.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
public class Permission implements Serializable {

    /** 主键 */
    private Integer id;
    /** 权限名称 */
    private String name;
    /** 权限关键字，用于权限控制 */
    private String keyword;
    /** 描述 */
    private String description;
    /** 角色集合 */
    private Set<Role> roles = new HashSet<Role>(0);
}