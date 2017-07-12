package cn.smlcx.template.api;

import cn.smlcx.template.bean.AppVersion;
import cn.smlcx.template.bean.HttpResult;
import cn.smlcx.template.bean.NotePad;
import cn.smlcx.template.bean.PageBean;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by lcx on 2017/5/4.
 */

public interface ApiService {
	@Headers({"Content-Type: application/json","Accept: application/json"})
	@GET("webservice/notepad/getNotePad.shtml")
	Observable<HttpResult<PageBean<NotePad>>> getNotePad(@Query("currentPage") int currentPage);

	@Headers({"Content-Type: application/json","Accept: application/json"})
	@POST("webservice/notepad/updateNotePad.shtml")
	Observable<HttpResult> updateNotePad(@Body String data);

	@Headers({"Content-Type: application/json","Accept: application/json"})
	@GET("webservice/appversion/getLastVersion.shtml")
	Observable<HttpResult<AppVersion>> getLastVersion();

	@Streaming
	@GET("downloadFile.shtml")
	Observable<ResponseBody> downloadFile(@Url String fileUrl);

	@Headers({"Content-Type: application/json","Accept: application/json"})
	@GET("webservice/notepad/deleteNotePad.shtml")
	Observable<HttpResult> deleteNotePad(@Query("npId") int npId);

}
