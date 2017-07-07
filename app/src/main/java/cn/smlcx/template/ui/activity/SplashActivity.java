package cn.smlcx.template.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.smlcx.template.R;
import cn.smlcx.template.utils.ImageLoader;
import cn.smlcx.template.utils.SPUtils;

/**
 * Created by lcx on 2017/6/5.
 */

public class SplashActivity extends AppCompatActivity {

	@BindView(R.id.iv_splsh)
	ImageView mIvSplsh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//取消标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//取消状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash);
		ButterKnife.bind(this);
		initViews();
		initData();
	}

	protected void initViews() {
		mIvSplsh.setImageBitmap(ImageLoader.load(SplashActivity.this, R.mipmap.splash));
	}

	protected void initData() {
		// 如果是第一次启动，则先进入功能引导页
		new Handler().postDelayed(new Runnable() {
		@Override
		public void run() {
			boolean isFirstOpen = SPUtils.getValue(SplashActivity.this,"global","isFirstOpen",true);
			Intent intent;
			if (isFirstOpen) {
				intent = new Intent(SplashActivity.this,IntroActivity.class);
				SPUtils.putValue(SplashActivity.this,"global","isFirstOpen",false);
			}else{
				intent = new Intent(SplashActivity.this,HomeActivity.class);
			}
			startActivity(intent);
			finish();
		}
	}, 3000);

}
}
