package com.celebrateclub;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.celebrateclub.Model.Estabelecimento;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListaEstabelecimentosActivity extends AppCompatActivity {
    private RecyclerView recyclerEstabelecimentos;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_estabelecimentos);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("estabelecimentos");
        mDatabase.keepSynced(true);
        recyclerEstabelecimentos = findViewById(R.id.myrecyclerview);
        recyclerEstabelecimentos.setHasFixedSize(true);
        recyclerEstabelecimentos.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Estabelecimento, EstabelecimentoViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Estabelecimento, EstabelecimentoViewHolder>
                (Estabelecimento.class, R.layout.lista_estabelecimentos, EstabelecimentoViewHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(EstabelecimentoViewHolder viewHolder, Estabelecimento model, int position) {

                viewHolder.setTitle(model.getNomeEstabelecimento());
                viewHolder.setLat(String.valueOf(model.getLatitude()));
                viewHolder.setLng(String.valueOf(model.getLongitude()));

            }
        };

        recyclerEstabelecimentos.setAdapter(firebaseRecyclerAdapter);
    }

    public static class EstabelecimentoViewHolder extends RecyclerView.ViewHolder {
        View view;

        public EstabelecimentoViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }
        public void setTitle(String title){
            TextView post_title = view.findViewById(R.id.nomeEstabelecimento);
            post_title.setText(title);

        }
        public void setLat(String latitudeString){
            TextView latitude = view.findViewById(R.id.enderecoEstabelecimentoLat);
            latitude.setText(latitudeString);

        }

        public void setLng(String longitudeString){
            TextView longitude = view.findViewById(R.id.enderecoEstabelecimentoLng);
            longitude.setText(longitudeString);

        }
    }
}

