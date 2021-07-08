/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.scoping;

import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * Improved {@link org.eclipse.xtext.resource.ForwardingEObjectDescription} that overrides {@link #delegate()} to make
 * the delegate available to the public.
 */
public abstract class ForwardingEObjectDescription extends org.eclipse.xtext.resource.ForwardingEObjectDescription {

	/**
	 * Creates a new instance with the given delegate.
	 */
	protected ForwardingEObjectDescription(IEObjectDescription delegate) {
		super(delegate);
	}

	@Override
	public final IEObjectDescription delegate() {
		return super.delegate();
	}

}
