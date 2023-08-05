package lbn.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lbn.CheckItemService;
import lbn.entity.CheckItem;
import lbn.mapper.CheckItemMapper;
import lbn.pojo.PageSelect;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DubboService
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemMapper checkItemMapper;
    @Override
    public void save(CheckItem checkItem) {
        checkItemMapper.insert(checkItem);
    }

    @Override
    public List<CheckItem> list() {
        return checkItemMapper.selectList(null);
    }


    @Override
    public PageSelect<CheckItem> pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        LambdaQueryWrapper<CheckItem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .like(null != queryString,CheckItem::getCode,queryString)
                .or()
                .like(null != queryString,CheckItem::getName,queryString);
        //1.通过分页参数创建分页对象
        Page<CheckItem> page = new Page<>(currentPage, pageSize);
        PageSelect<CheckItem> pageSelect = new PageSelect<>();
        //2.执行分页查询
        checkItemMapper.selectPage(page,lambdaQueryWrapper);
        pageSelect.setRows(page.getRecords());
        pageSelect.setTotal(page.getTotal());
        //3.返回分页结果
        return pageSelect;
    }

    @Override
    public Integer deleteCheckItem(Integer id) {
        CheckItem checkItem = checkItemMapper.selectById(id);
        Integer deleteById = checkItemMapper.deleteById(checkItem);
        return deleteById;
    }

    @Override
    public CheckItem findCheckItemById(Integer id) {
        CheckItem selectById = checkItemMapper.selectById(id);
        return selectById;
    }

    @Override
    public Integer editCheckItem(CheckItem checkItem) {
        Integer updateById = checkItemMapper.updateById(checkItem);
        return updateById;
    }
}
