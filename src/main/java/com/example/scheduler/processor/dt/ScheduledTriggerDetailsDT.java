package com.example.scheduler.processor.dt;

import com.example.scheduler.processor.conzt.dt.ScheduledTriggerDetailsConst;
import com.example.scheduler.processor.conzt.dt.ScheduledTriggerProcessedConst;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = ScheduledTriggerDetailsConst.TABLE_NAME)
@Data
public class ScheduledTriggerDetailsDT {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = ScheduledTriggerDetailsConst.ID_COLUMN, unique = true, updatable = false, nullable = false)
    private long id;

    @Column(name = ScheduledTriggerDetailsConst.NAME_COLUMN, nullable = false)
    private String name;

    @Column(name = ScheduledTriggerDetailsConst.EXPRESSION_COLUMN, nullable = false)
    private String expression;

    @Column(name = ScheduledTriggerDetailsConst.LAST_UPDATED_COLUMN)
    private Date lastUpdatedTS;

    @Column(name = ScheduledTriggerDetailsConst.CREATED_TS_COLUMN, nullable = false)
    private Date createdTS;

    @OneToOne(mappedBy = ScheduledTriggerDetailsConst.TRIGGER_MAPPED_BY, targetEntity = ScheduledTriggerJobsDT.class, fetch = FetchType.LAZY)
    private ScheduledTriggerJobsDT scheduledTriggerJobs;

    @OneToMany(mappedBy = ScheduledTriggerProcessedConst.TRIGGER_MAPPED_BY)
    private Set<ScheduledTriggerProcessedDT> triggerProcessing;

    @Column(name = ScheduledTriggerDetailsConst.STATUS, nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        NOT_PROVISIONED,PROVISIONED,ERROR;
    }

    @Override
    public String toString() {
        return "ScheduledTriggerDetailsDT{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", expression='" + expression + '\'' +
                ", lastUpdatedTS=" + lastUpdatedTS +
                ", createdTS=" + createdTS +
                ", status=" + status +
                '}';
    }
}
