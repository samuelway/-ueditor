package com.baidu.ueditor.upload;

import java.io.File;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.UploadFileClient;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;

public final class Base64Uploader {
    static Logger logger = LoggerFactory.getLogger(Base64Uploader.class);
	public static State save(String content, Map<String, Object> conf) {
		
		byte[] data = decode(content);

		long maxSize = ((Long) conf.get("maxSize")).longValue();

		if (!validSize(data, maxSize)) {
			return new BaseState(false, AppInfo.MAX_SIZE);
		}

		String suffix = FileType.getSuffix("JPG");

		String savePath = PathFormat.parse((String) conf.get("savePath"),
				(String) conf.get("filename"));
		String localSavePathPrefix = PathFormat.parse((String) conf.get("localSavePathPrefix"),
                (String) conf.get("filename"));
		savePath = savePath + suffix;
		localSavePathPrefix = localSavePathPrefix + suffix;
		String physicalPath = localSavePathPrefix;
		logger.info("Base64Uploader physicalPath:{},savePath:{}",localSavePathPrefix,savePath);
		State storageState = StorageManager.saveBinaryFile(data, physicalPath);

		if (storageState.isSuccess()) {
			//上传远程图片
			Integer remotreAddress = Integer.parseInt(String.valueOf(conf.get("remoteState"))) ;//远程上传是否开启
			if(remotreAddress==1){
				File file=new File(physicalPath);
				if(file.exists()){
					String uploadPath = (String) conf.get("remotreAddress");//远程地址
					UploadFileClient.uploadFile(file.getAbsolutePath(),uploadPath);
				}
			}
			storageState.putInfo("url", PathFormat.format(savePath));
			storageState.putInfo("type", suffix);
			storageState.putInfo("original", "");
		}

		return storageState;
	}

	private static byte[] decode(String content) {
		return Base64.decodeBase64(content);
	}

	private static boolean validSize(byte[] data, long length) {
		return data.length <= length;
	}
	
}