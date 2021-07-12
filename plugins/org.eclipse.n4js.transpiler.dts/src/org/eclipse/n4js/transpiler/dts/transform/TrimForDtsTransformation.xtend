/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.dts.transform

import com.google.common.collect.Lists
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.ModifierUtils
import org.eclipse.n4js.n4JS.N4EnumLiteral
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.n4JS.VariableBinding
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableDeclarationOrBinding
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.ts.types.MemberAccessModifier
import org.eclipse.n4js.ts.types.TypeAccessModifier

/**
 * Removes everything from the IM that is not required for .d.ts export.
 */
class TrimForDtsTransformation extends Transformation {

	override assertPreConditions() {
	}

	override assertPostConditions() {
	}

	override analyze() {
		// ignore
	}

	override transform() {
		val toBeRemoved1 = Lists.newArrayList(state.im.scriptElements.filter[isPureStatement]);
		toBeRemoved1.forEach[remove(it)];

		val toBeRemoved2 = collectNodes(state.im, false, Expression, Block).filter[!isValueOfEnum(it)];
		toBeRemoved2.forEach[remove(it)];

		// remove all non-public members from interfaces
		val toBeRemoved3 = collectNodes(state.im, false, N4InterfaceDeclaration)
			.flatMap[ownedMembers]
			.filter[!static]
			.filter[!isPublicMember(it)]
			.toList;
		toBeRemoved3.forEach[remove(it)];

		// remove all destructuring patterns (and turn them into plain variable declarations)
		collectNodes(state.im, false, VariableStatement)
			.forEach[convertDestructuringToOrdinaryVariableDeclarations];
	}

	def private boolean isPureStatement(EObject obj) {
		if (obj instanceof Statement) {
			if (obj instanceof VariableStatement
					|| obj instanceof FunctionDeclaration) {
				return false;
			}
			return true;
		}
		return false;
	}

	def private boolean isValueOfEnum(EObject obj) {
		val parent = obj?.eContainer();
		return parent instanceof N4EnumLiteral
			&& obj === (parent as N4EnumLiteral).valueExpression;
	}

	// TODO GH-2153 use reusable utility method for computing actual accessibility
	def private boolean isPublicMember(N4MemberDeclaration memberDecl) {
		val declaredModifier = ModifierUtils.convertToMemberAccessModifier(memberDecl.declaredModifiers, memberDecl.allAnnotations);
		if (declaredModifier === MemberAccessModifier.UNDEFINED) {
			val parentDecl = memberDecl.eContainer;
			if (parentDecl instanceof N4InterfaceDeclaration) {
				val parentDeclModifier = ModifierUtils.convertToTypeAccessModifier(parentDecl.declaredModifiers, parentDecl.allAnnotations);
				if (parentDeclModifier === TypeAccessModifier.PUBLIC) {
					return true;
				}
			}
		}
		return declaredModifier === MemberAccessModifier.PUBLIC;
	}

	def private void convertDestructuringToOrdinaryVariableDeclarations(VariableStatement varStmnt) {
		val varDeclsToBeMoved = <VariableDeclaration>newArrayList;
		val varBindingToBeRemoved = <VariableBinding>newArrayList;
		for (root : varStmnt.varDeclsOrBindings) {
			if (root instanceof VariableDeclaration) {
				// root is a variable declaration
				// --> need not be moved
			} else if (root instanceof VariableBinding) {
				// root is a VariableBinding
				// --> all contained variable declarations must be moved to the root level
				varDeclsToBeMoved += root.allVariableDeclarations;
				varBindingToBeRemoved += root;
			} else {
				throw new UnsupportedOperationException("unsupported subclass of " + VariableDeclarationOrBinding.simpleName + ": " + root.eClass.name);
			}
		}

		// move all variable declarations in 'varDeclsToBeMoved' to the root level
		for (varDecl : varDeclsToBeMoved) {
			varStmnt.varDeclsOrBindings += varDecl; // will be automatically removed from old location by EMF
		}

		// remove all variable bindings
		for (varBinding : varBindingToBeRemoved) {
			remove(varBinding);
		}
	}
}
