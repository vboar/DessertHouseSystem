package top.kass.dao;

import top.kass.model.User;

import java.util.List;

public interface UserDao {

    public User findByUsername(String username);

    public User findById(int id);

    public User update(User user);

    public User create(String username, String name, String password, int role, int shop);

    public List<User> getAll();

    public void delete(int id);

}
