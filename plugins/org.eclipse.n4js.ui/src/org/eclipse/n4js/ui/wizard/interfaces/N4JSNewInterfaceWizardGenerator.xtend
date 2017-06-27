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

import com.google.inject.Inject
import org.eclipse.n4js.ui.wizard.classifiers.N4JSNewClassifierWizardGenerator
import org.eclipse.n4js.ui.wizard.generator.ImportRequirement
import org.eclipse.n4js.ui.wizard.generator.N4JSImportRequirementResolver
import org.eclipse.n4js.ui.wizard.generator.WizardGeneratorHelper
import java.util.ArrayList
import java.util.List
import java.util.Map
import org.eclipse.emf.common.util.URI

/**
 * A file generator for {@link N4JSInterfaceWizardModel}.
 */
class N4JSNewInterfaceWizardGenerator extends N4JSNewClassifierWizardGenerator<N4JSInterfaceWizardModel> {

	@Inject
	private extension WizardGeneratorHelper generatorUtils;

	/**
	 *  Generates the interface code.
	 */
	override protected String generateClassifierCode(N4JSInterfaceWizardModel model, Map<URI,String> aliasBindings)
		'''
		«IF model.n4jsAnnotated»@N4JS«ENDIF»
		«IF model.isInternal»@Internal «ENDIF
		»«model.accessModifier.exportStatement.addSpace
		»«IF model.isDefinitionFile»external «ENDIF
		»«model.accessModifier.toCodeRepresentation.addSpace»interface «model.name»«
		IF model.interfaces.length > 0 » extends «ENDIF»«FOR iface : model.interfaces  SEPARATOR ", " »«
		iface.realOrAliasName(aliasBindings)»«ENDFOR» {

		}
		'''

	override protected getReferencedProjects(N4JSInterfaceWizardModel model) {
		model.interfaces.map[uri.projectOfUri];
	}

	/** Return the import requirement of a N4JSInterfaceWizardModel */
	override protected def List<ImportRequirement> getImportRequirements(N4JSInterfaceWizardModel model) {
		var demandedImports = new ArrayList<ImportRequirement>();

		if ( !model.interfaces.empty)
			demandedImports.addAll(N4JSImportRequirementResolver.classifierReferencesToImportRequirements(model.interfaces));

		demandedImports
	}
}
