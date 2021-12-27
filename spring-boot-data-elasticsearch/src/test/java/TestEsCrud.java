import cn.smartrick.EsApplication;
import cn.smartrick.dao.EsUserDao;
import cn.smartrick.domain.ESUserDo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Date: 2021/12/22 17:21
 * @Author: SmartRick
 * @Description: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsApplication.class)
public class TestEsCrud {
    @Resource
    private EsUserDao userDao;
    @Resource
    private ElasticsearchRestTemplate restTemplate;

    @Test
    public void TestEsSave() {
        ESUserDo esUserDo = new ESUserDo();
        esUserDo.setId(1L);
        esUserDo.setUsername("聪明的小羊羔");
        esUserDo.setAddress("四川省成都市高新区");
        esUserDo.setPhone("17760187082");
        esUserDo.setAge(22);

        userDao.save(esUserDo);
    }

    @Test
    public void TestEsFind() {
        ESUserDo esUserDo = userDao.findById(1L).get();
        System.out.println(esUserDo);
    }

    @Test
    public void TestEsOther() {
        System.out.println(userDao.count());
        Iterable<ESUserDo> id = userDao.findAll(Sort.by("id"));
        id.forEach(System.out::println);
    }

    @Test
    public void TestRestTemp(){
        System.out.println(restTemplate.cluster().health());
    }
}
