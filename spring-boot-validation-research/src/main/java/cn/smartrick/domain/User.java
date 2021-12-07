package cn.smartrick.domain;

import cn.smartrick.common.ValidateGroups;
import cn.smartrick.common.annotation.Onum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.validation.groups.Default;

/**
 * @Date: 2021/12/2 10:58
 * @Author: SmartRick
 * @Description: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @NotBlank(message = "用户名称不能为空")
    private String username;
    /**
     * 测试得出，Default组的优先级最高，只要有Defalut有限检验Default组条件
     */
    @Max(value = 35, message = "年龄不能大于35", groups = {Default.class, ValidateGroups.AddGroup.class})
    @Min(value = 18, message = "年龄不足18岁", groups = {Default.class, ValidateGroups.UpdateGroup.class})
    @Min(value = 3, message = "年龄不足3岁", groups = ValidateGroups.AddGroup.class)
    @Onum(message = "必须是偶数")
    private Integer age;

    @NotBlank
    @Email(message = "邮件格式错误")
    private String email;
    /**
     * 嵌套校验时，需要加@Valid注解才能生效
     */
    @Valid
    @NotNull(message = "用户电脑不能为空", groups = ValidateGroups.UpdateGroup.class)
    private Computer computer;
}
