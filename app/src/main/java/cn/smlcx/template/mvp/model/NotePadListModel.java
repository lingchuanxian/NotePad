package cn.smlcx.template.mvp.model;

import cn.smlcx.template.api.ApiEngine;
import cn.smlcx.template.api.RetryWithDelay;
import cn.smlcx.template.api.RxHelper;
import cn.smlcx.template.base.BaseModel;
import cn.smlcx.template.bean.NotePad;
import cn.smlcx.template.bean.PageBean;
import cn.smlcx.template.di.scope.ActivityScope;
import rx.Observable;

/**
 * Created by lcx on 2017/6/5.
 */
@ActivityScope
public class NotePadListModel implements BaseModel{

	public Observable<PageBean<NotePad>> getNotePadList(int currentPage){
		return ApiEngine.getInstance().getApiService().getNotePad(currentPage)
				.compose(RxHelper.<PageBean<NotePad>>handleResult())
				.retryWhen(new RetryWithDelay(3,1000));
	}
}
