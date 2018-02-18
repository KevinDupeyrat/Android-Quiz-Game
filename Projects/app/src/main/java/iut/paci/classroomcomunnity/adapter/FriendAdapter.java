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
 */

public class FriendAdapter extends ArrayAdapter<Amis> {


    Context context;
    int itemRessourceId;
    List<Amis> amisList;




    public FriendAdapter(@NonNull Context context, int resource, @NonNull List<Amis> objects) {
        super(context, resource, objects);

        this.context = context;
        itemRessourceId = resource;
        amisList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //super.getView(position, convertView, parent);

        View layout = convertView;
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            layout = inflater.inflate(itemRessourceId, parent, false);
        }

        TextView nameFriend = (TextView) layout.findViewById(R.id.nameFriend);
        ImageView avatarFriend = (ImageView) layout.findViewById(R.id.avatarFiend);

        nameFriend.setText(amisList.get(position).getNom());
        nameFriend.setTextColor(this.context.getResources().getColor(amisList.get(position).getColorRessource()));
        avatarFriend.setImageResource(amisList.get(position).getAvatarRessource());

        return layout;
    }
}
