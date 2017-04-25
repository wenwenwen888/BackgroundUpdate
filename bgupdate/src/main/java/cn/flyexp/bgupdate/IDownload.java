package cn.flyexp.bgupdate;

/**
 * Created by Won on 2017/4/25.
 */

public interface IDownload {

    /**
     * 开始下载
     */
    void srartDownload(String url);

    /**
     * 暂停下载
     */
    void pauseDownload();

    /**
     * 取消下载
     */
    void closeDownload();

    /**
     * 下载完成
     */
    void finishDownload();

}
