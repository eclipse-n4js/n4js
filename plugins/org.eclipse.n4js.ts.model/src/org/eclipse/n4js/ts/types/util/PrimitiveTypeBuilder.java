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
package org.eclipse.n4js.ts.types.util;

import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.xtext.naming.QualifiedName;

/**
 * Builder for {@link PrimitiveType}s.
 */
public final class PrimitiveTypeBuilder {

	private final PrimitiveType result;

	private PrimitiveTypeBuilder(String name) {
		result = TypesFactory.eINSTANCE.createPrimitiveType();
		result.setName(name);
	}

	/** Creates a {@link PrimitiveType}. */
	public static PrimitiveTypeBuilder createPrimitive(QualifiedName name) {
		return createPrimitive(name.getLastSegment());
	}

	/** Creates a {@link PrimitiveType}. */
	public static PrimitiveTypeBuilder createPrimitive(String name) {
		return new PrimitiveTypeBuilder(name);
	}

	/** Add a type parameter to the primitive type currently being created. */
	public PrimitiveTypeBuilder typeParam(String typeParamName) {
		TypeVariable typeParam = TypesFactory.eINSTANCE.createTypeVariable();
		typeParam.setName(typeParamName);
		result.getTypeVars().add(typeParam);
		return this;
	}

	/**
	 * Set the {@link PrimitiveType#getAssignmentCompatible() assignmentCompatible} property of the primitive type
	 * currently being created.
	 */
	public PrimitiveTypeBuilder assignmentCompatible(PrimitiveType type) {
		result.setAssignmentCompatible(type);
		return this;
	}

	/**
	 * Concludes creation.
	 *
	 * @return the newly created {@link PrimitiveType}.
	 */
	public PrimitiveType done() {
		return result;
	}
}
