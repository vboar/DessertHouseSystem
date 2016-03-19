package top.kass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kass.dao.*;
import top.kass.model.Book;
import top.kass.model.Consumption;
import top.kass.model.Customer;
import top.kass.service.SaleService;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private ConsumptionDao consumptionDao;
    @Autowired
    private PointDao pointDao;

    @Override
    public Map<String, Object> payForBook(int id, int type) {

        Map<String, Object> map = new HashMap<>();
        Book book = bookDao.findById(id);
        Customer customer = customerDao.findById(book.getCustomerId());

        // 卡余额不足
        if (type == 1 && customer.getCustomerAccount().getBalance() < book.getActualTotal()) {
            map.put("success", false);
            map.put("error", "卡余额不足，请用现金支付！");
            return map;
        }

        Consumption consumption = new Consumption();
        consumption.setPayType((byte)type);
        consumption.setBook(book);
        consumption.setCustomerId(customer.getId());
        consumption.setMoney(book.getActualTotal());
        consumption.setPoint(book.getTotalPoint());
        consumption.setTime(new Timestamp(System.currentTimeMillis()));
        consumptionDao.createOrUpdate(consumption);

        // 卡支付扣钱
        if (type == 1) {
            customer.getCustomerAccount().setBalance(
                    customer.getCustomerAccount().getBalance()-book.getActualTotal());
        }
        customer.getCustomerAccount().setPoint(
                customer.getCustomerAccount().getPoint()+book.getTotalPoint());
        customerDao.update(customer);

        book.setStatus((byte)1);
        book.setSaleTime(new Timestamp(System.currentTimeMillis()));
        bookDao.update(book);

        // 积分记录
        pointDao.create(customer.getId(), book.getTotalPoint(), 0, consumption.getId());

        map.put("success", true);
        return map;
    }
}
