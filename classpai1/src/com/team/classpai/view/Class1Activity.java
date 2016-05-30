package com.team.classpai.view;


import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import com.team.classpai.R;
import com.team.classpai.adapter.GridAdapter;
import com.team.classpai.model1.Users;
import com.team.classpai.ui.MyGridView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Class1Activity extends Activity implements  OnItemClickListener{

	@SuppressWarnings("unused")
	private static final String TAG = "Class1Activity" ;

	private TextView tt;         //控制登录(2)
	private TextView show;       //显示登录状态
	
	private MyGridView givClass;  	    //班级服务
	private MyGridView divClass;		//小派特递
	
	private int flag=0;    //判断是否登录
	private String s_name;
	private String idtype;
 	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class1);
		
		initView();
		
	}

 	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}


	/**
	 * 初始化组件并适配数据
	 */
	public void initView() {
		givClass = (MyGridView) findViewById(R.id.gv_gift_class);
		
		divClass = (MyGridView) findViewById(R.id.gv_food_class);
		
		//V1.0
		//班级服务
		givClass.setAdapter(new GridAdapter(this, 0));
		givClass.setOnItemClickListener(this);
		
		//小派快递
		divClass.setAdapter(new GridAdapter(this, 1));
		divClass.setOnItemClickListener(this);
		
		show=(TextView)findViewById(R.id.tv_show);
		tt=(TextView)findViewById(R.id.tv_d);
		
		tt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent();
				
				intent = new Intent(Class1Activity.this, LoginActicity.class);
				//用startActivityForResult来使下一个活动完成后将数据返回
				//第一个参数还是 Intent，第二个参数是请求码，用于在之后的回调中判断数据的来源
				startActivityForResult(intent, 1);
			}
		});
		/*imbg1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				
				intent = new Intent(Class1Activity.this, LoginActicity.class);
				//用startActivityForResult来使下一个活动完成后将数据返回
				//第一个参数还是 Intent，第二个参数是请求码，用于在之后的回调中判断数据的来源
				startActivityForResult(intent, 1);
			}
		});*/
		
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(requestCode)
		{
		case 1:
			if(resultCode ==RESULT_OK)
			{
				s_name=data.getStringExtra("data");
				flag=1;   //用户已经登录
				if(flag==1)
				{
				//根据BMOB数据库查询相应Users对象的信息
				BmobQuery<Users> quary= new BmobQuery<Users>();
				//查询符合用户名的信息
				quary.addWhereEqualTo("username", s_name);
				//返回1条数据
				quary.findObjects(this, new FindListener<Users>() {
					
					@Override
					public void onSuccess(List<Users> objects) {
						// TODO Auto-generated method stub
						
						for(Users user :objects)
						{
							//获得Users的信息
							//toast(user.getPassword());
							idtype=user.getInfo();
							show.setText("你好！ "+s_name);
							//toast("用户的身份信息："+user.getInfo());
							//user.getCreatedAt();   //数据的创建时间
						}
					}
					
					@Override
					public void onError(int code, String msg) {
						// TODO Auto-generated method stub
						toast("查询失败");
					}
				});
			}
			}
			
			break;
		default:
			break;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.i("GridView点击了： ", "position"+position);
		
		switch (parent.getId())
		{
		case R.id.gv_gift_class:		
			Intent intent = new Intent();
			if(position==3)
			{
				if(flag==1)
				{
				toast("你选择班级作业服务");
				intent = new Intent(Class1Activity.this, HomeworkActivity.class);
				startActivity(intent);
				}
				else
				{
					toast("您尚未登录！请先登录<- ->");
				}
			}
			else
			{
				toast("你选择班级服务，相关服务暂未开通");
			}
			break;
			
		case R.id.gv_food_class:
			toast("你选择小派特递，相关服务暂未开通");
			break;
			
			default:
				break;
		}
	}
	
	/*//获取本地用户
	public void GetCurrentUser()
	{
		String username=(String)BmobUser.getObjectByKey(this, "username");	
		
		Log.i("bmob", "username："+username);
	}*/
	
	@SuppressWarnings("unused")
	private void toast(String toast) { 
		Toast.makeText(this, toast,  Toast.LENGTH_SHORT).show();
	};
}
