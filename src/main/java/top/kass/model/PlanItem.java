package top.kass.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Vboar on 2016/1/26.
 */
@Entity
@Table(name = "plan_item", schema = "dhs", catalog = "")
public class PlanItem {
    private int id;
    private int planId;
    private int productId;
    private double price;
    private int number;
    private int remaining;
    private int point;
    private Date date;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "plan_id")
    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    @Basic
    @Column(name = "product_id")
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "number")
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Basic
    @Column(name = "remaining")
    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
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
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanItem planItem = (PlanItem) o;

        if (id != planItem.id) return false;
        if (planId != planItem.planId) return false;
        if (productId != planItem.productId) return false;
        if (Double.compare(planItem.price, price) != 0) return false;
        if (number != planItem.number) return false;
        if (remaining != planItem.remaining) return false;
        if (point != planItem.point) return false;
        if (date != null ? !date.equals(planItem.date) : planItem.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + planId;
        result = 31 * result + productId;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + number;
        result = 31 * result + remaining;
        result = 31 * result + point;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
