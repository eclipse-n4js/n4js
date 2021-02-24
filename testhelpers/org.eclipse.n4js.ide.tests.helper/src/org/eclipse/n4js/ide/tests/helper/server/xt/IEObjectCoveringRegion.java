/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.helper.server.xt;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xpect.xtext.lib.util.XtextOffsetAdapter.IEObjectOwner;

/**
 * Provides an AST element of type {@link EObject}. The AST related node covers the complete region defined by the
 * offset of the specified location and the length of the specified location. That is, for a given test class with a
 * parameter 'of' as OFFSET (via {@code @ParameterParser(syntax = "('of' arg1=OFFSET)?")}), the following two tests
 * <br/>
 * {@code // XpECT ... of 'a as B'} <br/>
 * and <br/>
 * {@code // XpECT ... of 'a'} <br/>
 * will return different objects (CastExpression vs. IdentifierRef). If no region is defined, this test will return the
 * next element after the test line.
 */
public interface IEObjectCoveringRegion extends IEObjectOwner {
	// no new fields
	/**
	 * Return the offset of the region
	 */
	public int getOffset();
}
