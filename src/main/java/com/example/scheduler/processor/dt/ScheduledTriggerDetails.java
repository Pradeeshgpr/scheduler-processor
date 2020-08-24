package com.example.scheduler.processor.dt;

import com.example.scheduler.processor.conzt.dt.ScheduledTriggerDetailsConst;
import com.example.scheduler.processor.conzt.dt.ScheduledTriggerProcessedConst;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = ScheduledTriggerDetailsConst.TABLE_NAME)
public class ScheduledTriggerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = ScheduledTriggerDetailsConst.ID_COLUMN, unique = true, updatable = false, nullable = false)
    private String id;

    @Column(name = ScheduledTriggerDetailsConst.NAME_COLUMN, nullable = false)
    private String name;

    @Column(name = ScheduledTriggerDetailsConst.EXPRESSION_COLUMN, nullable = false)
    private String expression;

    @Column(name = ScheduledTriggerDetailsConst.LAST_UPDATED_COLUMN)
    private Date lastUpdatedTS;

    @Column(name = ScheduledTriggerDetailsConst.CREATED_TS_COLUMN, nullable = false)
    private Date createdTS;

    @OneToOne(mappedBy = ScheduledTriggerDetailsConst.TRIGGER_MAPPED_BY, targetEntity = ScheduledTriggerJobs.class, fetch = FetchType.LAZY)
    private ScheduledTriggerJobs scheduledTriggerJobs;

    @OneToMany(mappedBy = ScheduledTriggerProcessedConst.TRIGGER_MAPPED_BY)
    private Set<ScheduledTriggerProcessed> triggerProcessing;

    public Set<ScheduledTriggerProcessed> getTriggerProcessing() {
        return triggerProcessing;
    }

    public void setTriggerProcessing(Set<ScheduledTriggerProcessed> triggerProcessing) {
        this.triggerProcessing = triggerProcessing;
    }

    public ScheduledTriggerJobs getJobDetails() {
        return scheduledTriggerJobs;
    }

    public void setJobDetails(ScheduledTriggerJobs scheduledTriggerJobs) {
        this.scheduledTriggerJobs = scheduledTriggerJobs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Date getLastUpdatedTS() {
        return lastUpdatedTS;
    }

    public void setLastUpdatedTS(Date lastUpdatedTS) {
        this.lastUpdatedTS = lastUpdatedTS;
    }

    public Date getCreatedTS() {
        return createdTS;
    }

    public void setCreatedTS(Date createdTS) {
        this.createdTS = createdTS;
    }
}
