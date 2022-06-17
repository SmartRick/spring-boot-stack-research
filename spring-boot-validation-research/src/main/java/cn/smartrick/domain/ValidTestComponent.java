package cn.smartrick.domain;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Date: 2021/12/31 10:12
 * @Author: SmartRick
 * @Description: TODO
 */
@Component
@Validated
public class ValidTestComponent {
    @NotBlank
    private String name;
    @NotNull
    private Integer age;

    public String getName() {
        return name;
    }

    @Validated
    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "ValidTestComponent{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
