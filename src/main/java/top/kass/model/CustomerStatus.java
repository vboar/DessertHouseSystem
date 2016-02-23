package top.kass.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "customer_status", schema = "dhs", catalog = "")
public class CustomerStatus {
    private int customerId;
    private Timestamp startTime;
    private Timestamp pauseTime;
    private Timestamp stopTime;

    @Id
    @Column(name = "customer_id")
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "start_time")
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "pause_time")
    public Timestamp getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(Timestamp pauseTime) {
        this.pauseTime = pauseTime;
    }

    @Basic
    @Column(name = "stop_time")
    public Timestamp getStopTime() {
        return stopTime;
    }

    public void setStopTime(Timestamp stopTime) {
        this.stopTime = stopTime;
    }
}
