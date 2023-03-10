package com.example.seckilldemo.config;

import com.example.seckilldemo.pojo.User;

/**
 * @author: LG
 * @date 2022/3/9 4:49 下午
 * @ClassName: UserContext
 */
public class UserContext {

    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static void setUser(User tUser) {
        userThreadLocal.set(tUser);
    }

    public static User getUser() {
        return userThreadLocal.get();
    }
}
