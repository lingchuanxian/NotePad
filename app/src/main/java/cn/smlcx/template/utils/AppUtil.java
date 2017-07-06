package cn.smlcx.template.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by lcx on 2017/7/6.
 */

public class AppUtil {
	/**
	 * 获取App版本号
	 *
	 * @param context 上下文
	 * @return App版本号
	 */
	public static String getAppVersionName(Context context) {
		return getAppVersionName(context, context.getPackageName());
	}

	/**
	 * 获取App版本号
	 *
	 * @param context     上下文
	 * @param packageName 包名
	 * @return App版本号
	 */
	public static String getAppVersionName(Context context, String packageName) {
		if (isSpace(packageName)) return null;
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(packageName, 0);
			return pi == null ? null : pi.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static boolean isSpace(String s) {
		if (s == null) return true;
		for (int i = 0, len = s.length(); i < len; ++i) {
			if (!Character.isWhitespace(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
