package com.celebrateclub;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.celebrateclub.MapsConfig.Permissao;


public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private WebView mWebView;
    private ProgressBar progressWebView;
    private AlertDialog alerta;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:

                    return true;
                case R.id.estabelecimentos:
                    startActivity(new Intent(HomeActivity.this, MapsActivity.class));
                    return true;

                case R.id.sobre_nos:
                    startActivity(new Intent(HomeActivity.this, SobreNosActivity.class));
                    return true;
                case R.id.promocoes:
                    startActivity(new Intent(HomeActivity.this, PromocoesActivity.class));
                    return true;


            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        validarPermissoes();


        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        progressWebView = findViewById(R.id.progressBar);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        mWebView.loadUrl("https://rh.mazza.tech/web/guest/webuser");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                mWebView.setVisibility(View.VISIBLE);
                progressWebView.setVisibility(View.INVISIBLE);
                super.onPageFinished(view, url);
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }


    private void validarPermissoes() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            String[] permissoes = {
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            };


            Permissao.validaPermissoes(222, this, permissoes);
            return;
        }

    }


}
