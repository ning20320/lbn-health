package lbn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lbn.CheckGroupService;
import lbn.entity.CheckGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CheckGroupMapper extends BaseMapper<CheckGroup> {

    void insertCheckGroupAndCheckItem(@Param("checkGroupId")Integer checkGroupId,@Param("checkItemId")Integer checkItemId);

    Integer deleteCheckGroupAndCheckItem(@Param("checkgroupid") Integer checkGroupId);

    List<Integer> listCheckItemIdsByCheckGroupId(Integer id);
}
