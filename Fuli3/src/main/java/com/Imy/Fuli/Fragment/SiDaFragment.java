package com.Imy.Fuli.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import com.Imy.Fuli.Activity.ViewPlayDetailsActivity;
import com.Imy.Fuli.Adapter.SiDaListViewAdapter;
import com.Imy.Fuli.Bean.ListInfo;
import com.Imy.Fuli.R;
import com.Imy.Fuli.View.HorizontalListView;

import com.Imy.Fuli.common.Constant;
import com.Imy.Fuli.View.MultiColumnListView;
import com.Imy.Fuli.lib.internal.PLA_AdapterView;
import com.Imy.Fuli.tools.UiUtils;

import java.util.ArrayList;

/**
 * Created by user on 2015/11/9.
 */
public class SiDaFragment extends Fragment {
    private ArrayList<ListInfo> datas;
    private ArrayAdapter<String> adapter;
    private MultiColumnListView mListView;
    private static HorizontalListView mIndicator = null;
    private static final String[] TITLE = new String[]{"第一个", "第二个", "第三个", "第四个", "第五个", "第六个", "第七个", "第八个",
            "第九个", "第十个", "第11个", "第12个", "第13个", "第14个", "第15个", "第16个", "第17个", "第18个",
            "第19个", "第20个", "第21个", "第22个", "第23个", "第24个", "第25个", "第26个", "第27个", "第28个",
            "第29个", "第30个"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(UiUtils.getContext(), R.layout.sida_viewpager_item, null);
        mIndicator = Constant.MY_INDICATOR;
        mListView = (MultiColumnListView) view.findViewById(R.id.sida_viewpage_listview);
//        mListView.setHead(mIndicator);

        mListView.setOnItemClickListener(new PLA_AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(PLA_AdapterView<?> parent, View view, int position, long id) {
                UiUtils.showToast("当前为第"+position+"个");
                startActivity(new Intent(getActivity(), ViewPlayDetailsActivity.class));

            }
        });
        initData();

        mListView.setAdapter(new SiDaListViewAdapter(datas));
        return view;
    }

    private void initData() {
        datas = new ArrayList<ListInfo>();
        for (int i = 0; i < Constant.url.length; i++) {
            String title = TITLE[i];
            String icon = Constant.url[i];
            ListInfo info = new ListInfo(title, icon);
            datas.add(info);
        }
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        //得到屏幕宽
        int width = wm.getDefaultDisplay().getWidth();
        Constant.DisplayWidth = width;

    }


}
