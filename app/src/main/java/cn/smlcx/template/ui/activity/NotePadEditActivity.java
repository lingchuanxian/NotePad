package cn.smlcx.template.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.smlcx.template.R;
import cn.smlcx.template.base.BaseActivity;
import cn.smlcx.template.bean.NotePad;
import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by lcx on 2017/6/9.
 */

public class NotePadEditActivity extends BaseActivity {

	@BindView(R.id.editor)
	RichEditor mEditor;

	@Override
	protected int attachLayoutRes() {
		return R.layout.activity_notepad_edit;
	}

	@Override
	protected void initViews() {
		NotePad note = (NotePad) getIntent().getExtras().getSerializable("note");
		getToolBar().setTitle("编辑")
				.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						String msg = "";
						switch (item.getItemId()) {
							case R.id.action_add:
								msg += "Click add";
								break;
						}
						if (!msg.equals("")) {
							Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
						}
						return false;
					}
				});
		if(note.getNpContent().equals("")){
			mEditor.setPlaceholder("请输入内容");
		}else{
			mEditor.setHtml(note.getNpContent());
		}
	}

	@Override
	protected void initData() {
	}

	@Override
	protected void initInject() {

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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO: add setContentView(...) invocation
		ButterKnife.bind(this);
	}
}
