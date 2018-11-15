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
package org.eclipse.n4js.tests.postprocessing

import com.google.inject.Inject
import java.util.function.BiConsumer
import org.eclipse.n4js.AbstractN4JSTest
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.StringLiteral
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.utils.emf.ProxyResolvingResource
import org.junit.Test

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Tests the various ways of triggering post-processing.
 */
class PostProcessingTriggerTest extends AbstractN4JSTest {

	val snippet = '''
		let msg = 'hello world';
		console.log(msg);
	''';

	@Inject
	private N4JSTypeSystem ts;


	@Test
	def void testTriggerExplicitly() throws Exception {
		assertTriggering[res, script|
			res.performPostProcessing(); // trivial case of triggering post-processing
		];
	}

	@Test
	def void testTriggerViaResolveAllLazyCrossRefs() throws Exception {
		assertTriggering[res, script|
			res.resolveLazyCrossReferences(null);
		];
	}

	@Test
	def void testTriggerViaTypeInference() throws Exception {
		assertTriggering[res, script|
			val stringLiteral = script.eAllContents.filter(StringLiteral).head;
			val G = res.newRuleEnvironment;
			ts.type(G, stringLiteral);
		];
	}

	/**
	 * Trigger post-processing via a proxy resolution for which the scoping will perform a type inference.
	 * <p>
	 * This should be trivial if {@link #testTriggerViaTypeInference()} is working properly, but still tested here for
	 * completeness.
	 */
	@Test
	def void testTriggerViaResolveProxy_withTypeInference() throws Exception {
		assertTriggering[res, script|
			val propAccess = script.eAllContents.filter(ParameterizedPropertyAccessExpression).head;
			propAccess.property;
		];
	}

	/**
	 * Trigger post-processing via a proxy resolution for which the scoping will *NOT* perform a type inference.
	 * <p>
	 * This is the most important test case in this file. This has been the most difficult case to support, prior to the
	 * introduction of {@link ProxyResolvingResource}. Now, this can be achieved easily with a call to
	 * {@link N4JSResource#performPostProcessing(CancelIndicator)} at the beginning of
	 * {@link N4JSResource#doResolveProxy(InternalEObject, EObject)}.
	 */
	@Test
	def void testTriggerViaResolveProxy_withoutTypeInference() throws Exception {
		assertTriggering[res, script|
			val identRef = script.eAllContents.filter(IdentifierRef).head;
			identRef.id;
		];
	}


	@Test
	def void ensureValidSnippet() {
		snippet.parseAndValidateSuccessfully;
	}


	def private void assertTriggering(BiConsumer<N4JSResource, Script> trigger) {
		val script = snippet.parse;
		val res = script.eResource as N4JSResource;

		assertFalse(res.isFullyProcessed);
		trigger.accept(res, script);
		assertTrue(res.isFullyProcessed);
	}
}
