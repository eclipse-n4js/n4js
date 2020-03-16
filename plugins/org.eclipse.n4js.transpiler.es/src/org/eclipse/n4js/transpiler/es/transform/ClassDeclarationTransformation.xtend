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
import java.util.List
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.ArrayLiteral
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
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.utils.TypeUtils

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

		// add 'n4type' getter for reflection
		reflectionAssistant.addN4TypeGetter(classDecl, classDecl);

		classConstructorAssistant.amendConstructor(classDecl, superClassSTE);

		val staticInits = createStaticFieldInitializations(classDecl, classSTE);
		insertAfter(classDecl.orContainingExportDeclaration, staticInits);

		val instanceDefs = createFieldDefinitions(classSTE, classDecl);
		insertAfter(classDecl.orContainingExportDeclaration, instanceDefs);

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
		
		val implementedInterfaces = createDirectlyImplementedInterfacesArgument(classDecl);
		if (!implementedInterfaces.elements.empty) {
			classDecl.ownedMembersRaw += _N4GetterDecl(
				_LiteralOrComputedPropertyName("$implements"),
				_Block(
					_ReturnStmnt(implementedInterfaces)
				)
			);
		}

		// change superClassRef to an equivalent extends-expression
		// (this is a minor quirk required because superClassRef is not supported by the PrettyPrinterSwitch;
		// for details see PrettyPrinterSwitch#caseN4ClassDeclaration())
		classDecl.superClassRef = null;
		classDecl.superClassExpression = __NSSafe_IdentRef(superClassSTE);
	}

	// FIXME GH-1602 only do this for fields that actually override a getter and/or setter!
	def private List<Statement> createFieldDefinitions(SymbolTableEntry steClass, N4ClassDeclaration classDecl) {
		val objectSTE = getSymbolTableEntryOriginal(state.G.objectType, true);
		val definePropertySTE = getSymbolTableEntryForMember(state.G.objectType, "defineProperty", false, true, true);
		return classDecl.ownedMembers.filter[AnnotationDefinition.OVERRIDE.hasAnnotation(it)].filter(N4FieldDeclaration).map[fieldDecl|
			// Object.defineProperty(D.prototype, "s", {writable: true});
			_ExprStmnt(
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
			) as Statement
		].toList;
	}

	def protected List<Statement> createStaticFieldInitializations(N4ClassDeclaration classDecl, SymbolTableEntry classSTE) {
		return classifierAssistant.createStaticFieldInitializations(classDecl, classSTE);
	}

	def public ArrayLiteral createDirectlyImplementedInterfacesArgument(N4ClassDeclaration classDecl) {
		val interfaces = typeAssistant.getSuperInterfacesSTEs(classDecl);

		// the return value of this method is intended for default method patching; for this purpose, we have to
		// filter out some of the directly implemented interfaces:
		val directlyImplementedInterfacesFiltered = interfaces.filter[ifcSTE|
			val tIfc = ifcSTE.originalTarget;
			if(tIfc instanceof TInterface) {
				return !TypeUtils.isBuiltIn(tIfc) // built-in types are not defined in Api/Impl projects -> no patching required
					&& !(typeAssistant.inN4JSD(tIfc) && !AnnotationDefinition.N4JS.hasAnnotation(tIfc)) // interface in .n4jsd file only patched in if marked @N4JS
			}
			return false;
		];

		return _ArrLit( directlyImplementedInterfacesFiltered.map[ _IdentRef(it) ] );
	}
}
