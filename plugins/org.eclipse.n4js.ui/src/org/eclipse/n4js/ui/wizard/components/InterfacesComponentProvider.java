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
package org.eclipse.n4js.ui.wizard.components;

import static org.eclipse.n4js.ui.wizard.components.WizardComponentUtils.fillLabelDefaults;

import java.util.ArrayList;

import org.eclipse.core.databinding.beans.typed.BeanProperties;
import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;
import org.eclipse.core.databinding.observable.list.ListDiffEntry;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.n4js.ui.dialog.InterfacesSelectionDialog;
import org.eclipse.n4js.ui.wizard.classifiers.N4JSClassifierWizardModel;
import org.eclipse.n4js.ui.wizard.model.ClassifierReference;
import org.eclipse.n4js.ui.wizard.model.InterfacesContainingModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * A provider for {@link InterfacesComponent} component.
 *
 */
public class InterfacesComponentProvider {

	@Inject
	private Provider<InterfacesSelectionDialog> interfacesSelectionDialogProvider;

	/**
	 * Creates a new InterfacesComponent with the given model and container.
	 *
	 * @param interfacesContainingModel
	 *            The model to bind it to
	 * @param container
	 *            To container to create it in
	 */
	public InterfacesComponent create(InterfacesContainingModel interfacesContainingModel,
			WizardComponentContainer container) {
		return new InterfacesComponent(interfacesContainingModel, container);
	}

	/**
	 * This component provides a list with buttons to add and remove interfaces. The component also provides in-line
	 * cell editing.
	 *
	 * When clicking into empty space of the list, a new in-line interface cell editor can be activated.
	 *
	 */
	public class InterfacesComponent extends WizardComponent {

		/**
		 * A mouse listener for the interfaces table to provide cell editing as well as empty space clicking
		 * functionality
		 */
		private final class InterfacesTableMouseListener implements MouseListener {
			private final TableEditor editor;

			/**
			 * Creates a new InterfacesTableMouseListener.
			 *
			 * @param editor
			 *            The editor of the interfaces table
			 */
			private InterfacesTableMouseListener(TableEditor editor) {
				this.editor = editor;
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// No mouse up action
			}

			@Override
			public void mouseDown(MouseEvent e) {
				if (editor.getItem() != null) {
					if (editor.getItem() != interfacesTable.getItem(new Point(e.x, e.y))) {
						editor.getEditor().dispose();
						interfacesTable.setItemCount(model.getInterfaces().size());
					}
				}
			}

			/**
			 * Creates an editor for the selected item if double clicked.
			 */
			@Override
			public void mouseDoubleClick(MouseEvent e) {

				TableItem item = interfacesTable.getItem(new Point(e.x, e.y));

				// If nothing is selected create a new empty TableItem at the end of the
				// list
				if (item == null) {
					interfacesTable.setItemCount(interfacesTable.getItemCount() + 1);
					item = interfacesTable.getItem(interfacesTable.getItemCount() - 1);
				}

				Control oldEditor = editor.getEditor();
				if (oldEditor != null) {
					oldEditor.dispose();
				}

				Text newEditor = new Text(interfacesTable, SWT.NONE);
				newEditor.setText(item.getText(0));
				newEditor.setToolTipText("Enter the interface specifier");

				// Disable key event propagation to not finish the wizard when pressing enter in the table editor
				newEditor.addTraverseListener(new TraverseListener() {

					@Override
					public void keyTraversed(TraverseEvent event) {
						event.doit = false;

					}
				});

				final int fSelectionIndex = interfacesTable.indexOf(item);

				// If the edited item is an existing item
				if (fSelectionIndex < model.getInterfaces().size()) {
					newEditor.addKeyListener(new KeyListener() {

						@Override
						public void keyReleased(KeyEvent event) {
							if (event.keyCode == SWT.CR) {
								// Copy interfaces list from table to make databinding comparision detect changes
								ArrayList<ClassifierReference> interfaces = new ArrayList<>(model.getInterfaces());
								ClassifierReference ref = interfaces.get(fSelectionIndex);

								if (newEditor.getText().isEmpty()) {
									model.removeInterfaces(ref);
									newEditor.dispose();
									return;
								}

								// Convert input to classifier reference using the converter
								ClassifierReference editedRef = new WizardComponentDataConverters.StringToClassifierReferenceConverter()
										.convert(newEditor.getText());

								interfaces.set(fSelectionIndex, editedRef);

								newEditor.dispose();
								interfacesTable.clear(fSelectionIndex);

								model.setInterfaces(interfaces);

							} else if (event.keyCode == SWT.ESC) {
								newEditor.dispose();
							}

						}

						@Override
						public void keyPressed(KeyEvent event) {
							// Only handle key released events

						}
					});
				} else {

					newEditor.addKeyListener(new KeyListener() {

						@Override
						public void keyReleased(KeyEvent event) {
							if (event.keyCode == SWT.CR) {
								String userInput = newEditor.getText();

								model.addInterfaces(
										new ClassifierReference(DotPathUtils.frontDotSegments(userInput),
												DotPathUtils.lastDotSegment(userInput)));
								newEditor.dispose();
							} else if (event.keyCode == SWT.ESC) {
								newEditor.dispose();
								// Reset the item count to delete the newly created item as the user aborted its
								// creation
								interfacesTable.setItemCount(model.getInterfaces().size());
							}
						}

						@Override
						public void keyPressed(KeyEvent event) {
							// Only handle key released events

						}
					});
				}

				newEditor.selectAll();
				newEditor.setFocus();
				editor.grabHorizontal = true;
				editor.setEditor(newEditor, item, 0);
			}
		}

