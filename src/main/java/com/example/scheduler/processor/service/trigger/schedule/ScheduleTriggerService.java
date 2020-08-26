package com.example.scheduler.processor.service.trigger.schedule;

import com.example.scheduler.processor.dt.ScheduledTriggerDetailsDT;
import com.example.scheduler.processor.dt.ScheduledTriggerJobsDT;
import com.example.scheduler.processor.repository.ScheduledTriggerDetailsRepository;
import com.example.scheduler.processor.repository.ScheduledTriggerJobsRepository;
import com.example.scheduler.processor.repository.impl.ScheduledTriggerDetailsRepositoryImpl;
import com.example.scheduler.processor.service.trigger.ischedule.IScheduler;
import com.example.scheduler.processor.utils.CronUtils;
import com.example.scheduler.processor.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Iterator;

@Service
public class ScheduleTriggerService implements IScheduler {

    private static final long FIXED_RATE = 300000;

    private ScheduledTriggerDetailsRepository scheduledTriggerDetailsRepository;

    private ScheduledTriggerDetailsRepositoryImpl scheduledTriggerDetailsRepositoryImpl;

    private ScheduledTriggerJobsRepository scheduledTriggerJobsRepository;

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
        Iterable<ScheduledTriggerDetailsDT> notProvisionedJobs = scheduledTriggerDetailsRepository.findByStatus(ScheduledTriggerDetailsDT.Status.NOT_PROVISIONED);
        notProvisionedJobs.forEach(scheduledTriggerDetails -> processScheduledTrigger(scheduledTriggerDetails));
        System.out.println("Processing completed");
    }

    private void processScheduledTrigger (ScheduledTriggerDetailsDT scheduledTriggerDetails) {
        try {
            scheduledTriggerDetails.setStatus(ScheduledTriggerDetailsDT.Status.PROVISIONED);
            createTriggerJob(scheduledTriggerDetails);
        } catch (Exception ex) {
            ex.printStackTrace();
            scheduledTriggerDetails.setStatus(ScheduledTriggerDetailsDT.Status.ERROR);
            scheduledTriggerDetailsRepository.save(scheduledTriggerDetails);
        }
    }

    private void createTriggerJob(ScheduledTriggerDetailsDT scheduledTriggerDetails) {
        ScheduledTriggerJobsDT scheduledTriggerJobsDT = new ScheduledTriggerJobsDT();
        scheduledTriggerJobsDT.setCreatedTS(DateUtils.getUTCDate());
        scheduledTriggerJobsDT.setLastUpdatedTS(DateUtils.getUTCDate());
        scheduledTriggerJobsDT.setNextRun(CronUtils.getNextRun(scheduledTriggerDetails.getExpression()));
        scheduledTriggerJobsDT.setScheduledTriggerDetails(scheduledTriggerDetails);
        scheduledTriggerJobsRepository.save(scheduledTriggerJobsDT);
    }
}
