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
import static org.eclipse.n4js.ui.wizard.components.WizardComponentUtils.fillTextDefaults;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.typed.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.SelectObservableValue;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.layout.RowLayoutFactory;
import org.eclipse.n4js.ui.wizard.classifiers.N4JSClassifierWizardModel;
import org.eclipse.n4js.ui.wizard.model.AccessModifiableModel;
import org.eclipse.n4js.ui.wizard.model.AccessModifier;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * A component to allow the selection of a N4JS access modifiers and an additional internal annotation checkbox.
 */
public class AccessModifierComponent extends WizardComponent {

	private final Button privateAccessModifierBox;
	private final Button projectAccessModifierBox;
	private final Button internalAnnotationBox;
	private final Button publicAccessModifierBox;

	private final AccessModifiableModel model;
	private final DataBindingContext dataBindingContext;

	private final WizardComponentContainer componentContainer;

	/**
	 * Creates a new AccessModifierComponent.
	 *
	 * @param model
	 *            The model to bind to
	 * @param container
	 *            The parent to create the widgets in
	 */
	public AccessModifierComponent(AccessModifiableModel model, WizardComponentContainer container) {
		super(container);

		this.model = model;
		this.componentContainer = container;
		this.dataBindingContext = container.getDataBindingContext();

		Label accessModifierLabel = new Label(componentContainer.getComposite(), SWT.NONE);
		accessModifierLabel.setLayoutData(fillLabelDefaults());
		accessModifierLabel.setText("Access modifier:");

		Composite accessModifiersComposite = new Composite(componentContainer.getComposite(), SWT.NONE);
		accessModifiersComposite.setLayout(RowLayoutFactory.swtDefaults().extendedMargins(0, 0, 0, 0).create());

		GridData accessModifiersCompositeLayoutData = fillTextDefaults();
		accessModifiersCompositeLayoutData.horizontalSpan = 2;
		accessModifiersComposite.setLayoutData(accessModifiersCompositeLayoutData);

		publicAccessModifierBox = new Button(accessModifiersComposite, SWT.RADIO);
		publicAccessModifierBox.setText("public");

		projectAccessModifierBox = new Button(accessModifiersComposite, SWT.RADIO);
		projectAccessModifierBox.setText("project");

		privateAccessModifierBox = new Button(accessModifiersComposite, SWT.RADIO);
		privateAccessModifierBox.setText("private");

		internalAnnotationBox = new Button(accessModifiersComposite, SWT.CHECK);
		getInternalAnnotationBox().setText("@Internal");

		setupBindings();
	}

	private void setupBindings() {
		// Access modifier property binding
		IObservableValue<Boolean> publicButtonSelection = WidgetProperties.buttonSelection()
				.observe(publicAccessModifierBox);
		IObservableValue<Boolean> projectButtonSelection = WidgetProperties.buttonSelection()
				.observe(projectAccessModifierBox);
		IObservableValue<Boolean> privateButtonSelection = WidgetProperties.buttonSelection()
				.observe(privateAccessModifierBox);

		SelectObservableValue<AccessModifier> accessModifierSelectObservable = new SelectObservableValue<>();
		accessModifierSelectObservable.addOption(AccessModifier.PUBLIC, publicButtonSelection);
		accessModifierSelectObservable.addOption(AccessModifier.PROJECT, projectButtonSelection);
		accessModifierSelectObservable.addOption(AccessModifier.PRIVATE, privateButtonSelection);

		IObservableValue<AccessModifier> accessModifierProperty = BeanProperties.value(AccessModifiableModel.class,
				N4JSClassifierWizardModel.ACCESS_MODIFIER_PROPERTY, AccessModifier.class).observe(model);

		dataBindingContext.bindValue(accessModifierSelectObservable, accessModifierProperty);

		// Internal property binding

		IObservableValue<Boolean> internalValue = BeanProperties
				.value(AccessModifiableModel.class, N4JSClassifierWizardModel.INTERNAL_PROPERTY, Boolean.class)
				.observe(model);
		IObservableValue<Boolean> internalUI = WidgetProperties.buttonSelection().observe(getInternalAnnotationBox());
		dataBindingContext.bindValue(internalUI, internalValue);
	}

	/**
	 * Returns the internal annotation checkbox of the component
	 */
	public Button getInternalAnnotationBox() {
		return internalAnnotationBox;
	}

	@Override
	public void setFocus() {
		publicAccessModifierBox.setFocus();
	}
}
