package top.kass.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by Vboar on 2016/1/28.
 */
@Entity
public class Book {
    private int id;
    private int customerId;
    private int shopId;
    private Timestamp time;
    private double discount;
    private double originalTotal;
    private double actualTotal;
    private Date buyDate;
    private byte status;

    private Set<BookItem> bookItems;

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
    @Column(name = "shop_id")
    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
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
    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
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

    @Basic
    @Column(name = "buy_date")
    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    @Basic
    @Column(name = "status")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    public Set<BookItem> getBookItems() {
        return bookItems;
    }

    public void setBookItems(Set<BookItem> bookItems) {
        this.bookItems = bookItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != book.id) return false;
        if (customerId != book.customerId) return false;
        if (shopId != book.shopId) return false;
        if (Double.compare(book.discount, discount) != 0) return false;
        if (Double.compare(book.originalTotal, originalTotal) != 0) return false;
        if (Double.compare(book.actualTotal, actualTotal) != 0) return false;
        if (status != book.status) return false;
        if (time != null ? !time.equals(book.time) : book.time != null) return false;
        if (buyDate != null ? !buyDate.equals(book.buyDate) : book.buyDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + customerId;
        result = 31 * result + shopId;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        temp = Double.doubleToLongBits(discount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(originalTotal);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(actualTotal);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (buyDate != null ? buyDate.hashCode() : 0);
        result = 31 * result + (int) status;
        return result;
    }
}
