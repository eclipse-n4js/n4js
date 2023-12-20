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
import org.eclipse.n4js.scoping.members.MemberScope;
import org.eclipse.n4js.ts.types.FieldAccessor;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * If the {@link MemberScope} finds a field accessor on the wrong side of an assignment, this descriptor is used to
 * bound the reference to the wrong accessor and display an error. This may be the case if getter of an object literal
 * is used on the left hand side of an assignment and no setter is defined, or vice versa.
 */
public class WrongWriteAccessDescription extends AbstractDescriptionWithError {

	private final boolean accessForWriteOperation;
	private final boolean isAssignmentToFinalFieldInCtor;

	/**
	 * Wraps an existing description for a member (field or accessor) with a wrong accessor error message.
	 *
	 * @param accessForWriteOperation
	 *            if true, the getter is used instead of a setter.
	 * @param isAssignmentToFinalFieldInCtor
	 *            if true, we are in an assignment expression to a final field within the ctor of the owning class.
	 */
	public WrongWriteAccessDescription(IEObjectDescription delegate, boolean accessForWriteOperation,
			boolean isAssignmentToFinalFieldInCtor) {
		super(delegate);
		this.accessForWriteOperation = accessForWriteOperation;
		this.isAssignmentToFinalFieldInCtor = isAssignmentToFinalFieldInCtor;
	}

	@Override
	public String getMessage() {
		if (isAssignmentToFinalFieldInCtor) {
			return IssueCodes.CLF_FIELD_FINAL_REINIT_IN_CTOR.getMessage(getName());
		}
		return IssueCodes.VIS_WRONG_READ_WRITE_ACCESS.getMessage(
				getKeyword(), getName(), accessForWriteOperation ? "read-only" : "write-only");
	}

	@Override
	public String getIssueCode() {
		if (isAssignmentToFinalFieldInCtor) {
			return IssueCodes.CLF_FIELD_FINAL_REINIT_IN_CTOR.name();
		}
		return IssueCodes.VIS_WRONG_READ_WRITE_ACCESS.name();
	}

	private String getKeyword() {
		final EObject obj = delegate().getEObjectOrProxy();
		if (obj instanceof TField) {
			if (((TField) obj).isConst())
				return "const field";
			if (((TField) obj).isFinal())
				return "final field";
			return "field";
		}
		if (obj instanceof FieldAccessor)
			return "accessor";
		if (obj instanceof TMethod)
			return "method";
		return "member";
	}
}
