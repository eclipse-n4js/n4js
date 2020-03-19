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
package org.eclipse.n4js.transpiler.es.transform

import com.google.common.collect.Lists
import com.google.inject.Inject
import java.util.LinkedHashSet
import java.util.List
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.GenericDeclaration
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4ClassExpression
import org.eclipse.n4js.n4JS.N4FieldAccessor
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.TransformationDependency.RequiresBefore
import org.eclipse.n4js.transpiler.assistants.TypeAssistant
import org.eclipse.n4js.transpiler.es.assistants.ClassConstructorAssistant
import org.eclipse.n4js.transpiler.es.assistants.ClassifierAssistant
import org.eclipse.n4js.transpiler.es.assistants.DelegationAssistant
import org.eclipse.n4js.transpiler.es.assistants.ReflectionAssistant
import org.eclipse.n4js.transpiler.im.DelegatingMember
import org.eclipse.n4js.transpiler.im.SymbolTableEntry

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.transpiler.utils.TranspilerUtils.*
import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Transforms {@link N4ClassDeclaration}s into a constructor function and a <code>$makeClass</code> call.
 * <p>
 * Dependencies:
 * <ul>
 * <li>requiresBefore {@link MemberPatchingTransformation}:
 *     additional members must be in place before they are transformed within this transformation.
 * </ul>
 */
@RequiresBefore(MemberPatchingTransformation)
class ClassDeclarationTransformation extends Transformation {

	@Inject private ClassConstructorAssistant classConstructorAssistant;
	@Inject private ClassifierAssistant classifierAssistant;
	@Inject private ReflectionAssistant reflectionAssistant;
	@Inject private DelegationAssistant delegationAssistant;
	@Inject private TypeAssistant typeAssistant;

	override assertPreConditions() {
		typeAssistant.assertClassifierPreConditions();
		assertFalse("class expressions are not supported yet",
			state.im.eAllContents.exists[it instanceof N4ClassExpression]);
		assertFalse("only top-level classes are supported, for now",
			collectNodes(state.im, N4ClassDeclaration, false).exists[!typeAssistant.isTopLevel(it)]);
	}

	override assertPostConditions() {
		// none
	}

	override analyze() {
		// ignore
	}

	override transform() {
		collectNodes(state.im, N4ClassDeclaration, false).forEach[transformClassDecl];
	}

	def private void transformClassDecl(N4ClassDeclaration classDecl) {
		val classSTE = findSymbolTableEntryForElement(classDecl, false);
		val superClassSTE = typeAssistant.getSuperClassSTE(classDecl);
		val fieldsRequiringExplicitDefinition = findFieldsRequiringExplicitDefinition(classDecl);

		// add 'n4type' getter for reflection
		reflectionAssistant.addN4TypeGetter(classDecl, classDecl);

		classConstructorAssistant.amendConstructor(classDecl, superClassSTE, fieldsRequiringExplicitDefinition);

		val belowClassDecl = newArrayList;
		belowClassDecl += createExplicitFieldDefinitions(classSTE, fieldsRequiringExplicitDefinition);
		belowClassDecl += classifierAssistant.createStaticFieldInitializations(classDecl, classSTE, fieldsRequiringExplicitDefinition);
		belowClassDecl += createAdditionalClassDeclarationCode(classDecl, classSTE);
		insertAfter(classDecl.orContainingExportDeclaration, belowClassDecl);

		// remove fields and abstract members (they do not have a representation in the output code)
		classDecl.ownedMembersRaw.removeIf[
			it instanceof N4FieldDeclaration
			|| (it instanceof N4FieldAccessor && (it as N4FieldAccessor).isAbstract)
			|| (it instanceof N4MethodDeclaration && (it as N4MethodDeclaration).isAbstract)
		];

		// replace delegation members with actual members
		for(currMember : Lists.newArrayList(classDecl.ownedMembersRaw)) {
			if(currMember instanceof DelegatingMember) {
				val resolvedDelegatingMember = delegationAssistant.createOrdinaryMemberForDelegatingMember(currMember);
				replace(currMember, resolvedDelegatingMember);
			}
		}

		// remove type information
		for(currMember : classDecl.ownedMembersRaw) {
			if (currMember instanceof GenericDeclaration) {
				currMember.typeVars.clear();
			}
			switch(currMember) {
				N4GetterDeclaration:
					currMember.declaredTypeRef = null
				N4MethodDeclaration:
					currMember.returnTypeRef = null
			}
		}

		// change superClassRef to an equivalent extends-expression
		// (this is a minor quirk required because superClassRef is not supported by the PrettyPrinterSwitch;
		// for details see PrettyPrinterSwitch#caseN4ClassDeclaration())
		classDecl.superClassRef = null;
		classDecl.superClassExpression = __NSSafe_IdentRef(superClassSTE);
	}

	/**
	 * Data fields that override an accessor require an explicit property definition along the lines of
	 * <pre>
	 * Object.defineProperty(D.prototype, "myField", {writable: true});
	 * </pre>
	 * A simple initialization of the form <code>this.myField = undefined;</code> would throw an exception at runtime (in case of
	 * overriding only a getter) or would simply invoke the setter (in case of overriding a setter or an accessor pair).
	 * <p>
	 * This applies to both instance and static fields.
	 */
	def private LinkedHashSet<N4FieldDeclaration> findFieldsRequiringExplicitDefinition(N4ClassDeclaration classDecl) {
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

	def private List<Statement> createExplicitFieldDefinitions(SymbolTableEntry steClass, LinkedHashSet<N4FieldDeclaration> fieldsRequiringExplicitDefinition) {
		val objectSTE = getSymbolTableEntryOriginal(state.G.objectType, true);
		val definePropertySTE = getSymbolTableEntryForMember(state.G.objectType, "defineProperty", false, true, true);
		val result = <Statement>newArrayList;
		for (fieldDecl : fieldsRequiringExplicitDefinition) {
			// Object.defineProperty(D.prototype, "s", {writable: true});
			result += _ExprStmnt(
				_CallExpr(
					_PropertyAccessExpr(objectSTE, definePropertySTE),
					if (fieldDecl.static) {
						__NSSafe_IdentRef(steClass)
					} else {
						__NSSafe_PropertyAccessExpr(steClass, steFor_prototype)
					},
					_StringLiteral(fieldDecl.name),
					_ObjLit(
						"writable" -> _BooleanLiteral(true)
					)
				)
			);
		}
		return result;
	}

	/** Override to add additional output code directly after the default class declaration output code. */
	def protected List<Statement> createAdditionalClassDeclarationCode(N4ClassDeclaration classDecl, SymbolTableEntry classSTE) {
		return #[]; // no additional statements by default
	}
}
