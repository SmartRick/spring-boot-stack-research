package cn.smartrick.controller;

import cn.smartrick.annotation.TimeLog;
import cn.smartrick.common.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date: 2021/12/13 14:35
 * @Author: SmartRick
 * @Description: TODO
 */
@RestController
public class HelloController {
    @TimeLog
    @RequestMapping("/hello")
    public ResponseDTO doHello(@RequestParam(required = false) String msg) {
        return ResponseDTO.succ("请求成功", msg);
    }
}
