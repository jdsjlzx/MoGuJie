package com.Imy.Fuli.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Imy.Fuli.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * 抽取BaseActivity   管理所有activity 方便退出
 *
 *
 *
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static FragmentManager mFragmentManager;
    private LinearLayout mAllView; // 总布局容器
    private RelativeLayout mBodyView;// 内容视图
    private View mCustomView;
    private TextView mActionBarTitleText;
    private TextView mActionBarSubTitleText; // 副标题
    private TextView navSymbol; // 标题和副标题中间符号
    private ImageView mTitleIconImg; // title图标
    private TextView mLoadingHintText;
    private View mActionBarRightBtn;
    private ImageButton btnBack;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    // 管理运行的所有的activity
    public final static List<BaseActivity> mActivities = new LinkedList<BaseActivity>();

    public static BaseActivity activity;


    public static BaseActivity getactivity(int index){
        return mActivities.get(index);
    }

    public FragmentManager getManager() {
        if (mFragmentManager==null){
            mFragmentManager =getSupportFragmentManager();
            return mFragmentManager;
        }
        return mFragmentManager;
    }

    @Override
    protected void onResume() {
        super.onResume();
        activity=this;
    }
    @Override
    protected void onPause() {
        super.onPause();
        activity=null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synchronized (mActivities) {
            mActivities.add(this);
        }
        mAllView = new LinearLayout(this);
        mAllView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mAllView.setOrientation(LinearLayout.VERTICAL);
        initActionBarView();
        initBodyView();
        mAllView.addView(mBodyView);
        init();
        setContentView(mAllView);
        initView();
    }

    private void initBodyView() {
        mBodyView = new RelativeLayout(this);
        mBodyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        // 初始化用户布局
        mCustomView = View.inflate(this, getLayoutID(), null);
        mCustomView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        // 将用户布局添加到relaContainer
        mBodyView.addView(mCustomView);
    }


    public  void initView() {

    }
    /**
     * 设置导航条后退的状态(是否可见)
     *
     * @return 按钮的状态
     */
    protected boolean isBtnBackVisible() {
        return false;
    }

    /**
     * 是否有导航条 默认为有
     * @return
     */
    protected boolean hasActionBar() {
        return true;
    }

    private void initActionBarView() {
        if (hasActionBar()) {
            View actionBarView = View.inflate(this,
                    R.layout.custom_actionbar_layout, null);
            mActionBarTitleText = (TextView) actionBarView
                    .findViewById(R.id.custom_actionbar_title);
            mActionBarSubTitleText = (TextView) actionBarView
                    .findViewById(R.id.custom_actionbar_subtitle);
            navSymbol = (TextView) actionBarView.findViewById(R.id.nav_symbol);
            mTitleIconImg = (ImageView) actionBarView
                    .findViewById(R.id.custom_actionbar_icon);
            mAllView.addView(actionBarView);
            btnBack = (ImageButton) actionBarView
                    .findViewById(R.id.custom_actionbar_back_btn);
            mActionBarRightBtn = actionBarView
                    .findViewById(R.id.custom_actionbar_right_btn);
            mActionBarRightBtn.setVisibility(View.INVISIBLE);
            btnBack.setVisibility(isBtnBackVisible() ? View.VISIBLE
                    : View.INVISIBLE);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public void init() {
    }
    public abstract int getLayoutID();
    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
//		if(receiver!=null){
//			unregisterReceiver(receiver);
//			receiver=null;
//		}
    }

    public void killAll() {
        // 复制了一份mActivities 集合
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new LinkedList<BaseActivity>(mActivities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }
        // 杀死当前的进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    /**
     * 设置导航条标题左图标
     *
     * @param drawableReId
     */
    protected void setNavIcon(int drawableReId) {
        mTitleIconImg.setVisibility(View.VISIBLE);
        mTitleIconImg.setImageResource(drawableReId);
    };

    /**
     * 设置导航条副标题
     *
     * @param subtitle
     */
    protected void setNavSubTitle(String subtitle) {
        mActionBarSubTitleText.setText(subtitle);
    }

    /**
     * 设置导航条副标题
     *
     * @param subtitle
     */
    protected void setNavSubTitle(int subtitle) {
        mActionBarSubTitleText.setText(subtitle);
    }

    /**
     * 设置导航条标题
     *
     * @param title
     */
    protected void setNavTitle(String title) {
        mActionBarTitleText.setText(title);
    }

    /**
     * 设置导航条标题
     *
     * @param titleStrResId
     */
    protected void setNavTitle(int titleStrResId) {
        mActionBarTitleText.setText(titleStrResId);
    }

}
