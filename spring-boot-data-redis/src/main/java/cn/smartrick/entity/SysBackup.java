package cn.smartrick.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Date: 2022/3/1 10:16
 * @Author: SmartRick
 * @Description: 系统备份实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_backup")
public class SysBackup {
    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 备份时间
     */
    @TableField(value = "`backup_time`")
    private Date backupTime;

    /**
     * 备份用户名称
     */
    @TableField(value = "`user_name`")
    private String userName;

    /**
     * 备份路径
     */
    @TableField(value = "`path`")
    private String path;
}
