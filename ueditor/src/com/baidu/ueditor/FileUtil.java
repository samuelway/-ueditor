package com.baidu.ueditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;



public class FileUtil {
	
	static int i=1;
	
	static BufferedWriter bf=null;
	static FileWriter fw=null;
	static String s="";
	
	private static Set<String> fileset=new HashSet<>();
	
	
	public static void main(String[] args) throws IOException {
		File file=new File("D:\\home\\mwclg\\resources");
		try {
			listDir(file);
			System.out.println("图片数"+i);
			System.out.println(fileset.size());
			File files=new File("D:\\home\\file.log");
			
			writeFile(files);
		} catch (Exception e) {
		e.printStackTrace();
		}finally {
			bf.close();
			fw.close();
		}
		
	}
	public static void writeFile(File file) throws Exception{
		
		
		BufferedReader br=new BufferedReader(new FileReader(file));
		
		while (br.readLine()!=null) {
			System.out.println(String.valueOf(br.readLine()));
		}
		br.close();
		
	}
	
	public static void appendStr(String ss) throws IOException{
		try {
			File files=new File("D:\\home");
			if(!files.exists()){
				files.mkdirs();
			}
			File filew=new File("D:\\home\\file.log");
			filew.createNewFile();
			
			fw=new FileWriter(filew,true);
			fw.append(ss);
			bf=new BufferedWriter(fw);
			fileset.add(ss);
		} catch (Exception e) {
			
		}		
		finally {
			bf.close();
			fw.close();
		}
		
	}
	public static  String listDir(File file) throws IOException{
		String fileName="";
		if(file.isDirectory()){
			File[] files=file.listFiles();
			for (File file2 : files) {
				i+=1;
				if(file2.isDirectory()){
					listDir(file2);
				}else{
					fileName+=file2.getName()+":"+file2.getPath()+"\r\n";	
					appendStr(fileName);
				}
				
			}
		}else{
			fileName+=file.getName()+":"+file.getPath()+"\r\n";	
			appendStr(fileName);
		}
		return fileName;
	}


}
