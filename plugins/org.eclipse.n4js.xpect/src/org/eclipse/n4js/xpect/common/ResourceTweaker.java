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
package org.eclipse.n4js.xpect.common;

import org.eclipse.xtext.resource.XtextResource;

/**
 * Hook-provider for just-before compilation resource tweaks. Required by quick-fix tests
 */
public interface ResourceTweaker {

	/**
	 * @param resource
	 *            resource to modify
	 */
	void tweak(XtextResource resource);
}
