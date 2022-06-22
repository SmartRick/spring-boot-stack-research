package cn.smartrick.config;

import cn.smartrick.entity.SysBackup;
import cn.smartrick.entity.SysRedisTemp;
import cn.smartrick.service.SysBackupServiceImpl;
import cn.smartrick.utils.MysqlKeyValueUtil;
import cn.smartrick.utils.RedisConnectionCheckTimer;
import cn.smartrick.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Date: 2022/6/17 14:54
 * @Author: SmartRick
 * @Description: TODO
 */
@Component
@Slf4j
public class MyApplicationRunner implements ApplicationRunner {
    @Autowired
    private RedisConnectionCheckTimer redisConnectionCheckTimer;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysBackupServiceImpl sysBackupService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("正在启动redis连接检测器");
        redisConnectionCheckTimer.start();

        SysRedisTemp sysRedisTemp = new SysRedisTemp();
        sysRedisTemp.setValue("123456");
        sysRedisTemp.setKey("dsafdsf");
////        redisUtil.set("fuck6", sysRedisTemp);
//        String key = "fuck12";
//        boolean set = redisUtil.set(key, sysRedisTemp);
//        System.out.println(set);
//        Object o = redisUtil.get(key);
//        System.out.println(o);

        List<SysBackup> sysBackups = sysBackupService.queryAll();
        System.out.println(sysBackups);
        SysBackup sysBackup = new SysBackup();
        sysBackup.setId(19);
        System.out.println(sysBackupService.queryById(sysBackup));

        sysBackupService.removeCache(19);
    }
}
