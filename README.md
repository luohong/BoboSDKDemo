## 啵啵SDK集成文档

### 1.添加依赖
Gradle编译环境（AndroidStudio）
- 拷贝BoboSDK.aar到App的lib目录下
- 在App级别的build.gradle引入BoboSDK.aar和其他包（易于控制版本和去重）

> compile(name: 'BoboSDK', ext: 'aar')
> or
> implementation fileTree(dir: 'libs', include: ['*.jar','*.aar'])

``` 
    api 'com.android.support:support-v4:28.0.0'
    api 'com.android.support:appcompat-v7:28.0.0'
    api 'com.android.support.constraint:constraint-layout:1.1.3'
    api 'com.android.support:gridlayout-v7:28.0.0'
    api 'com.android.support:recyclerview-v7:28.0.0'
    
    api 'cz.msebera.android:httpclient:4.4.1.2'
    api 'org.msgpack:jackson-dataformat-msgpack:0.8.12'
    api 'org.adw.library:discrete-seekbar:1.0.1'
    api 'org.greenrobot:eventbus:3.0.0'
    api 'com.facebook.fresco:drawee-volley:1.9.0'
    api 'com.facebook.fresco:fresco:1.9.0'
    api 'com.facebook.fresco:animated-webp:1.9.0'
    api 'com.google.code.gson:gson:2.8.5'
    api 'com.liulishuo.filedownloader:library:1.7.6'
    api 'com.kaopiz:kprogresshud:1.2.0'
    api 'com.squareup.okhttp3:okhttp:3.11.0'
    api 'com.github.yyued:SVGAPlayer-Android:2.3.0' 
```

### 2.初始化SDK
> BoboSDK.init(Context context, UserInfoProvider provider);

- Context 应用上下文
- UserInfoProvider 用户信息提供者，用于获取API请求访问的token和基本用户信息，以及基本登录和退出操作。示例如下：

```java
private static class BoBoUserInfoProvider extends UserInfoProvider {

    @Override
    public User getUser() {
        User user = new User();
        user.id = 204174713220556289l;
        user.name = "test";
        user.avatar = "http://avatar.app-remix.com/ELY2CYYVRMVPM0PZ.jpg";
        return user;
    }

    @Override
    public void setToken(String token) {
        // 更新token
    }

    @Override
    public String getToken() {
        return "9219e18a43d967e4670c26e041105276";
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
```

### 3.代码混淆
如果你启用了混淆，请在你的proguard-rules.pro中加入如下代码：

```
-keep class qsbk.app.** {
    *;
}
-dontwarn qsbk.app.**
​
-keepattributes SourceFile,LineNumberTable

# filedownloader uses okhttp3-lib, so need add below proguard rules.
-dontwarn okhttp3.*
-dontwarn okio.**
-dontwarn javax.annotation.**

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

## 声网连麦
-keep class io.agora.** {*;}

## MessagePack
-keep class com.fasterxml.jackson.** {*;}
-keep class org.msgpack.** {*;}

## Facebook fresco
# Keep our interfaces so they can be used by other ProGuard rules.
# See http://sourceforge.net/p/proguard/bugs/466/
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}
```