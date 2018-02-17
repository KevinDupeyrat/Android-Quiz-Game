package iut.paci.classroomcomunnity.activity;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iut.paci.classroomcomunnity.R;

public class QuizActivity extends AppCompatActivity {

    // Élémént graphique
    private final List<Button> LISTBUTTON = new ArrayList<Button>();
    private final String GOODANSWER = "Wifi";
    private ProgressBar progressBar;
    private TextView time;
    private TextView textScore1;
    private TextView textScore2;
    private TextView nomAdvers;

    private Handler handler = new Handler();
    private int infoSec = 10;
    private int progressStatus = 0;
    private boolean timeOut = false;
    private int score1 = 0;


    private List<Button> getListButton() {
        return LISTBUTTON;
    }

    private String getGoodAnswer() {
        return GOODANSWER;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        this.initButton();
        this.initProgressBar();



        // ici on vérifie que des données n'ont pas
        // étaient sauvegardé par onStop()
        if (savedInstanceState != null){

            infoSec = savedInstanceState.getInt("infoSec");
            progressStatus = savedInstanceState.getInt("progressStatus");
            score1 = savedInstanceState.getInt("score1");
            timeOut = savedInstanceState.getBoolean("timeOut");

        }

    }

    /**
     * Méthode qui permet de faire une sauvegarde de l'état
     * de l'activité au moment d'un onStop()
     *
     * @param icicle
     */
    @Override
    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);

        icicle.putInt("infoSec", infoSec);
        icicle.putInt("progressStatus", progressStatus);
        icicle.putInt("score1", score1);
        icicle.putBoolean("timeOut", timeOut);
    }

    /**
     * Initialisation des boutons
     * de l'activité
     */
    private void initButton() {

        // Création de la liste de bouton
        this.getListButton().add((Button) findViewById(R.id.b1));
        this.getListButton().add((Button) findViewById(R.id.b2));
        this.getListButton().add((Button) findViewById(R.id.b3));
        this.getListButton().add((Button) findViewById(R.id.b4));

        // Ajout du listeneur à tous les boutons
        for(final Button b : this.getListButton()) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkButton(b);
                }
            });
        }

        this.textScore1 = (TextView) findViewById(R.id.textScore);
        this.textScore2 = (TextView) findViewById(R.id.textScore2);
        this.nomAdvers = (TextView) findViewById(R.id.textNom2);

        this.nomAdvers.setText(getIntent().getExtras().getString("nom"));
    }

    /**
     * Initialisation de la bar de
     * progression.
     * Ajout du Thread pour sa mise à jour
     */
    private void initProgressBar() {

        // Bar de progression
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        // Compteur de temps
        time = (TextView) findViewById(R.id.textTimer);
        time.setText(Integer.toString(infoSec));

        // Nouveau Thread
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (progressStatus < 10 && !timeOut) {

                    // On incrémante le status de la bar
                    progressStatus++;

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            // Mise à jour de la bar
                            progressBar.setProgress(progressStatus);
                            infoSec --;
                            // Mise à jour du compteur
                            time.setText(Integer.toString(infoSec));

                            if(infoSec == 0) {
                                time.setText("TIME OUT");
                                time.setTextColor(Color.RED);

                                winButton();
                            }
                        }
                    });
                    // Attente de 1 seconde pour
                    // raffraichir la bar de progression
                    // ainsi que le compteur
                    try {
                        // Attente de 1 seconde
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                timeOut = true;
            }
        }).start();// Demarrage du service
    }


    /**
     * Méthode de vérification de l'événement
     * effectuer sur un bouton.
     * Nous vérifions ici si le bouton cliqué
     * est une bonne ou mauvaise reponse
     *
     * @param button
     */
    private void checkButton(Button button) {

        this.timeOut = true;

        // Si la reponse est fausse
        if (!button.getText().toString().equals(this.getGoodAnswer())) {
            // On color en rouge la mauvaise reponse
            button.setBackgroundResource(R.color.falseResp);
            // On l'indique à l'utilisateur
            Toast.makeText(getApplicationContext(), "Mauvaise reponse ... :(", Toast.LENGTH_SHORT).show();
            // On grise toutes les autres case sauf la bonne reponse
            // qu'on laisse en vert
            for (Button b : this.getListButton()) {
                if (b != button && !b.getText().toString().equals(this.getGoodAnswer())) {
                    b.setBackgroundResource(R.color.otherResp);
                    // On retir le padding
                    b.setPadding(0, 0, 0, 0);
                    // On desactive les boutons
                    b.setEnabled(false);
                }
                // On retire le listener
                // pour ne pas que le joueur
                // essai de jouer encore
                b.setOnClickListener(null);
            }

        } else {
            // On indique à l'utilisateur qu'il a gangé
            Toast.makeText(getApplicationContext(), "C'est gagnée !! :)", Toast.LENGTH_SHORT).show();
            this.score1 += 5;
            this.textScore1.setText(Integer.toString(this.score1));

            // On désactive tous les autres boutons
            this.winButton();
        }

    }

    /**
     * Affichage de la bonne reponse
     */
    private void winButton() {

        for (Button b : this.getListButton()) {
            if (!b.getText().toString().equals(this.GOODANSWER)) {
                b.setBackgroundResource(R.color.otherResp);
                b.setPadding(0,0,0,0);
                b.setEnabled(false);
            } else {
                // On retire le listener
                // pour ne pas que le joueur
                // essai de jouer encore
                b.setOnClickListener(null);
            }
        }
    }
}
