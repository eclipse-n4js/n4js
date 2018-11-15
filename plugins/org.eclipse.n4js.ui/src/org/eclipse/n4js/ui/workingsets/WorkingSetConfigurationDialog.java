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
import static com.google.common.collect.Lists.newArrayList;
import static org.eclipse.n4js.ui.workingsets.WorkingSet.OTHERS_WORKING_SET_ID;
import static org.eclipse.n4js.utils.collections.Arrays2.isEmpty;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.utils.Diff;
import org.eclipse.n4js.utils.collections.Arrays2;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 * Dialog for configuring the currently active working set for the navigator.
 */
public class WorkingSetConfigurationDialog extends SelectionDialog {

	private final WorkingSetManager manager;
	private final WorkingSetDiffBuilder diffBuilder;
	private final AtomicReference<Diff<WorkingSet>> diff;

	private List<WorkingSet> allWorkingSets;
	private CheckboxTableViewer tableViewer;

	private Button newButton;
	private Button editButton;
	private Button removeButton;
	private Button upButton;
	private Button downButton;
	private Button selectAllButton;
	private Button deselectAllButton;

	private int nextButtonId = IDialogConstants.CLIENT_ID + 1;

	/**
	 * Creates a new dialog for the configuration of the working set manager argument.
	 *
	 * @param manager
	 *            the manager that has to be configured.
	 */
	public WorkingSetConfigurationDialog(final WorkingSetManager manager) {
		super(UIUtils.getShell());
		this.manager = manager;
		setTitle("Configure Working Sets");
		setMessage("Select and sort &working sets visible in Project Explorer:");
		diffBuilder = new WorkingSetDiffBuilder(manager);
		diff = new AtomicReference<>(WorkingSetDiffBuilder.EMPTY_DIFF);
		allWorkingSets = newArrayList(manager.getAllWorkingSets());

		WorkingSet[] workingSetsToSelect = this.manager.getWorkingSets();
		Object[] workingSetsToSelectCpy = Arrays.copyOf(workingSetsToSelect, workingSetsToSelect.length,
				Object[].class);
		setInitialSelections(workingSetsToSelectCpy);
	}

	@Override
	protected Control createContents(final Composite parent) {
		final Control control = super.createContents(parent);
		setInitialSelection();
		updateButtonAvailability();
		return control;
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite composite = (Composite) super.createDialogArea(parent);

		createMessageArea(composite);
		final Composite inner = new Composite(composite, SWT.NONE);
		inner.setLayoutData(new GridData(GridData.FILL_BOTH));
		final GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		inner.setLayout(layout);
		createTableViewer(inner);
		createOrderButtons(inner);
		// New, Edit and Remove is allowed only for mutable managers.
		if (manager instanceof MutableWorkingSetManager) {
			createModifyButtons(composite);
		}
		tableViewer.setInput(allWorkingSets);
		applyDialogFont(composite);

		return composite;
	}

	@Override
	protected void okPressed() {

		final WorkingSet[] newItems = Arrays2.filter(tableViewer.getCheckedElements(), WorkingSet.class);
		final WorkingSet[] newAllItems = from(Arrays.asList(tableViewer.getTable().getItems()))
				.transform(item -> item.getData()).filter(WorkingSet.class).toArray(WorkingSet.class);
		diff.set(diffBuilder.build(newItems, newAllItems));

		manager.updateState(diff.get());
		manager.saveState(new NullProgressMonitor());

		super.okPressed();
	}

