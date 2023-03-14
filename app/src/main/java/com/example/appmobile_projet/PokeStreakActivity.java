package com.example.appmobile_projet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class PokeStreakActivity extends Fragment {
    RequeteOnAPI requeteOnAPI = new RequeteOnAPI();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        return inflater.inflate(R.layout.frag_pokestreak, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        int i = (int) (Math.random() * 151) + 1;
        TextView nomPokeL =view.findViewById(R.id.name_pokemonL);
        Pokemon pokeL = requeteOnAPI.getPokemonByID(Integer.toString(i));
        nomPokeL.setText(pokeL.getName());
    }


}
