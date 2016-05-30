package com.team.classpai.view;

import java.io.File;
import java.io.FileNotFoundException;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import com.team.classpai.R;
import com.team.classpai.model1.Found;
import com.team.classpai.model1.Lost;
import com.team.classpai.ui.BaseActivity;

public class AddActivity extends BaseActivity implements OnClickListener{

	EditText edit_title, edit_photo, edit_describe;
	Button btn_back, btn_true;

	TextView tv_add;
	ImageView iv_icon;
	String from = "";
	
	String old_title = "";
	String old_describe = "";
	String old_phone = "";
	
	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_add);
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		
		tv_add = (TextView) findViewById(R.id.tv_add);
		
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_true = (Button) findViewById(R.id.btn_true);
		
		edit_photo = (EditText) findViewById(R.id.edit_photo);
		edit_describe = (EditText) findViewById(R.id.edit_describe);
		edit_title = (EditText) findViewById(R.id.edit_title);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		btn_back.setOnClickListener(this);
		btn_true.setOnClickListener(this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
		//编辑时将值传入
		from = getIntent().getStringExtra("from");
		old_title = getIntent().getStringExtra("title");
		old_phone = getIntent().getStringExtra("phone");
		old_describe = getIntent().getStringExtra("describe");
		
		edit_title.setText(old_title);
		edit_describe.setText(old_describe);
		edit_photo.setText(old_phone);
		
		if (from.equals("作业布置")) {
			tv_add.setText("作业布置");
		} else {
			tv_add.setText("作业提交情况");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btn_true) {
			addByType();
		} else if (v == btn_back) {
			finish();
		}
	}

	
	String title = "";
	String photo="";
	String describe = "";
	String path;
	
	private void addByType(){
		title = edit_title.getText().toString();
		photo = edit_photo.getText().toString();
		describe = edit_describe.getText().toString();
		
		if(TextUtils.isEmpty(title)){
			ShowToast("请填写科目");
			return;
		}
		if(TextUtils.isEmpty(describe)){
			ShowToast("请填写内容");
			return;
		}
		if(TextUtils.isEmpty(photo)){
			ShowToast("请填写备注");
			return;
		}
		if(from.equals("作业布置")){
			addLost();
		}else{
			addFound();
		}
		onActivityResult(mScreenHeight, mScreenHeight, null);
	}

	private void addLost(){
		Lost lost = new Lost();
		lost.setTitle(title);
		lost.setContext(photo);
		lost.setDescribe(describe);
		
		
		submit();
		lost.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ShowToast("作业信息添加成功!");
				setResult(RESULT_OK);
				finish();
			}
			
			@Override
			public void onFailure(int code, String arg0) {
				// TODO Auto-generated method stub
				ShowToast("作业信息添加失败:"+arg0);
			}
		});
	}
	
	private void addFound(){
		Found found = new Found();
		found.setDescribe(describe);
		found.setContext(photo);
		found.setTitle(title);
		submit();
		found.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ShowToast("作业情况发布成功成功!");
				setResult(RESULT_OK);
				finish();
			}
			
			@Override
			public void onFailure(int code, String arg0) {
				// TODO Auto-generated method stub
				ShowToast("发布失败:"+arg0);
			}
		});
	}
	
	/**
	 * 点击按钮显示图片并获得图片路径
	 */
	@Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
          
        Button button = (Button)findViewById(R.id.btn_up);   
        button.setOnClickListener(new Button.OnClickListener(){  
        	
            @Override  
            public void onClick(View v) {  
                Intent intent = new Intent();  
                /* 开启Pictures画面Type设定为image */  
                intent.setType("image/*");  
                /* 使用Intent.ACTION_GET_CONTENT这个Action */  
                intent.setAction(Intent.ACTION_GET_CONTENT);   
                /* 取得相片后返回本画面 */  
                startActivityForResult(intent, 1);  
                
            }  
              
        });  
    }  
	
	 @Override  
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        if (resultCode == RESULT_OK) {  
	            Uri uri = data.getData();  
	            
	            String[] proj = {MediaStore.Images.Media.DATA};

	            //好像是android多媒体数据库的封装接口，具体的看Android文档

	            Cursor cursor = managedQuery(uri, proj, null, null, null); 

	            //按我个人理解 这个是获得用户选择的图片的索引值

	            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

	            //将光标移至开头 ，这个很重要，不小心很容易引起越界

	            cursor.moveToFirst();

	            //最后根据索引值获取图片路径
	            path = cursor.getString(column_index);
	            TextView tv=(TextView) findViewById(R.id.tv1);
	            tv.setText(path);
	            //上传图片
	            submit();
	            Log.e("uri", uri.toString());  
	            ContentResolver cr = this.getContentResolver();  
	            try {  
	                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));  
	                ImageView imageView = (ImageView) findViewById(R.id.iv);  
	                /* 将Bitmap设定到ImageView */  
	                imageView.setImageBitmap(bitmap);  
	            } catch (FileNotFoundException e) {  
	                Log.e("Exception", e.getMessage(),e);  
	            }  
	        }  
	        super.onActivityResult(requestCode, resultCode, data);  
	    }  
	 
	 /**
	     * 上传图片到Bmob
	     */
	  private void submit() {
			//上传图片
		    TextView tv=(TextView) findViewById(R.id.tv1);
		    path=tv.getText().toString();
	        final BmobFile file=new BmobFile(new File(path)); //创建BmobFile对象，转换为Bmob对象
	        file.upload(AddActivity.this,new UploadFileListener() {
	        	@Override
	            public void onSuccess() {
	        		Lost lost=new Lost();
	                lost.setIcon(file);  //设置图片
	                lost.save(AddActivity.this,new SaveListener() {
	                	 @Override
	                     public void onSuccess() {
	                         Toast.makeText(AddActivity.this,"上传成功",Toast.LENGTH_LONG).show();
	                     }
	  
	                     @Override
	                     public void onFailure(int i, String s) {
	                         Toast.makeText(AddActivity.this,"上传失败"+s,Toast.LENGTH_LONG).show();
	  
	                     }
	                }
	                );
	        	}

				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onProgress(Integer arg0) {
					// TODO Auto-generated method stub
					
				}
	        	
	        }
	        );
		}
	  
	  private void submit1() {
			//上传图片
		    TextView tv=(TextView) findViewById(R.id.tv1);
		    path=tv.getText().toString();
	      final BmobFile file=new BmobFile(new File(path)); //创建BmobFile对象，转换为Bmob对象
	      file.upload(AddActivity.this,new UploadFileListener() {
	      	@Override
	          public void onSuccess() {
	      		Found found=new Found();
	              found.setIcon(file);  //设置图片
	              found.save(AddActivity.this,new SaveListener() {
	              	 @Override
	                   public void onSuccess() {
	                       Toast.makeText(AddActivity.this,"上传成功",Toast.LENGTH_LONG).show();
	                   }

	                   @Override
	                   public void onFailure(int i, String s) {
	                       Toast.makeText(AddActivity.this,"上传失败"+s,Toast.LENGTH_LONG).show();

	                   }
	              }
	              );
	      	}

				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onProgress(Integer arg0) {
					// TODO Auto-generated method stub
					
				}
	      	
	      }
	      );
		}
}
