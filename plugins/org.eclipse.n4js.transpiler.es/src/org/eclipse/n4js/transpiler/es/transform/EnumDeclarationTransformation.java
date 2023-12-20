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
package org.eclipse.n4js.transpiler.es.transform;

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ArrLit;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._CallExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ExprStmnt;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Fpar;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._IdentRef;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4FieldDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4MethodDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._NewExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._PropertyAccessExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._StringLiteral;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._SuperLiteral;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._TypeReferenceNode;
import static org.eclipse.n4js.transpiler.utils.TranspilerUtils.orContainingExportDeclaration;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.n4EnumTypeRef;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4EnumLiteral;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.TransformationDependency.RequiresAfter;
import org.eclipse.n4js.transpiler.assistants.TypeAssistant;
import org.eclipse.n4js.transpiler.es.assistants.ReflectionAssistant;
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryIMOnly;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.N4JSLanguageUtils.EnumKind;

import com.google.inject.Inject;

/**
 * Transforms {@link N4EnumDeclaration}s into a corresponding class declaration (for ordinary enums) or removes them
 * entirely (for <code>@StringBased</code> enums).
 */
@RequiresAfter(ClassDeclarationTransformation.class)
public class EnumDeclarationTransformation extends Transformation {

	@Inject
	private ReflectionAssistant reflectionAssistant;
	@Inject
	private TypeAssistant typeAssistant;

	@Override
	public void assertPreConditions() {
		assertFalse("only top-level enums are supported, for now",
				exists(filter(getState().im.eAllContents(), N4EnumDeclaration.class),
						ed -> !typeAssistant.isTopLevel(ed)));
	}

	@Override
	public void assertPostConditions() {
		assertFalse("there should not be any N4EnumDeclarations in the intermediate model",
				exists(getState().im.eAllContents(), elem -> elem instanceof N4EnumDeclaration));
	}

	@Override
	public void analyze() {
		// nothing to do
	}

	@Override
	public void transform() {
		for (N4EnumDeclaration ed : collectNodes(getState().im, N4EnumDeclaration.class, false)) {
			transformEnumDecl(ed);
		}
	}

	private void transformEnumDecl(N4EnumDeclaration enumDecl) {
		EnumKind enumKind = N4JSLanguageUtils.getEnumKind(enumDecl);
		if (enumKind != EnumKind.Normal) {
			// declarations of number/string-based enums are simply removed
			// (they do not have a representation in the output code)
			EObject root = orContainingExportDeclaration(enumDecl);
			remove(root);
			return;
		}

		N4ClassDeclaration classDecl = N4JSFactory.eINSTANCE.createN4ClassDeclaration();
		classDecl.setName(enumDecl.getName()); // need to set name before creating a symbol table entry
		SymbolTableEntryIMOnly classSTE = createSymbolTableEntryIMOnly(classDecl);

		List<N4FieldDeclaration> fieldsForLiterals = toList(
				map(enumDecl.getLiterals(), l -> convertLiteralToField(l, classSTE)));

		classDecl.getDeclaredModifiers().addAll(enumDecl.getDeclaredModifiers()); // reuse existing modifiers
		classDecl.setSuperClassRef(_TypeReferenceNode(getState(), n4EnumTypeRef(getState().G)));

		classDecl.getOwnedMembersRaw().add(createEnumConstructor());
		classDecl.getOwnedMembersRaw().addAll(fieldsForLiterals);
		classDecl.getOwnedMembersRaw().add(_N4FieldDecl(
				true,
				"literals",
				_ArrLit(toList(map(fieldsForLiterals,
						fd -> _PropertyAccessExpr(classSTE, findSymbolTableEntryForElement(fd, true))))
								.toArray(new ParameterizedPropertyAccessExpression_IM[0]))));

		// add 'n4type' getter for reflection
		reflectionAssistant.addN4TypeGetter(enumDecl, classDecl);

		getState().tracer.copyTrace(enumDecl, classDecl);

		replace(enumDecl, classDecl);
	}

	private N4MethodDeclaration createEnumConstructor() {
		// constructor(name, value) {
		// super(name, value);
		// }
		FormalParameter nameFpar = _Fpar("name");
		FormalParameter valueFpar = _Fpar("value");
		SymbolTableEntryIMOnly nameSTE = createSymbolTableEntryIMOnly(nameFpar);
		SymbolTableEntryIMOnly valueSTE = createSymbolTableEntryIMOnly(valueFpar);
		return _N4MethodDecl(
				N4JSLanguageConstants.CONSTRUCTOR,
				new FormalParameter[] { nameFpar, valueFpar },
				new Statement[] {
						_ExprStmnt(
								_CallExpr(_SuperLiteral(), _IdentRef(nameSTE), _IdentRef(valueSTE)))
				});
	}

	private N4FieldDeclaration convertLiteralToField(N4EnumLiteral literal, SymbolTableEntry classSTE) {
		Object value = N4JSLanguageUtils.getEnumLiteralValue(literal);
		return _N4FieldDecl(
				true,
				literal.getName(),
				_NewExpr(
						_IdentRef(classSTE),
						_StringLiteral(literal.getName()),
						(value instanceof String) ? _StringLiteral((String) value) : null));
	}
}
