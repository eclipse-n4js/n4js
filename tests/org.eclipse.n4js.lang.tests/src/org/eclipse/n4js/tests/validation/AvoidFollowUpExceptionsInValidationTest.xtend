/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.validation

import org.eclipse.n4js.AbstractN4JSTest
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.N4JSStandaloneTestsModule
import org.eclipse.n4js.postprocessing.N4JSPostProcessor
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.resource.PostProcessingAwareResource
import org.eclipse.n4js.resource.PostProcessingAwareResource.PostProcessor
import org.eclipse.n4js.tests.issues.IssueUtils
import org.eclipse.n4js.validation.N4JSResourceValidator
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.xtext.validation.CheckMode
import org.eclipse.xtext.validation.IResourceValidator
import org.junit.Test

/**
 * This tests the early return in method {@link N4JSResourceValidator#validate(Resource, CheckMode, CancelIndicator)}.
 */
@InjectWith(N4JSInjectorProviderWithPostProcessingFailure) // NOTE the special injector provider!
class AvoidFollowUpExceptionsInValidationTest extends AbstractN4JSTest {

	@Test
	def void testSimulatedEditorUpdate() throws Exception {
		val res = '''
			class C {
				m() {
				}
			}
		'''.parse.eResource as N4JSResource;

		// the following code simulates behavior of an Xtext editor that is triggered when the source is edited by the
		// user, see method: XtextDocumentReconcileStrategy#postParse(XtextResource, IProgressMonitor)

		try {
			EcoreUtil2.resolveLazyCrossReferences(res, CancelIndicator.NullImpl); // this triggers: res.performPostProcessing()
			fail("expected exception during post-processing was not thrown by FailingN4JSPostProcessor");
		} catch(RuntimeException e) {
			assertTrue("expected FailedPostProcessingException, actual: " + e.class.simpleName,
				e instanceof FailedPostProcessingException);

			res.getCache().clear(res); // <-- Xtext clears the cache after RuntimeException

			// <-- Xtext does not re-throw the runtime exception
		}

		// Xtext always triggers a validation
		// see last finally block in method: XtextDocument.XtextDocumentLocker#modify(IUnitOfWork<T, XtextResource>)
		// which always invokes: XtextDocument#checkAndUpdateAnnotations()
		val IResourceValidator validator = (res as XtextResource).getResourceServiceProvider()
				.getResourceValidator();
		val issues = validator.validate(res, CheckMode.ALL, CancelIndicator.NullImpl); // <-- ASSERTION #1: this should not throw follow-up exceptions

		// ASSERTION #2: the exception thrown during post-processing should show up as a single error
		assertEquals("incorrect number of issues", 1, issues.size);
		val issueStr = IssueUtils.toString(issues.get(0));
		assertTrue("incorrect beginning of issue string", issueStr.startsWith(
			"ERROR:exception FailedPostProcessingException thrown during post-processing (please report this!): failure of post-processing as simulated by FailingN4JSPostProcessor\n"));
		assertTrue("incorrect end of issue string", issueStr.endsWith(
			" (__synthetic0.n4js line : 0 column : 0)"));
	}



	/** Special injector provider, to simulate an exception during post-processing. */
	public static class N4JSInjectorProviderWithPostProcessingFailure extends N4JSInjectorProvider {
		public new() {
			super(new FailingPostProcessingModule());
		}
	}

	private static class FailingPostProcessingModule extends N4JSStandaloneTestsModule {
		def public Class<? extends PostProcessor> bindPostProcessor() {
			return FailingN4JSPostProcessor;
		}
	}

	private static class FailingN4JSPostProcessor extends N4JSPostProcessor {
		override public void performPostProcessing(PostProcessingAwareResource resource, CancelIndicator cancelIndicator) {
			super.performPostProcessing(resource, cancelIndicator);
			throw new FailedPostProcessingException(); // <-- simulate exception thrown during post-processing
		}
	}

	private static class FailedPostProcessingException extends RuntimeException {
		public static final String MESSAGE = "failure of post-processing as simulated by FailingN4JSPostProcessor";
		public new() {
			super(MESSAGE);
		}
	}
}
