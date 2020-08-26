package com.example.scheduler.processor.dt;

import com.example.scheduler.processor.conzt.dt.ScheduledTriggerProcessedConst;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = ScheduledTriggerProcessedConst.TABLE_NAME)
@Data
public class ScheduledTriggerProcessedDT {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = ScheduledTriggerProcessedConst.ID_COLUMN, unique = true, updatable = false, nullable = false)
    private long id;

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
    private ScheduledTriggerDetailsDT scheduledTriggerDetails;

    public enum State {
        IN_PROGRESS, COMPLETED,ERROR;
    };

    public enum Action {
        HTTPS;
    }

}
