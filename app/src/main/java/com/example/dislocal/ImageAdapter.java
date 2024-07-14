package com.example.dislocal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private final String[] gridValues;



    public Integer[] thumbImages = {
            R.drawable.abouticon, R.drawable.discovericon, R.drawable.feedbackicon,
            R.drawable.profileicon,
    };

    public ImageAdapter(Context c, String[] gridValues){mContext = c ;
        this.gridValues = gridValues;
    }

    @Override
    public int getCount(){return thumbImages.length;}

    @Override
    public Object getItem(int position){return thumbImages[position];}

    @Override
    public long getItemId(int position){return 0;}

    @Override
    public View getView(int position, View converrtView, ViewGroup parent){
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(250,250));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(20,20,20,20);
        imageView.setImageResource(thumbImages[position]);
        return imageView;
    }
}
