package com.example.scheduler.processor.service.trigger.schedule;

import com.example.scheduler.processor.conzt.ThreadConfiguration;
import com.example.scheduler.processor.dt.ScheduledTriggerDetailsDT;
import com.example.scheduler.processor.dt.ScheduledTriggerJobsDT;
import com.example.scheduler.processor.repository.ScheduledTriggerDetailsRepository;
import com.example.scheduler.processor.repository.ScheduledTriggerJobsRepository;
import com.example.scheduler.processor.repository.impl.ScheduledTriggerDetailsRepositoryImpl;
import com.example.scheduler.processor.service.trigger.ischedule.IScheduler;
import com.example.scheduler.processor.utils.CronUtils;
import com.example.scheduler.processor.utils.DateUtils;
import com.example.scheduler.processor.utils.ThreadCountProcessorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class ScheduleTriggerService implements IScheduler {

    private static final long FIXED_RATE = 10000;

    private ScheduledTriggerDetailsRepository scheduledTriggerDetailsRepository;

    private ScheduledTriggerDetailsRepositoryImpl scheduledTriggerDetailsRepositoryImpl;

    private ScheduledTriggerJobsRepository scheduledTriggerJobsRepository;

    @Autowired
    @Qualifier(ThreadConfiguration.SCHEDULE_TRIGGER_BEAN)
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private ThreadCountProcessorUtils threadCountProcessorUtils;

    private static final List<ScheduledTriggerDetailsDT.Status> DEFAULT_QUERY_ELEMENTS = Arrays.asList(ScheduledTriggerDetailsDT.Status.NOT_PROVISIONED,ScheduledTriggerDetailsDT.Status.DELETE);

    @Autowired
    public ScheduleTriggerService(ScheduledTriggerDetailsRepositoryImpl scheduledTriggerDetailsRepositoryImpl, ScheduledTriggerDetailsRepository scheduledTriggerDetailsRepository, ScheduledTriggerJobsRepository scheduledTriggerJobsRepository) {
        this.scheduledTriggerDetailsRepository = scheduledTriggerDetailsRepository;
        this.scheduledTriggerDetailsRepositoryImpl = scheduledTriggerDetailsRepositoryImpl;
        this.scheduledTriggerJobsRepository = scheduledTriggerJobsRepository;
    }
    
    @Override
    @Scheduled(fixedDelay = FIXED_RATE)
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    public void process() {
        System.out.println("Checking for scheduled jobs");
        int limit = getLimitByThreadCount();
        Iterable<ScheduledTriggerDetailsDT> notProvisionedJobs = scheduledTriggerDetailsRepositoryImpl.getTriggerDetailsToBeScheduled(limit, DEFAULT_QUERY_ELEMENTS);
        notProvisionedJobs.forEach(scheduledTriggerDetails -> threadPoolExecutor.execute(processScheduledTrigger(scheduledTriggerDetails)));
        System.out.println("Processing completed");
    }

    public int getLimitByThreadCount() {
        return  threadCountProcessorUtils.getFreeThreadCount(threadPoolExecutor.getActiveCount(), threadPoolExecutor.getPoolSize(), threadPoolExecutor.getMaximumPoolSize());
    }

    private Runnable processScheduledTrigger (ScheduledTriggerDetailsDT scheduledTriggerDetails) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    if (scheduledTriggerDetails.getStatus() == ScheduledTriggerDetailsDT.Status.NOT_PROVISIONED) {
                        scheduledTriggerDetails.setStatus(ScheduledTriggerDetailsDT.Status.PROVISIONED);
                        createTriggerJob(scheduledTriggerDetails);
                    } else if (scheduledTriggerDetails.getStatus() == ScheduledTriggerDetailsDT.Status.DELETE) {
                        scheduledTriggerDetails.setStatus(ScheduledTriggerDetailsDT.Status.DELETED);
                        scheduledTriggerJobsRepository.delete(scheduledTriggerDetails.getScheduledTriggerJobs());
                        scheduledTriggerDetailsRepository.save(scheduledTriggerDetails);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    scheduledTriggerDetails.setStatus(ScheduledTriggerDetailsDT.Status.ERROR);
                    scheduledTriggerDetailsRepository.save(scheduledTriggerDetails);
                }
            }
        };
    }

    private void createTriggerJob(ScheduledTriggerDetailsDT scheduledTriggerDetails) {
        ScheduledTriggerJobsDT scheduledTriggerJobsDT = new ScheduledTriggerJobsDT();
        scheduledTriggerJobsDT.setCreatedTS(DateUtils.getUTCDate());
        scheduledTriggerJobsDT.setLastUpdatedTS(DateUtils.getUTCDate());
        scheduledTriggerJobsDT.setNextRun(CronUtils.getNextRun(scheduledTriggerDetails.getExpression()));
        scheduledTriggerJobsDT.setPriority(scheduledTriggerDetails.getPriority());
        scheduledTriggerJobsDT.setScheduledTriggerDetails(scheduledTriggerDetails);
        scheduledTriggerJobsRepository.save(scheduledTriggerJobsDT);
    }
}
