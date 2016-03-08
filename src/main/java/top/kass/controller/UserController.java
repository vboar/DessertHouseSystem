package top.kass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kass.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Map;

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
    @RequestMapping(value="/admin/user/add", method= RequestMethod.GET)
    public String addUserPage() {
        return "admin/user/add_user";
    }

    // 新建店员操作
    @RequestMapping(value="/admin/user/add", method= RequestMethod.POST)
    public String addUser() {
        return "";
    }

    // 编辑店员页面
    @RequestMapping(value="/admin/user/edit", method= RequestMethod.GET)
    public String editUserPage() {
        return "admin/user/edit_user";
    }

    // 编辑店员操作
    @RequestMapping(value="/admin/user/edit", method= RequestMethod.POST)
    public String editUser() {
        return "";
    }

    // 删除店员操作
    @RequestMapping(value="/admin/user/delete", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteUser(int id) {
        return null;
    }

    // 修改密码页面
    @RequestMapping(value="/admin/password", method= RequestMethod.GET)
    public String passwordPage() {
        return "admin/user/password";
    }

    // 修改密码操作
    @RequestMapping(value="/admin/password", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> password(String old, String password, String passwordAgain,
                                        HttpSession session) {
        int id = (int)session.getAttribute("id");
        return userService.password(id, old, password, passwordAgain);
    }

}
