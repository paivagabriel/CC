package com.celebrateclub;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.celebrateclub.MapsConfig.CustomInfoWindow;
import com.celebrateclub.MapsConfig.Permissao;
import com.celebrateclub.Model.Estabelecimento;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location l;
    NetworkInfo activeNetwork;
    ConnectivityManager cm;
    boolean isConnected;

    Estabelecimento estabelecimento = new Estabelecimento();

    private ValueEventListener eventListener;
    DatabaseReference myRef;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mUsers;
    Marker marker;
    public String geoUri;

    double latWaze;
    double longWaze;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);




        mUsers = FirebaseDatabase.getInstance().getReference("estabelecimentos");
        mUsers.push().setValue(marker);


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("estabelecimentos");

        verificarConexaoInternet();


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.aoredor:
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(l.getLatitude(), l.getLongitude()), 18.0f));
                        return true;
                    case R.id.regiao:
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(l.getLatitude(), l.getLongitude()), 10.0f));
                        return true;


                }
                return false;
            }
        };


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationMap);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }



    private void verificarConexaoInternet() {
        cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {

        } else {
            Toast.makeText(this, "Ops... Verifique sua conexão de internet", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MapsActivity.this, SobreNosActivity.class));
        }

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        checarPermissoes();

        mUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    final Estabelecimento estabelecimento = s.getValue(Estabelecimento.class);
                    LatLng location = new LatLng(estabelecimento.getLatitude(), estabelecimento.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(location)
                            .title(estabelecimento.getNomeEstabelecimento()).snippet("Clique para navegar até o local").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));

                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            geoUri = "http://maps.google.com/maps?q=loc:" + marker.getPosition().latitude + "," + marker.getPosition().longitude + " (" + marker.getTitle() + ")";
                            latWaze = marker.getPosition().latitude;
                            longWaze = marker.getPosition().longitude;
                            mostrarDialogNavegacao();


                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void mostrarDialogNavegacao() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View view = inflater.inflate(R.layout.dialog_navegacao, null, false);
        final ImageView ivWaze = view.findViewById(R.id.ivWaze);
        final ImageView ivMaps = view.findViewById(R.id.ivMaps);
        builder.setView(view);

        ivWaze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Launch Waze to look for Hawaii:
                    String url = "https://www.waze.com/ul?ll=" + latWaze + "%2C" + longWaze + "&navigate=yes&zoom=17";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
                    startActivity(intent);
                }
            }
        });
        ivMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent_local = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                    intent_local.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                    intent_local.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                    intent_local.setData(Uri.parse(geoUri));
                    startActivity(intent_local);
                } catch (ActivityNotFoundException ex) {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.apps.maps"));
                    startActivity(intent);
                }


            }
        });


        builder.show();
    }

    private void checarPermissoes() {
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


        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    l = location;
                    LatLng userLocation = new LatLng(l.getLatitude(), l.getLongitude());


                    if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mMap.setMyLocationEnabled(true);


                    CameraPosition position = new CameraPosition.Builder()
                            .target(userLocation)
                            .bearing(0)
                            .tilt(0)
                            .zoom(15)
                            .build();

                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);


                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(l.getLatitude(), l.getLongitude()), 10.0f));


                } else {
                    Toast.makeText(MapsActivity.this, "Problemas ao capturar localização.", Toast.LENGTH_SHORT).show();
                }


            }


        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;


    }
}

