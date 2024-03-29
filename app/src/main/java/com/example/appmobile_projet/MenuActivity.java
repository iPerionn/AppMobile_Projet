package com.example.appmobile_projet;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class MenuActivity extends Fragment implements View.OnClickListener{
    Button pokestreak;
    Button zoomon;
    Button pokedex;
    private Fragment frag_pokestreak;
    private Fragment frag_zoomon;
    private Fragment frag_pokedex;
    private WebView actus;
    private static final int id_frag_pokestreak = 0;
    private static final int id_frag_zoomon = 1;
    private static final int id_frag_pokedex = 2;

    /**
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstance If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return l'affichage du fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        return inflater.inflate(R.layout.frag_menu, container, false);
    }
    /**
     *
     * @param v The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstance If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(View v, Bundle savedInstance){
        pokestreak = v.findViewById(R.id.pokeStreak);
        zoomon = v.findViewById(R.id.zomMon);
        pokedex = v.findViewById(R.id.pokedex);
        pokestreak.setOnClickListener(this);
        zoomon.setOnClickListener(this);
        pokedex.setOnClickListener(this);
        actus = v.findViewById(R.id.actu);

        actus.loadUrl("https://www.pokemon.com/fr/actus-pokemon");
        actus.getSettings().setJavaScriptEnabled(true);
        /**
         *  Affichage détailler des régles lors d'un appuis long sur les différents bouttons
         */
        zoomon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("ZooMon")
                        .setIcon(R.drawable.zoom)
                        .setMessage("Info du jeu : \n\nDevinez le nom du pokémon qui s'affiche et tous les 5 points vous débloquez un nouveau pokemon dans votre pokedex")
                        .show();
                return true;
            }
        });

        pokestreak.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Pokestreak")
                        .setIcon(R.drawable.choice)
                        .setMessage("Info du jeu : \n\nDevinez le pokemon qui a le plus de stat dans la catéorie définie : HP, ATT, DEF, ... \nTous les 5 points vous débloquerez un nouveau pokemon dans votre pokedex.")
                        .show();
                return true;
            }
        });

        pokedex.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Pokedex")
                        .setIcon(R.drawable.pokedex)
                        .setMessage("Info : \n\nDécouvrez la liste des pokemons que vous avez débloqué")
                        .show();
                return true;
            }
        });
    }

    /**
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pokeStreak:
                joinFragToId(id_frag_pokestreak);
                break;
            case R.id.zomMon:
                joinFragToId(id_frag_zoomon);
                break;
            case R.id.pokedex:
                joinFragToId(id_frag_pokedex);
                break;
        }
    }

    /**
     *
     * @param fragID
     */
    private void joinFragToId(int fragID){
        switch (fragID){
            case id_frag_pokestreak:
                if(frag_pokestreak == null) frag_pokestreak = new PokeStreakActivity();
                changeFragment(frag_pokestreak);
                break;
            case id_frag_zoomon:
                if(frag_zoomon == null) frag_zoomon = new ZooMonActivity();
                changeFragment(frag_zoomon);
                break;
            case id_frag_pokedex:
                if(frag_pokedex == null) frag_pokedex = new PokedexActivity();
                changeFragment(frag_pokedex);
                break;
        }
    }

    /**
     * permet le changement de fragments
     * @param fragment
     */
    private void changeFragment(Fragment fragment){
        if(!fragment.isVisible()){
            getParentFragmentManager().beginTransaction().replace(R.id.fragmentG, fragment).commit();
        }
    }
}
