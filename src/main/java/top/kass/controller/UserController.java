package top.kass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.kass.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // 店员管理页面
    @RequestMapping(value="/admin/user", method= RequestMethod.GET)
    public String userListPage() {
        return "admin/user/user_list";
    }

    // 新建店员页面
    @RequestMapping(value="/admin/addUser", method= RequestMethod.GET)
    public String addUserPage() {
        return "admin/user/add_user";
    }

    // 新建店员操作
    @RequestMapping(value="/admin/addUser", method= RequestMethod.POST)
    public String addUser() {
        return "";
    }

    // 编辑店员页面
    @RequestMapping(value="/admin/editUser", method= RequestMethod.GET)
    public String editUserPage() {
        return "admin/user/edit_user";
    }

    // 编辑店员操作
    @RequestMapping(value="/admin/editUser", method= RequestMethod.POST)
    public String editUser() {
        return "";
    }

    // 删除店员操作
    @RequestMapping(value="/admin/deleteUser", method= RequestMethod.POST)
    public String deleteUser() {
        return "";
    }

}
