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

/**
 * This class access the variable ${target_home} and create a config.ini file specifying
 * org.eclipse.n4js.product.product as the default product to run. This saves us from having to manually configure the
 * product again and again whenever we trigger a test.
 * <P>
 * Attention: If you change this class, make sure to create a new JAR to replace
 * lib/CreateConfigFileForSettingDefaultProduct.jar!
 * <p>
 * Cmd for creating jar in bin/ folder: jar -cvf CreateConfigFileForSettingDefaultProduct.jar
 * org/eclipse/n4js/runconfig/CreateConfigFileForSettingDefaultProduct.class
 * <p>
 */
public class CreateConfigFileForSettingDefaultProduct {

	/**
	 * Main logic to create config.ini file in ${target_home}/configuration folder
	 */
	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			return;
		}

		String targetHome = args[0];
		String defaultProductId = args[1];

		String folderPath = targetHome + File.separatorChar + "configuration";
		File folder = new File(folderPath);
		if (!folder.exists())
			folder.mkdirs();

		String filePath = folderPath + File.separatorChar + "config.ini";
		File file = new File(filePath);

		// Create the file config.ini
		if (!file.createNewFile()) {
			System.out.println("File " + filePath + " already exists.\n"
					+ "By default, the product to run is set to " + defaultProductId);
			return;
		}

		FileWriter writer = new FileWriter(file);
		writer.write("eclipse.product=" + defaultProductId);
		writer.close();

		System.out.println("File" + filePath + " has been created successfully!\n"
				+ "By default, the product to run is set to " + defaultProductId);
	}

}
