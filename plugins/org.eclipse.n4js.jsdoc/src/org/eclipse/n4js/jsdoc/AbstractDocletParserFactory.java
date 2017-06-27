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

/**
 * Clients using DocletParser are recommended to subclass DocletFactory to capture complexity of creating specific
 * instances. Especially possible dependencies between instances of ITags and injected sub parsers / sub tokenizers
 * should be handled by subclasses of this factory.
 */
public abstract class AbstractDocletParserFactory {
	/**
	 * @return configured instance of parser
	 */
	public abstract DocletParser createDocletParser();
}
