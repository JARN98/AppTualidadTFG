package com.example.apptualidad.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.apptualidad.R;

import java.util.List;

public class ImageAdapter extends PagerAdapter {
    private Context cxt;
    private List<String> urls;
    private ImageView[] photos;
    private LayoutInflater layoutInflater;

    public ImageAdapter(Context context, List<String> rutaDeImagenes) {
        cxt = context;
        urls = rutaDeImagenes;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) cxt.getSystemService(cxt.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView_image);

        if (urls.size() > 0) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            Glide
                    .with(this.cxt)
                    .load(urls.get(position))
                    .into(imageView);


            ViewPager vp = (ViewPager) container;
            vp.addView(view, 0);
        }


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
