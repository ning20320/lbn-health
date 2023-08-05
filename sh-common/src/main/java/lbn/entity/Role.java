package lbn.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class Role implements Serializable {

    /** 主键 */
    private Integer id;
    /** 角色名称 */
    private String name;
    /** 角色关键字，用于权限控制 */
    private String keyword;
    /** 角色描述 */
    private String description;

    /**
     * 新增
     */

    /**用户集合*/
    private Set<User> users = new HashSet<>(0);
    /**权限集合*/
    private Set<Permission> permissions = new HashSet<>(0);
    /**菜单集合*/
    private LinkedHashSet<Menu> menus = new LinkedHashSet<>(0);

}