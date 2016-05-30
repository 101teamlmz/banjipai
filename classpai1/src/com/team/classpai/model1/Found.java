package com.team.classpai.model1;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/** 招领
  * @ClassName: 提交作业信息对象
  * @Description: TODO
  * @author LY
  * @date 2016-4-26 
  */
public class Found extends BmobObject {

	private String title;//标题
	private String describe;//描述
	private String context;//内容
	private BmobFile icon;//图片
	 
	public BmobFile getIcon() {
		return icon;
	}
	public void setIcon(BmobFile icon) {
		this.icon = icon;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	
}
