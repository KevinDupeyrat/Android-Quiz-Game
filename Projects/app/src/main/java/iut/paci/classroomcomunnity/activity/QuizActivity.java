package iut.paci.classroomcomunnity.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import iut.paci.classroomcomunnity.R;
import iut.paci.classroomcomunnity.bean.Question;
import iut.paci.classroomcomunnity.bean.Reponse;
import iut.paci.classroomcomunnity.tools.ErrorServerTools;
import iut.paci.classroomcomunnity.tools.JsonTools;
import iut.paci.classroomcomunnity.tools.PropertiesTools;

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
    private TextView nomMoi;
    private TextView avatarRMoi;

    private Thread thread;
    private Handler handler;
    private int infoSec;
    private int progressStatus;
    private boolean timeOut;
    private int score1 = 0;
    private int score2 = 0;
    private Question randomQuestion;
    private String json;

    private int idAdv;
    private String monNom;
    private String monPrenom;
    private String nomAdv;
    private String prenomAdv;

    private Timer timerScore;


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

        this.initQuestion();

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

        //TODO: Gérer les boutons
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
        timeOut = true;
        this.finish();
    }


    /**
     * Initialisation des boutons
     * de l'activité
     */
    private void initButton() {

        this.nomAdv = getIntent().getExtras().getString("nom");
        this.prenomAdv = getIntent().getExtras().getString("prenom");
        this.monNom = getIntent().getExtras().getString("MonNom");
        this.monPrenom = getIntent().getExtras().getString("MonPrenom");
        this.score1 = getIntent().getExtras().getInt("score1");
        this.score2 = getIntent().getExtras().getInt("score2");
        this.idAdv = getIntent().getExtras().getInt("id");

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
        this.avatarRAdvers = (TextView) findViewById(R.id.initialQuiz2);
        this.nomMoi = (TextView) findViewById(R.id.textNom1);
        this.avatarRMoi = (TextView) findViewById(R.id.initialQuiz1);

        // On met à jout le nom, la couleur du nom et l'avatar
        // en fonction de la séléction précédante
        this.nomAdvers.setText(prenomAdv + " " + nomAdv);
        this.avatarRAdvers.setText(String.valueOf(nomAdv.charAt(0)));

        this.nomMoi.setText(String.valueOf(this.monPrenom + " " + this.monNom));
        this.avatarRMoi.setText(String.valueOf(this.monNom.charAt(0)));
        // Bar de progression
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        // Compteur de temps
        this.time = (TextView) findViewById(R.id.textTimer);

        this.textScore1.setText(String.valueOf(this.score1));
        this.textScore2.setText(String.valueOf(this.score2));

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
            b.setBackgroundResource(R.color.colorCircle);
            b.setEnabled(true);
        }

        // On récupère la liste de reponses
        List<Reponse> reponses = this.randomQuestion.getReponses();

        Collections.shuffle(reponses);


        // Ajout du listeneur à tous les boutons
        // et du text sur les questions
        for(int i = 0; i <  reponses.size(); i++) {

            final Button boutonSelect = this.getListButton().get(i);
            boutonSelect.setText(reponses.get(i).getReponse());
            // On enregistre la bonne reponse pour la suite
            if (reponses.get(i).isBonneReponse() == 1) {
                this.setGoodAnswer(reponses.get(i).getReponse());
            }

            boutonSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    timeOut = true;
                    boolean reponse = checkButton(boutonSelect);
                    sendServerResponse(reponse);
                    reinitialise();
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

                                time.setText("TIME OUT");
                                time.setTextColor(Color.RED);
                                winButton();
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
    private void initQuestion() {


        Ion.with(getApplicationContext())
                .load(PropertiesTools.genURL(getApplicationContext(),"question")
                        +"?key="+ MainActivity.getServerCode() +
                        "&id=" + MainActivity.getMy_id())
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> response) {


                        // Si la réponse est null
                        if(response == null){
                            Toast.makeText(getApplicationContext(), "Erreur: Le serveur ne repond pas !", Toast.LENGTH_SHORT).show();

                        } else {

                            // On verrifi si la requête nous a renvoyer une erreur.
                            // Si elle renvoie False c'est qu'il n'y as pas d'erreur
                            // et on peut passer à la suite
                            if(ErrorServerTools.errorManager(response, getApplicationContext(), null)){

                                System.out.println(response.getResult());

                                if(!response.getResult().equals("fin")) {
                                    json = response.getResult();
                                    Log.i("QUESTION JSON", response.getResult());
                                    randomQuestion = JsonTools.getQuestion(json);

                                    initButton();
                                    initProgressBar();
                                    return;
                                } else {

                                    TextView textView = new TextView(getApplicationContext());

                                    if(score1 > score2)
                                        textView.setText("Vous avez perdu :( ");
                                    else if(score1 < score2)
                                            textView.setText("Vous avez ganger :) ");
                                    else
                                        textView.setText("Égalité parfaite !!");


                                    PopupWindow popupWindow = new PopupWindow(getApplicationContext());
                                    popupWindow.setContentView(textView);

                                    doWait();
                                    finish();
                                }

                            } else {

                                // Si nous avons une erreur, on ferme l'activité
                                // pour retourner sur la précédante avec 'normalement'
                                // le Fragment de Scan ouvert
                                callback();
                            }
                        }
                    }
                });
    }


    /**
     * Méthode qui ferme l'activité quand nous
     * rencontrons un problème avec la clé
     */
    private void callback() {

        Toast.makeText(getApplicationContext(), "Erreur Clé incorrecte !", Toast.LENGTH_SHORT).show();

        this.doWait();
        this.finish();
    }


    /**
     * Méthode de vérification de l'événement
     * effectuer sur un bouton.
     * Nous vérifions ici si le bouton cliqué
     * est une bonne ou mauvaise reponse
     *
     * @param button
     */
    private boolean checkButton(Button button) {

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

           // this.score2 += 5;
           // this.textScore2.setText(Integer.toString(this.score2));

            return false;

        } else {
            // On indique à l'utilisateur qu'il a gangé
            Toast.makeText(getApplicationContext(), "C'est gagnée !! :)", Toast.LENGTH_SHORT).show();
            this.score1 += 5;
            this.textScore1.setText(Integer.toString(this.score1));

            // On désactive tous les autres boutons
            this.winButton();

            return true;
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
     * Méthode qui va permettre de récupérer
     * le score de l'adversaire
     */
    private void getScoreAdvers() {

        Ion.with(getApplicationContext())
                .load(PropertiesTools.genURL(getApplicationContext(),"get_score_advers")
                        +"?key="+ MainActivity.getServerCode() +
                        "&my_id=" + MainActivity.getMy_id())
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> response) {


                        // Si la réponse est null
                        if(response == null){
                            Toast.makeText(getApplicationContext(), "Erreur: Le serveur ne repond pas !", Toast.LENGTH_SHORT).show();

                        } else {

                            //TODO: On ne passe pas dans le IF

                            // On verrifi si la requête nous a renvoyer une erreur.
                            // Si elle renvoie False c'est qu'il n'y as pas d'erreur
                            // et on peut passer à la suite
                            if(ErrorServerTools.errorManager(response, getApplicationContext(), null)){

                                System.out.println("QuizActivity : Score adverse" + response.getResult());
                                if(response.getResult().equals("0") || response.getResult() == null)
                                    textScore2.setText(String.valueOf(0));
                                else
                                    textScore2.setText(String.valueOf(response.getResult()));

                            }
                        }
                    }
                });

    }

    /**
     * Méthode de réinitialisation
     * de l'activité
     */
    private void reinitialise() {

        this.getScoreAdvers();

        doWait();

        Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
        // Création d'une boite
        Bundle bundle = new Bundle();
        // Ajout de l'identifiant dans notre boite
        bundle.putInt("id",this.idAdv);
        bundle.putString("nom",String.valueOf(this.nomAdv));
        bundle.putString("prenom",String.valueOf(this.prenomAdv));
        bundle.putString("MonNom",String.valueOf(this.monNom));
        bundle.putString("MonPrenom",String.valueOf(this.monPrenom));
        bundle.putInt("score1",this.score1);
        bundle.putInt("score2",this.score2);
        // Ajout de notre boite dans notre prochaine activité
        intent.putExtras(bundle);
        //intent.putExtra("fragmentActivity",getActivity());
        // On demarre une activité
        startActivity(intent);

        this.finish();

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


    /**
     * Méthode qui permet d'envoyer
     * notre reponse à la question au server
     */
    private void sendServerResponse(boolean reponse) {

        Ion.with(getApplicationContext())
                .load(PropertiesTools.genURL(getApplicationContext(),"put_response")
                        +"?key="+ MainActivity.getServerCode()
                        +"&my_id=" + MainActivity.getMy_id()
                        +"&reponse=" + reponse)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> response) {


                        // Si la réponse est null
                        if(response == null){
                            Toast.makeText(getApplicationContext(), "Erreur: Le serveur ne repond pas !", Toast.LENGTH_SHORT).show();

                        } else {

                            // On verrifi si la requête nous a renvoyer une erreur.
                            // Si elle renvoie False c'est qu'il n'y as pas d'erreur
                            // et on peut passer à la suite
                            if(!ErrorServerTools.errorManager(response, getApplicationContext(), null)){

                                callback();

                            } else {
                                System.out.println("REPONSEEEE ::: " + response.getResult());
                            }
                        }
                    }
                });

    }

}
