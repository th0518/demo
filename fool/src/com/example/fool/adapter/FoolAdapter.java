package com.example.fool.adapter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fool.R;
import com.example.fool.entity.ArticleEntity;

public class FoolAdapter extends BaseAdapter{
	private Context context;
	private List<ArticleEntity> list;
	ViewHolder mViewHolder;
	public FoolAdapter(Context context, List<ArticleEntity> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(v == null){
			mViewHolder = new ViewHolder();
			v = LayoutInflater.from(context).inflate(R.layout.item, null);
			mViewHolder.name = (TextView) v.findViewById(R.id.name);
			mViewHolder.img = (ImageView) v.findViewById(R.id.item_img);
			v.setTag(mViewHolder);
		}else{
			mViewHolder = (ViewHolder) v.getTag();
		}
		mViewHolder.name.setText(list.get(position).getName());
		Bitmap bm = BitmapFactory.decodeFile(list.get(position).getUrl());
		Log.d("info", list.get(position).getUrl());
		mViewHolder.img.setImageBitmap(bm);
		return v;
	}
	class ViewHolder{
		TextView name;
		ImageView img;
	}
}
