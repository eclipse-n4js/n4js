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
package org.eclipse.n4js.tests.builder;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Event;
import org.junit.Assert;

/**
 */
public class TestEventListener implements IResourceDescription.Event.Listener {
	private volatile boolean eventFired = false;
	private final IFile file;
	private final String context;

	/***/
	public TestEventListener(String context, IFile file) {
		this.file = file;
		this.context = context;
	}

	@Override
	public void descriptionsChanged(Event event) {
		URI expectedURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		for (IResourceDescription.Delta delta : event.getDeltas()) {
			URI deltaURI = delta.getUri();
			if (expectedURI.equals(deltaURI)) {
				eventFired = true;
			}
		}
	}

	/***/
	public void waitForFiredEvent() {
		long start = System.currentTimeMillis();
		long end = System.currentTimeMillis();
		long span = end - start;

		while (!eventFired && span < 5000) {
			// wait
			end = System.currentTimeMillis();
			span = end - start;
		}
		if (!eventFired && span > 5000) {
			Assert.fail("DirtyStateManager event not produced for " + context + ".");
		}
	}
}
