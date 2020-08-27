package com.example.scheduler.processor.repository;

import com.example.scheduler.processor.dt.ScheduledTriggerDetailsDT;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledTriggerDetailsRepository extends CrudRepository<ScheduledTriggerDetailsDT, String> {

    public Iterable<ScheduledTriggerDetailsDT> findByStatusIn(List<ScheduledTriggerDetailsDT.Status> status);

}
