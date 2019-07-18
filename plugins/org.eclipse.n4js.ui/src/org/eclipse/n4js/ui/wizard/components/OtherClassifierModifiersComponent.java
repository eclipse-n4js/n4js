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

import org.eclipse.core.databinding.beans.typed.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.layout.RowLayoutFactory;
import org.eclipse.n4js.ui.wizard.classes.N4JSClassWizardModel;
import org.eclipse.n4js.ui.wizard.classifiers.N4JSClassifierWizardModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * A component which provides control over the annotations of a N4JS class
 *
 */
public class OtherClassifierModifiersComponent extends WizardComponent {

	/** The Final annotation box. */
	private final Button finalAnnotationBox;

	/** The N4JS annotation box. */
	private final Button n4jsAnnotationBox;

	/** Model for the data binding. */
	private final N4JSClassifierWizardModel model;

	/**
	 * Creates a new OtherClassModifiers component.
	 *
	 * @param model
	 *            The model the bind it to
	 * @param container
	 *            The container to put it in
	 */
	public OtherClassifierModifiersComponent(N4JSClassifierWizardModel model, WizardComponentContainer container,
			boolean createFinalButton) {

		super(container);
		this.model = model;

		Label otherModifiersLabel = new Label(getParentComposite(), SWT.NONE);
		otherModifiersLabel.setText("Other modifiers:");

		Composite otherModifierComposite = new Composite(this.getParentComposite(), SWT.NONE);

		otherModifierComposite.setLayout(RowLayoutFactory.swtDefaults().extendedMargins(0, 0, 0, 0).create());

		if (createFinalButton) {
			finalAnnotationBox = new Button(otherModifierComposite, SWT.CHECK);
			finalAnnotationBox.setText("@Final");
		} else {
			finalAnnotationBox = null;
		}

		n4jsAnnotationBox = new Button(otherModifierComposite, SWT.CHECK);
		getN4jsAnnotationBox().setText("@N4JS");

		WizardComponentUtils.emptyGridCell(getParentComposite());

		setupBindings();
	}

	private void setupBindings() {
		// Final property binding

		if (null != finalAnnotationBox && model instanceof N4JSClassWizardModel) {
			IObservableValue<Boolean> finalValue = BeanProperties
					.value(N4JSClassWizardModel.class, N4JSClassWizardModel.FINAL_ANNOTATED_PROPERTY,
							Boolean.class)
					.observe((N4JSClassWizardModel) model);
			IObservableValue<Boolean> finalUI = WidgetProperties.buttonSelection().observe(finalAnnotationBox);
			getDataBindingContext().bindValue(finalUI, finalValue);
		}

		// n4js annotation property binding

		IObservableValue<Boolean> n4jsValue = BeanProperties
				.value(N4JSClassifierWizardModel.class, N4JSClassifierWizardModel.N4JS_ANNOTATED_PROPERTY,
						Boolean.class)
				.observe(model);
		IObservableValue<Boolean> n4jsUI = WidgetProperties.buttonSelection().observe(n4jsAnnotationBox);

		getDataBindingContext().bindValue(n4jsUI, n4jsValue);
	}

	@Override
	public void setFocus() {
		if (null != finalAnnotationBox) {
			this.finalAnnotationBox.setFocus();
		} else {
			this.n4jsAnnotationBox.setFocus();
		}
	}

	/**
	 * Returns the N4JS annotation box widget
	 */
	public Button getN4jsAnnotationBox() {
		return n4jsAnnotationBox;
	}
}
