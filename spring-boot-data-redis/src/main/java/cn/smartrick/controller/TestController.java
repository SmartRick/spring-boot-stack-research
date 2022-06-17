package cn.smartrick.controller;

import cn.smartrick.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Date: 2022/6/17 13:52
 * @Author: SmartRick
 * @Description: TODO
 */
@Controller
public class TestController {
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/api/test")
    @ResponseBody
    public String test() {
        redisUtil.get("123");
        return "正常使用";
    }
}
