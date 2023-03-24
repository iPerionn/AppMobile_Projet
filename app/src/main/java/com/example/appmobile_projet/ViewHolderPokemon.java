package com.example.appmobile_projet;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderPokemon extends RecyclerView.ViewHolder {
    private final TextView name;
    private Pokemon pokemon;
    public ViewHolderPokemon(final View itemView) {
        super(itemView);

        name = (itemView.findViewById(R.id.name));
    }
    public void afficher(Pokemon pokemon){
        this.pokemon = pokemon;
        name.setText(pokemon.getName());
    }
}
