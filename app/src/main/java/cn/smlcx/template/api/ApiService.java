package cn.smlcx.template.api;

import cn.smlcx.template.bean.HttpResult;
import cn.smlcx.template.bean.NotePad;
import cn.smlcx.template.bean.PageBean;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lcx on 2017/5/4.
 */

public interface ApiService {
	@Headers({"Content-Type: application/json","Accept: application/json"})
	@GET("notepad/getNotePad.shtml")
	Observable<HttpResult<PageBean<NotePad>>> getNotePad(@Query("currentPage") int currentPage);

}
