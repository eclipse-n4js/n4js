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
package org.eclipse.n4js.ui.quickfix.packagejson;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.n4js.external.ExternalIndexSynchronizer;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.locations.PlatformResourceURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.semver.SemverHelper;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
import org.eclipse.n4js.ui.changes.IChange;
import org.eclipse.n4js.ui.changes.PackageJsonChangeProvider;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.ui.quickfix.AbstractN4JSQuickfixProvider;
import org.eclipse.n4js.ui.quickfix.N4Modification;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.StatusUtils;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;

import com.google.common.base.Strings;
import com.google.inject.Inject;

/**
 * Contains all quick-fixes for package.json documents
 */
public class N4JSPackageJsonQuickfixProviderExtension extends AbstractN4JSQuickfixProvider {

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private LibraryManager libraryManager;

	@Inject
	private ExternalIndexSynchronizer indexSynchronizer;

	@Inject
	private SemverHelper semverHelper;

	@Inject
	private StatusHelper statusHelper;

	/** Installs a specific npm */
	@Fix(IssueCodes.NON_EXISTING_PROJECT)
	@Fix(IssueCodes.NO_MATCHING_VERSION)
	@Fix(IssueCodes.MISSING_YARN_WORKSPACE)
	public void installMissingNPM(Issue issue, IssueResolutionAcceptor acceptor) {
		final String[] userData = issue.getData();
		final String packageName = userData[0];
		final String versionRequirement = userData[1];
		final String msgAtVersion = Strings.isNullOrEmpty(versionRequirement) ? "" : "@" + versionRequirement;
		final String label = "Install npm package " + packageName + msgAtVersion;
		final String description = "Calls npm/yarn to install the missing npm package into the workspace.";
		final String errMsg = "Error while uninstalling npm dependency: '" + packageName + "'.";

		N4Modification modification = new N4Modification() {
			@Override
			public boolean supportsMultiApply() {
				return true;
			}

			@Override
			public boolean isApplicableTo(IMarker marker) {
				return true;
			}

			@Override
			public Collection<? extends IChange> computeChanges(IModificationContext context, final IMarker marker,
					int offset, int length, EObject element) throws Exception {

				Function<IProgressMonitor, IStatus> registerFunction = new Function<>() {
					@Override
					public IStatus apply(IProgressMonitor monitor) {
						final URI uri = issue.getUriToProblem();
						final IN4JSProject containingProject = n4jsCore.findProject(uri).orNull();
						if (containingProject == null) {
							return statusHelper.createError("cannot find containing project");
						}
						FileURI targetLocation = containingProject.getLocation().toFileURI();
						Map<N4JSProjectName, NPMVersionRequirement> installedNpms = new HashMap<>();
						NPMVersionRequirement versionReq = semverHelper.parse(versionRequirement);
						N4JSProjectName typesafePackageName = new N4JSProjectName(packageName);
						installedNpms.put(typesafePackageName, versionReq);
						return libraryManager.installNPM(typesafePackageName, versionRequirement, targetLocation,
								monitor);
					}
				};
				wrapWithMonitor(label, errMsg, registerFunction);
				return Collections.emptyList();
			}
		};

		accept(acceptor, issue, label, description, null, modification);
	}

	/** Runs 'npm/yarn install' on a single project. Afterwards, re-registers external libraries. */
	@Fix(IssueCodes.NON_EXISTING_PROJECT)
	@Fix(IssueCodes.NO_MATCHING_VERSION)
	@Fix(IssueCodes.MISSING_YARN_WORKSPACE)
	public void runNpmInstallInProject(Issue issue, IssueResolutionAcceptor acceptor) {
		final String label = "Run 'npm/yarn install' in this project";
		final String description = "Runs 'npm/yarn install' on this project and then registers all npms.";
		final String errMsg = "Error while installing npms";

		N4Modification modification = new N4Modification() {
			@Override
			public boolean supportsMultiApply() {
				return false;
			}

			@Override
			public boolean isApplicableTo(IMarker marker) {
				return true;
			}

			@Override
			public Collection<? extends IChange> computeChanges(IModificationContext context, IMarker marker,
					int offset, int length, EObject element) throws Exception {

				Function<IProgressMonitor, IStatus> registerFunction = new Function<>() {
					@Override
					public IStatus apply(IProgressMonitor monitor) {
						return libraryManager.runNpmYarnInstall(new PlatformResourceURI(issue.getUriToProblem()),
								monitor);
					}
				};
				wrapWithMonitor(label, errMsg, registerFunction);
				return Collections.emptyList();
			}
		};

		accept(acceptor, issue, label, description, null, modification);
	}

