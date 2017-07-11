package cn.smlcx.template.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.smlcx.template.R;
import cn.smlcx.template.base.BaseActivity;
import cn.smlcx.template.bean.NotePad;
import cn.smlcx.template.di.component.DaggerUpdateNotePadComponent;
import cn.smlcx.template.di.module.UpdateNotePadModule;
import cn.smlcx.template.mvp.presenter.UpdateNotePadPresenter;
import cn.smlcx.template.mvp.view.ViewContract;
import cn.smlcx.template.utils.SoftKeyboardStateHelper;
import cn.smlcx.template.utils.ViewUtils;
import cn.smlcx.template.widget.GradationScrollView;
import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by lcx on 2017/6/9.
 */

public class NotePadEditActivity extends BaseActivity<UpdateNotePadPresenter> implements ViewContract.UpdateNotePadView {
	protected final String TAG = this.getClass().getSimpleName();
	@BindView(R.id.editor)
	RichEditor mEditor;
	@BindView(R.id.et_title)
	EditText mEtTitle;
	@BindView(R.id.sc_edit)
	GradationScrollView mScEdit;
	@BindView(R.id.tv_title_index)
	TextView mTvTitleIndex;
	@BindView(R.id.tv_content_index)
	TextView mTvContentIndex;
	@BindView(R.id.ll_edit_se)
	LinearLayout mLlEditSe;
	private int isChange = 0;
	private int actionSave = 0;
	private NotePad note;
	private int flag = 0;//1  add  2  edit
	Gson gson = new Gson();
	private MaterialDialog progressDialog;
	private InputMethodManager imm;

	@Override
	protected int attachLayoutRes() {
		return R.layout.activity_notepad_edit;
	}

	@Override
	protected void initViews() {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		mEditor.setEditorFontSize(17);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			note = (NotePad) getIntent().getExtras().getSerializable("note");
			flag = 2;
			if (note.getNpContent().equals("")) {
				mEditor.setPlaceholder("请输入内容");
			} else {
				mEditor.setOnInitialLoadListener(new RichEditor.AfterInitialLoadListener() {
					@Override
					public void onAfterInitialLoad(boolean isReady) {
						mEditor.setHtml(note.getNpContent());
					}
				});
			}
			if (!note.getNpTitle().equals("")) {
				mEtTitle.setText(note.getNpTitle());
			}
		} else {//新增
			flag = 1;
			mEtTitle.setHint("请输入标题");
			mEditor.setPlaceholder("请输入内容");
		}
		mEtTitle.addTextChangedListener(new EditChangedListener());
		mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
			@Override
			public void onTextChange(String text) {
				isChange = 1;
			}
		});
		mEditor.setKeepScreenOn(true);
		getToolBar().setTitle("编辑")
				.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						switch (item.getItemId()) {
							case R.id.save:
								actionSave = 1;
								save();
								break;
						}
						return false;
					}
				})
				.setNavigationOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						confirmFinish();
					}
				});


		progressDialog = new MaterialDialog.Builder(mContext)
				.title("正在保存")
				.content("请稍候...")
				.progress(true, 0)
				.cancelable(false)
				.build();

		mScEdit.setScrollViewListener(new GradationScrollView.ScrollViewListener() {
			@Override
			public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
				if (y >= (mTvTitleIndex.getHeight() + mEtTitle.getHeight())) {
					getToolBar().setTitle(mEtTitle.getText().toString());
				} else {
					getToolBar().setTitle("编辑");
				}
			}
		});
	}

	@Override
	protected void initData() {
		SoftKeyboardStateHelper softKeyboardStateHelper = new SoftKeyboardStateHelper(findViewById(R.id.sc_edit));
		softKeyboardStateHelper.addSoftKeyboardStateListener(new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
			@Override
			public void onSoftKeyboardOpened(int keyboardHeightInPx) {
				ViewUtils.setMargins(mScEdit, 0, 0, 0, keyboardHeightInPx);
			}

			@Override
			public void onSoftKeyboardClosed() {
				ViewUtils.setMargins(mScEdit, 0, 0, 0, 0);
			}
		});

	}

	@Override
	protected void initInject() {
		DaggerUpdateNotePadComponent.builder()
				.updateNotePadModule(new UpdateNotePadModule(this))
				.build()
				.inject(this);
	}

	public void save() {
		progressDialog.show();
		NotePad np;
		if (flag == 2) {
			np = new NotePad(note.getNpId(), mEtTitle.getText().toString(), mEditor.getHtml());
		} else {
			np = new NotePad(0, mEtTitle.getText().toString(), mEditor.getHtml());
		}
		mPresenter.updateNotePadList(gson.toJson(np));
	}

	@Override
	public void success() {
		if (actionSave == 1) {
			progressDialog.dismiss();
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					new MaterialDialog.Builder(mContext)
							.title("提示")
							.content("保存成功")
							.autoDismiss(true)
							.cancelable(true)
							.show();
				}
			});
			isChange = 0;
		} else {
			finish();
		}

	}

	@Override
	public void fail(String msg) {
		progressDialog.dismiss();
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				new MaterialDialog.Builder(mContext)
						.title("提示")
						.content("保存失败")
						.autoDismiss(true)
						.show();
			}
		});
	}

	/**
	 * 创建菜单
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_sec, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		confirmFinish();
	}

	public void confirmFinish() {
		if (isChange == 1) {
			new MaterialDialog.Builder(mContext)
					.title("提示")
					.content("是否保存已修改的内容？")
					.positiveText("确定")
					.onPositive(new MaterialDialog.SingleButtonCallback() {
						@Override
						public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
							save();
						}
					})
					.negativeText("取消")
					.onNegative(new MaterialDialog.SingleButtonCallback() {
						@Override
						public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
							dialog.dismiss();
							finish();
						}
					})
					.cancelable(false)
					.show();
		} else {
			finish();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO: add setContentView(...) invocation
		ButterKnife.bind(this);
	}

	private class EditChangedListener implements TextWatcher {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			isChange = 1;
		}
	}
}
