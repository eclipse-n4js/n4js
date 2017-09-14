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
package org.eclipse.n4js.flowgraphs.analysers;

/**
 *
 */
public class Test {

	public static void log(String l) {
		System.out.println(l);
	}

	public static void main(String[] args) {
		rek(args);
	}

	public static int rek(String[] args) {
		log("start");

		try {
			for (String s : args)
				;
		} catch (Throwable t) {

		}

		R: do {
			try {
				log("1");
				if (true)
					return 0;
				else if (false)
					throw new RuntimeException();

			} finally {
				log("3");
				// throw new RuntimeException();
				continue R;
			}
		} while (true);
		// log("end");
	}
}
