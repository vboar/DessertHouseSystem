package top.kass.model;

import javax.persistence.*;

/**
 * Created by Vboar on 2016/1/26.
 */
@Entity
@Table(name = "customer_account", schema = "dhs", catalog = "")
public class CustomerAccount {
    private int customerId;
    private String bankId;
    private double balance;
    private int point;
    private VipLevel vipLevel;

    @Id
    @Column(name = "customer_id")
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "bank_id")
    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    @Basic
    @Column(name = "balance")
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "point")
    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @ManyToOne
    @JoinColumn(name = "vip_level")
    public VipLevel getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(VipLevel vipLevel) {
        this.vipLevel = vipLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerAccount that = (CustomerAccount) o;

        if (customerId != that.customerId) return false;
        if (Double.compare(that.balance, balance) != 0) return false;
        if (point != that.point) return false;
        if (vipLevel.getLevel() != that.vipLevel.getLevel()) return false;
        if (bankId != null ? !bankId.equals(that.bankId) : that.bankId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = customerId;
        result = 31 * result + (bankId != null ? bankId.hashCode() : 0);
        temp = Double.doubleToLongBits(balance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + point;
        result = 31 * result + (int) vipLevel.getLevel();
        return result;
    }
}
