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
package org.eclipse.n4js.ide.xtext.server.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.collect.ImmutableList;

/**
 * Utilities for {@link CancelIndicator}s.
 */
public class CancelIndicatorUtil {

	/**
	 * A cancel indicator that will report a cancellation iff one or more of its delegate indicators reports a
	 * cancellation.
	 */
	public static class CombinedCancelIndicator implements CancelIndicator {
		/** The cancel indicators this instance delegates to. */
		protected final ImmutableList<CancelIndicator> delegates;

		/** See {@link CombinedCancelIndicator}. */
		public CombinedCancelIndicator(List<CancelIndicator> cancelIndicators) {
			this.delegates = ImmutableList.copyOf(cancelIndicators);
		}

		@Override
		public boolean isCanceled() {
			for (CancelIndicator ci : delegates) {
				if (ci.isCanceled()) {
					return true;
				}
			}
			return false;
		}
	}

	/** See {@link #combine(List)}. */
	public static CancelIndicator combine(CancelIndicator... indicators) {
		return combine(Arrays.asList(indicators));
	}

	/**
	 * Returns a cancel indicator that will report a cancellation, i.e. will return true from
	 * {@link CancelIndicator#isCanceled()}, iff at least one of the given indicators reports a cancellation. Might
	 * return one of the given indicators.
	 */
	public static CancelIndicator combine(List<CancelIndicator> cancelIndicators) {
		List<CancelIndicator> actualIndicators = cancelIndicators.stream()
				.filter(ci -> ci != null && ci != CancelIndicator.NullImpl)
				.collect(Collectors.toList());
		if (actualIndicators.isEmpty()) {
			return CancelIndicator.NullImpl;
		} else if (actualIndicators.size() == 1) {
			return actualIndicators.get(0);
		} else {
			return new CombinedCancelIndicator(actualIndicators);
		}
	}
}
