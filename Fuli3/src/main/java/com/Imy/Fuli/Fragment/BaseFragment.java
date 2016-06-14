package com.Imy.Fuli.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Imy.Fuli.View.LoadingPage;
import com.Imy.Fuli.tools.BitmapHelper;
import com.Imy.Fuli.tools.LogUtils;
import com.Imy.Fuli.tools.ViewUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Administrator on 2015/9/28 0028.
 */

public abstract class BaseFragment extends Fragment {

    private LoadingPage loadingPage;

    protected BitmapUtils bitmapUtils;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        bitmapUtils = BitmapHelper.getBitmapUtils();

        if (loadingPage == null) {  // 之前的frameLayout 已经记录了一个爹了  爹是之前的ViewPager
            loadingPage = new LoadingPage(getActivity()) {

                @Override
                public View createSuccessView() {
                    return BaseFragment.this.createSuccessView();
                }

                @Override
                protected LoadResult load() {
                    LogUtils.w("load方法执行了");
                    return BaseFragment.this.load();
                }
            };
        } else {
            ViewUtils.removeParent(loadingPage);// 移除frameLayout之前的爹
        }

        return loadingPage;  //  拿到当前viewPager 添加这个framelayout
    }

    /***
     * 创建成功的界面
     *
     * @return
     */
    public abstract View createSuccessView();

    /**
     * 请求服务器
     *
     * @return
     */
    protected abstract LoadingPage.LoadResult load();

    public void show() {
        if (loadingPage != null) {
            LogUtils.w("BaseFragment 即将调用shou方法了");
            LogUtils.w("我是---------------------------------------------------------");
            loadingPage.show();
        }
    }
    public void setNavigationTitle(String title){

    }

    /**
     * 校验数据
     */
    public LoadingPage.LoadResult checkData(List datas) {
        if (datas == null) {
            return LoadingPage.LoadResult.error;//  请求服务器失败
        } else {
            if (datas.size() == 0) {
                return LoadingPage.LoadResult.empty;
            } else {
                return LoadingPage.LoadResult.success;
            }
        }

    }
    //获取Lodingpage对象
    public LoadingPage getLoadingPage() throws Exception {
        if (loadingPage!=null) {
            return loadingPage;
        }else{
             throw new Exception("lodingpage为空");
        }

    }
}
