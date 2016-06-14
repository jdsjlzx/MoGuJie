package com.Imy.Fuli.Activity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.Imy.Fuli.Fragment.BaseFragment;
import com.Imy.Fuli.Fragment.FragmentFactory;
import com.Imy.Fuli.R;
import com.Imy.Fuli.View.NoScrollViewPager;
import com.Imy.Fuli.tools.LogUtils;
import com.nostra13.universalimageloader.utils.L;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    public static final int TAB_HOME = 0;
    public static final int TAB_ME = 1;
    private NoScrollViewPager mViewPager;
    private String[] tab_names = new String[]{"首页","我的"};  // 标签的名字
    private static final String TEST_FILE_NAME = "Universal Image Loader @#&=+-_.,!()~'%20.png";
    //2个radiobutton
    private RadioButton mHomeButton;
    private RadioButton mMeButton;
    private RadioGroup mRadioGroup;


    public void initView(){

        super.initView();

        // 定义文件对象，目录：/mnt/sdcard, 文件名:TEST_FILE_NAME
        File testImageOnSdCard = new File("/mnt/sdcard", TEST_FILE_NAME);
        if (!testImageOnSdCard.exists()) {  // 如果文件不存在
            // 把文件复制到SD卡
            copyTestImageToSdCard(testImageOnSdCard);
        }
        mViewPager = (NoScrollViewPager) findViewById(R.id.vp_content);
        //获取RadioButton对象
        mHomeButton = (RadioButton) findViewById(R.id.rb_home);
        mMeButton = (RadioButton) findViewById(R.id.rb_me);
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_group);
        //设置监听
        mHomeButton.setOnClickListener(this);
        mMeButton.setOnClickListener(this);
        mViewPager.setAdapter(new MainAdpater(getSupportFragmentManager()));
        mViewPager.setCurrentItem(0);//设置当前viewpager位置为首页
        mHomeButton.setChecked(true);
        BaseFragment HomeFragment= FragmentFactory.createFragment(0);
        HomeFragment.show();
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                BaseFragment createFragment = FragmentFactory.createFragment(position);
                LogUtils.w("通过HomeActivity 执行了了shou方法");
                createFragment.show();//
            }

        });
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_home;
    }

    private void copyTestImageToSdCard(final File testImageOnSdCard) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is = getAssets().open(TEST_FILE_NAME);
                    FileOutputStream fos = new FileOutputStream(testImageOnSdCard);
                    byte[] buffer = new byte[8192];
                    int read;
                    try {
                        while ((read = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, read); // 写入输出流
                        }
                    } finally {
                        fos.flush();        // 写入SD卡
                        fos.close();        // 关闭输出流
                        is.close();         // 关闭输入流
                    }
                } catch (IOException e) {
                    L.w("Can't copy test image onto SD card");
                }
            }
        }).start();     // 启动线程
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mHomeButton.getId()) {
            mViewPager.setCurrentItem(0, true);//是否平滑
        } else if (v.getId() == mMeButton.getId()) {
            mViewPager.setCurrentItem(3, true);//是否平滑
        }
    }



    private class MainAdpater extends FragmentPagerAdapter {
        public MainAdpater(FragmentManager fm) {
            super(fm);
        }

        // 每个条目返回的fragment
        @Override
        public Fragment getItem(int position) {
            //  通过Fragment工厂  生产Fragment
            LogUtils.w("通过Fragment工厂 生产Fragment 当前position:" + position);
            BaseFragment fragment = FragmentFactory.createFragment(position);

            return fragment;

        }

        // 一共有几个条目
        @Override
        public int getCount() {
            return tab_names.length;
        }

        // 返回每个条目的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return tab_names[position];
        }
    }
    //获取HomeFragment

    public RadioGroup getRadioGroup(){
        return  mRadioGroup;
    }
    public LinearLayout getHomeActivityLinearLayout(){
        return (LinearLayout) findViewById(R.id.activity_home_ll);
    }

    /**
     * 不需要导航条
     * @return
     */
    @Override
    protected boolean hasActionBar() {
        return false;
    }

}
