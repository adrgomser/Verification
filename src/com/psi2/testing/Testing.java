package com.psi2.testing;


import com.psi2.client.IntegrityVerifierClient;
import com.psi2.config.Configuration;
import com.psi2.config.GlobalConfiguration;
import com.psi2.server.IntegrityVerifierServer;
import com.psi2.utils.ExecutionUtils;

public class Testing {

	public static void main(String[] args) {
		GlobalConfiguration globalConfig=new GlobalConfiguration("C:\\Users\\ADRIAN\\Desktop\\Universidad\\SSI\\PAI\\PAI2\\config.properties", 
				"C:\\Users\\ADRIAN\\Desktop\\Universidad\\SSI\\PAI\\PAI2\\Logs\\");
		Configuration config=new Configuration(globalConfig);
		ExecutionUtils.setConfiguration(config);
		IntegrityVerifierServer server = null;
		try {
			server = new IntegrityVerifierServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new IntegrityVerifierClient();
		server.runServer();
	}
}
