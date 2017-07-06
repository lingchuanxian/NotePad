package cn.smlcx.template.mvp.presenter;

import javax.inject.Inject;

import cn.smlcx.template.base.BasePresenter;
import cn.smlcx.template.bean.AppVersion;
import cn.smlcx.template.di.scope.ActivityScope;
import cn.smlcx.template.mvp.model.GetLastVersionModel;
import cn.smlcx.template.mvp.view.ViewContract;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by lcx on 2017/6/6.
 */
@ActivityScope
public class GetLastVersionPresenter extends BasePresenter<GetLastVersionModel,ViewContract.GetLaseVersionView>{
	private Subscription subscribe;
	@Inject
	public GetLastVersionPresenter(GetLastVersionModel model, ViewContract.GetLaseVersionView view) {
		this.mModel = model;
		this.mView = view;
	}

	public void getLastVersion() {
		subscribe = mModel.getLastVersion()
				.doOnSubscribe(new Action0() {
					@Override
					public void call() {
							mView.showLoding();
					}
				})
				.subscribe(new Subscriber<AppVersion>() {
					@Override
					public void onCompleted() {
						mView.hideLoding();
					}

					@Override
					public void onError(Throwable e) {
						mView.GetLaseVersionfail(e.getMessage());
					}

					@Override
					public void onNext(AppVersion result) {
						mView.GetLaseVersionsuccess(result);
					}
				});
		addSubscribe(subscribe);
	}

}
