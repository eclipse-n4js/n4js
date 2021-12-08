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
package org.eclipse.n4js.tests.contentassist;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.XtextRuntimeModule;
import org.eclipse.xtext.formatting2.IFormatter2;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.impl.Serializer;
import org.eclipse.xtext.serializer.tokens.CrossReferenceSerializer;
import org.eclipse.xtext.service.CompoundModule;
import org.eclipse.xtext.xbase.lib.Functions.Function1;

import com.google.inject.Binder;
import com.google.inject.Guice;

/**
 * Useful in tests that need to visualize an Xtext grammar element.
 */
@SuppressWarnings("restriction")
public class GrammarPrettyPrinter implements Function1<AbstractElement, String> {

	private final Serializer serializer = Guice.createInjector(new XtextRuntimeModule() {

		@Override
		public void configure(Binder binder) {
			// Avoid bindings that change the global registry
			CompoundModule compound = getBindings();
			compound.configure(binder);
		}

		@SuppressWarnings("unused")
		public Class<? extends CrossReferenceSerializer> bindCrossReferenceSerializer() {
			return MyCrossReferenceSerializer.class;
		}

		@Override
		public Class<? extends IFormatter2> bindIFormatter2() {
			// unbind the XtextFormatterJava
			return null;
		}

	}).getInstance(Serializer.class);

	/**
	 * A custom reference serializer that knows how to print certain referable elements, e.g. rules and Ecore
	 * {@link ENamedElement named elements}.
	 */
	static class MyCrossReferenceSerializer extends CrossReferenceSerializer {

		@Override
		protected String getCrossReferenceNameFromScope(EObject semanticObject, CrossReference crossref,
				EObject target, IScope scope, Acceptor errors) {
			if (target instanceof AbstractRule) {
				return ((AbstractRule) target).getName();
			}
			if (target instanceof ENamedElement) {
				return ((ENamedElement) target).getName();
			}
			return super.getCrossReferenceNameFromScope(semanticObject, crossref, target, scope, errors);
		}
	}

	@Override
	public String apply(AbstractElement p) {
		return serializer.serialize(p);
	}

}
