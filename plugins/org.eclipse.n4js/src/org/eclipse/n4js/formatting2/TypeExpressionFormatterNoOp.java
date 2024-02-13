/**
 * Copyright (c) 2024 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.formatting2;

import org.eclipse.xtext.formatting2.IFormattableDocument;

/**
 *
 */
public class TypeExpressionFormatterNoOp extends TypeExpressionsFormatter {

	@Override
	public void format(Object obj, IFormattableDocument document) {
		throw new UnsupportedOperationException("TypeExpressionFormatter should not be used directly.");
	}
}
