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
	 * Read all files from a directory to see if it has the correct amount of
	 * files
	 */
	public static String readDirectory(String directory, Configuration config) {
		File folder = new File(directory);
		File[] files = folder.listFiles();
		int longitud = -1;
		if (files != null) {
			longitud = files.length;
		}
		List<String> lista = config.getProperties().get(directory);
		if (longitud != -1) {
			if (longitud > lista.size()) {
				return "ERROR --- Directory '" + directory
						+ "' has more files than specified on the config file";
			} else if (longitud < lista.size()) {
				return "ERROR --- Directory '" + directory
						+ "' has less files than specified on the config file";
			} else {
				return "OK --- Directory '" + directory
						+ "' has the correct number of files";
			}
		}
		return "ERROR --- Directory '" + directory
				+ "' has more files than specified on the config file";
	}

	/**
	 * Read the file to see if its hash is correct
	 */
	public static String proveFile(String directory, String arch,
			Configuration config) {
		String dir = directory + "\\" + arch;
		File file = new File(dir);
		if (file.exists()) {
			String hash = FileUtils.hashContent(FileUtils.readFile(file),
					config);
			if (config.getHashes().containsKey(dir)) {
				if (config.getHashes().get(dir).equals(hash)) {
					return "OK --- File '" + dir + "' hash correct";
				} else {
					return "ERROR --- File '" + dir + "' hash incorrect";
				}
			} else {
				config.getHashes().put(dir, hash);
				return "OK --- File '" + dir + "' hash not found --- CREATING";
			}
		} else {
			return "ERROR --- File '" + dir + "' does not exist";
		}

	}

	/**
	 * Read the web to see if its hash is correct
	 */
	public static String proveWeb(String web, Configuration config) {

		String content = getContentFromHtmlPage(web);
		if (!content.equals("")) {
			String hash = FileUtils.hashContent(content, config);
			if (config.getHashes().containsKey(web)) {
				if (config.getHashes().get(web).equals(hash)) {
					return "OK --- Web '" + web + "' hash correct";
				} else {
					return "ERROR --- Web '" + web + "' hash incorrect";
				}
			} else {
				config.getHashes().put(web, hash);
				return "OK --- Web '" + web + "' hash not found --- CREATING";
			}
		} else {
			return "ERROR --- Web '" + web + "' content not found";
		}

	}

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
