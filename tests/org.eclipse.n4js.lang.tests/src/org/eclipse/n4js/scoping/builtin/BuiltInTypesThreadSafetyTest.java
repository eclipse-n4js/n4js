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
package org.eclipse.n4js.scoping.builtin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.N4JSStandaloneTestsModule;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.typesbuilder.N4JSTypesBuilder;
import org.eclipse.xtext.resource.DerivedStateAwareResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.service.AbstractGenericModule;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.util.concurrent.Uninterruptibles;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Ensures all predefined type definitions contain no parse/validation errors. This is also checked indirectly in other
 * tests, but this test better marks the initial error.
 */
@RunWith(XtextRunner.class)
@InjectWith(BuiltInTypesThreadSafetyTest.BuiltInTypesThreadSafetyInjectorProvider.class)
public class BuiltInTypesThreadSafetyTest {

	private static final CountDownLatch workingOnPrimitives = new CountDownLatch(1);
	private static final CountDownLatch mayContinue = new CountDownLatch(1);

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@Test
	public void test() throws InterruptedException {
		assertEquals(1, workingOnPrimitives.getCount());
		assertEquals(1, mayContinue.getCount());

		AtomicReference<TClass> arrayType1 = new AtomicReference<>();
		AtomicReference<TClass> arrayType2 = new AtomicReference<>();
		AtomicReference<Throwable> exceptionInThread2 = new AtomicReference<>();
		CountDownLatch requestingArrayType2 = new CountDownLatch(1);
		CountDownLatch done1 = new CountDownLatch(1);
		CountDownLatch done2 = new CountDownLatch(1);

		Thread thread1 = new Thread(() -> {
			XtextResourceSet rs = resourceSetProvider.get();
			TClass arrType = BuiltInTypeScope.get(rs).getArrayType();
			arrayType1.set(arrType);
			done1.countDown();
		});
		Thread thread2 = new Thread(() -> {
			XtextResourceSet rs = resourceSetProvider.get();
			requestingArrayType2.countDown();
			try {
				TClass arrType = BuiltInTypeScope.get(rs).getArrayType();
				arrayType2.set(arrType);
			} catch (Throwable th) {
				exceptionInThread2.set(th);
			}
			done2.countDown();
		});

		thread1.start();
		workingOnPrimitives.await();

		// now thread 1 is executing method EnumerableScope#createElements() (and is blocked until we count down latch
		// 'mayContinue')

		assertNull(arrayType1.get());

		thread2.start();
		requestingArrayType2.await();
		// unfortunately, no better way to ensure the following assumption
		Uninterruptibles.sleepUninterruptibly(500, TimeUnit.MILLISECONDS);

		// now thread 2 should be waiting at the "synchronized" keyword in EnumerableScope#getElements()

		assertNull(arrayType2.get());

		mayContinue.countDown();

		done1.await();
		done2.await();

		Throwable failure = exceptionInThread2.get();
		if (failure != null) {
			failure.printStackTrace();
			fail("exception in thread #2; check console for stack trace");
		}
		assertNotNull(arrayType1.get());
		assertNotNull(arrayType2.get());
		assertEquals("Array", arrayType1.get().getName());
		assertSame(arrayType1.get(), arrayType2.get());
	}

	public static final class BuiltInTypesThreadSafetyInjectorProvider extends N4JSInjectorProvider {

		public BuiltInTypesThreadSafetyInjectorProvider() {
			super(new N4JSStandaloneTestsModule(), new MyTestsModule());
		}
	}

	private static final class MyTestsModule extends AbstractGenericModule {

		@SuppressWarnings("unused")
		public Class<? extends N4JSTypesBuilder> bindN4JSTypesBuilder() {
			return MyTypesBuilder.class;
		}
	}

	private static final class MyTypesBuilder extends N4JSTypesBuilder {

		@Override
		public void createTModuleFromSource(DerivedStateAwareResource resource, boolean preLinkingPhase) {
			String resourceName = resource.getURI().lastSegment();
			if (resourceName == BuiltInTypeScope.PRIMITIVES_N4JSD) {
				workingOnPrimitives.countDown();
				try {
					mayContinue.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			super.createTModuleFromSource(resource, preLinkingPhase);
		}
	}
}
