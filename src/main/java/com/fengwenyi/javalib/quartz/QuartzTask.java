package com.fengwenyi.javalib.quartz;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import java.util.Map;
import java.util.Set;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author Wenyi Feng
 */
public class QuartzTask {

    private Scheduler scheduler;

    // 开启
    public boolean start(ScheduleBean scheduleBean, Map<String, Object> paramMap) throws SchedulerException {
        if (!status()) {
            // define the job and tie it to our MyJob class
            JobDetail jobDetail = newJob(scheduleBean.getClazz())
                    .withIdentity(scheduleBean.getJobName(), scheduleBean.getJobGroup())
                    .build();

            // 参数
            if (paramMap != null && !paramMap.isEmpty()) {
                Set<String> keys = paramMap.keySet();
                for (String key : keys) {
                    jobDetail.getJobDataMap().put(key, paramMap.get(key));
                }
            }

            // Trigger the job to run now, and then repeat every 40 seconds
            Trigger trigger = newTrigger()
                    .withIdentity(scheduleBean.getTriggerName(), scheduleBean.getTriggerGroup())
                    .startNow()

                    .withSchedule(simpleSchedule()
                            // simple
                    .withIntervalInSeconds(scheduleBean.getTime())
                            // cron

                    .repeatForever())
                    .build();

            if (scheduler == null)
                scheduler = scheduleBean.getScheduler();

            scheduler.scheduleJob(jobDetail, trigger);
            return true;
        }
        return false;
    }

    public boolean start(ScheduleBean scheduleBean) throws SchedulerException {
        return start(scheduleBean, null);
    }

    // 状态
    public boolean status() throws SchedulerException {
        if (scheduler != null) {
            return scheduler.isStarted();
        }
        return false;
    }

    // 停止
    public boolean stop() throws SchedulerException {
        if (scheduler != null) {
            scheduler.shutdown();
            return true;
        }
        return false;
    }
}
