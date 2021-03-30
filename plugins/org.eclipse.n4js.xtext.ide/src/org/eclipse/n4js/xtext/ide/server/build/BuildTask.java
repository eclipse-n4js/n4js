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
package org.eclipse.n4js.xtext.ide.server.build;

import java.util.Collections;

import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionChangeEvent;
import org.eclipse.xtext.util.CancelIndicator;

/**
 * A handle that can be used to trigger a cancellable build.
 *
 * Implementors have to make sure, that the parts of the build, that have to be performed atomically, are not being
 * cancelled.
 */
public interface BuildTask {
	/** No build is going to happen. */
	BuildTask NO_BUILD = (cancelIndicator) -> new ResourceDescriptionChangeEvent(Collections.emptyList());

	/** Run the build */
	IResourceDescription.Event build(CancelIndicator cancelIndicator);
}