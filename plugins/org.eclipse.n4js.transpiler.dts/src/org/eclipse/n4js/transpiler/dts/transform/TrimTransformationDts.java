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
package org.eclipse.n4js.transpiler.dts.transform;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.ModifierUtils;
import org.eclipse.n4js.n4JS.N4EnumLiteral;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableDeclarationOrBinding;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.TypeAccessModifier;

/**
 * Removes everything from the IM that is not required for .d.ts export.
 */
public class TrimTransformationDts extends Transformation {

	@Override
	public void assertPreConditions() {
		// empty
	}

	@Override
	public void assertPostConditions() {
		// empty
	}

	@Override
	public void analyze() {
		// ignore
	}

	@Override
	public void transform() {
		for (ScriptElement se : getState().im.getScriptElements()) {
			if (isPureStatement(se)) {
				remove(se);
			}
		}

		for (ControlFlowElement se : collectNodes(getState().im, false, Expression.class, Block.class)) {
			if (!isValueOfEnum(se)) {
				remove(se);
			}
		}

		// remove all non-public members from interfaces
		for (N4InterfaceDeclaration intfDecl : collectNodes(getState().im, false, N4InterfaceDeclaration.class)) {
			intfDecl.getOwnedMembers();
			for (N4MemberDeclaration member : intfDecl.getOwnedMembers()) {
				if (!member.isStatic() && !isPublicMember(member)) {
					remove(member);
				}
			}
		}

		// remove all destructuring patterns (and turn them into plain variable declarations)
		for (VariableStatement vs : collectNodes(getState().im, false, VariableStatement.class)) {
			convertDestructuringToOrdinaryVariableDeclarations(vs);
		}
	}

	private boolean isPureStatement(EObject obj) {
		if (obj instanceof Statement) {
			if (obj instanceof VariableStatement
					|| obj instanceof FunctionDeclaration) {
				return false;
			}
			return true;
		}
		return false;
	}

	private boolean isValueOfEnum(EObject obj) {
		EObject parent = obj == null ? null : obj.eContainer();
		return parent instanceof N4EnumLiteral
				&& obj == ((N4EnumLiteral) parent).getValueExpression();
	}

	// TODO GH-2153 use reusable utility method for computing actual accessibility
	private boolean isPublicMember(N4MemberDeclaration memberDecl) {
		MemberAccessModifier declaredModifier = ModifierUtils
				.convertToMemberAccessModifier(memberDecl.getDeclaredModifiers(), memberDecl.getAllAnnotations());
		if (declaredModifier == MemberAccessModifier.UNDEFINED) {
			EObject parentDecl = memberDecl.eContainer();
			if (parentDecl instanceof N4InterfaceDeclaration) {
				N4InterfaceDeclaration pInterfDecl = (N4InterfaceDeclaration) parentDecl;
				TypeAccessModifier parentDeclModifier = ModifierUtils.convertToTypeAccessModifier(
						pInterfDecl.getDeclaredModifiers(), pInterfDecl.getAllAnnotations());
				if (parentDeclModifier == TypeAccessModifier.PUBLIC) {
					return true;
				}
			}
		}
		return declaredModifier == MemberAccessModifier.PUBLIC;
	}

	private void convertDestructuringToOrdinaryVariableDeclarations(VariableStatement varStmnt) {
		List<VariableDeclaration> varDeclsToBeMoved = new ArrayList<>();
		List<VariableBinding> varBindingToBeRemoved = new ArrayList<>();
		for (VariableDeclarationOrBinding root : varStmnt.getVarDeclsOrBindings()) {
			if (root instanceof VariableDeclaration) {
				// root is a variable declaration
				// --> need not be moved
			} else if (root instanceof VariableBinding) {
				// root is a VariableBinding
				// --> all contained variable declarations must be moved to the root level
				varDeclsToBeMoved.addAll(root.getAllVariableDeclarations());
				varBindingToBeRemoved.add((VariableBinding) root);
			} else {
				throw new UnsupportedOperationException("unsupported subclass of "
						+ VariableDeclarationOrBinding.class.getSimpleName() + ": " + root.eClass().getName());
			}
		}

		// move all variable declarations in 'varDeclsToBeMoved' to the root level
		for (VariableDeclaration varDecl : varDeclsToBeMoved) {
			varStmnt.getVarDeclsOrBindings().add(varDecl); // will be automatically removed from old location by EMF
		}

		// remove all variable bindings
		for (VariableBinding varBinding : varBindingToBeRemoved) {
			remove(varBinding);
		}
	}
}
