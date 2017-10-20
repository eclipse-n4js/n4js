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
 *
 */
public class CreateConfigFileForSettingDefaultProduct {

	/**
	 * Main
	 */
	public static void main(String[] args) throws IOException {
		// Create config init
		if (args.length < 1) {
			return;
		}
		String targetHome = args[0];
		String folderPath = targetHome + File.separatorChar + "configuration";
		File folder = new File(folderPath);
		System.out.println("We want to create folder " + folderPath);
		if (!folder.exists())
			folder.mkdirs();
		System.out.println("Folder " + folderPath + " has been created successfully!");

		String filePath = folderPath + File.separatorChar + "config.ini";
		File file = new File(filePath);

		// Create the file
		if (file.createNewFile()) {
			System.out.println("File is created!");
		} else {
			System.out.println("File already exists.");
			return;
		}

		FileWriter writer = new FileWriter(file);
		writer.write("eclipse.product=org.eclipse.n4js.product.product");
		writer.close();
		System.out.println("File" + filePath + " has been created");
	}

}
