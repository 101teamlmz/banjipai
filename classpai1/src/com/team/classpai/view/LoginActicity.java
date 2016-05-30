package com.team.classpai.view;

import com.team.classpai.R;
import com.team.classpai.model1.Users;
import com.team.classpai.tools.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;



/**
 * 登陆界面
 * 
 * @date 2016-4-24
 * @author LY
 */
public class LoginActicity extends Activity implements OnClickListener {

	@SuppressWarnings("unused")
	private static final String TAG = "LoginActicity";

	private Button btnLogin;
	private Button btnReg;
	private Button btnback;
	
	private EditText etUsername;
	private EditText etPassword;

	private String username;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 初始化 BMOB SDK
		// 使用时请将第二个参数Application ID替换成Bmob服务器端创建的Application ID
		Bmob.initialize(this, "00e3ef4338672e8f00f15fb261a4d074");
		setContentView(R.layout.activity_login);

		btnLogin = (Button) findViewById(R.id.btn_login);
		btnReg = (Button) findViewById(R.id.btn_register);
		btnback=(Button)findViewById(R.id.btn_reset_psd);
		
		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);

		btnLogin.setOnClickListener(this);
		btnReg.setOnClickListener(this);
		btnback.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 登陆
		case R.id.btn_login:
			username = etUsername.getText().toString();
			password = etPassword.getText().toString();
			
			if(!Util.isNetworkConnected(this) ){
				toast("亲, 没有网络  ( ⊙    o ⊙  ) ");
			} else if (username.equals("") || password.equals("")) {
				toast("亲, 请输入小派账号和密码");
				break;
			} else {
				Users bu2 = new Users();
				bu2.setUsername(username);
				bu2.setPassword(password);
				bu2.login(this, new SaveListener() {

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						toast("亲, 网络不通, 无法登录小派");
					}

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						toast("欢迎您的加入~~");
						
						//将用户名传给主页
						String data=username;
						// 跳转到主页
						/*Intent toHome = new Intent(LoginActicity.this,
								HomeActivity.class);
						toHome.putExtra("data", data);
						toHome.putExtra("info", "1");   //登录成功标记
						startActivity(toHome);*/
						
						Intent intent=new Intent();
						intent.putExtra("data", data);
						setResult(RESULT_OK, intent);
						finish();
					}
					
				});
			}
			break;

		case R.id.btn_register:
			Intent toReg = new Intent(LoginActicity.this,
					RegisterActivity.class);
			startActivity(toReg);
			break;
		case R.id.btn_reset_psd:
			toast("暂时未开通此项业务~~");
		default:
			break;

		}
	}

	public void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}

	//退出
 	 public boolean onKeyDown(int keyCode, KeyEvent event)  {  
        if (keyCode == KeyEvent.KEYCODE_BACK )   {  
       	 AlertDialog.Builder builder = new Builder(LoginActicity.this); 
      		 builder.setIcon(android.R.drawable.ic_dialog_info);
      	        builder.setMessage("确定要退出?"); 
      	        builder.setTitle("提示"); 
      	        builder.setPositiveButton("确认", 
      	                new android.content.DialogInterface.OnClickListener() { 
      	                    public void onClick(DialogInterface dialog, int which) { 
      	                        dialog.dismiss(); 
      	                     LoginActicity.this.finish(); 
      	                    } 
      	                }); 
      	        builder.setNegativeButton("取消", 
      	                new android.content.DialogInterface.OnClickListener() { 
      	                    public void onClick(DialogInterface dialog, int which) { 
      	                        dialog.dismiss(); 
      	                    } 
      	                }); 
      	        		builder.create().show();  
        }    
        return false;       
    }

}

