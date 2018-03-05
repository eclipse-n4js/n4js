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
package org.eclipse.n4js.n4idl;

import java.util.Set;

import org.eclipse.n4js.AnnotationDefinition;

import com.google.common.collect.Sets;

/**
 * Contains constants for the N4IDL language.
 */
public class N4IDLGlobals {

	/**
	 * Files extension of N4IDL source files (<b>not</b> including the separator dot).
	 */
	public static final String N4IDL_FILE_EXTENSION = "n4idl";

	/**
	 * The version separator is used to separate a type name from the declared/requested version. E.g. A#1
	 */
	public static final String VERSION_SEPARATOR = "#";

	/**
	 * The version separator that is used in compiled code to separate a type name from the declared/requested version.
	 * E.g. A#1
	 */
	public static final String COMPILED_VERSION_SEPARATOR = "$";

	/**
	 * The set of annotations that enable a context to declare explicit type version requests (declare a context to be
	 * version-aware).
	 */
	public static final Set<AnnotationDefinition> VERSION_AWARENESS_ANNOTATIONS = Sets
			.newHashSet(AnnotationDefinition.MIGRATION, AnnotationDefinition.VERSION_AWARE);
}
