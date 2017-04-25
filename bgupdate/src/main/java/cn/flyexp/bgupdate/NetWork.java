package cn.flyexp.bgupdate;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Won on 2017/4/20.
 */

public class NetWork {

    private static DownloadApi downloadApi;

    public static DownloadApi getApi() {
        if (downloadApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://183.56.150.169")
                    .addConverterFactory(GsonConverterFactory.create())//使用工厂模式创建Gson的解析器
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            downloadApi = retrofit.create(DownloadApi.class);
        }
        return downloadApi;
    }

}
