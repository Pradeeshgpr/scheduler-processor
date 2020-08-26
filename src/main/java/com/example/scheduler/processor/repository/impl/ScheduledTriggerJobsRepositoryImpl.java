package com.example.scheduler.processor.repository.impl;

import com.example.scheduler.processor.conzt.dt.ScheduledTriggerJobsConst;
import com.example.scheduler.processor.dt.ScheduledTriggerJobsDT;
import com.example.scheduler.processor.utils.CronUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class ScheduledTriggerJobsRepositoryImpl {

    @Autowired
    private EntityManager entityManager;

    public Iterable<ScheduledTriggerJobsDT> getNextRunJobs(final int limit) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ScheduledTriggerJobsDT> query = cb.createQuery(ScheduledTriggerJobsDT.class);

        Root<ScheduledTriggerJobsDT> scheduledJobs = query.from(ScheduledTriggerJobsDT.class);

        cb.le(scheduledJobs.get(ScheduledTriggerJobsConst.NEXT_RUN_COLUMN), CronUtils.getNextRun());

        return entityManager.createQuery(query).setMaxResults(limit).getResultList();
    }

}
