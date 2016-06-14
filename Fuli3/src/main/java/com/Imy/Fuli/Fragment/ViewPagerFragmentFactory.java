package com.Imy.Fuli.Fragment;



import android.support.v4.app.Fragment;

import com.Imy.Fuli.tools.LogUtils;

import java.util.HashMap;
import java.util.Map;

public class ViewPagerFragmentFactory {

	private static Map<Integer,Fragment> mFragments = new HashMap<Integer, Fragment>();

	public static Fragment createFragment(int position) {
		Fragment fragment = null;
		fragment = mFragments.get(position);  //在集合中取出来Fragment
		if (fragment == null) {  //如果再集合中没有取出来 需要重新创建
			if (position == 0) {
				LogUtils.w("ViewpagerFragment 被new出来了");
				fragment = new SiDaFragment();
			} else if (position == 1) {
				fragment = new HotFragment();
			}else if (position == 2) {
				fragment = new StartListFragment();
			}else if (position == 3) {
//				fragment = new AttentionFragment();
			}else if (position == 4) {
				fragment = new ShaiHuoFragment();
			}
			if (fragment != null) {
				mFragments.put(position, fragment);// 把创建好的Fragment存放到集合中缓存起来
			}
		}
		return fragment;

	}
	public static Fragment getFragment(int position){
		return mFragments.get(position);
	}
}
