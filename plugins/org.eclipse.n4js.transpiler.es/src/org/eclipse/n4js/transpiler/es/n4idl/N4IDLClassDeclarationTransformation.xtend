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
package org.eclipse.n4js.transpiler.es.n4idl

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.n4idl.N4IDLGlobals
import org.eclipse.n4js.transpiler.es.n4idl.assistants.MigrationSwitchAssistant
import org.eclipse.n4js.transpiler.es.transform.ClassDeclarationTransformation
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.ts.types.TMigratable

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

/**
 *
 */
class N4IDLClassDeclarationTransformation extends ClassDeclarationTransformation {
	
	@Inject private extension MigrationSwitchAssistant
	
	override protected createStaticInitialisers(SymbolTableEntry steClass, N4ClassDeclaration classDecl) {
		val statements = super.createStaticInitialisers(steClass, classDecl);
		
		return statements + createMigrationSupportInitialiser(steClass, classDecl);
	}
	
	private def Iterable<Statement> createMigrationSupportInitialiser(SymbolTableEntry steClass, N4ClassDeclaration classDecl) {
		val SymbolTableEntryOriginal originalSTE = if (steClass instanceof SymbolTableEntryOriginal) {
			steClass
		} else {
			state.steCache.mapOriginal.get(classDecl);
		}
		val tClass = originalSTE?.originalTarget;
		if (null === tClass) {
			// without the type model class declaration we cannot go any further 
			return #[];
		}
		
		if (!(tClass instanceof TMigratable)) {
			// only consider migratable elements
			return #[];
		}
		
		// <migration switch>
		val Expression migrationsFieldValue = makeMigrationSwitch(tClass as TMigratable);
		// A.$migrations__n4
		val staticFieldAccess =_PropertyAccessExpr(_IdentRef(steClass), getSymbolTableEntryInternal(N4IDLGlobals.MIGRATIONS_STATIC_FIELD, true))
		
		// A.$migration__n4 = function() { <migration switch> }
		return #[_ExprStmnt(_AssignmentExpr(staticFieldAccess, migrationsFieldValue))];
	}
	
}