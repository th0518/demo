package com.example.fool;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.example.fool.adapter.FoolAdapter;
import com.example.fool.dao.FoolDao;
import com.example.fool.entity.ArticleEntity;

public class MainActivity extends Activity {
	private SwipeMenuListView mListView;
	private Button mAddBtn;
	private FoolDao dao;
	private List<ArticleEntity> list;
	ArticleEntity articleEntity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		dao = new FoolDao(this);
		mAddBtn.setOnClickListener(listener);
		list = getEntities();
		final FoolAdapter adapter = new FoolAdapter(this, list);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(lis);
		//第一步：创建一个SwipeMenuCreator 来添加item
		
		SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
			
			@Override
			public void create(SwipeMenu menu) {
				// TODO Auto-generated method stub
				SwipeMenuItem deleItem = new SwipeMenuItem(MainActivity.this);
				deleItem.setBackground(new ColorDrawable(Color.rgb(0xf9, 0x3f, 0x25)));
				deleItem.setWidth(120);
				deleItem.setIcon(R.drawable.ic_delete);
				menu.addMenuItem(deleItem);
			
			}
			
		};
		mListView.setMenuCreator(swipeMenuCreator);
		
		mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(int arg0, SwipeMenu arg1, int arg2) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					dao.delete(list.get(arg0).getName());
					list.remove(arg0);
					adapter.notifyDataSetChanged();
					       
					break;   

				default:
					break;
				}
				return false;
			}
		});
	}
	public void initView(){
		mListView = (SwipeMenuListView) findViewById(R.id.add_wupin);
		mAddBtn = (Button) findViewById(R.id.add_btn);
		
	}
	//给添加按钮设置点击跳转
	OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MainActivity.this, PhotoActivity.class); 
			startActivity(intent);
		}
	};
	public List<ArticleEntity> getEntities(){
		List<ArticleEntity> entities =null;
		
		if(dao.queryAll() != null){
			entities = dao.queryAll();
		}
		
		
		return entities;
	}
	
	OnItemClickListener lis = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			articleEntity = list.get(position);
			Intent in= new Intent(MainActivity.this, InfoActivity.class);
			in.putExtra("entity", articleEntity);
			startActivity(in);
			
		}
	};
}
