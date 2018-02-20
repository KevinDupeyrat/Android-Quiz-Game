package iut.paci.classroomcomunnity.activity;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import iut.paci.classroomcomunnity.R;
import iut.paci.classroomcomunnity.frame.AboutFragment;
import iut.paci.classroomcomunnity.frame.FriendFragment;
import iut.paci.classroomcomunnity.frame.PresentFragment;
import iut.paci.classroomcomunnity.frame.SettingFragment;

/**
 * Activité pricincipale qui
 * permet de switcher avec les Framgments
 */
public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    NavigationView nav;


    /**
     * Méthode appelé à la création de l'activité
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("Drawer", "Initialisation ...");
        // On recupère le Drawer
        drawer = (DrawerLayout) findViewById(R.id.drawer);

        // On recupère le Navigateur View
        nav = (NavigationView) findViewById(R.id.nav_view);
        nav.getMenu().performIdentifierAction(R.id.nav_friend, 0);
        nav.bringToFront();

        // Setup drawer view
        setupDrawerContent(nav);

        // Création du bouton pour ouvrir et fermet le Drawer
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);

        // On ajoute un listener au Drawer qui écoutera
        // les événement sur le bouton toggle
        drawer.setDrawerListener(toggle);
        // Synchronisation du toggle avec le Drower
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    /**
     * Méthode pour initialiser le
     * contenu du Drawer
     * @param navigationView
     */
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            selectDrawerItem(menuItem);
                        }
                        return true;
                    }
                });
    }


    /**
     * Méthode pour la gestion de la selection dans le menu
     * @param menuItem
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void selectDrawerItem(MenuItem menuItem) {

        ((FrameLayout)findViewById(R.id.contentFrame)).setBackground(null);
        ((TextView)findViewById(R.id.textWelcome)).setText("");

        // Creation d'un nouveau fragment que nous mettrons à la place
        // de celle par défaut
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_friend:
                fragmentClass = FriendFragment.class;
                break;
            case R.id.nav_present:
                fragmentClass = PresentFragment.class;
                break;
            case R.id.nav_setting:
                fragmentClass = SettingFragment.class;
                break;
            case R.id.nav_about:
                fragmentClass = AboutFragment.class;
                break;
            case R.id.nav_logout:
                this.finish();
                return;
            case R.id.nav_home:
                // TODO: Faire revenir à l'écran principal (fermeture des fragments)
                Toast.makeText(this, "Fonctionnalité non implémenté encore ...", Toast.LENGTH_SHORT).show();
                drawer.closeDrawer(GravityCompat.START);
                return;
            default:
                fragmentClass = null;
                return;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // On insert le fragment qui va remplacer celui de base
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentFrame, fragment).commit();

        // Surbrillance dans le Navigateur View de l'item séléctionné
        menuItem.setChecked(true);
        // changement du titre de l'action bar
        setTitle(menuItem.getTitle());
        // Fermeture du Drawer qui contient le Navigateur View
        drawer.closeDrawer(GravityCompat.START);
    }


    /**
     * Méthode qui permet de savoir si le bouton
     * a était activé
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(toggle.onOptionsItemSelected(item))
            return true;

        return false;
    }

}
