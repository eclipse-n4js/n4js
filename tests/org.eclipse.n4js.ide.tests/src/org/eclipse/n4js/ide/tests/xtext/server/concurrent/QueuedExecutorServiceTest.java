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
package org.eclipse.n4js.ide.tests.xtext.server.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Level;
import org.eclipse.n4js.xtext.ide.server.QueuedExecutorService;
import org.eclipse.n4js.xtext.ide.server.QueuedExecutorService.QueuedTaskFuture;
import org.eclipse.xtext.ide.server.ServerModule;
import org.eclipse.xtext.testing.logging.LoggingTester;
import org.eclipse.xtext.testing.logging.LoggingTester.LogCapture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.inject.Guice;
import com.google.inject.Inject;

/**
 * Ported from Xtext's RequestManager tests.
 */

public class QueuedExecutorServiceTest {

	@Inject
	private QueuedExecutorService queuedExecutorService;

	private AtomicInteger sharedState;

	@Before
	public void setUp() {
		sharedState = new AtomicInteger();
		Guice.createInjector(new ServerModule()).injectMembers(this);
	}

	@After
	public void tearDown() {
		queuedExecutorService.shutdown();
		sharedState = null;
	}

	@Test(timeout = 1000)
	public void testSubmitLogException() {
		LogCapture logResult = LoggingTester.captureLogging(Level.ALL, QueuedExecutorService.class, () -> {
			QueuedTaskFuture<?> future = queuedExecutorService.submit("test", (ci) -> {
				throw new RuntimeException();
			});
			try {
				future.join();
			} catch (Exception e) {
				// empty
			}
		});
		logResult.assertLogEntry("error during queued task:");
	}

	@Test(timeout = 1000, expected = UncheckedExecutionException.class)
	public void testSubmitCatchException() {
		LoggingTester.captureLogging(Level.ALL, QueuedExecutorService.class, () -> {
			QueuedTaskFuture<?> future = queuedExecutorService.submit("test", (ci) -> {
				throw new RuntimeException();
			});

			assertEquals("Foo", Futures.getUnchecked(future));
		});

		fail("unreachable");
	}

	@Test(timeout = 1000)
	public void testSubmit() {
		QueuedTaskFuture<?> future = queuedExecutorService.submit("test", (ci) -> {
			return "Foo";
		});
		assertEquals("Foo", Futures.getUnchecked(future));
	}

	@Test(timeout = 1000)
	public void testSubmitConcurrent01() {
		CountDownLatch firstStarted = new CountDownLatch(1);
		QueuedTaskFuture<?> future = queuedExecutorService.submit("id1", "first", (ci) -> {
			firstStarted.countDown();
			while (sharedState.get() == 0) {
				Uninterruptibles.sleepUninterruptibly(10, TimeUnit.MILLISECONDS);
			}
			return sharedState.incrementAndGet();
		});
		queuedExecutorService.submit("id2", "second", (ci) -> {
			Uninterruptibles.awaitUninterruptibly(firstStarted);
			return sharedState.incrementAndGet();
		});
		future.join();
		assertEquals(2, sharedState.get());
	}

	@Test(timeout = 1000)
	public void testSubmitConcurrent02() {
		CountDownLatch firstStarted = new CountDownLatch(1);
		QueuedTaskFuture<?> future = queuedExecutorService.submit("first", (ci) -> {
			firstStarted.countDown();
			while (sharedState.get() == 0) {
				Uninterruptibles.sleepUninterruptibly(10, TimeUnit.MILLISECONDS);
			}
			return sharedState.incrementAndGet();
		});
		queuedExecutorService.submit("second", (ci) -> {
			Uninterruptibles.awaitUninterruptibly(firstStarted);
			return sharedState.incrementAndGet();
		});
		future.join();
		assertEquals(2, sharedState.get());
	}

