package com.example.fool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.example.fool.dao.FoolDao;
import com.example.fool.entity.ArticleEntity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import java.util.Date;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PhotoActivity extends Activity{
	private ImageButton photo;
	private ImageView img;
	private EditText name,location;
	private Button save,add_sound;
	private TextView time;
	private static boolean b = false;
	private static String sdStatus = Environment.getExternalStorageState();

	File file = new File("/sdcard/self/myImage/");
	File filesRecoder = new File("/sdcard/self/recorder/");
	private static String imgName = "";
	private static String recorderName = "";
	String fileName = "";
	public static Bitmap bitmap = null;
	public static FileOutputStream fos = null;

	SimpleDateFormat dateFormat = null;
	Date data  = null;
	
	MediaPlayer player;
	MediaRecorder recorder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo);
		initView();
		Log.d("qqqqqqqqqqqq", sdStatus);  
		player = new MediaPlayer();
		recorder = new MediaRecorder();
		try {
			recorderName = dateFormat.format(data)+".3gp";
			//设置输入源和输出格式、输出文件 文件夹、设置编码格式
			recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			recorder.setOutputFile(recorderName);
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			recorder.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 初始化组件
	 */
	private void initView() {
		photo = (ImageButton) findViewById(R.id.photo);
		img = (ImageView) findViewById(R.id.img_photo);
		name = (EditText) findViewById(R.id.edit_name);
		location = (EditText) findViewById(R.id.location);
		save = (Button) findViewById(R.id.save);
		add_sound = (Button) findViewById(R.id.add_sound);
		file.mkdirs();// 创建文件夹
		filesRecoder.mkdirs();

		dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		data = new Date(System.currentTimeMillis());

		photo.setOnClickListener(listener);
		save.setOnClickListener(listener);
		add_sound.setOnClickListener(listener);
		time = (TextView) findViewById(R.id.time);
		time.setText("时间："+dateFormat.format(data).toString());
		
		
	}

	//设置点击事件
	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.photo:
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivityForResult(intent, 11);
				break;

			case R.id.save:
				ArticleEntity articleEntity = null;
				try {
					imgName = dateFormat.format(data)+".jpg";
					time.setText(dateFormat.format(data).toString());
					fileName = "/sdcard/self/myImage/"+imgName;
					fos = new FileOutputStream(fileName);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
					Toast.makeText(getApplicationContext(), fileName, 1).show();

					articleEntity = new ArticleEntity();
					articleEntity.setName(name.getText().toString());
					articleEntity.setLocation(location.getText().toString());
					articleEntity.setUrl(fileName);
					Log.d("aaaaaaaaaaaaa", articleEntity.toString()+"sssssssssssss");

					FoolDao dao =new FoolDao(PhotoActivity.this);
					dao.insertFool(articleEntity);
			   		Log.d("jjjjjj", fileName+"**************");
					Intent in = new Intent(PhotoActivity.this, MainActivity.class);
					startActivity(in);
			   		finish();   
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{
					try {
						fos.flush();   
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				break;
				//录音点击事件
			case R.id.add_sound:

				
				if(b){
					recorder.start();
					add_sound.setBackgroundResource(R.drawable.sound);
					b = true;
				}else{   
					add_sound.setBackgroundResource(R.drawable.luyin);
					b =false;
					recorder.stop();
					recorder.release();
					recorder = null;
				}   
				break;
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode,Intent data) 
	{
		switch(resultCode)
		{
		case RESULT_OK:
			super.onActivityResult(requestCode, resultCode, data); 
			if(requestCode == 11){
				if(data != null){
					Bundle bundle = data.getExtras();
					bitmap = (Bitmap) bundle.get("data");
					img.setImageBitmap(bitmap);
					img.setMinimumWidth(WindowManager.LayoutParams.MATCH_PARENT);
					img.setMinimumHeight((WindowManager.LayoutParams.MATCH_PARENT)/3);
				}
			}
			break;
		}

	}
}
