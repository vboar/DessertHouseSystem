package top.kass.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "customer_status", schema = "dhs", catalog = "")
public class CustomerStatus {

    private int customerId;
    private Date startTime;
    private Date pauseTime;
    private Date stopTime;

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
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "pause_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(Date pauseTime) {
        this.pauseTime = pauseTime;
    }

    @Basic
    @Column(name = "stop_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }
}
