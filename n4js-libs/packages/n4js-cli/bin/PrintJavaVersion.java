/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */

/** Utility to detect and print out the Java version */
public class PrintJavaVersion {
	public static void main(String[] args) {
		String versionStr = System.getProperty("java.version");
		System.out.println(versionStr);
	}
}
