package com.psi2.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Configuration {
	private String dir;
	private Map<String,List<String>> properties;
	private String algoritmo;
	private String tiempo;
	
	public Configuration(String dir) {
		super();
		this.dir = dir;
		loadConfig(dir);
	}

	public void loadConfig(String dir){
		Map<String,List<String>> map=new HashMap<String,List<String>>();
		Properties propiedades= new Properties();
		
		try {
			File file=new File(dir);
			if(file.exists()){
			propiedades.load(new FileInputStream(dir));
			this.algoritmo=propiedades.getProperty("algoritmo");
			this.tiempo=propiedades.getProperty("tiempo");
			String directories=propiedades.getProperty("directories");
			String[] direc=directories.split(";");
			int length=1;
			for (String s:direc){
				List<String> lista=new ArrayList<String>();
				String files=propiedades.getProperty(""+length);
				for(String r:files.split(";")){
					lista.add(r);
				}
				map.put(s,lista);
				length++;
			}
			String urls=propiedades.getProperty("urls");
			List<String> lista=new ArrayList<String>();
			for (String s:urls.split(";")){
				lista.add(s);
			}
			map.put("urls", lista);
			}else{
				System.out.println("ERROR - Config file not found");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.properties=map;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public Map<String, List<String>> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, List<String>> properties) {
		this.properties = properties;
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
	
	
	
}
