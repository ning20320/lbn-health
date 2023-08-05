package lbn.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import lbn.CheckGroupService;
import lbn.constant.MessageConstant;
import lbn.entity.CheckGroup;
import lbn.entity.CheckItem;
import lbn.pojo.PageSelect;
import lbn.pojo.QueryBean;
import lbn.util.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
@Slf4j
public class CheckGroupController {

    @DubboReference
    private CheckGroupService checkGroupService;

    @PostMapping ("/add")
    public R add(@RequestBody CheckGroup checkGroup, @RequestParam("checkItemIds") Integer[] checkItemIds) {
        try{
            checkGroupService.save(checkGroup,checkItemIds);
            return new R(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            log.error(e.getMessage());
            return new R(false,MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    @GetMapping("/findAll")
    public R findAll(){
        List<CheckGroup> checkGroupList = checkGroupService.list();
        if (checkGroupList != null && checkGroupList.size() > 0){
            return new R(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroupList);
        }
        return new R(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }

    @PostMapping("/findPage")
    public PageSelect<CheckGroup> findPage(@RequestBody QueryBean queryBean){
        PageSelect<CheckGroup> pageSelect =
                checkGroupService.pageQuery(
                        queryBean.getCurrentPage(),
                        queryBean.getPageSize(),
                        queryBean.getQueryString());
        return pageSelect;
    }

    @GetMapping("/findById")
    public R findById(Integer id){
        CheckGroup checkGroup = checkGroupService.getById(id);
        if (!ObjectUtils.isEmpty(checkGroup)){
            return new R(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        }
        return new R(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }

    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public R findCheckItemIdsByCheckGroupId(Integer id){
        try {
            List<Integer> checkItemIds = checkGroupService.listCheckItemIdsByCheckGroupId(id);
            return new R(true,"查询关联的检查项成功",checkItemIds);
        } catch (Exception e) {
            return new R(false,"查询关联的检查项失败");
        }

    }

    @RequestMapping("/edit")
    public R edit(@RequestBody CheckGroup checkGroup,Integer[] checkItemIds){
        try {
            checkGroupService.updateCheckGroupByCheckItemIds(checkGroup,checkItemIds);
        } catch (Exception e) {
            return new R(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return new R(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    @GetMapping("/delete")
    public R deleteGroup(@RequestParam("id") Integer id){
        try{
            Integer result = checkGroupService.deleteCheckGroup(id);
            return new R(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new R(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }
}
