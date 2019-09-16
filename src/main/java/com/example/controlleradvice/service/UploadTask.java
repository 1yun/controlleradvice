package com.example.controlleradvice.service;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * quartz中有个concurrent 是否并发执行 默认为true 即第一个任务还未执行完整，第二个任务如果到了执行时间则会立马开始新线程执行 任务，
 * 如果我们是从数据库读取信息，两次重复读取可能重复执行任务的情况 所以需要设置为false  第一个任务执行完成后才会执行第二个任务
 */
@DisallowConcurrentExecution
public class UploadTask extends QuartzJobBean {
    /**
     * 实现excuteInternal方法，方法中写自己需要定时执行的
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("任务开始");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("任务结束");

    }
}
