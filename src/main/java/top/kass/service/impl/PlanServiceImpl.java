package top.kass.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kass.dao.PlanDao;
import top.kass.model.Plan;
import top.kass.model.PlanItem;
import top.kass.service.PlanService;
import top.kass.vo.IndexProduct;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanDao planDao;

    @Override
    public String[] getDate(int shopId) {
        String[] dates = new String[2];

        Date start = planDao.getNextPlanDate(shopId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dates[0] = sdf.format(start);
        System.out.println(start);

        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        dates[1] = sdf.format(cal.getTime());
        return dates;
    }

    @Override
    public void addPlan(String data, int shopId) {
        planDao.create(data, shopId);
    }

    @Override
    public void edit(String data) {
        planDao.update(data);
    }

    @Override
    public Plan getPlanById(int id) {
        return planDao.findById(id);
    }

    @Override
    public void updateStatus(int id, int status) {
        Plan plan = planDao.findById(id);
        plan.setStatus((byte)status);
        planDao.updateStatus(plan);
    }

    @Override
    public List<Plan> getPlansByShop(int shopId) {
        return planDao.getPlansByShop(shopId);
    }

    @Override
    public List<Plan> getPlansByStatus(int status) {
        return planDao.getPlansByStatus(status);
    }

    @Override
    public PlanItem getPlanItem(int productId, String date) {
        return planDao.findByProductAndDate(productId, date);
    }

    @Override
    public List<IndexProduct> getProductsByShopAndSearch(int shopId, String key) {
        return planDao.findByShopAndSearch(shopId, key);
    }

    @Override
    public List<PlanItem> getPlanItemsByProduct(int productId) {
        return planDao.getByProduct(productId);
    }

    @Override
    public List<PlanItem> getTodayPlanItemsByShop(int shopId) {
        return planDao.getTodayByShop(shopId);
    }


}
