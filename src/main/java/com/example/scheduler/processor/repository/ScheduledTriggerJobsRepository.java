package com.example.scheduler.processor.repository;

import com.example.scheduler.processor.dt.ScheduledTriggerJobsDT;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduledTriggerJobsRepository extends CrudRepository<ScheduledTriggerJobsDT, String> {
}
