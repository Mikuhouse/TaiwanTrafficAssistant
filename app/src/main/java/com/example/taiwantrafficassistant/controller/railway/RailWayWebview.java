package com.example.taiwantrafficassistant.controller.railway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.taiwantrafficassistant.R;

import static android.view.KeyEvent.KEYCODE_BACK;

public class RailWayWebview extends AppCompatActivity {
    WebView mMrtMapWebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rail_way_webview);
        setTitle("台鐵行動版網頁");
        mMrtMapWebview = findViewById(R.id.wv_railway_webview);
        //mMrtMapWebview.getSettings().setJavaScriptEnabled(true);
        /*
        mMrtMapWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });
        */
        WebSettings webSettings = mMrtMapWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true);
        mMrtMapWebview.canGoBack();
        mMrtMapWebview.setWebViewClient(new WebViewClient());
        mMrtMapWebview.loadUrl("http://twtraffic.tra.gov.tw/twrail/mobile/home.aspx");
        /*
        mMrtMapWebview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String javascript =  "javascript:function hideOther() {" +
                        "document.getElementsByTagName('div')[1].style.display='none';" +
                        "document.getElementsByTagName('div')[6].style.display='none';}"
                        ;
                view.loadUrl(javascript);
                view.loadUrl("javascript:hideOther();");
            }
        });
        */
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mMrtMapWebview.canGoBack()) {
            mMrtMapWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
