package lbn.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import lbn.SetmealService;
import lbn.constant.MessageConstant;
import lbn.constant.RedisConstant;
import lbn.entity.Setmeal;
import lbn.pojo.PageSelect;
import lbn.pojo.QueryBean;
import lbn.util.QiniuUtils;
import lbn.util.R;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @DubboReference
    private SetmealService setmealService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 上传图片
     * @param imgFile
     * @return
     */
    @RequestMapping("/upload")
    public R upload(@RequestParam("imgFile")MultipartFile imgFile){
        try {
            //获取原始文件名
            String originalFilename = imgFile.getOriginalFilename();
            //截取文件后缀名
            assert originalFilename != null;
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            //生成唯一文件名
            String fileName = UUID.randomUUID() + suffix;
            //调用七牛云上传文件
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //封装返回的结果
            R r = new R(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
            //封装图片名称，返回给前端
            r.setData(fileName);
            //return new R(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
            redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return r;
        } catch (IOException e) {
            return new R(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @PostMapping("/add")
    public R add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            setmealService.save(setmeal,checkgroupIds);
            return new R(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            return new R(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @PostMapping("/findPage")
    public PageSelect<Setmeal> findPage(@RequestBody QueryBean queryBean){
        PageSelect<Setmeal> pageSelect =
                setmealService.pageQuery(
                        queryBean.getCurrentPage(),
                        queryBean.getPageSize(),
                        queryBean.getQueryString());
        return pageSelect;
    }

    @GetMapping("/findMealById")
    public R findById(Integer id){
        Setmeal setmeal = setmealService.getById(id);
        if (!ObjectUtils.isEmpty(setmeal)){
            return new R(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }
        return new R(false,MessageConstant.QUERY_SETMEAL_FAIL);
    }

    @RequestMapping("/findCheckgroupIdsByMealId")
    public R findCheckGroupIdsBySetmealId(Integer id){
        try {
            List<Integer> checkGroupIds = setmealService.listCheckGroupIdsBySetmealId(id);
            return new R(true,"查询关联的检查组成功",checkGroupIds);
        } catch (Exception e) {
            return new R(false,"查询关联的检查组失败");
        }

    }

    @RequestMapping ("/edit")
    public R edit(@RequestBody Setmeal setmeal,Integer[] checkGroupIds){
        try {
            setmealService.update(setmeal,checkGroupIds);
        } catch (Exception e) {
            return new R(false,"编辑套餐失败");
        }
        return new R(true,"编辑套餐成功");
    }

    @GetMapping("/delete")
    public R deleteSetmeal(@RequestParam("id") Integer id){
        try {
            Integer result = setmealService.deleteSetmeal(id);
            return new R(true,"删除套餐成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new R(false,"删除套餐失败");
        }
    }
}
