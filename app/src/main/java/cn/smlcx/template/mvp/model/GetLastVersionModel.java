package cn.smlcx.template.mvp.model;

import cn.smlcx.template.api.ApiEngine;
import cn.smlcx.template.api.RetryWithDelay;
import cn.smlcx.template.api.RxHelper;
import cn.smlcx.template.base.BaseModel;
import cn.smlcx.template.bean.AppVersion;
import cn.smlcx.template.di.scope.ActivityScope;
import rx.Observable;

/**
 * Created by lcx on 2017/6/5.
 */
@ActivityScope
public class GetLastVersionModel implements BaseModel{

	public Observable<AppVersion> getLastVersion(){
		return ApiEngine.getInstance().getApiService().getLastVersion()
				.retryWhen(new RetryWithDelay(3,1000))
				.compose(RxHelper.<AppVersion>handleResult());
	}
}
