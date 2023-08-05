package lbn.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lbn.CheckGroupService;
import lbn.entity.CheckGroup;
import lbn.mapper.CheckGroupMapper;
import lbn.pojo.PageSelect;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@DubboService
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupMapper checkGroupMapper;

    @Override
    public void save(CheckGroup checkGroup,Integer[] checkItemIds) {
        checkGroupMapper.insert(checkGroup);
        saveCheckGroupAndCheckItem(checkGroup.getId(),checkItemIds);
    }

    private void saveCheckGroupAndCheckItem(Integer checkGroupId,Integer[] checkItemIds) {
        if (checkItemIds != null){
            for (Integer checkItemId : checkItemIds) {
                checkGroupMapper.insertCheckGroupAndCheckItem(checkGroupId,checkItemId);
            }
        }
    }

    @Override
    public List<CheckGroup> list() {
        return checkGroupMapper.selectList(null);
    }

    @Override
    public PageSelect pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        LambdaQueryWrapper<CheckGroup> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .like(null != queryString,CheckGroup::getCode,queryString)
                .or()
                .like(null != queryString,CheckGroup::getName,queryString)
                .or()
                .like(null != queryString,CheckGroup::getHelpCode,queryString);
        Page<CheckGroup> page = new Page<>(currentPage,pageSize);
        PageSelect<CheckGroup> pageSelect = new PageSelect<>();
        checkGroupMapper.selectPage(page,lambdaQueryWrapper);
        pageSelect.setRows(page.getRecords());
        pageSelect.setTotal(page.getTotal());
        return pageSelect;
    }

    @Override
    public CheckGroup getById(Integer id) {
        return checkGroupMapper.selectById(id);
    }

    @Override
    public List<Integer> listCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupMapper.listCheckItemIdsByCheckGroupId(id);
    }

    @Override
    public void updateCheckGroupByCheckItemIds(CheckGroup checkGroup, Integer[] checkItemIds) {
        //删除原来有的检查组下的检查项的关联
        checkGroupMapper.deleteCheckGroupAndCheckItem(checkGroup.getId());
        //再建立编辑的新的检查组和检查项的关联关系
        saveCheckGroupAndCheckItem(checkGroup.getId(), checkItemIds);
        //更新检查组信息
        checkGroupMapper.updateById(checkGroup);
    }

   @Override
   @Transactional
   public Integer deleteCheckGroup(Integer id) {
       try {
           // 先从t_checkgroup_checkitem表中删除相关的检查项
           checkGroupMapper.deleteCheckGroupAndCheckItem(id);

           // 然后，从t_check_group表中删除检查组
           return checkGroupMapper.deleteById(id);
       } catch (Exception e) {
           // 处理删除过程中可能发生的任何异常
           e.printStackTrace();
           return 0; // 返回0或特定错误代码以指示失败
       }
   }
}
