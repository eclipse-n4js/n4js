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
import java.util.concurrent.CancellationException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import org.apache.log4j.Level
import org.eclipse.n4js.ide.xtext.server.concurrent.XAbstractRequest
import org.eclipse.n4js.ide.xtext.server.concurrent.XRequestManager
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
		val logResult = LoggingTester.captureLogging(Level.ALL, XAbstractRequest, [
			val future = requestManager.runWrite("test", [], [
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
		val logResult = LoggingTester.captureLogging(Level.ALL, XAbstractRequest, [
			val future = requestManager.runWrite("test", [
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
		LoggingTester.captureLogging(Level.ALL, XAbstractRequest, [
			val future = requestManager.runWrite("test", [
				throw new RuntimeException()
			], [])

			assertEquals('Foo', Futures.getUnchecked(future))
		])
		
		Assert.fail("unreachable")
	}

	@Test(timeout = 1000)
	def void testRunReadLogException() {
		val logResult = LoggingTester.captureLogging(Level.ALL, XAbstractRequest, [
			val future = requestManager.runRead("test", [
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
		LoggingTester.captureLogging(Level.ALL, XAbstractRequest, [
			val future = requestManager.runRead("test", [
				throw new RuntimeException()
			])

			assertEquals('Foo', Futures.getUnchecked(future))
		])
		
		Assert.fail
	}

	@Test(timeout = 1000)
	def void testRunRead() {
		val future = requestManager.runRead("test") [
			'Foo'
		]
		assertEquals('Foo', Futures.getUnchecked(future))
	}

	@Ignore
	@Test(timeout = 1000)
	def void testRunReadConcurrent() {
		val future = requestManager.runRead("test") [
			while (sharedState.get == 0) {
				Uninterruptibles.sleepUninterruptibly(10, TimeUnit.MILLISECONDS)
			}
			sharedState.incrementAndGet
		]
		requestManager.runRead("test") [
			sharedState.incrementAndGet
		]
		future.join
		assertEquals(2, sharedState.get)
	}

	// TODO when re-enabling concurrent execution of read-requests:
	// delete the following test case and re-enable test #testRunReadConcurrent():
	@Test(timeout = 2000)
	def void testRunReadNotConcurrent() {
		// as above in method #testRunReadConcurrent():
		val future = requestManager.runRead("test") [
			while (sharedState.get == 0) {
				Uninterruptibles.sleepUninterruptibly(10, TimeUnit.MILLISECONDS)
			}
			sharedState.incrementAndGet
		]
		requestManager.runRead("test") [
			sharedState.incrementAndGet
		]
		// but now we expect a timeout:
		try {
			Uninterruptibles.getUninterruptibly(future, 1000, TimeUnit.MILLISECONDS)
			fail("expected timeout")
		} catch (ExecutionException exc) {
			fail("incorrect exception")
		} catch(TimeoutException e) {
			assertEquals(0, sharedState.get)
			sharedState.incrementAndGet
			requestManager.allRequests.join
		}
	}

	@Test(timeout = 1000)
	def void testRunReadAfterWrite() {
		requestManager.runWrite("test", [], [
			sharedState.incrementAndGet
		])
		val future = requestManager.runRead("test") [
			sharedState.get
		]
		assertEquals(1, Futures.<Integer>getUnchecked(future))
	}

	@Test(timeout = 1000)
	def void testRunWrite() {
		requestManager.runWrite("test", [], [
			sharedState.incrementAndGet
		]).join
		assertEquals(1, sharedState.get)
	}

	@Test(timeout = 1000)
	def void testRunWriteAfterWrite() {
		requestManager.runWrite("test", [], [
			sharedState.incrementAndGet
		]).join
		requestManager.runWrite("test", [], [
			if (sharedState.get != 0)
				sharedState.incrementAndGet as Integer
		]).join
		assertEquals(2, sharedState.get)
	}

	@Test(timeout = 1000)
	def void testRunWriteAfterReadStarted() {
		val readStarted = new CountDownLatch(1)
		requestManager.runRead("read") [
			readStarted.countDown
			sharedState.incrementAndGet
		]
		Uninterruptibles.awaitUninterruptibly(readStarted)
		requestManager.runWrite("write", [], [
			assertEquals (1, sharedState.get)
			sharedState.incrementAndGet
		]).join
		assertEquals(2, sharedState.get)
	}
	
	@Test(timeout = 1000)
	def void testRunWriteBeforeReadStarted() {
		val writeSubmitted = new CountDownLatch(1)
		val firstWriteDone = new AtomicBoolean
		requestManager.runWrite("write", [
			Uninterruptibles.awaitUninterruptibly(writeSubmitted)
			firstWriteDone.set(true)
			null
		], [
			sharedState.incrementAndGet
		])
		requestManager.runRead("read") [
			sharedState.incrementAndGet
		]
		val joinMe = requestManager.runWrite("write-again", [], [
			assertEquals (0, sharedState.get)
			assertTrue(firstWriteDone.get)
			sharedState.incrementAndGet
		])
		writeSubmitted.countDown
		joinMe.join
		assertTrue(firstWriteDone.get)
		assertEquals(1, sharedState.get)
	}

	@Test(timeout = 1000)
	def void testCancelRead() {
		val isCanceled = new AtomicBoolean(false)
		val future = requestManager.runRead("test") [ cancelIndicator |
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
	
	@Test
	def void testWriteWaitsForReadToFinish() throws Exception {
		val marked = new AtomicBoolean(false)
		val countDownInRead = new CountDownLatch(1)
		val countDownInWrite = new CountDownLatch(1)
		val proceedWithWrite = new CountDownLatch(1)
		val reader = requestManager.runRead("reader") [ cancelIndicator |
			countDownInRead.countDown
			Uninterruptibles.awaitUninterruptibly(countDownInWrite)
			marked.set(true)
			proceedWithWrite.countDown
			return null
		]
		Uninterruptibles.awaitUninterruptibly(countDownInRead)
		val writer = requestManager.runWrite("writer", [], [ cancelIndicator, ignored |
			countDownInWrite.countDown
			Uninterruptibles.awaitUninterruptibly(proceedWithWrite)
			assertTrue(marked.get)
			null
		])
		try {
			Uninterruptibles.getUninterruptibly(writer, 100, TimeUnit.MILLISECONDS)
			fail("Expected timeout")
		} catch(TimeoutException e) {
			assertFalse(marked.get()) // this should not be the case
		}
		countDownInWrite.countDown;
		Uninterruptibles.getUninterruptibly(writer, 100, TimeUnit.MILLISECONDS)
		assertTrue(reader.isDone)
		assertFalse(reader.isCancelled)
	}
	
	@Test
	def void testWriteWaitsForReadToFinish_02() throws Exception {
		val marked = new AtomicBoolean(false)
		val countDownInRead = new CountDownLatch(1)
		val countDownInWrite = new CountDownLatch(1)
		val proceedWithWrite = new CountDownLatch(1)
		val reader = requestManager.runRead("reader") [ cancelIndicator |
			countDownInRead.countDown
			Uninterruptibles.awaitUninterruptibly(countDownInWrite)
			marked.set(true)
			proceedWithWrite.countDown
			throw new CancellationException
		]
		Uninterruptibles.awaitUninterruptibly(countDownInRead)
		val writer = requestManager.runWrite("writer", [], [ cancelIndicator, ignored |
			countDownInWrite.countDown
			Uninterruptibles.awaitUninterruptibly(proceedWithWrite)
			assertTrue(marked.get)
			null
		])
		try {
			Uninterruptibles.getUninterruptibly(writer, 100, TimeUnit.MILLISECONDS)
			fail("Expected timeout")
		} catch(TimeoutException e) {
			assertFalse(marked.get()) // this should not be the case
		}
		countDownInWrite.countDown;
		Uninterruptibles.getUninterruptibly(writer, 100, TimeUnit.MILLISECONDS)
		assertTrue(reader.isDone)
		assertTrue(reader.isCancelled)
	}
	
}
