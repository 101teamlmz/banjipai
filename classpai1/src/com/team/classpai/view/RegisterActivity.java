package com.team.classpai.view;

/**
 * 学生和老师注册界面  
 * author：LY   time:2016.4.16
 */
import java.util.ArrayList;
import java.util.List;


import cn.bmob.v3.listener.SaveListener;

import com.team.classpai.R;
import com.team.classpai.model1.Users;
import com.team.classpai.tools.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener{

	private static final String TAG = "RegisterActivity";

	private Button btnReg;
	private EditText etUsername;
	private EditText etPassword;
	private EditText etComfirmPsd;
	 
	private Spinner Qspinner1;
	private Spinner Qspinner2;
	
	private RadioGroup rg1; 
	private RadioButton r_stu;
	private RadioButton r_tea;

	private String username = null;
	private String password = null;
	private String comfirmPsd = null;
	private String idmessage=null;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		etComfirmPsd = (EditText) findViewById(R.id.et_comfirm_psd);
		Qspinner1=(Spinner)findViewById(R.id.spinner1);
		Qspinner2=(Spinner)findViewById(R.id.spinner2);
		
		btnReg = (Button) findViewById(R.id.btn_reg_now);
		
		rg1=(RadioGroup)findViewById(R.id.rg1);
		r_stu=(RadioButton)findViewById(R.id.r_stu);
		r_tea=(RadioButton)findViewById(R.id.r_tea);
		
		//RadioButton 的选择事件
		rg1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				
				if(r_stu.getId()==checkedId)
				{
					idmessage=r_stu.getText().toString();
				}
				else if(r_tea.getId()==checkedId)
				{
					idmessage=r_tea.getText().toString();
				}
			}
		});
		
		btnReg.setOnClickListener(this);
		
		List<String> Qlist1 = new ArrayList<String>();
		Qlist1.add("土木学院");
		Qlist1.add("机械学院");
		Qlist1.add("计算机学院");
		ArrayAdapter<String> Qadapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, Qlist1);
		//设置下拉样式
		Qadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Qspinner1.setAdapter(Qadapter);
		
		List<String> Qlist2 = new ArrayList<String>();
		Qlist2.add("1301");
		Qlist2.add("1302");
		Qlist2.add("1303");
		ArrayAdapter<String> Qadapter2 = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, Qlist2);
		//设置下拉样式
		Qadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Qspinner2.setAdapter(Qadapter2);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) 
		{
		case R.id.btn_reg_now:
		username = etUsername.getText().toString();
		password = etPassword.getText().toString();
		comfirmPsd = etComfirmPsd.getText().toString();
		String s1=Qspinner1.getSelectedItem().toString();
		String s2=Qspinner2.getSelectedItem().toString();
		
		if(!Util.isNetworkConnected(this)) {
			toast("		亲, 木有网络 ( ⊙   o ⊙ ) ");
		} else if (username.equals("") || password.equals("")
				|| comfirmPsd.equals("") || s1.equals("")||s2.equals("")||idmessage.equals("")) {
			toast("		亲, 不填完整,不能拿到通行证, ~~~~(>_<)~~~~ ");
		} else if (!comfirmPsd.equals(password)) {
			toast("		亲, 你手抖了两下, 两次密码输入不一致");
		}else 
		{
			// 开始提交注册信息
			Users bu = new Users();
			bu.setUsername(username);
			bu.setPassword(password);
			bu.setMajor(s1);
			bu.setClass1(s2);
			bu.setInfo(idmessage);
			
			bu.signUp(this, new SaveListener()
			{

				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					toast("亲, 被人捷足先登了, 换个名字吧.");
				}

				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					
					toast("亲, 有通行证了, 一起小派中遨游去吧");
					Intent backLogin = new Intent(RegisterActivity.this,
							LoginActicity.class);
					startActivity(backLogin);
					RegisterActivity.this.finish();
				}
				}
			);
				
		}
		}
	}
	
	public void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	};

}
