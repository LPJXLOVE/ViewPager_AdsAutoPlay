package com.example.lpjxlove.viewpager_adsautoplay;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PagerAdapter adapter;
    private ViewPager viewPager;
    private List<View> views;
    private static final int AUTOPLAY = 0;//自动播放
    private static final int MANUALLY = 1;//手机播放
    private Myhandler myhandler;
    private ImageView iv_one, iv_two, iv_three;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_one = (ImageView) findViewById(R.id.one);
        iv_two = (ImageView) findViewById(R.id.two);
        iv_three = (ImageView) findViewById(R.id.three);
        InitData();
    }

    private void InitData() {
        myhandler = new Myhandler(this);
        GetAdsData data = new GetAdsData(this);
        data.Add(R.drawable.first);
        data.Add(R.drawable.second);
        data.Add(R.drawable.third);
        views = data.GetData();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;//设置Count为无限大，这样就可以模拟实现循环效果

            }


            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                int location = Math.abs(position);
                //location%viewsize 取余数
                if (views.get(location % views.size()).getParent() != null) {
                    ((ViewPager) views.get(location % views.size())
                            .getParent()).removeView(views.get(location
                            % views.size()));
                }


                (container).addView(views.get(location % views.size()), 0);


                return views.get(location % views.size());

            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };
        viewPager.setAdapter(adapter);

        //设置位置为中间位置
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2);
        myhandler.sendEmptyMessageDelayed(AUTOPLAY, 5000);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //设置小圆点
                int mark = position % views.size();
                switch (mark) {
                    case 0:
                        iv_three.setImageResource(R.drawable.point_unpress);
                        iv_two.setImageResource(R.drawable.point_unpress);
                        iv_one.setImageResource(R.drawable.point_press);

                        break;
                    case 1:
                        iv_one.setImageResource(R.drawable.point_unpress);
                        iv_two.setImageResource(R.drawable.point_press);
                        iv_three.setImageResource(R.drawable.point_unpress);
                        break;

                    case 2:
                        iv_one.setImageResource(R.drawable.point_unpress);
                        iv_two.setImageResource(R.drawable.point_unpress);
                        iv_three.setImageResource(R.drawable.point_press);

                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        //当手指碰到ViewPager控件时，发送信息,设置为手动模式
                        myhandler.sendEmptyMessage(MANUALLY);

                        break;

                    case ViewPager.SCROLL_STATE_IDLE:
                        //当手指离开ViewPager时，发送信息，设置为自动模式
                        myhandler.sendEmptyMessageDelayed(AUTOPLAY, 5000);

                        break;
                }

            }
        });
    }


    static class Myhandler extends Handler {
        private WeakReference<MainActivity> weakReference;

        public Myhandler(MainActivity activity) {
            weakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = weakReference.get();
            if (activity == null) {

                return;
            }
            if (activity.myhandler.hasMessages(AUTOPLAY)) {
                removeMessages(AUTOPLAY);
            }

            switch (msg.what) {
                case AUTOPLAY://设置自动播放信息
                    activity.viewPager.setCurrentItem(activity.viewPager.getCurrentItem() + 1);

                    break;
                case MANUALLY://接收信息，不做任何处理;

                    break;
            }


        }
    }

}
