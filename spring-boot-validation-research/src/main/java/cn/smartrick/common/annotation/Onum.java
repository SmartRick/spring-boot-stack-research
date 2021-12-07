package cn.smartrick.common.annotation;

import cn.smartrick.validator.OnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义校验注解，可以绑定多个校验器
 * 如果一个注解绑定了多个校验器，优先根据所注解的参数类型匹配符合的校验器，匹配不到类型则报错
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OnumValidator.class)
public @interface Onum {
    String message();

    // groups 和 payload 这两个parameter 必须包含,不然会报错
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
