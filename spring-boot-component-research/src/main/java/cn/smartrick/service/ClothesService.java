package cn.smartrick.service;

import cn.smartrick.anno.DataSource;
import cn.smartrick.anno.DataSourceType;
import cn.smartrick.entity.Clothes;
import cn.smartrick.entity.User;
import cn.smartrick.mapper.ClothesMapper;
import cn.smartrick.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.Clock;

/**
 * @Date: 2021/12/25
 * @Author: SmartRick
 * @Description: TODO
 */
@Service
public class ClothesService {
    @Autowired
    private ClothesMapper clothesMapper;

    @DataSource(DataSourceType.SECOND)
    public Clothes getById(Integer id) {
        return clothesMapper.selectById(id);
    }

    @DataSource(DataSourceType.SECOND)
    public boolean addClothes(Clothes clothes) {
        return clothesMapper.insert(clothes) == 1;
    }

    @DataSource(DataSourceType.SECOND)
    public boolean modifyClothes(Clothes clothes) {
        return clothesMapper.updateById(clothes) == 1;
    }
}
