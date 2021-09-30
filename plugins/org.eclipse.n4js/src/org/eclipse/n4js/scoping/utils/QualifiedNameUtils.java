/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.scoping.utils;

import org.eclipse.xtext.naming.QualifiedName;

/**
 * Utilities for {@link QualifiedName}s.
 */
public class QualifiedNameUtils {

	/**
	 * Null-safe appending of segments. If segment is null, null is returned. If prefix is null, a new qualified name
	 * (with non-null segment) is created.
	 */
	public static QualifiedName append(QualifiedName prefix, String segment) {
		if (segment == null) {
			return null;
		}
		if (prefix == null) {
			return QualifiedName.create(segment);
		}
		return prefix.append(segment);
	}

	/**
	 * Prefixing a qualified name. If the prefix segment is null, the suffix is returned, else if the suffix is null a
	 * QN with only one segment is returned. Otherwise segment gets prefixed to suffix.
	 */
	public static QualifiedName prepend(String segment, QualifiedName suffix) {
		if (segment == null) {
			return suffix;
		}
		QualifiedName qn = QualifiedName.create(segment);
		if (suffix != null) {
			qn = qn.append(suffix);
		}
		return qn;
	}
}
