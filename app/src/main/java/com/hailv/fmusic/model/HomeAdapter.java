package com.hailv.fmusic.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hailv.fmusic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msi on 3/20/2018.
 */

public class HomeAdapter extends ArrayAdapter<Songs> {
    private ArrayList<Songs> arrsongs;
    private LayoutInflater layoutInflater;



    public HomeAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Songs> objects) {
        super(context, resource, objects);
        this.arrsongs = objects;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //pt nay se giup lay 1 view tho --> (ket hop voi 1 phan tu mang tuong ung) --> de tao ra 1 item cua listview
        ViewHolder holder;
        if (convertView == null){ //lan dau tien ==> se tao moi
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_home_layout, parent, false);

            holder.tvName = (TextView)convertView.findViewById(R.id.tvName);


            convertView.setTag(holder);
        }else { //lay view tu holder
            holder = (ViewHolder)convertView.getTag();
        }

        //thiet lap gia tri cua cac item ung voi moi phan tu mang
        final Songs songs = arrsongs.get(position);


        holder.tvName.setText(songs.getName());
        holder.tvDuration.setText(songs.getDuration());



        return convertView;
    }

    public static class ViewHolder{
        public TextView tvName;
        public TextView tvDuration;
    }
}
