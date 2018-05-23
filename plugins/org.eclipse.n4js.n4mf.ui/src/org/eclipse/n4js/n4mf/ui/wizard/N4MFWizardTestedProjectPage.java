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
package org.eclipse.n4js.n4mf.ui.wizard;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;

import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.n4mf.ProjectType;
import org.eclipse.n4js.n4mf.resource.N4MFResourceDescriptionStrategy;

/**
 * This wizard page provides controls for tested projects selection.
 *
 * See {@link N4MFProjectInfo#getTestedProjects()}
 */
public class N4MFWizardTestedProjectPage extends WizardPage {

	private final IResourceDescriptions resourceDescriptions;
	private final N4MFProjectInfo projectInfo;

	/**
	 * Create a new tested project wizard page.
	 *
	 * @param projectInfo
	 *            The N4MFProjectInfo to use as model
	 * @param resourceDescriptions
	 *            A {@link IResourceDescriptions} implementation.
	 */
	public N4MFWizardTestedProjectPage(N4MFProjectInfo projectInfo, IResourceDescriptions resourceDescriptions) {
		super("Select projects to be tested");
		this.resourceDescriptions = resourceDescriptions;
		this.projectInfo = projectInfo;

		this.setTitle("Select projects to be tested");
		this.setMessage("Select projects to be tested in your new test project");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void createControl(Composite parent) {
		Composite listComposite = new Composite(parent, NONE);
		listComposite.setLayout(new FillLayout());

		ListViewer projectListViewer = new ListViewer(listComposite, SWT.BORDER | SWT.MULTI);
		projectListViewer.setContentProvider(ArrayContentProvider.getInstance());
		projectListViewer.setInput(getNonTestProjects());

		// Data binding
		DataBindingContext databindingContext = new DataBindingContext();
		parent.addDisposeListener(e -> databindingContext.dispose());

		databindingContext.bindList(ViewersObservables.observeMultiSelection(projectListViewer),
				PojoProperties.list(N4MFProjectInfo.class, N4MFProjectInfo.TESTED_PROJECT_PROP_NAME)
						.observe(projectInfo));

		setControl(listComposite);
	}

	private boolean isExternal(URI resourceURI) {
		return !resourceURI.isPlatformResource();
	}

	/**
	 * Returns all non-external, non-test typed N4JS projects in the workspace.
	 *
	 * The result is sorted alphabetically.
	 */
	private String[] getNonTestProjects() {

		Stream<IEObjectDescription> projectDescriptions = StreamSupport.stream(resourceDescriptions
				.getExportedObjectsByType(N4mfPackage.eINSTANCE.getProjectDescription()).spliterator(), false);

		// Filter for non-test, non-external, non-null project descriptions and return their id
		return projectDescriptions
				.filter(desc -> {
					ProjectType type = N4MFResourceDescriptionStrategy.getProjectType(desc);
					return type != null && !ProjectType.TEST.equals(type);
				})
				.filter(desc -> !isExternal(desc.getEObjectURI()))
				.map(d -> N4MFResourceDescriptionStrategy.getProjectId(d))
				.sorted()
				.toArray(String[]::new);

	}

}
