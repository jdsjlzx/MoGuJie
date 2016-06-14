package com.Imy.Fuli.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Imy.Fuli.tools.UiUtils;

/**
 * Created by user on 2015/11/9.
 */
public class ShaiHuoFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView mTextView =new TextView(UiUtils.getContext());
        mTextView.setText("我是ViewPager5");
        return mTextView ;
    }
}
