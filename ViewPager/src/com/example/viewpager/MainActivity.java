package com.example.viewpager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends Activity {
	//间隔秒数
	private static final long TIME_INTERVAL = 5000;
	private ImageView[] imgViews;

	private ViewPager viewPager;
	private ViewGroup group;
	int[] img = {R.drawable.erji,R.drawable.beiying,R.drawable.hua};
	private AtomicInteger what = new AtomicInteger(0);
	private List<View> list;
	public boolean b = true;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(msg.what);
			if(msg.what==list.size()-1){
				Intent intent = new Intent(MainActivity.this, FirstActivity.class);
				startActivity(intent);
			}
			super.handleMessage(msg);
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initImg();
		
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(listener);
		viewPager.setOnTouchListener(lis);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					if(b){
						handler.sendEmptyMessage(what.get());
						whatOption();
					}
				}
			}
		}).start();
	}
	
	private void whatOption() {
		what.incrementAndGet();
		if (what.get() > imgViews.length - 1) {
			what.getAndAdd(-4);
		}
		try {   
			Thread.sleep(TIME_INTERVAL);
		} catch (InterruptedException e) {

		}
	}
	//初始化组件以及添加图片和底部的脚标
	public void initImg(){
		group = (ViewGroup) findViewById(R.id.group);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		list = new ArrayList<View>();
		for(int i = 0;i<img.length;i++){
			ImageView imgView = new ImageView(MainActivity.this);
			imgView.setBackgroundResource(img[i]);
//			imgView.setMinimumHeight(screenHeigh);   
			list.add(imgView);
		}
		imgViews = new ImageView[list.size()];
		for(int i = 0;i<list.size();i++){
			ImageView imgView = new ImageView(MainActivity.this);
			imgView.setLayoutParams(new LayoutParams(20, 20));
			imgView.setPadding(5, 5, 5, 5);
			imgViews[i] = imgView;
			if(i == 0){
				imgViews[i].setBackgroundResource(android.R.drawable.btn_star_big_on);
			}else{
				imgViews[i].setBackgroundResource(android.R.drawable.btn_star_big_off);
			}
			group.addView(imgViews[i]);
		}
	}
	
	//设置adapter
	PagerAdapter adapter = new PagerAdapter() {
		
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			View view = list.get(position);
			
			container.addView(view);
			return view;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position,
				Object object) {
			// TODO Auto-generated method stub
			View view = list.get(position);
			container.removeView(view);
		}
	};
	
	//设置页面变化监听，并且设置图标明暗变换
	OnPageChangeListener listener = new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			for(int i = 0 ;i<imgViews.length;i++){
				if(i == arg0){
					imgViews[i].setBackgroundResource(android.R.drawable.btn_star_big_on);
				}else{
					imgViews[i].setBackgroundResource(android.R.drawable.btn_star_big_off);
				}
			}
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	OnTouchListener lis = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				b = false;
				break;
			case MotionEvent.ACTION_UP:
				b = true;
				break;
			default:
				b = true;
				break;
			}
			return false;
		}
	};
}
	