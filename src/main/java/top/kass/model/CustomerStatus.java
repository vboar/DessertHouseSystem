package top.kass.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Vboar on 2016/1/26.
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerStatus that = (CustomerStatus) o;

        if (customerId != that.customerId) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (pauseTime != null ? !pauseTime.equals(that.pauseTime) : that.pauseTime != null) return false;
        if (stopTime != null ? !stopTime.equals(that.stopTime) : that.stopTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = customerId;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (pauseTime != null ? pauseTime.hashCode() : 0);
        result = 31 * result + (stopTime != null ? stopTime.hashCode() : 0);
        return result;
    }
}
