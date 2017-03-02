package com.psi2.testing;

import java.util.List;
import java.util.Map;

import com.psi2.config.Configuration;
import com.psi2.utils.FileUtils;

public class Testing {

	public static void main(String[] args) {
		Configuration config=new Configuration("C:\\Users\\ADRIAN\\Desktop\\config.properties");
		Map<String,List<String>> map=config.getProperties();
		System.out.println(map.keySet());
		System.out.println(map.values());
		for(String s:map.keySet()){
			if(s.contains("\\")){
				List<String> lista=map.get(s);
				for(String r:lista){
				String str=FileUtils.readFile(s+"\\"+r);
				str=FileUtils.hashContent(str,config);
				System.out.println(s+"\\"+r+" --- Contenido ---> "+str);
				}
			}	
		}
		
	}

}
