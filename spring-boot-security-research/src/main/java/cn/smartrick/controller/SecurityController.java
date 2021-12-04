package cn.smartrick.controller;

import cn.smartrick.common.dto.ResponseDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date: 2021/12/4
 * @Author: SmartRick
 * @Description: TODO
 */
@RestController
public class SecurityController {

    @RequestMapping("/hello")
    private ResponseDTO hello() {
        return ResponseDTO.succ("hello world");
    }
}
