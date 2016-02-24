import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import top.kass.dao.UserDao;
import top.kass.model.Customer;
import top.kass.model.Shop;
import top.kass.model.User;
import top.kass.model.UserShop;
import top.kass.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Set;

public class MyTest {

    @Test
    public void test() {
        ApplicationContext ctx = new FileSystemXmlApplicationContext(
                "src/main/webapp/WEB-INF/applicationContext.xml");
        UserDao userDao = ctx.getBean(UserDao.class);
//        User user = userDao.test();
//        UserShop userShop = userDao.test2();
        Shop shop = userDao.test3();
        Set<UserShop> userShops = shop.getUserShops();
        Iterator<UserShop> it = userShops.iterator();
//        Customer customer = userDao.test4();
//        System.out.println(customer.getCustomerStatus().getPauseTime());


    }
}
