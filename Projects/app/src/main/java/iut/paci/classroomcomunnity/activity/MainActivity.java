package iut.paci.classroomcomunnity.activity;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


import iut.paci.classroomcomunnity.R;
import iut.paci.classroomcomunnity.frame.AboutFragment;
import iut.paci.classroomcomunnity.frame.FriendFragment;
import iut.paci.classroomcomunnity.frame.HomeFragment;
import iut.paci.classroomcomunnity.frame.ScanFragment;
import iut.paci.classroomcomunnity.frame.SettingFragment;
import iut.paci.classroomcomunnity.tools.PropertiesTools;

/**
 *
 * Created by Kevin Dupeyrat on 10/03/18.
 *
 * Activité pricincipale qui
 * permet de switcher avec les Framgments
 * présent dans le Drawer.
 */
public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle toggle;
    private static DrawerLayout drawer;
    private static NavigationView nav;

    private static String serverCode;
    private static int my_id;


    public static int getMy_id() {
        return my_id;
    }

    public static NavigationView getNavigatView() {
        return nav;
    }

    public static void setServerCode(String code) {
        serverCode = code;
    }

    public static String getServerCode() {
        return serverCode;
    }

    public static DrawerLayout getDrawer() { return drawer; }


    /**
     * Méthode appelé à la création de l'activité
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        my_id = getIntent().getExtras().getInt("my_id");


        drawer = (DrawerLayout) findViewById(R.id.drawer);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA},99);

        } else {

            this.creatDrawer();
        }
    }


    /**
     * Méthode de gestion du boutton
     * de backPress
     * Ici nous allons seulement ouvrir
     * le Drawer pour ne pas revenir sur
     * la page de connexion
     */
    @Override
    public void onBackPressed() {

        drawer.openDrawer(GravityCompat.START);
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


    /**
     * Méthode qui permet de faire une action
     * en fonction du resultat de la demande d'autorisation
     * pour la caméra
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==99 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

            this.creatDrawer();

        } else {

            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        }
    }


    /**
     * A la fermeture de l'application
     * nous demandons à nous déloger du server
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        this.logOutServer();
    }

    /**
     * Méthode de création du Drawer
     */
    private void creatDrawer() {

        nav = (NavigationView) findViewById(R.id.nav_view);
        nav.bringToFront();

        setupDrawerContent(nav);

        toggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nav.getMenu().performIdentifierAction(R.id.nav_photo, 0);


        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }


    /**
     * Méthode pour initialiser le
     * contenu du Drawer
     *
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
     *
     * @param menuItem
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void selectDrawerItem(MenuItem menuItem) {

        ((FrameLayout)findViewById(R.id.contentFrame)).setBackground(null);

        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_friend:
                fragmentClass = FriendFragment.class;
                break;
            case R.id.nav_setting:
                fragmentClass = SettingFragment.class;
                break;
            case R.id.nav_about:
                fragmentClass = AboutFragment.class;
                break;
            case R.id.nav_home:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_photo:
                fragmentClass = ScanFragment.class;
                break;
            case R.id.nav_logout:
                this.logOutServer();
                return;
            default:
                fragmentClass = null;
                return;
        }


        FriendFragment.delAllTimer();;


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
     * Méthode qui permet de ce déloger du server
     */
    private void logOutServer(){

        // On envoie la demande de connexion
        // au serveur
        Ion.with(getApplicationContext())
                .load( PropertiesTools.genURL(getApplicationContext(), "logout")
                        +"?key=" + MainActivity.getServerCode()
                        +"&id="+ my_id)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        finish();
                    }
                });
    }

}
