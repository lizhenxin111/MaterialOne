package com.lzx.materialone.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lzx.materialone.Bean.javascript.JSFunction;
import com.lzx.materialone.R;

import java.io.IOException;
import java.io.InputStream;

public class DetailMusicAct extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_music);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_music_toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        webView = (WebView)findViewById(R.id.detail_music_activity_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.loadUrl(getJsFromFile("Javascript/remove"));
                webView.loadUrl("javascript:remove()");
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new JSFunction(this), "webview");
        webView.loadUrl(getIntent().getStringExtra("url"));
    }

    private String getJsFromFile(String fileName){
        String js = null;
        try {
            InputStream inputStream = this.getAssets().open(fileName);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            inputStream.close();
            js = new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return js;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null){
            webView.clearCache(true);
            webView.clearHistory();
            webView.destroy();
            webView = null;
        }
    }
}
