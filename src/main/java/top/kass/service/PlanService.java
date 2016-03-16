package top.kass.service;

import top.kass.model.Plan;

public interface PlanService {

    public String[] getDate(int shopId);

    public void addPlan(String data, int shopId);

    public void edit(String data);

    public Plan getPlanById(int id);

    public void updateStatus(int id, int status);

}
