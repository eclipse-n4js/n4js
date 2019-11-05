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

import org.eclipse.core.databinding.beans.typed.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.n4js.ui.wizard.classifiers.N4JSClassifierWizardModel;
import org.eclipse.n4js.ui.wizard.model.DefinitionFileModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * A component which allows the selection of a checkbox specifying the Definition File property
 */
public class FileTypeComponent extends WizardComponent {

	/** The definition file checkbox */
	private final Button definitionFileBox;
	private final DefinitionFileModel model;

	/**
	 * Creates a new definition file component using model in container.
	 *
	 * @param model
	 *            The model to bind it to
	 * @param container
	 *            The container to create it in
	 */
	public FileTypeComponent(DefinitionFileModel model, WizardComponentContainer container) {
		super(container);
		this.model = model;

		Composite parent = getParentComposite();

		Label definitionFileLabel = new Label(parent, SWT.NONE);
		definitionFileLabel.setLayoutData(fillLabelDefaults());
		definitionFileLabel.setText("File type:");

		definitionFileBox = new Button(parent, SWT.CHECK);
		definitionFileBox.setText("Definition file (.n4jsd)");
		definitionFileBox.setLayoutData(fillLabelDefaults());

		WizardComponentUtils.emptyGridCell(parent);

		setupBindings();
	}

	private void setupBindings() {
		// Definition file property binding (definition file)

		IObservableValue<Boolean> externalValue = BeanProperties
				.value(DefinitionFileModel.class, N4JSClassifierWizardModel.DEFINITION_FILE_PROPERTY, Boolean.class)
				.observe(model);
		IObservableValue<Boolean> externalUI = WidgetProperties.buttonSelection().observe(definitionFileBox);
		getDataBindingContext().bindValue(externalUI, externalValue);
	}

	@Override
	public void setFocus() {
		this.definitionFileBox.setFocus();
	}
}
