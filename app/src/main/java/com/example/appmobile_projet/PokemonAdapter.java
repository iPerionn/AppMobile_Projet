package com.example.appmobile_projet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<ViewHolderPokemon> {
    private List<Pokemon> pokemons = null;

    /**
     * @param pokemons
     */
    public PokemonAdapter(List<Pokemon> pokemons){
        if (pokemons != null){
            this.pokemons = pokemons;
        }
    }

    /**
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @NonNull
    @Override
    public ViewHolderPokemon onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.pokemon_layout,parent,false);
        return new ViewHolderPokemon(view);
    }

    /**
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderPokemon holder, int position) {
        Pokemon pokemon = pokemons.get(position);
        holder.afficher(pokemon);
    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if(pokemons != null) return pokemons.size();
        return 0;
    }
}
