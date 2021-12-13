package cn.smartrick.controller;

import cn.smartrick.common.dto.ResponseDTO;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Date: 2021/12/4
 * @Author: SmartRick
 * @Description: TODO
 */
@RestController
public class SecurityController {
    //角色要求
    @Secured("ROLE_USER")
    //前置置自定义表达式要求
    @PreAuthorize("hasAnyAuthority('sys:user') and hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    //后置自定义表达式要求
    @PostAuthorize("hasAnyAuthority('sys:user') and hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @RequestMapping("/hello")
    private ResponseDTO hello() {
        return ResponseDTO.succ("hello world");
    }
//    @RequestMapping("/user/login")
//    private ModelAndView login(@RequestParam("username") String username,@RequestParam("username") String password) {
//
//        return new ModelAndView("/login/success");
//    }


    @RequestMapping("/login/success")
    private ModelAndView success() {
        ModelAndView modelAndView = new ModelAndView("/success");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal);
        return modelAndView;
    }
}
