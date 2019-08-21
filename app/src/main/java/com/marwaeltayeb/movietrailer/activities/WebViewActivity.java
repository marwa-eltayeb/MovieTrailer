package com.marwaeltayeb.movietrailer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.marwaeltayeb.movietrailer.R;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        final ProgressBar loadingIndicator = findViewById(R.id.indicator);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        loadingIndicator.setVisibility(View.VISIBLE);
        WebView web = findViewById(R.id.webView);
        web.setWebViewClient(new WebViewClient());
        web.loadUrl(url);

        web.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                // Hide Loading Indicator
                loadingIndicator.setVisibility(View.GONE);
            }
        });



    }
}
