package cn.smartrick.service;

import cn.smartrick.annotation.RedisCacheConfig;
import cn.smartrick.annotation.RedisCacheEvict;
import cn.smartrick.annotation.RedisCacheable;
import cn.smartrick.entity.SysBackup;
import cn.smartrick.mapper.SysBackupMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date: 2022/6/20 15:35
 * @Author: SmartRick
 * @Description: TODO
 */
@Service
@RedisCacheConfig(cacheName = "SYSBACK")
public class SysBackupServiceImpl {
    @Autowired
    private SysBackupMapper sysBackupMapper;

    @RedisCacheable(cacheName = "USER", key = "#root.methodName")
    public List<SysBackup> queryAll() {
        return sysBackupMapper.selectList(new QueryWrapper<>());
    }

    @RedisCacheable(key = "#param.sysBackup.id")
    public SysBackup queryById(SysBackup sysBackup) {
        return sysBackupMapper.selectById(sysBackup.getId());
    }

    @RedisCacheEvict(keys = "#param.id")
    public void removeCache(Integer id) {

    }

}
