package top.kass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kass.dao.UserDao;
import top.kass.model.User;
import top.kass.service.UserService;
import top.kass.util.Utils;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        username = username.trim();
        password = password.trim();
        if (username.length() == 0 || password.length() == 0) {
            map.put("success", false);
            map.put("error", "请把信息填写完整！");
        } else  {
            User user = userDao.findByUsername(username);
            if (user == null) {
                map.put("success", false);
                map.put("error", "用户名或密码错误！");
            } else {
                if (!Utils.md5(password).equals(user.getPassword())) {
                    map.put("success", false);
                    map.put("error", "用户名或密码错误！");
                } else {
                    map.put("success", true);
                    map.put("user_id", user.getId());
                    map.put("user_name", user.getName());
                    map.put("role", (int)user.getRole());
                }
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> password(int id, String old, String password, String passwordAgain) {
        Map<String, Object> map = new HashMap<>();

        old = old.trim();
        password = password.trim();
        passwordAgain = passwordAgain.trim();
        if (old.length() == 0 || password.length() == 0 || passwordAgain.length() == 0) {
            map.put("success", false);
            map.put("error", "请把密码填写完整！");
        } else if (!password.equals(passwordAgain)) {
            map.put("success", false);
            map.put("error", "输入的新密码不匹配！");
        } else {
            User user = userDao.findById(id);
            old = Utils.md5(old);
            if (!user.getPassword().equals(old)) {
                map.put("success", false);
                map.put("error", "旧密码不正确！");
            } else {
                user.setPassword(Utils.md5(password));
                map.put("success", true);
            }
        }
        return map;
    }
}
