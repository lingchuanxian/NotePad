package cn.smlcx.template.ui.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import butterknife.BindView;
import cn.smlcx.template.R;
import cn.smlcx.template.base.BaseActivity;
import cn.smlcx.template.bean.NotePad;
import cn.smlcx.template.di.component.DaggerUpdateNotePadComponent;
import cn.smlcx.template.di.module.UpdateNotePadModule;
import cn.smlcx.template.mvp.presenter.UpdateNotePadPresenter;
import cn.smlcx.template.mvp.view.ViewContract;
import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by lcx on 2017/6/9.
 */

public class NotePadEditActivity extends BaseActivity<UpdateNotePadPresenter> implements ViewContract.UpdateNotePadView{
	protected final String TAG = this.getClass().getSimpleName();
	@BindView(R.id.editor)
	RichEditor mEditor;
	@BindView(R.id.et_title)
	EditText mEtTitle;
	private int isChange = 0;
	private int actionSave = 0;
	private int isFirst = 1;
	private NotePad note;
	Gson gson = new Gson();
	private MaterialDialog progressDialog;
	@Override
	protected int attachLayoutRes() {
		return R.layout.activity_notepad_edit;
	}

	@Override
	protected void initViews() {
		note = (NotePad) getIntent().getExtras().getSerializable("note");
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

		if (note.getNpContent().equals("")) {
			mEditor.setPlaceholder("请输入内容");
		} else {
			mEditor.setHtml(note.getNpContent());
		}
		if (!note.getNpTitle().equals("")) {
			mEtTitle.setText(note.getNpTitle());
		}
		mEtTitle.addTextChangedListener(new EditChangedListener());
		mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
			@Override
			public void onTextChange(String text) {
				if(isFirst != 1){
					isChange = 1;
				}else{
					isFirst = 0;
				}
			}
		});
		progressDialog = new MaterialDialog.Builder(mContext)
				.title("正在保存")
				.content("请稍候...")
				.progress(true, 0)
				.cancelable(false)
				.build();
	}

	@Override
	protected void initData() {
	}

	@Override
	protected void initInject() {
		DaggerUpdateNotePadComponent.builder()
				.updateNotePadModule(new UpdateNotePadModule(this))
				.build()
				.inject(this);
	}

	public void save(){
		progressDialog.show();
		NotePad np = new NotePad(note.getNpId(),mEtTitle.getText().toString(),mEditor.getHtml());
		mPresenter.updateNotePadList(gson.toJson(np));
	}

	@Override
	public void success() {
		if(actionSave == 1){
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					new MaterialDialog.Builder(mContext)
							.title("提示")
							.content("保存成功")
							.autoDismiss(true)
							.show();
				}
			});
			isChange = 0;
			progressDialog.dismiss();
		}else{
			finish();
		}

	}

	@Override
	public void fail(String msg) {

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

	public void confirmFinish(){
		if(isChange==1){
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
						}
					})
					.cancelable(false)
					.show();
		}else{
			finish();
		}
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
