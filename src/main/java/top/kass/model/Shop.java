package top.kass.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Shop {
    private int id;
    private String name;
    private String description;
    private String address;
    private byte status;
    private Set<UserShop> userShops;
    private Set<Product> products;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "status")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    public Set<UserShop> getUserShops() {
        return userShops;
    }

    public void setUserShops(Set<UserShop> userShops) {
        this.userShops = userShops;
    }

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

}
