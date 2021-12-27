package cn.smartrick.mapper;

import cn.smartrick.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Date: 2021/12/25
 * @Author: SmartRick
 * @Description: TODO
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
