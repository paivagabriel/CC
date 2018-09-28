package com.celebrateclub;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.Toast;

import com.celebrateclub.MapsConfig.Permissao;

public class PromocoesActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private WebView mWebView;
    private ProgressBar progressWebView;

    SharedPreferences sharedPreferences;
    Boolean firstTime = true;
    NetworkInfo activeNetwork;
    ConnectivityManager cm;
    boolean isConnected;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.promocoes:
mWebView.reload();
                    return true;
                case R.id.estabelecimentos:
                    startActivity(new Intent(PromocoesActivity.this, MapsActivity.class));
                    return true;

                case R.id.sobre_nos:
                    startActivity(new Intent(PromocoesActivity.this, SobreNosActivity.class));
                    return true;


            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promocoes);


        checkPermissions();
        cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {

        } else {
            Toast.makeText(this, "Ops... Verifique sua conexão de internet", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PromocoesActivity.this, SobreNosActivity.class));
            finish();
        }


        mTextMessage = (TextView) findViewById(R.id.message);

        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        progressWebView = findViewById(R.id.progressBar);


        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        mWebView.loadUrl("http://www.celebrateclub.com.br/sorteio/");

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

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            String[] permissoes = {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            };


            Permissao.validaPermissoes(222, this, permissoes);
            return;
        }
    }


}
