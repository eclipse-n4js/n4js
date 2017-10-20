/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.runconfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * This class access the variable ${target_home} and create a config.ini file specifying
 * org.eclipse.n4js.product.product as the default product to run. This saves us from having to manually configure the
 * product again and again whenever we trigger a test. GH-307. Attention: If you change this class, make sure to make a
 * new JAR to replace lib/CreateConfigFileForSettingDefaultProduct.jar!
 */
public class CreateConfigFileForSettingDefaultProduct {

	private static Logger LOGGER = Logger.getLogger(CreateConfigFileForSettingDefaultProduct.class);

	private static final String defaultProductId = "org.eclipse.n4js.product.product";

	/**
	 * Main logic to create config.ini file in ${target_home}/configuration folder
	 */
	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			return;
		}
		String targetHome = args[0];
		String folderPath = targetHome + File.separatorChar + "configuration";
		File folder = new File(folderPath);
		if (!folder.exists())
			folder.mkdirs();

		String filePath = folderPath + File.separatorChar + "config.ini";
		File file = new File(filePath);

		// Create the file config.ini
		if (!file.createNewFile()) {
			LOGGER.info("File " + filePath + "already exists. By default, the product to run is set to " +
					defaultProductId);
			return;
		}

		FileWriter writer = new FileWriter(file);
		writer.write("eclipse.product=" + defaultProductId);
		writer.close();
		System.out.println("File" + filePath
				+ " has been created successfully! From now on, the default product to run is set to " +
				defaultProductId);
	}

}
