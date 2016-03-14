package top.kass.dao;

import java.util.Date;

public interface PlanDao {

    // 获得下一个周一
    public Date getNextPlanDate(int shopId);

}
