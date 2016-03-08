package top.kass.service;

import java.util.Map;

public interface UserService {

    public Map<String, Object> login(String username, String password);

    public Map<String, Object> password(int id, String old, String password, String passwordAgain);

}
