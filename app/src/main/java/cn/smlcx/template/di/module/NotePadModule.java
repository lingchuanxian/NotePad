package cn.smlcx.template.di.module;

import cn.smlcx.template.mvp.model.NotePadListModel;
import cn.smlcx.template.mvp.presenter.NotePadListPresenter;
import cn.smlcx.template.mvp.view.ViewContract;
import dagger.Module;
import dagger.Provides;

/**
 * Created by fjlcx on 6/6/17.
 */
@Module
public class NotePadModule {
    private ViewContract.NotePadListView mView;

    public NotePadModule(ViewContract.NotePadListView view) {
        this.mView = view;
    }
    @Provides
    public NotePadListPresenter getNewsListPresenter(NotePadListModel model){
        return new NotePadListPresenter(model,mView);
    }

    @Provides
    public NotePadListModel getNotePadListModel(){
        return new NotePadListModel();
    }

}
