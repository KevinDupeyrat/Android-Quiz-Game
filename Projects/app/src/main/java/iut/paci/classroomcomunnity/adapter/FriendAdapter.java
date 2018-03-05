package iut.paci.classroomcomunnity.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import iut.paci.classroomcomunnity.R;
import iut.paci.classroomcomunnity.bean.Amis;

/**
 * Created by dupeyrat on 18/02/18.
 *
 * Class Adapter qui va permetre de mettre les informations
 * relative a la Class Amis dans la ListView
 */

public class FriendAdapter extends ArrayAdapter<Amis> {


    Context context;
    int itemRessourceId;
    List<Amis> amisList;


    /**
     * Constructeur de la class
     *
     * @param context
     * @param resource
     * @param objects
     */
    public FriendAdapter(@NonNull Context context, int resource, @NonNull List<Amis> objects) {
        super(context, resource, objects);

        this.context = context;
        itemRessourceId = resource;
        amisList = objects;
    }


    /**
     * Méthode qui permet renvoie la LinearView qui contiendra
     * le nom et l'avatar de l'ami
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View layout = convertView;
        Amis ami = amisList.get(position);
        // Pour tous les amis sauf pour nous (je suis l'ID 11 dans la liste)



        // On vérifi que la View est vide avant de
        // la modifier
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            layout = inflater.inflate(itemRessourceId, parent, false);
        }

        // Récupération de la textView et de l'imageView
        TextView nameFriend = (TextView) layout.findViewById(R.id.nameFriend);
        TextView lastScoreFriend = (TextView) layout.findViewById(R.id.lastScoreFriend);
        ImageView connected = (ImageView) layout.findViewById(R.id.connected);
        TextView initial = (TextView) layout.findViewById(R.id.initial);
        CircleImageView avatarCircle = (CircleImageView) layout.findViewById(R.id.avatarFiend);

//        int r = getRandomColor(129, 255);
//        int g = getRandomColor(129, 255);
//        int b = getRandomColor(129, 255);
//        avatarCircle.setColorFilter(Color.rgb(r, g, b));


        if(ami.isPresent() == 0) {
            connected.setImageResource(R.drawable.circle_absent);
            nameFriend.setAlpha(0.2f);
            lastScoreFriend.setAlpha(0.2f);
            avatarCircle.setAlpha(0.2f);
            initial.setAlpha(0.2f);
        } else {
            connected.setImageResource(R.drawable.circle_present);
            nameFriend.setAlpha(1.0f);
            lastScoreFriend.setAlpha(1.0f);
            avatarCircle.setAlpha(1.0f);
            initial.setAlpha(1.0f);
        }


        // On met à jour ces deux views avec les information
        // sur l'amis séléctionné
        nameFriend.setText(ami.getNom());
        initial.setText(String.valueOf(ami.getNom().charAt(0)));
        lastScoreFriend.setText("Dernier score : " + ami.getLastScore());


        // On retourne le Layout modifié
        return layout;
    }

    private int getRandomColor(int min, int max) {

        return (int) (Math.random() * (max - min)) + min;
    }


}
