package com.example.myblog.service.impl;

import com.example.myblog.entity.Blog;
import com.example.myblog.mapper.BlogMapper;
import com.example.myblog.service.BlogService;
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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
