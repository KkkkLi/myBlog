package com.example.myblog.controller;


import com.example.myblog.common.Result;
import com.example.myblog.entity.User;
import com.example.myblog.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 86716

 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

//    @RequiresAuthentication//对登陆进行拦截
    @GetMapping("/{id}")
    public Result test(@PathVariable("id") Long id) {
        User user= userService.getById(id);
        return Result.succ(200,"操作成功",user);
    }

    @PostMapping("/save")
    public Result testUser(@Validated @RequestBody User user) {
        return Result.succ(user);
    }

}
