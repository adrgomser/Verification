package com.psi2.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class Configuration {
	private String dir;
	private String algoritmo;
	private String clave;
	private String tiempo;
	private GlobalConfiguration globalConfig;

	/**
	 * This class have all the variables that we will need to run the
	 * application
	 */
	public Configuration(GlobalConfiguration global) {
		super();
		this.dir = global.getConfigurationFile();
		this.globalConfig = global;
		loadConfig(dir);
	}



	
	/**
	 * Read a line
	 */
	public String readLine(BufferedReader br) throws IOException{
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
	

	/**
	 * Loading all the variables from the configuration file
	 */
	public void loadConfig(String dir) {
		Properties propiedades = new Properties();

		try {
			File file = new File(dir);
			if (file.exists()) {
				propiedades.load(new FileInputStream(dir));
				this.algoritmo = propiedades.getProperty("algoritmo");
				this.clave = propiedades.getProperty("clave");
				this.tiempo = propiedades.getProperty("tiempo");
			} 
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			
		}
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	

	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}



	public GlobalConfiguration getGlobalConfig() {
		return globalConfig;
	}

	public void setGlobalConfig(GlobalConfiguration globalConfig) {
		this.globalConfig = globalConfig;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

}
