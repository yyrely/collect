package com.wanli.collect.context;

import com.wanli.collect.model.entity.User;

/**
 * @author Hu
 * @date 2018/12/25 15:21
 */

public class RequestContext {

    public static ThreadLocal<Long> REQUEST_TIME = new InheritableThreadLocal<>();
    public static ThreadLocal<User> USER_INFO = new InheritableThreadLocal<>();

    private RequestContext() {}

    public static User getUserInfo() {
        return USER_INFO.get();
    }

    public static void setUserInfo(User users) {
        USER_INFO.set(users);
    }

    public static Long getRequestTime() {
        return REQUEST_TIME.get();
    }

    public static void setRequestTime(Long requestTime) {
        REQUEST_TIME.set(requestTime);
    }

    public static void clear() {
        REQUEST_TIME.remove();
        USER_INFO.remove();
    }


}
