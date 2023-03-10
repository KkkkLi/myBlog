package com.example.myblog.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: quest
 * @Description:
 * @FileName: Result
 */
@Data
public class Result implements Serializable {
    private int code; //200是正常 400表示异常
    private String msg;
    private Object data;//返回数据
    //成功
    public static Result succ( Object data){

        return succ(200,"操作成功",data);
    }
    //成功
    public static Result succ(int code,String msg,Object data){
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
    //失败
    public static Result fail(String msg){

        return fail(400,msg,null);
    }
    //失败
    public static Result fail(String msg,Object data){

        return fail(400,msg,data);
    }
    //失败
    public static Result fail(int code,String msg,Object data){
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

}

