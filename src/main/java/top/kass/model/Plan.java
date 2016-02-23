package top.kass.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class Plan {
    private int id;
    private int shopId;
    private Timestamp startTime;
    private Timestamp endTime;
    private byte status;
    private Set<PlanItem> planItems;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "start_time")
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "status")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    public Set<PlanItem> getPlanItems() {
        return planItems;
    }

    public void setPlanItems(Set<PlanItem> planItems) {
        this.planItems = planItems;
    }

}
