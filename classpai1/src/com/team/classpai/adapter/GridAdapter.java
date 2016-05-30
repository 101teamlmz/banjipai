package com.team.classpai.adapter;

import com.team.classpai.R;
import com.team.classpai.ui.TypeDef;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * -- 网格布局(ImageView+TextView)适配器
 * 
 * @date 2016.4.24
 * @author LY
 */
public class GridAdapter extends BaseAdapter {

	private Context mContext;
	private int mIndex = 0; // 代表当前需要适配页面中第几个GridView

	//班级服务
	public static String[] mSchoolTexts = TypeDef.typeSonList1;
	private int[] mSchoolImages = { R.drawable.scool_page2, R.drawable.class_color2,
			R.drawable.school_course2, R.drawable.class_homework2};
	
	//小派特递
	public static String[] mFoodTexts = TypeDef.typeSonList2;
	private int[] mFoodImages = { R.drawable.ic_5, R.drawable.ic_5,
			R.drawable.ic_5, R.drawable.ic_5};

	

	public GridAdapter(Context context, int index) {
		mContext = context;
		mIndex = index;
	}

	@Override
	public int getCount() {
		int count = 0;
		switch (mIndex) {
		case 0:
			count = mSchoolImages.length;
			break;
		case 1:
			count = mFoodImages.length;
			break;
		
		default:
			break;
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(mContext, R.layout.shop_grid_item, null);
		

		ImageView image = (ImageView) view.findViewById(R.id.img_chooseImage);
		TextView text = (TextView) view.findViewById(R.id.tv_chooseText);
		switch (mIndex) {
		case 0:
			image.setImageResource(mSchoolImages[position]);
			text.setText(mSchoolTexts[position]);
			break;
		case 1:
			image.setImageResource(mFoodImages[position]);
			text.setText(mFoodTexts[position]);
			break;
		default:
			break;
		}

		return view;
	}

}
