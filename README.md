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
    api 'com.liulishuo.okdownload:okdownload:1.0.5'
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
        // 当前登录用户信息
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
        return "当前登录用户的TOKEN";
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

### 3.接入微信支付
- 将 WXPayEntryActivity extends qsbk.app.pay.wxapi.WXPayEntryActivity 
- 发起微信支付前需要设置APP_ID. PayConstants.WECHAT_APP_ID = "wechat_app_id";
- 在 WXPayEntryActivity 的 handleResp 方法处理自己的微信支付响应
- 删除支付宝和微信的jar包（alipaySdk-20180601.jar，libammsdk.jar）


### 4.代码混淆
如果你启用了混淆，请在你的proguard-rules.pro中加入如下代码：

```
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-dontoptimize
-ignorewarnings

-keep class qsbk.app.** {
    *;
}
-dontwarn qsbk.app.**


-keepattributes SourceFile,LineNumberTable

# filedownloader uses okhttp3-lib, so need add below proguard rules.
-dontwarn okhttp3.*
-dontwarn okio.**
-dontwarn javax.annotation.**

-dontwarn com.liulishuo.okdownload.**
-keep class com.liulishuo.okdownload.**{*;}

#################
-keep class com.squareup.** { *; }
-dontwarn com.squareup.**
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-dontwarn **.R$*
-dontwarn **.R
-dontwarn com.facebook.**
-keep class com.facebook.** { *; }
-keep class com.android.volley.** { *; }
-keep class cz.msebera.android.** { *; }
-keep class org.adw.library.** { *; }
-keep class org.greenrobot.eventbus.** { *; }

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

## agora
-keep class io.agora.** {*;}

## msgpack
-keep class com.fasterxml.jackson.** {*;}
-keep class org.msgpack.** {*;}

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.** { *; }

##---------------End: proguard configuration for Gson  ----------

# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep public class * implements java.io.Serializable {*;}

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

-dontwarn okio.**
-dontwarn javax.annotation.**

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keep class android.support.**{*;}
-keep class com.android.support.**{*;}


-keep public class * extends android.view.View
-keep public class * extends android.app.Activity
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-keep class com.tencent.mm.opensdk.** {
    *;
}

-keep class com.tencent.wxop.** {
    *;
}

-keep class com.tencent.mm.sdk.** {
    *;
}

# MTA
-keep class com.tencent.stat.** {*;}
-keep class com.tencent.mid.** {*;}
-keep class org.json.** {*;}
```


