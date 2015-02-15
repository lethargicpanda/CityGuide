package com.thomasezan.lyft.customviews;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.thomasezan.lyft.R;
import com.thomasezan.lyft.models.Place;
import com.thomasezan.lyft.providers.BusProvider;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnTouch;

/**
 * Created by thomas on 2/14/15.
 */
public class SliderSelector extends RelativeLayout {

    @InjectView(R.id.bar_textview) TextView barTextView;
    @InjectView(R.id.bistro_textview) TextView bistroTextView;
    @InjectView(R.id.cafe_textview) TextView cafeTextView;
    @InjectView(R.id.slider_view) View sliderView;

    Place.TYPE currentPlaceType;

    public SliderSelector(Context context) {
        super(context);
        init();
    }

    public SliderSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SliderSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.slider_view, this, true);

        ButterKnife.inject(this, getRootView());

        currentPlaceType = Place.TYPE.BAR;

    }

    @OnTouch(R.id.tray_layout)
    public boolean onSliderTouch(View view, MotionEvent event){
        float sliderViewCenterX = sliderView.getWidth()/2;
        float touchLocation = event.getX();

        // Move the slider along the tray
        if (event.getAction()==MotionEvent.ACTION_MOVE && touchLocation - sliderViewCenterX > 0 && touchLocation + sliderViewCenterX < getWidth()){
            sliderView.setX(touchLocation - sliderViewCenterX);
        }

        // Set the final position of the slider on release (ACTION_UP)
        if (event.getAction()==MotionEvent.ACTION_UP){
            float finalPosition = 0;

            if (touchLocation<(getWidth()/3)){
                finalPosition = barTextView.getX();
                currentPlaceType = Place.TYPE.BAR;
            } else if (touchLocation<(getWidth()*2/3)) {
                finalPosition = bistroTextView.getX();
                currentPlaceType = Place.TYPE.BISTRO;
            } else if (touchLocation>(getWidth()*2/3)) {
                finalPosition = cafeTextView.getX();
                currentPlaceType = Place.TYPE.CAFE;
            }

            postCurrentPlaceType();

            // Animate the slider
            ValueAnimator animator = ValueAnimator.ofFloat(sliderView.getX(), finalPosition);
            animator.setDuration(100);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    sliderView.setX((Float)animation.getAnimatedValue());
                }
            });

            animator.start();
        }

        return true;
    }


    public Place.TYPE getCurrentPlaceType(){
        return currentPlaceType;
    }

    public void postCurrentPlaceType(){
        BusProvider.getInstance().post(currentPlaceType);
    }
}
