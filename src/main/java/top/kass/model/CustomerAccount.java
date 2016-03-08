package top.kass.model;

import javax.persistence.*;

/**
 * Created by Vboar on 2016/1/26.
 */
@Entity
@Table(name = "customer_account", schema = "dhs")
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

}
