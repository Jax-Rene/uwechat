import com.youyicun.dao.BaseDao;
import com.youyicun.entity.Order;
import com.youyicun.framework.config.AppsApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by johnny on 16/4/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
@WebAppConfiguration
public class MainTest {
    @Autowired
    private BaseDao<Order> baseDao;

    @Test
    public void Test(){
        Order order = new Order();
        order.setLastName("庄");
//        order.setPhone("18649713696");
//        order.setPeople(2);
//        order.setOpenId("abcdefghsdasdsa");
//        order.setSex(1);
//        order.setSuccess(0);
        baseDao.save(order);
    }

}
