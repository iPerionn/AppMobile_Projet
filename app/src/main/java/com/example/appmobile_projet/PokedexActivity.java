package com.example.appmobile_projet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class PokedexActivity extends Fragment {
    private RecyclerView recyclerView; // la vue
    private RecyclerView.Adapter adapter; // l'adaptateur
    private RecyclerView.LayoutManager layoutManager; // le gesdtionnaire de mise en page

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        return inflater.inflate(R.layout.frag_pokedex, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstance){
        recyclerView = v.findViewById(R.id.listPokemon);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        List<Pokemon> skieurs = recupererDonnees();
        adapter = new PokemonAdapter(skieurs);
        recyclerView.setAdapter(adapter);
    }
    
    public List<Pokemon> recupererDonnees(){
        List<Pokemon> b =Arrays.asList(
                new Pokemon("patate","erererer",4,4,4,4,4,4),
                new Pokemon("pppp","erererer",4,4,4,4,4,4));
        return b;
    }
}
