package cn.smartrick.common;

import lombok.Data;

import java.util.Map;

/**
 * @Date: 2021/12/2 11:17
 * @Author: SmartRick
 * @Description: TODO
 */
@Data
public class ValidateResult {

    // 校验结果是否有错
    private boolean hasErrors;

    // 校验错误信息
    private Map<String, String> errorMsg;

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Map<String, String> errorMsg) {
        this.errorMsg = errorMsg;
    }
}