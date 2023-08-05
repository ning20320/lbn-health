package lbn.config;


import lbn.job.ClearSetmealPicJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduleConfig {
    public static class JobSchedule{
        @Bean
        public JobDetail clearSetmealPicJobDetail(){
            return JobBuilder.newJob(ClearSetmealPicJob.class)
                    .withIdentity("clearSetmealPicJob")
                    .storeDurably(true)
                    .build();
        }

        @Bean
        public Trigger clearSetmealPicJobTrigger(){
            SimpleScheduleBuilder simpleScheduleBuilder =
                    SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(30)
                            .repeatForever();
            return TriggerBuilder.newTrigger()
                    .forJob(clearSetmealPicJobDetail())
                    .withIdentity("clearSetmealPicJobTrigger")
                    .withSchedule(simpleScheduleBuilder)
                    .build();
        }
    }
}