	private void createTableViewer(final Composite parent) {
		tableViewer = CheckboxTableViewer.newCheckList(parent, SWT.BORDER | SWT.MULTI);
		tableViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(final CheckStateChangedEvent event) {
				syncSelectionAndButtonStates();
			}
		});
		final GridData data = new GridData(GridData.FILL_BOTH);
		data.heightHint = convertHeightInCharsToPixels(20);
		data.widthHint = convertWidthInCharsToPixels(50);
		tableViewer.getTable().setLayoutData(data);

		tableViewer.setLabelProvider(new DelegatingStyledCellLabelProvider(WorkingSetLabelProvider.INSTANCE));
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
				handleSelectionChanged();
			}
		});
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(final DoubleClickEvent event) {
				if (editButton != null && editButton.isEnabled()) {
					editSelectedWorkingSet();
				}
			}
		});
	}

	private void createModifyButtons(final Composite composite) {
		final Composite buttonComposite = new Composite(composite, SWT.RIGHT);
		final GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		buttonComposite.setLayout(layout);
		final GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.GRAB_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		composite.setData(data);

		newButton = createButton(buttonComposite, nextButtonId++,
				"New...", false);
		newButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				createWorkingSet();
			}
		});

		editButton = createButton(buttonComposite, nextButtonId++,
				"Edit...", false);
		editButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				editSelectedWorkingSet();
			}
		});

		removeButton = createButton(buttonComposite, nextButtonId++,
				"Remove", false);
		removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				removeSelectedWorkingSets();
			}
		});
	}

	private void createOrderButtons(final Composite parent) {
		final Composite buttons = new Composite(parent, SWT.NONE);
		buttons.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		final GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		buttons.setLayout(layout);

		upButton = new Button(buttons, SWT.PUSH);
		upButton.setText("Up");
		setButtonLayoutData(upButton);
		upButton.addSelectionListener(new SelectionAdapter() {
			@Override
			@SuppressWarnings("unchecked")
			public void widgetSelected(final SelectionEvent e) {
				moveUp(((IStructuredSelection) tableViewer.getSelection()).toList());
			}
		});

		downButton = new Button(buttons, SWT.PUSH);
		downButton.setText("Down");
		setButtonLayoutData(downButton);
		downButton.addSelectionListener(new SelectionAdapter() {
			@Override
			@SuppressWarnings("unchecked")
			public void widgetSelected(final SelectionEvent e) {
				moveDown(((IStructuredSelection) tableViewer.getSelection()).toList());
			}
		});

		selectAllButton = new Button(buttons, SWT.PUSH);
		selectAllButton.setText("Select All");
		setButtonLayoutData(selectAllButton);
		selectAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				selectAll();
			}
		});

		deselectAllButton = new Button(buttons, SWT.PUSH);
		deselectAllButton.setText("Deselect All");
		setButtonLayoutData(deselectAllButton);
		deselectAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				deselectAll();
			}
		});
	}

	private void setInitialSelection() {
		@SuppressWarnings("unchecked")
		final List<Object[]> selections = getInitialElementSelections();
		if (!selections.isEmpty()) {
			tableViewer.setCheckedElements(selections.toArray());
		}
	}

	private void createWorkingSet() {
		if (manager instanceof MutableWorkingSetManager) {

			final WorkingSetNewWizard wizard = ((MutableWorkingSetManager) manager).createNewWizard();
			// set allWorkingSets according to dialog to use it as a base for validation
			wizard.setAllWorkingSets(allWorkingSets);

			final WizardDialog dialog = new WizardDialog(getShell(), wizard);
			if (dialog.open() == Window.OK) {
				final WorkingSet workingSet = wizard.getWorkingSet().orNull();
				if (workingSet != null) {
					diffBuilder.add(workingSet);
					getShell().getDisplay().asyncExec(new Runnable() {

						@Override
						public void run() {
							allWorkingSets.add(workingSet);
							tableViewer.add(workingSet);
							tableViewer.setChecked(workingSet, true);
						}

					});
				}
			}
		}
	}

	private void editSelectedWorkingSet() {
		if (manager instanceof MutableWorkingSetManager) {
			final IStructuredSelection selection = tableViewer.getStructuredSelection();
			final Object firstElement = selection.getFirstElement();
			if (firstElement instanceof WorkingSet) {
				final WorkingSet oldState = (WorkingSet) firstElement;
				if (!OTHERS_WORKING_SET_ID.equals(oldState.getId())) {
					final WorkingSetEditWizard wizard = ((MutableWorkingSetManager) manager).createEditWizard();

					wizard.init(PlatformUI.getWorkbench(), selection);
					// make sure the wizard validates against changed set of working set of this dialog
					wizard.setAllWorkingSets(this.allWorkingSets);

					final WizardDialog dialog = new WizardDialog(getShell(), wizard);
					if (dialog.open() == Window.OK) {
						final WorkingSet newState = wizard.getWorkingSet().orNull();
						diffBuilder.edit(oldState, newState);
						getShell().getDisplay().asyncExec(new Runnable() {

							@Override
							public void run() {
								int oldStateIndex = allWorkingSets.indexOf(oldState);

								// if the old element is not in the list of all working sets
								if (-1 == oldStateIndex) {
									// abort the edit operation
									return;
								}

								// replace old state with new state in allWorkingSets
								allWorkingSets.remove(oldState);
								allWorkingSets.add(oldStateIndex, newState);

								// replace table viewer element
								tableViewer.replace(newState, oldStateIndex);

								// always check edited working set elements
								tableViewer.setChecked(newState, true);
								// set selection to newState element
								tableViewer.setSelection(new StructuredSelection(newState));
							}

						});
					}
				}
			}
		}
	}

	/**
	 * Called when the selection has changed.
	 */
	private void handleSelectionChanged() {
		updateButtonAvailability();
	}

	/**
	 * Removes the selected working sets from the workbench.
	 */
	private void removeSelectedWorkingSets() {
		if (manager instanceof MutableWorkingSetManager) {

			final IStructuredSelection selection = tableViewer.getStructuredSelection();
			final Object[] objects = selection.toArray();
			final Collection<WorkingSet> removedWorkingSets = newArrayList();

			for (int i = 0, size = objects.length; i < size; i++) {
				final Object object = objects[i];
				if (object instanceof WorkingSet) {
					final WorkingSet workingSet = (WorkingSet) object;
					if (!OTHERS_WORKING_SET_ID.equals(workingSet.getName())) {
						removedWorkingSets.add(workingSet);
					}
				}
			}

			if (!removedWorkingSets.isEmpty()) {
				for (final WorkingSet workingSet : removedWorkingSets) {
					diffBuilder.delete(workingSet);
				}
				getShell().getDisplay().asyncExec(new Runnable() {

					@Override
					public void run() {
						tableViewer.remove(removedWorkingSets.toArray());
						allWorkingSets.removeAll(removedWorkingSets);
					}

				});
			}
		}

	}

	/**
	 * Updates the modify buttons' enabled state based on the current selection.
	 */
	private void updateButtonAvailability() {
		final IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
		final boolean hasSelection = !selection.isEmpty();
		final boolean hasSingleSelection = selection.size() == 1;

		if (manager instanceof MutableWorkingSetManager) {
			removeButton.setEnabled(hasSelection && containsNoBuiltInWorkingSets(selection));
			editButton.setEnabled(hasSingleSelection && containsNoBuiltInWorkingSets(selection));
		}
		if (upButton != null) {
			upButton.setEnabled(canMoveUp());
		}
		if (downButton != null) {
			downButton.setEnabled(canMoveDown());
		}
	}

	private boolean containsNoBuiltInWorkingSets(final IStructuredSelection selection) {
		for (final Iterator<?> itr = selection.iterator(); itr.hasNext();) {
			final Object next = itr.next();
			if (next instanceof WorkingSet) {
				if (OTHERS_WORKING_SET_ID.equals(((WorkingSet) next).getId())) {
					return false;
				}
			}
		}
		return true;
	}

	private void moveUp(final List<WorkingSet> toMoveUp) {
		if (toMoveUp.size() > 0) {
			setElements(moveUp(allWorkingSets, toMoveUp));
			tableViewer.reveal(toMoveUp.get(0));
		}
	}

	private void moveDown(final List<WorkingSet> toMoveDown) {
		if (toMoveDown.size() > 0) {
			setElements(reverse(moveUp(reverse(allWorkingSets), toMoveDown)));
			tableViewer.reveal(toMoveDown.get(toMoveDown.size() - 1));
		}
	}

	private void setElements(final List<WorkingSet> elements) {
		allWorkingSets = elements;
		tableViewer.setInput(allWorkingSets);
		updateButtonAvailability();
	}

	private List<WorkingSet> moveUp(final List<WorkingSet> elements, final List<WorkingSet> move) {
		final int nElements = elements.size();
		final List<WorkingSet> result = newArrayList();
		WorkingSet floating = null;
		for (int i = 0; i < nElements; i++) {
			final WorkingSet curr = elements.get(i);
			if (move.contains(curr)) {
				result.add(curr);
			} else {
				if (floating != null) {
					result.add(floating);
				}
				floating = curr;
			}
		}
		if (floating != null) {
			result.add(floating);
		}
		return result;
	}

	private List<WorkingSet> reverse(final List<WorkingSet> p) {
		final List<WorkingSet> copy = newArrayList(p);
		Collections.reverse(copy);
		return copy;
	}

	private boolean canMoveUp() {
		final int[] indices = tableViewer.getTable().getSelectionIndices();
		for (int i = 0; i < indices.length; i++) {
			if (indices[i] != i) {
				return true;
			}
		}
		return false;
	}

	private boolean canMoveDown() {
		final int[] indices = tableViewer.getTable().getSelectionIndices();
		int k = allWorkingSets.size() - 1;
		for (int i = indices.length - 1; i >= 0; i--, k--) {
			if (indices[i] != k) {
				return true;
			}
		}
		return false;
	}

	private void selectAll() {
		tableViewer.setAllChecked(true);
		syncSelectionAndButtonStates();

	}

	private void deselectAll() {
		tableViewer.setAllChecked(false);
		syncSelectionAndButtonStates();
	}

	private void syncSelectionAndButtonStates() {
		updateButtonAvailability();
		getButton(IDialogConstants.OK_ID).setEnabled(!isEmpty(tableViewer.getCheckedElements()));
	}

}
