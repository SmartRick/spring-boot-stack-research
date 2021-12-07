import cn.smartrick.ValidationResearchApplication;
import cn.smartrick.common.ValidateGroups;
import cn.smartrick.common.ValidateResult;
import cn.smartrick.domain.User;
import cn.smartrick.utils.ValidateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.groups.Default;
import java.util.Map;

/**
 * @Date: 2021/12/2 16:30
 * @Author: SmartRick
 * @Description: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ValidationResearchApplication.class)
public class TestValidation {
    @Test
    public void testValidation() {
        User user = new User("", 4, "dkods@kq", null);
        ValidateResult validateResult = ValidateUtil.validateEntity(user, ValidateGroups.AddGroup.class, Default.class);
        if (validateResult.isHasErrors()) {
            for (Map.Entry entry : validateResult.getErrorMsg().entrySet()) {
                System.out.println(String.format("属性%s检测失败，原因：%s", entry.getKey(), entry.getValue()));
            }
        } else {
            System.out.println("对象验证成功");

        }
    }
}
