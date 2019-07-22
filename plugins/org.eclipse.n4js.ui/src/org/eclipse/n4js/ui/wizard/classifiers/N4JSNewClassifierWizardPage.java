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
package org.eclipse.n4js.ui.wizard.classifiers;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.typed.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.ui.dialog.ModuleSpecifierSelectionDialog;
import org.eclipse.n4js.ui.wizard.components.AccessModifierComponent;
import org.eclipse.n4js.ui.wizard.components.NameComponent;
import org.eclipse.n4js.ui.wizard.components.OtherClassifierModifiersComponent;
import org.eclipse.n4js.ui.wizard.components.WizardComponentDataConverters;
import org.eclipse.n4js.ui.wizard.generator.ContentBlock;
import org.eclipse.n4js.ui.wizard.generator.WorkspaceWizardGenerator;
import org.eclipse.n4js.ui.wizard.model.AccessModifier;
import org.eclipse.n4js.ui.wizard.model.DefinitionFileModel;
import org.eclipse.n4js.ui.wizard.workspace.PreviewableWizardPage;
import org.eclipse.n4js.ui.wizard.workspace.SuffixText;
import org.eclipse.n4js.ui.wizard.workspace.WizardPreviewProvider.WizardPreview;
import org.eclipse.n4js.ui.wizard.workspace.WorkspaceWizardModel;
import org.eclipse.n4js.ui.wizard.workspace.WorkspaceWizardModelValidator;
import org.eclipse.n4js.ui.wizard.workspace.WorkspaceWizardModelValidator.ValidationResult;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Generic wizard page for all N4JS classifiers.
 */
