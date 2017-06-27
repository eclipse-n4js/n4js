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
package org.eclipse.n4js.tests.parser;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.diagnostics.IDiagnosticConsumer;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.validation.ASTStructureValidator;

/**
 * An injector provider which disables the AST structure validation to allow testing the semicolon injection with
 * minimal overhead
 */
public class N4JSInjectorProviderWithoutStructureValidation extends N4JSInjectorProvider {

	/** */
	public N4JSInjectorProviderWithoutStructureValidation() {
		super(new NullASTStructurValidatorModule());
	}

	/** Runtime module to disable the {@link ASTStructureValidator} */
	public static class NullASTStructurValidatorModule extends BaseTestModule {

		/** */
		public Class<? extends ASTStructureValidator> bindASTStructureValidator() {
			return NullASTStructureValidator.class;
		}

		/** IMPORTANT, this re-binding is required since we are not in the same bundle as our base class. */
		@Override
		public java.lang.ClassLoader bindClassLoaderToInstance() {
			return getClass().getClassLoader();
		}
	}

	/** Null implementation for {@link ASTStructureValidator} */
	private static class NullASTStructureValidator extends ASTStructureValidator {
		@Override
		public void validate(EObject model, IDiagnosticConsumer consumer) {
			// no-op
		}
	}
}
