package com.example.johnny.story;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements StoryStatusView.UserInteractionListener{

    Button next,pervious;
    StoryStatusView storyStatusView;

    private ViewPager mPager;
    private int currentPage = 0;
    private int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;

    SlidingImage_Adapter pagerAdapter;

    private int[] myImageList = new int[]{
            R.drawable.android,
            R.drawable.brightness,
            R.drawable.cake};


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();

        init();

        storyStatusView.playStories();
        storyStatusView.pause();
    }

    private ArrayList<ImageModel> populateList(){

        ArrayList<ImageModel> list = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }

        return list;
    }

    private void init() {
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPager.setOffscreenPageLimit(0);
        storyStatusView = findViewById(R.id.storiesStatus);
        storyStatusView.setUserInteractionListener(MainActivity.this);
        storyStatusView.setStoriesCount(3);
        storyStatusView.setStoryDuration(5000L);
        pagerAdapter = new SlidingImage_Adapter(MainActivity.this,imageModelArrayList);
        mPager.setAdapter(pagerAdapter);

        final float density = getResources().getDisplayMetrics().density;

        NUM_PAGES =imageModelArrayList.size();

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //fuck this shit
//                Animation animZoomIn = AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoom_in);
//        animZoomIn.setAnimationListener(this);
//                pagerAdapter.imageView.startAnimation(animZoomIn);

            }

            @Override
            public void onPageSelected(int position) {
                if (currentPage != position){
                    if (position > currentPage){
                        Toast.makeText(MainActivity.this, "next", Toast.LENGTH_SHORT).show();
                        storyStatusView.skip();
                        currentPage = position;
                    }else {
                        Toast.makeText(MainActivity.this, "shit", Toast.LENGTH_SHORT).show();
                        storyStatusView.reverse();
                        currentPage = position;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        storyStatusView.resume();
    }

    @Override
    public void onNext() {
        mPager.setCurrentItem(++currentPage, true);
    }

    @Override
    public void onPrev() {

    }

    @Override
    public void onComplete() {
        storyStatusView.playStories();
        currentPage = 0;
        mPager.setCurrentItem(0, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        storyStatusView.destroy();
    }
}
