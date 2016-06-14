package com.Imy.Fuli.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import com.Imy.Fuli.common.Constant;
import com.Imy.Fuli.tools.LogUtils;
import com.Imy.Fuli.tools.UiUtils;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by user on 2015/11/12.
 */
public class SuspendScrollView extends ScrollView {
    private OnRefreshScrollViewListener mOnRefreshScrollViewListener = null;
    private final static float OFFSET_RADIO = 0.5f;
    private final static int SCROLL_DURATION = 400; //滑动的时间
    private int ScrollY;
    private boolean isMeasured = false;
    private View Headview;
    private int headerHeight;
    private int offsetHeight;
    private int RefreshViewHeight;
    private HorizontalListView mSupListView;
    private GestureDetector mGestureDetector;
    private int headerViewHeight;//headerView高
    private Scroller scroller = null;
    private int downY = 0;//按下时y坐标
    private int deltY = 1;
    private int offset = 0;
    private HorizontalListView mHorizontalListView;
    private final int PULL_REFRESH = 0;//下拉刷新的状态
    private final int RELEASE_REFRESH = 1;//松开刷新的状态
    private final int REFRESHING = 2;//正在刷新的状态
    public final static int STATE_FINISH = 3; //刷新成功的状态
    private int currentState = PULL_REFRESH;
    private LinearLayout RefreshHeadContainer;
    private ScrollViewHead mScrollViewHead;
    private LinearLayout mScrollContainer; //scrollview里的容器
    private RelativeLayout mRefreshLayout;//刷新布局
    private int mScrollContainerTopMargin = 0;
    private float amendMargin; //修正后的margin;
    private int mRefreshLayoutHheight; //下啦刷新的布局高度. 不包含隐藏BOSS的布局
    private boolean Intercept =false ;
    private boolean canMove;


    private RotateAnimation upAnimation, downAnimation;

    public SuspendScrollView(Context context) {
        super(context);
        initView(context);
    }

    public SuspendScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public SuspendScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                downY = (int) ev.getRawY(); //由于事件拦截不一定能执行Down事件 需要进行多重计算;

