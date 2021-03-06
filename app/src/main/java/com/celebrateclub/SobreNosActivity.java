package com.celebrateclub;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.celebrateclub.Utils.BaseActivity;

public class SobreNosActivity extends AppCompatActivity {

    NetworkInfo activeNetwork;
    ConnectivityManager cm;
    boolean isConnected;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.home:
                    cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                    activeNetwork = cm.getActiveNetworkInfo();
                    isConnected = activeNetwork != null &&
                            activeNetwork.isConnectedOrConnecting();

                    if (isConnected) {
                        startActivity(new Intent(SobreNosActivity.this, HomeActivity.class));
                    } else {
                        Toast.makeText(SobreNosActivity.this, "Ops... Verifique sua conexão de internet", Toast.LENGTH_SHORT).show();

                    }
                    return true;
                case R.id.promocoes:
                    cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                    activeNetwork = cm.getActiveNetworkInfo();
                    isConnected = activeNetwork != null &&
                            activeNetwork.isConnectedOrConnecting();

                    if (isConnected) {
                        startActivity(new Intent(SobreNosActivity.this, PromocoesActivity.class));
                    } else {
                        Toast.makeText(SobreNosActivity.this, "Ops... Verifique sua conexão de internet", Toast.LENGTH_SHORT).show();

                    }
                    return true;

                case R.id.estabelecimentos:

                    cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                    activeNetwork = cm.getActiveNetworkInfo();
                    isConnected = activeNetwork != null &&
                            activeNetwork.isConnectedOrConnecting();

                    if (isConnected) {
                        startActivity(new Intent(SobreNosActivity.this, MapsActivity.class));
                    } else {
                        Toast.makeText(SobreNosActivity.this, "Ops... Verifique sua conexão de internet", Toast.LENGTH_SHORT).show();

                    }

                    return true;

                case R.id.sobre_nos:

                    return true;


            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_nos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mailIntent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:?subject=" + "&to=" + "suporte@celebrateclub.com");
                mailIntent.setData(data);
                startActivity(Intent.createChooser(mailIntent, "Contato Celebrate Club"));
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
