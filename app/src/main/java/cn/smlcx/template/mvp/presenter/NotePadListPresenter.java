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

/**
 * Created by lcx on 2017/6/6.
 */
@ActivityScope
public class NotePadListPresenter extends BasePresenter<NotePadListModel,ViewContract.NotePadListView>{
	private Subscription subscribe;
	@Inject
	public NotePadListPresenter(NotePadListModel model, ViewContract.NotePadListView view) {
		this.mModel = model;
		this.mView = view;
	}

	public void getNotePadList(int currentPage) {
		subscribe = mModel.getNotePadList(currentPage)
				.subscribe(new Subscriber<PageBean<NotePad>>() {
					@Override
					public void onCompleted() {
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
