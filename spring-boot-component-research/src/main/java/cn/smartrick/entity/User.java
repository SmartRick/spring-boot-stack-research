package cn.smartrick.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date: 2021/12/25
 * @Author: SmartRick
 * @Description: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user")
public class User {
    private Integer id;
    private String username;
    private String password;
}
