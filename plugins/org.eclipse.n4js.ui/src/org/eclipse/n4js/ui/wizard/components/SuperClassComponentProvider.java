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

import static org.eclipse.n4js.ui.wizard.components.WizardComponentUtils.fillTextDefaults;

import org.eclipse.core.databinding.beans.typed.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.window.Window;
import org.eclipse.n4js.ui.dialog.SingleClassSelectionDialog;
import org.eclipse.n4js.ui.wizard.classes.N4JSClassWizardModel;
import org.eclipse.n4js.ui.wizard.model.ClassifierReference;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * A provider for {@link SuperClassComponent}s
 *
 */
public class SuperClassComponentProvider {

	@Inject
	private Provider<SingleClassSelectionDialog> singleClassSelectionDialogProvider;

	/**
	 * Creates a new SuperClassComponent
	 */
	public SuperClassComponent create(N4JSClassWizardModel model, WizardComponentContainer container) {
		return new SuperClassComponent(model, container);
	}

	/**
	 * A component to select a super class. Either by typing in the absolute class specifier or by using the browse
	 * button.
	 */
	public class SuperClassComponent extends WizardComponent {
		/** The super class text */
		private final Text text;
		/** The browse button */
		private final Button browseButton;

		private final N4JSClassWizardModel model;

		/**
		 * Creates a new super class component using the given model and container.
		 *
		 * @param model
		 *            The model to bind this to
		 * @param container
		 *            The parent WizardComponentContainer
		 */
		public SuperClassComponent(N4JSClassWizardModel model, WizardComponentContainer container) {
			super(container);
			this.model = model;

			Label superClassLabel = new Label(container.getComposite(), SWT.NONE);
			superClassLabel.setText("Super class:");

			text = new Text(container.getComposite(), SWT.BORDER);
			text.setLayoutData(fillTextDefaults());

			browseButton = new Button(container.getComposite(), SWT.NONE);
			browseButton.setToolTipText("Opens a dialog to choose the super class");
			browseButton.setText("Browse...");

			setupBindings();
			setupDialog();
		}

		private void setupBindings() {
			// super class property binding

			IObservableValue<ClassifierReference> superClassValue = BeanProperties.value(N4JSClassWizardModel.class,
					N4JSClassWizardModel.SUPER_CLASS_PROPERTY, ClassifierReference.class).observe(model);
			IObservableValue<String> superClassUI = WidgetProperties.text(SWT.Modify).observe(text);

			getDataBindingContext().bindValue(superClassUI, superClassValue,
					new WizardComponentDataConverters.StringToClassifierReferenceConverter().updatingValueStrategy(),
					new WizardComponentDataConverters.ClassifierReferenceToStringConverter()
							.updatingValueStrategy());
		}

		private void setupDialog() {
			browseButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					SingleClassSelectionDialog dialog = singleClassSelectionDialogProvider.get();
					dialog.setInitialPattern(model.getSuperClass().getFullSpecifier());

					dialog.open();

					if (dialog.getReturnCode() == Window.CANCEL) {
						return;
					}

					Object result = dialog.getFirstResult();

					if (result instanceof IEObjectDescription) {
						IEObjectDescription objectDescription = (IEObjectDescription) result;

						URI objectUri = ((IEObjectDescription) result).getEObjectURI();
						model.setSuperClass(
								new ClassifierReference(objectDescription.getQualifiedName(), objectUri));
					}

				}
			});
		}

		@Override
		public void setFocus() {
			this.text.setFocus();
		}
	}
}
