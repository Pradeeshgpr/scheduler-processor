package com.example.scheduler.processor.service.trigger.schedule;

import com.example.scheduler.processor.repository.ScheduledTriggerJobsRepository;
import com.example.scheduler.processor.service.trigger.ischedule.IScheduler;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;

@Service
public class TriggerJobService  implements IScheduler {

    private static final long FIXED_RATE = 1000;

    private ScheduledTriggerJobsRepository scheduledTriggerJobsRepository;

    @Override
    @Scheduled(fixedRate = FIXED_RATE)
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    public void process() {

    }

}