public abstract class N4JSNewClassifierWizardPage<M extends N4JSClassifierWizardModel>
		extends PreviewableWizardPage<M> {

	/** Component for the classifier name. */
	protected NameComponent nameComponent;

	/** Component for the access modifier buttons with the bindings. */
	protected AccessModifierComponent accessModifierComponent;

	/** Component for the other modifier, such as @N4JS */
	protected OtherClassifierModifiersComponent otherClassifierModifiersComponent;

	@Override
	public void openModuleSpecifierDialog(Shell shell) {

		ModuleSpecifierSelectionDialog dialog = new ModuleSpecifierSelectionDialog(shell,
				getModel().getProject().append(getModel().getSourceFolder()));

		if (!getModel().getEffectiveModuleSpecifier().isEmpty()) {
			String initialSelectionSpecifier = getModel().getEffectiveModuleSpecifier();

			String fileExtension = getModel().computeFileLocation().getFileExtension();
			if (fileExtension != null) {
				dialog.setDefaultFileExtension(fileExtension);
			}

			dialog.setInitialSelection(initialSelectionSpecifier);
		}

		dialog.open();

		Object result = dialog.getFirstResult();

		if (result instanceof String) {

			IPath specifierPath = new Path((String) result);

			String fileExtension = specifierPath.getFileExtension();

			// If the selected module specifier is a file
			if (fileExtension != null && !fileExtension.isEmpty()) {
				// and its file extension suggests a different external value than the model
				if (fileExtension.equals(N4JSGlobals.N4JSD_FILE_EXTENSION) != getModel().isDefinitionFile()) {
					// toggle the external value of the model
					getModel().setDefinitionFile(!getModel().isDefinitionFile());
				}
				specifierPath = specifierPath.removeFileExtension();
			}

			// If the last segment corresponds with the non-empty interface name remove it
			if (specifierPath.segmentCount() > 0
					&& specifierPath.removeFileExtension().lastSegment().equals(getModel().getName())
					&& !getModel().getName().isEmpty()) {
				if (specifierPath.segmentCount() > 1) {
					specifierPath = specifierPath.removeLastSegments(1).addTrailingSeparator();
				} else {
					specifierPath = specifierPath.removeLastSegments(1);
				}
			}

			getModel().setModuleSpecifier(specifierPath.toString());
		}
	}

	/**
	 * Setup additional non-component contained bindings
	 */
	protected void setupBindings() {

		DataBindingContext dataBindingContext = this.getDataBindingContext();

		IObservableValue<String> moduleSpecifierValue = BeanProperties
				.value(WorkspaceWizardModel.class, WorkspaceWizardModel.MODULE_SPECIFIER_PROPERTY, String.class)
				.observe(getModel());

		IObservableValue<Boolean> suffixVisibilityValue = BeanProperties
				.value(SuffixText.class, SuffixText.SUFFIX_VISIBILITY_PROPERTY, Boolean.class)
				.observe(workspaceWizardControl.getModuleSpecifierText());

		//// Only show the suffix on input values ending with a '/' character or empty module specifiers.
		dataBindingContext.bindValue(suffixVisibilityValue, moduleSpecifierValue, noUpdateValueStrategy(),
				WizardComponentDataConverters.strategyForPredicate(m -> {
					String moduleSpecifier = (String) m;
					return (moduleSpecifier.isEmpty()
							|| moduleSpecifier.charAt(moduleSpecifier.length() - 1) == IPath.SEPARATOR);
				}));

		//// interface name to module specifier suffix binding
		IObservableValue<String> interfaceNameModelValue = BeanProperties
				.value(N4JSClassifierWizardModel.class, N4JSClassifierWizardModel.NAME_PROPERTY, String.class)
				.observe(getModel());
		IObservableValue<String> greySuffixValue = BeanProperties
				.value(SuffixText.class, SuffixText.SUFFIX_PROPERTY, String.class)
				.observe(workspaceWizardControl.getModuleSpecifierText());
		dataBindingContext.bindValue(greySuffixValue,
				interfaceNameModelValue, noUpdateValueStrategy(),
				new UpdateValueStrategy<>(UpdateValueStrategy.POLICY_UPDATE));

		//// Enable n4js <-> Definition value(external) is selected
		IObservableValue<Boolean> externalValue = BeanProperties
				.value(DefinitionFileModel.class, N4JSClassifierWizardModel.DEFINITION_FILE_PROPERTY, Boolean.class)
				.observe((DefinitionFileModel) getModel());
		IObservableValue<Boolean> n4jsEnabled = WidgetProperties.enabled()
				.observe(otherClassifierModifiersComponent.getN4jsAnnotationBox());
		dataBindingContext.bindValue(n4jsEnabled, externalValue, noUpdateValueStrategy(),
				WizardComponentDataConverters.strategyForPredicate(input -> getModel().isDefinitionFile()
						&& AccessModifier.PRIVATE != getModel().getAccessModifier()));

		// One way binding of the access modifiers to the enabled state of internal checkbox
		IObservableValue<Boolean> internalEnabledValue = WidgetProperties.enabled()
				.observe(accessModifierComponent.getInternalAnnotationBox());
		IObservableValue<AccessModifier> accessModifierSelectObservable = BeanProperties
				.value(N4JSClassifierWizardModel.class, N4JSClassifierWizardModel.ACCESS_MODIFIER_PROPERTY,
						AccessModifier.class)
				.observe(getModel());

		dataBindingContext.bindValue(internalEnabledValue, accessModifierSelectObservable, noUpdateValueStrategy(),
				WizardComponentDataConverters.strategyForPredicate(object -> {
					if (object instanceof AccessModifier) {
						return isInternalAccessModifierEnabled((AccessModifier) object);
					}
					return false;
				}));

		// N4JS annotation checkbox disabled when access modifier is private
		IObservableValue<Boolean> n4jsEnabledValue = WidgetProperties.enabled()
				.observe(otherClassifierModifiersComponent.getN4jsAnnotationBox());
		dataBindingContext.bindValue(n4jsEnabledValue, accessModifierSelectObservable, noUpdateValueStrategy(),
				WizardComponentDataConverters.strategyForPredicate(object -> {
					if (object instanceof AccessModifier) {
						return ((AccessModifier) object != AccessModifier.PRIVATE) && getModel().isDefinitionFile();
					}
					return false;
				}));

		// Refresh wizard state on validation change
		IObservableValue<ValidationResult> observableValidationValue = BeanProperties
				.value(WorkspaceWizardModelValidator.VALIDATION_RESULT, ValidationResult.class)
				.observe(getValidator());
		observableValidationValue.addValueChangeListener(new IValueChangeListener<ValidationResult>() {
			@Override
			public void handleValueChange(ValueChangeEvent<? extends ValidationResult> event) {
				onValidationChange(event.diff.getNewValue());
			}
		});

	}

	/**
	 * Returns {@code true} if the <code>@Internal</code> access modifier should be enabled for selection in the UI.
	 *
	 * @param modifier
	 *            the currently selected modifier on the UI.
	 * @return {@code true} if the internal visibility should be enabled on the UI, otherwise {@code false}.
	 */
	private boolean isInternalAccessModifierEnabled(AccessModifier modifier) {
		return modifier == AccessModifier.PUBLIC;
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);

		// Set initial UI state.
		getDataBindingContext().updateTargets();
	}

	@Override
	protected boolean setInitialFocus() {
		if (!super.setInitialFocus()) {
			this.nameComponent.setFocus();
		}
		return false;
	}

	@Override
	protected void updateContentPreview(WizardPreview contentPreview) {
		Display.getCurrent().asyncExec(() -> {
			ContentBlock[] codeBlocks = getGenerator().generateContentPreview(getModel());
			contentPreview.setContent(codeBlocks);

			// Reveal last content block (class code)
			contentPreview.revealContentBlock(codeBlocks[codeBlocks.length - 1]);

			// Show file location in the info bar
			contentPreview.setInfo(getModel().computeFileLocation().toString());
		});
	}

	/**
	 * Returns the {@link WorkspaceWizardGenerator} of this page.
	 */
	public abstract WorkspaceWizardGenerator<M> getGenerator();

	/**
	 * Invoked when the validation result of the model has changed.
	 *
	 * @param result
	 *            The most recent validation result
	 */
	private void onValidationChange(ValidationResult result) {
		if (result.valid) {
			this.setPageComplete(true);
			this.setMessage("Press finish to create the new " + getModel().getClassifierName());
			this.setErrorMessage(null);
		} else {
			this.setPageComplete(false);
			this.setErrorMessage(result.errorMessage);
		}
	}

	private static <S, D> UpdateValueStrategy<S, D> noUpdateValueStrategy() {
		return new UpdateValueStrategy<>(UpdateValueStrategy.POLICY_NEVER);
	}

}
