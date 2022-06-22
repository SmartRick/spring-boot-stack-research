package cn.smartrick.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Date: 2022/6/20 10:38
 * @Author: SmartRick
 * @Description: TODO
 */
@Data
@TableName("sys_redis_temp")
public class SysRedisTemp implements Serializable {
    @TableId(value = "_key", type = IdType.INPUT)
    private String key;
    @TableField("_value")
    private String value;
    @TableField("_expire")
    private Long expire;

    @TableField(exist = false)
    private Object realValue;
}
