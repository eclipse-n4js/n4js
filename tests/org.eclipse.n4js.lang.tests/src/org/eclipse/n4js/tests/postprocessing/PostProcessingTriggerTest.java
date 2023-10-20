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
package org.eclipse.n4js.tests.postprocessing;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;

import java.util.function.BiConsumer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.n4js.AbstractN4JSTest;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.utils.emf.ProxyResolvingResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.junit.Test;

import com.google.inject.Inject;

/**
 * Tests the various ways of triggering post-processing.
 */
public class PostProcessingTriggerTest extends AbstractN4JSTest {

	String snippet = """
			let msg = 'hello world';
			console.log(msg);
			""";

	@Inject
	private N4JSTypeSystem ts;

	@Test
	public void testTriggerExplicitly() throws Exception {
		assertTriggering((res, script) -> {
			res.performPostProcessing(); // trivial case of triggering post-processing
		});
	}

	@Test
	public void testTriggerViaResolveAllLazyCrossRefs() throws Exception {
		assertTriggering((res, script) -> {
			res.resolveLazyCrossReferences(null);
		});
	}

	@Test
	public void testTriggerViaTypeInference() throws Exception {
		assertTriggering((res, script) -> {
			StringLiteral stringLiteral = IteratorExtensions
					.head(IteratorExtensions.filter(script.eAllContents(), StringLiteral.class));
			RuleEnvironment G = newRuleEnvironment(res);
			ts.type(G, stringLiteral);
		});
	}

	/**
	 * Trigger post-processing via a proxy resolution for which the scoping will perform a type inference.
	 * <p>
	 * This should be trivial if {@link #testTriggerViaTypeInference()} is working properly, but still tested here for
	 * completeness.
	 */
	@Test
	public void testTriggerViaResolveProxy_withTypeInference() throws Exception {
		assertTriggering((res, script) -> {
			ParameterizedPropertyAccessExpression propAccess = IteratorExtensions.head(
					IteratorExtensions.filter(script.eAllContents(), ParameterizedPropertyAccessExpression.class));
			propAccess.getProperty();
		});
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
	public void testTriggerViaResolveProxy_withoutTypeInference() throws Exception {
		assertTriggering((res, script) -> {
			IdentifierRef identRef = IteratorExtensions
					.head(IteratorExtensions.filter(script.eAllContents(), IdentifierRef.class));
			identRef.getId();
		});
	}

	@Test
	public void ensureValidSnippet() throws Exception {
		testHelper.parseAndValidateSuccessfully(snippet);
	}

	private void assertTriggering(BiConsumer<N4JSResource, Script> trigger) {
		Script script;
		try {
			script = parserHelper.parse(snippet);
			N4JSResource res = (N4JSResource) script.eResource();

			assertFalse(res.isFullyProcessed());
			trigger.accept(res, script);
			assertTrue(res.isFullyProcessed());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
