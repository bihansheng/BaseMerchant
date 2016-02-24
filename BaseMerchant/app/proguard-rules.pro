#指定代码的压缩级别
-optimizationpasses 5

#包名不混合大小写
-dontusemixedcaseclassnames

#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses

#预校验
-dontpreverify

#混淆时是否记录日志
-verbose

#混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#不混淆内部匿名类
-keepattributes Exceptions,InnerClasses

#忽略警告
-ignorewarnings

#保护注解
-keepattributes *Annotation*
-keepattributes Signature

#保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.v13.**
-keep public class * extends org.apache.http.**
-keep public class com.android.vending.licensing.ILicensingService

#
-keepclassmembers class com.igrs.dlna.activity.webviewActivity$InJavaScriptLocalObj {
    public void showSource(java.lang.String,java.lang.String);
    public void showTitle(java.lang.String);
}

#不混淆枚举
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#保持 ButterKnife 不混淆
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
-keepnames class * { @butterknife.InjectView *;}

#不混淆eventBus
-keepclassmembers class ** {
    public void onEvent*(**);
}
-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#保护Volley不被混淆
-keep class com.android.volley.** {*;}
-keep class com.android.volley.toolbox.** {*;}
-keep class com.android.volley.Response$* { *; }
-keep class com.android.volley.Request$* { *; }
-keep class com.android.volley.RequestQueue$* { *; }
-keep class com.android.volley.toolbox.HurlStack$* { *; }
-keep class com.android.volley.toolbox.ImageLoader$* { *; }

#保护GSON不被混淆
-keepattributes Signature
-dontwarn com.google.gson.**
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *; }
-keep class com.google.gson.JsonObject { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.heshidai.cdzmerchant.entity.**{*;}

#保护 imageLoader 不混淆
-dontwarn com.nostra13.universalimageloader.**
-keep class com.nostra13.universalimageloader.** { *; }

#保持native方法不被混淆
-keepclasseswithmembernames class * { native <methods>;}

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

#保留自定义View
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
#保留自定义LinearLayout
-keep public class * extends android.widget.LinearLayout {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#保持类成员
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

#保持Parcelable不被混淆
-keep class * implements android.os.Parcelable  {
    public static final android.os.Parcelable$Creator *;
}

#保持Serializable不被混淆
-keep public class * implements java.io.Serializable {
    public *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#保留极光推送
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class com.google.protobuf.** {*;}

#apk 包内所有 class 的内部结构
-dump class_files.txt

#未混淆的类和成员
-printseeds seeds.txt

#列出从 apk 中删除的代码
-printusage unused.txt

#混淆前后的映射
-printmapping mapping.txt

#打包后清除日志
-assumenosideeffects  class com.heshidai.cdzmerchant.utils.log.HLog {
    *;
}

