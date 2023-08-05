package lbn.controller;

import lbn.ReserveSettingService;
import lbn.constant.MessageConstant;
import lbn.entity.ReserveSetting;
import lbn.util.PoiUtils;
import lbn.util.R;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ordersetting")
public class ReserveSettingController {

    @DubboReference
    ReserveSettingService reserveSettingService;

    @RequestMapping("/upload")
    public R upload(@RequestParam("excelFile")MultipartFile excelFile){
        try {
            //使用POI解析上传的文件数据
            List<String[]> list = PoiUtils.readExcel(excelFile);
            ArrayList<ReserveSetting> reserveSettings = new ArrayList<>();
            //遍历解析后的数据
            for (String[] strings : list) {
                //日期
                String orderDate = strings[0];
                //最大预约数
                String number = strings[1];
                ReserveSetting reserveSetting = new ReserveSetting();
                reserveSetting.setOrderDate(new Date(orderDate));
                reserveSetting.setNumber(Integer.parseInt(number));
                reserveSettings.add(reserveSetting);
            }
            //调用Dubbo远程服务接口保存数据
            reserveSettingService.save(reserveSettings);
        } catch (Exception e) {
            e.printStackTrace();
            return new R(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new R(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    @RequestMapping("/getOrderSettingByMonth")
    public R getOrderSettingByMonth(String date){
        try {
            List<ReserveSetting> orderSettings = reserveSettingService.listReserveSettingByMonth(date);
            return new R(true,MessageConstant.GET_ORDERSETTING_SUCCESS,orderSettings);
        } catch (Exception e) {
            e.printStackTrace();
            return new R(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    //根据日期设置对应的预约设置数据
    @RequestMapping("/editNumberByDate")
    public R editNumberByDate(@RequestBody ReserveSetting reserveSetting){
        try {
            reserveSettingService.editNumberByDate(reserveSetting);
            return new R(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new R(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }

}
