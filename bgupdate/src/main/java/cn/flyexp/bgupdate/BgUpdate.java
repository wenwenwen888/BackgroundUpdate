package cn.flyexp.bgupdate;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by Won on 2017/4/26.
 */

public class BgUpdate {

    private static int TYPE = 0x00;
    public static final int TYPE_DIALOG = 0x01;
    public static final int TYPE_NOTIFICATION = 0x02;

    // 要申请的权限
    private static String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    //下载链接
    private static String url = "";
    //下载文件保存路径
    private static String filePath = "";
    //后台服务对象
    private static DownloadService.MyBinder myBinder;
    private static Intent intent;

    private static ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //拿到后台服务代理对象
            myBinder = (DownloadService.MyBinder) iBinder;
            myBinder.startDownload(url, filePath, TYPE);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    /**
     * Dialog更新
     */
    public static void updateForDialog(Context context, String url, String filePath) {
        TYPE = TYPE_DIALOG;
        BgUpdate.url = url;
        BgUpdate.filePath = filePath;
        startDownload(context);
    }

    /**
     * Notification更新
     */
    public static void updateForNotification(Context context, String url, String filePath) {
        TYPE = TYPE_NOTIFICATION;
        BgUpdate.url = url;
        BgUpdate.filePath = filePath;
        startDownload(context);
    }

    /**
     * 开始下载
     */
    private static void startDownload(Context context) {
        if (myBinder != null) {
            myBinder.startDownload(url, filePath, TYPE);
        } else {
            // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(context, permissions[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 如果没有授予该权限，就去提示用户请求
                    showDialogTipUserRequestPermission(context);
                } else {
                    //已授权就开始下载
                    intent = new Intent(context, DownloadService.class);
                    context.startService(intent);
                    context.bindService(intent, serviceConnection, BIND_AUTO_CREATE);
                }
            } else {
                intent = new Intent(context, DownloadService.class);
                context.startService(intent);
                context.bindService(intent, serviceConnection, BIND_AUTO_CREATE);
            }
        }
    }

    // 提示用户该请求权限的弹出框
    private static void showDialogTipUserRequestPermission(final Context context) {
        new AlertDialog.Builder(context)
                .setTitle("存储权限不可用")
                .setMessage("请先开启存储权限；\n否则，您将无法正常更新版本")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission(context);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false).show();
    }

    // 开始提交请求权限
    private static void startRequestPermission(Context context) {
        ActivityCompat.requestPermissions((Activity) context, permissions, 321);
    }

    /**
     * 关闭服务
     */
    public static void closeService(Context context) {
        if (intent != null && serviceConnection != null) {
            context.unbindService(serviceConnection);
            intent = null;
            serviceConnection = null;
        }
    }

}
