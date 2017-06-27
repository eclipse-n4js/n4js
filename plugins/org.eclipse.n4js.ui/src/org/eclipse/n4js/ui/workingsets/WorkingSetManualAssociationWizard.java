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
package org.eclipse.n4js.ui.workingsets;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Sets.newTreeSet;
import static java.util.Collections.singletonList;
import static org.eclipse.jface.layout.GridDataFactory.fillDefaults;
import static org.eclipse.swt.SWT.BORDER;
import static org.eclipse.swt.SWT.CENTER;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.PUSH;
import static org.eclipse.swt.SWT.SHADOW_IN;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.google.common.base.Optional;
import com.google.common.primitives.Ints;
import com.google.inject.Inject;

import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;
import org.eclipse.n4js.ui.navigator.N4JSProjectExplorerLabelProvider;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.ui.viewer.TableViewerBuilder;
import org.eclipse.n4js.ui.workingsets.ManualAssociationAwareWorkingSetManager.ManualAssociationWorkingSet;
import org.eclipse.n4js.utils.collections.Arrays2;

/**
 * Wizard for creating and editing working sets based on manual associations between working sets and workspace
 * projects.
 */
public class WorkingSetManualAssociationWizard extends WorkingSetEditWizard {

	private static final String TITLE = "Manual Association Working Set";
	private static final String DESCRIPTION = "Enter a working set name and select projects that have to be associated with the working set.";

	private static final Point SHELL_SIZE = new Point(600, 650);

	private static final Comparator<IProject> PROJECT_NAME_COMPARATOR = (left, right) -> {
		if (left == null) {
			return right == null ? 0 : 1;
		}
		if (right == null) {
			return -1;
		}
		return left.getName().compareTo(right.getName());
	};

	private final AtomicReference<WorkingSet> workingSetRef = new AtomicReference<>();
	private final AtomicReference<String> originalName = new AtomicReference<>();

	@Inject
	private N4JSProjectExplorerLabelProvider labelProvider;

