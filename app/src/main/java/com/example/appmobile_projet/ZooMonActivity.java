package com.example.appmobile_projet;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
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
    EditText reponse;
    View context;
    int score_numerique;
    DBHandler db;

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
        return inflater.inflate(R.layout.frag_zoomon, container, false);
    }

    /**
     * Initialisation de la vue
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        requestOnAPI = new RequestTask();
        context = getActivity().findViewById(android.R.id.content);
        zoomon_img = view.findViewById(R.id.zoomon_img);
        score = view.findViewById(R.id.zoomon_score);
        score_numerique = 0;
        reponse = view.findViewById(R.id.reponse);
        requestOnAPI.execute(String.valueOf((int)(Math.random() * 151) + 1));
        view.findViewById(R.id.refresh).setOnClickListener(this);
    }

    /**
     * Lors de la destruction du fragment on passe le pokémon a nul
     */
    @Override
    public void onDestroy() {
        imageURL = null;
        zoomon_img = null;
        score = null;
        super.onDestroy();
    }

    /**
     * Récupère la chaine de caractères entrée par l'utilisateur et la compare avec celle du pokémon
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view){
        if (view.getId() == R.id.refresh){
            String reponseText = reponse.getText().toString();
            //Si l'utilisateur ne rentre rien, affichage d'une snackbar
            if (reponseText.isEmpty()) {
                Snackbar.make(context, "Merci de rentrer un nom de pokemon", Snackbar.LENGTH_LONG).show();
            } else {
                verifRéponse(reponseText);
                chargementNouveauPokemon();
                if (score_numerique % 5 == 0 && score_numerique != 0 && db.addRandomPokemon()) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Félicitations !")
                            .setMessage("Un nouveau pokémon a été ajouté à votre collection n'hésitez pas à faire un tours")
                            .show();
                }
            }
        }
    }

    /**
     * Vérifie la réponse de l'utilisateur
     * Incrémente son score en cas de bonne réponse sinon remet à 0
     * @param reponseUtilisateur
     */
    private void verifRéponse(String reponseUtilisateur) {
        if ( reponseUtilisateur.equalsIgnoreCase(nomPokemon)) {
            score_numerique++;
        } else {
            score_numerique = 0;
        }
    }

    /**
     * Appel de la requete API
     */
    private void chargementNouveauPokemon(){
        requestOnAPI = new RequestTask();
        requestOnAPI.execute(String.valueOf((int)(Math.random() * 151) + 1));
    }

    /**
     * Charge l'image du pokémon et récupère son nom via l'API
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
                score.setText(String.valueOf(score_numerique));
                reponse.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

