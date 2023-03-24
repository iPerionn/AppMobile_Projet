package com.example.appmobile_projet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class MenuActivity extends Fragment implements View.OnClickListener{
    Button pokestreak;
    Button zoomon;
    Button pokedex;
    private Fragment frag_pokestreak;
    private Fragment frag_zoomon;
    private Fragment frag_pokedex;
    private static final int id_frag_pokestreak = 0;
    private static final int id_frag_zoomon = 1;
    private static final int id_frag_pokedex = 2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        return inflater.inflate(R.layout.frag_menu, container, false);
    }
    @Override
    public void onViewCreated(View v, Bundle savedInstance){
        pokestreak = v.findViewById(R.id.pokeStreak);
        zoomon = v.findViewById(R.id.zomMon);
        pokedex = v.findViewById(R.id.pokedex);
        pokestreak.setOnClickListener(this);
        zoomon.setOnClickListener(this);
        pokedex.setOnClickListener(this);
    }
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
    private void changeFragment(Fragment fragment){
        if(!fragment.isVisible()){
            getParentFragmentManager().beginTransaction().replace(R.id.fragmentG, fragment).commit();
        }
    }
}
