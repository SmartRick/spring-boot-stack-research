import cn.smartrick.StartApplication;
import cn.smartrick.config.DynamicDataSource;
import cn.smartrick.entity.Clothes;
import cn.smartrick.entity.User;
import cn.smartrick.service.ClothesService;
import cn.smartrick.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @Date: 2021/12/25
 * @Author: SmartRick
 * @Description: TODO
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApplication.class)
public class TestRouterDataSource {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserService userService;
    @Autowired
    private ClothesService clothesService;

    @Autowired
    public ApplicationContext applicationContext;

    @Test
    public void TestDataSourceType() {
        if (dataSource instanceof DynamicDataSource) {
            log.info("当前为动态数据源");
        }
    }

    @Test
    public void TestDynamicDataSource() {
        boolean addUserFlag = userService.addUser(new User(4, "牛人", "123"));
        if (addUserFlag) {
            log.info("添加用户成功");
        }

        boolean addClothesFlag = clothesService.addClothes(new Clothes(6, "羽绒服", "RED", "L"));
        if (addClothesFlag) {
            log.info("添加衣服成功");
        }

    }

    @Test
    public void TestApplicationContext() {
        Map<String, DataSource> beansOfType = applicationContext.getBeansOfType(DataSource.class);
        beansOfType.keySet().forEach(key -> {
            log.info(key +" = "+ beansOfType.get(key).toString());
        });
    }

}
