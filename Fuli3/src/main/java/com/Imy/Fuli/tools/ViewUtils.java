package com.Imy.Fuli.tools;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;

public class ViewUtils {
	public static void removeParent(View v){
		//  先找到爹 在通过爹去移除孩子
		ViewParent parent = v.getParent();
		//所有的控件 都有爹  爹一般情况下 就是ViewGoup
		if(parent instanceof ViewGroup){
			ViewGroup group=(ViewGroup) parent;
			group.removeView(v);
		}
	}
	public static int getwindowsDisplay(){
		WindowManager wm = (WindowManager) UiUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
		//得到屏幕宽
		int width = wm.getDefaultDisplay().getWidth();
		return width;
	}
}
