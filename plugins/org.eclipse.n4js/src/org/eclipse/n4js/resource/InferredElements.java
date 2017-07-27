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

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4EnumLiteral;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.n4JS.util.N4JSSwitch;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.util.NameStaticPair;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.ContainerTypesHelper.MemberCollector;
import org.eclipse.n4js.validation.validators.utils.MemberCube;
import org.eclipse.n4js.validation.validators.utils.MemberMatrix;
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
	public void collectInferredElements(EObject astElement, IAcceptor<? super EObject> result,
			ContainerTypesHelper containerTypesHelper) {
		new Impl(result, containerTypesHelper).doSwitch(astElement);
	}

	private static class Impl extends N4JSSwitch<Void> {
		private final IAcceptor<? super EObject> result;
		private final ContainerTypesHelper containerTypesHelper;

		private Impl(IAcceptor<? super EObject> result, ContainerTypesHelper containerTypesHelper) {
			this.result = result;
			this.containerTypesHelper = containerTypesHelper;
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
			// Accept all inherited and implemented fields as well
			TField tfield = object.getDefinedField();
			for (TMember member : getInheritedAndImplementedMembers(tfield)) {
				result.accept(member);
			}

			result.accept(object.getDefinedField());
			return super.caseN4FieldDeclaration(object);
		}

		@Override
		public Void caseN4GetterDeclaration(N4GetterDeclaration object) {
			// Accept all inherited and implemented getters as well
			TGetter tgetter = object.getDefinedGetter();
			for (TMember member : getInheritedAndImplementedMembers(tgetter)) {
				result.accept(member);
			}
			result.accept(object.getDefinedGetter());
			return super.caseN4GetterDeclaration(object);
		}

		@Override
		public Void caseN4SetterDeclaration(N4SetterDeclaration object) {
			// Accept all inherited and implemented getters as well
			TSetter tsetter = object.getDefinedSetter();
			for (TMember member : getInheritedAndImplementedMembers(tsetter)) {
				result.accept(member);
			}

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
			// LiteralOrComputedProperty does not have a type representation, that's add its parent GH-73
			result.accept(object.eContainer());
			return doSwitch(object.eContainer());
		}

		@Override
		public Void caseN4MethodDeclaration(N4MethodDeclaration object) {
			// Accept all inherited and implemented methods as well
			TMethod tmethod = (TMethod) object.getDefinedType();
			for (TMember member : getInheritedAndImplementedMembers(tmethod)) {
				result.accept(member);
			}

			return super.caseN4MethodDeclaration(object);
		}

		private MemberMatrix getMemberMatrix(TMember member) {
			TClassifier tclassifier = (TClassifier) member.eContainer();
			MemberCollector memberCollector = containerTypesHelper.fromContext(tclassifier);
			MemberCube memberCube = new MemberCube(tclassifier, memberCollector);
			NameStaticPair nsp = NameStaticPair.of(member);
			Optional<Entry<NameStaticPair, MemberMatrix>> ret = memberCube.entrySet().stream()
					.filter(entry -> entry.getKey().equals(nsp)).findFirst();
			if (ret.isPresent()) {
				Entry<NameStaticPair, MemberMatrix> mmEntry = ret.get();
				return mmEntry.getValue();
			}
			return null;
		}

		private List<TMember> getInheritedAndImplementedMembers(TMember tmember) {
			List<TMember> ret = new ArrayList<>();
			MemberMatrix mm = getMemberMatrix(tmember);
			if (mm != null) {
				for (TMember member : mm.inherited()) {
					ret.add(member);
				}
				for (TMember member : mm.implemented()) {
					ret.add(member);
				}
			}
			return ret;
		}
	}
}
