package iut.paci.classroomcomunnity.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

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
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View layout = convertView;

        // On vérifi que la View est vide avant de
        // la modifier
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            layout = inflater.inflate(itemRessourceId, parent, false);
        }

        // Récupération de la textView et de l'imageView
        TextView nameFriend = (TextView) layout.findViewById(R.id.nameFriend);
        ImageView avatarFriend = (ImageView) layout.findViewById(R.id.avatarFiend);

        // On met à jour ces deux views avec les information
        // sur l'amis séléctionné
        nameFriend.setText(amisList.get(position).getNom());
        nameFriend.setTextColor(this.context.getResources().getColor(amisList.get(position).getColorRessource()));
        avatarFriend.setImageResource(amisList.get(position).getAvatarRessource());

        // On retourne le Layout modifié
        return layout;
    }
}
