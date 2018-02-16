package iut.paci.classroomcomunnity.frame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iut.paci.classroomcomunnity.R;

/**
 * Fragment un
 */
public class FirstFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("First Fragment");
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);

        return rootView;

    }
}
