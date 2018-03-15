package iut.paci.classroomcomunnity.tools;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.koushikdutta.ion.Response;

import iut.paci.classroomcomunnity.R;
import iut.paci.classroomcomunnity.frame.ScanFragment;

/**
 * Created by Kevin Dupeyrat on 28/02/18.
 *
 * Class qui permet la gestion des erreurs rencontré
 * avec la communication avec le serveur
 * pour que celle-ci soit localiser et généraliser
 * pour toutes l'application
 *
 */

public class ErrorServerTools {

    /**
     * Méthode de gestion des erreurs
     * avec le server
     */
    public static boolean errorManager(Response<String> response, Context context, FragmentActivity fragmentActivity) {

        Log.i("Resultat", response.getResult());

        // Gestion des différents code d'erreur en
        // retour de la requête
        switch (response.getHeaders().code()) {
            case 404:
                //Toast.makeText(context, "Erreur 404 page not found !", Toast.LENGTH_SHORT).show();
                return false;
            case 403:
                //Toast.makeText(context, "Erreur 404 page not found !", Toast.LENGTH_SHORT).show();
                return false;
        }

        // Si la clés n'est pas bonne
        if(response.getResult().equals("{\"error\" : \"Wrong key !!!\"}")) {

            if (fragmentActivity != null) {
                // On insert le fragment qui va remplacer celui de base
                FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentFrame, new ScanFragment()).commit();

            }

            return false;
        }

        return true;
    }
}
