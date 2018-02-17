package iut.paci.classroomcomunnity.frame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import iut.paci.classroomcomunnity.R;
import iut.paci.classroomcomunnity.activity.QuizActivity;

/**
 * Fragment des amies
 */
public class FriendFragment extends Fragment {


    View rootView;


    public FriendFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Modification du titre
        getActivity().setTitle("Mes Amis");
        // On récupère l'instance de l'activité qui
        // contient ce Fragement
        rootView = inflater.inflate(R.layout.fragment_friend, container, false);

        this.initListView();

        return rootView;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Méthode de création de la ListView
     */
    private void initListView() {

        ListView listView = (ListView) rootView.findViewById(R.id.listView);

        String [] items = {
                "Loic.P",
                "Arnaud.R",
                "Aurore.B",
                "Mathieu.N",
                "Kevin.B",
                "Émilie.D",
                "Nicolas.E",
                "Huguette.L",
                "Gabrielle.D",
                "Sheldon.C",
                "Léonard.H",
                "Marine.C",
                "Sisko.Y"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, items);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String nom = (String) adapterView.getItemAtPosition(position);

                //Toast.makeText(getContext(),
                  //      "Vous avez cliquer sur " + nom, Toast.LENGTH_LONG)
                    //    .show();

                // Création d'un Intent (activité)
                // Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Intent intent = new Intent(getContext(), QuizActivity.class);
                // Création d'une boite
                Bundle bundle = new Bundle();
                // Ajout de l'identifiant dans notre boite
                bundle.putString("nom",nom);
                // Ajout de notre boite dans notre prochaine activité
                intent.putExtras(bundle);
                // On demarre une activité
                startActivity(intent);
            }
        });


        listView.setAdapter(adapter);
    }
}
