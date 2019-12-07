package com.marwaeltayeb.movietrailer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.marwaeltayeb.movietrailer.R;

import static com.marwaeltayeb.movietrailer.R.id.webView;
import static com.marwaeltayeb.movietrailer.utils.Constant.URL_OF_REVIEW;

public class WebViewActivity extends AppCompatActivity {

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        final ProgressBar loadingIndicator = findViewById(R.id.indicator);
        loadingIndicator.setVisibility(View.VISIBLE);
        WebView web = findViewById(webView);

        Intent intent = getIntent();
        url = intent.getStringExtra(URL_OF_REVIEW);
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
