package top.kass.dao;

import top.kass.model.User;

public interface UserDao {

    public User findByUsername(String username);

    public User findById(int id);

    public User update(User user);

    public User create(String username, String name, String password, int role, int shop);

    public User update(int id, String name, String password, int role, int shop);


}
