package com.team.classpai.view;

/**
 * 布置作业的功能界面
 * author: LY  2016.5.21
 */

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;

import com.team.classpai.R;
import com.team.classpai.adapter.*;
import com.team.classpai.model1.*;
import com.team.classpai.ui.BaseActivity;
import com.team.classpai.ui.Constants;
import com.team.classpai.ui.EditPopupWindow;
import com.team.classpai.ui.IPopupItemClick;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.*;
import android.widget.AdapterView.OnItemLongClickListener;


public class HomeworkActivity extends BaseActivity implements OnClickListener,OnItemLongClickListener,IPopupItemClick{

	RelativeLayout layout_action;
	LinearLayout layout_all;	//起始的作业布置页面
	TextView tv_lost;
	ListView listview;
	Button btn_add;
	
	protected QuickAdapter<Lost> LostAdapter;// 失物

	protected QuickAdapter<Found> FoundAdapter;// 招领
	
	private Button layout_found;
	private Button layout_lost;
	
	PopupWindow morePop;  //弹出框

	RelativeLayout progress;
	LinearLayout layout_no;
	TextView tv_no;
	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_homework);
	}
	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		
		progress = (RelativeLayout) findViewById(R.id.progress);//缓冲动画
		layout_no = (LinearLayout) findViewById(R.id.layout_no);//无数据
		tv_no = (TextView) findViewById(R.id.tv_no);
		
		layout_action = (RelativeLayout) findViewById(R.id.layout_action);
		layout_all = (LinearLayout) findViewById(R.id.layout_all);
		
		// 默认是作业布置页面
		tv_lost = (TextView) findViewById(R.id.tv_lost);
		tv_lost.setTag("作业布置");
		listview = (ListView) findViewById(R.id.list_lost);
		btn_add = (Button) findViewById(R.id.btn_add);
		
		// 初始化长按弹窗
		initEditPop();
		
	}
	@Override	
	public void initListeners() {
		// TODO Auto-generated method stub
		
		listview.setOnItemLongClickListener(this);
		btn_add.setOnClickListener(this);
		layout_all.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if (v == layout_all) {
			
			showListPop();
		}  else if (v == btn_add) {
			
			Intent intent = new Intent(this, AddActivity.class);
			intent.putExtra("from", tv_lost.getTag().toString());
			//可以一次性完成这项任务，当程序执行到这段代码的时候，
			//假若从T1Activity跳转到下一个Text2Activity，
			//而当这个Text2Activity调用了finish()方法以后，程序会自动跳转回T1Activity，
			//并调用前一个T1Activity中的onActivityResult( )方法。
			startActivityForResult(intent, Constants.REQUESTCODE_ADD);
		} else if (v == layout_found) {
			
			changeTextView(v);
			morePop.dismiss();
			queryFounds();
		} else if (v == layout_lost) {
			
			changeTextView(v);
			morePop.dismiss();
			queryLosts();
		}
	}
	
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
		if (LostAdapter == null) {
			LostAdapter = new QuickAdapter<Lost>(this, R.layout.item_list) {
				@Override
				protected void convert(BaseAdapterHelper helper, Lost lost) {
					
					helper.setText(R.id.ta_title4,lost.getTitle())
					.setText(R.id.tv_describe, lost.getDescribe())
					.setText(R.id.tv_time, lost.getCreatedAt())
					.setText(R.id.tv_photo, lost.getContext());
					
				}
			};
		}

		if (FoundAdapter == null) {
			FoundAdapter = new QuickAdapter<Found>(this, R.layout.item_list) {
				@Override
				protected void convert(BaseAdapterHelper helper, Found found) {
					helper.setText(R.id.ta_title4, found.getTitle())
							.setText(R.id.tv_describe, found.getDescribe())
							.setText(R.id.tv_time, found.getCreatedAt())
							.setText(R.id.tv_photo, found.getContext());
				}
			};
		}
		listview.setAdapter(LostAdapter);
		// 默认加载作业查看
		queryLosts();
	}

	private void changeTextView(View v) { //添加页面改标题名
		if (v == layout_found) {
			tv_lost.setTag("作业提交提示");
			tv_lost.setText("作业提交提示");
		} else {
			tv_lost.setTag("作业布置");
			tv_lost.setText("作业布置");
		}
	}
	
	@SuppressWarnings("deprecation")
	private void showListPop() {
		
		//获得相应布局
		View view = LayoutInflater.from(this).inflate(R.layout.pop_lost, null);
		
		// 加入两个选项
		layout_found = (Button) view.findViewById(R.id.layout_found);
		layout_lost = (Button) view.findViewById(R.id.layout_lost);
		
		layout_found.setOnClickListener(this);    //内嵌两个事件
		layout_lost.setOnClickListener(this);
		
		morePop = new PopupWindow(view, mScreenWidth, 600);

		morePop.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					
					//点击PopuWindow之外的地方此或者back键PopuWindow会消失
					morePop.dismiss();  
					return true;
				}
				return false;
			}
		});

		morePop.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
		morePop.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		morePop.setTouchable(true);
		morePop.setFocusable(true);
		
		//这里设置显示PopuWindow之后在外面点击是否有效。如果为false的话，那么点击PopuWindow外面并不会关闭PopuWindow
		morePop.setOutsideTouchable(true);
		morePop.setBackgroundDrawable(new BitmapDrawable());
		
		// 动画效果 从顶部弹下
		morePop.setAnimationStyle(R.style.MenuPop);
		////设置显示PopupWindow的位置位于View的左下方，x,y表示坐标偏移量
		morePop.showAsDropDown(layout_action, 0, -dip2px(this, 2.0F));
	}

	private void initEditPop() {
		mPopupWindow = new EditPopupWindow(this, 200, 48);
		mPopupWindow.setOnPopupItemClickListner(this);
	}

	EditPopupWindow mPopupWindow;
	int position;
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		position = arg2;
		int[] location = new int[2];
		arg1.getLocationOnScreen(location);
		mPopupWindow.showAtLocation(arg1, Gravity.RIGHT | Gravity.TOP,
				location[0], getStateBar() + location[1]);
		return false;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case Constants.REQUESTCODE_ADD:// 添加成功之后的回调
			String tag = tv_lost.getTag().toString();
			if (tag.equals("Lost")) {
				queryLosts();
			} else {
				queryFounds();
			}
			break;
		}
	}

	/**
	 * 查询全部作业信息 queryLosts
	 * 
	 * @return void
	 * @throws
	 */
	private void queryLosts() {
		showView();
		BmobQuery<Lost> query = new BmobQuery<Lost>();
		query.order("-createdAt");// 按照时间降序
		query.findObjects(this, new FindListener<Lost>() {

			@Override
			public void onSuccess(List<Lost> losts) {
				// TODO Auto-generated method stub
				LostAdapter.clear();
				FoundAdapter.clear();
				if (losts == null || losts.size() == 0) {
					showErrorView(0);
					LostAdapter.notifyDataSetChanged();
					return;
				}
				progress.setVisibility(View.GONE);
				LostAdapter.addAll(losts);
				listview.setAdapter(LostAdapter);
			}

			@Override
			public void onError(int code, String arg0) {
				// TODO Auto-generated method stub
				showErrorView(0);
			}
		});
	}
	
	public void queryFounds() {
		showView();
		BmobQuery<Found> query = new BmobQuery<Found>();
		query.order("-createdAt");// 按照时间降序
		query.findObjects(this, new FindListener<Found>() {

			@Override
			public void onSuccess(List<Found> arg0) {
				// TODO Auto-generated method stub
				LostAdapter.clear();
				FoundAdapter.clear();
				if (arg0 == null || arg0.size() == 0) {
					showErrorView(1);
					FoundAdapter.notifyDataSetChanged();
					return;
				}
				FoundAdapter.addAll(arg0);
				listview.setAdapter(FoundAdapter);
				progress.setVisibility(View.GONE);
			}

			@Override
			public void onError(int code, String arg0) {
				// TODO Auto-generated method stub
				showErrorView(1);
			}
		});
	}
	
	/**
	 * 请求出错或者无数据时候显示的界面 showErrorView
	 * 
	 * @return void
	 * @throws
	 */
	private void showErrorView(int tag) {
		progress.setVisibility(View.GONE);
		listview.setVisibility(View.GONE);
		layout_no.setVisibility(View.VISIBLE);
		if (tag == 0) {
			tv_no.setText(getResources().getText(R.string.list_no_data_lost));
		} else {
			tv_no.setText(getResources().getText(R.string.list_no_data_found));
		}
	}
	
	private void showView() {
		listview.setVisibility(View.VISIBLE);
		layout_no.setVisibility(View.GONE);
	}
	@Override
	public void onEdit(View v) {
		// TODO Auto-generated method stub
		
		String tag = tv_lost.getTag().toString();
		Intent intent = new Intent(this, AddActivity.class);
		String title = "";
		String describe = "";
		String phone = "";
		if (tag.equals("作业布置")) {
			title = LostAdapter.getItem(position).getTitle();
			describe = LostAdapter.getItem(position).getDescribe();
			phone = LostAdapter.getItem(position).getContext();
		} else {
			title = FoundAdapter.getItem(position).getTitle();
			describe = FoundAdapter.getItem(position).getDescribe();
			phone = FoundAdapter.getItem(position).getContext();
		}
		intent.putExtra("describe", describe);
		intent.putExtra("phone", phone);
		intent.putExtra("title", title);
		intent.putExtra("from", tag);
		startActivityForResult(intent, Constants.REQUESTCODE_ADD);
	}
	@Override
	public void onDelete(View v) {
		// TODO Auto-generated method stub
		String tag = tv_lost.getTag().toString();
		if (tag.equals("作业布置")) {
			deleteLost();
		} else {
			deleteFound();
		}
	}
	
	private void deleteLost() {
		Lost lost = new Lost();
		lost.setObjectId(LostAdapter.getItem(position).getObjectId());
		lost.delete(this, new DeleteListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				LostAdapter.remove(position);
			}

			@Override
			public void onFailure(int code, String arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	private void deleteFound() {
		Found found = new Found();
		found.setObjectId(FoundAdapter.getItem(position).getObjectId());
		found.delete(this, new DeleteListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				FoundAdapter.remove(position);
			}

			@Override
			public void onFailure(int code, String arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
}
