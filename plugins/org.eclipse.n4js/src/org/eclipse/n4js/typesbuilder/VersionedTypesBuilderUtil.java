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
package org.eclipse.n4js.typesbuilder;

import java.math.BigDecimal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.VersionedElement;
import org.eclipse.n4js.ts.types.TVersionable;

/**
 * Utility class to build versioned elements of the types model.
 */
public class VersionedTypesBuilderUtil {

	/**
	 * Sets the declared version of the given type model instance to the declared version of the given AST element in
	 * case the given AST element provides version information.
	 *
	 * An AST element is considered to provide version information if it implements {@link VersionedElement} and the
	 * {@link VersionedElement#getDeclaredVersion()} returns a non-null value. .
	 */
	/* package */ static void setTypeVersion(TVersionable versionable, EObject astElement) {
		if (astElement instanceof VersionedElement) {
			BigDecimal declaredVersion = ((VersionedElement) astElement).getDeclaredVersion();
			if (null == declaredVersion) {
				versionable.setDeclaredVersion(0);
			} else {
				versionable.setDeclaredVersion(declaredVersion.intValue());
			}
		}
	}

	/***/
	private VersionedTypesBuilderUtil() {
		// Non-instantiable utility class
	}
}
