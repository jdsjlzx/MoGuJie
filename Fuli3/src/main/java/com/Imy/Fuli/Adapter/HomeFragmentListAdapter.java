package com.Imy.Fuli.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.Imy.Fuli.Holder.PagerHolder;
import com.Imy.Fuli.R;
import com.Imy.Fuli.View.SuspendListView;
import com.Imy.Fuli.tools.UiUtils;

/**
 * Created by user on 2015/11/9.
 */
public class HomeFragmentListAdapter extends BaseAdapter {
    private SuspendListView mListView;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//        PagerHolder mPagerHolder = new PagerHolder(mTabPageIndicator,mListView);
        return null;
    }
}
