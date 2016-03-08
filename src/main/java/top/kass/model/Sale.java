package top.kass.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Vboar on 2016/1/28.
 */
@Entity
public class Sale {
    private int id;
    private int shopId;
    private int userId;
    private Integer customerId;
    private Book book;
    private Timestamp time;
    private Double discount;
    private double originalTotal;
    private double actualTotal;

    @Id
    @Column(name = "id")
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "shop_id")
    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "customer_id")
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @OneToOne
    @JoinColumn(name = "book_id")
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "discount")
    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Basic
    @Column(name = "original_total")
    public double getOriginalTotal() {
        return originalTotal;
    }

    public void setOriginalTotal(double originalTotal) {
        this.originalTotal = originalTotal;
    }

    @Basic
    @Column(name = "actual_total")
    public double getActualTotal() {
        return actualTotal;
    }

    public void setActualTotal(double actualTotal) {
        this.actualTotal = actualTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sale sale = (Sale) o;

        if (id != sale.id) return false;
        if (shopId != sale.shopId) return false;
        if (userId != sale.userId) return false;
        if (Double.compare(sale.originalTotal, originalTotal) != 0) return false;
        if (Double.compare(sale.actualTotal, actualTotal) != 0) return false;
        if (customerId != null ? !customerId.equals(sale.customerId) : sale.customerId != null) return false;
        if (book != null ? !book.equals(sale.book) : sale.book != null) return false;
        if (time != null ? !time.equals(sale.time) : sale.time != null) return false;
        if (discount != null ? !discount.equals(sale.discount) : sale.discount != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + shopId;
        result = 31 * result + userId;
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        temp = Double.doubleToLongBits(originalTotal);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(actualTotal);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
