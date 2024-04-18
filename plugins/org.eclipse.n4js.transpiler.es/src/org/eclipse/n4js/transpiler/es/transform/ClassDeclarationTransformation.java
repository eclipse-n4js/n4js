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
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Block;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._IdentRef;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._LiteralOrComputedPropertyName;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4GetterDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ReturnStmnt;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.exists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassExpression;
import org.eclipse.n4js.n4JS.N4FieldAccessor;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.TransformationDependency.RequiresBefore;
import org.eclipse.n4js.transpiler.assistants.TypeAssistant;
import org.eclipse.n4js.transpiler.es.assistants.ClassConstructorAssistant;
import org.eclipse.n4js.transpiler.es.assistants.ClassifierAssistant;
import org.eclipse.n4js.transpiler.es.assistants.DelegationAssistant;
import org.eclipse.n4js.transpiler.es.assistants.ReflectionAssistant;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.transpiler.utils.TranspilerUtils;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * Transforms {@link N4ClassDeclaration}s into a constructor function and a <code>$makeClass</code> call.
 * <p>
 * Dependencies:
 * <ul>
 * <li>requiresBefore {@link MemberPatchingTransformation}: additional members must be in place before they are
 * transformed within this transformation.
 * </ul>
 */
@RequiresBefore(MemberPatchingTransformation.class)
public class ClassDeclarationTransformation extends Transformation {

	@Inject
	private ClassConstructorAssistant classConstructorAssistant;
	@Inject
	private ClassifierAssistant classifierAssistant;
	@Inject
	private ReflectionAssistant reflectionAssistant;
	@Inject
	private DelegationAssistant delegationAssistant;
	@Inject
	private TypeAssistant typeAssistant;

	@Override
	public void assertPreConditions() {
		typeAssistant.assertClassifierPreConditions();
		assertFalse("class expressions are not supported yet",
				exists(getState().im.eAllContents(), el -> el instanceof N4ClassExpression));
		assertFalse("only top-level classes are supported, for now",
				exists(collectNodes(getState().im, N4ClassDeclaration.class, false),
						cd -> !typeAssistant.isTopLevel(cd)));
	}

	@Override
	public void assertPostConditions() {
		// none
	}

	@Override
	public void analyze() {
		// ignore
	}

	@Override
	public void transform() {
		for (N4ClassDeclaration cd : collectNodes(getState().im, N4ClassDeclaration.class, false)) {
			transformClassDecl(cd);
		}
	}

	private void transformClassDecl(N4ClassDeclaration classDecl) {
		SymbolTableEntry classSTE = findSymbolTableEntryForElement(classDecl, false);
		SymbolTableEntryOriginal superClassSTE = typeAssistant.getSuperClassSTE(classDecl);
		LinkedHashSet<N4FieldDeclaration> fieldsRequiringExplicitDefinition = classifierAssistant
				.findFieldsRequiringExplicitDefinition(classDecl);

		reflectionAssistant.addN4TypeGetter(classDecl, classDecl);

		classConstructorAssistant.amendConstructor(classDecl, classSTE, superClassSTE,
				fieldsRequiringExplicitDefinition);

		List<Statement> belowClassDecl = new ArrayList<>();
		belowClassDecl.addAll(
				classifierAssistant.createExplicitFieldDefinitions(classSTE, true, fieldsRequiringExplicitDefinition));
		belowClassDecl.addAll(classifierAssistant.createStaticFieldInitializations(classDecl, classSTE,
				fieldsRequiringExplicitDefinition));
		belowClassDecl.addAll(createAdditionalClassDeclarationCode());
		insertAfter(TranspilerUtils.orContainingExportDeclaration(classDecl), belowClassDecl.toArray(new Statement[0]));

		removeFieldsAndAbstractMembers(classDecl);
		delegationAssistant.replaceDelegatingMembersByOrdinaryMembers(classDecl);
		removeTypeInformation(classDecl);

		ArrayLiteral implementedInterfaces = createDirectlyImplementedInterfaces(classDecl);
		String $implements = steFor_$implementsInterfaces().getName();
		if (!implementedInterfaces.getElements().isEmpty()) {
			N4GetterDeclaration newGetter = _N4GetterDecl(
					_LiteralOrComputedPropertyName($implements),
					_Block(
							_ReturnStmnt(implementedInterfaces)));
			newGetter.getDeclaredModifiers().add(N4Modifier.STATIC);
			classDecl.getOwnedMembersRaw().add(newGetter);
		}

		// change superClassRef to an equivalent extends-expression
		// (this is a minor quirk required because superClassRef is not supported by the PrettyPrinterSwitch;
		// for details see PrettyPrinterSwitch#caseN4ClassDeclaration())
		classDecl.setSuperClassRef(null);
		classDecl.setSuperClassExpression(__NSSafe_IdentRef(superClassSTE));
	}

	/** Removes fields and abstract members (they do not have a representation in the output code). */
	private void removeFieldsAndAbstractMembers(N4ClassDeclaration classDecl) {
		classDecl.getOwnedMembersRaw().removeIf(m -> {
			if (m instanceof N4FieldDeclaration) {
				return true;
			}
			if (m instanceof N4FieldAccessor) {
				return m.isAbstract();
			}
			if (m instanceof N4MethodDeclaration) {
				return m.isAbstract();
			}
			return false;
		});
	}

	private void removeTypeInformation(N4ClassDeclaration classDecl) {
		for (N4MemberDeclaration currMember : classDecl.getOwnedMembersRaw()) {
			if (currMember instanceof GenericDeclaration) {
				((GenericDeclaration) currMember).getTypeVars().clear();
			}
			if (currMember instanceof N4GetterDeclaration) {
				((N4GetterDeclaration) currMember).setDeclaredTypeRefNode(null);
			}
			if (currMember instanceof N4MethodDeclaration) {
				((N4MethodDeclaration) currMember).setDeclaredReturnTypeRefNode(null);
			}
		}
	}

	/** Override to add additional output code directly after the default class declaration output code. */
	private List<Statement> createAdditionalClassDeclarationCode() {
		return Collections.emptyList(); // no additional statements by default
	}

	private ArrayLiteral createDirectlyImplementedInterfaces(N4ClassDeclaration classDecl) {
		List<SymbolTableEntryOriginal> interfaces = typeAssistant.getSuperInterfacesSTEs(classDecl);

		// the return value of this method is intended for default method patching; for this purpose, we have to
		// filter out some of the directly implemented interfaces:
		Iterable<SymbolTableEntryOriginal> directlyImplementedInterfacesFiltered = TranspilerUtils
				.filterNominalInterfaces(interfaces);
		return _ArrLit(Iterables.toArray(map(directlyImplementedInterfacesFiltered, ste -> _IdentRef(ste)),
				IdentifierRef.class));
	}
}
