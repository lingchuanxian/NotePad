package cn.smlcx.template.di.module;

import cn.smlcx.template.mvp.model.GetLastVersionModel;
import cn.smlcx.template.mvp.presenter.GetLastVersionPresenter;
import cn.smlcx.template.mvp.view.ViewContract;
import dagger.Module;
import dagger.Provides;

/**
 * Created by fjlcx on 6/6/17.
 */
@Module
public class GetLastVersionModule {
    private ViewContract.GetLaseVersionView mView;

    public GetLastVersionModule(ViewContract.GetLaseVersionView view) {
        this.mView = view;
    }
    @Provides
    public GetLastVersionPresenter getGetLastVersionPresenter(GetLastVersionModel model){
        return new GetLastVersionPresenter(model,mView);
    }

    @Provides
    public GetLastVersionModel getGetLaseVersionModel(){
        return new GetLastVersionModel();
    }

}
