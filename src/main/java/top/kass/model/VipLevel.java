package top.kass.model;

import javax.persistence.*;

/**
 * Created by Vboar on 2016/1/26.
 */
@Entity
@Table(name = "vip_level", schema = "dhs", catalog = "")
public class VipLevel {
    private byte level;
    private String name;
    private double discount;
    private double money;

    @Id
    @Column(name = "level")
    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "money")
    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VipLevel vipLevel = (VipLevel) o;

        if (level != vipLevel.level) return false;
        if (Double.compare(vipLevel.discount, discount) != 0) return false;
        if (Double.compare(vipLevel.money, money) != 0) return false;
        if (name != null ? !name.equals(vipLevel.name) : vipLevel.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) level;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(discount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(money);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
