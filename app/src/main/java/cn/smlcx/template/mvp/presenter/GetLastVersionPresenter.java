package cn.smlcx.template.mvp.presenter;

import javax.inject.Inject;

import cn.smlcx.template.base.BasePresenter;
import cn.smlcx.template.bean.NotePad;
import cn.smlcx.template.bean.PageBean;
import cn.smlcx.template.di.scope.ActivityScope;
import cn.smlcx.template.mvp.model.NotePadListModel;
import cn.smlcx.template.mvp.view.ViewContract;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by lcx on 2017/6/6.
 */
@ActivityScope
public class GetLastVersionPresenter extends BasePresenter<NotePadListModel,ViewContract.NotePadListView>{
	private Subscription subscribe;
	@Inject
	public GetLastVersionPresenter(NotePadListModel model, ViewContract.NotePadListView view) {
		this.mModel = model;
		this.mView = view;
	}

	public void getNotePadList(int currentPage, final boolean isFirst) {
		subscribe = mModel.getNotePadList(currentPage)
				.doOnSubscribe(new Action0() {
					@Override
					public void call() {
						if(isFirst){
							mView.showLoding();
						}
					}
				})
				.subscribe(new Subscriber<PageBean<NotePad>>() {
					@Override
					public void onCompleted() {
						mView.hideLoding();
					}

					@Override
					public void onError(Throwable e) {
						mView.fail(e.getMessage());
					}

					@Override
					public void onNext(PageBean<NotePad> result) {
						mView.success(result.getPageNum(),result.getList());
					}
				});
		addSubscribe(subscribe);
	}

}
