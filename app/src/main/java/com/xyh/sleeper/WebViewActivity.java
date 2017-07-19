package com.xyh.sleeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.progress)
    ProgressBar progressBar;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        initView();
        Intent intent = getIntent();
        url = intent.getStringExtra("webUrl");
        webView.loadUrl(url);
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
//        progressBar.setMax(100);
        initWebView();

    }

    private void initWebView() {
        WebSettings settings = webView.getSettings();
        // 开启 JS 支持
        settings.setJavaScriptEnabled(true);
        // 支持屏幕缩放
        settings.setSupportZoom(true);
        // 设置出现缩放工具
        settings.setBuiltInZoomControls(true);
        // 不显示webview缩放按钮
        settings.setDisplayZoomControls(false);
        // 扩大比例的缩放
        settings.setUseWideViewPort(true);
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        settings.setDefaultTextEncodingName("utf-8");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("title","====:"+view.getTitle());
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                toolbar.setTitle(title);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
            // 返回键退回
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(webView.canGoBack()){
                    webView.goBack();
                }else {
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.stopLoading();//再次打开页面时，若界面没有消亡，会导致进度条不显示并且界面崩溃
            webView.onPause();
            webView.clearCache(true);
            webView.clearHistory();
            webView.removeAllViews();
            webView.destroyDrawingCache();
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.destroy();//这句由于有些在其他线程还没有结束，会导致空指针异常导致没办法使用
            webView = null;
        }
        ButterKnife.unbind(this);
    }
}
