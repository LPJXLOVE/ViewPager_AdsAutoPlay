package com.example.lpjxlove.viewpager_adsautoplay;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LPJXLOVE on 2016/4/23.
 */
public class GetAdsData {

    private List<View> views;
    private Context context;

    public GetAdsData(Context context) {
        this.context=context;
         views=new ArrayList<>();
    }

    public void Add(int ResId){
        if (views.size()>3){
            throw new IllegalStateException("数量不能超过3！");
        }
        ImageView imageView=new ImageView(context);
        imageView.setImageResource(ResId);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        views.add(imageView);
    }

    public List<View> GetData(){
        if (views.size()==0){
            throw new IllegalStateException("数量不能为0！");
        }
        return views;
    }


}
