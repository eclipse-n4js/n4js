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
package org.eclipse.n4js.jsdoc;

import org.eclipse.n4js.jsdoc.dom.DomFactory;
import org.eclipse.n4js.jsdoc.dom.TagTitle;

/**
 */
public abstract class AbstractJSDocParser {

	/**
	 * Creates tag title with actual and canonical title.
	 */
	protected TagTitle createTagTitle(JSDocToken token, ITagDefinition tagDefinition) {
		TagTitle tagTitle = DomFactory.eINSTANCE.createTagTitle();
		tagTitle.setTitle(tagDefinition.getTitle());
		tagTitle.setActualTitle(token.token);
		tagTitle.setBegin(token.start);
		tagTitle.setEnd(token.end);
		return tagTitle;
	}
}
