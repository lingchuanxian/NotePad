package cn.smlcx.template.mvp.presenter;

import android.util.Log;

import javax.inject.Inject;

import cn.smlcx.template.base.BasePresenter;
import cn.smlcx.template.bean.HttpResult;
import cn.smlcx.template.di.scope.ActivityScope;
import cn.smlcx.template.mvp.model.UpdateNotePadModel;
import cn.smlcx.template.mvp.view.ViewContract;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by lcx on 2017/6/6.
 */
@ActivityScope
public class UpdateNotePadPresenter extends BasePresenter<UpdateNotePadModel,ViewContract.UpdateNotePadView>{
	private Subscription subscribe;
	@Inject
	public UpdateNotePadPresenter(UpdateNotePadModel model, ViewContract.UpdateNotePadView view) {
		this.mModel = model;
		this.mView = view;
	}

	public void updateNotePadList(String data) {
		subscribe = mModel.updateNotePad(data)
				.doOnSubscribe(new Action0() {
					@Override
					public void call() {
							mView.showLoding();
					}
				})
				.subscribe(new Subscriber<HttpResult>() {
					@Override
					public void onCompleted() {
						mView.hideLoding();
					}

					@Override
					public void onError(Throwable e) {
						mView.fail(e.getMessage());
					}

					@Override
					public void onNext(HttpResult result) {
						Log.e(TAG, "onNext: "+result.toString() );
						mView.success();
					}
				});
		addSubscribe(subscribe);
	}

}
