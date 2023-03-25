package com.example.appmobile_projet;

import android.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolderPokemon extends RecyclerView.ViewHolder {
    private final TextView name;
    private final ImageView img;
    private Pokemon pokemon;
    public ViewHolderPokemon(final View itemView) {
        super(itemView);
        img = (itemView.findViewById(R.id.pokedex_img));
        name = (itemView.findViewById(R.id.name));
        itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(itemView.getContext())
                        .setTitle(pokemon.getName())
                        .setMessage(pokemon.toString())
                        .show();
            }
        });
    }
    public void afficher(Pokemon pokemon){
        this.pokemon = pokemon;
        name.setText(pokemon.getName());
        Picasso.get()
                .load(pokemon.getImageURL())
                .into(img);
    }
}
