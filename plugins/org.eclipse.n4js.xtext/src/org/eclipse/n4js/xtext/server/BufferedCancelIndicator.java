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
package org.eclipse.n4js.xtext.server;

import java.time.Duration;

import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.base.Preconditions;
import com.google.common.base.Ticker;

/**
 * A cancel indicator that will not cancel immediately but only after a second delay to allow short running tasks to
 * complete despite an attempt to cancel.
 */
public class BufferedCancelIndicator implements CancelIndicator {

	private final CancelIndicator delegate;

	private final Ticker clock;

	private final long bufferNanos;

	private long canceledSince;

	/**
	 * Buffer the cancellation attempts that are issued by the given delegate.
	 */
	public BufferedCancelIndicator(CancelIndicator delegate, Duration buffer) {
		this(delegate, buffer, Ticker.systemTicker());
	}

	/**
	 * Buffer the cancellation attempts that are issued by the given delegate. Use the given clock as the source for the
	 * time.
	 *
	 * This is public for testing purpose.
	 */
	public BufferedCancelIndicator(CancelIndicator delegate, Duration buffer, Ticker clock) {
		this.bufferNanos = buffer.toNanos();
		this.delegate = Preconditions.checkNotNull(delegate);
		this.clock = Preconditions.checkNotNull(clock);
	}

	@Override
	public boolean isCanceled() {
		if (this.canceledSince == 0 && this.delegate.isCanceled()) {
			this.canceledSince = clock.read();
			return false;
		}
		return this.canceledSince != 0 && clock.read() > this.canceledSince + bufferNanos;
	}
}