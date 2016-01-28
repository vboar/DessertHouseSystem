package top.kass.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Vboar on 2016/1/28.
 */
@Entity
@Table(name = "plan_item", schema = "dhs", catalog = "")
public class PlanItem {
    private int id;
    private Plan plan;
    private Product product;
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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "plan_id")
    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    @OneToOne
    @JoinColumn(name = "product_id")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        if (plan.getId() != planItem.getPlan().getId()) return false;
        if (product.getId() != planItem.getProduct().getId()) return false;
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
        result = 31 * result + plan.getId();
        result = 31 * result + product.getId();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + number;
        result = 31 * result + remaining;
        result = 31 * result + point;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
