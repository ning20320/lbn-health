package lbn;

import lbn.entity.CheckItem;
import lbn.pojo.PageSelect;

import java.util.List;

public interface CheckItemService {

    //添加检查项
    void save(CheckItem checkItem);

    //查找所有检查项
    List<CheckItem> list();

    //分页查询
    PageSelect<CheckItem> pageQuery(Integer currentPage, Integer pageSize, String queryString);

    //删除
    Integer deleteCheckItem(Integer id);

    //编辑
    CheckItem findCheckItemById(Integer id);
    Integer editCheckItem(CheckItem checkItem);
}
