package com.caishi.aop;

import com.caishi.model.entity.TUser;

/**
 * @Author CYC
 * @Version 1.0.0
 * @Description
 **/
public class LoginAopParms {

    private static ThreadLocal<TUser> localUser = new ThreadLocal<>();

    public static TUser getLocalUser() {
        return localUser.get();
    }

    public static void setLocalUser(TUser user) {
        localUser.set(user);
    }

}
