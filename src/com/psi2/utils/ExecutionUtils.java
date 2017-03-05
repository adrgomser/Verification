package com.psi2.utils;

import com.psi2.config.Configuration;

public class ExecutionUtils {

	private static Configuration configuration;


	public static Configuration getConfiguration() {
		return configuration;
	}

	public static void setConfiguration(Configuration c) {

		configuration = c;

	}

}
