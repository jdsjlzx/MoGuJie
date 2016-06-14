package com.Imy.Fuli.Holder;

import android.view.View;

import com.Imy.Fuli.tools.BitmapHelper;
import com.lidroid.xutils.BitmapUtils;

public abstract class BaseHolder<Data>  {
	private View contentView;
	private Data data;
	protected BitmapUtils bitmapUtils;
	public BaseHolder(){
		bitmapUtils = BitmapHelper.getBitmapUtils();
		contentView=initView();
		contentView.setTag(this);
	}


	/** 初始化视图子类实现*/
	public  abstract View initView();
	public  View getContentView() {
		return contentView;
	}

	public void setData(Data data){
		this.data=data;
		refreshView(data);
	}
	/** 刷新数据*/
	public abstract void refreshView(Data data);
}
