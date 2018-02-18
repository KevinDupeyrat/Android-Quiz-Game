package iut.paci.classroomcomunnity.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import iut.paci.classroomcomunnity.R;


/**
 * Class de l'activité principal
 * Elle va permettre à l'utilisateur
 * de s'authentifier avec un login
 * et un mot de passe
 */
public class LoginActivity extends AppCompatActivity {

    private TextView identifiant;
    private TextView pass;
    private Button bValidation;

    private final String TAG = "LoginActivity";

    /**
     * Au moment de la création de l'activité
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Liaison du XML avec la class Java
        setContentView(R.layout.activity_login);

        // Récupération des données saisie dans
        // les input
        identifiant = (TextView) findViewById(R.id.editTextIdent);
        pass = (TextView) findViewById(R.id.editTextPass);
        bValidation = (Button) findViewById(R.id.buttonValider);

        // Création de l'événement du bouton
        bValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String iden = identifiant.getText().toString();
                String mdp = pass.getText().toString();

                if (iden.isEmpty()) {
                    // Affichage d'un Toast à l'acran
                    Toast.makeText(view.getContext(), "Identifiant manquant", Toast.LENGTH_SHORT).show();
                    // Affichage d'un Log
                    Log.i(TAG, "Identifiant manquant");
                }
                else if(mdp.isEmpty()) {
                    Toast.makeText(view.getContext(), "Mot de passe manquant", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Mot de passe manquant");
                }
                else {

                    if (!iden.equals("a") || !mdp.equals("1")){
                        Toast.makeText(view.getContext(), "Identifiant ou mot de passe incorrecte !", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Identifiant ou mot de passe incorrecte !");
                    } else {

                        // Création d'un Intent (activité)
                        // Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        // On demarre une activité
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
