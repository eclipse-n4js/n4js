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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.validation.ResourceValidatorImpl;

import com.google.inject.Inject;

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
	@Inject
	private OperationCanceledManager operationCanceledManager;

	@Override
	public List<Issue> validate(Resource resource, CheckMode mode, CancelIndicator cancelIndicator) {
		if (resource instanceof N4JSResource) {
			final N4JSResource resourceCasted = (N4JSResource) resource;
			try {
				resourceCasted.performPostProcessing(cancelIndicator);
			} catch (Throwable th) {
				// ignore this exception/error (we will create an issue for it below)
			}
			if (resourceCasted.isFullyProcessed()
					&& resourceCasted.getPostProcessingThrowable() != null) {
				// When getting here, we have an attempt to validate a resource that was post-processed but the
				// post-processing failed (i.e. postProcessingThrowable!=null). Validating such a resource will usually
				// cause a multitude of follow-up exceptions in many @Check methods (could easily be hundreds). Since,
				// EMF/Xtext has the behavior of not aborting the overall validation due to an exception in a single
				// @Check method, this will lead to many exceptions, which are all just follow-up issues of the problem
				// that caused the post-processing to fail.
				// 1) Since this is annoying and misleading, we just ignore all ordinary validation.
				// 2) To not overlook the problem, we create a single validation issue (error) pointing out the problem.
				// (for a test, see class AvoidFollowUpExceptionsInValidationTest)
				final Throwable th = resourceCasted.getPostProcessingThrowable();
				if (operationCanceledManager.isOperationCanceledException(th)) {
					return Collections.emptyList(); // do not show errors in case of cancellation
				}
				final Issue issue = createPostProcessingFailedError(resourceCasted, th);
				return Collections.singletonList(issue);
			}
		}
		return super.validate(resource, mode, cancelIndicator);
	}

	/**
	 * Don't validate the inferred module since all validation information should be available on the AST elements.
	 */
	@Override
	protected void validate(Resource resource, CheckMode mode, CancelIndicator cancelIndicator,
			IAcceptor<Issue> acceptor) {
		operationCanceledManager.checkCanceled(cancelIndicator);
		if (n4jsCore.isNoValidate(resource.getURI())) {
			return;
		}
		List<EObject> contents = resource.getContents();
		if (!contents.isEmpty()) {
			EObject firstElement = contents.get(0);
			// // Mark the scoping as sealed. (No other usage-flags should be set for import-declarations.)
			// if (firstElement instanceof Script) {
			// ((Script) firstElement).setFlaggedBound(true);
			// }
			validate(resource, firstElement, mode, cancelIndicator, acceptor);

			// UtilN4.takeSnapshotInGraphView("post validation", resource);
		}
	}

	private Issue createPostProcessingFailedError(N4JSResource res, Throwable th) {
		final String thKind = th instanceof Error ? "error" : (th instanceof Exception ? "exception" : "throwable");
		final String thName = th.getClass().getSimpleName();
		final String trace = "\n" + Stream.of(th.getStackTrace())
				.map(ste -> ste.toString())
				.collect(Collectors.joining("\n")); // cannot add indentation, because Xtext would reformat the message
		final String msg = IssueCodes.getMessageForPOST_PROCESSING_FAILED(thKind, thName, th.getMessage() + trace);
		final Issue.IssueImpl issue = new Issue.IssueImpl();
		issue.setCode(IssueCodes.POST_PROCESSING_FAILED);
		issue.setSeverity(IssueCodes.getDefaultSeverity(IssueCodes.POST_PROCESSING_FAILED));
		issue.setMessage(msg);
		issue.setUriToProblem(EcoreUtil.getURI(res.getScript()));
		issue.setType(CheckType.FAST);
		issue.setOffset(0);
		issue.setLength(0);
		issue.setLineNumber(0);
		issue.setColumn(0);
		return issue;
	}
}
