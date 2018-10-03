package com.celebrateclub;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.celebrateclub.Model.Estabelecimento;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ListaEstabelecimentosActivity extends AppCompatActivity {
    private RecyclerView recyclerEstabelecimentos;

    private Query query, querySearch;
    private ImageView btnSearch;
    private AutoCompleteTextView editSearch;

    ArrayAdapter<String> autoComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_estabelecimentos);
        autoComplete();
        query = FirebaseDatabase.getInstance().getReference().child("estabelecimentos").orderByChild("nomeEstabelecimento");

        editSearch = findViewById(R.id.editSearch);
        btnSearch = findViewById(R.id.searchBtn);
        recyclerEstabelecimentos = findViewById(R.id.myrecyclerview);
        recyclerEstabelecimentos.setHasFixedSize(true);
        recyclerEstabelecimentos.setLayoutManager(new LinearLayoutManager(this));
        editSearch.setAdapter(autoComplete);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = editSearch.getText().toString();
                firebaseEstabelecimentoSearch(searchText);
            }
        });
    }

    private void autoComplete() {
        //Nothing special, create database reference.
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        //Create a new ArrayAdapter with your context and the simple layout for the dropdown menu provided by Android
        autoComplete = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        //Child the root before all the push() keys are found and add a ValueEventListener()
        database.child("estabelecimentos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Basically, this says "For each DataSnapshot *Data* in dataSnapshot, do what's inside the method.
                for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                    //Get the suggestion by childing the key of the string you want to get.
                    String suggestion = suggestionSnapshot.child("nomeEstabelecimento").getValue(String.class);
                    //Add the retrieved string to the list
                    autoComplete.add(suggestion);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void firebaseEstabelecimentoSearch(String searchText) {

        querySearch = FirebaseDatabase.getInstance().getReference().child("estabelecimentos").orderByChild("nomeEstabelecimento").startAt(searchText).endAt(searchText + "\uf8ff");
        FirebaseRecyclerAdapter<Estabelecimento, EstabelecimentoViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Estabelecimento, EstabelecimentoViewHolder>
                (Estabelecimento.class, R.layout.lista_estabelecimentos, EstabelecimentoViewHolder.class, querySearch) {
            @Override
            protected void populateViewHolder(EstabelecimentoViewHolder viewHolder, Estabelecimento model, int position) {

                viewHolder.setTitle(model.getNomeEstabelecimento());

                viewHolder.setEndereco(String.valueOf(model.getEnderecoEstabelecimento()));

            }
        };

        recyclerEstabelecimentos.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Estabelecimento, EstabelecimentoViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Estabelecimento, EstabelecimentoViewHolder>
                (Estabelecimento.class, R.layout.lista_estabelecimentos, EstabelecimentoViewHolder.class, query) {
            @Override
            protected void populateViewHolder(EstabelecimentoViewHolder viewHolder, Estabelecimento model, int position) {

                viewHolder.setTitle(model.getNomeEstabelecimento());

                viewHolder.setEndereco(String.valueOf(model.getEnderecoEstabelecimento()));

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

        public void setTitle(String title) {
            TextView post_title = view.findViewById(R.id.nomeEstabelecimento);
            post_title.setText(title);

        }



        public void setEndereco(String enderecoEstabelecimento) {
            TextView longitude = view.findViewById(R.id.ruaEstabelecimento);
            longitude.setText(enderecoEstabelecimento);

        }
    }
}

