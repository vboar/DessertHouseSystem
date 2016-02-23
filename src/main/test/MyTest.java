import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

public class MyTest {

    @Test
    public void test() throws SQLException {
        ApplicationContext ctx = new FileSystemXmlApplicationContext(
                "src/main/webapp/WEB-INF/applicationContext.xml");
        DataSource dataSource = ctx.getBean(DataSource.class);
        System.out.println(dataSource.getConnection());

    }
}
