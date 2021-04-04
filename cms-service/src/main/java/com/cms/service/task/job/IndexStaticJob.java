package com.cms.service.task.job;

import com.cms.service.api.CmsStaticPageService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
public class IndexStaticJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext){
      log.info("执行首页静态化");
        Scheduler scheduler = jobExecutionContext.getScheduler();
        try {
            SchedulerContext context = scheduler.getContext();
            ApplicationContext applicationContext = (ApplicationContext) context.get("applicationContext");
            CmsStaticPageService cmsStaticPageService = applicationContext.getBean(CmsStaticPageService.class);
            cmsStaticPageService.staticIndex();
        } catch (SchedulerException e) {
            log.error("执行首页静态化定时任务失败,信息=[{}]",e.getMessage());
        }
    }
}
