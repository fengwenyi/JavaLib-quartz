package com.fengwenyi.javalib.quartz;

import lombok.Data;
import org.quartz.Scheduler;

/**
 * ScheduleBean
 * @author Wenyi Feng
 */
@Data
public class ScheduleBean {

    /** quartz scheduler obj */
    private Scheduler scheduler;

    /** 编号 */
    private Long id;

    /** task name */
    private String name;

    /** task description */
    private String description;

    /** time type */
    private TimeTypeEnum timeType;

    /** 类clazz */
    private Class clazz;

    /** cron */
    private String cron;

    /** time, simple */
    private Integer time;

    /** 时间点 */
    private Long atTime;

    /** job name */
    private String jobName;

    /** job group name */
    private String jobGroup;

    /** trigger name */
    private String triggerName;

    /** trigger group name */
    private String triggerGroup;

}
