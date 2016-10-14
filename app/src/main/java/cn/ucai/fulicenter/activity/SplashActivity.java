package cn.ucai.fulicenter.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;


import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.utils.MFGT;

/**
 * 开屏页
 *
 */
public class SplashActivity extends Activity {
	private RelativeLayout rootLayout;
	Context mContext;
	
	private static final int sleepTime = 2000;

	@Override
	protected void onCreate(Bundle arg0) {
		setContentView(R.layout.activity_splash);
		super.onCreate(arg0);
		mContext = this;
		rootLayout = (RelativeLayout) findViewById(R.id.splash_root);
		AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
		animation.setDuration(1500);
		rootLayout.startAnimation(animation);
	}

	@Override
	protected void onStart() {
		super.onStart();
         new Handler().postDelayed(new Runnable() {
			 @Override
			 public void run() {
				 MFGT.gotoMainActivity(SplashActivity.this);
				 finish();
			 }
		 },2000);

	}
}
