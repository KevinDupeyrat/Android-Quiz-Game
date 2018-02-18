package iut.paci.classroomcomunnity.frame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import iut.paci.classroomcomunnity.R;
import iut.paci.classroomcomunnity.activity.QuizActivity;
import iut.paci.classroomcomunnity.adapter.FriendAdapter;
import iut.paci.classroomcomunnity.bean.Amis;

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

        List<Amis> amisList = new ArrayList<Amis>();
        amisList.add(new Amis("Loic.P", R.drawable.man, R.color.manColor));
        amisList.add(new Amis("Arnaud.R", R.drawable.man, R.color.manColor));
        amisList.add(new Amis("Aurore.B", R.drawable.woman, R.color.womanColor));
        amisList.add(new Amis("Mathieu.N", R.drawable.man, R.color.manColor));
        amisList.add(new Amis("Kevin.B", R.drawable.man, R.color.manColor));
        amisList.add(new Amis("Émilie.D", R.drawable.woman, R.color.womanColor));
        amisList.add(new Amis("Nicolas.E", R.drawable.man, R.color.manColor));
        amisList.add(new Amis("Huguette.L", R.drawable.woman, R.color.womanColor));
        amisList.add(new Amis("Gabrielle.D", R.drawable.woman, R.color.womanColor));
        amisList.add(new Amis("Sheldon.C", R.drawable.man, R.color.manColor));
        amisList.add(new Amis("Léonard.H", R.drawable.man, R.color.manColor));
        amisList.add(new Amis("Marine.C", R.drawable.woman, R.color.womanColor));
        amisList.add(new Amis("Sisko.Y", R.drawable.man, R.color.manColor));


        FriendAdapter adapter = new FriendAdapter(rootView.getContext(), R.layout.item_player, amisList);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String nom = ((Amis) adapterView.getItemAtPosition(position)).getNom();
                int ImageRessource = ((Amis) adapterView.getItemAtPosition(position)).getAvatarRessource();
                int colorText = ((Amis) adapterView.getItemAtPosition(position)).getColorRessource();

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
                bundle.putInt("avatar",ImageRessource);
                bundle.putInt("color",colorText);
                // Ajout de notre boite dans notre prochaine activité
                intent.putExtras(bundle);
                // On demarre une activité
                startActivity(intent);
            }
        });


        listView.setAdapter(adapter);
    }
}
