package iut.paci.classroomcomunnity.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import iut.paci.classroomcomunnity.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Récupération de la boite qui vient de l'activité précédante
        Bundle bundle = getIntent().getExtras();
        // On récupère la valeur
        String identifiant = bundle.getString("identifiant");
        String mdp= bundle.getString("mdp");

        TextView textView = (TextView) findViewById(R.id.text);
        // On récupère l'élément de la Ressources String
        textView.setText(getResources().getString(R.string.welcome_to_my_world) + " " + identifiant
        + " Mot de passe : " + mdp);

    }
}
