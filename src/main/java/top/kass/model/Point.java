package top.kass.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Vboar on 2016/1/28.
 */
@Entity
public class Point {
    private int id;
    private int customerId;
    private byte type;
    private Consumption consumption;
    private int point;
    private Timestamp time;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "customer_id")
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "type")
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @OneToOne
    @JoinColumn(name = "consumption_id")
    public Consumption getConsumption() {
        return consumption;
    }

    public void setConsumption(Consumption consumption) {
        this.consumption = consumption;
    }

    @Basic
    @Column(name = "point")
    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point1 = (Point) o;

        if (id != point1.id) return false;
        if (customerId != point1.customerId) return false;
        if (type != point1.type) return false;
        if (point != point1.point) return false;
        if (consumption != null ? !consumption.equals(point1.consumption) : point1.consumption != null)
            return false;
        if (time != null ? !time.equals(point1.time) : point1.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + customerId;
        result = 31 * result + (int) type;
        result = 31 * result + (consumption != null ? consumption.hashCode() : 0);
        result = 31 * result + point;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