	/** Runs 'npm/yarn install' on all projects. Afterwards, re-registers external libraries. */
	@Fix(IssueCodes.NON_EXISTING_PROJECT)
	@Fix(IssueCodes.NO_MATCHING_VERSION)
	public void runNpmInstallInAllProjects(Issue issue, IssueResolutionAcceptor acceptor) {
		final String label = "Run 'npm/yarn install' in all projects";
		final String description = "Runs 'npm/yarn install' on all projects sequentially and then registers all npms.";
		final String errMsg = "Error while installing npms";

		N4Modification modification = new N4Modification() {
			@Override
			public boolean supportsMultiApply() {
				return false;
			}

			@Override
			public boolean isApplicableTo(IMarker marker) {
				return true;
			}

			@Override
			public Collection<? extends IChange> computeChanges(IModificationContext context, IMarker marker,
					int offset, int length, EObject element) throws Exception {

				Function<IProgressMonitor, IStatus> registerFunction = new Function<>() {
					@Override
					public IStatus apply(IProgressMonitor monitor) {
						return libraryManager.runNpmYarnInstallOnAllProjects(monitor);
					}
				};
				wrapWithMonitor(label, errMsg, registerFunction);
				return Collections.emptyList();
			}
		};

		accept(acceptor, issue, label, description, null, modification);
	}

	/** Registers a specific npm */
	@Fix(IssueCodes.NON_REGISTERED_PROJECT)
	public void registerNPMs(Issue issue, IssueResolutionAcceptor acceptor) {
		final String label = "Register npm(s)";
		final String description = "Registers all not registered npms so that they can be imported by modules.";

		N4Modification modification = new N4Modification() {
			@Override
			public boolean supportsMultiApply() {
				return true;
			}

			@Override
			public boolean isApplicableTo(IMarker marker) {
				return true;
			}

			@Override
			public Collection<? extends IChange> computeChanges(IModificationContext context, IMarker marker,
					int offset, int length, EObject element) throws Exception {

				Function<IProgressMonitor, IStatus> registerFunction = new Function<>() {
					@Override
					public IStatus apply(IProgressMonitor monitor) {
						indexSynchronizer.synchronizeNpms(monitor);
						return statusHelper.OK();
					}
				};
				wrapWithMonitor(label, "Error during registering of npm(s)", registerFunction);
				return Collections.emptyList();
			}
		};

		accept(acceptor, issue, label, description, null, modification);
	}

	/** Changes the project type to {@link ProjectType#VALIDATION} */
	@Fix(IssueCodes.OUTPUT_AND_SOURCES_FOLDER_NESTING)
	public void changeProjectTypeToValidation(Issue issue, IssueResolutionAcceptor acceptor) {
		String validationPT = ProjectType.VALIDATION.getName().toLowerCase();
		String title = "Change project type to '" + validationPT + "'";
		String descr = "The project type '" + validationPT
				+ "' does not generate code. Hence, output and source folders can be nested.";

		accept(acceptor, issue, title, descr, null, new N4Modification() {
			@Override
			public Collection<? extends IChange> computeChanges(IModificationContext context, IMarker marker,
					int offset, int length, EObject element) throws Exception {

				Resource resource = element.eResource();
				ProjectDescription prjDescr = EcoreUtil2.getContainerOfType(element, ProjectDescription.class);
				Collection<IChange> changes = new LinkedList<>();
				changes.add(PackageJsonChangeProvider.setProjectType(resource, ProjectType.VALIDATION, prjDescr));
				return changes;
			}

			@Override
			public boolean supportsMultiApply() {
				return false;
			}

			@Override
			public boolean isApplicableTo(IMarker marker) {
				return true;
			}
		});
	}

	private Collection<? extends IChange> wrapWithMonitor(String msg, String errMsg,
			Function<IProgressMonitor, IStatus> f) throws Exception {

		MultiStatus multiStatus = statusHelper.createMultiStatus(msg);
		new ProgressMonitorDialog(UIUtils.getShell()).run(true, false, (monitor) -> {
			try {
				IStatus status = f.apply(monitor);
				multiStatus.merge(status);
			} catch (Exception e) {
				multiStatus.merge(statusHelper.createError(errMsg, e));
			}
		});

		if (!multiStatus.isOK()) {
			N4JSActivator.getInstance().getLog().log(multiStatus);
			UIUtils.getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					String title = "Failed: " + msg;
					String descr = StatusUtils.getErrorMessage(multiStatus, true);
					ErrorDialog.openError(UIUtils.getShell(), title, descr, multiStatus);
				}
			});
		}

		return Collections.emptyList();
	}
}
