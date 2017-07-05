package cn.smlcx.template.mvp.model;

import cn.smlcx.template.api.ApiEngine;
import cn.smlcx.template.api.RetryWithDelay;
import cn.smlcx.template.base.BaseModel;
import cn.smlcx.template.bean.HttpResult;
import cn.smlcx.template.di.scope.ActivityScope;
import rx.Observable;

/**
 * Created by lcx on 2017/6/5.
 */
@ActivityScope
public class UpdateNotePadModel implements BaseModel{

	public Observable<HttpResult> updateNotePad(String data){
		return ApiEngine.getInstance().getApiService().updateNotePad(data)
				.retryWhen(new RetryWithDelay(3,1000));
				//.compose(RxHelper.<HttpResult<NotePad>>handleResult());
	}
}
