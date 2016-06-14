package com.Imy.Fuli.Fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.Imy.Fuli.Activity.HomeActivity;
import com.Imy.Fuli.Adapter.HomeFragmentViewPagerAdapter;
import com.Imy.Fuli.R;
import com.Imy.Fuli.View.FragmentViewPager;
import com.Imy.Fuli.View.HorizontalListView;
import com.Imy.Fuli.View.LoadingPage;
import com.Imy.Fuli.View.SuspendScrollView;
import com.Imy.Fuli.common.Constant;
import com.Imy.Fuli.manager.ThreadManager;
import com.Imy.Fuli.tools.LogUtils;
import com.Imy.Fuli.tools.UiUtils;

/**
 * Created by user on 2015/11/9.
 */
public class HomeFragment extends BaseFragment {

    private FragmentViewPager mViewPager;

    private TextView mTextView;
    private LinearLayout mLinearLayout;
    private static boolean isInit = false;
    private HomeActivity mHomeActivity;
    private RadioGroup mRadioGroup;
    private LinearLayout linearLayout;
    private boolean isMeasured = false;
    private SuspendScrollView mSuspendScrollView;
    private LinearLayout RefreshHeadView;
    private HorizontalListView mHorizontalListView;
    private HorizontalListView mSupListView;
    private ImageView imageView;
    private LinearLayout mScrollContainer;

    /**
     * 当Fragment挂载的activity创建的时候调用
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show(); //为了使第一次加载HomeFragment的时候可以正常显示 执行以下shouw方法.

    }

    @Override
    public void onPause() {
        super.onPause();
        isInit = true;
    }

    @Override
    public View createSuccessView() {
        View view = View.inflate(getContext(), R.layout.home_fragment_context, null);
        mScrollContainer = (LinearLayout) view.findViewById(R.id.scrollview_container);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.home_banner_header);//广告头的总布局;
        mViewPager = (FragmentViewPager) view.findViewById(R.id.home_viewpager);
        mHorizontalListView = (HorizontalListView) view.findViewById(R.id.user); //非悬浮导航
        mSupListView = (HorizontalListView) view.findViewById(R.id.Sup); //悬浮导航
        Constant.MY_INDICATOR = mSupListView;
        imageView = (ImageView) view.findViewById(R.id.myimage);
        mHorizontalListView.setAdapter(new OrderAdapter(getContext()));
        mSupListView.setAdapter(new OrderAdapter(getContext()));
        RefreshHeadView = (LinearLayout) view.findViewById(R.id.scrollView_refresh_head);
        mSuspendScrollView = (SuspendScrollView) view.findViewById(R.id.home_scrollview);
        //接受参数
        mSuspendScrollView.setView(mLinearLayout, mSupListView, RefreshHeadView, mHorizontalListView,mScrollContainer);
        mSuspendScrollView.setOnRefreshScrollViewListener(new SuspendScrollView.OnRefreshScrollViewListener() {
            @Override
            public void onRefresh() {
                UiUtils.showToast("下啦刷新中");
                //请求数据操作 子线程操作
                ThreadManager.getInstance().createLongPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1000);//模拟请求数据
                        mSuspendScrollView.completeRefresh();


                    }
                });
            }

            /**
             * 刷新完成时需要的操作  更新UI等
             */
            @Override
            public void onRefreshFinish() {
                UiUtils.showToast("刷新完成");
            }
        });
        mViewPager.setAdapter(new HomeFragmentViewPagerAdapter(getActivity().getSupportFragmentManager()));
        mViewPager.setOffscreenPageLimit(2);//设置预加载 防止切换时状态丢失
        setViewpagerHeight(mViewPager);
        initIndicator();

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                ((OrderAdapter)mHorizontalListView.getAdapter()).setCurOrderItem(position);
            }

        });

        return view;
    }


    private void initIndicator() {
        mLinearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isInit) {
                    int top = mHorizontalListView.getTop();
                    int height = mHorizontalListView.getHeight();
                    //mSupListView.layout(0, top, mSupListView.getWidth(), top + height);
                }
            }
        });
    }


    private void setViewpagerHeight(final ViewPager mViewPager) {
        mHomeActivity = (HomeActivity) getActivity();
        mRadioGroup = mHomeActivity.getRadioGroup();
        linearLayout = mHomeActivity.getHomeActivityLinearLayout();
        linearLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (!isMeasured) {
                    int height = linearLayout.getHeight() - mRadioGroup.getHeight() - mHorizontalListView.getHeight();
                    LinearLayout.LayoutParams llparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            height);
                    mViewPager.setLayoutParams(llparams);
                    LogUtils.w("是---------------------dp=" + UiUtils.px2dip(imageView.getHeight()));
                    isMeasured = true;
                }
                return true;
            }
        });

    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

}
