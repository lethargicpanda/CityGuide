package com.thomasezan.lyft.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.thomasezan.lyft.R;

/**
 * Created by thomas on 2/14/15.
 */
public class StarRow extends LinearLayout{

    float rating;

    public StarRow(Context context) {
        super(context);
        init();
    }

    public StarRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StarRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        removeAllViews();

        setPadding(0,0,0,0);

        for (int i = 0; i<5; i++){
            ImageView imageView = new ImageView(getContext());
            int starRessourceId;

            if (i < Math.round(rating)){
                starRessourceId = R.drawable.star_pink;
            } else {
                starRessourceId = R.drawable.star_grey;
            }
            imageView.setImageResource(starRessourceId);
            addView(imageView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        if (rating==-1){
            setVisibility(INVISIBLE);
        }
    }

    public void setRating(float rating) {
        this.rating = rating;
        init();
    }
}
