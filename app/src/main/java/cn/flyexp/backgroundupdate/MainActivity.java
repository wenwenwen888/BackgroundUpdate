package cn.flyexp.backgroundupdate;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.flyexp.bgupdate.BgUpdate;

public class MainActivity extends AppCompatActivity {

    //测试下载的链接
    private String url = "http://183.56.150.169/imtt.dd.qq.com/16891/50172C52EBCCD8F9B0AD2B576DB7BA16.apk?mkey=58fedb8402e16784&f=9602&c=0&fsname=cn.flyexp_2.0.1_6.apk&csr=1bbd&p=.apk";
    private String filePath = Environment.getExternalStorageDirectory() + "/xiaohui.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_dialog, R.id.bt_notification})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_dialog:
                BgUpdate.updateForDialog(MainActivity.this, url, filePath);
                break;
            case R.id.bt_notification:
                BgUpdate.updateForNotification(MainActivity.this, url, filePath);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        BgUpdate.closeService(this);
        super.onDestroy();
    }
}
