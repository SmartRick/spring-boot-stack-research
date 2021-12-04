package cn.smartrick.utils;



import cn.smartrick.common.ValidateResult;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Date: 2021/12/2 11:14
 * @Author: SmartRick
 * @Description: TODO
 */
public class ValidateUtil {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 校验对象所有参数
     * @param obj
     * @param groups
     * @return
     */
    public static <T> ValidateResult validateEntity(T obj, Class<?>... groups) {
        return obtainValidateResult(validator.validate(obj, groups.length == 0 ? new Class[]{Default.class} : groups));
    }

    /**
     * 校验对象指定属性
     * @param obj
     * @param propertyName
     * @param groups
     * @return
     */
    public static <T> ValidateResult validateProperty(T obj, String propertyName, Class<?>... groups) {
        return obtainValidateResult(validator.validateProperty(obj, propertyName, groups.length == 0 ? new Class[]{Default.class} : groups));
    }

    private static <T> ValidateResult obtainValidateResult(Set<ConstraintViolation<T>> set) {
        ValidateResult result = new ValidateResult();
        if (!set.isEmpty()) {
            result.setHasErrors(true);
            Map<String, String> errorMsg = new HashMap<String, String>();
            for (ConstraintViolation cv : set) {
                errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
            }
            result.setErrorMsg(errorMsg);
        }
        return result;
    }
}
