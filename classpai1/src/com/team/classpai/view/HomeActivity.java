package com.team.classpai.view;

import com.ljp.ani.TestRolateAnimActivity;
import com.team.classpai.R;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 应用主界面
 * @date  2016.4.24
 * @author LY
 */
@SuppressWarnings("deprecation")
public class HomeActivity extends TabActivity {
	
	@SuppressWarnings("unused")
	private static final String TAG = "HomeActivity";
	
	private TabHost tabHost;
	private LayoutInflater layoutInflater;
	
	
	String[] mTitle = new String[] { "小派", "校园", "个人主页"};  
    int[] mIcon = new int[] { R.drawable.class_log1, R.drawable.service,  
            R.drawable.botice};   

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home); 
		
       initTabView();
	}
	
	public View getTabItemView(int i) {  
        // TODO Auto-generated method stub  
        View view = layoutInflater.inflate(R.layout.tab_widget_item, null);  
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);  
        imageView.setImageResource(mIcon[i]);  
        TextView textView = (TextView) view.findViewById(R.id.textview);  
        textView.setText(mTitle[i]);  
        return view;  
    } 
	
	public void initTabView() {
		
		/** 
         * tabHost.newTabSpec("artist")创建一个标签项，其中artist为它的标签标识符，相当于jsp页面标签的name属性 
         * setIndicator("艺术标签",resources.getDrawable(R.drawable.ic_tab))设置标签显示文本以及标签上的图标（该图标并不是一个图片，而是一个xml文件哦） 
         * setContent(intent)为当前标签指定一个意图 
         * tabHost.addTab(spec); 将标签项添加到标签中 
         */  
		
        tabHost = getTabHost();  
        layoutInflater = LayoutInflater.from(this);
        TabHost.TabSpec spec;   //TabSpec 代表选项卡的一个Tab页面
        
        //班级
		Intent intent1 = new Intent(this, Class1Activity.class);  
        spec = tabHost.newTabSpec(mTitle[0]).setIndicator( getTabItemView(0) ).setContent(intent1);  
        tabHost.addTab(spec);   //增加该选项卡
        
        //校园
        Intent intent2 = new Intent(this, TestRolateAnimActivity.class);  
       spec = tabHost.newTabSpec(mTitle[1]).setIndicator( getTabItemView(1) ).setContent(intent2);  
       tabHost.addTab(spec);
        
        //个人
        Intent intent3 = new Intent(this, ActivityMine.class);  
        spec = tabHost.newTabSpec(mTitle[2]).setIndicator( getTabItemView(2) ).setContent(intent3);  
        tabHost.addTab(spec); 
        
       
        
       tabHost.setCurrentTab(0);   //默认为第一个Tab
	}
	
	
	//退出 (存在问题)
	 public boolean onKeyDown(int keyCode, KeyEvent event)  {  
       if (keyCode == KeyEvent.KEYCODE_BACK )   {  
      	 AlertDialog.Builder builder = new Builder(HomeActivity.this); 
     		 builder.setIcon(android.R.drawable.ic_dialog_info);
     	        builder.setMessage("确定要退出?"); 
     	        builder.setTitle("提示"); 
     	        builder.setPositiveButton("确认", 
     	                new android.content.DialogInterface.OnClickListener() { 
     	                    public void onClick(DialogInterface dialog, int which) { 
     	                        dialog.dismiss(); 
     	                    HomeActivity.this.finish(); 
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
