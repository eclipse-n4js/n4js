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
package org.eclipse.n4js.packagejson.model.edit;

import org.eclipse.n4js.json.JSON.JSONDocument;

/**
 * Represents a modification of a {@link JSONDocument} instance.
 *
 * @See {@link PackageJsonModificationProvider}
 */
public interface IJSONDocumentModification {
	/**
	 * Applies this modification to the given {@code document}.
	 */
	void apply(JSONDocument document);
}
