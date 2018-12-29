package com.example.taiwantrafficassistant.controller.mrt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.taiwantrafficassistant.R;

public class MrtMapWebviewActivity extends AppCompatActivity {
    private WebView mMrtMapWebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrt_map_webview);
        setTitle("MRT路線圖");
        mMrtMapWebview = findViewById(R.id.wv_mrt_webview);
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
        mMrtMapWebview.loadUrl("https://m.metro.taipei/roadmap.asp");
    }

}
