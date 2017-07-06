package cn.smlcx.template.di.module;

import cn.smlcx.template.mvp.model.DeleteNotePadModel;
import cn.smlcx.template.mvp.presenter.DeleteNotePadPresenter;
import cn.smlcx.template.mvp.view.ViewContract;
import dagger.Module;
import dagger.Provides;

/**
 * Created by fjlcx on 6/6/17.
 */
@Module
public class DeleteNotePadModule {
    private ViewContract.DeleteNotePadView mView;

    public DeleteNotePadModule(ViewContract.DeleteNotePadView view) {
        this.mView = view;
    }
    @Provides
    public DeleteNotePadPresenter getDeleteNotePadPresenter(DeleteNotePadModel model){
        return new DeleteNotePadPresenter(model,mView);
    }

    @Provides
    public DeleteNotePadModel getDeleteNotePadModel(){
        return new DeleteNotePadModel();
    }

}
