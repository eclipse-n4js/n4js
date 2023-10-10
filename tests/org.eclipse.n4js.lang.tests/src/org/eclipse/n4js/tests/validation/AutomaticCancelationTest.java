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
package org.eclipse.n4js.tests.validation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.validation.validators.N4JSClassifierValidator;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CancelableDiagnostician;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Test whether a validation method runs only after a check for pending requests was made.
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)

public class AutomaticCancelationTest {

	@Inject
	ParseHelper<Script> ph;

	@Inject
	ValidationTestHelper vth;

	@Inject
	N4JSClassifierValidator v;

	@Inject
	OperationCanceledManager operationCanceledManager;

	private static final AtomicBoolean wasChecked = new AtomicBoolean(false);

	private static class TraceLeavingCancelIndicator implements CancelIndicator {

		@Override
		public boolean isCanceled() {
			wasChecked.set(true);
			// always cancel, ie don't run validation methods (that's intended).
			return true;
		}

	}

	@Test
	public void testCancelIndicatorGetsInvokedAutomatically() throws Exception {
		String program = " class X { \n\t a: any; \n\t a: any; } } ";
		Script s;
		try {

			s = ph.parse(program);

			N4ClassDeclaration clazz = (N4ClassDeclaration) s.getScriptElements().get(0);

			Map<Object, Object> context = new HashMap<>();
			context.put(CancelableDiagnostician.CANCEL_INDICATOR, new TraceLeavingCancelIndicator());

			// the cancel indicator in use sets a flag in case its isCanceled() was queried
			assertTrue(!(wasChecked.get()));
			DiagnosticChain chain = new BasicDiagnostic();
			try {
				v.validate(clazz, chain, context);
				fail("expected OperationCanceledException or OperationCanceledError was not thrown");
			} catch (Throwable th) {
				assertTrue(
						"wrong kind of throwable; expected: OperationCanceledException or OperationCanceledError, actual: "
								+ th.getClass().getSimpleName(),
						operationCanceledManager.isOperationCanceledException(th));
			}
			assertTrue(wasChecked.get());

			// now validate with a cancel indicator that never cancels,
			// upon which validation methods run and errors are recorded.
			context.put(CancelableDiagnostician.CANCEL_INDICATOR, CancelIndicator.NullImpl);
			final boolean isValid02 = v.validate(clazz, chain, context);
			assertTrue(!isValid02);

			// validate again (this time with the default cancel indicator, which never cancels),
			// check expected errors one by one.
			String[] expectedErrorMsgs = {
					"The field a (line 2) duplicates field a (line 3).",
					"The field a (line 3) duplicates field a (line 2)."
			};
			for (String expectedErrorMsg : expectedErrorMsgs) {
				vth.assertError(s, N4JSPackage.Literals.N4_MEMBER_DECLARATION, IssueCodes.CLF_DUP_MEMBER,
						expectedErrorMsg);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
