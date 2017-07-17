package com.baidu.ueditor;
import java.io.File;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

/**
 * 把当前服务器上的资源，上传到静态资源库上
 * @author 
 *
 */
public class UploadFileClient{
	
	public  static void uploadFile(String path,String urlPath){
		String  targetURL = urlPath+"/UploadFileServlet"; //servleturl  resource/opweb
		File targetFile = new File(path);
		PostMethod filePost = new PostMethod(targetURL);
		System.out.println("path:"+path);
		try{
			Part[] parts = { new FilePart(targetFile.getName(), targetFile) };
			filePost.setRequestEntity(new MultipartRequestEntity(parts,filePost.getParams()));
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			int status = client.executeMethod(filePost);
			if (status == HttpStatus.SC_OK){
				System.out.println("上传成功");
				// 上传成功
			}else{
				System.out.println("上传失败");
				// 上传失败
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}finally{
			filePost.releaseConnection();
		} 
	}
}