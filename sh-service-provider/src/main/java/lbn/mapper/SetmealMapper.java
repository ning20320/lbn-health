package lbn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lbn.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {

    void insertSetmealAndCheckGroup(@Param("setmealId") Integer setmealId,@Param("checkgroupId") Integer checkgroupId);

    Integer deleteSetmealAndCheckGroup(@Param("setmealid") Integer setmealId);

    List<Integer> listCheckGroupIdsBySetmealId(Integer id);


}
