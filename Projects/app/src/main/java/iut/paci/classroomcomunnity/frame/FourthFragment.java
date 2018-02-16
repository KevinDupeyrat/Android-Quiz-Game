package iut.paci.classroomcomunnity.frame;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iut.paci.classroomcomunnity.R;

/**
 * Quatrième Fragment
 */
public class FourthFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Fourth Fragment");
        View rootView = inflater.inflate(R.layout.fragment_fourth, container, false);

        return rootView;

    }
}
