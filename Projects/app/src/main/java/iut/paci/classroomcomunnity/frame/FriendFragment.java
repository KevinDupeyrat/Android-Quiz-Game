package iut.paci.classroomcomunnity.frame;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import iut.paci.classroomcomunnity.R;
import iut.paci.classroomcomunnity.tools.ErrorTools;
import iut.paci.classroomcomunnity.tools.JsonTools;
import iut.paci.classroomcomunnity.activity.MainActivity;
import iut.paci.classroomcomunnity.activity.QuizActivity;
import iut.paci.classroomcomunnity.adapter.FriendAdapter;
import iut.paci.classroomcomunnity.bean.Amis;

/**
 * Fragment des amies qui va gérer
 * la liste des amis présent sur le server
 */
public class FriendFragment extends Fragment {


    private View rootView;
    private ProgressDialog progressDialog;
    private String jSonFriend = "";


    public FriendFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Modification du titre
        getActivity().setTitle("Mes Amis");
        // On récupère l'instance de l'activité qui
        // contient ce Fragement
        rootView = inflater.inflate(R.layout.fragment_friend, container, false);

        // this.getLocalFriend();
        this.getRemoteFriend();

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Méthode qui permet d'aller chercher la liste
     * d'amis sur le server
     */
    private void getRemoteFriend() {

        // Initialisation de la bar de progression
        this.progressDialog = new ProgressDialog(getContext());
        this.progressDialog.setMessage(getString(R.string.wait_friend));
        this.progressDialog.setIndeterminate(true);
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();

        // Objet Ion permet comme en Ajax de récupérer une
        // reponse d'un server via HTTP.
        // Ici nous voulons récupérer un fichier Json
        Ion.with(getContext())
                //paci.iut.1235
                .load("http://192.168.137.1/classroom_server/getFriends.php?key=" + MainActivity.getServerCode())
                .asString()
                .withResponse() // Gestion des reponses
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> response) {

                        // Une fois la requête terminé nous
                        // fermons la bar de progression
                        progressDialog.dismiss();

                        // Si la réponse est null
                        if(response == null){
                            Toast.makeText(getContext(), "Erreur: Le serveur ne repond pas !", Toast.LENGTH_SHORT).show();

                        } else {

                            // On verrifi si la requête nous a renvoyer une erreur.
                            // Si elle renvoie False c'est qu'il n'y as pas d'erreur
                            // et on peut passer à la suite
                            if(ErrorTools.errorManager(response, getContext(), getActivity())){
                                jSonFriend = response.getResult();
                                initListView();
                            }
                        }

                    }
                });

        Log.i("Resultat en String", this.jSonFriend);
    }

    /**
     * Méthode de génération d'amis avec un
     * fichier json local
     *
     * @test
     */
    private void getLocalFriend() {

        String json = null;

        try {

            // On ouvre le Flux vers le fichier
            InputStream is = getResources().openRawResource(R.raw.friends);
            int size = 0;
            size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convertion en String de la lecture
            json = new String(buffer, "UTF-8");
            // On retourne le resultat


        } catch (IOException e) {
            e.printStackTrace();
        }

        jSonFriend = json;
        initListView();

    }

    /**
     * Méthode de création de la ListView
     */
    private void initListView() {

        // On récupère la listeView
        ListView listView = (ListView) rootView.findViewById(R.id.listView);

        // On utilise la class JsonTools pour récupérer la
        // liste d'amis à partir de la String Json
        List<Amis> amisList = JsonTools.getAmis(this.jSonFriend);

        //TODO: Test a supprimer après validation
        // ##################################################################
       /*
        String json = null;

        try {

            // On ouvre le Flux vers le fichier
            InputStream is = getResources().openRawResource(R.raw.friends);
            int size = 0;
            size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convertion en String de la lecture
            json = new String(buffer, "UTF-8");
            // On retourne le resultat


        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Amis> amisList = JsonTools.getAmis(json);
       */
        // ##################################################################

        // On crée l'adapter pour la listeView
        FriendAdapter adapter = new FriendAdapter(rootView.getContext(), R.layout.item_player, amisList);

        // On crée un listener sur chaque bouton
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Amis ami = (Amis) adapterView.getItemAtPosition(position);

                // On vérifi que l'utilistateur est connecté
                // S'il ne l'ai pas on affiche un message à l'utilisateur
                if(ami.isPresent() == 0){

                    Toast.makeText(getContext(), "Désolé mais " + ami.getNom() + " "
                            + ami.getPrenom() + " n'est pas connecté :(", Toast.LENGTH_SHORT).show();
                } else {

                    goToQuiz(ami);

                }
            }
        });

        // Nous ajoutons notre adapter à notre listView
        listView.setAdapter(adapter);
    }

    /**
     * Méthode qui permet de lancer
     * le Quiz avec l'ami séléctionné
     * @param ami
     */
    private void goToQuiz(Amis ami){

        // Nous récupérons toutes les info que
        // nous avons besoin pour la suite
        String nom = ami.getNom();
        String prenom = ami.getPrenom();
        int id  = ami.getId();

        // Création d'un Intent (activité)
        Intent intent = new Intent(getContext(), QuizActivity.class);
        // Création d'une boite
        Bundle bundle = new Bundle();
        // Ajout de l'identifiant dans notre boite
        bundle.putInt("id",id);
        bundle.putString("nom",nom);
        bundle.putString("prenom",prenom);
        // Ajout de notre boite dans notre prochaine activité
        intent.putExtras(bundle);
        //intent.putExtra("fragmentActivity",getActivity());
        // On demarre une activité
        startActivity(intent);

    }
}
