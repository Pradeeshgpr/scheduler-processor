package com.example.scheduler.processor.repository.impl;

import com.example.scheduler.processor.conzt.dt.ScheduledTriggerDetailsConst;
import com.example.scheduler.processor.dt.ScheduledTriggerDetailsDT;
import com.example.scheduler.processor.dt.ScheduledTriggerJobsDT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ScheduledTriggerDetailsRepositoryImpl {

    @Autowired
    private EntityManager entityManager;

    public Iterable<ScheduledTriggerDetailsDT> getTriggerDetailsToBeScheduled(int limit, List queryElements) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ScheduledTriggerDetailsDT> query = criteriaBuilder.createQuery(ScheduledTriggerDetailsDT.class);
        Root<ScheduledTriggerDetailsDT> scheduledTriggerDetailsDTRoot = query.from(ScheduledTriggerDetailsDT.class);

        Predicate jobsToBePicked = scheduledTriggerDetailsDTRoot.get(ScheduledTriggerDetailsConst.STATUS)
                .in(queryElements);
        query.where(jobsToBePicked);
        query.orderBy(criteriaBuilder.desc(scheduledTriggerDetailsDTRoot.get(ScheduledTriggerDetailsConst.PRIORITY)));

        return entityManager.createQuery(query).setMaxResults(limit).getResultList();
    }

}
