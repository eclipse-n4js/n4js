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
package org.eclipse.n4js.ui.wizard.interfaces

import org.eclipse.n4js.ui.wizard.classifiers.N4JSClassifierWizardModel
import org.eclipse.n4js.ui.wizard.model.AccessModifiableModel
import org.eclipse.n4js.ui.wizard.model.DefinitionFileModel
import org.eclipse.n4js.ui.wizard.model.InterfacesContainingModel
import org.eclipse.n4js.ui.wizard.model.NamedModel
import org.eclipse.n4js.utils.beans.PropertyChangeSupport

/**
 * A data model to hold the information of a {@link N4JSNewInterfaceWizard}.
 */
@PropertyChangeSupport
class N4JSInterfaceWizardModel extends N4JSClassifierWizardModel
	implements InterfacesContainingModel, AccessModifiableModel, NamedModel, DefinitionFileModel {

	@Override
	override getClassifierName() {
		return 'interface';
	}

}
