package com.example.taiwantrafficassistant.controller.mrt;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.taiwantrafficassistant.R;

import static android.view.KeyEvent.KEYCODE_BACK;

public class MrtMapWebviewActivity extends AppCompatActivity {
    private WebView mMrtMapWebview;
    private TextView tvLayer;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrt_map_webview);
        setTitle("MRT路線圖");
        mMrtMapWebview = findViewById(R.id.wv_mrt_webview);
        //layer = findViewById(R.id.imageView6);
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
        //webSettings.setBuiltInZoomControls(true);
        mMrtMapWebview.canGoBack();
        mMrtMapWebview.setWebViewClient(new WebViewClient());
        mMrtMapWebview.loadUrl("https://m.metro.taipei/roadmap.asp");
        mMrtMapWebview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);
                System.out.println("1");
                mMrtMapWebview.setVisibility(View.INVISIBLE);
                //tvLayer.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
                String javascript =  "javascript:function hideOther() {" +
                        "document.getElementById('header').style.display = 'none';" +
                        "document.getElementById('footer').style.display = 'none';" +
                        "document.getElementsByClassName('color2')[0].style.display='none';" +
                        "document.getElementsByTagName('map')[0].remove();" +
                        "}";

                //document.getElementsByClassName('color2')[0].style.display='none'
                view.loadUrl(javascript);
                view.loadUrl("javascript:hideOther();");
                //layer.setVisibility(View.INVISIBLE);

                System.out.println("2");


                try{
                    // delay 1 second
                    Thread.sleep(1000);

                } catch(InterruptedException e){
                    e.printStackTrace();

                }
                mMrtMapWebview.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.GONE);
                //tvLayer.setVisibility(View.INVISIBLE);
            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mMrtMapWebview.canGoBack()) {
            mMrtMapWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
