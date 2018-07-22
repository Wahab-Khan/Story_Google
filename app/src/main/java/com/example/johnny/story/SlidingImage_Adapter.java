package com.example.johnny.story;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SlidingImage_Adapter extends PagerAdapter implements Animation.AnimationListener {

    private ArrayList<ImageModel> imageModelArrayList;
    private LayoutInflater inflater;
    private Context context;
    Animation animZoomIn, a;

    ImageView imageView, iconImage;
    TextView tv,sourceText;
    View imageLayout;

    public SlidingImage_Adapter(Context context, ArrayList<ImageModel> imageModelArrayList) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageModelArrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        imageView = imageLayout.findViewById(R.id.image);
        tv = imageLayout.findViewById(R.id.discription);
        iconImage = imageLayout.findViewById(R.id.icon);
        sourceText = imageLayout.findViewById(R.id.source);

        imageView.setImageResource(imageModelArrayList.get(position).getImage_drawable());

        view.addView(imageLayout, 0);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        //main image animation
        animZoomIn = AnimationUtils.loadAnimation(context, R.anim.zoom_in);
//        animZoomIn.setAnimationListener(this);
        imageView.startAnimation(animZoomIn);

        //text fading animation
        Animation a = AnimationUtils.loadAnimation(context, R.anim.fade_in);
//        a.setAnimationListener(this);
        tv.startAnimation(a);

        Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale);
        iconImage.startAnimation(anim);

        Animation RtoL = AnimationUtils.loadAnimation(context, R.anim.right_to_left);
        sourceText.startAnimation(RtoL);

        return imageLayout;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {

        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }


    @Override
    public Parcelable saveState() {
        return null;
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

}