package com.transsnet.aop;

import com.app.annotation.TimeTrace;

import java.util.Random;

/**
 * AOP 登录模块
 * Created by Administrator on 2017/10/13.
 */

public class AOPLoginUtils {
    private static final String TAG = "OOP";

    @TimeTrace(value = "登录")
    public static boolean Login(String userName, String passWord){

        try {
            Thread.sleep(new Random().nextInt(2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if ("张三".equals(userName) && "123456".equals(passWord)){
            return true;
        }else{
            return false;
        }
    }
}
