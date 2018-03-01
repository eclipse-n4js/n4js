/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.typesbuilder

import java.util.List
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4idl.versioning.VersionUtils
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TMigration
import org.eclipse.n4js.ts.types.TypesFactory

/**
 * A types builder to create and initialise {@link TMigration}s from {@link FunctionDeclaration} instances. 
 */
class N4IDLMigrationTypesBuilder {
	
	/**
	 * Returns {@code true} if the given {@link FunctionDeclaration} is a 
	 * migration declaration.
	 */
	public def boolean isMigrationDeclaration(FunctionDefinition functionDef) {
		return functionDef instanceof FunctionDeclaration && 
			AnnotationDefinition.MIGRATION.hasAnnotation(functionDef);
	}
	
	/**
	 * Initialises a {@link TMigration} instance based on a {@link FunctionDeclaration}.
	 * 
	 * This method assumes that all {@link TFunction} related attributes of tMigration were already
	 * initialised appropriately.
	 * 
	 * This method also assumes that functionDecl is a migration. 
	 */
	public def void initialiseTMigration(FunctionDeclaration functionDecl, TMigration tMigration) {
		// set source and target version
		tMigration.sourceVersion = computeVersion(tMigration.sourceTypeRefs)
		tMigration.targetVersion = computeVersion(tMigration.targetTypeRefs)
	}
	
	
	/**
	 * Returns a new instance of {@link TMigration}.
	 */
	public def TMigration createTMigration() {
		return TypesFactory::eINSTANCE.createTMigration();
	}
	
	/**
	 * Computes the version of the given {@link TypeRef}s.
	 *
	 * Returns {@code 0} if none of the {@link TypeRef}s declare a version.
	 *
	 * If the given {@link TypeRef}s declared multiple different versions, this method may 
	 * yield an inaccurate result. At this point however, there is no other sensible value
	 * to populate the type model with. 
	 */
	private static def int computeVersion(List<TypeRef> typeRefs) {
		return typeRefs.stream()
			.map([ref | VersionUtils.getVersion(ref)])
			.filter[v | v != 0]
			.findFirst().orElse(0);
	}
	
}