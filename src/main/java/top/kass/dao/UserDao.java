package top.kass.dao;

import top.kass.model.User;

public interface UserDao {

    public User findByUsername(String username);

    public User findById(int id);

    public User update(User user);

}
