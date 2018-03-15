package iut.paci.classroomcomunnity.frame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iut.paci.classroomcomunnity.R;

/**
 * Created by Kevin Dupeyrat on 28/02/18.
 *
 * Class qui représente la page de renseignement
 * sur l'Autheur
 */
public class AboutFragment extends Fragment {

    public AboutFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Modification du titre
        getActivity().setTitle("À propos");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }
}
