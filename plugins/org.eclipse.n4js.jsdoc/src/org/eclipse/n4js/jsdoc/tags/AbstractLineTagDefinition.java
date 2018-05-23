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
package org.eclipse.n4js.jsdoc.tags;

import org.eclipse.n4js.jsdoc.dom.LineTag;
import org.eclipse.n4js.jsdoc.dom.TagTitle;

/**
 * Base class for implementations {@link LineTag} instances.
 */
public abstract class AbstractLineTagDefinition extends AbstractBaseTagDefinition {

	/**
	 * Convenience method.
	 */
	protected LineTag createLineTag(TagTitle tagTitle) {
		LineTag tag = DOM.createLineTag();
		tag.setTagDefinition(this);
		tag.setTitle(tagTitle);
		return tag;
	}

}
