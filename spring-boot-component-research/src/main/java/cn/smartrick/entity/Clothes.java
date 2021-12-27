package cn.smartrick.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("t_clothes")
public class Clothes {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String type;
    private String color;
    private String size;

}
