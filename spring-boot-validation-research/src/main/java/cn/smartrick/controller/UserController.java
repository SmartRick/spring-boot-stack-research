package cn.smartrick.controller;

import cn.smartrick.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Date: 2021/12/2 15:38
 * @Author: SmartRick
 * @Description: TODO
 */
@Controller
@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping
    public String add(@Valid @RequestBody User user) {
        return "参数校验完成" ;
    }
}
