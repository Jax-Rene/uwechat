import com.youyicun.entity.Order;
import com.youyicun.framework.config.AppsApplicationConfig;
import com.youyicun.service.MessageService;
import com.youyicun.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by johnny on 16/4/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
@WebAppConfiguration
public class MainTest {
    @Autowired
    private MessageService messageService;
    @Autowired
    private OrderService orderService;

    @Test
    public void Test() {
        String startTime = "2000-01-01F00:00";
        String endTime = LocalDateTime.now().toString();
        orderService.countMsgNum(startTime,endTime,"",1);
    }

}
