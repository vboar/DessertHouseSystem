import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import top.kass.service.UserService;

public class MyTest {

    @Test
    public void test() {
        ApplicationContext ctx = new FileSystemXmlApplicationContext(
                "src/main/webapp/WEB-INF/applicationContext.xml");
        UserService userService = ctx.getBean(UserService.class);
    }
}
