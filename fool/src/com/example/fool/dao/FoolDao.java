package com.example.fool.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.util.Log;

import com.example.fool.entity.ArticleEntity;
import com.example.fool.utils.OpenHelper;
import com.example.fool.utils.SysTemCall;

public class FoolDao {
	OpenHelper mOpenHelper;
	Context mContext;
	SQLiteDatabase database;
	public FoolDao( Context mContext) {
		super();
		this.mContext = mContext;
		mOpenHelper = new OpenHelper(mContext);
	}
	//添加进数据库
	public void insertFool(ArticleEntity entity){
		database = mOpenHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", entity.getName());
		values.put("location", entity.getLocation());
		values.put("url", entity.getUrl());
		database.insert(SysTemCall.TABNAME, null, values);
	}
	//删除
	public boolean delete(String names){
		database = mOpenHelper.getReadableDatabase();
//		int result = database.delete(SysTemCall.TABNAME, "name="+"'names'", new String[]{names});
		int result=database.delete(SysTemCall.TABNAME, "name=?", new String[]{names});
		if(result>0){
			return true;
		}else{
			return false;
		}
	}
	//查询
	public List<ArticleEntity> queryAll(){
		List<ArticleEntity> list = new ArrayList<ArticleEntity>();
	
		database = mOpenHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select * from "+SysTemCall.TABNAME, null);
		while(cursor.moveToNext()){
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String location = cursor.getString(cursor.getColumnIndex("location"));
			String url = cursor.getString(cursor.getColumnIndex("url"));
			ArticleEntity entity = new ArticleEntity(name, location, url);
			list.add(entity);
		}
		return list;
	}
	
	//根据条件查询
	public List<ArticleEntity> querySomeOne(String name){
		List<ArticleEntity> list = new ArrayList<ArticleEntity>();
		database = mOpenHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select * from "+SysTemCall.TABNAME+"where name = "+"+'name'+", null);
		while(cursor.moveToNext()){
			String name2 = cursor.getString(cursor.getColumnIndex("name"));
			String location = cursor.getString(cursor.getColumnIndex("location"));
			
			ArticleEntity entity = new ArticleEntity(name2, location, null);
			list.add(entity);
		}
		return list;
	}
	
	public void close(){
		if(database !=null){
			
			database.close();
		}
	}
	
	
}
