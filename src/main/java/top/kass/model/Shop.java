package top.kass.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Vboar on 2016/1/26.
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shop shop = (Shop) o;

        if (id != shop.id) return false;
        if (status != shop.status) return false;
        if (name != null ? !name.equals(shop.name) : shop.name != null) return false;
        if (description != null ? !description.equals(shop.description) : shop.description != null) return false;
        if (address != null ? !address.equals(shop.address) : shop.address != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (int) status;
        return result;
    }
}
