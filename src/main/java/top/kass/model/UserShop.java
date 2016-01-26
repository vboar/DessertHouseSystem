package top.kass.model;

import javax.persistence.*;

/**
 * Created by Vboar on 2016/1/26.
 */
@Entity
@Table(name = "user_shop", schema = "dhs", catalog = "")
public class UserShop {
    private int userId;
    private int shopId;

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "shop_id")
    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserShop userShop = (UserShop) o;

        if (userId != userShop.userId) return false;
        if (shopId != userShop.shopId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + shopId;
        return result;
    }
}
