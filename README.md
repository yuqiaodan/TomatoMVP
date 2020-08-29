# TomatoMVP
感谢开源 基于门心叼龙大佬的MVP架构项目
FlyTour V5.0.0进行部分修改精简 
原地址：https://github.com/mxdldev/android-mvp-mvvm-flytour

本人的MVP架构 这是这是一个非常好且单纯的MVP例子 后续会添加各种实用的工具类和网络请求框架等

##### 一.导入

使用方法 首先clone整个项目或下载zip

将`versions.gradle`复制到你的根目录下//这个其实就是管理依赖的 有些依赖版本需要更新 可以在这里手动修改

并且在项目的build.gradle中添加如下语句：

```
   buildscript {
   apply from: 'versions.gradle'
   addRepos(repositories)
   ......
   }
```

然后使用在AS中 File-->New-->import Module

选择`lib_common`

![image-20200828153326319](C:\Users\TestBird\AppData\Roaming\Typora\typora-user-images\image-20200828153326319.png)

build完成后在app的build.gradle文件中引入

```
implementation project(":lib_common")
```

就可以了，此项目的给了一个非常简单的例子，清晰的给给出MVP架构的用法

目前功能非常简单，当然后续会继续添加功能，也会在这里更新，再次感谢门心叼龙

使用注意事项：

1.在使用BaseActivity前请务必将 Application继承于BaseApplication

2.app的Theme需要设置为NoActionBar

```
<style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
```



