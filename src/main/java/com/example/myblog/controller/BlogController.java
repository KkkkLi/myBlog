package com.example.myblog.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myblog.common.Result;
import com.example.myblog.entity.Blog;
import com.example.myblog.service.BlogService;
import com.example.myblog.utils.ShiroUtil;
import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.spi.CurrencyNameProvider;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 86716
 */
@RestController
public class BlogController {
    @Autowired
    BlogService blogService;

    /**
     * @Description: 查询所有blogs
     * @author: quest
     * @param
     * @return: com.example.myblog.common.Result
     */
    @GetMapping("/blogs")
    public Result getAllBlogs(@RequestParam(defaultValue = "1") Integer currentPage){
        System.out.println("page页号为："+currentPage);
//        分页
        Page page = new Page(currentPage, 5);
        IPage pageData= blogService.page(page,new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.succ(pageData);

    }


    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id){
        System.out.println("这个页面可能要被修改:"+id);
        Blog blog = blogService.getById(id);
        Assert.notNull(blog,"该博客已删除");
        return Result.succ(blog);
    }
/**
 * @Description: @RequiresAuthentication说明需要登录之后才能访问的接口
 * @author: quest
 * @param blog
 * @return: com.example.myblog.common.Result
 */
    @PostMapping("/blog/edit")
    @RequiresAuthentication
    public Result edit(@Validated @RequestBody Blog blog){
//        并没有直接拿前端传来的对象操作
        Blog temp =null;
//        如果有id就是编辑
        if (blog.getId()!=null){
            temp =blogService.getById(blog.getId());
            //用户只能编辑自己的文章
            Assert.isTrue(temp.getUserId().equals(ShiroUtil.getProfile().getId()), "没有权限编辑");
        }else {
//            不然就是发布
            temp=new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        //将blog的值赋给temp 忽略 id userid created status 引用于hutool
        BeanUtil.copyProperties(blog,temp,"id","userId", "created", "status");
        blogService.saveOrUpdate(temp);
        return Result.succ(null);
    }

    /**
     * @Description: 不知道为啥deleteMapping 报错权限有问题，前端token可以被拿到
     * @author: quest
     * @param id
     * @return: com.example.myblog.common.Result
     */
    @RequiresAuthentication
    @PostMapping("/blog/{id}")
    public Result delBlog(@PathVariable(name = "id") Long id){
        System.out.println("这个id将要删除：" +id);
        boolean b = blogService.removeById(id);
        if (b){
            return Result.succ("删除成功");
        }
        return Result.fail("失败");
    }
}
