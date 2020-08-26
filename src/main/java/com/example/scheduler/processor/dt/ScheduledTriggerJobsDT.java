package com.example.scheduler.processor.dt;

import com.example.scheduler.processor.conzt.dt.ScheduledTriggerJobsConst;
import com.example.scheduler.processor.conzt.dt.ScheduledTriggerDetailsConst;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = ScheduledTriggerJobsConst.TABLE_NAME)
@Data
public class ScheduledTriggerJobsDT {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = ScheduledTriggerJobsConst.ID_COLUMN, unique = true, updatable = false, nullable = false)
    private long id;

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
    private ScheduledTriggerDetailsDT scheduledTriggerDetails;

}
