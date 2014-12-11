package com.example.fool.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper{

	public OpenHelper(Context context) {
		super(context, SysTemCall.DBNAME, null, SysTemCall.DBVSERION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub autoincrement
		db.execSQL("create table if not exists " + SysTemCall.TABNAME +"(_id integer primary key autoincrement," +
				"name varchar(20),location varchar(100),url varchar(1000))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
