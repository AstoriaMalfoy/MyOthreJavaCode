package 延时任务的几种实现逻辑;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author litao34
 * @ClassName QuarJob
 * @Description TODO
 * @CreateDate 2022/1/7-11:27 AM
 **/
public class QuarJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("定时执行任务");
    }

    public static void main(String[] args) throws SchedulerException {
        // 创建任务
        JobDetail jobDetail = JobBuilder.newJob(QuarJob.class).withIdentity("job1","group1").build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1","group3").withSchedule(
                SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).repeatForever()
        ).build();
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();
    }
}
