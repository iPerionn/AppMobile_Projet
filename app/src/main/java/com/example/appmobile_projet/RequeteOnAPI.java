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
import java.net.URLEncoder;/*
public class RequeteOnAPI {
    Pokemon pokemonByID;
    String namePokemon;

        // methode pour récupérer les nom de pokemon en fonction de leurs ID
    public Pokemon getPokemonByID(String string){
            RequestTask requestTask = new RequestTask();
            requestTask.execute(string);
            return pokemonByID;
    }
    private class RequestTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... number) {
            String response =requeteFindPokemonByNumber(number[0]);
            return response;
        }

        private String requeteFindPokemonByNumber(String number) {
            String response = "";
            try {
                HttpURLConnection connection = null;
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
            JSONObject toDecode = null;
            try {
                toDecode = new JSONObject(result);
                pokemonByID = decodePokemon(toDecode);
            } catch (Exception e) {
                e.printStackTrace();
                pokemonByID = null;
            }
        }
    }
    }/*
    private class RequestTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String ... number) {
            String response = requeteFindPokemonByNumber(number[0]);
            return response;
        }
        private String requeteFindPokemonByNumber(String number) {
            String response = "";
            try {
                HttpURLConnection connection = null;
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
        private String getPokemon(JSONObject jso) throws Exception {
            return jso.getString("name");
        }
        protected void onPostExecute(String result) {
            JSONObject toDecode = null;
            try {
                toDecode = new JSONObject(result);
                namePokemon = getPokemon(toDecode);
            } catch (Exception e) {
                e.printStackTrace();
                namePokemon = "Pokemon erreur";
            }
        }*/

