package top.kass.model;

import javax.persistence.*;

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

}
