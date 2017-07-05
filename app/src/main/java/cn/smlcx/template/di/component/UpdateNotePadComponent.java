package cn.smlcx.template.di.component;

import cn.smlcx.template.di.module.UpdateNotePadModule;
import cn.smlcx.template.ui.activity.NotePadEditActivity;
import dagger.Component;

/**
 * Created by lcx on 6/6/17.
 */
@Component(modules = UpdateNotePadModule.class)
public interface UpdateNotePadComponent {
    void inject(NotePadEditActivity activity);
}
