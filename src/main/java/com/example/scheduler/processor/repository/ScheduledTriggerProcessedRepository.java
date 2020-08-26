package com.example.scheduler.processor.repository;

import com.example.scheduler.processor.dt.ScheduledTriggerProcessedDT;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduledTriggerProcessedRepository extends CrudRepository<ScheduledTriggerProcessedDT, String> {
}
