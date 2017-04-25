package cn.flyexp.backgroundupdate;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cn.flyexp.bgupdate.DownloadService;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    private DownloadService.MyBinder myBinder;

    private String url = "http://183.56.150.169/imtt.dd.qq.com/16891/50172C52EBCCD8F9B0AD2B576DB7BA16.apk?mkey=58fedb8402e16784&f=9602&c=0&fsname=cn.flyexp_2.0.1_6.apk&csr=1bbd&p=.apk";

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //拿到后台服务代理对象
            myBinder = (DownloadService.MyBinder) iBinder;
            myBinder.download(url);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start = (Button) findViewById(R.id.start);
        Button close = (Button) findViewById(R.id.close);

        intent = new Intent(MainActivity.this, DownloadService.class);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindService(intent , serviceConnection , BIND_AUTO_CREATE);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbindService(serviceConnection);
            }
        });

    }
}
