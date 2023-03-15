package com.example.appmobile_projet;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.squareup.picasso.Picasso;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class PokeStreakActivity extends Fragment implements View.OnClickListener{
    Pokemon pokemonL;
    Pokemon pokemonR;
    ImageButton imageButtonL;
    ImageButton imageButtonR;
    TextView textViewL;
    TextView textViewR;
    RequestTask requestOnAPI;
    int points;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        return inflater.inflate(R.layout.frag_pokestreak, container, false);
    }
    // A la création du fragment on créer 2 pokemon et on affiche leurs noms et leurs images
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // création du pokemon de gauche :
        requestOnAPI = new RequestTask();
        textViewL = view.findViewById(R.id.name_pokemonL);
        imageButtonL = view.findViewById(R.id.img_pokemonL);
        requestOnAPI.execute(String.valueOf((int)(Math.random() * 151) + 1));
        //création du pokemon de droite :
        requestOnAPI = new RequestTask();
        textViewR = view.findViewById(R.id.name_pokemonR);
        imageButtonR = view.findViewById(R.id.img_pokemonR);
        requestOnAPI.execute(String.valueOf((int)(Math.random() * 151) + 1));
        imageButtonL.setOnClickListener(this);
        imageButtonR.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        TextView score = view.findViewById(R.id.score);
        switch (view.getId()){
            case R.id.img_pokemonL:
                if (compare() == pokemonL){
                    pokemonR = null;
                    points += 1;
                }else {
                    pokemonL =null;
                    points = 0;
                }
                break;
            case R.id.img_pokemonR:
                if (compare() == pokemonR){
                pokemonL = null;
                points += 1;
            }else {
                pokemonR =null;
                points = 0;
            }
                break;
        }
        requestOnAPI = new RequestTask();
        requestOnAPI.execute(String.valueOf((int)(Math.random() * 151) + 1));
        //score.setText("Votre série de victoire est de : "+points);
    }
    public Pokemon compare(){
        if (pokemonR.getAttack() == pokemonL.getAttack()){
            return null;
        } else if (pokemonL.getAttack() > pokemonR.getAttack()) {
            return pokemonL;
        }else return pokemonR;
    }
    @Override
    public void onDestroy() {
        pokemonL =null;
        pokemonR = null;
        super.onDestroy();
    }
    private class RequestTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... number) {
            return requeteFindPokemonByNumber(number[0]);
        }
        private String requeteFindPokemonByNumber(String number) {
            String response = "";
            try {
                HttpURLConnection connection;
                URL url = new URL("https://pokebuildapi.fr/api/v1/pokemon/" + URLEncoder.encode(number, "utf-8"));
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String ligne = bufferedReader.readLine();
                while (ligne != null) {
                    response += ligne;
                    ligne = bufferedReader.readLine();
                }
            } catch (UnsupportedEncodingException e) {
                response = "problème d'encodage";
            } catch (MalformedURLException e) {
                response = "problème d'URL ";
            } catch (IOException e) {
                response = "problème de connexion ";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
        private Pokemon decodePokemon(JSONObject jso) throws Exception {
            Pokemon result;
            JSONObject stats = jso.getJSONObject("stats");
            result = new Pokemon(jso.getString("name"), jso.getString("image"), Integer.parseInt(stats.getString("HP")),
                    stats.getInt("attack"), stats.getInt("defense"), stats.getInt("special_attack"),
                    stats.getInt("special_defense"), stats.getInt("speed"));
            return result;
        }
        protected void onPostExecute(String result) {
            JSONObject toDecode;
            try {
                toDecode = new JSONObject(result);
                setView(decodePokemon(toDecode));

            } catch (Exception e) {
                e.printStackTrace();
                pokemonL = null;
                pokemonR = null;
            }
        }
        private void setView(Pokemon pokemon) {
            if(pokemonL == null){
                pokemonL =pokemon;
                textViewL.setText(pokemonL.getName());
                Picasso.get()
                        .load(pokemonL.getImageURL())
                        .into(imageButtonL);
            } else if (pokemonR == null) {
                pokemonR =pokemon;
                textViewR.setText(pokemonR.getName());
                Picasso.get()
                        .load(pokemonR.getImageURL())
                        .into(imageButtonR);
            }
        }
    }
}

