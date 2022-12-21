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
package org.eclipse.n4js.scoping.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.resource.ErrorAwareLinkingService;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.xtext.scoping.ForwardingEObjectDescription;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * A special {@link IEObjectDescription} that is recognized by the {@link ErrorAwareLinkingService} to produce a
 * resource diagnostic if it is obtained from the scope.
 * <p>
 * Do not check for instances of this class, instead use utility methods in {@link IEObjectDescriptionWithError}.
 * </p>
 */
public abstract class AbstractDescriptionWithError extends ForwardingEObjectDescription implements
		IEObjectDescriptionWithError {

	/**
	 * Wraps the given delegate with an error description.
	 */
	protected AbstractDescriptionWithError(IEObjectDescription delegate) {
		super(delegate);
	}

	/**
	 * Returns the name of the member type, e.g., method or field.
	 */
	protected static String getMemberTypeName(EObject eObject, boolean structFieldInitMode) {
		if (eObject instanceof TMethod) {
			return "method";
		}
		if (eObject instanceof TField) {
			return "field";
		}
		if (eObject instanceof TGetter) {
			return "getter";
		}
		if (eObject instanceof TSetter) {
			return structFieldInitMode ? "getter" : "setter";
		}
		if (eObject instanceof TEnumLiteral) {
			return "enum literal";
		}
		return "member";
	}

}
