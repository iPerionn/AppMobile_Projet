package com.example.appmobile_projet;

import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolderPokemon extends RecyclerView.ViewHolder {
    private final TextView name;
    private final ImageView img;
    private Pokemon pokemon;
    private MediaPlayer best_unlocking;

    /**
     * Ajout de la gestion d'événement dans le recycleur view
     * @param itemView
     */
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
                if (pokemon.getId() == 151){
                    best_unlocking = MediaPlayer.create(view.getContext(), R.raw.best_unlocking);
                    best_unlocking.start();
                }
            }
        });
    }

    /**
     * Affichage des images associée aux pokémons par leur URL
     * @param pokemon
     */
    public void afficher(Pokemon pokemon){
        this.pokemon = pokemon;
        name.setText(pokemon.getName());
        Picasso.get()
                .load(pokemon.getImageURL())
                .into(img);
    }
}
