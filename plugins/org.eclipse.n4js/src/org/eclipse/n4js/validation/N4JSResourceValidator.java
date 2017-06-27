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
package org.eclipse.n4js.validation;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.validation.ResourceValidatorImpl;

import com.google.inject.Inject;

import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.resource.N4JSResource;

/**
 * A resource validator that will only validate the first element directly contained in the resource if the resource is
 * an instance of {@link N4JSResource}. This is required because <code>N4JSResource</code>s contain the AST as first
 * content element (this should be validated) and the type model as second content element (this should *not* be
 * validated; the type model is automatically generated from the AST and assumed to be correct).
 *
 * It will not create issues for resources which are contained in folders that are filtered by module filters in the
 * manifest.
 */
public class N4JSResourceValidator extends ResourceValidatorImpl {

	@Inject
	private IN4JSCore n4jsCore;

	/**
	 * Don't validate the inferred module since all validation information should be available on the AST elements.
	 */
	@Override
	protected void validate(Resource resource, CheckMode mode, CancelIndicator monitor, IAcceptor<Issue> acceptor) {
		if (monitor.isCanceled() || n4jsCore.isNoValidate(resource.getURI())) {
			return;
		}
		List<EObject> contents = resource.getContents();
		if (!contents.isEmpty()) {
			EObject firstElement = contents.get(0);
			// // Mark the scoping as sealed. (No other usage-flags should be set for import-declarations.)
			// if (firstElement instanceof Script) {
			// ((Script) firstElement).setFlaggedBound(true);
			// }
			validate(resource, firstElement, mode, monitor, acceptor);

			// UtilN4.takeSnapshotInGraphView("post validation", resource);
		}
	}
}
