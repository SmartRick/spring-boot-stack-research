package cn.smartrick.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Date: 2021/12/2 14:46
 * @Author: SmartRick
 * @Description: TODO
 */
@Data
@AllArgsConstructor
public class Computer {
    @NotBlank(message = "用户电脑颜色不能为空")
    private String color;
}
