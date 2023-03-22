package com.example.appmobile_projet;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

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

public class ZooMonActivity extends Fragment implements View.OnClickListener{
    String imageURL;

    String nomPokemon;
    ImageView zoomon_img;
    RequestTask requestOnAPI;
    TextView score;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        return inflater.inflate(R.layout.frag_zoomon, container, false);
    }
    // A la création du fragment on créer 2 pokemon et on affiche leurs noms et leurs images
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        requestOnAPI = new RequestTask();
        zoomon_img = view.findViewById(R.id.zoomon_img);
        requestOnAPI.execute(String.valueOf((int)(Math.random() * 151) + 1));
        view.findViewById(R.id.refresh).setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        imageURL = null;
        zoomon_img = null;
        score = null;
        super.onDestroy();
    }

    @Override
    public void onClick(View view){
        if (view.getId() == R.id.refresh){
            requestOnAPI = new RequestTask();
            requestOnAPI.execute(String.valueOf((int)(Math.random() * 151) + 1));
        }
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
        private String decodeURL(JSONObject jso) throws Exception {
            String result;
            result = jso.getString("image");
            nomPokemon = jso.getString("name");
            return result;
        }
        protected void onPostExecute(String result) {
            JSONObject toDecode;
            try {
                toDecode = new JSONObject(result);
                Picasso.get()
                        .load(decodeURL(toDecode))
                        .into(zoomon_img);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
