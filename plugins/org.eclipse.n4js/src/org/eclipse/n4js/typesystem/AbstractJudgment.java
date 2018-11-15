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
package org.eclipse.n4js.typesystem;

import org.eclipse.n4js.n4idl.versioning.N4IDLVersionResolver;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.ContainerTypesHelper;

import com.google.inject.Inject;

/**
 * Abstract base class for all "type system judgments". Each concrete subclass will represent one judgment, i.e. one
 * major operation of the type system such as inference of types of AST nodes, subtype checking, computation of type
 * expectations, etc.
 * <p>
 * The term "judgment" comes from the Xsemantics framework, with which the subclasses' functionality was implemented,
 * originally.
 */
/* package */ abstract class AbstractJudgment {

	@Inject
	protected N4JSTypeSystem ts;
	@Inject
	protected TypeSystemHelper typeSystemHelper;
	@Inject
	protected ContainerTypesHelper containerTypesHelper;
	@Inject
	protected N4IDLVersionResolver n4idlVersionResolver;

	protected static UnknownTypeRef unknown() {
		return TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
	}
}
