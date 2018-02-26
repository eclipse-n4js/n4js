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
package org.eclipse.n4js.jsdoc2spec.ui.adoc;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.n4js.jsdoc2spec.SpecFile;
import org.eclipse.n4js.jsdoc2spec.adoc.JSDoc2ADocSpecProcessor;
import org.eclipse.n4js.jsdoc2spec.ui.ComparePageVisibilityListener;
import org.eclipse.n4js.jsdoc2spec.ui.SpecChangeSetProvider;
import org.eclipse.n4js.jsdoc2spec.ui.SpecComparePage;
import org.eclipse.n4js.jsdoc2spec.ui.SpecExportCodeSummaryPage;
import org.eclipse.n4js.jsdoc2spec.ui.SpecPage.VisibilityChangedListener;
import org.eclipse.n4js.jsdoc2spec.ui.SpecProcessPage;
import org.eclipse.n4js.jsdoc2spec.ui.SummaryPageVisibilityListener;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;

/**
 * Wizard for exporting specification JSDoc to AsciiDoc. This wizard mainly defines its pages, their order, and it is
 * the main entry for invoking the adoc or html computation tasks, and the adoc file write task.
 */
@SuppressWarnings("restriction")
public class ADocSpecExportWizard extends Wizard implements IExportWizard, SpecChangeSetProvider {
	static final String WIZARD_NAME = "AdocSpecExportWizard";

	@Inject
	private JSDoc2ADocSpecProcessor jsDoc2SpecProcessor;
	@Inject
	private IResourceSetProvider resourceSetProvider;
	@Inject
	private IN4JSCore n4JSCore;

	private SpecConfigAdocPage configAdocPage;
	private SpecProcessPage processAdocPage;
	private SpecComparePage comparePage;
	private SpecExportCodeSummaryPage summaryPage;
	private SpecProcessPage processOutputPage;

	private IStructuredSelection selection;

	private ConfigAdoc configAdoc;

	private TaskGenerateAdoc taskGenAdoc;
	private TaskWriteFiles taskWriteFiles;

	/**
	 * Default constructor
	 */
	public ADocSpecExportWizard() {
		IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault().getDialogSettings();
		IDialogSettings section = workbenchSettings.getSection(WIZARD_NAME);// $NON-NLS-1$
		if (section == null) {
			section = workbenchSettings.addNewSection(WIZARD_NAME);// $NON-NLS-1$
		}
		setDialogSettings(section);
	}

	@Override
	public boolean performFinish() {
		return true;
	}

	@Override
	public void init(IWorkbench targetWorkbench, IStructuredSelection currentSelection) {
		this.selection = currentSelection;

		List<?> selectedResources = IDE.computeSelectedResources(currentSelection);
		if (!selectedResources.isEmpty()) {
			this.selection = new StructuredSelection(selectedResources);
		}

		setWindowTitle("AsciiDoc Specification Export");
		setNeedsProgressMonitor(true);

		configAdocPage = new SpecConfigAdocPage("Configuration Page");
		processAdocPage = new SpecProcessPage("Process Page");
		comparePage = new SpecComparePage("Compare Page", "Adoc");
		summaryPage = new SpecExportCodeSummaryPage("Summary Page");
		processOutputPage = new SpecProcessPage("Process Page");

		taskGenAdoc = new TaskGenerateAdoc(jsDoc2SpecProcessor, resourceSetProvider, n4JSCore, selection,
				configAdocPage, processAdocPage);
		taskWriteFiles = new TaskWriteFiles(processOutputPage, taskGenAdoc);

		addVisibilityListeners();
	}

	private void addVisibilityListeners() {
		processAdocPage.setChangeListener(new VisibilityChangedListener() {
			boolean tasksPerformed = false;

			@Override
			public void isVisibleChanged(boolean visible) {
				if (visible && !tasksPerformed) {
					performAdocTasks();
					tasksPerformed = true;
				}
			}
		});
		comparePage.setChangeListener(new ComparePageVisibilityListener(this, comparePage));
		summaryPage.setChangeListener(new SummaryPageVisibilityListener(this, summaryPage));
		processOutputPage.setChangeListener(new VisibilityChangedListener() {
			boolean tasksPerformed = false;

			@Override
			public void isVisibleChanged(boolean visible) {
				if (visible && !tasksPerformed) {
					performWriteFileTasks();
					tasksPerformed = true;
					processOutputPage.displayMessage("done.");
				}
			}
		});
	}

	@Override
	public void addPages() {
		addPage(configAdocPage);
		addPage(processAdocPage);
		addPage(comparePage);
		addPage(summaryPage);
		addPage(processOutputPage);
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if (page == configAdocPage) {
			return processAdocPage;
		}
		if (page == processAdocPage) {
			if (getSpecChangeSet() == null || getSpecChangeSet().isEmpty())
				return processOutputPage;
			return comparePage;
		}
		if (page == comparePage) {
			return summaryPage;
		}
		if (page == summaryPage) {
			return processOutputPage;
		}
		if (page == processOutputPage) {
			return null;
		}
		throw new RuntimeException("Missing page successor.");
	}

	@Override
	public IWizardPage getPreviousPage(IWizardPage page) {
		if (page == configAdocPage) {
			return null;
		}
		if (page == processAdocPage) {
			return configAdocPage;
		}
		if (page == comparePage) {
			return processAdocPage;
		}
		if (page == summaryPage) {
			return comparePage;
		}
		if (page == processOutputPage) {
			return null;
		}
		throw new RuntimeException("Missing page predecessor.");
	}

	@Override
	public Set<SpecFile> getSpecChangeSet() {
		return taskGenAdoc.getSpecChangeSet();
	}

	@Override
	public boolean needsProgressMonitor() {
		return true;
	}

	@Override
	public boolean canFinish() {
		IWizardPage curPage = getContainer().getCurrentPage();
		if (curPage == processOutputPage)
			return true;
		return false;
	}

	void performAdocTasks() {
		try {
			configAdoc = configAdocPage.getConfig();
			taskGenAdoc.setConfig(configAdoc);
			getContainer().run(true, true, taskGenAdoc);
		} catch (InvocationTargetException | InterruptedException e) {
			processAdocPage.setErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	void performWriteFileTasks() {
		try {
			configAdoc = configAdocPage.getConfig();
			taskWriteFiles.setConfig(configAdoc);
			getContainer().run(true, true, taskWriteFiles);
		} catch (InvocationTargetException | InterruptedException e) {
			processOutputPage.setErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}

}
