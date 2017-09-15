/**
 * Copyright (c) 2017 Marcus Mews.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Marcus Mews - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.factories;

import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.n4jsx.n4JSX.JSXPropertyAttribute;
import org.eclipse.n4js.n4jsx.n4JSX.JSXSpreadAttribute;

class JSXAttributeFactory {

	static ComplexNode buildComplexNode(JSXPropertyAttribute jasPA) {
		return ExpressionFactory.buildComplexNode(jasPA);
	}

	static ComplexNode buildComplexNode(JSXSpreadAttribute jasSA) {
		return ExpressionFactory.buildComplexNode(jasSA);
	}

}
