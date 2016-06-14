package com.Imy.Fuli.Activity;

import android.os.Build;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.Imy.Fuli.R;

/**
 * Created by user on 2015/11/24.
 */
public class ViewPlayDetailsActivity extends BaseActivity {
    private WebView mWebView;
    private String mTitle;
    private String mUrl = "http://pan.baidu.com/s/1pJw4X6r";

    /**
     * 初始化数据
     */
    @Override
    public void init() {
        super.init();

    }



    /**
     * 初始化视图
     */
    @Override
    public void initView() {
        super.initView();
        mWebView = (WebView) findViewById(R.id.viewplaydetails_webview);
        final WebSettings webSettings = mWebView.getSettings();
        mWebView.clearCache(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setAppCacheEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        mWebView.setWebViewClient(webViewClient);
        mWebView.loadUrl(mUrl);


    }
    private WebViewClient webViewClient = new WebViewClient() {

    };
    protected boolean isBtnBackVisible() {
        return true;
    }
    @Override
    public int getLayoutID() {
        return R.layout.activity_listview_details;
    }
}
