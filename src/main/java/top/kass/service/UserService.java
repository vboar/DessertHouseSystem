package top.kass.service;

import top.kass.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    public Map<String, Object> login(String username, String password);

    public Map<String, Object> password(int id, String old, String password, String passwordAgain);

    public Map<String, Object> add(String username, String name, String password, int role, int shop);

    public Map<String, Object> edit(int id, String name, String password, int role, int shop);

    public User getUserById(int id);

    public List<User> getAllUsers(); //Except Admin

    public Map<String, Object> deleteUser(int id);

}