                LogUtils.w("按下的downY=" + downY);
                return true;
            case MotionEvent.ACTION_MOVE:
                //条件判断  只有当布局在顶部时 才允许下拉
                if (downY == 0) { //修正因事件拦截引起的Down事件无法准确获取的BUG
                    downY = (int) ((int) ev.getRawY() - 1.5f); //模拟Down事件 ,只需要一次即可
                }
                deltY = (int) (ev.getRawY() - downY);
                LogUtils.w("deltY" + deltY);
                if (currentState == REFRESHING) {
                    break;
                }
                amendMargin = deltY * OFFSET_RADIO;
//
                if (ScrollY == 0 && amendMargin > 0) {    //开始下啦状态处理, 包括下啦的时候又进行了上啦操作,此时没有松开.
                    LogUtils.w("amendMargin" + amendMargin);
                    updateHeader(amendMargin);
                    updateScrollview(amendMargin);
                    LogUtils.w("down=" + downY);
                    return true;  //返回true 消费事件 不再进行传递
                } else if (amendMargin <= 0) {  //下啦的时候又上啦了 事件转交给scrollview;
                    return super.onTouchEvent(ev);
                }
            case MotionEvent.ACTION_UP:
                downY = 0;//修正处理  对应Down事件的 downY;
                if (amendMargin < mRefreshLayoutHheight) {//不进入正在刷新状态 //重置各个位置
                    updateHeader(-RefreshViewHeight);
                    updateScrollview(0);
                    mScrollViewHead.setState(PULL_REFRESH);
                }else if (amendMargin> mRefreshLayoutHheight){ //进入下拉刷新状态
                    updateScrollview(mRefreshLayoutHheight);
                    updateHeader(mRefreshLayoutHheight);
                    mScrollViewHead.setState(REFRESHING);
                    //在此可以设置在下啦刷新状态是否还能进行滑动操作;
                    if (getRefreshingCanMove()){
                        Intercept =true;
                    }else {
                        Intercept=false;
                    }
                    if (mOnRefreshScrollViewListener!=null){
                        mOnRefreshScrollViewListener.onRefresh();
                    }
                }
//
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 完成刷新操作，重置状态,在你获取完数据并更新完adater之后，去在UI线程中调用该方法
     */
    public void completeRefresh() {
        //重置headerView状态
        Intercept = false;
        UiUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateHeader(-RefreshViewHeight);
                updateScrollview(0);
                mScrollViewHead.setState(PULL_REFRESH);
                if (mOnRefreshScrollViewListener != null) {
                    mOnRefreshScrollViewListener.onRefreshFinish();
                }
            }
        });

    }

    private void updateScrollview(float margin) {
        int currentMargin = (int) (margin);
        FrameLayout.LayoutParams layoutParams = (LayoutParams) mScrollContainer.getLayoutParams();
        layoutParams.topMargin = currentMargin;
        mScrollContainerTopMargin = currentMargin;
        mScrollContainer.setLayoutParams(layoutParams);
    }

    public boolean getRefreshingCanMove(){
        return canMove;
    }
    /**
     * 设置在下拉刷新状态的时候 是否还可以进行滑动操作. true 不能操作, false可以操作; 默认可以操作
     * @param canMove
     */
    public void setRefreshingCanMove(boolean canMove){
        this.canMove =canMove;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (Intercept) {
            return true;
        } else {
            return super.dispatchTouchEvent(ev);
        }

    }

    private void updateHeader(float deltY) {
        int currentMargin = (int) (-RefreshViewHeight + deltY);
        mScrollViewHead.updatemargin(currentMargin);
        if (currentState != REFRESHING) {
            if (amendMargin > mRefreshLayoutHheight) { //实际移动的margin
                mScrollViewHead.setState(mScrollViewHead.STATE_READY);
            } else {
                mScrollViewHead.setState(mScrollViewHead.STATE_NORMAL);
            }
        }
    }

    /**
     * @param top 实际偏移的距离
     */
    private void UpdateIndicatorLayout(int top) {
        ViewHelper.setTranslationY(mHorizontalListView, top);
    }


    /**
     * 初始化View
     *
     * @param context
     */
    private void initView(Context context) {
        scroller = new Scroller(context);
        mGestureDetector = new GestureDetector(getContext(), new YScrollDetector()); //初始化手势
        setFadingEdgeLength(0);
    }

    /**
     * 下啦刷新头部初始化
     */
    private void initHeaderView() {

        mScrollViewHead = new ScrollViewHead(getContext());
        RefreshHeadContainer.addView(mScrollViewHead);
        RefreshHeadContainer.measure(0, 0);//通知系统测量
        RefreshViewHeight = RefreshHeadContainer.getMeasuredHeight();
        mScrollViewHead.updatemargin(-RefreshViewHeight);
        mRefreshLayout = mScrollViewHead.getRefreshLayout();
        mRefreshLayoutHheight = mRefreshLayout.getMeasuredHeight();


    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LogUtils.w("滑动距离------" + t);
        ScrollY = t;
        CheckMargin(t);

        if (t >= offsetHeight - 2) {//上啦过程
            mSupListView.setVisibility(View.VISIBLE);
            if (t >= offsetHeight - 2) {
                Constant.RET = false; //不拦截
            } else {
                Constant.RET = true; //拦截
            }
        } else {
            mSupListView.setVisibility(View.GONE);
            Constant.RET = true; //拦截
        }
        super.onScrollChanged(l, t, oldl, oldt);


    }

    /**
     * 在下啦刷新过程中又上啦了. 此过程并没有进入正在刷新状态,
     * 此方法是为了修复Margin 值的准确性
     *
     * @param t
     */
    private void CheckMargin(int t) {
        if (t == 0) {
            FrameLayout.LayoutParams layoutParams = (LayoutParams) mScrollContainer.getLayoutParams();
            int topMargin = layoutParams.topMargin;
            if (topMargin != 0) {
                layoutParams.topMargin = 0;
                mScrollContainer.setLayoutParams(layoutParams);
            }
            if (mScrollViewHead.getTopMargin() != -RefreshViewHeight) {
                mScrollViewHead.updatemargin(-RefreshViewHeight);
            }
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mHorizontalListView.getTop() > 0 && mHorizontalListView.getTop() <= Constant.offsetHeight - 2) {
            Constant.RET = super.onInterceptTouchEvent(ev);
        }
        LogUtils.w("super.onInterceptTouchEvent(ev)=" + super.onInterceptTouchEvent(ev));

        return Constant.RET && mGestureDetector.onTouchEvent(ev);
    }

    /**
     * 头部初始化
     *
     * @param banner           广告头
     * @param sup              悬浮的头布局
     * @param refreshHeadView  下啦刷新头布局容器
     * @param NoSup            不是悬浮的布局
     * @param mScrollContainer
     */
    public void setView(View banner, HorizontalListView sup, LinearLayout refreshHeadView, HorizontalListView NoSup, LinearLayout mScrollContainer) {
        this.Headview = banner;
        this.mSupListView = sup;
        this.RefreshHeadContainer = refreshHeadView;
        this.mScrollContainer = mScrollContainer;
        Headview.measure(0, 0);
        this.mSupListView.measure(0, 0);
        this.mHorizontalListView = NoSup;
        headerHeight = Headview.getMeasuredHeight();
        initHeaderView();
        Headview.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (!isMeasured) {
                    offsetHeight = headerHeight - SuspendScrollView.this.mHorizontalListView.getMeasuredHeight();
                    isMeasured = true;
                }
                return true;
            }
        });


    }


    public void setOnRefreshScrollViewListener(OnRefreshScrollViewListener OnRefreshScrollViewListener) {
        this.mOnRefreshScrollViewListener = OnRefreshScrollViewListener;
    }

    public interface OnRefreshScrollViewListener {
        public void onRefresh();

        public void onRefreshFinish();
    }
    //此方法为判断是否是左右滑动 处理Viewpager事件拦截 从而不需要再自定义一个Viewpager进行事件判断
    private class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {

            if (Math.abs(distanceY) >= Math.abs(distanceX)) {
                return true; ////如果更相对于左右滑动 事件交给子控件处理
            }
            return false;
        }
    }
}
