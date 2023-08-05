package lbn.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lbn.SetmealService;
import lbn.constant.RedisConstant;
import lbn.entity.Setmeal;
import lbn.mapper.SetmealMapper;
import lbn.pojo.PageSelect;
import lbn.util.QiniuUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Set;


@DubboService
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void save(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealMapper.insert(setmeal);
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealMapper.insertSetmealAndCheckGroup(setmeal.getId(), checkgroupId);
            }
        }
        //把保存的图片名称保存到Redis集合中
        redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_RESOURCES_DB,setmeal.getImg());
        //同时，把临时上传的图片名称进行删除
        //clearSetmealImg();    //移动到任务调度中去执行

    }

    private void clearSetmealImg() {
        //得到临时保存的图片名称集合
       /* Set<String> difference = redisTemplate.opsForSet().difference(RedisConstant.SETMEAL_PIC_RESOURCES,RedisConstant.SETMEAL_PIC_RESOURCES_DB);
        System.out.println(difference);
        if (difference != null){
            difference.forEach(fileName ->{
                //从七牛云删除
                QiniuUtils.deleteFile(fileName);
                //从Redis集合中删除
                redisTemplate.opsForSet().remove(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            });
        }*/
    }

    private void saveSetmealAndCheckGroup(Integer setmealId, Integer[] checkGroupIds) {
        if (checkGroupIds != null) {
            for (Integer checkGroupId : checkGroupIds) {
                setmealMapper.insertSetmealAndCheckGroup(setmealId, checkGroupId);
            }
        }
    }

    @Override
    public PageSelect<Setmeal> pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .like(null != queryString, Setmeal::getCode, queryString)
                .or()
                .like(null != queryString, Setmeal::getName, queryString)
                .or()
                .like(null != queryString, Setmeal::getHelpCode, queryString);

        Page<Setmeal> page = new Page<>(currentPage, pageSize);
        PageSelect<Setmeal> pageSelect = new PageSelect<>();
        setmealMapper.selectPage(page, lambdaQueryWrapper);
        pageSelect.setRows(page.getRecords());
        pageSelect.setTotal(page.getTotal());
        return pageSelect;
    }

    @Override
    public Setmeal getById(Integer id) {
        return setmealMapper.selectById(id);
    }

    @Override
    public List<Integer> listCheckGroupIdsBySetmealId(Integer id) {
        return setmealMapper.listCheckGroupIdsBySetmealId(id);
    }

    @Override
    public void update(Setmeal setmeal, Integer[] checkGroupIds) {
        setmealMapper.deleteSetmealAndCheckGroup(setmeal.getId());
        saveSetmealAndCheckGroup(setmeal.getId(),checkGroupIds);
        setmealMapper.updateById(setmeal);
    }

    @Override
    public Integer deleteSetmeal(Integer id) {
        Integer result = setmealMapper.deleteById(id);
        result = setmealMapper.deleteSetmealAndCheckGroup(id);
        return result;
    }


}


