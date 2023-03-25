package com.example.appmobile_projet;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
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
import java.util.Arrays;
import java.util.List;
public class PokeStreakActivity extends Fragment implements View.OnClickListener{
    Pokemon pokemonL;
    Pokemon pokemonR;
    ImageButton imageButtonL;
    ImageButton imageButtonR;
    TextView textViewL;
    TextView textViewR;
    RequestTask requestOnAPI;
    TextView score;
    TextView question;
    View context;
    int points = 0;
    int statRetenu = 3;
    DBHandler db;
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        db = new DBHandler(activity);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        return inflater.inflate(R.layout.frag_pokestreak, container, false);
    }
    // A la création du fragment on créer 2 pokemon et on affiche leurs noms et leurs images
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //association des élément à leurs vues :
        context = getActivity().findViewById(android.R.id.content);
        score = view.findViewById(R.id.score);
        question = view.findViewById(R.id.qPokestreak);
        //création des 2 pokémons  :
        setPokemons();
        //association des vues du pokemon de gauche :
        textViewL = view.findViewById(R.id.name_pokemonL);
        imageButtonL = view.findViewById(R.id.img_pokemonL);
        //association des vues du pokemon de droite :
        textViewR = view.findViewById(R.id.name_pokemonR);
        imageButtonR = view.findViewById(R.id.img_pokemonR);
        //ajout des évenements lié au bouttons
        imageButtonL.setOnClickListener(this);
        imageButtonR.setOnClickListener(this);

        imageButtonR.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(v.getContext()).setMessage(pokemonR.toString()+"\n"+statGetForR).show();
                return true;
            }
        });
        imageButtonL.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(v.getContext()).setMessage(pokemonL.toString()+"\n"+statGetForL).show();
                return true;
            }
        });
    }
    int statGetForL = 0;
    int statGetForR = 0;
    @Override // Appel lors d'un choix de l'utilisateur : instancie le score et lance le nouvelle affichage
    public void onClick(View view){
        try {
            switch (view.getId()){
                case R.id.img_pokemonL:
                    if (compare() == pokemonL || compare() == null){
                        points += 1;
                    }else {
                        points = 0;
                    }
                    break;
                case R.id.img_pokemonR:
                    if (compare() == pokemonR || compare() == null){
                        points += 1;
                    }else {
                        points = 0;
                    }
                    break;
            }
            if (points % 5 == 0 && points != 0 ) {
                db.addRandomPokemon();
                new AlertDialog.Builder(getContext())
                        .setTitle("Félicitations !")
                        .setMessage("Un nouveau pokémon a été ajouté à votre collection n'hésitez pas à faire un tours")
                        .setMessage("Un nouveau pokémon a été ajouté à votre collection n'hésitez pas à faire un tours")
                        .show();
            }
            pokemonL = null;
            pokemonR = null;
            statRetenu = (int) (Math.random() *5)+1;
            setPokemons();
            score.setText("Votre série de victoire est de : "+points);
        }catch (Exception e){
            Snackbar.make(context,"Attention vous avez cliquer trop vite ! Cela peut entrainer des erreurs",Snackbar.LENGTH_LONG).show();
        }
    }
    public Pokemon compare(){ // Compare en fonction d'une statistique choisi aléatoirement les 2 pokemons et retourne celui en possédant le plus
        if (statGetForL == statGetForR){
            return null;
        } else if (statGetForL > statGetForR) {
            return pokemonL;
        }else {
            return pokemonR;
        }
    }
    public void showNewQuestion(int i){
        String resp = "Probléme d'affichage :/";
        switch (i){
            case 0 :
                resp = "Quel Pokemon posséde le plus d'attaque ?";
                break;
            case 1 :
                resp = "Quel Pokemon posséde le plus d'attaque spécial ?";
                break;
            case 2 :
                resp = "Quel Pokemon posséde le plus de défense spéciale ?";
                break;
            case 3 :
                resp = "Quel Pokemon posséde le plus de défense  ?";
                break;
            case 4 :
                resp = "Quel Pokemon posséde le plus d'hp ?";
                break;
            case 5 :
                resp = "Quel Pokemon posséde le plus de vitesse  ?";
        }
        question.setText(resp);
    }
    private void setPokemons(){
        requestOnAPI = new RequestTask();
        requestOnAPI.execute(String.valueOf((int)(Math.random() * 151) + 1));
        requestOnAPI = new RequestTask();
        requestOnAPI.execute(String.valueOf((int)(Math.random() * 151) + 1));
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
            result = new Pokemon(jso.getInt("id"),jso.getString("name"), jso.getString("image"), stats.getInt("HP"),
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
                statGetForL = pokemonL.getCarctFromInt(statRetenu);
                Picasso.get()
                        .load(pokemonL.getImageURL())
                        .into(imageButtonL);
            } else if (pokemonR == null) {
                pokemonR =pokemon;
                textViewR.setText(pokemonR.getName());
                statGetForR = pokemonR.getCarctFromInt(statRetenu);
                showNewQuestion(statRetenu);
                Picasso.get()
                        .load(pokemonR.getImageURL())
                        .into(imageButtonR);
            }
        }
    }
}