package cn.smlcx.template.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.smlcx.template.R;
import cn.smlcx.template.base.BaseActivity;
import cn.smlcx.template.bean.AppVersion;
import cn.smlcx.template.bean.NotePad;
import cn.smlcx.template.di.component.DaggerHomeComponent;
import cn.smlcx.template.di.module.DeleteNotePadModule;
import cn.smlcx.template.di.module.GetLastVersionModule;
import cn.smlcx.template.di.module.NotePadModule;
import cn.smlcx.template.global.TemplateApplication;
import cn.smlcx.template.mvp.presenter.DeleteNotePadPresenter;
import cn.smlcx.template.mvp.presenter.GetLastVersionPresenter;
import cn.smlcx.template.mvp.presenter.NotePadListPresenter;
import cn.smlcx.template.mvp.view.ViewContract;
import cn.smlcx.template.ui.adapter.NotePadListAdapter;
import cn.smlcx.template.utils.AppUtil;

/**
 * Created by lcx on 2017/6/5.
 */

public class HomeActivity extends BaseActivity<NotePadListPresenter> implements ViewContract.NotePadListView, ViewContract.GetLaseVersionView,ViewContract.DeleteNotePadView,View.OnClickListener {
	protected final String TAG = this.getClass().getSimpleName();
	@BindView(R.id.rlv_news)
	RecyclerView mRlvNews;
	@BindView(R.id.swiperefresh)
	SwipeRefreshLayout mSwiperefresh;
	private NotePadListAdapter mAdapter;
	private List<NotePad> mDatas = new ArrayList<NotePad>();
	private int mCurrentPage = 1;//当前页码
	private int mTotalPage;//总页码
	private int flag = 0;//0 -- 第一次加载或者刷新  1 -- 加载更多
	String currentVersion;
	@Inject
	GetLastVersionPresenter mGetLastVersionPresenter;
	@Inject
	DeleteNotePadPresenter mDeleteNotePadPresenter;

	@Override
	protected int attachLayoutRes() {
		return R.layout.activity_home;
	}

	@Override
	protected void initViews() {
		/* 设置toolBar */
		getToolBar().setTitle("Loving Fang")
				.setDisplayHomeAsUpEnabled(false)
				.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						switch (item.getItemId()) {
							case R.id.exit:
								new MaterialDialog.Builder(mContext)
										.title("退出")
										.content("您确定要退出吗？")
										.positiveText("确定")
										.onPositive(new MaterialDialog.SingleButtonCallback() {
											@Override
											public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
												TemplateApplication.getInstance().getActivityManager().finishAllActivity();
											}
										})
										.negativeText("取消")
										.onNegative(new MaterialDialog.SingleButtonCallback() {
											@Override
											public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
												dialog.dismiss();
											}
										})
										.cancelable(false)
										.show();
								break;
						}
						return false;
					}
				});
		mAdapter = new NotePadListAdapter(mDatas);
		mRlvNews.setAdapter(mAdapter);
		mAdapter.openLoadAnimation();
		/* item点击事件 */
		mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				Intent intent = new Intent(HomeActivity.this, NotePadEditActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("note", (NotePad) adapter.getItem(position));
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		/* 刷新操作 */
		mSwiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				flag = 0;
				mCurrentPage = 1;
				mPresenter.getNotePadList(mCurrentPage, false);
			}
		});
		/* 加载更多 */
		mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
			@Override
			public void onLoadMoreRequested() {
				mRlvNews.postDelayed(new Runnable() {
					@Override
					public void run() {
						if (mCurrentPage >= mTotalPage) {//数据全部加载完毕
							mAdapter.loadMoreEnd();
						} else {//数据未加载完，继续请求加载
							flag = 1;
							mCurrentPage += 1;
							mPresenter.getNotePadList(mCurrentPage, false);
						}
					}
				}, 1000);
			}
		});

		mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
				final int npId = ((NotePad) adapter.getItem(position)).getNpId();
				new MaterialDialog.Builder(mContext)
						.title("删除")
						.content("您确定要删除该记录吗？")
						.positiveText("确定")
						.onPositive(new MaterialDialog.SingleButtonCallback() {
							@Override
							public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
								mDeleteNotePadPresenter.deleteNotePad(npId);
								mAdapter.remove(position);
							}
						})
						.negativeText("取消")
						.onNegative(new MaterialDialog.SingleButtonCallback() {
							@Override
							public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
								dialog.dismiss();
							}
						})
						.cancelable(false)
						.show();
				return false;
			}
		});

		currentVersion = AppUtil.getAppVersionName(mContext);
	}

	@Override
	protected void initInject() {
		DaggerHomeComponent.builder()
				.notePadModule(new NotePadModule(this))
				.getLastVersionModule(new GetLastVersionModule(this))
				.deleteNotePadModule(new DeleteNotePadModule(this))
				.build()
				.inject(this);
	}

	@Override
	protected void initData() {
		mPresenter.getNotePadList(mCurrentPage, true);
		mGetLastVersionPresenter.getLastVersion();
	}

	@Override
	public void success(int totalPage, List<?> list) {
		mTotalPage = totalPage;
		if (flag == 0) {//第一次加载或者刷新
			mDatas.clear();
			if (list.size() == 0) {
				showNonData("当前暂无数据。");
			}
			mSwiperefresh.setRefreshing(false);
			mRlvNews.scrollToPosition(0);
		} else if (flag == 1) {//加载更多
			mAdapter.loadMoreComplete();
		}
		mAdapter.addData((List<NotePad>) list);
	}

	@Override
	public void GetLaseVersionsuccess(AppVersion appVersion) {
		Log.d("AppVersion:", appVersion.toString());
	}

	@Override
	public void GetLaseVersionfail(String msg) {

	}

	@Override
	public void fail(String msg) {
		showErr(msg);
	}


	@Override
	public void DeleteNotePadSuccess() {
		Log.e(TAG, "DeleteNotePadSuccess: 删除成功");
	}

	@Override
	public void DeleteNotePadFail(String msg) {
		Log.e(TAG, "DeleteNotePadSuccess: 删除失败");
	}

	/**
	 * 创建菜单
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		flag = 0;
		mCurrentPage = 1;
		mPresenter.getNotePadList(mCurrentPage, false);
	}

	/* 按返回键后台运行程序 */
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.iv_add:
				startActivity(NotePadEditActivity.class);
				break;
		}
	}

}
