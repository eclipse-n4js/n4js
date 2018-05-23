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
package org.eclipse.n4js;

import org.eclipse.xtext.util.CancelIndicator;

/**
 */
public class CancelIndicatorBaseExtractor {

	/**
	 * We can't hardcode a dependency on EclipseResourceFileSystemAccess2 because IDE-1739, ie the headless compiler
	 * would get a dependency on org.eclipse.xtext.builder.
	 * <p>
	 * This is a stop-gap measure until a real cancel indicator is made available to an IGenerator (or IGenerator2 as
	 * proposed in https://bugs.eclipse.org/bugs/show_bug.cgi?id=477068) in a more straightforward manner.
	 * <p>
	 * In the meantime the cancel indicator is extracted from IFileSystemAccess (in the IDE scenario only; in the
	 * headless compiler scenario a CancelIndicator.NullImpl is used instead).
	 * <p>
	 * This method implements the default behavior, which is also the expected behavior for use in the headless
	 * compiler.
	 *
	 */
	public CancelIndicator extractCancelIndicator(@SuppressWarnings("unused") Object fsa) {
		return CancelIndicator.NullImpl;
	}
}
