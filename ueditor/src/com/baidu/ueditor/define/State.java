package com.baidu.ueditor.define;

/**
 * 处理状态接口
 * @author ww-alvin    Samuel_way@163.com
 *
 */
public interface State {
	
	public boolean isSuccess ();
	
	public void putInfo( String name, String val );
	
	public void putInfo ( String name, long val );
	
	public String toJSONString ();

}
