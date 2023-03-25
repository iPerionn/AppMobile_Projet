package com.example.appmobile_projet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class PokedexActivity extends Fragment {
    private RecyclerView recyclerView; // la vue
    private RecyclerView.Adapter adapter; // l'adaptateur
    private RecyclerView.LayoutManager layoutManager; // le gestionnaire de mise en page
    private TextView pokedexInfo;
    DBHandler db;
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        db = new DBHandler(activity);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        return inflater.inflate(R.layout.frag_pokedex, container, false);
    }
    @Override
    public void onViewCreated(View v, Bundle savedInstance){
        pokedexInfo = v.findViewById(R.id.pokedexInfo);
        setTextInfo(pokedexInfo);

        recyclerView = v.findViewById(R.id.listPokemon);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        List<Pokemon> pokemons = db.selectAll();
        adapter = new PokemonAdapter(pokemons);
        recyclerView.setAdapter(adapter);
    }

    public void setTextInfo(TextView tv) {
        if (db.selectAll().size() ==0 ) {
            tv.setText("Vous n'avez pas encore de pokémons dans votre pokedex, n'hesitez pas à jouez à nos mini-jeux pour en débloquer !");
            tv.setTextSize(25);
        } else if (db.selectAll().size() ==151) {
            tv.setTextSize(15);
            tv.setText("Vous avez débloquez tout les pokémons disponibles d'autres seront disponible dans les mises à jours à venir !");
        }else {
            tv.setText("Vous avez : "+db.selectAll().size()+", sur 151 disponibles. Continuez à jouez pour en débloquer de nouveau !");
        }
    }
}
