package com.example.scheduler.processor.dt;

import com.example.scheduler.processor.conzt.dt.ScheduledTriggerProcessedConst;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = ScheduledTriggerProcessedConst.TABLE_NAME)
public class ScheduledTriggerProcessed {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = ScheduledTriggerProcessedConst.ID_COLUMN, unique = true, updatable = false, nullable = false)
    private String id;

    @Column(name = ScheduledTriggerProcessedConst.STATE_COLUMN, nullable = false)
    private State currentState;

    @Column(name = ScheduledTriggerProcessedConst.ACTION_COLUMN, nullable = false)
    private Action action;

    @Column(name = ScheduledTriggerProcessedConst.COMPLETED_TS_COLUMN)
    private Date completedTS;

    @Column(name = ScheduledTriggerProcessedConst.STARTED_TS_COLUMN)
    private Date startedTS;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ScheduledTriggerProcessedConst.TRIGGER_ID_COLUMN, nullable = false)
    private ScheduledTriggerDetails scheduledTriggerDetails;

    public enum State {
        IN_PROGRESS, COMPLETED,ERROR;
    };

    public enum Action {
        HTTPS;
    }

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

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Date getCompletedTS() {
        return completedTS;
    }

    public void setCompletedTS(Date completedTS) {
        this.completedTS = completedTS;
    }

    public Date getStartedTS() {
        return startedTS;
    }

    public void setStartedTS(Date startedTS) {
        this.startedTS = startedTS;
    }
}
