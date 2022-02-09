/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.resource;

import org.eclipse.xtext.util.ITextRegionWithLineInformation;

/**
 *
 */
public interface XITextRegionWithLineInformation extends ITextRegionWithLineInformation {

	/** @return the character position in the start line */
	int getCharacter();

	/** @return the character position in the end line */
	int getEndCharacter();

	/** @return true iff there exists jsdoc */
	boolean hasJsDoc();

	/** @return the jsdoc or null */
	String getJsDoc();

}
