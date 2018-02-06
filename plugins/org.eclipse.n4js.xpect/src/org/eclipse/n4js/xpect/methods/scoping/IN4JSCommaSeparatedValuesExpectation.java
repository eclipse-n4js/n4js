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
package org.eclipse.n4js.xpect.methods.scoping;

import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.expectation.ICommaSeparatedValuesExpectation;

import org.eclipse.n4js.xpect.methods.scoping.N4JSCommaSeparatedValuesExpectation.N4JSCommaSeparatedValuesExpectationImpl;

/**
 */
@XpectImport(N4JSCommaSeparatedValuesExpectationImpl.class)
public interface IN4JSCommaSeparatedValuesExpectation extends ICommaSeparatedValuesExpectation {
	// just a marker interface
}
