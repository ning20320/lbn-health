package lbn;

import lbn.entity.CheckGroup;
import lbn.entity.CheckItem;
import lbn.pojo.PageSelect;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CheckGroupService {
    void save(CheckGroup checkGroup,Integer[] checkitemIds);

    List<CheckGroup> list();

    PageSelect pageQuery(Integer currentPage,Integer pageSize,String queryString);


    CheckGroup getById(Integer id);

    List<Integer> listCheckItemIdsByCheckGroupId(Integer id);

    void updateCheckGroupByCheckItemIds(CheckGroup checkGroup,Integer[] checkItemIds);

    Integer deleteCheckGroup(Integer id);
}
