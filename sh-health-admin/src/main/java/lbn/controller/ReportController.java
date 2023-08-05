package lbn.controller;

import lbn.MemberService;
import lbn.constant.MessageConstant;
import lbn.util.R;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/reportmember")
public class ReportController {

    @DubboReference
    private MemberService memberService;

    @RequestMapping("/getMemberReport")
    public R getMemberReport(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);//获得当前日期之前12个月的日期

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            // 使用set方法设置日期为每月的第一天，然后再增加i个月
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.MONTH, i);
            list.add(new SimpleDateFormat("yyyy.MM").format(calendar.getTime()));
        }

        Map<String,Object> map = new HashMap<>();
        map.put("months",list);

        List<Integer> memberCount = memberService.findMemberCountByMonth(list);
        map.put("memberCount",memberCount);

        return new R(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }
}

