package top.kass.model;

import javax.persistence.*;

/**
 * Created by Vboar on 2016/1/26.
 */
@Entity
@Table(name = "user_shop", schema = "dhs")
public class UserShop {
    private int userId;
    private User user;
    private Shop shop;

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @OneToOne(mappedBy = "userShop")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "shop_id")
    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserShop userShop = (UserShop) o;

        if (userId != userShop.userId) return false;
        if (shop.getId() != userShop.getShop().getId()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + shop.getId();
        return result;
    }
}
