package cn.flyexp.bgupdate;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Won on 2017/4/25.
 */

public interface DownloadApi {

    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

}
