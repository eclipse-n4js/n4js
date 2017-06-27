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
package org.eclipse.n4js.tester;

import static java.util.concurrent.TimeUnit.SECONDS;
import org.eclipse.n4js.tester.fsm.TestFsm;

/**
 * Defaults and constants for the N4 tester core module.
 */
public abstract class TesterModuleDefaults {

	/**
	 * Key for the default FSM timeout value.
	 */
	public static final String DEFAULT_FSM_TIMEOUT_KEY = "defaultFsmTimeoutKey";
	/**
	 * Key for the setup FSM timeout value. Used between the {@link TestFsm#initializeSession(String) initialize} and
	 * the {@link TestFsm#startTest(String, long) start test} phases.
	 */
	public static final String SETUP_FSM_TIMEOUT_KEY = "setupFsmTimeoutKey";

	/**
	 * Key for the embedded HTTP server port.
	 */
	public static final String HTTP_SERVER_PORT_KEY = "httpServerPortKey";

	/**
	 * Key for the property whether the server should perform an info dump on shut down.
	 */
	public static final String DUMP_SERVER_ON_STOP_KEY = "dumpServerOnStopKey";

	/**
	 * Minimum thread number for the HTTP server.
	 */
	public static final String MIN_THREAD_COUNT_KEY = "minThreadCountKey";

	/**
	 * Maximum thread number for the HTTP server.
	 */
	public static final String MAX_THREAD_COUNT_KEY = "maxThreadCountKey";

	/**
	 * The HTTP server thread pool blocking capacity. <br>
	 * For detailed explanation one can reference <a
	 * href="https://wiki.eclipse.org/Jetty/Howto/High_Load#Thread_Pool">this</a> thread.
	 */
	public static final String THREAD_POOL_BLOCKING_CAPACITY_KEY = "threadPoolBlockingCapacityKey";

	/**
	 * Key for the test tree registry timeout. <br>
	 * Since test tree updates its internal state asynchronously one should wait some time before validating its
	 * internal final state.
	 */
	public static final String TEST_TREE_TIMEOUT_KEY = "testTreeTimeoutKey";

	/**
	 * Value for the default FSM timeout in milliseconds.
	 */
	/* default */static final long DEFAULT_FSM_TIMEOUT_VALUE = SECONDS.toMillis(10L);

	/**
	 * Value for the setup FSM timeout in milliseconds.
	 */
	/* default */static final long SETUP_FSM_TIMEOUT_VALUE = SECONDS.toMillis(90L);

	/**
	 * Port for the embedded HTTP server.
	 */
	/* default */static final int HTTP_SERVER_PORT_VALUE = 9415;

	/**
	 * Server should perform an info dump on shut down.
	 */
	/* default */static final boolean DUMP_SERVER_ON_STOP_VALUE = false;

	/**
	 * Minimum thread number for the server thread pool.
	 */
	/* default */static final int MIN_THREAD_COUNT_VALUE = 16;

	/**
	 * Maximum thread number for the server thread pool.
	 */
	/* default */static final int MAX_THREAD_COUNT_VALUE = 512;

	/**
	 * The value for the thread pool blocking capacity.
	 */
	/* default */static final int THREAD_POOL_BLOCKING_CAPACITY_VALUE = 60_000;

	/**
	 * The default timeout value for the test tree registry.
	 */
	/* default */static final long TEST_TREE_TIMEOUT_VALUE = SECONDS.toMillis(90L);

	private TesterModuleDefaults() {
	}

}
