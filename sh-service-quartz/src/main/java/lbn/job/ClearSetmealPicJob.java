package lbn.job;

import lbn.constant.RedisConstant;
import lbn.util.QiniuUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Set;

@Slf4j
public class ClearSetmealPicJob extends QuartzJobBean {

    @Autowired
    RedisTemplate redisTemplate;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        clearSetmealPic();
    }

    private void clearSetmealPic() {
        log.info("QUARTZ: <ClearSetmealPicJob> [开始执行清理套餐图片任务]");
        Set<String> difference = redisTemplate.opsForSet().difference(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_RESOURCES_DB);
        System.out.println(difference);
        if(difference != null){
            difference.forEach(filename -> {
                QiniuUtils.deleteFile(filename);
                redisTemplate.opsForSet().remove(RedisConstant.SETMEAL_PIC_RESOURCES, filename);
                log.info("QUARTZ: <ClearSetmealPicJob> [执行清理套餐图片] {}", filename);
            });
        }
        log.info("QUARTZ: <ClearSetmealPicJob> [执行清理套餐图片任务完成]");
    }
}