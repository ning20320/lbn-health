package lbn.controller;

import lbn.CheckItemService;
import lbn.constant.MessageConstant;
import lbn.entity.CheckItem;
import lbn.pojo.PageSelect;
import lbn.pojo.QueryBean;
import lbn.util.R;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @DubboReference
    private CheckItemService checkItemService;

    @PostMapping("/add")
    public R add(@RequestBody CheckItem checkItem){
        try {
            checkItemService.save(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new R(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new R(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @GetMapping("/findAll")
    public R findAll(){
        List<CheckItem> checkItemList = checkItemService.list();
        if (checkItemList != null && checkItemList.size() > 0){
            return new R(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemList);
        }
        return new R(false,MessageConstant.QUERY_CHECKITEM_FAIL);
    }

    @PostMapping("/findPage")
    public PageSelect<CheckItem> findPage(@RequestBody QueryBean queryBean){
        PageSelect<CheckItem> pageSelect =
                checkItemService.pageQuery(
                        queryBean.getCurrentPage(),
                        queryBean.getPageSize(),
                        queryBean.getQueryString());
        return pageSelect;
    }

    @DeleteMapping ("/delete")
    public R delete(@RequestParam("id") Integer id){
        try {
            checkItemService.deleteCheckItem(id);
            return new R(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new R(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    @GetMapping("findById.do")
    public R find(@RequestParam("id") Integer id){
        try {
            CheckItem checkItemById = checkItemService.findCheckItemById(id);
            return new R(true,MessageConstant.GET_CHECKITEM_DETAIL_SUCCESS,checkItemById);
        } catch (Exception e) {
            e.printStackTrace();
            return new R(false,MessageConstant.GET_CHECKITEM_DETAIL_FAIL);
        }
    }

    @PostMapping("edit.do")
    public R edit(@RequestBody CheckItem checkItem){
        try {
            checkItemService.editCheckItem(checkItem);
            return new R(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new R(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }
}
