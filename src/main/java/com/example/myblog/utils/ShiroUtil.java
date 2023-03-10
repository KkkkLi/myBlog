package com.example.myblog.utils;


import com.example.myblog.shior.AccountProfile;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {
    public static AccountProfile getProfile(){

        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}

