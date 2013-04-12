package com.mypal.service.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

@Service
public class QuartzTrigger {

    public void startJob(int minutes) throws Exception {

        System.out.println("StartJob");

        JobDetail job = JobBuilder.newJob(RollbackJob.class)
                .withIdentity("rollbackJob", "group1").build();

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("quartzTrigger", "group1")
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(5)
                                .repeatForever())
                .build();

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }
}
