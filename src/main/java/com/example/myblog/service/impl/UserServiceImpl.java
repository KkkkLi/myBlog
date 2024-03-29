package com.example.myblog.service.impl;

import com.example.myblog.entity.User;
import com.example.myblog.mapper.UserMapper;
import com.example.myblog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 86176
 * @since 2023-03-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
