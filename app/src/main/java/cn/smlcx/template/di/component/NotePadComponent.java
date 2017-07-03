package cn.smlcx.template.di.component;

import cn.smlcx.template.di.module.NotePadModule;
import cn.smlcx.template.ui.activity.HomeActivity;
import dagger.Component;

/**
 * Created by lcx on 6/6/17.
 */
@Component(modules = NotePadModule.class)
public interface NotePadComponent {
    void inject(HomeActivity activity);
}
