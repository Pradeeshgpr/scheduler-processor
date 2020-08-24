package com.example.scheduler.processor.dt;

import com.example.scheduler.processor.conzt.dt.ScheduledTriggerJobsConst;
import com.example.scheduler.processor.conzt.dt.ScheduledTriggerDetailsConst;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = ScheduledTriggerJobsConst.TABLE_NAME)
public class ScheduledTriggerJobs {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = ScheduledTriggerJobsConst.ID_COLUMN, unique = true, updatable = false, nullable = false)
    private String id;

    @Column(name = ScheduledTriggerJobsConst.NEXT_RUN_COLUMN, nullable = false)
    private long nextRun;

    @Column(name = ScheduledTriggerJobsConst.LAST_RUN_COLUMN)
    private long lastRun;

    @Column(name = ScheduledTriggerJobsConst.CREATED_TS_COLUMN, nullable = false)
    private Date createdTS;

    @Column(name = ScheduledTriggerJobsConst.LAST_UPDATED_TS_COLUMN)
    private Date lastUpdatedTS;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ScheduledTriggerJobsConst.TRIGGER_ID_COLUMN, referencedColumnName = ScheduledTriggerDetailsConst.ID_COLUMN, nullable = false)
    private ScheduledTriggerDetails scheduledTriggerDetails;

    public ScheduledTriggerDetails getTriggerDetails() {
        return scheduledTriggerDetails;
    }

    public void setTriggerDetails(ScheduledTriggerDetails scheduledTriggerDetails) {
        this.scheduledTriggerDetails = scheduledTriggerDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getNextRun() {
        return nextRun;
    }

    public void setNextRun(long nextRun) {
        this.nextRun = nextRun;
    }

    public long getLastRun() {
        return lastRun;
    }

    public void setLastRun(long lastRun) {
        this.lastRun = lastRun;
    }

    public Date getCreatedTS() {
        return createdTS;
    }

    public void setCreatedTS(Date createdTS) {
        this.createdTS = createdTS;
    }

    public Date getLastUpdatedTS() {
        return lastUpdatedTS;
    }

    public void setLastUpdatedTS(Date lastUpdatedTS) {
        this.lastUpdatedTS = lastUpdatedTS;
    }
}
