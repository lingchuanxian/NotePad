package cn.smlcx.template.mvp.model;

import cn.smlcx.template.api.ApiEngine;
import cn.smlcx.template.api.RetryWithDelay;
import cn.smlcx.template.base.BaseModel;
import cn.smlcx.template.di.scope.ActivityScope;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by lcx on 2017/6/5.
 */
@ActivityScope
public class DownloadFileModel implements BaseModel{

	public Observable<ResponseBody> downloadFile(String fileUrl){
		return ApiEngine.getInstance().getApiService().downloadFile(fileUrl)
				.retryWhen(new RetryWithDelay(3,1000));
	}
}
