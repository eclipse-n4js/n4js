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

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectType;
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
	private LibraryManager libraryManager;

	@Inject
	private StatusHelper statusHelper;

	/** Installs a specific npm */
	@Fix(IssueCodes.NON_EXISTING_PROJECT)
	public void installMissingNPM(Issue issue, IssueResolutionAcceptor acceptor) {
		final String[] userData = issue.getData();
		final String packageName = userData[0];
		final String versionRequirement = userData[1];
		final String msgAtVersion = Strings.isNullOrEmpty(versionRequirement) ? "" : "@" + versionRequirement;
		final String label = "Install npm " + packageName + msgAtVersion;
		final String description = "Calls npm to install the missing npm package into the workspace.";

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

				return invokeNpmManager(packageName, versionRequirement);
			}
		};

		accept(acceptor, issue, label, description, "SomeImage.gif", modification);
	}

	private Collection<? extends IChange> invokeNpmManager(String packageName, String versionRequirement)
			throws Exception {

		MultiStatus multiStatus = statusHelper.createMultiStatus("Installing npm '" + packageName + "'.");

		new ProgressMonitorDialog(UIUtils.getShell()).run(true, false, new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				try {
					multiStatus.merge(libraryManager.installNPM(packageName, versionRequirement, monitor));
				} catch (Exception e) {
					String msg = "Error while uninstalling npm dependency: '" + packageName + "'.";
					multiStatus.merge(statusHelper.createError(msg, e));
				}
			}
		});

		if (!multiStatus.isOK()) {
			N4JSActivator.getInstance().getLog().log(multiStatus);
			UIUtils.getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					String title = "NPM Install Failed";
					String descr = StatusUtils.getErrorMessage(multiStatus, true);
					ErrorDialog.openError(UIUtils.getShell(), title, descr, multiStatus);
				}
			});
		}

		return Collections.emptyList();
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
}
