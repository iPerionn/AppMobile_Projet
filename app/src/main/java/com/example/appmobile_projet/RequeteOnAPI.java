package com.example.appmobile_projet;

import android.os.AsyncTask;
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
public class RequeteOnAPI {
    Pokemon pokemonByID;

        // methode pour récupérer les nom de pokemon en fonction de leurs ID
    public Pokemon getPokemonByID(String string){
            RequestTask requestTask = new RequestTask();
            requestTask.execute(string);
            return pokemonByID;
    }
    private class RequestTask extends AsyncTask<String, Void, Pokemon> {

        protected Pokemon doInBackground(String ... value) {
            Pokemon response;
            try{
                JSONObject toDecode = new JSONObject(requeteFindPokemonByNumber(value[0]));
                response = getPokemon(toDecode);
            }catch (Exception e) {
                e.printStackTrace();
                response = null;
            }
            return response;
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
    private Pokemon getPokemon(JSONObject jso) throws Exception {
        Pokemon result;
        JSONObject stats = jso.getJSONObject("stats");
        result = new Pokemon(jso.getString("name"), jso.getString("image"), Integer.parseInt(stats.getString("HP")),
                stats.getInt("attack"),stats.getInt("defense"), stats.getInt("special_attack"),
                stats.getInt("special_defense"),stats.getInt("speed"));
        return result;
    }
    protected void onPostExecute(Pokemon result) {
        try {
            pokemonByID = result;
        }catch (Exception e) {
            e.printStackTrace();
            pokemonByID = null;
        }

    }
    }
}
