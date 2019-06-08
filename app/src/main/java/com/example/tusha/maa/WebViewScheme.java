package com.example.tusha.maa;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewScheme extends AppCompatActivity {

    ProgressDialog pDailog;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_scheme);

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        pDailog = new ProgressDialog(WebViewScheme.this);
        pDailog.setMessage("Loading..");

        webView.loadUrl("https://drive.google.com/open?id=1G9moUPt53U_-a_Gz-RHb1JtT1Y-Q91Kn");
    }
}
