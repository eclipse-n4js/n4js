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
package org.eclipse.n4js.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4EnumLiteral;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.n4JS.util.N4JSSwitch;
import org.eclipse.xtext.util.IAcceptor;

/**
 * Allows to obtain all inferred elements from an AST element.
 *
 * Useful for cases where all elements at the current cursor position should be considered, e.g. in case of 'find
 * references'.
 */
public class InferredElements {

	/**
	 * Find all the elements that are directly inferred from the given ast element.
	 *
	 * @param astElement
	 *            the element in the concrete syntax
	 * @param result
	 *            acceptor for all elements
	 */
	public void collectInferredElements(EObject astElement, IAcceptor<? super EObject> result) {
		new Impl(result).doSwitch(astElement);
	}

	private static class Impl extends N4JSSwitch<Void> {
		private final IAcceptor<? super EObject> result;

		private Impl(IAcceptor<? super EObject> result) {
			this.result = result;
		}

		@Override
		public Void caseScript(Script object) {
			result.accept(object.getModule());
			return super.caseScript(object);
		}

		@Override
		public Void caseTypeDefiningElement(TypeDefiningElement object) {
			result.accept(object.getDefinedType());
			return super.caseTypeDefiningElement(object);
		}

		@Override
		public Void caseN4FieldDeclaration(N4FieldDeclaration object) {
			result.accept(object.getDefinedField());
			return super.caseN4FieldDeclaration(object);
		}

		@Override
		public Void caseN4GetterDeclaration(N4GetterDeclaration object) {
			result.accept(object.getDefinedGetter());
			return super.caseN4GetterDeclaration(object);
		}

		@Override
		public Void caseN4SetterDeclaration(N4SetterDeclaration object) {
			result.accept(object.getDefinedSetter());
			return super.caseN4SetterDeclaration(object);
		}

		@Override
		public Void caseN4EnumLiteral(N4EnumLiteral object) {
			result.accept(object.getDefinedLiteral());
			return super.caseN4EnumLiteral(object);
		}

		@Override
		public Void caseExportedVariableDeclaration(ExportedVariableDeclaration object) {
			result.accept(object.getDefinedVariable());
			return super.caseExportedVariableDeclaration(object);
		}

		@Override
		public Void caseLiteralOrComputedPropertyName(LiteralOrComputedPropertyName object) {
			if (object.eContainer() instanceof N4FieldDeclaration) {
				return caseN4FieldDeclaration((N4FieldDeclaration) object.eContainer());
			}
			return super.caseLiteralOrComputedPropertyName(object);
		}
	}
}
