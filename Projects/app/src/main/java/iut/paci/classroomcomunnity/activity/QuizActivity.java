package iut.paci.classroomcomunnity.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import iut.paci.classroomcomunnity.R;
import iut.paci.classroomcomunnity.bean.Question;

public class QuizActivity extends AppCompatActivity {

    // Élémént graphique
    private final List<Button> LISTBUTTON = new ArrayList<Button>();
    private String goodAnswer;
    private TextView question;
    private ProgressBar progressBar;
    private TextView time;
    private TextView textScore1;
    private TextView textScore2;
    private TextView nomAdvers;
    private TextView avatarRAdvers;

    private Thread thread;
    private Handler handler;
    private int infoSec;
    private int progressStatus;
    private boolean timeOut;
    private int score1 = 0;
    private int score2 = 0;
    private List<Question> questionList = new ArrayList<>();
    private Question randomQuestion;
    private String json;


    private List<Button> getListButton() {
        return LISTBUTTON;
    }

    private String getGoodAnswer() {
        return this.goodAnswer;
    }

    private void setGoodAnswer(String a) {

        this.goodAnswer = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        try {
            this.initQuestion();
            this.initButton();
            this.initProgressBar();
        } catch (JSONException e) {
            e.printStackTrace();
        }



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

        // Initialisation des éléments graphiques
        this.question = (TextView) findViewById(R.id.textQuestion);
        this.textScore1 = (TextView) findViewById(R.id.textScore);
        this.textScore2 = (TextView) findViewById(R.id.textScore2);
        this.nomAdvers = (TextView) findViewById(R.id.textNom2);
        this.avatarRAdvers = (TextView) findViewById(R.id.initialQuiz);

        // On met à jout le nom, la couleur du nom et l'avatar
        // en fonction de la séléction précédante
        this.nomAdvers.setText(getIntent().getExtras().getString("nom"));
        this.avatarRAdvers.setText(String.valueOf(getIntent().getExtras().getString("nom").charAt(0)));
        this.nomAdvers.setTextColor(ContextCompat.getColor(getApplicationContext(), getIntent().getExtras().getInt("color")));
        this.textScore2.setTextColor(ContextCompat.getColor(getApplicationContext(), getIntent().getExtras().getInt("color")));

        // Bar de progression
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        // Compteur de temps
        this.time = (TextView) findViewById(R.id.textTimer);

        this.initData();

    }


