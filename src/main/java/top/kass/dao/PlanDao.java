package top.kass.dao;

import top.kass.model.Plan;

import java.util.Date;
import java.util.List;

public interface PlanDao {

    // 获得下一个周一
    public Date getNextPlanDate(int shopId);

    public void create(String data, int shopId);

    public Plan findById(int planId);

    public void update(String data);

    public void updateStatus(Plan plan);

    public List<Plan> getPlansByShop(int shopId);

    public List<Plan> getPlansByStatus(int status);

}
