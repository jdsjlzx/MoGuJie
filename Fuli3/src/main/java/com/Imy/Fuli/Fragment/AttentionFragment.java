//package com.Imy.Fuli.Fragment;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AbsListView;
//import android.widget.ArrayAdapter;
//import android.widget.LinearLayout;
//
//import com.Imy.Fuli.R;
//
//import com.Imy.Fuli.View.SuspendListView;
//import com.Imy.Fuli.common.Constant;
//import com.Imy.Fuli.tools.LogUtils;
//import com.Imy.Fuli.tools.UiUtils;
//import com.nineoldandroids.view.ViewHelper;
//import com.viewpagerindicator.TabPageIndicator;
//
//import java.util.ArrayList;
//
///**
// * Created by user on 2015/11/9.
// */
//public class AttentionFragment extends Fragment {
//    private ArrayList<String> datas;
//    private ArrayAdapter<String> adapter;
//    private SuspendListView mListView;
//    private LinearLayout mEmptyHeader;
//    private boolean isMeasured = false;
//    private int headerHeight;
//    private static TabPageIndicator mIndicator = null;
//    private static LinearLayout mHomeFragmentHeader = null;
//    private int headertop;
//    private int top;
//
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = View.inflate(UiUtils.getContext(), R.layout.sida_viewpager_item, null);
////        mListView = (SuspendListView) view.findViewById(R.id.viewpage_listview);
//        datas = new ArrayList<String>();
//        for (int i = 0; i < 20; i++) {
//            datas.add("currnet page: = 1" + " item --" + i);
//        }
//        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, android.R.id.text1, datas);
////        iniEmptyHeader();
//        mListView.setAdapter(adapter);
////        mListView.setMyScrollListener(new SuspendListView.OnScrollListener() {
////            @Override
////            public void onMove(int offset) {
////                //动态获取listview 滑动的高度-- 总高度. 不管滑动多少都能准确计算
////                top = getScrollY(mListView);
////                LogUtils.w("总高度=" + top);
////                //动态更改头布局的高度以及悬浮控件的高度
////                mHomeFragmentHeader.post(new Runnable() {
////                    @Override
////                    public void run() {
//////                        mHomeFragmentHeader.layout(0, -top, mHomeFragmentHeader.getWidth(), top + mHomeFragmentHeader.getHeight());
////                        //动态更改头布局
////                        ViewHelper.setTranslationY(mHomeFragmentHeader, -top);
//////                        LogUtils.w("mIndicator.getTop();=" + mIndicator.getTop());
////                        int mIndicatorTop = headerHeight - mIndicator.getHeight() - top;
////
////                        //动态更改悬浮导航窗口
////                        if (mIndicatorTop <= 0) { //悬浮判断
////                            mIndicatorTop = 0;
////                        }
////                        LogUtils.w("mIndicatorTop=" + mIndicatorTop + "-------mHomeFragmentHeaderHeight="
////                                + headerHeight + "--------mIndicator.getHeight()=" + mIndicator.getHeight());
////                        mIndicator.layout(0, mIndicatorTop, mIndicator.getWidth(), mIndicatorTop + mIndicator.getHeight());
////                    }
////                });
//
//
////                paddingTop =-getScrollY(mListView);
////                LogUtils.w("getScrollY="+getScrollY(mListView));
////                LogUtils.w("移动偏移距离=" + offset);
//////                paddingTop = offset + oldpaddingTop;
//////
//////                if (paddingTop >= 0) {
//////                    paddingTop = 0;
//////                }
////                //判断当前Item 不是最后一项时 并且offset
////                LogUtils.w("paddingTop=" + paddingTop);
////                mHomeFragmentHeader.setPadding(0, paddingTop, 0, 0);
//            }
//
//            //按下时
//            @Override
//            public void onDown(int Y) {
//                LogUtils.w("onDown----------------firstVisiblePosition=" + mListView.getFirstVisiblePosition());
//            }
//
//
//
//            //抬起时
//            @Override
//            public void onUP() {
//
//            }
//
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                    LogUtils.w("快速滑动中---------"+"高度="+getScrollY(mListView));
//
//                }
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
//                    LogUtils.w("正在滑动---------"+"高度="+getScrollY(mListView));
//                }
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                    LogUtils.w("闲置状态---------"+"高度="+getScrollY(mListView));
//                }
//            }
//
//        });
//        return view;
//    }
//    public int getScrollY(AbsListView view) {
//        View c = view.getChildAt(0);
//        if (c == null) {
//            return 0;
//        }
//        //基于父控件坐标
//        int top = c.getTop();
////                LogUtils.w("int top=" + top);
//        int firstVisiblePosition = view.getFirstVisiblePosition();
//                LogUtils.w("firstVisiblePosition=" + firstVisiblePosition);
//        if (firstVisiblePosition == 0) {
//            headertop = -top;
//            return -top;
//        } else if (firstVisiblePosition == 1) {
//            return -top + headertop;
//        } else {
//            return -top + (firstVisiblePosition - 2) * c.getHeight() + headertop;
//        }
//
//    }
//    private void iniEmptyHeader() {
//        mEmptyHeader = new LinearLayout(UiUtils.getContext());
////        mEmptyHeader.setBackgroundColor(getResources().getColor(R.color.color_purple_bd6aff));
//        mHomeFragmentHeader = Constant.MY_HEAD_LAYOUT;
//        mIndicator = Constant.MY_INDICATOR;
////        mHomeFragmentHeader.getViewTreeObserver().addOnPreDrawListener(
////                new ViewTreeObserver.OnPreDrawListener() {
////                    @Override
////                    public boolean onPreDraw() {
////                        if (!isMeasured) {
////
////                            headerHeight = mHomeFragmentHeader.getMeasuredHeight();
////                            AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
////                                    headerHeight);
////                            mEmptyHeader.setLayoutParams(params);
////                            mListView.addHeaderView(mEmptyHeader);
////                            mListView.deferNotifyDataSetChanged();
////                            LogUtils.w("headerHeight=" + headerHeight);
////                            isMeasured = true;
////                        }
////                        return true;
//
////                    }
////                });
//
//
////        mEmptyHeader = new LinearLayout(UiUtils.getContext());
////        mEmptyHeader.setBackgroundColor(getResources().getColor(R.color.color_purple_bd6aff));
////        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
////                UiUtils.dip2px(280));
////        mEmptyHeader.setLayoutParams(params);
////        mListView.addHeaderView(mEmptyHeader);
////        reLocation = true;
////        mEmptyHeader.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
////            @Override
////            public boolean onPreDraw() {
////                if (!isMeasured) {
////                    headerHeight = mEmptyHeader.getMeasuredHeight();
////                    isMeasured = true;
////                }
////                headerTranslationDis = headerHeight - mTextView.getMeasuredHeight();
////
////                return true;
////
////            }
////        });
//
//    }
//
//}
