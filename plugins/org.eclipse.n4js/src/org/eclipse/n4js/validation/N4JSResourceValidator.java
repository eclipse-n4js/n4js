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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.packagejson.PackageJsonUtils;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.postprocessing.ASTMetaInfoCache;
import org.eclipse.n4js.resource.N4JSCache;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.ResourceType;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.n4js.xtext.resource.XITextRegionWithLineInformation;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.util.LineAndColumn;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.validation.Issue.IssueImpl;
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
	private WorkspaceAccess workspaceAccess;
	@Inject
	private OperationCanceledManager operationCanceledManager;
	@Inject
	private N4JSCache n4jsCache;

	private List<Issue> doValidate(Resource resource, CheckMode mode, CancelIndicator cancelIndicator) {
		try (Measurement m = N4JSDataCollectors.dcValidations.getMeasurement()) {

			List<Issue> unknownTypeRefIssues = new ArrayList<>();
			String fileExtension = URIUtils.fileExtension(resource.getURI());
			switch (fileExtension) {
			case N4JSGlobals.DTS_FILE_EXTENSION:
				return Collections.emptyList(); // comment out for debugging
			case N4JSGlobals.N4JS_FILE_EXTENSION:
			case N4JSGlobals.N4JSX_FILE_EXTENSION:
			case N4JSGlobals.N4JSD_FILE_EXTENSION:
				if (N4JSLanguageUtils.isDefaultLanguageVersion()) {
					// checkForUnknownTypeRefs(resource, unknownTypeRefIssues);
				}
				break;
			}

			List<Issue> issues = doValidateWithMeasurement(resource, mode, cancelIndicator);
			unknownTypeRefIssues.addAll(issues);
			return unknownTypeRefIssues;
		}
	}

	private List<Issue> doValidateWithMeasurement(Resource resource, CheckMode mode, CancelIndicator cancelIndicator) {
		final N4JSWorkspaceConfigSnapshot ws = workspaceAccess.getWorkspaceConfig(resource);
		final N4JSProjectConfigSnapshot project = ws.findProjectContaining(resource.getURI());

		// QUICK EXIT #1: in case of invalid file type (e.g. js file in a project with project type definition)
		if (project != null && !isValidFileTypeForProjectType(resource, project)) {
			final Issue issue = createInvalidFileTypeError(resource, project);
			return Collections.singletonList(issue);
		}
		// QUICK EXIT #2: for external projects
		if (project != null && project.isExternal()) {
			return Collections.emptyList();
		}
		// QUICK EXIT #3: for files that match a "noValidate" module filter from package.json
		if (ws.isNoValidate(resource.getURI())) {
			return Collections.emptyList();
		}

		if (resource instanceof N4JSResource) {
			final N4JSResource resourceCasted = (N4JSResource) resource;

			// QUICK EXIT #4: for "opaque" modules (e.g. js files)
			// (pure performance tweak, because those resources have an empty AST anyway; see N4JSResource#doLoad())
			if (resourceCasted.isOpaque()) {
				return Collections.emptyList();
			}

			// trigger post-processing of N4JS resource (won't harm if post-processing has already taken place)
			try {
				resourceCasted.performPostProcessing(cancelIndicator);
			} catch (Throwable th) {
				// ignore this exception/error (we will create an issue for it below)
			}

			// QUICK EXIT #5: if post-processing failed
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

	@SuppressWarnings("unused")
	private void checkForUnknownTypeRefs(Resource resource, List<Issue> issues) {
		if (resource instanceof N4JSResource) {
			final N4JSResource resourceCasted = (N4JSResource) resource;
			ASTMetaInfoCache cache = resourceCasted.getASTMetaInfoCache();
			if (cache != null && cache.hasUnknownTypeRef()) {
				boolean hasErrors = issues.stream().anyMatch(issue -> issue.getSeverity() == Severity.ERROR);
				if (!hasErrors) {
					final String msg = IssueCodes.getMessageForTYS_UNKNOWN_TYPE_REF();
					for (TypableElement elem : cache.getAstNodesOfUnknownTypes()) {
						Issue issue = createFileIssue(resource, elem, msg, IssueCodes.TYS_UNKNOWN_TYPE_REF);

						issues.add(issue);
					}
				}
			}
		}
	}

	@Override
	public List<Issue> validate(Resource resource, CheckMode mode, CancelIndicator cancelIndicator) {
		return n4jsCache.getOrUpdateIssues(this::doValidate, resource, mode, cancelIndicator);
	}

	/**
	 * Don't validate the inferred module since all validation information should be available on the AST elements.
	 */
	@Override
	protected void validate(Resource resource, CheckMode mode, CancelIndicator cancelIndicator,
			IAcceptor<Issue> acceptor) {

		ResourceType resourceType = ResourceType.getResourceType(resource);
		if (resourceType == ResourceType.DTS) {
			// disable these validations in dts files
			return;
		}

		operationCanceledManager.checkCanceled(cancelIndicator);
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

	private boolean isValidFileTypeForProjectType(Resource resource, N4JSProjectConfigSnapshot project) {
		final ResourceType resourceType = ResourceType.getResourceType(resource);
		final ProjectType projectType = project.getType();
		if (resourceType == ResourceType.JS
				|| resourceType == ResourceType.JSX
				|| resourceType == ResourceType.N4JS
				|| resourceType == ResourceType.N4JSX) {
			// we have a .js or .n4js file ...
			if (projectType == ProjectType.RUNTIME_LIBRARY
					|| projectType == ProjectType.DEFINITION) {
				// in a project of type 'definition' or 'runtime library'
				// --> invalid!
				return false;
			}
		}
		return true;
	}

	private static Issue createInvalidFileTypeError(Resource res, N4JSProjectConfigSnapshot project) {
		final String projectTypeStr = PackageJsonUtils.getProjectTypeStringRepresentation(project.getType());
		final String msg = IssueCodes.getMessageForINVALID_FILE_TYPE_FOR_PROJECT_TYPE(projectTypeStr);
		return createFileIssue(res, msg, IssueCodes.INVALID_FILE_TYPE_FOR_PROJECT_TYPE);
	}

	private static Issue createPostProcessingFailedError(Resource res, Throwable th) {
		final String thKind = th instanceof Error ? "error" : (th instanceof Exception ? "exception" : "throwable");
		final String thName = th.getClass().getSimpleName();
		final String trace = "\n" + Stream.of(th.getStackTrace())
				.map(ste -> ste.toString())
				.collect(Collectors.joining("\n")); // cannot add indentation, because Xtext would reformat the message
		final String msg = IssueCodes.getMessageForPOST_PROCESSING_FAILED(thKind, thName, th.getMessage() + trace);
		return createFileIssue(res, msg, IssueCodes.POST_PROCESSING_FAILED);
	}

	private static Issue createFileIssue(Resource res, String message, String issueCode) {
		return createFileIssue(res, null, message, issueCode);
	}

	private static Issue createFileIssue(Resource res, TypableElement elem, String message, String issueCode) {
		URI uri = res.getURI();
		final IssueImpl issue = new IssueImpl();
		issue.setCode(issueCode);
		issue.setSeverity(IssueCodes.getDefaultSeverity(issueCode));
		issue.setMessage(message);
		issue.setUriToProblem(URIUtils.getBaseOfVirtualResourceURI(uri));
		issue.setType(CheckType.FAST); // using CheckType.FAST is important to get proper marker update behavior in ...
		// ... the editor between persisted and dirty states!
		issue.setOffset(1);
		issue.setLength(1);
		issue.setLineNumber(1);
		issue.setLineNumberEnd(1);
		issue.setColumn(1);
		issue.setColumnEnd(1);

		if (elem != null) {
			if (N4JSGlobals.DTS_FILE_EXTENSION.equals(URIUtils.fileExtension(uri))) {
				for (EObject elem2 = elem; elem2 != null; elem2 = elem2.eContainer()) {
					for (Adapter adapter : elem2.eAdapters()) {
						if (adapter instanceof XITextRegionWithLineInformation) {
							XITextRegionWithLineInformation nodeInfo = (XITextRegionWithLineInformation) adapter;
							issue.setOffset(nodeInfo.getOffset());
							issue.setLength(nodeInfo.getLength());
							issue.setLineNumber(nodeInfo.getLineNumber());
							issue.setLineNumberEnd(nodeInfo.getEndLineNumber());
							issue.setColumn(nodeInfo.getCharacter());
							issue.setColumnEnd(nodeInfo.getEndCharacter());
						}
					}
				}
			} else {
				ICompositeNode node = NodeModelUtils.findActualNodeFor(elem);
				if (node != null) {
					issue.setOffset(node.getOffset());
					issue.setLength(node.getLength());
					issue.setLineNumber(node.getStartLine());
					issue.setLineNumberEnd(node.getEndLine());
					LineAndColumn lineAndColumn = NodeModelUtils.getLineAndColumn(node, node.getOffset());
					if (lineAndColumn != null) {
						issue.setColumn(lineAndColumn.getColumn());
						issue.setColumnEnd(lineAndColumn.getColumn() + 1);
					}
				}
			}
		}
		return issue;
	}
}
