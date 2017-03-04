package com.psi2.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.psi2.config.Configuration;

public class DirectoryUtils {

	
	/**
	 * Method to get the content from a web
	 */
	public static String getContentFromHtmlPage(String page) {
		URL url = null;
		try {
			url = new URL(page);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		BufferedReader in = null;
		try {
			if (url != null) {
				in = new BufferedReader(new InputStreamReader(url.openStream()));
			}
		} catch (IOException e) {
			
		}
		String result = "";
		String inputLine;
		try {
			if(in!=null){
			while ((inputLine = readLine(in)) != null)
				result += inputLine;
			}
		} catch (IOException e) {
			
		}
		try {
			if (in != null)
				in.close();
		} catch (IOException e) {
			
		}

		return result;
	}
	/**
	 * Read a line
	 */
	public static String readLine(BufferedReader br) throws IOException{
		StringBuffer sb=new StringBuffer();
		int intC;
		intC=br.read();
		String line=null;
		do{
			if(intC==-1)
				return null;
			char c=(char) intC;
			if(c=='\n'){
				break;
			}
			if(sb.length()>=1000000){
				throw new IOException("input too long");
			}
			sb.append(c);
		} while(((intC=br.read())!=-1));
		line=sb.toString();
		return line;
		}

}
