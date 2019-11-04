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
package org.eclipse.n4js.validation.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.xtext.service.OperationCanceledError;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Provider;

/**
 * A cancelable (ie, interruptible) provider of issues for a resource.
 * <p>
 * The cancelation capability implies we definitely want the {@link OperationCanceledError} to escape the getter
 * invocation. Otherwise any value conveniently returned from there ("empty list") would be stored in the cache, thus
 * corrupting it.
 */
public final class IssuesProvider implements Provider<List<Issue>> {

	private final IResourceValidator rv;
	private final Resource r;
	private final OperationCanceledManager operationCanceledManager;
	private final CancelIndicator ci;

	@SuppressWarnings("javadoc")
	public IssuesProvider(IResourceValidator resourceValidator, Resource res,
			OperationCanceledManager operationCanceledManager, CancelIndicator ci) {
		this.rv = resourceValidator;
		this.r = res;
		this.operationCanceledManager = operationCanceledManager;
		this.ci = ci;
	}

	/**
	 * Why does this method resort to returning via exceptional-control-flow upon detecting a cancellation request?
	 * Didn't the validation method already handle it?
	 * <p>
	 * Upon cancellation, some validators (for example, {@code ManifestAwareResourceValidator}) may decide to stop all
	 * work and return only the issues found thus far (or even an empty list of issues). That's a valid realization of
	 * the cancellation contract. Thus the {@code validate()} method returned normally. If we were to return those
	 * partial results, the caller of this method would proceed to pollute the cache with them. That's prevented by
	 * throwing a fabricated {@link OperationCanceledError}
	 */
	@Override
	public List<Issue> get() throws OperationCanceledError {
		operationCanceledManager.checkCanceled(ci);
		URI uri = r.getURI();
		DataCollector dc = uri != null && N4JSGlobals.PACKAGE_JSON.equals(uri.lastSegment())
				? N4JSDataCollectors.dcValidationsPackageJson
				: N4JSDataCollectors.dcValidations;
		List<Issue> issues;
		try (Measurement m = dc.getMeasurement("validation");) {
			issues = rv.validate(r, CheckMode.ALL, ci);
		}
		if (!issues.contains(null)) {
			operationCanceledManager.checkCanceled(ci);
			return issues;
		}
		ArrayList<Issue> result = new ArrayList<>(issues);
		result.removeAll(Collections.<Issue> singleton(null));
		operationCanceledManager.checkCanceled(ci);
		return result;
	}
}
