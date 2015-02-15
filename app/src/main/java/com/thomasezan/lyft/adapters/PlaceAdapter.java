package com.thomasezan.lyft.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.thomasezan.lyft.R;
import com.thomasezan.lyft.customviews.StarRow;
import com.thomasezan.lyft.models.Place;
import java.util.ArrayList;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by thomas on 2/13/15.
 */
public class PlaceAdapter extends BaseAdapter {

    private ArrayList<Place> placeList;
    private Place.TYPE type;
    private LayoutInflater inflater;

    public PlaceAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setPlaceList(ArrayList<Place> placeList, Place.TYPE type) {
        this.placeList = placeList;
        this.type = type;
    }

    @Override
    public int getCount() {
        if (placeList==null){
            return 0;
        }

        return placeList.size();
    }

    @Override
    public Object getItem(int position) {
        if (placeList==null){
            return null;
        }

        return placeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.place_item_view, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        Place currentPlace = (Place)getItem(position);

        viewHolder.nameTextView.setText(currentPlace.getName());
        viewHolder.starRow.setRating(currentPlace.getRating());
        viewHolder.placeImageView.setImageResource(type.iconResource);

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.place_name) TextView nameTextView;
        @InjectView(R.id.star_row) StarRow starRow;
        @InjectView(R.id.place_icon) ImageView placeImageView;


        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
