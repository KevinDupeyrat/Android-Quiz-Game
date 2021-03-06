package iut.paci.classroomcomunnity.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import iut.paci.classroomcomunnity.R;
import iut.paci.classroomcomunnity.tools.PropertiesTools;


/**
 *
 * Created by Kevin Dupeyrat on 10/03/18.
 *
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
        setContentView(R.layout.activity_login);


        identifiant = (TextView) findViewById(R.id.editTextIdent);
        pass = (TextView) findViewById(R.id.editTextPass);
        bValidation = (Button) findViewById(R.id.buttonValider);

        bValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String iden = identifiant.getText().toString();
                String mdp = pass.getText().toString();

                if (iden.isEmpty()) {
                    Toast.makeText(view.getContext(), "Identifiant manquant", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Identifiant manquant");
                }
                else if(mdp.isEmpty()) {
                    Toast.makeText(view.getContext(), "Mot de passe manquant", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Mot de passe manquant");
                }
                else {

                    if (Integer.parseInt(iden) < 0 && Integer.parseInt(iden) > 29){
                        Toast.makeText(view.getContext(), "Identifiant ou mot de passe incorrecte !", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Identifiant ou mot de passe incorrecte !");
                    } else {

                        identifiant.setText("");
                        pass.setText("");

                        Bundle bundle = new Bundle();
                        bundle.putInt("my_id",Integer.parseInt(iden));

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
