package com.Imy.Fuli.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.Imy.Fuli.R;

/**
 * Scrollview下拉刷新头部
 * Created by user on 2015/11/20.
 */
public class ScrollViewHead extends LinearLayout {
    private LinearLayout mContainer;
    private ImageView mArrowImageView;
    private ProgressBar mProgressBar;
    private TextView mHintTextView;
    private int mState = STATE_NORMAL;
    private int topMargin = 0;
    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;
    private RelativeLayout mRefreshLayout;

    private final int ROTATE_ANIM_DURATION = 180;

    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;
    public final static int STATE_FINISH = 3;
    private Scroller scroller = null;

    public ScrollViewHead(Context context) {
        this(context, null);
    }

    public ScrollViewHead(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollViewHead(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        scroller = new Scroller(context);
        // 初始情况，设置下拉刷新view高度为0
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.pull_refresh_listview_header, null);
        addView(mContainer, lp);
        setGravity(Gravity.BOTTOM);
        mArrowImageView = (ImageView) findViewById(R.id.xlistview_header_arrow);
        mHintTextView = (TextView) findViewById(R.id.xlistview_header_hint_textview);
        mProgressBar = (ProgressBar) findViewById(R.id.xlistview_header_progressbar);
        mRefreshLayout= (RelativeLayout) findViewById(R.id.xlistview_header_content);

        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
    }

    /**
     * 设置顶部图标样式
     */
    public void setArrowImgStyle(int id) {
        if (mArrowImageView != null) {
            mArrowImageView.setImageResource(id);
        }
    }

    /**
     * 设置顶部进度条样式
     */
    public void setProgressStyle(int id) {
        if (mProgressBar != null) {
            mProgressBar.setIndeterminate(true);
            mProgressBar.setIndeterminateDrawable(getResources().getDrawable(id));
        }
    }

    public void setState(int state) {
        if (state == mState) return;

        if (state == STATE_REFRESHING) {    // 显示进度
            mArrowImageView.clearAnimation();
            mArrowImageView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {    // 显示箭头图片
            mArrowImageView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }

        switch (state) {
            case STATE_NORMAL:
                if (mState == STATE_READY) {
                    mArrowImageView.startAnimation(mRotateDownAnim);
                }
                if (mState == STATE_REFRESHING) {
                    mArrowImageView.clearAnimation();
                }
                mHintTextView.setText("下啦刷新");
                break;
            case STATE_READY:
                if (mState != STATE_READY) {
                    mArrowImageView.clearAnimation();
                    mArrowImageView.startAnimation(mRotateUpAnim);
                    mHintTextView.setText("松开刷新数据");
                }
                break;
            case STATE_FINISH:
                mHintTextView.setText("刷新成功");
                break;
            case STATE_REFRESHING:
                mHintTextView.setText("正在刷新");
                break;
            default:
        }

        mState = state;
    }

    public void updatemargin(int margin) {
//        这里用Linearlayout的原因是Headerview的父控件是scrollcontainer是一个linearlayout
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) this.getLayoutParams();
        params.topMargin = margin;
        topMargin = margin;
        setLayoutParams(params);
//        setPadding(0,Padding,0,0);
    }
    /**
     * 获取header的margin
     * @return
     */
    public int getTopMargin() {
        return topMargin;
    }

    public void setVisiableHeight(int height) {
        if (height < 0)
            height = 0;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContainer
                .getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    public RelativeLayout getRefreshLayout(){
        return mRefreshLayout;
    }
    public int getVisiableHeight() {
        return mContainer.getHeight();
    }



    @Override
    public void computeScroll() {
        //先判断mScroller滚动是否完成
        if (scroller.computeScrollOffset()) {

            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(scroller.getCurrX(), scroller.getCurrY());

            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }
}