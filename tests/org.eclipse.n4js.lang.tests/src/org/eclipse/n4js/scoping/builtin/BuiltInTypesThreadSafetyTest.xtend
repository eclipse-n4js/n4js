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
package org.eclipse.n4js.scoping.builtin

import com.google.common.util.concurrent.Uninterruptibles
import com.google.inject.Inject
import com.google.inject.Provider
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.N4JSStandaloneTestsModule
import org.eclipse.n4js.typesbuilder.N4JSTypesBuilder
import org.eclipse.xtext.resource.DerivedStateAwareResource
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.service.AbstractGenericModule
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 * Ensures all predefined type definitions contain no parse/validation errors. This is also checked indirectly in other tests, but this
 * test better marks the initial error.
 */
@RunWith(XtextRunner)
@InjectWith(BuiltInTypesThreadSafetyInjectorProvider)
class BuiltInTypesThreadSafetyTest {

	private static final CountDownLatch workingOnPrimitives = new CountDownLatch(1);
	private static final CountDownLatch mayContinue = new CountDownLatch(1);

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider

	@Test
	def void test() {
		assertEquals(1, workingOnPrimitives.count);
		assertEquals(1, mayContinue.count);

		val arrayType1 = new AtomicReference();
		val arrayType2 = new AtomicReference();
		val exceptionInThread2 = new AtomicReference<Throwable>();
		val requestingArrayType2 = new CountDownLatch(1);
		val done1 = new CountDownLatch(1);
		val done2 = new CountDownLatch(1);

		val thread1 = new Thread[
			val XtextResourceSet rs = resourceSetProvider.get();
			val arrType = BuiltInTypeScope.get(rs).arrayType
			arrayType1.set(arrType);
			done1.countDown();
		];
		val thread2 = new Thread[
			val XtextResourceSet rs = resourceSetProvider.get();
			requestingArrayType2.countDown();
			try {
				val arrType = BuiltInTypeScope.get(rs).arrayType;
				arrayType2.set(arrType);
			} catch(Throwable th) {
				exceptionInThread2.set(th);
			}
			done2.countDown();
		];

		thread1.start();
		workingOnPrimitives.await();

		// now thread 1 is executing method EnumerableScope#createElements() (and is blocked until we count down latch 'mayContinue')

		assertNull(arrayType1.get());

		thread2.start();
		requestingArrayType2.await();
		Uninterruptibles.sleepUninterruptibly(500, TimeUnit.MILLISECONDS); // unfortunately, no better way to ensure the following assumption

		// now thread 2 should be waiting at the "synchronized" keyword in EnumerableScope#getElements()

		assertNull(arrayType2.get());

		mayContinue.countDown();

		done1.await();
		done2.await();

		val failure = exceptionInThread2.get();
		if (failure !== null) {
			failure.printStackTrace();
			fail("exception in thread #2; check console for stack trace");
		}
		assertNotNull(arrayType1.get());
		assertNotNull(arrayType2.get());
		assertEquals("Array", arrayType1.get().name);
		assertSame(arrayType1.get(), arrayType2.get());
	}

	public static final class BuiltInTypesThreadSafetyInjectorProvider extends N4JSInjectorProvider {

		new() {
			super(new N4JSStandaloneTestsModule(), new MyTestsModule());
		}
	}

	private static final class MyTestsModule extends AbstractGenericModule {

		def public Class<? extends N4JSTypesBuilder> bindN4JSTypesBuilder() {
			return MyTypesBuilder;
		}
	}

	private static final class MyTypesBuilder extends N4JSTypesBuilder {

		override public void createTModuleFromSource(DerivedStateAwareResource resource, boolean preLinkingPhase) {
			val resourceName = resource.URI.lastSegment;
			if (resourceName == BuiltInTypeScope.PRIMITIVES_N4JSD) {
				workingOnPrimitives.countDown();
				mayContinue.await();
			}

			super.createTModuleFromSource(resource, preLinkingPhase);
		}
	}
}
