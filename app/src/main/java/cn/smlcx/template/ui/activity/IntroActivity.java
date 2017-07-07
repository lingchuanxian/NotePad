package cn.smlcx.template.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import cn.smlcx.template.R;

/**
 * Created by lcx on 2017/7/3.
 */
public class IntroActivity extends AppIntro {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addSlide(AppIntroFragment.newInstance("Hand", "if(you.hand==cold&&weather=winter)\n" +
				"  giveyoulove(myhand.temp,yourhand.temp);\n" +
				"return you.happyface; ", R.mipmap.splash ,Color.parseColor("#3F51B5")));
		addSlide(AppIntroFragment.newInstance("Living", "try\n{living();}\n" +
				"  catch(Exception e){faceTogether();}\n" +
				"  finally{\nours.love++;\n}        ", R.mipmap.splash ,Color.parseColor("#3F51B5")));
		addSlide(AppIntroFragment.newInstance("Never Betray", "if(you never betray)\n" +
				"printf(I never give up)\nelse\nprintf(I never give up also)", R.mipmap.splash ,Color.parseColor("#3F51B5")));

		setBarColor(Color.parseColor("#3F51B5"));
		setSeparatorColor(Color.parseColor("#2196F3"));

		showSkipButton(false);
		setProgressButtonEnabled(true);

		setVibrate(true);
		setVibrateIntensity(30);
	}

	@Override
	public void onSkipPressed(Fragment currentFragment) {
		super.onSkipPressed(currentFragment);
		// Do something when users tap on Skip button.
	}

	@Override
	public void onDonePressed(Fragment currentFragment) {
		super.onDonePressed(currentFragment);
		startActivity(new Intent(this, HomeActivity.class));
	}

	@Override
	public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
		super.onSlideChanged(oldFragment, newFragment);
		// Do something when the slide changes.
	}
}
