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
package org.eclipse.n4js.xtext.serializer;

import org.eclipse.xtext.serializer.sequencer.ContextFinder;

import com.google.inject.Singleton;

/**
 * The ContextFinder is not threadsafe in its initConstraints. In tests and on other occasions we concurrently try to
 * init the constraints thus we need to guard against these modifications.
 */
@SuppressWarnings("restriction")
@Singleton
class SynchronizedContextFinder extends ContextFinder {

	@Override
	public synchronized void initConstraints() {
		super.initConstraints();
	}

}
