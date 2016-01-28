package top.kass.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Vboar on 2016/1/28.
 */
@Entity
public class Consumption {
    private int id;
    private int customerId;
    private byte type;
    private Book book;
    private Sale sale;
    private double money;
    private byte payType;
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
    @JoinColumn(name = "book_id")
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @OneToOne
    @JoinColumn(name = "sale_id")
    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    @Basic
    @Column(name = "money")
    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Basic
    @Column(name = "pay_type")
    public byte getPayType() {
        return payType;
    }

    public void setPayType(byte payType) {
        this.payType = payType;
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

        Consumption that = (Consumption) o;

        if (id != that.id) return false;
        if (customerId != that.customerId) return false;
        if (type != that.type) return false;
        if (Double.compare(that.money, money) != 0) return false;
        if (payType != that.payType) return false;
        if (point != that.point) return false;
        if (book != null ? !book.equals(that.getBook()) : that.getBook() != null) return false;
        if (sale != null ? !sale.equals(that.sale) : that.sale != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + customerId;
        result = 31 * result + (int) type;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (sale != null ? sale.hashCode() : 0);
        temp = Double.doubleToLongBits(money);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) payType;
        result = 31 * result + point;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
