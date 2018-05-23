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
package org.eclipse.n4js.tester.internal;

import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.cache.CacheBuilder.newBuilder;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.primitives.Ints.max;
import static org.eclipse.n4js.tester.TesterModuleDefaults.TEST_TREE_TIMEOUT_KEY;
import static java.lang.Runtime.getRuntime;
import static java.util.Collections.synchronizedMap;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.apache.log4j.Logger.getLogger;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import org.eclipse.n4js.tester.TestTreeRegistry;
import org.eclipse.n4js.tester.domain.TestCase;
import org.eclipse.n4js.tester.domain.TestResult;
import org.eclipse.n4js.tester.domain.TestTree;
import org.eclipse.n4js.tester.fsm.TestFsmRegistry;

/**
 * Implementation of a {@link TestTreeRegistry test tree registry}.
 */
@Singleton
public class TestTreeRegistryImpl implements InternalTestTreeRegistry {

	private static final Logger LOGGER = getLogger(TestTreeRegistryImpl.class);
	private static final boolean debugEnabled = LOGGER.isDebugEnabled();

	private final TestFsmRegistry fsmRegistry;
	private final Map<String, TestTree> cache;
	private final Map<String, Object> mutex;
	private final long timeout;
	private final LoadingCache<String, ExecutorService> executors = newBuilder()
			.removalListener(new RemovalListener<String, ExecutorService>() {

				@Override
				public void onRemoval(final RemovalNotification<String, ExecutorService> notification) {
					if (!notification.getValue().isShutdown()) {
						notification.getValue().shutdown();
					}
				}
			}).build(new CacheLoader<String, ExecutorService>() {

				@Override
				public ExecutorService load(final String sessionId) throws Exception {
					final int threads = getRuntime().availableProcessors() / 2;
					return newFixedThreadPool(max(threads, 1));
				}
			});

	@Inject
	/* default */ TestTreeRegistryImpl(final TestFsmRegistry fsmRegistry,
			final @Named(TEST_TREE_TIMEOUT_KEY) long timeout) {
		this.fsmRegistry = fsmRegistry;
		this.timeout = timeout;
		cache = synchronizedMap(newHashMap());
		mutex = synchronizedMap(newHashMap());
	}

	@Override
	public void registerTestTree(final TestTree tree) {
		final String sessionId = tree.getSessionId().getValue();
		if (!fsmRegistry.isSessionExist(sessionId)) {
			throw new IllegalStateException("Test session does not exist with ID: '" + sessionId + "'.");
		}
		if (!nullToEmpty(sessionId).equals(tree.getSessionId().getValue())) {
			throw new IllegalArgumentException("Session ID mismatch. Session ID was: " + sessionId
					+ " and the session ID of the test tree was: " + tree.getSessionId().getValue());
		}
		synchronized (this) {
			mutex.put(sessionId, new Object());
			if (null != getTree(sessionId)) {
				mutex.remove(sessionId);
				throw new IllegalStateException("Test tree already registered for session. Session ID: '" + sessionId
						+ "'.");
			}
			try {
				cache.put(sessionId, tree.clone());
				executors.get(sessionId);
			} catch (final CloneNotSupportedException e) {
				mutex.remove(sessionId);
				throw new RuntimeException(
						"Cloning the test tree for session failed. Session ID: '" + sessionId + "'.", e);
			} catch (final ExecutionException e) {
				mutex.remove(sessionId);
				throw new RuntimeException(
						"Error while registering executor service for session: '" + sessionId + "'.");
			}
		}
	}

	@Override
	public TestTree getTestTree(final String sessionId) {
		final TestTree copy = getTreeCopy(sessionId);
		if (null == copy) {
			throw new IllegalStateException("Test tree does not exist for session. Session ID: '" + sessionId + "'.");
		}
		return copy;
	}

