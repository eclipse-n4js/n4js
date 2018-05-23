/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.scoping.members;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.base.Predicate;

/**
 * Same as {@link TypingStrategyFilter}, but accepts {@link IEObjectDescription}s instead of {@link TMember}s as input.
 */
public class TypingStrategyFilterDesc implements Predicate<IEObjectDescription> {

	private final TypingStrategyFilter delegate;

	/**
	 * Creates new instance.
	 */
	public TypingStrategyFilterDesc(TypingStrategy typingStrategy, boolean isWriteAccess) {
		this.delegate = new TypingStrategyFilter(typingStrategy, isWriteAccess, false);
	}

	/**
	 * Returns the typing strategy of this filter, set in the constructor.
	 */
	public TypingStrategy getTypingStrategy() {
		return delegate.getTypingStrategy();
	}

	@Override
	public boolean apply(IEObjectDescription description) {
		final TypingStrategy typingStrategy = delegate.getTypingStrategy();
		if (typingStrategy == TypingStrategy.DEFAULT || typingStrategy == TypingStrategy.NOMINAL) {
			return true;
		}
		EObject proxyOrInstance = description.getEObjectOrProxy();
		if (proxyOrInstance == null || proxyOrInstance.eIsProxy()) {
			return true;
		}
		if (!(proxyOrInstance instanceof TMember)) {
			return true;
		}
		return delegate.apply((TMember) proxyOrInstance);
	}
}