	@Override
	public void addPages() {
		addPage(new WizardPage("") {

			private Text nameText;
			private final Collection<IProject> workspaceProjects = newTreeSet(PROJECT_NAME_COMPARATOR);
			private final Collection<IProject> workingSetProjects = newTreeSet(PROJECT_NAME_COMPARATOR);

			@Override
			public void createControl(Composite parent) {

				final Composite composite = new Composite(parent, NONE);
				composite.setLayout(GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).create());
				composite.setLayoutData(fillDefaults().align(FILL, FILL).grab(true, true).create());

				new Label(composite, NONE).setText("Working set name:");
				nameText = new Text(composite, BORDER);
				nameText.setLayoutData(fillDefaults().align(FILL, CENTER).grab(true, false).create());

				Composite tableComposite = new Composite(composite, NONE);
				tableComposite.setLayout(GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).create());
				tableComposite.setLayoutData(fillDefaults().align(FILL, FILL).grab(true, true).span(2, 1).create());

				Group workspaceGroup = new Group(tableComposite, SHADOW_IN);
				workspaceGroup.setText("Available workspace projects");
				workspaceGroup.setLayout(GridLayoutFactory.fillDefaults().create());
				workspaceGroup.setLayoutData(fillDefaults().align(FILL, FILL).grab(true, true).create());

				final TableViewer allProjectsViewer = new TableViewerBuilder(singletonList(""))
						.setHasBorder(true)
						.setHeaderVisible(false)
						.setLinesVisible(false)
						.setMultipleSelection(true)
						.setColumnWidthsInPixel(Ints.asList(350))
						.setLabelProvider(labelProvider)
						.build(workspaceGroup);

				Composite buttonComposite = new Composite(tableComposite, NONE);
				buttonComposite.setLayout(GridLayoutFactory.fillDefaults().create());
				// buttonComposite.setLayoutData(fillDefaults().align(CENTER, CENTER).grab(false, false).create());

				final Button addButton = new Button(buttonComposite, PUSH);
				addButton.setImage(ImageRef.RIGHT_ARROW.asImage().orNull());
				addButton.setToolTipText("Add all selected workspace projects");
				addButton.setEnabled(false);

				final Button removeButton = new Button(buttonComposite, PUSH);
				removeButton.setImage(ImageRef.LEFT_ARROW.asImage().orNull());
				removeButton.setToolTipText("Remove all selected working set element projects");
				removeButton.setEnabled(false);

				Group workingSetGroup = new Group(tableComposite, SHADOW_IN);
				workingSetGroup.setText("Associated working set projects");
				workingSetGroup.setLayout(GridLayoutFactory.fillDefaults().create());
				workingSetGroup.setLayoutData(fillDefaults().align(FILL, FILL).grab(true, true).create());

				final TableViewer associatedProjectsViewer = new TableViewerBuilder(singletonList(""))
						.setHasBorder(true)
						.setHeaderVisible(false)
						.setLinesVisible(false)
						.setMultipleSelection(true)
						.setColumnWidthsInPixel(Ints.asList(350))
						.setLabelProvider(labelProvider)
						.build(workingSetGroup);

				addButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						IStructuredSelection selection = allProjectsViewer.getStructuredSelection();
						if (selection != null && !selection.isEmpty()) {
							final IProject[] projects = Arrays2.filter(selection.toArray(), IProject.class);
							allProjectsViewer.remove(projects);
							associatedProjectsViewer.add(projects);
							workspaceProjects.removeAll(Arrays.asList(projects));
							workingSetProjects.addAll(Arrays.asList(projects));
							setPageComplete(validatePage());
						}
					}
				});

				removeButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						IStructuredSelection selection = associatedProjectsViewer.getStructuredSelection();
						if (selection != null && !selection.isEmpty()) {
							final IProject[] projects = Arrays2.filter(selection.toArray(), IProject.class);
							associatedProjectsViewer.remove(projects);
							allProjectsViewer.add(projects);
							workingSetProjects.removeAll(Arrays.asList(projects));
							workspaceProjects.addAll(Arrays.asList(projects));
							setPageComplete(validatePage());
						}
					}
				});

				associatedProjectsViewer.addSelectionChangedListener(event -> {
					final IStructuredSelection selection = associatedProjectsViewer.getStructuredSelection();
					removeButton.setEnabled(null != selection && !selection.isEmpty());
				});

				allProjectsViewer.addSelectionChangedListener(event -> {
					final IStructuredSelection selection = allProjectsViewer.getStructuredSelection();
					addButton.setEnabled(null != selection && !selection.isEmpty());
				});

				setPageComplete(false);
				setControl(composite);

				final Optional<WorkingSet> editedWorkingSet = getEditedWorkingSet();
				workspaceProjects.addAll(Arrays.asList(ResourcesPlugin.getWorkspace().getRoot().getProjects()));
				if (editedWorkingSet.isPresent()) {
					final ManualAssociationWorkingSet workingSet = (ManualAssociationWorkingSet) editedWorkingSet.get();
					workingSetRef.set(workingSet);
					nameText.setText(workingSet.getName());
					nameText.selectAll();
					workingSetProjects.addAll(workingSet.getAssociatedProjects());
					workspaceProjects.removeAll(workingSetProjects);
					originalName.set(workingSet.getName());
				}

				composite.getDisplay().asyncExec(() -> {
					setTitle(TITLE);
					setDescription(DESCRIPTION);
					allProjectsViewer.setInput(workspaceProjects);
					associatedProjectsViewer.setInput(workingSetProjects);
				});

				nameText.addModifyListener(e -> setPageComplete(validatePage()));

			}

			@Override
			public void setVisible(boolean visible) {
				if (visible) {
					Rectangle location = UIUtils.getConstrainedShellBounds(getShell(), SHELL_SIZE);
					getShell().setBounds(location);
				}
				super.setVisible(visible);
			}

			@SuppressWarnings("null")
			private boolean validatePage() {

				String errorMessage = null;

				final String name = nameText.getText();
				final WorkingSetManager manager = getManager();

				if (manager == null) {
					errorMessage = "No active working set manager is available.";
				}

				if (errorMessage == null) {
					if (name == null || name.trim().length() == 0) {
						errorMessage = "Working set name should be specified.";
					}
				}

				if (errorMessage == null) {
					if (!name.equals(originalName.get())
							// This case ID and name are equal. Intentionally name.
							&& Arrays2.transform(manager.getAllWorkingSets(), ws -> ws.getName()).contains(name)) {
						errorMessage = "A working set already exists with name '" + name + "'.";
					}
				}

				if (errorMessage != null) {
					workingSetRef.set(null);
				} else {
					final Iterable<String> projectNames = from(workingSetProjects).transform(p -> p.getName());
					workingSetRef.set(new ManualAssociationWorkingSet(projectNames, name, manager));
				}

				setMessage(errorMessage, ERROR);
				return errorMessage == null;
			}

		});
	}

	@Override
	public Optional<WorkingSet> getWorkingSet() {
		return Optional.fromNullable(workingSetRef.get());
	}

	@Override
	public boolean performFinish() {
		return null != getWorkingSet();
	}

}
