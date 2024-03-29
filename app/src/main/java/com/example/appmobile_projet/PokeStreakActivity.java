package com.example.appmobile_projet;

import android.app.AlertDialog;
import android.content.Context;
import android.media.MediaPlayer;
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
    private MediaPlayer mp;

    /**
     * lien avec la base de données
     * @param activity
     */
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        db = new DBHandler(activity);
    }

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
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        return inflater.inflate(R.layout.frag_pokestreak, container, false);
    }

    /**
     * Initialisation de la vue
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
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
    }
    int statGetForL = 0;
    int statGetForR = 0;

    /**
     * @param view The view that was clicked.
     */
    @Override // Appel lors d'un choix de l'utilisateur : instancie le score et lance le nouvelle affichage
    public void onClick(View view){
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
            if (points % 5 == 0 && points != 0 && db.addRandomPokemon()) {
                mp = MediaPlayer.create(view.getContext(), R.raw.add_new);
                mp.start();
                new AlertDialog.Builder(getContext())
                        .setTitle("Félicitations !")
                        .setMessage("Un nouveau pokémon a été ajouté à votre collection n'hésitez pas à faire un tours")
                        .show();
            }
            pokemonL = null;
            pokemonR = null;
            statRetenu = (int) (Math.random() *6)+1;
            setPokemons();
            score.setText("Votre série de victoire est de : "+points);
    }

    /**
     * @return
     */
    public Pokemon compare(){ // Compare en fonction d'une statistique choisi aléatoirement les 2 pokemons et retourne celui en possédant le plus
        if (statGetForL == statGetForR){
            return null;
        } else if (statGetForL > statGetForR) {
            return pokemonL;
        }else {
            return pokemonR;
        }
    }

    /**
     * Affiche la question associée avec les nouvelles caractéristiques
     * @param i
     */
    public void showNewQuestion(int i){
        String resp = "Probléme d'affichage :/";
        switch (i){
            case 1 :
                resp = "Quel Pokemon posséde le plus d'attaque ?";
                break;
            case 2 :
                resp = "Quel Pokemon posséde le plus d'attaque spécial ?";
                break;
            case 3 :
                resp = "Quel Pokemon posséde le plus de défense spéciale ?";
                break;
            case 4 :
                resp = "Quel Pokemon posséde le plus de défense  ?";
                break;
            case 5 :
                resp = "Quel Pokemon posséde le plus d'hp ?";
                break;
            case 6 :
                resp = "Quel Pokemon posséde le plus de vitesse  ?";
        }
        question.setText(resp);
    }

    /**
     * Créer et affiche 2 nouveaux pokémons
     */
    private void setPokemons(){
        requestOnAPI = new RequestTask();
        requestOnAPI.execute(String.valueOf((int)(Math.random() * 151) + 1));
        requestOnAPI = new RequestTask();
        requestOnAPI.execute(String.valueOf((int)(Math.random() * 151) + 1));
    }

    /**
     * Lors de la destruction du fragment on passe les pokémons a nul
     */
    @Override
    public void onDestroy() {
        pokemonL =null;
        pokemonR = null;
        super.onDestroy();
    }

    /**
     * Tache asynchrone qui permet l'affichage des pokémons et les instancie par la même occasion
     */
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