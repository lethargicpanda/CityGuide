package com.thomasezan.lyft.customviews;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

    private int whiteColor;
    private int orangeColor;

    private Place.TYPE currentPlaceType;

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

    void init(){
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.slider_view, this, true);

        whiteColor = getResources().getColor(R.color.white);
        orangeColor = getResources().getColor(R.color.lyft_orange);

        ButterKnife.inject(this, getRootView());

        currentPlaceType = Place.TYPE.CAFE;
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

            int barTextViewColor = whiteColor;
            int bistroTextViewColor = whiteColor;
            int cafeTextViewColor = whiteColor;

            if (touchLocation<(getWidth()/3)){
                finalPosition = barTextView.getX();
                barTextViewColor = orangeColor;
                currentPlaceType = Place.TYPE.CAFE;
            } else if (touchLocation<(getWidth()*2/3)) {
                finalPosition = bistroTextView.getX();
                bistroTextViewColor = orangeColor;
                currentPlaceType = Place.TYPE.STARBUCKS;
            } else if (touchLocation>(getWidth()*2/3)) {
                finalPosition = cafeTextView.getX();
                cafeTextViewColor = orangeColor;
                currentPlaceType = Place.TYPE.PEETS_COFFEE;
            }

            barTextView.setTextColor(barTextViewColor);
            bistroTextView.setTextColor(bistroTextViewColor);
            cafeTextView.setTextColor(cafeTextViewColor);

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

    void postCurrentPlaceType(){
        BusProvider.getInstance().post(currentPlaceType);
    }
}
