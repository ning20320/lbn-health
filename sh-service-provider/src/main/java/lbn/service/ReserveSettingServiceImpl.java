package lbn.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lbn.ReserveSettingService;
import lbn.entity.ReserveSetting;
import lbn.mapper.ReserveSettingMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DubboService
public class ReserveSettingServiceImpl implements ReserveSettingService {

    @Autowired
    private ReserveSettingMapper reserveSettingMapper;

    @Override
    public void save(List<ReserveSetting> reserveSettings) {
        if (reserveSettings != null && reserveSettings.size() > 0){
            for (ReserveSetting reserveSetting : reserveSettings) {
                //判断当前日期是否已经有预约数了
                QueryWrapper<ReserveSetting> queryWrapper = new QueryWrapper<ReserveSetting>().eq("order_date", reserveSetting.getOrderDate());
                Long orderDateCount = reserveSettingMapper.selectCount(queryWrapper);
                if (orderDateCount > 0){
                    //如果有预约数了，就修改预约数
                    reserveSettingMapper.updateById(reserveSetting);
                }else {
                    //如果没有预约数，就添加预约数
                    reserveSettingMapper.insert(reserveSetting);
                }
            }
        }
    }

    //根据月份查询对应的预约设置数据
    @Override
    public List<ReserveSetting> listReserveSettingByMonth(String date) {
        //获取当前月的第一天
        String begin = date + "-01";
        //获取当前月的最后一天
        String end = date + "-31";
        QueryWrapper<ReserveSetting> queryWrapper = new QueryWrapper<ReserveSetting>().between("order_date", begin, end);
        List<ReserveSetting> orderDate = reserveSettingMapper.selectList(queryWrapper);
        return orderDate;
    }

    //根据日期设置对应的预约设置数据
    @Override
    public void editNumberByDate(ReserveSetting reserveSetting) {
        //判断当前日期是否已经有预约数了
        QueryWrapper<ReserveSetting> queryWrapper = new QueryWrapper<ReserveSetting>().eq("order_date", reserveSetting.getOrderDate());
        Long orderDateCount = reserveSettingMapper.selectCount(queryWrapper);
        if (orderDateCount > 0){
            //如果有预约数了，就修改预约数
            reserveSettingMapper.updateById(reserveSetting);
        }else {
            //如果没有预约数，就添加预约数
            reserveSettingMapper.insert(reserveSetting);
        }
    }
}
