package com.example.appmobile_projet;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar myToolBar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isNetworkConnected()){
            Snackbar.make(findViewById(android.R.id.content),"L'application ne fonctionnera pas correctement si vous n'êtes pas connecter a internet",Snackbar.LENGTH_LONG).show();
        }

        //Configuration de l'affichage :
        configureDrawerLayout();
        configureNavigationView();
        configureNavigationView();

        //Affichage de l'écran de menu au lancement de l'application :
        FragmentTransaction tf = getSupportFragmentManager().beginTransaction();
        tf.replace(R.id.fragmentG,new MenuActivity()).commit();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override // Association des éléments du ToolBar au sein de son layout
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_values, menu);
        return true;
    }
    @Override //Traitement des actions du ToolBar
    public boolean onOptionsItemSelected(MenuItem item) {
        //ouvre le menu de navigation :
        if (item.getItemId() == R.id.openNav) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override //Traitement des actions du NavigationView
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnMenu:
                break;
            case R.id.btnJeu1:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    //Configuration des éléments du layout :
    //ToolBar :
    private void configureToolBar(){
        myToolBar = findViewById(R.id.tool_bar);
        setSupportActionBar(myToolBar);
    }
    //DrawerLayout :
    private void configureDrawerLayout(){
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, myToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
    //NavigationView :
    private void configureNavigationView(){
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}