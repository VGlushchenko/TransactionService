package com.mypal.service.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.CalendarIntervalScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.DateBuilder.*;

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
                                .withIntervalInSeconds(20)
                                .repeatForever())
                .build();

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }
}
