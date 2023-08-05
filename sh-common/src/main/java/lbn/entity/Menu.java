package lbn.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Menu implements Serializable {

    /** 主键 */
    private Integer id;
    /** 菜单名称 */
    private String name;
    /** 访问路径 */
    private String linkUrl;
    /** 菜单项所对应的路由路径 */
    private String path;
    /** 优先级（用于排序） */
    private Integer priority;
    /** 描述 */
    private String description;
    /** 图标 */
    private String icon;
    /** 角色集合 */
    private Set<Role> roles = new HashSet<Role>(0);
    /** 子菜单集合 */
    private List<Menu> children = new ArrayList<>();
    /** 父菜单id */
    private Integer parentMenuId;
}
