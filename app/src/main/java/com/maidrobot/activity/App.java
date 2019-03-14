package com.maidrobot.activity;

import android.app.Activity;
import android.support.multidex.MultiDexApplication;

import qsbk.app.BoboSDK;
import qsbk.app.core.model.User;
import qsbk.app.core.provider.UserInfoProvider;

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        BoboSDK.init(this, new BoBoUserInfoProvider());
    }

    private static class BoBoUserInfoProvider extends UserInfoProvider {

        @Override
        public User getUser() {
            User user = new User();
            user.id = 204174713220556289l;
            user.name = "罗洪QQ";
            user.avatar = "http://avatar.app-remix.com/ELY2CYYVRMVPM0PZ.jpg?imageMogr2/format/jpg/thumbnail/300x300/auto-orient";
            return user;
        }

        @Override
        public void setToken(String token) {
            // 更新token
        }

        @Override
        public String getToken() {
            return "17deb7469f3b6ff5ee8b44334f0b2851";
        }

        @Override
        public void toLogin(Activity activity, int requestCode) {
            // 登录操作
        }

        @Override
        public void onLogout(String message) {
            // 退出登录操作
        }
    }

}