	@Test(timeout = 1000)
	public void testSubmitSequential01() {
		queuedExecutorService.submit("id", "first", (ci) -> {
			return sharedState.incrementAndGet();
		});
		QueuedTaskFuture<Integer> future = queuedExecutorService.submit("id", "second", (ci) -> {
			return sharedState.get();
		});
		assertEquals((Integer) 1, Futures.<Integer> getUnchecked(future));
	}

	@Test(timeout = 1000)
	public void testSubmitSequential02() {
		CountDownLatch releaseFirst = new CountDownLatch(1);
		QueuedTaskFuture<Integer> future = queuedExecutorService.submit("id", "first", (ci) -> {
			Uninterruptibles.awaitUninterruptibly(releaseFirst);
			return sharedState.get();
		});
		queuedExecutorService.submit("id", "second", (ci) -> {
			return sharedState.incrementAndGet();
		});
		Uninterruptibles.sleepUninterruptibly(500, TimeUnit.MILLISECONDS);
		releaseFirst.countDown();
		assertEquals((Integer) 0, Futures.<Integer> getUnchecked(future));
		queuedExecutorService.join();
	}

	@Test(timeout = 1000)
	public void testSubmitSequential03() throws Exception {
		CountDownLatch secondStarted = new CountDownLatch(1);
		queuedExecutorService.submit("id", "first", (ci) -> {
			Uninterruptibles.awaitUninterruptibly(secondStarted);
			return null;
		});
		queuedExecutorService.submit("id", "second", (ci) -> {
			secondStarted.countDown();
			return null;
		});
		assertFalse(secondStarted.await(500, TimeUnit.MILLISECONDS));
		secondStarted.countDown();
		queuedExecutorService.join();
	}

	@Test(timeout = 1000)
	public void testCancel01() {
		QueuedTaskFuture<?> future1 = queuedExecutorService.submit("id", "first", (ci) -> {
			while (!ci.isCanceled()) {
				Uninterruptibles.sleepUninterruptibly(10, TimeUnit.MILLISECONDS);
			}
			return null;
		});
		QueuedTaskFuture<?> future2 = queuedExecutorService.submit("id", "second", (ci) -> {
			return sharedState.incrementAndGet();
		});
		future1.cancel(false);
		future2.join();
		assertEquals(1, sharedState.get());
	}

	@Test(timeout = 1000)
	public void testCancel02() {
		queuedExecutorService.submit("id", "first", (ci) -> {
			while (!ci.isCanceled()) {
				Uninterruptibles.sleepUninterruptibly(10, TimeUnit.MILLISECONDS);
			}
			return null;
		});
		QueuedTaskFuture<?> future = queuedExecutorService.submit("id", "second", (ci) -> {
			assertTrue(ci.isCanceled());
			return sharedState.incrementAndGet();
		});
		queuedExecutorService.cancelAll("id");
		future.join();
		assertEquals(1, sharedState.get());
	}

	@Test(timeout = 1000)
	public void testCancel03() {
		queuedExecutorService.submit("id", "first", (ci) -> {
			while (!ci.isCanceled()) {
				Uninterruptibles.sleepUninterruptibly(10, TimeUnit.MILLISECONDS);
			}
			return null;
		});
		QueuedTaskFuture<?> future = queuedExecutorService.submitAndCancelPrevious("id", "second", (ci) -> {
			sharedState.incrementAndGet();
			return null;
		});
		future.join();
		assertEquals(1, sharedState.get());
	}

