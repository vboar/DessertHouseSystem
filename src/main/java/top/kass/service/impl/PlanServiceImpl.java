package top.kass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kass.dao.PlanDao;
import top.kass.service.PlanService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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


}
