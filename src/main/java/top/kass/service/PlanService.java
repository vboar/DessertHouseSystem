package top.kass.service;

import top.kass.model.Plan;
import top.kass.model.PlanItem;
import top.kass.vo.IndexProduct;

import java.util.List;

public interface PlanService {

    public String[] getDate(int shopId);

    public void addPlan(String data, int shopId);

    public void edit(String data);

    public Plan getPlanById(int id);

    public void updateStatus(int id, int status);

    public List<Plan> getPlansByShop(int shopId);

    public List<Plan> getPlansByStatus(int status);

    public PlanItem getPlanItem(int productId, String date);

    public List<IndexProduct> getProductsByShopAndSearch(int shopId, String key);

    public List<PlanItem> getPlanItemsByProduct(int productId);

    public List<PlanItem> getTodayPlanItemsByShop(int shopId);

}
