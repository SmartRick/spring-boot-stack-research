package cn.smartrick.validator;

import cn.smartrick.common.annotation.Onum;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Date: 2021/12/6 9:52
 * @Author: SmartRick
 * @Description: 自定义校验器
 * @Param Onum 校验注解类型
 * @Param Integer 校验参数类型
 */
public class OnumValidator implements ConstraintValidator<Onum, Integer> {
    @Override
    public boolean isValid(Integer o, ConstraintValidatorContext constraintValidatorContext) {
        return o % 2 == 0;
    }
}