	@Test(timeout = 1000)
	public void testCancel04() {
		CountDownLatch firstStarted = new CountDownLatch(1);
		CountDownLatch firstCanceled = new CountDownLatch(1);
		CountDownLatch secondStarted = new CountDownLatch(1);
		CountDownLatch secondCanceled = new CountDownLatch(1);
		queuedExecutorService.submit("id1", "first", (ci) -> {
			firstStarted.countDown();
			while (!ci.isCanceled()) {
				Uninterruptibles.sleepUninterruptibly(10, TimeUnit.MILLISECONDS);
			}
			firstCanceled.countDown();
			return null;
		});
		queuedExecutorService.submit("id2", "second", (ci) -> {
			secondStarted.countDown();
			while (!ci.isCanceled()) {
				Uninterruptibles.sleepUninterruptibly(10, TimeUnit.MILLISECONDS);
				sharedState.incrementAndGet();
			}
			secondCanceled.countDown();
			return null;
		});
		Uninterruptibles.awaitUninterruptibly(firstStarted);
		Uninterruptibles.awaitUninterruptibly(secondStarted);

		queuedExecutorService.cancelAll("id1");
		// 1) assert that 'first' got the cancellation event
		Uninterruptibles.awaitUninterruptibly(firstCanceled);
		// 2) assert that 'second' did not get the cancellation event (i.e. that it is still incrementing sharedState)
		int cnt = sharedState.get();
		while (sharedState.get() == cnt) {
			Uninterruptibles.sleepUninterruptibly(10, TimeUnit.MILLISECONDS);
		}

		queuedExecutorService.cancelAll("id2");
		// assert that now 'second' also got the cancellation event
		Uninterruptibles.awaitUninterruptibly(secondCanceled);
	}

	@Test(timeout = 1000)
	public void testShutdown() {
		queuedExecutorService.shutdown();
		try {
			queuedExecutorService.submit("id", "test", (ci) -> null);
			fail("expected RejectedExecutionException was not thrown");
		} catch (RejectedExecutionException e) {
			assertEquals("this QueuedExecutorService has been shut down", e.getMessage());
		}
	}

	@Test(timeout = 3000)
	public void testShutdownDuration_noTasks() {
		long start = System.currentTimeMillis();
		queuedExecutorService.shutdown(2000, TimeUnit.MILLISECONDS);
		long duration = System.currentTimeMillis() - start;
		assertTrue("expected a shutdown duration below 100ms, but was: " + duration + "ms", duration < 100);
	}

	@Test(timeout = 3000)
	public void testShutdownDuration_cancelableTask() {
		queuedExecutorService.submit("id", "test", (ci) -> {
			while (!ci.isCanceled()) {
				Uninterruptibles.sleepUninterruptibly(10, TimeUnit.MILLISECONDS);
			}
			return null;
		});
		long start = System.currentTimeMillis();
		queuedExecutorService.shutdown(2000, TimeUnit.MILLISECONDS);
		long duration = System.currentTimeMillis() - start;
		assertTrue("expected a shutdown duration below 100ms, but was: " + duration + "ms", duration < 100);
	}

	@Test(timeout = 3000)
	public void testShutdownDuration_nonCancelableButInterruptibleTask() {
		queuedExecutorService.submit("id", "test", (ci) -> {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// end task when interrupted
			}
			return null;
		});
		long start = System.currentTimeMillis();
		// first half of timeout will expire, because task does not react to cancellation:
		queuedExecutorService.shutdown(2000, TimeUnit.MILLISECONDS);
		long duration = System.currentTimeMillis() - start;
		assertTrue("expected a shutdown duration above 1000ms but below 1100ms, but was: " + duration + "ms",
				1000 <= duration && duration < 1100);
	}

	@Test(timeout = 3000)
	public void testShutdownDuration_uninterruptibleTask() {
		queuedExecutorService.submit("id", "test", (ci) -> {
			Uninterruptibles.sleepUninterruptibly(3000, TimeUnit.MILLISECONDS);
			return null;
		});
		long start = System.currentTimeMillis();
		// first AND second half of timeout will expire, because task does react neither to cancellation nor interrupt:
		queuedExecutorService.shutdown(2000, TimeUnit.MILLISECONDS);
		long duration = System.currentTimeMillis() - start;
		assertTrue("expected a shutdown duration above 2000ms but below 2100ms, but was: " + duration + "ms",
				2000 <= duration && duration < 2100);
	}
}
