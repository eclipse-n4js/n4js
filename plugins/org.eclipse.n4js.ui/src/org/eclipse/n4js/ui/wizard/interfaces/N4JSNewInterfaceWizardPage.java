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
package org.eclipse.n4js.ui.wizard.interfaces;

import com.google.inject.Inject;

import org.eclipse.n4js.ui.wizard.classifiers.N4JSNewClassifierWizardPage;
import org.eclipse.n4js.ui.wizard.components.AccessModifierComponent;
import org.eclipse.n4js.ui.wizard.components.EmptyComponent;
import org.eclipse.n4js.ui.wizard.components.FileTypeComponent;
import org.eclipse.n4js.ui.wizard.components.InterfacesComponentProvider;
import org.eclipse.n4js.ui.wizard.components.NameComponent;
import org.eclipse.n4js.ui.wizard.components.OtherClassifierModifiersComponent;
import org.eclipse.n4js.ui.wizard.components.WizardComponentContainer;
import org.eclipse.n4js.ui.wizard.generator.WorkspaceWizardGenerator;

/**
 * A wizard page to allow the user to specify the informations about the creation of a new interface.
 *
 * <p>
 * Note: The wizard page is not meant to be used without setting a {@link N4JSInterfaceWizardModel}. To accomplish this
 * use the {@link N4JSNewInterfaceWizardPage#setModel(N4JSInterfaceWizardModel)} method.
 * </p>
 *
 */
public class N4JSNewInterfaceWizardPage extends N4JSNewClassifierWizardPage<N4JSInterfaceWizardModel> {

	@Inject
	private N4JSInterfaceWizardModelValidator validator;

	@Inject
	private InterfacesComponentProvider interfacesComponentProvider;

	@Inject
	private N4JSNewInterfaceWizardGenerator generator;

	/**
	 * Instantiates a New N4JS Interface wizard main page
	 */
	public N4JSNewInterfaceWizardPage() {
		this.setTitle("New N4JS Interface");
		this.setMessage("Create a new N4JS Interface");
		this.setPageComplete(false);
	}

	@SuppressWarnings("unused")
	@Override
	public void createComponents(WizardComponentContainer container) {

		// instance variable to set intial focus
		nameComponent = new NameComponent(getModel(), container);

		new EmptyComponent(container);

		new FileTypeComponent(getModel(), container);

		accessModifierComponent = new AccessModifierComponent(getModel(), container);

		// instance variable to allow custom bindings
		otherClassifierModifiersComponent = new OtherClassifierModifiersComponent(getModel(), container, false);

		new EmptyComponent(container);

		interfacesComponentProvider.create(getModel(), container);

		setupBindings();
	}

	@Override
	public WorkspaceWizardGenerator<N4JSInterfaceWizardModel> getGenerator() {
		return generator;
	}

	@Override
	public N4JSInterfaceWizardModelValidator getValidator() {
		return validator;
	}
}
