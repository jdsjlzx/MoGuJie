package com.Imy.Fuli.Holder;

import android.provider.ContactsContract;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;


import com.Imy.Fuli.Activity.BaseActivity;
import com.Imy.Fuli.Adapter.HomeFragmentViewPagerAdapter;
import com.Imy.Fuli.R;
import com.Imy.Fuli.View.SuspendListView;
import com.Imy.Fuli.tools.BitmapHelper;
import com.Imy.Fuli.tools.LogUtils;
import com.Imy.Fuli.tools.UiUtils;
import com.lidroid.xutils.BitmapUtils;


/**
 * Created by user on 2015/11/9.
 */
public class PagerHolder  {
    private TextView mTextView;
    private View contentView;
    protected BitmapUtils bitmapUtils;
    private ViewPager mViewPager;
    private SuspendListView mListView;

    public PagerHolder( SuspendListView mListView) {
        bitmapUtils = BitmapHelper.getBitmapUtils();

        this.mListView = mListView;
//        this.mTabPageIndicator = mTabPageIndicator;
        contentView=initView();
        contentView.setTag(this);
    }

    public View initView() {

        View view = View.inflate(UiUtils.getContext(), R.layout.home_viewpager,null);
//        mTextView = (TextView) view.findViewById(R.id.user);
//        mTabPageIndicator = (TabPageIndicator) view.findViewById(R.id.tab_indicator);
        mViewPager= (ViewPager) view.findViewById(R.id.home_viewpager);
        LogUtils.w("mViewPager=" + mViewPager);
        HomeFragmentViewPagerAdapter adapter = new HomeFragmentViewPagerAdapter(BaseActivity.getactivity(0).getManager());
        mViewPager.setAdapter(adapter);
//        mTabPageIndicator.setViewPager(mViewPager);

//        mListView.setOnScrollListener(new SuspendListView.OnScrollListener() {
//            @Override
//            public void ViewScroll(int scrollY) {
//                int mBuyLayout2ParentTop = Math.max(scrollY, mTextView.getTop());
//                mTabPageIndicator.layout(0, mBuyLayout2ParentTop, mTabPageIndicator.getWidth(),
//                        mBuyLayout2ParentTop + mTabPageIndicator.getHeight());
//            }
//        });
        return view;
    }


    public void setData(ContactsContract.Data data){
        refreshView(data);
    }

    private void refreshView(ContactsContract.Data data) {

    }
    public  View getContentView() {
        return contentView;
    }

}
