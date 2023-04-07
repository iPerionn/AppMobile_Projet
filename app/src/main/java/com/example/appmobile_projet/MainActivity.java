package com.example.appmobile_projet;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar myToolBar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Fragment frag_menu;
    private Fragment frag_pokestreak;
    private Fragment frag_zoomon;
    private Fragment frag_pokedex;
    private static final int id_frag_menu = 0;
    private static final int id_frag_pokestreak = 1;
    private static final int id_frag_zoomon = 2;
    private static final int id_frag_pokedex = 3;
    private boolean musiqueOn = true;
    private MediaPlayer mp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            // Vérification lors de la création si l'utilisateur est bien connecté à un réseau
        if (!isNetworkConnected()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Connect to wifi or quit")
                    .setCancelable(false)
                    .setPositiveButton("Connect to WIFI", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    })
                    .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

        //Configuration de l'affichage :
        configureDrawerLayout();
        configureNavigationView();
        configureNavigationView();
        configureToolBar();
        //Affichage de l'écran de menu au lancement de l'application :
        joinFragToId(id_frag_menu);

        //Musique de fond
        mp = MediaPlayer.create(this, R.raw.background_music);
        mp.setLooping(true);
        mp.start();

    }
    /**
     *  Retourne un booléen si l'utilisateur est connectée à un réseaux
     * */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    /**
     *
     * @param menu The options menu in which you place your items.
     *
     * @return boolean
     */
    @Override // Association des éléments du ToolBar au sein de son layout
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_values, menu);
        return true;
    }

    /**
     *
     * @param item The menu item that was selected.
     *
     * @return boolean
     */
    @Override //Traitement des actions du ToolBar
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logoApp) {
            joinFragToId(id_frag_menu);
        }
        return true;
    }

    /**
     * Permet un retour du drawer menu
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     *
     * @param item The selected item
     * @return boolean
     */
    @Override //Traitement des actions du NavigationView
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnMenu:
                joinFragToId(id_frag_menu);
                break;
            case R.id.btnJeu1:
                joinFragToId(id_frag_pokestreak);
                break;
            case R.id.btnJeu2:
                joinFragToId(id_frag_zoomon);
                break;
            case R.id.pokedex:
                joinFragToId(id_frag_pokedex);
                break;
            case R.id.song:
                onOffVolume(item);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Gestion de la musique de fond
     * @param item
     */
    private void onOffVolume(MenuItem item){
        if(mp.isPlaying()){
            item.setIcon(R.drawable.volume_on);
            item.setTitle("Volume On");
            mp.stop();
        }else{
            mp = MediaPlayer.create(this, R.raw.background_music);
            item.setIcon(R.drawable.volume_off);
            item.setTitle("Volume Off");
            mp.start();
        }
    }

    /**
     *
     * @param fragID
     *
     */
    private void joinFragToId(int fragID){ //ici on lie un fragment créer à un id fixe puis on fait appel a la methode pour les afficher
        switch (fragID){
            case id_frag_menu:
                if(frag_menu == null) frag_menu = new MenuActivity();
                changeFragment(frag_menu);
                break;
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
     * Configuration des éléments du layout
     */
    /**
     * ToolBar :
     */
    private void configureToolBar(){
        myToolBar = findViewById(R.id.tool_bar);

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.menu_bar2);
        myToolBar.setNavigationIcon(drawable);

        setSupportActionBar(myToolBar);
        myToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    /**
     * DrawerLayout
     */
    private void configureDrawerLayout(){
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, myToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * NavigationView
     */
    private void configureNavigationView(){
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Permet le changement de fragments
     * @param fragment
     */
    private void changeFragment(Fragment fragment){
        if(!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentG, fragment).commit();
        }
    }
}