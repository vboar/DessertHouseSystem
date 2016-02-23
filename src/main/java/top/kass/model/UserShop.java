package top.kass.model;

import javax.persistence.*;

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
}
