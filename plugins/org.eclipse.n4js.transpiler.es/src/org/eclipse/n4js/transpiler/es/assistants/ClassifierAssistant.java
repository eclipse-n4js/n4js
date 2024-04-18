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
package org.eclipse.n4js.transpiler.es.assistants;

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._AssignmentExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._CallExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ExprStmnt;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._IdentRef;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._PropertyAccessExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._StringLiteral;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ThisLiteral;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.transpiler.TransformationAssistant;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.Iterables;

/**
 * Helper methods for transformations of class and interface declarations.
 */
public class ClassifierAssistant extends TransformationAssistant {

	/**
	 * Returns a set/list of data fields that require an explicit property definition. Actual creation of those explicit
	 * property definitions is done with method
	 * {@link #createExplicitFieldDefinitions(SymbolTableEntry, boolean, LinkedHashSet)}.
	 *
	 * <h3>Background</h3>
	 *
	 * Data fields that override an accessor require an explicit property definition along the lines of
	 *
	 * <pre>
	 * Object.defineProperty(this, "myField", {writable: true, ...});
	 * </pre>
	 *
	 * A simple initialization of the form <code>this.myField = undefined;</code> would throw an exception at runtime
	 * (in case of overriding only a getter) or would simply invoke the setter (in case of overriding a setter or an
	 * accessor pair).
	 * <p>
	 * This applies to both instance and static fields.
	 */
	public LinkedHashSet<N4FieldDeclaration> findFieldsRequiringExplicitDefinition(N4ClassDeclaration classDecl) {
		TClass tClass = getState().info.getOriginalDefinedType(classDecl);
		Set<Pair<Boolean, String>> fieldsOverridingAnAccessor = null;
		if (tClass != null) {
			fieldsOverridingAnAccessor = IterableExtensions.toSet(IterableExtensions.map(
					getState().memberCollector.computeOwnedFieldsOverridingAnAccessor(tClass, true),
					f -> Pair.of(f.isStatic(), f.getName())));
		}

		LinkedHashSet<N4FieldDeclaration> result = new LinkedHashSet<>();
		for (N4MemberDeclaration member : classDecl.getOwnedMembers()) {
			if (member instanceof N4FieldDeclaration
					&& AnnotationDefinition.OVERRIDE.hasAnnotation(member)
					&& (fieldsOverridingAnAccessor == null
							|| fieldsOverridingAnAccessor.contains(Pair.of(member.isStatic(), member.getName())))) {
				result.add((N4FieldDeclaration) member);
			}
		}

		return result;
	}

	/**
	 * Creates explicit property definitions for the fields identified by method
	 * {@link #findFieldsRequiringExplicitDefinition(N4ClassDeclaration)}.
	 */
	public List<Statement> createExplicitFieldDefinitions(SymbolTableEntry steClass, boolean staticCase,
			LinkedHashSet<N4FieldDeclaration> fieldsRequiringExplicitDefinition) {

		// Creates either
		// $defineFields(D, "staticFieldName1", "staticFieldName2", ...);
		// or
		// $defineFields(this, "instanceFieldName1", "instanceFieldName2", ...);
		List<StringLiteral> names = new ArrayList<>();
		for (N4FieldDeclaration fd : fieldsRequiringExplicitDefinition) {
			if (fd.isStatic() == staticCase) {
				names.add(_StringLiteral(fd.getName()));
			}
		}

		if (names.isEmpty()) {
			return Collections.emptyList();
		}

		List<Expression> args = new ArrayList<>();
		args.add((staticCase) ? __NSSafe_IdentRef(steClass) : _ThisLiteral());
		args.addAll(names);

		ExpressionStatement result = _ExprStmnt(
				_CallExpr(
						_IdentRef(steFor_$defineFields()),
						Iterables.toArray(args, Expression.class)));
		return List.of(result);
	}

	/**
	 * Creates a new list of statements to initialize the static fields of the given classifier declaration.
	 * <p>
	 * Clients of this method may modify the returned list.
	 */
	public List<Statement> createStaticFieldInitializations(N4ClassifierDeclaration classifierDecl,
			SymbolTableEntry classifierSTE,
			Set<N4FieldDeclaration> fieldsWithExplicitDefinition) {

		List<Statement> result = new ArrayList<>();
		for (N4MemberDeclaration member : classifierDecl.getOwnedMembers()) {
			if (member.isStatic() && member instanceof N4FieldDeclaration
					&& !(((N4FieldDeclaration) member).getExpression() == null
							&& fieldsWithExplicitDefinition.contains(member))) {

				Statement sic = createStaticInitialiserCode((N4FieldDeclaration) member, classifierSTE);
				result.add(sic);
			}
		}

		return result;
	}

	private Statement createStaticInitialiserCode(N4FieldDeclaration fieldDecl, SymbolTableEntry classSTE) {
		TField tField = (TField) getState().info.getOriginalDefinedMember(fieldDecl);
		SymbolTableEntry fieldSTE = (tField != null) ? getSymbolTableEntryOriginal(tField, true)
				: findSymbolTableEntryForElement(fieldDecl, true);

		ExpressionStatement exprStmnt = _ExprStmnt(
				_AssignmentExpr(
						_PropertyAccessExpr(__NSSafe_IdentRef(classSTE), fieldSTE),
						fieldDecl.getExpression() == null ? undefinedRef() : fieldDecl.getExpression()// reuse existing
																										// expression
																										// (if present)
				));
		getState().tracer.copyTrace(fieldDecl, exprStmnt);

		return exprStmnt;
	}
}
