package com.example.controlleradvice.config;

import com.example.controlleradvice.service.UploadTask;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartConfig {
    /**
     * uploadTaskDetail 指定具体需要执行的类，只不过具体的方法就是我们需要实现的excuteInternal
     * @return
     */
    @Bean
    public JobDetail uploadTaskDetail(){
        return JobBuilder.newJob(UploadTask.class).withIdentity("uploadTask").storeDurably().build();
    }

    /**
     * uploadTaskTrigger指定了触发的规则
     * @return
     */
    @Bean
    public Trigger uploadTaskTrigger(){
        CronScheduleBuilder scheduleBuilder=CronScheduleBuilder.cronSchedule("*/5 * * * * ?");

        return TriggerBuilder.newTrigger().forJob(uploadTaskDetail())
                .withIdentity("uploadTask").withSchedule(scheduleBuilder).build();
    }

}
