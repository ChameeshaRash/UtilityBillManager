package com.mobileapp.app;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ScreenshotsAdapter extends PagerAdapter {

    private int[] image_resources={
            R.drawable.img_onboarding_1,
            R.drawable.img_onboarding_2,
            R.drawable.img_onboarding_3
    };



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

        imageView.setImageResource(image_resources[position]);
        container.addView(item_view);
        return item_view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }

}