    /**
     * Méthode d'initialisation des données
     * de l'activité (Question et reponses)
     */
    private void initData() {

        this.handler = new Handler();
        this.infoSec = 10;
        this.progressStatus = 0;
        this.timeOut = false;
        this.time.setText(Integer.toString(infoSec));

        for (Button b: this.getListButton()) {
            b.setBackgroundResource(R.color.colorAccent);
            b.setEnabled(true);
        }

        List<String> rep = this.randomQuestion.getReponseText();

        Collections.shuffle(rep);

        int i = 0;
        // Ajout du listeneur à tous les boutons
        // et du text sur les questions
        for(final Button b : this.getListButton()) {
            b.setText(rep.get(i).toString());
            // On enregistre la bonne reponse pour la suite
            if (this.randomQuestion.getReponses().get(b.getText()))
                this.setGoodAnswer(rep.get(i).toString());

            i++;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    timeOut = true;
                    checkButton(b);
                    (new Handler()).post(new Runnable() {
                        @Override
                        public void run() {
                            doWait();
                            reinitialise();
                        }
                    });

                }
            });
        }

        this.question.setText(this.randomQuestion.getQuestion());
    }

    /**
     * Initialisation de la bar de
     * progression.
     * Ajout du Thread pour sa mise à jour
     */
    private void initProgressBar() {

        // Nouveau Thread
        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (progressStatus <= 10 && !timeOut) {

                    handler.post(new Runnable() {

                        @Override
                        public void run() {

                            // Mise à jour de la bar
                            progressBar.setProgress(progressStatus);

                            // Mise à jour du compteur
                            time.setText(Integer.toString(infoSec));

                            Log.i("Dans le Runnable", ""+progressBar);
                            if(progressStatus == 10) {
                                Log.i("Dans le Runnable", "JE SUIS DANS LE IF");
                                time.setText("TIME OUT");
                                time.setTextColor(Color.RED);
                                winButton();
                                doWait();
                                reinitialise();
                            }
                        }
                    });

                    doWait();
                    // On incrémante le status de la bar
                    progressStatus++;
                    infoSec --;

                }

            }
        });

        this.thread.start();// Demarrage du service
    }


    /**
     * Méthode d'initialisation de la question
     * et des reponses
     * @throws JSONException
     */
    private void initQuestion() throws JSONException {

        this.generQuestion();
       // this.chooseQuestion();

    }


    /**
     * Méthode qui ve permettre de choisir au hasard une question
     */
   /* private void chooseQuestion() {

        Random random = new Random();
        int randomInt = showRandomInteger(0,this.questionList.size()-1, random);
        this.randomQuestion = questionList.get(randomInt);

    } */


    /**
     * Méthode qui renvoie un chiffre au hasard
     * @param aStart
     * @param aEnd
     * @param aRandom
     * @return
     */
    private int showRandomInteger(int aStart, int aEnd, Random aRandom){

        //get the range, casting to long to avoid overflow problems
        long range = (long)aEnd - (long)aStart + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long)(range * aRandom.nextDouble());
        int randomNumber =  (int)(fraction + aStart);

        return randomNumber;
    }


    /**
     * Génération de la question aléatoire dans le fichier JSON
     * @throws JSONException
     */
    public void generQuestion()  {




        Ion.with(getApplicationContext()) //998889235
                .load("http://192.168.137.1/classroom_server/getQuestions.php?key=paci.iut.1235")
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> response) {



                        if(response == null){

                            Log.e("Chargement des amis","Pas de reponse du serveur");


                        } else {

                            Log.i("Resultat", response.getResult());

                            switch (response.getHeaders().code()) {
                                case 404:
                                    Toast.makeText(getApplicationContext(), "Erreur 404 page not found !", Toast.LENGTH_SHORT).show();
                                    break;
                                case 403:
                                    Toast.makeText(getApplicationContext(), "Erreur 404 page not found !", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    break;
                            }


                            if(response.getResult().equals("{\"error\" : \"Wrong key !!!\"}")) {

                                Toast.makeText(getApplicationContext(), "ERREUR Mauvaise cles !!", Toast.LENGTH_SHORT).show();



                            } else {

                                json = response.getResult();

                            }
                        }

                    }
                });


        Log.i("Question", json);
        // On ajoute à la liste la question et la Map de reponse
        this.randomQuestion = JsonTools.getQuestion(json);
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

        // Si la reponse est fausse
        if (!button.getText().toString().equals(this.getGoodAnswer())) {
            // On color en rouge la mauvaise reponse
            button.setBackgroundResource(R.drawable.roundedbutton_lose);
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
                } else if (b.getText().toString().equals(this.getGoodAnswer())) {

                    // On met en verre la bonne réponse
                    b.setBackgroundResource(R.drawable.roundedbutton_win);
                }
                // On retire le listener
                // pour ne pas que le joueur
                // essai de jouer encore
                b.setOnClickListener(null);
            }

            this.score2 += 5;
            this.textScore2.setText(Integer.toString(this.score2));

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
            if (!b.getText().toString().equals(this.getGoodAnswer())) {
                b.setBackgroundResource(R.color.otherResp);
                b.setPadding(0,0,0,0);
                b.setEnabled(false);
            } else {
                b.setBackgroundResource(R.drawable.roundedbutton_win);
                // On retire le listener
                // pour ne pas que le joueur
                // essai de jouer encore
                b.setOnClickListener(null);
            }
        }
    }

    /**
     * Méthode de réinitialisation
     * de l'activité
     */
    private void reinitialise() {

        try {
            // On repet la bar de progression à 0
            progressBar.setProgress(0);
            // On remet la couleur d'origine au compteur
            this.time.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
            // On réinitialise la question
            this.initQuestion();
            // On réinitialise les données
            this.initData();
            // On relance le Thread
            this.thread.start();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * On fait une petite pause d'une seconde
     */
    private void doWait() {

        try {
            // Attente de 1 seconde
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
