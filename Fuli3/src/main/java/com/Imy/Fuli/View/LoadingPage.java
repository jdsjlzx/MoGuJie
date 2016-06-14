package com.Imy.Fuli.View;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.Imy.Fuli.R;
import com.Imy.Fuli.manager.ThreadManager;
import com.Imy.Fuli.tools.LogUtils;
import com.Imy.Fuli.tools.UiUtils;


/***
 * 创建了自定义帧布局 把baseFragment 一部分代码 抽取到这个类中
 * 
 * @author itcast
 * 
 */
public abstract class LoadingPage extends FrameLayout {

	public static final int STATE_UNKOWN = 0;
	public static final int STATE_LOADING = 1;
	public static final int STATE_ERROR = 2;
	public static final int STATE_EMPTY = 3;
	public static final int STATE_SUCCESS = 4;
	public int state = STATE_UNKOWN;
	private ImageView mIv_anim;// 初始化动画
	private View loadingView;// 加载中的界面
	private View errorView;// 错误界面
	private View emptyView;// 空界面
	private View successView;// 加载成功的界面
	private AnimationDrawable anim_Drawable; //初始化图动画

	public LoadingPage(Context context) {
		super(context);
		init();
	}

	public LoadingPage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public LoadingPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		//将各类型的返回结果添加到帧布局中
		loadingView = createLoadingView(); // 创建了加载中的界面
		if (loadingView != null) {
			this.addView(loadingView, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			LogUtils.w("LoadingView被加载了");
		}
		errorView = createErrorView(); // 加载错误界面
		if (errorView != null) {
			this.addView(errorView, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			LogUtils.w("errorView被加载了");
		}
		emptyView = createEmptyView(); // 加载空的界面
		if (emptyView != null) {
			this.addView(emptyView, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			LogUtils.w("emptyView被加载了");
		}
		showPage();// 根据不同的状态显示不同的界面
	}

	// 根据不同的状态显示不同的界面
	private void showPage() {
		if (loadingView != null) {
			loadingView.setVisibility(state == STATE_UNKOWN
					|| state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
		}
		if (errorView != null) {
			errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE
					: View.INVISIBLE);
		}
		if (emptyView != null) {
			emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE
					: View.INVISIBLE);
		}
		if (state == STATE_SUCCESS) {
			if (successView == null) {
				successView = createSuccessView();
				this.addView(successView, new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			}
			//如果每次切换都要增加动画的话. 在这里执行逻辑
			successView.setVisibility(View.VISIBLE);
		} else {
			if (successView != null) {
				successView.setVisibility(View.INVISIBLE);
			}
		}
		LogUtils.w("当前状态state" + state);

	}

	/* 创建了空的界面 */
	private View createEmptyView() {
		View view = View.inflate(UiUtils.getContext(), R.layout.loadpage_error,
				null);
		return view;
	}

	/* 创建了错误界面 */
	private View createErrorView() {
		View view = View.inflate(UiUtils.getContext(), R.layout.loadpage_error,
				null);
		Button page_bt = (Button) view.findViewById(R.id.page_bt);
		page_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				show();
			}
		});
		return view;
	}

	/* 创建加载中的界面 */
	private View createLoadingView() {
		View view = View.inflate(UiUtils.getContext(),
				R.layout.loadpage_loading, null);
		return view;
	}



	public enum LoadResult {
		error(2), empty(3), success(4);

		int value;

		LoadResult(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}

	// 根据服务器的数据 切换状态
	public void show() {
		LogUtils.w("shou方法调用了");
		if (state == STATE_ERROR || state == STATE_EMPTY) {
			state = STATE_LOADING;
		}
		//设置动画参数
		if(mIv_anim==null){
			mIv_anim = (ImageView) findViewById(R.id.anim_iv);
			mIv_anim.setImageResource(R.drawable.loading_animation);
		}
		//开启动画
		if(anim_Drawable==null){
			anim_Drawable = (AnimationDrawable) mIv_anim.getDrawable();
		}
		anim_Drawable.start();
		// 请求服务器 获取服务器上数据 进行判断
		// 请求服务器 返回一个结果
		ThreadManager.getInstance().createLongPool().execute(new Runnable() {

			@Override
			public void run() {
				SystemClock.sleep(600);
				System.out.println("延时了600毫秒");
				final LoadResult result = load();
				UiUtils.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (result != null) {
							state = result.getValue();
							showPage(); // 状态改变了,重新判断当前应该显示哪个界面
						}
					}
				});
			}
		});
		
		
		showPage();

	}

	/***
	 * 创建成功的界面
	 * 
	 * @return
	 */
	public abstract View createSuccessView();

	/**
	 * 请求服务器
	 * 
	 * @return
	 */
	protected abstract LoadResult load();




}
