package cn.smlcx.template.di.module;

import cn.smlcx.template.mvp.model.UpdateNotePadModel;
import cn.smlcx.template.mvp.presenter.UpdateNotePadPresenter;
import cn.smlcx.template.mvp.view.ViewContract;
import dagger.Module;
import dagger.Provides;

/**
 * Created by fjlcx on 6/6/17.
 */
@Module
public class UpdateNotePadModule {
    private ViewContract.UpdateNotePadView mView;

    public UpdateNotePadModule(ViewContract.UpdateNotePadView view) {
        this.mView = view;
    }
    @Provides
    public UpdateNotePadPresenter getUpdateNotePadPresenter(UpdateNotePadModel model){
        return new UpdateNotePadPresenter(model,mView);
    }

    @Provides
    public UpdateNotePadModel getUpdateNotePadModel(){
        return new UpdateNotePadModel();
    }

}
