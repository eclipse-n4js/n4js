/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.builder

import com.google.common.base.Optional
import com.google.inject.Inject
import java.util.concurrent.atomic.AtomicInteger
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.n4js.ide.xtext.server.build.XIndexer.XIndexResult
import org.eclipse.n4js.ide.xtext.server.build.XSource2GeneratedMapping
import org.eclipse.n4js.ide.xtext.server.build.XStatefulIncrementalBuilder
import org.eclipse.xtext.service.AbstractGenericModule
import org.eclipse.xtext.service.OperationCanceledManager
import org.junit.Test

import static org.junit.Assert.*

/**
 * Tests handling of a cancellation during an incremental build.
 */
class IncrementalBuilderCancellationTest extends AbstractIncrementalBuilderTest {

	private static final AtomicInteger cancelCounter = new AtomicInteger(0);

	private static final class CancelingStatefulIncrementalBuilder extends XStatefulIncrementalBuilder {

		@Inject
		private OperationCanceledManager operationCanceledManager;

		override protected buildClustured(Resource resource, XSource2GeneratedMapping newSource2GeneratedMapping, XIndexResult result) {
			var isCancelRequested = false;
			synchronized(cancelCounter) {
				isCancelRequested = cancelCounter.get() > 0 && cancelCounter.decrementAndGet() == 0;
			}
			if (isCancelRequested) {
				operationCanceledManager.checkCanceled([true]);
			}
			super.buildClustured(resource, newSource2GeneratedMapping, result);
		}
	}

	public static final class IncrementalBuilderCancellationTestModule extends AbstractGenericModule {

		def Class<? extends XStatefulIncrementalBuilder> bindXStatefulIncrementalBuilder() {
			return CancelingStatefulIncrementalBuilder;
		}
	}

	override protected getOverridingModule() {
		return Optional.of(IncrementalBuilderCancellationTestModule);
	}

	@Test
	def void testTEMP() {
		testWorkspaceManager.createTestProjectOnDisk(
			"A01" -> '''
				import {Cls} from "Main";
				new Cls().meth();
			''',
			"A02" -> '''
				import {Cls} from "Main";
				new Cls().meth();
			''',
			"A03" -> '''
				import {Cls} from "Main";
				new Cls().meth();
			''',
			"A04" -> '''
				import {Cls} from "Main";
				new Cls().meth();
			''',
			"A05" -> '''
				import {Cls} from "Main";
				new Cls().meth();
			''',
			"A06" -> '''
				import {Cls} from "Main";
				new Cls().meth();
			''',
			"A07" -> '''
				import {Cls} from "Main";
				new Cls().meth();
			''',
			"A08" -> '''
				import {Cls} from "Main";
				new Cls().meth();
			''',
			"Main" -> '''
				export public class Cls {
					public meth() {}
				}
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("Main");
		changeOpenedFile("Main", "meth(" -> "methx(");
		joinServerRequests();
		assertNoIssues();

		cancelCounter.set(5);
		saveOpenedFile("Main");
		joinServerRequests();
		assertEquals(0, cancelCounter.get());

		val issues = getIssues().values();
		assertEquals(3, issues.size);
		issues.map[getStringLSP4J().toStringShort(it)].forEach[
			assertEquals("(Error, [1:10 - 1:14], Couldn't resolve reference to IdentifiableElement 'meth'.)", it);
		];

		changeOpenedFile("Main", "methx(" -> "meth(");
		saveOpenedFile("Main");
		joinServerRequests();

		assertNoIssues();
	}
}
