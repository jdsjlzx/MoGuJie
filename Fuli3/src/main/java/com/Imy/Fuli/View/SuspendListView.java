package com.Imy.Fuli.View;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.util.AttributeSet;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.Imy.Fuli.common.Constant;
import com.Imy.Fuli.tools.LogUtils;


public class SuspendListView extends ListView implements AbsListView.OnScrollListener {
    private HorizontalListView Indicator;
    private int downY=1;
    private boolean ret;


    public SuspendListView(Context context) {
        this(context, null);
        init();
        // TODO Auto-generated constructor stub
    }

    public SuspendListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
        // TODO Auto-generated constructor stub
    }

    public SuspendListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 重写该方法，达到使ListView适应ScrollView的效果 ,此方法会屏蔽掉listview的滑动事件. 即使监测到了滑动事
     * 件也是无法滑动.
     */
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//                MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expandSpec);
//    }
    private void init() {
        setOnScrollListener(this);
    }

    public void setHead(HorizontalListView Indicator) {
        this.Indicator = Indicator;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {

    }


    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getRawY();
                LogUtils.w("ListviewdownY=" + downY);
                break;
            case MotionEvent.ACTION_MOVE:
                //只有下啦过程中 才需要转交事件
                if (downY ==0){
                    downY = (int) ((int) ev.getRawY()-1.5f);//模拟Down事件
                }
                int deltaY = (int) (ev.getRawY() - downY);
                LogUtils.w("ListviewdeltaY=" + deltaY);
                //判断是否是下拉过程
                if (deltaY > 0 ) {
                    //再次判断position是否为0 为0 转交事件
                    if (getFirstVisiblePosition() == 0 && getChildAt(0).getTop() == 0) {
                        Constant.RET = true;
                    } else {
                        Constant.RET = false;
                    }
                } else if (deltaY < 0) { //上啦过程
                    LogUtils.w("Indicator.getVisibility()"+Indicator.getVisibility());
//                    if (Indicator.getVisibility() ==VISIBLE){ //特殊滑动状态
//                      Constant.RET = true; //转交事件
//                      return false;
//                    }
                }
                break;
            case MotionEvent.ACTION_UP:

            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onTouchEvent(ev);
    }
}
