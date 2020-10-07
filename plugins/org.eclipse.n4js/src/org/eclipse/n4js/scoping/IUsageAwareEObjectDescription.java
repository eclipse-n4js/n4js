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
package org.eclipse.n4js.scoping;

import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * An {@link IEObjectDescription} that is aware of usage.
 *
 * Descriptions that implement this interface are notified, when the scoping actually binds the description to a name.
 */
public interface IUsageAwareEObjectDescription extends IEObjectDescription {

	/**
	 * The import specifier that imported the element denoted by this eObject description.
	 */
	public ImportSpecifier getOrigin();

	/**
	 * This method is invoked when this {@link IEObjectDescription} used to bind a name.
	 */
	public abstract void markAsUsed();

	/**
	 * Iff unset, sets the transient <code>originImport<code> property of the given {@link IdentifierRef} to this
	 * description's {@link #getOrigin() origin}.
	 */
	public void recordOrigin(IdentifierRef identRef);
}
