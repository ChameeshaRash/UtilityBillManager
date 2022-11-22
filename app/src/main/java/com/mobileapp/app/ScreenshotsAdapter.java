package com.mobileapp.app;

import androidx.viewpager.widget.PagerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class ScreenshotsAdapter extends PagerAdapter {

    //images set of the slider
    private int[] image_resources={
            R.drawable.frame1,
            R.drawable.frame2,
            R.drawable.frame3
    };
//    //text set of the slider
//    private String[] image_text_resources={
//            String.valueOf(R.string.text1),
//            String.valueOf(R.string.text2),
//            String.valueOf(R.string.text3)
//
//    };



    private LayoutInflater layoutInflater;
    private Context ctx;

    public ScreenshotsAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return image_resources.length;
    }

    @Override
    public boolean isViewFromObject( View view,Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View item_view=layoutInflater.inflate(R.layout.activity_screenshots,container,false);

        ImageView imageView=(ImageView)item_view.findViewById(R.id.slider_image);
        //TextView textView=(TextView)item_view.findViewById(R.id.textView_Slider);

        imageView.setImageResource(image_resources[position]);
        //textView.setText(image_text_resources[position]);
        container.addView(item_view);

        return item_view;



    }
    @Override
    public void destroyItem(ViewGroup container, int position,  Object object) {
        container.removeView((LinearLayout)object);
    }

}