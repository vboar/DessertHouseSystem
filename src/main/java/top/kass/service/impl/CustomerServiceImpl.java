package top.kass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kass.dao.CustomerDao;
import top.kass.dao.PaymentDao;
import top.kass.dao.PointDao;
import top.kass.model.Customer;
import top.kass.model.Payment;
import top.kass.model.Point;
import top.kass.model.VipLevel;
import top.kass.service.CustomerService;
import top.kass.util.Utils;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private PaymentDao paymentDao;
    @Autowired
    private PointDao pointDao;

    @Override
    public Map<String, Object> register(String phone, String password, String passwordAgain) {
        Map<String, Object> map = new HashMap<>();
        phone = phone.trim();
        password = password.trim();
        passwordAgain = passwordAgain.trim();
        if (phone.length() == 0 || password.length() == 0 || passwordAgain.length() == 0) {
            map.put("success", false);
            map.put("error", "请把信息填写完整！");
        } else if (!Utils.isMobileNumber(phone)) {
            map.put("success", false);
            map.put("error", "请输入正确的手机号码！");
        } else if (!password.equals(passwordAgain)) {
            map.put("success", false);
            map.put("error", "两次输入的密码不对应！");
        } else if (customerDao.phoneExist(phone)) {
            map.put("success", false);
            map.put("error", "该手机号码已经注册了会员！");
        } else {
            Customer customer = customerDao.create(phone, password);
            map.put("success", true);
            map.put("customer_id", customer.getId());
            map.put("customer_name", customer.getCustomerInfo().getName());
        }
        return map;
    }

    @Override
    public Map<String, Object> login(String phone, String password) {
        Map<String, Object> map = new HashMap<>();
        phone = phone.trim();
        password = password.trim();
        if (phone.length() == 0 || password.length() == 0) {
            map.put("success", false);
            map.put("error", "请把信息填写完整！");
        } else if (!Utils.isMobileNumber(phone)) {
            map.put("success", false);
            map.put("error", "请输入正确的手机号码！");
        } else {
            Customer customer = customerDao.findByPhone(phone);
            if (customer == null) {
                map.put("success", false);
                map.put("error", "手机号或密码错误！");
            } else {
                if (!Utils.md5(password).equals(customer.getPassword())) {
                    map.put("success", false);
                    map.put("error", "手机号或密码错误！");
                } else {
                    map.put("success", true);
                    map.put("customer_id", customer.getId());
                    map.put("customer_name", customer.getCustomerInfo().getName());
                }
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> supplyInfo(int id, String name, String birthday, int gender,
                                          String province, String city, String bank) {
        Map<String, Object> map = new HashMap<>();
        name = name.trim();
        birthday = birthday.trim();
        province = province.trim();
        city = city.trim();
        bank = bank.trim();
        if (name.length() == 0 || birthday.length() == 0 || province.length() == 0 ||
                city.length() == 0 || bank.length() == 0) {
            map.put("success", false);
            map.put("error", "请把信息填写完整！");
            return map;
        }

        Customer customer = customerDao.findById(id);
        customer.getCustomerInfo().setName(name);
        try {
            customer.getCustomerInfo().setBirthday(Date.valueOf(birthday));
        } catch (Exception e) {
            map.put("success", false);
            map.put("error", "生日格式不正确！");
            return map;
        }
        customer.getCustomerInfo().setGender((byte)gender);
        customer.getCustomerInfo().setCity(city);
        customer.getCustomerInfo().setProvince(province);
        customer.getCustomerAccount().setBankId(bank);
        customer = customerDao.update(customer);
        map.put("success", true);
        map.put("customer_name", customer.getCustomerInfo().getName());
        return map;
    }

    @Override
    public Customer getCustomerById(int id) {
        return customerDao.findById(id);
    }

    @Override
    public Map<String, Object> validate(int id, String money) {
        Map<String, Object> map = new HashMap<>();

        int moneyNumber = 0;
        try {
            moneyNumber = Integer.parseInt(money);
        } catch (Exception e) {
            map.put("success", false);
            map.put("error", "金额格式不对！");
            return map;
        }

        if (moneyNumber < 200) {
            map.put("success", false);
            map.put("error", "请把信息填写完整！");
            return map;
        }

        Customer customer = customerDao.findById(id);
        customer.getCustomerAccount().setBalance(
                customer.getCustomerAccount().getBalance() + moneyNumber);
        customer.setStatus((byte)1);

        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        customer.getCustomerStatus().setStartTime(startTime);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.YEAR, 1);
        Timestamp pauseTime = new Timestamp(calendar.getTime().getTime());
        customer.getCustomerStatus().setPauseTime(pauseTime);

        calendar.add(Calendar.YEAR, 1);
        Timestamp stopTime = new Timestamp(calendar.getTime().getTime());
        customer.getCustomerStatus().setStopTime(stopTime);

        VipLevel vipLevel = new VipLevel();
        int level = 0;
        if (moneyNumber >= 8888) level = 6;
        else if (moneyNumber >= 3888) level = 5;
        else if (moneyNumber >= 1888) level = 4;
        else if (moneyNumber >= 1288) level = 3;
        else if (moneyNumber >= 688) level = 2;
        else if (moneyNumber >= 200) level = 1;
        else level = 0;
        if ((int)customer.getCustomerAccount().getVipLevel().getLevel() < level) {
            vipLevel.setLevel((byte)level);
            customer.getCustomerAccount().setVipLevel(vipLevel);
        }

        customerDao.update(customer);
        paymentDao.create(id, moneyNumber);

        map.put("success", true);
        return map;
    }

    @Override
    public Map<String, Object> editInfo(int id, String name, String birthday, int gender,
                                        String province, String city) {
        Customer customer = customerDao.findById(id);
        customer.getCustomerInfo().setName(name);
        customer.getCustomerInfo().setBirthday(Date.valueOf(birthday));
        customer.getCustomerInfo().setProvince(province);
        customer.getCustomerInfo().setCity(city);
        customer.getCustomerInfo().setGender((byte)gender);

        customerDao.update(customer);

        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
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
            Customer customer = customerDao.findById(id);
            old = Utils.md5(old);
            if (!customer.getPassword().equals(old)) {
                map.put("success", false);
                map.put("error", "旧密码不正确！");
            } else {
                customer.setPassword(Utils.md5(password));
                customerDao.update(customer);
                map.put("success", true);
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> exchangePoint(int id, int point) {
        Map<String, Object> map = new HashMap<>();

        Customer customer = customerDao.findById(id);
        int currentPoint = customer.getCustomerAccount().getPoint();
        double currentBalance = customer.getCustomerAccount().getBalance();

        if (currentPoint < 100) {
            map.put("success", false);
            map.put("error", "积分不足100，无法兑换！");
            return map;
        }

        if (point < 100) {
            map.put("success", false);
            map.put("error", "一次兑换积分需大于100！");
            return map;
        }

        if (point > currentPoint) {
            map.put("success", false);
            map.put("error", "兑换的积分大于已有的积分，无法兑换！");
            return map;
        }

        // 100积分兑换1元
        customer.getCustomerAccount().setPoint(currentPoint-point);
        customer.getCustomerAccount().setBalance(currentBalance+point*1.0/100);

        customerDao.update(customer);

        // 积分记录
        pointDao.create(id, point, 1, null);

        map.put("success", true);
        map.put("point", customer.getCustomerAccount().getPoint());

        return map;
    }

    @Override
    public List<Point> getPointsByCustomer(int id) {
        return pointDao.findByCustomerId(id);
    }

    @Override
    public List<Payment> getPaymentsByCustomer(int id) {
        return paymentDao.findByCustomerId(id);
    }

    @Override
    public Map<String, Object> recharge(int id, int money, String password) {

        Map<String, Object> map = new HashMap<>();

        if (money < 10) {
            map.put("success", false);
            map.put("error", "充值金额不足10元！");
            return map;
        }

        Customer customer = customerDao.findById(id);
        if (!Utils.md5(password).equals(customer.getPassword())) {
            map.put("success", false);
            map.put("error", "密码错误！");
            return map;
        }

        customer.getCustomerAccount().setBalance(
                customer.getCustomerAccount().getBalance() + money);
        customer.setStatus((byte)1);

        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        customer.getCustomerStatus().setStartTime(startTime);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.YEAR, 1);
        Timestamp pauseTime = new Timestamp(calendar.getTime().getTime());
        customer.getCustomerStatus().setPauseTime(pauseTime);

        calendar.add(Calendar.YEAR, 1);
        Timestamp stopTime = new Timestamp(calendar.getTime().getTime());
        customer.getCustomerStatus().setStopTime(stopTime);

        VipLevel vipLevel = new VipLevel();
        int level = 0;
        if (money >= 8888) level = 6;
        else if (money >= 3888) level = 5;
        else if (money >= 1888) level = 4;
        else if (money >= 1288) level = 3;
        else if (money >= 688) level = 2;
        else if (money >= 200) level = 1;
        else level = 0;
        if ((int)customer.getCustomerAccount().getVipLevel().getLevel() < level) {
            vipLevel.setLevel((byte)level);
            customer.getCustomerAccount().setVipLevel(vipLevel);
        }
        customerDao.update(customer);
        paymentDao.create(id, money);

        map.put("success", true);
        return map;
    }

    @Override
    public Map<String, Object> stop(int id) {
        Map<String, Object> map = new HashMap<>();
        Customer customer = customerDao.findById(id);
        customer.setStatus((byte)3);
        customerDao.update(customer);
        map.put("success", true);
        return map;
    }

    @Override
    public void statusRecheck(int id) {
        Customer customer = customerDao.findById(id);

        if (customer.getStatus() == 3) {
            return;
        }

        if (customer.getStatus() == 0 && customer.getCustomerStatus().getPauseTime() == null) {
            return;
        }

        Timestamp current = new Timestamp(System.currentTimeMillis());
        Timestamp pause = customer.getCustomerStatus().getPauseTime();
        Timestamp stop = customer.getCustomerStatus().getStopTime();

        Calendar calendar = Calendar.getInstance();

        if (stop == null) {
            calendar.setTime(new Date(pause.getTime()));
            calendar.add(Calendar.YEAR, 1);
            customer.getCustomerStatus().setStopTime(new Timestamp(calendar.getTime().getTime()));
            stop = customer.getCustomerStatus().getStopTime();
        }

        if (customer.getCustomerAccount().getBalance() < 10
                && current.compareTo(pause) > 0) {
            customer.setStatus((byte)2);
            calendar.setTime(new Date(pause.getTime()));
            calendar.add(Calendar.YEAR, 1);
            customer.getCustomerStatus().setStopTime(new Timestamp(calendar.getTime().getTime()));
        } else if (current.compareTo(pause) < 0) {
            customer.setStatus((byte)1);
        }
        if (current.compareTo(stop) > 0) {
            customer.setStatus((byte)3);
        }

        customerDao.update(customer);
    }

    @Override
    public Customer getCustomerByCode(int code) {
        return customerDao.findByCode(code);
    }

}
