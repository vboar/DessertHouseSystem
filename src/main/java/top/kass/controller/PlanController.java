package top.kass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.kass.service.PlanService;

@Controller
public class PlanController {

    @Autowired
    private PlanService planService;

    // 产品计划管理页面
    @RequestMapping(value="/admin/plan", method= RequestMethod.GET)
    public String planListPage() {
        return "admin/plan/plan_list";
    }

    // 产品计划详情页面
    @RequestMapping(value="/admin/planDetail", method= RequestMethod.GET)
    public String planDetailPage() {
        return "admin/plan/plan_detail";
    }

    // 制定产品计划页面
    @RequestMapping(value="/admin/addPlan", method= RequestMethod.GET)
    public String addPlanPage() {
        return "admin/plan/add_plan";
    }

    // 制定产品计划操作
    @RequestMapping(value="/admin/addPlan", method= RequestMethod.POST)
    public String addPlan() {
        return "";
    }

    // 编辑产品计划页面
    @RequestMapping(value="/admin/editPlan", method= RequestMethod.GET)
    public String editPlanPage() {
        return "admin/plan/edit_plan";
    }

    // 编辑产品计划操作
    @RequestMapping(value="/admin/editPlan", method= RequestMethod.POST)
    public String editPlan() {
        return "";
    }

}