	@Override
	public boolean validateTestTree(final String sessionId) {
		final TestTree copy = getTree(sessionId);
		if (null == copy) {
			throw new IllegalStateException("Test tree does not exist for session. Session ID: '" + sessionId + "'.");
		}
		synchronized (this) {
			final ExecutorService executorService = getExecutorService(sessionId);
			if (debugEnabled) {
				LOGGER.debug("Validating test tree for session: '" + sessionId + "'.");
			}
			if (!executorService.isShutdown()) {
				if (debugEnabled) {
					LOGGER.debug("Test tree is incomplete. Waiting for executors to finish the tree state update...");
				}
				try {
					executorService.shutdown();
					executorService.awaitTermination(timeout, MILLISECONDS);
				} catch (final InterruptedException e) {
					throw new RuntimeException(e);
				}
				if (debugEnabled) {
					LOGGER.debug("Test tree is in complete state.");
				}
			}
			return validate(getTree(sessionId));
		}
	}

	@Override
	public void putTestResult(final String sessionId, final String testId, final TestResult result) {

		final TestTree t = getTree(sessionId);
		if (null == t) {
			throw new IllegalStateException("Test tree does not exist for session. Session ID: '" + sessionId + "'.");
		}

		if (null == t.getTestCase(testId)) {
			throw new IllegalStateException("Test case cannot be found in test tree. Session ID: '" + sessionId
					+ "'. Test ID: " + testId);
		}

		getExecutorService(sessionId).submit(
				() -> {
					synchronized (mutex.get(sessionId)) {
						final TestTree tree = getTree(sessionId);
						if (null == tree) {
							throw new IllegalStateException("Test tree does not exist for session. Session ID: '"
									+ sessionId + "'.");
						}
						final TestTree updatedTree = updateWithResult(tree, testId, result);
						cache.put(sessionId, updatedTree);
					}
					return null; // the Void
				});
	}

	@Override
	public void purgeTestTree(final String sessionId) {
		if (!mutex.containsKey(sessionId)) {
			throw new IllegalStateException("Error while purging tree for session: '" + sessionId + "'.");
		}
		synchronized (mutex.get(sessionId)) {
			if (null == getTreeCopy(sessionId)) {
				throw new IllegalStateException("Test tree does not exist for session. Session ID: '" + sessionId
						+ "'.");
			}
			cache.remove(sessionId);
			executors.invalidate(sessionId);
			mutex.remove(sessionId);
		}
	}

	@Override
	public void purge() {
		synchronized (TestTreeRegistryImpl.class) {
			cache.clear();
		}
	}

	private ExecutorService getExecutorService(final String sessionId) {
		final ExecutorService executorService = executors.getIfPresent(sessionId);
		if (null == executorService) {
			throw new IllegalStateException("Executor service does not exist for test session: '" + sessionId + "'.");
		}
		return executorService;
	}

	private boolean validate(final TestTree tree) {
		boolean valid = true;
		for (final Iterator<TestCase> itr = tree.iterator(); itr.hasNext(); /**/) {
			valid &= null != itr.next().getResult();
		}
		return valid;
	}

	private TestTree updateWithResult(final TestTree tree, final String testId, final TestResult result) {
		final TestCase testCase = tree.getTestCase(testId);
		if (null == testCase) {
			throw new IllegalStateException("Test case cannot be found in test tree. Test ID: " + testId);
		}
		testCase.setResult(result);
		return tree;
	}

	private TestTree getTree(final String sessionId) {
		return cache.get(sessionId);
	}

	private TestTree getTreeCopy(final String sessionId) {
		if (!mutex.containsKey(sessionId)) {
			throw new IllegalStateException("Error while getting tree for session: '" + sessionId + "'.");
		}
		synchronized (mutex.get(sessionId)) {
			final TestTree tree = cache.get(sessionId);
			try {
				return null != tree ? tree.clone() : null;
			} catch (final CloneNotSupportedException e) {
				throw new RuntimeException(
						"Cloning the test tree for session failed. Session ID: '" + sessionId + "'.", e);
			}
		}
	}

}
