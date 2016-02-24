package top.kass.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Plan {
    private int id;
    private int shopId;
    private Date startTime;
    private Date endTime;
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
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
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
