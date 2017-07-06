package cn.smlcx.template.mvp.presenter;

import javax.inject.Inject;

import cn.smlcx.template.base.BasePresenter;
import cn.smlcx.template.bean.HttpResult;
import cn.smlcx.template.di.scope.ActivityScope;
import cn.smlcx.template.mvp.model.DeleteNotePadModel;
import cn.smlcx.template.mvp.view.ViewContract;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by lcx on 2017/6/6.
 */
@ActivityScope
public class DeleteNotePadPresenter extends BasePresenter<DeleteNotePadModel,ViewContract.DeleteNotePadView>{
	private Subscription subscribe;
	@Inject
	public DeleteNotePadPresenter(DeleteNotePadModel model, ViewContract.DeleteNotePadView view) {
		this.mModel = model;
		this.mView = view;
	}

	public void deleteNotePad(int npId) {
		subscribe = mModel.deleteNotePad(npId)
				.subscribe(new Subscriber<HttpResult>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {
						mView.DeleteNotePadFail(e.getMessage());
					}

					@Override
					public void onNext(HttpResult result) {
						mView.DeleteNotePadSuccess();
					}
				});
		addSubscribe(subscribe);
	}

}
