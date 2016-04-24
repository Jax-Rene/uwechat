import com.youyicun.framework.config.AppsApplicationConfig;
import com.youyicun.service.MessageService;
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
    private MessageService messageService;

    @Test
    public void Test(){
        System.out.println(messageService.avgScore());
    }

}
