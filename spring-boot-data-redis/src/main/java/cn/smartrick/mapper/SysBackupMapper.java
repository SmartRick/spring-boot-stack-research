package cn.smartrick.mapper;

import cn.smartrick.entity.SysBackup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Date: 2022/3/1 10:20
 * @Author: SmartRick
 * @Description: TODO
 */
public interface SysBackupMapper extends BaseMapper<SysBackup> {

    @Select("select TABLE_NAME from information_schema.TABLES where TABLE_SCHEMA=(select database())")
    List<String> getAllTables();
}
