/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.jsdoc;

/**
 * Interface for JSDocTokenizers.
 */
public interface IJSDocTokenizer {

	/**
	 * Returns next JSDocToken based on current state of JSDocCharScanner. Each implementing class may decide if it will
	 * be single char, single word, substring or rest of the string provided by JSDocCharScanner. This method normally
	 * has one side effect - it shnges state of JSDocCharScanner to the offset_ where it finished parsing. If parsing was
	 * unsuccesfull caller Should take care for restoring proper state of the scanner.
	 *
	 * @param scanner
	 *            JSDocCharScanner holding input string and pointer to lat parsed position
	 * @return JSDocToken for parsed part of input string.
	 */
	JSDocToken nextToken(JSDocCharScanner scanner);

}
