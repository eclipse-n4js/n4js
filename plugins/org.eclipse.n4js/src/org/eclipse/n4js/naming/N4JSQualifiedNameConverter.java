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
package org.eclipse.n4js.naming;

import java.util.List;

import org.eclipse.n4js.ts.naming.N4TSQualifiedNameConverter;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.common.collect.Lists;

/**
 * Changes separator for string representations of qualified names to "/".
 */
public class N4JSQualifiedNameConverter extends N4TSQualifiedNameConverter {

	/**
	 * Delimiter used in string representations of fully-qualified names.
	 * <p>
	 * Copied from super class to avoid annoying warnings when accessing the constant via this class:
	 * "The static field N4TSQualifiedNameConverter.DELIMITER should be accessed directly".
	 */
	@SuppressWarnings("hiding")
	public static final String DELIMITER = N4TSQualifiedNameConverter.DELIMITER;

	@Override
	public QualifiedName toQualifiedName(String qualifiedNameAsString) {
		QualifiedName result = super.toQualifiedName(qualifiedNameAsString);
		if (result != null && ProjectDescriptionUtils.isProjectNameWithScope(qualifiedNameAsString)) {
			// in case of NPM scopes, merge the first two segments:
			List<String> segs = Lists.newArrayList(result.getSegments());
			segs.set(0, segs.get(0) + ProjectDescriptionUtils.NPM_SCOPE_SEPARATOR + segs.get(1));
			segs.remove(1);
			return QualifiedName.create(segs);
		}
		return result;
	}
}
