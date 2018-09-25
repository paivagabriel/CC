package com.celebrateclub.MapsConfig;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.celebrateclub.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter{
    private Context context;




    public CustomInfoWindow(Context ctx) {
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity) context).getLayoutInflater()
                .inflate(R.layout.info_window, null);

        TextView tvEstabelecimento = view.findViewById(R.id.tvNomeEstabelecimento);



        if (marker.getTitle().equals("Garoa")) {
            tvEstabelecimento.setText("Garoa Bar");

        }
        if (marker.getTitle().equals("Via Reggio")) {
            tvEstabelecimento.setText("Via Reggio");

        }
        if (marker.getTitle().equals("Tipo Prime")) {
            tvEstabelecimento.setText("Tipo Prime");

        }
        if (marker.getTitle().equals("Joakins")) {
            tvEstabelecimento.setText("Joakins");
        }
        if (marker.getTitle().equals("Digi Alpha")) {
            tvEstabelecimento.setText("Digi Alpha");
        }

        if (marker.getTitle().equals("Dialetto")) {
            tvEstabelecimento.setText("Dialetto");
        }

        if (marker.getTitle().equals("Esmalteria Nacional")) {
            tvEstabelecimento.setText("Esmalteria Nacional");
        }
        if (marker.getTitle().equals("VIG Studio Hair")) {
            tvEstabelecimento.setText("VIG Studio Hair");
        }
        if (marker.getTitle().equals("Não+Pêlo-")) {
            tvEstabelecimento.setText("Não+Pêlo-");
        }
        if (marker.getTitle().equals("Marcio Soares Podólogo")) {
            tvEstabelecimento.setText("Marcio Soares Podólogo");
        }
        if (marker.getTitle().equals("Barbearia Macchina")) {
            tvEstabelecimento.setText("Barbearia Macchina");
        }
        if (marker.getTitle().equals("Valentinas Esmalteria e Depilação")) {
            tvEstabelecimento.setText("Valentinas Esmalteria e Depilação");
        }

        return view;
    }


}
