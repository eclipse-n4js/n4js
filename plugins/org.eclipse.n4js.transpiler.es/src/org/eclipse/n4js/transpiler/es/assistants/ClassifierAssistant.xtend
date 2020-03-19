/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.es.assistants

import java.util.List
import java.util.Set
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.transpiler.TransformationAssistant
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.n4js.ts.types.TField

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

/**
 * Helper methods for transformations of class and interface declarations.
 */
class ClassifierAssistant extends TransformationAssistant {


	/**
	 * Creates a new list of statements to initialize the static fields of the given classifier declaration.
	 * <p>
	 * Clients of this method may modify the returned list.
	 */
	def public List<Statement> createStaticFieldInitializations(N4ClassifierDeclaration classifierDecl, SymbolTableEntry classifierSTE,
		Set<N4FieldDeclaration> fieldsWithExplicitDefinition) {

		return classifierDecl.ownedMembers
			.filter[isStatic]
			.filter(N4FieldDeclaration)
			.filter[!(expression===null && fieldsWithExplicitDefinition.contains(it))]
			.map[createStaticInitialiserCode(classifierSTE)]
			.filterNull
			.toList;
	}

	def private Statement createStaticInitialiserCode(N4FieldDeclaration fieldDecl, SymbolTableEntry classSTE) {
		val tField = state.info.getOriginalDefinedMember(fieldDecl) as TField;
		val fieldSTE = if (tField !== null) getSymbolTableEntryOriginal(tField, true) else findSymbolTableEntryForElement(fieldDecl, true);

		val exprStmnt = _ExprStmnt(
			_AssignmentExpr(
				_PropertyAccessExpr(__NSSafe_IdentRef(classSTE), fieldSTE),
				fieldDecl.expression ?: undefinedRef // reuse existing expression (if present)
			)
		);
		state.tracer.copyTrace(fieldDecl, exprStmnt);

		return exprStmnt;
	}
}
