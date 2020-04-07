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

import java.util.LinkedHashSet
import java.util.List
import java.util.Set
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.N4ClassDeclaration
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
	 * Returns a set/list of data fields that require an explicit property definition. Actual creation of those explicit
	 * property definitions is done with method {@link #createExplicitFieldDefinitions(SymbolTableEntry, boolean, LinkedHashSet)}.
	 * 
	 * <h3>Background</h3>
	 * 
	 * Data fields that override an accessor require an explicit property definition along the lines of
	 * <pre>
	 * Object.defineProperty(this, "myField", {writable: true, ...});
	 * </pre>
	 * A simple initialization of the form <code>this.myField = undefined;</code> would throw an exception at runtime (in case of
	 * overriding only a getter) or would simply invoke the setter (in case of overriding a setter or an accessor pair).
	 * <p>
	 * This applies to both instance and static fields.
	 */
	def public LinkedHashSet<N4FieldDeclaration> findFieldsRequiringExplicitDefinition(N4ClassDeclaration classDecl) {
		val tClass = state.info.getOriginalDefinedType(classDecl);
		val fieldsOverridingAnAccessor = if (tClass !== null) {
			state.memberCollector.computeOwnedFieldsOverridingAnAccessor(tClass, true)
				.map[static -> name]
				.toSet;
		};
		val result = newLinkedHashSet;
		result += classDecl.ownedMembers
			.filter[AnnotationDefinition.OVERRIDE.hasAnnotation(it)]
			.filter(N4FieldDeclaration)
			.filter[fieldsOverridingAnAccessor === null || fieldsOverridingAnAccessor.contains(static -> name)];
		return result;
	}

	/**
	 * Creates explicit property definitions for the fields identified by method
	 * {@link #findFieldsRequiringExplicitDefinition(N4ClassDeclaration)}.
	 */
	def public List<Statement> createExplicitFieldDefinitions(SymbolTableEntry steClass, boolean staticCase,
		LinkedHashSet<N4FieldDeclaration> fieldsRequiringExplicitDefinition) {

		// Creates either
		//   $defineFields(D, "staticFieldName1", "staticFieldName2", ...);
		// or
		//   $defineFields(this, "instanceFieldName1", "instanceFieldName2", ...);
		val names = fieldsRequiringExplicitDefinition.filter[static === staticCase].map[name].map[_StringLiteral(it)].toList;
		if (names.empty) {
			return #[];
		}
		val result = _ExprStmnt(
			_CallExpr(
				_IdentRef(steFor_$defineFields),
				#[
					if (staticCase) {
						__NSSafe_IdentRef(steClass)
					} else {
						_ThisLiteral
					}
				] + names
			)
		);
		return #[ result ];
	}

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
