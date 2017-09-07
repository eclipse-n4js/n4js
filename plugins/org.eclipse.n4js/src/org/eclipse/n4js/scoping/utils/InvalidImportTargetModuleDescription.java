/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.scoping.utils;

import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.resource.IEObjectDescription;

/** Custom {@link IEObjectDescriptionWithError} for nicer user message */
public class InvalidImportTargetModuleDescription extends AbstractDescriptionWithError {
	private final String message;
	private final String issueCode;

	@SuppressWarnings("javadoc")
	public InvalidImportTargetModuleDescription(IEObjectDescription delegate, String message, String issueCode) {
		super(delegate);
		this.issueCode = issueCode;
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public String getIssueCode() {
		return this.issueCode;
	}
}
