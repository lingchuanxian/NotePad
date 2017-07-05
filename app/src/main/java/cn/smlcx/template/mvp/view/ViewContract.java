package cn.smlcx.template.mvp.view;

import java.util.List;

import cn.smlcx.template.base.BaseView;
import cn.smlcx.template.bean.AppVersion;

/**
 * Created by lcx on 2017/6/5.
 */

public interface ViewContract {
	interface NewsListView extends BaseView {
		void success(int totlaPage,List<?> list);
		void fail(String msg);
	}
	interface NotePadListView extends BaseView {
		void success(int totlaPage,List<?> list);
		void fail(String msg);
	}
	interface UpdateNotePadView extends BaseView {
		void success();
		void fail(String msg);
	}
	interface GetLaseVersionView extends BaseView {
		void success(AppVersion appVersion);
		void fail(String msg);
	}
}
