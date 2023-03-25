package com.example.appmobile_projet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<ViewHolderPokemon> {
    private List<Pokemon> pokemons = null;

    public PokemonAdapter(List<Pokemon> pokemons){
        if (pokemons != null){
            this.pokemons = pokemons;
        }
    }
    @NonNull
    @Override
    public ViewHolderPokemon onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.pokemon_layout,parent,false);
        return new ViewHolderPokemon(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolderPokemon holder, int position) {
        Pokemon pokemon = pokemons.get(position);
        holder.afficher(pokemon);
    }
    @Override
    public int getItemCount() {
        if(pokemons != null) return pokemons.size();
        return 0;
    }
}
