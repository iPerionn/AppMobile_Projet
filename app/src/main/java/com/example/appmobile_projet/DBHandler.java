package com.example.appmobile_projet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Form.db";
    private Pokemon pokemonInsert;
    RequestTask requestTask;
    public  DBHandler(Context context) { super(context, DATABASE_NAME,null,DATABASE_VERSION);}
    public boolean addRandomPokemon(){
        requestTask = new RequestTask();
        List<Pokemon> collection =selectAll();
        if (collection.size() != 152) {
            Boolean pokemonIsInPokedex = false;
            int id;
            do {
                id = (int) ((Math.random()* 151)+1);
                for (Pokemon pokemon : collection){
                    if (pokemon.getId() == id){
                        pokemonIsInPokedex = true;
                        break;
                    }
                    pokemonIsInPokedex = false;
                }
            }while (pokemonIsInPokedex);
            requestTask.execute(String.valueOf(id));
            return true;
        }else {
            return false;
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DBContract.Form.TABLE_NAME + " (" +
                DBContract.Form._ID + " INTEGER,"+
                DBContract.Form.COLUMN_NAME + " TEXT," +
                DBContract.Form.COLUMN_IMAGE + " TEXT," +
                DBContract.Form.COLUMN_HP + " INTEGER," +
                DBContract.Form.COLUMN_ATTACK + " INTEGER," +
                DBContract.Form.COLUMN_DEFENSE + " INTEGER," +
                DBContract.Form.COLUMN_ATTACKER + " INTEGER," +
                DBContract.Form.COLUMN_DEFENSESPE + " INTEGER," +
                DBContract.Form.COLUMN_SPEED + " INTEGER)";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + DBContract.Form.TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }
    public void insertPokemon(String id, String name, String image, String hp, String attack, String defense, String attackspe, String defensespe, String speed){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(DBContract.Form._ID,id);
        row.put(DBContract.Form.COLUMN_NAME,name);
        row.put(DBContract.Form.COLUMN_IMAGE,image);
        row.put(DBContract.Form.COLUMN_HP,hp);
        row.put(DBContract.Form.COLUMN_ATTACK,attack);
        row.put(DBContract.Form.COLUMN_DEFENSE,defense);
        row.put(DBContract.Form.COLUMN_ATTACKER,attackspe);
        row.put(DBContract.Form.COLUMN_DEFENSESPE,defensespe);
        row.put(DBContract.Form.COLUMN_SPEED,speed);
        db.insert(DBContract.Form.TABLE_NAME,null,row);
    }
    public List<Pokemon> selectAll() {
        List<Pokemon> responses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String [] pokemons = {
                DBContract.Form._ID,
                DBContract.Form.COLUMN_NAME,
                DBContract.Form.COLUMN_IMAGE,
                DBContract.Form.COLUMN_HP,
                DBContract.Form.COLUMN_ATTACK,
                DBContract.Form.COLUMN_DEFENSE,
                DBContract.Form.COLUMN_ATTACKER,
                DBContract.Form.COLUMN_DEFENSESPE,
                DBContract.Form.COLUMN_SPEED };
        Cursor cursor = db.query(
                DBContract.Form.TABLE_NAME,
                pokemons,
                null,
                null,
                null,
                null,
                "id ASC");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Form._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Form.COLUMN_NAME));
            String image = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Form.COLUMN_IMAGE));
            int hp = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Form.COLUMN_HP));
            int attack = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Form.COLUMN_ATTACK));
            int defense = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Form.COLUMN_DEFENSE));
            int attackSpe = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Form.COLUMN_ATTACKER));
            int defenseSpe = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Form.COLUMN_DEFENSESPE));
            int speed = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Form.COLUMN_SPEED));
            responses.add(new Pokemon(id,name,image,hp,attack,defense,attackSpe,defenseSpe,speed));
        }
        return responses;
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
                pokemonInsert = (decodePokemon(toDecode));
                insertPokemon(String.valueOf(pokemonInsert.getId()),pokemonInsert.getName(),pokemonInsert.getImageURL(),String.valueOf(pokemonInsert.getHp()),
                        String.valueOf(pokemonInsert.getAttack()),String.valueOf(pokemonInsert.getDefense()),String.valueOf(pokemonInsert.getAttack_spe()),
                        String.valueOf(pokemonInsert.getDefense_spe()), String.valueOf(pokemonInsert.getSpeed()));
            } catch (Exception e) {
                e.printStackTrace();
                pokemonInsert = null;
            }
        }
    }
}
