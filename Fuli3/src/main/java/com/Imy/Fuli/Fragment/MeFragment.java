package com.Imy.Fuli.Fragment;

import android.view.View;
import android.widget.TextView;

import com.Imy.Fuli.View.LoadingPage;

/**
 * Created by user on 2015/11/9.
 */
public class MeFragment extends BaseFragment {
    @Override
    public View createSuccessView() {
        TextView mTextView =  new TextView(getContext());
        mTextView.setText("我是MeFragment");
        return mTextView;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }
}
