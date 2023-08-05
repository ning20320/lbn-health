package lbn;

import lbn.entity.Setmeal;
import lbn.pojo.PageSelect;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SetmealService {

    void save(Setmeal setmeal,Integer[] checkgroupIds);

    PageSelect<Setmeal> pageQuery(Integer currentPage,Integer pageSize,String queryString);

    Setmeal getById(Integer id);
    List<Integer> listCheckGroupIdsBySetmealId(Integer id);
    void update(Setmeal setmeal,Integer[] checkGroupIds);

    Integer deleteSetmeal(Integer id);


}
