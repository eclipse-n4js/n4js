/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.xtext

import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.UncheckedExecutionException
import com.google.common.util.concurrent.Uninterruptibles
import com.google.inject.Guice
import com.google.inject.Inject
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import org.apache.log4j.Level
import org.eclipse.n4js.ide.xtext.server.concurrent.XReadRequest
import org.eclipse.n4js.ide.xtext.server.concurrent.XRequestManager
import org.eclipse.n4js.ide.xtext.server.concurrent.XWriteRequest
import org.eclipse.xtext.ide.server.ServerModule
import org.eclipse.xtext.testing.logging.LoggingTester
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

import static org.junit.Assert.*

/**
 * Ported from Xtext
 */
class XRequestManagerTest {

	@Inject
	XRequestManager requestManager

	AtomicInteger sharedState

	@Before
	def void setUp() {
		sharedState = new AtomicInteger
		Guice.createInjector(new ServerModule).injectMembers(this)
	}

	@After
	def void tearDown() {
		requestManager.shutdown
		sharedState = null
	}

	@Test(timeout = 1000)
	def void testRunWriteLogExceptionNonCancellable() {
		val logResult = LoggingTester.captureLogging(Level.ALL, XWriteRequest, [
			val future = requestManager.runWrite([], [
				throw new RuntimeException();
			])
			
			// join future to assert log later
			try {
				future.join
			} catch (Exception e) {}
		])
		
		logResult.assertLogEntry("Error during request:")
	}

	@Test(timeout = 1000)
	def void testRunWriteLogExceptionCancellable() {
		val logResult = LoggingTester.captureLogging(Level.ALL, XWriteRequest, [
			val future = requestManager.runWrite([
				throw new RuntimeException();
			], [])
			
			// join future to assert log later
			try {
				future.join
			} catch (Exception e) {}
		])
		
		logResult.assertLogEntry("Error during request:")
	}

	@Test(timeout = 1000, expected = UncheckedExecutionException)
	def void testRunWriteCatchException() {
		LoggingTester.captureLogging(Level.ALL, XWriteRequest, [
			val future = requestManager.runWrite([
				throw new RuntimeException()
			], [])

			assertEquals('Foo', Futures.getUnchecked(future))
		])
		
		Assert.fail("unreachable")
	}

	@Test(timeout = 1000)
	def void testRunReadLogException() {
		val logResult = LoggingTester.captureLogging(Level.ALL, XReadRequest, [
			val future = requestManager.runRead([
				throw new RuntimeException();
			])
			
			// join future to assert log later
			try {
				future.join
			} catch (Exception e) {}
		])
		
		logResult.assertLogEntry("Error during request:")
	}

	@Test(timeout = 1000, expected = UncheckedExecutionException)
	def void testRunReadCatchException() {
		LoggingTester.captureLogging(Level.ALL, XReadRequest, [
			val future = requestManager.runRead([
				throw new RuntimeException()
			])

			assertEquals('Foo', Futures.getUnchecked(future))
		])
		
		Assert.fail
	}

	@Test(timeout = 1000)
	def void testRunRead() {
		val future = requestManager.runRead [
			'Foo'
		]
		assertEquals('Foo', Futures.getUnchecked(future))
	}

	@Test(timeout = 1000)
	def void testRunReadConcurrent() {
		val future = requestManager.runRead [
			while (sharedState.get == 0) {
				Uninterruptibles.sleepUninterruptibly(10, TimeUnit.MILLISECONDS)
			}
			sharedState.incrementAndGet
		]
		requestManager.runRead [
			sharedState.incrementAndGet
		]
		future.join
		assertEquals(2, sharedState.get)
	}

	@Test(timeout = 1000)
	def void testRunReadAfterWrite() {
		requestManager.runWrite([], [
			sharedState.incrementAndGet
		])
		val future = requestManager.runRead [
			sharedState.get
		]
		assertEquals(1, Futures.<Integer>getUnchecked(future))
	}

	@Test(timeout = 1000)
	def void testRunWrite() {
		requestManager.runWrite([], [
			sharedState.incrementAndGet
		]).join
		assertEquals(1, sharedState.get)
	}

	@Test(timeout = 1000)
	def void testRunWriteAfterWrite() {
		requestManager.runWrite([], [
			sharedState.incrementAndGet
		]).join
		requestManager.runWrite([], [
			if (sharedState.get != 0)
				sharedState.incrementAndGet as Integer
		]).join
		assertEquals(2, sharedState.get)
	}

	//FIXME https://github.com/eclipse/xtext-core/issues/622
	@Test(timeout = 1000)
	@Ignore("https://github.com/eclipse/xtext-core/issues/622")
	def void testRunWriteAfterRead() {
		requestManager.runRead [
			sharedState.incrementAndGet
		]
		requestManager.runWrite([], [
			assertEquals (1, sharedState.get)
			sharedState.incrementAndGet
		]).join
		assertEquals(2, sharedState.get)
	}

	@Test(timeout = 1000)
	def void testCancelRead() {
		val isCanceled = new AtomicBoolean(false)
		val future = requestManager.runRead [ cancelIndicator |
			try {
				sharedState.incrementAndGet
				while (!cancelIndicator.isCanceled) {
					Uninterruptibles.sleepUninterruptibly(10, TimeUnit.MILLISECONDS)
				}
			} finally {
				isCanceled.set(true)
			}
			return null
		]
		while (sharedState.get === 0) {
			Uninterruptibles.sleepUninterruptibly(10, TimeUnit.MILLISECONDS)
		}
		future.cancel(true)
		while (!isCanceled.get) {
			Uninterruptibles.sleepUninterruptibly(10, TimeUnit.MILLISECONDS)
		}
	}
}
