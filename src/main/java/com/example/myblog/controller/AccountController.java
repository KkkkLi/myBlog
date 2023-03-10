package com.example.myblog.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.myblog.common.LoginDto;
import com.example.myblog.common.Result;
import com.example.myblog.entity.User;
import com.example.myblog.service.UserService;
import com.example.myblog.shior.JwtToken;
import com.example.myblog.utils.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import cn.hutool.core.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

/**
 * @author: quest
 * @Description: 登录接口
 * @FileName: AccountController
 */
@RestController
public class AccountController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    /**
     * 默认账号密码：admin / 111111
     */
    @CrossOrigin
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){
        System.out.println(loginDto.toString());
          User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        Assert.notNull(user,"用户不存在");
        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){
            return Result.fail("密码错误");
        }
//        如果验证成功，用id生产token
        String token = jwtUtils.generateToken(user.getId());
        System.out.println("token已生成："+token);
        //把token放到响应头里
        response.setHeader("Authorization",token);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        // 用户可以另一个接口
        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout(){
        System.out.println("准备退出");
        SecurityUtils.getSubject().logout();
        return Result.succ("注销成功");
    }

}

