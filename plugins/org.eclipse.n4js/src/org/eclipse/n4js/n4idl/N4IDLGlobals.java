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
	 * The hashbang prefix is used to start a hashbang at the beginning of a module or script. E.g. #!/usr/bin/env node
	 */
	public static final String HASHBANG_PREFIX = "#!";

	/**
	 * The set of annotations that enable a context to declare explicit type version requests (declare a context to be
	 * version-aware).
	 */
	public static final Set<AnnotationDefinition> VERSION_AWARENESS_ANNOTATIONS = Sets
			.newHashSet(AnnotationDefinition.MIGRATION, AnnotationDefinition.VERSION_AWARE);

	// ****************************************************************************************************
	// N4IDL transpilation related globals
	// ****************************************************************************************************

	/**
	 * The version separator that is used in compiled code to separate a type name from the declared/requested version.
	 * E.g. A#1
	 */
	public static final String COMPILED_VERSION_SEPARATOR = "$";

	/**
	 * The static field name which holds the available migrations for a given N4 classifier.
	 */
	public static final String MIGRATIONS_STATIC_FIELD = "$migrations__n4";

	/**
	 * That static field name of a class or interface constructor which holds all implemented interface of the type.
	 */
	public static final String N4_SUPER_INTERFACE_STATIC_FIELD = "$implementedInterfaces__n4";

	/**
	 * The migration call identifier in N4IDL migrations.
	 *
	 * Also the name of the function of the N4IDL contract interface {@code MigrationController} that can be used to
	 * implicitly invoke the migration of given arguments.
	 */
	public static final String MIGRATION_CALL_IDENTIFIER = "migrate";

	/**
	 * The name of the function of the N4IDL contract interface {@code MigrationController} that can be used to invoke a
	 * specified migration function using the controller.
	 */
	public static final String MIGRATION_CONTROLLER_MIGRATE_WITH_FUNCTION_NAME = "migrateWith";

	/**
	 * The name of the property of the N4IDL contract interface {@code MigrationController} that can be used to access
	 * the current {@code MigrationContext}.
	 */
	public static final String MIGRATION_CONTROLLER_CONTEXT_PROPERTY_NAME = "context";

}