		private final Table interfacesTable;
		private final Button interfacesAddButton;
		private final Button interfacesRemoveButton;

		private final InterfacesContainingModel model;

		/**
		 * Creates a new interfaces component inside the parent composite using the given model.
		 *
		 * @param interfacesContainingModel
		 *            A interface containing model
		 * @param container
		 *            The component container
		 */
		public InterfacesComponent(InterfacesContainingModel interfacesContainingModel,
				WizardComponentContainer container) {
			super(container);
			this.model = interfacesContainingModel;

			Composite parent = getParentComposite();

			Label interfacesLabel = new Label(parent, SWT.NONE);

			GridData interfacesLabelGridData = fillLabelDefaults();
			interfacesLabelGridData.verticalAlignment = SWT.TOP;
			interfacesLabel.setLayoutData(interfacesLabelGridData);

			interfacesLabel.setText("Interfaces:");

			interfacesTable = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL);
			interfacesTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

			Composite interfacesButtonsComposite = new Composite(parent, SWT.NONE);
			interfacesButtonsComposite.setLayoutData(GridDataFactory.fillDefaults().create());

			interfacesButtonsComposite.setLayout(GridLayoutFactory.swtDefaults().numColumns(1).margins(0, 0).create());

			interfacesAddButton = new Button(interfacesButtonsComposite, SWT.NONE);
			interfacesAddButton.setText("Add...");
			interfacesAddButton.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());

			interfacesRemoveButton = new Button(interfacesButtonsComposite, SWT.NONE);
			interfacesRemoveButton.setText("Remove");
			interfacesRemoveButton.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());

			setupBindings();
		}

		private void setupBindings() {
			if (interfacesTable == null || interfacesAddButton == null || interfacesRemoveButton == null) {
				return;
			}
			IObservableList<ClassifierReference> interfacesValue = BeanProperties
					.list(InterfacesContainingModel.class, N4JSClassifierWizardModel.INTERFACES_PROPERTY,
							ClassifierReference.class)
					.observe(model);

			// Update SWT Table on model interfaces list update
			interfacesValue.addListChangeListener(new IListChangeListener<ClassifierReference>() {

				@Override
				public void handleListChange(ListChangeEvent<? extends ClassifierReference> event) {
					int itemCount = event.getObservableList().size();
					interfacesTable.setItemCount(itemCount);

					// Find the minimum index to refresh from, to update the Table
					int minIndex = itemCount - 1;
					for (ListDiffEntry<? extends ClassifierReference> entry : event.diff.getDifferences()) {
						if (minIndex > entry.getPosition()) {
							minIndex = entry.getPosition();
						}
					}
					if (itemCount > 0) {
						interfacesTable.clear(minIndex, itemCount - 1);
					}

					for (ListDiffEntry<? extends ClassifierReference> diff : event.diff.getDifferences()) {
						interfacesTable.clear(diff.getPosition(), itemCount - 1);
					}
					// If no interfaces are contained disable the remove button
					if (itemCount < 1) {
						interfacesRemoveButton.setEnabled(false);
					}
				}
			});

			// Set data of the interfaces table
			interfacesTable.addListener(SWT.SetData, new Listener() {

				@Override
				public void handleEvent(Event event) {
					TableItem item = (TableItem) event.item;
					int index = interfacesTable.indexOf(item);
					if (index < model.getInterfaces().size()) {
						item.setText(model.getInterfaces().get(index).getFullSpecifier());
					} else {
						item.setText("");
					}

				}
			});

			this.interfacesRemoveButton.setEnabled(false);

			final TableEditor editor = new TableEditor(interfacesTable);

			editor.horizontalAlignment = SWT.LEFT;
			editor.grabHorizontal = true;
			editor.minimumWidth = 50;

			// register listener to enable in-line table editing
			interfacesTable.addMouseListener(new InterfacesTableMouseListener(editor));

			// Enable remove button when the user has selected a table element
			interfacesTable.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					interfacesRemoveButton.setEnabled(interfacesTable.getSelectionIndex() != -1
							&& interfacesTable.getSelectionIndex() < model.getInterfaces().size());
				}
			});

			// Remove butt on functionality
			this.interfacesRemoveButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					int selectionIndex = interfacesTable.getSelectionIndex();
					// Return if nothing is selected
					if (selectionIndex == -1) {
						return;
					}
					ClassifierReference itemToRemove = model.getInterfaces().get(selectionIndex);
					// Return if index is invalid in model
					if (itemToRemove == null) {
						return;
					}
					model.removeInterfaces(itemToRemove);
					interfacesRemoveButton.setEnabled(interfacesTable.getSelectionIndex() != -1);
				}
			});

			// Add button functionality
			this.interfacesAddButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					InterfacesSelectionDialog dialog = interfacesSelectionDialogProvider.get();
					dialog.open();

					if (dialog.getReturnCode() == Window.CANCEL) {
						return;
					}

					Object[] results = dialog.getResult();

					for (Object result : results) {
						if (result instanceof IEObjectDescription) {
							IEObjectDescription objectDescription = (IEObjectDescription) result;

							URI objectUri = ((IEObjectDescription) result).getEObjectURI();

							model.addInterfaces(
									new ClassifierReference(objectDescription.getQualifiedName(), objectUri));
						}
					}
				}
			});
		}

		@Override
		public void setFocus() {
			this.interfacesAddButton.setFocus();
		}
	}

}
