package cn.smlcx.template.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lcx on 2017/7/11.
 */

public class ViewUtils {

	public static void setMargins (View v, int l, int t, int r, int b) {
		if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
			p.setMargins(l, t, r, b);
			v.requestLayout();
		}
	}
}
