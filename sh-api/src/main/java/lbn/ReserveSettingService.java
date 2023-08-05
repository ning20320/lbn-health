package lbn;

import lbn.entity.ReserveSetting;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReserveSettingService {

    /**
     * 新增预约设置
     */

    void save(List<ReserveSetting> reserveSettings);

    /**
     * 按月份获取预约信息
     */
    List<ReserveSetting> listReserveSettingByMonth(String date);

    public void editNumberByDate(ReserveSetting reserveSetting);
}
