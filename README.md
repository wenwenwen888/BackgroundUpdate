# BackgroundUpdate
后台更新APP(两句代码实现)

先看预览图 : 

(1)调用Dialog更新对话框效果
</br></br>
<img src="https://github.com/wenwenwen888/BackgroundUpdate/blob/master/preview/1.png" width="30%" height="30%">
</br></br>
(2)调用Notification更新效果
</br></br>
<img src="https://github.com/wenwenwen888/BackgroundUpdate/blob/master/preview/2.png" width="30%" height="30%">


前言
-------
1,使用了retorfit2 + rxjava做的下载库

2,apk下载完毕会自动跳到安装界面,下载过程可取消

3,需要修改更多内容可以下载library自行修改

4,不妥之处或BUG请Issues指出,谢谢


Usage
--------

With Gradle:
```groovy
  compile 'com.wenwenwen888:bgupdate:1.0.1'
```


How to use
--------
第一句 , 调用下载:
* (a.调用Dialog对话框下载
```java
  BgUpdate.updateForDialog(context, url, filePath);
```
* (b.调用Notification下载
```java
  BgUpdate.updateForNotification(context , url, filePath);
```
* (c.url即为下载apk的链接,下面为filepath示例
```java
  String filePath = Environment.getExternalStorageDirectory() + "/xxx.apk";
```
第二句 , 在onDestroy()的时候关闭服务:
```java
 BgUpdate.closeService(context);
```

End (。・`ω´・)
--------
