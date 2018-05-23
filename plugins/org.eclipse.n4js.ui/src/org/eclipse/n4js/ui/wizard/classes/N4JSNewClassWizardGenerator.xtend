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
package org.eclipse.n4js.ui.wizard.classes

import com.google.inject.Inject
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.ui.wizard.classifiers.N4JSNewClassifierWizardGenerator
import org.eclipse.n4js.ui.wizard.generator.ImportRequirement
import org.eclipse.n4js.ui.wizard.generator.N4JSImportRequirementResolver
import org.eclipse.n4js.ui.wizard.generator.WizardGeneratorHelper
import java.util.ArrayList
import java.util.Map
import org.eclipse.emf.common.util.URI

/**
 * A file generator for {@link N4JSClassWizardModel}
 */
class N4JSNewClassWizardGenerator extends N4JSNewClassifierWizardGenerator<N4JSClassWizardModel> {

	@Inject
	private extension WizardGeneratorHelper generatorUtils;

	override protected generateClassifierCode(N4JSClassWizardModel model, Map<URI, String> aliasBindings) '''
		«IF model.isFinalAnnotated»@Final«ENDIF»
		«IF model.isN4jsAnnotated»@N4JS«ENDIF»
		«IF model.isInternal»@Internal «ENDIF
		»«model.accessModifier.exportStatement.addSpace
		»«IF model.isDefinitionFile»external «ENDIF
		»«model.accessModifier.toCodeRepresentation.addSpace»class «model.name»«
		IF model.superClass.isComplete» extends «model.superClass.realOrAliasName(aliasBindings)»«ENDIF»«
		IF model.interfaces.length > 0 » implements «ENDIF»«FOR iface : model.interfaces  SEPARATOR ", " »«
		iface.realOrAliasName(aliasBindings)»«ENDFOR» {

		}
		'''

	override protected getReferencedProjects(N4JSClassWizardModel model) {
		var referencedProjects = new ArrayList<IN4JSProject>(model.interfaces.map[uri.projectOfUri]);

		if (model.superClass.isComplete) {
			referencedProjects.add(model.superClass.uri.projectOfUri);
		}

		return referencedProjects;
	}

	override protected getImportRequirements(N4JSClassWizardModel model) {
		var demandedImports = new ArrayList<ImportRequirement>();

		if (model.superClass.complete) {
			demandedImports.add(N4JSImportRequirementResolver.classifierReferenceToImportRequirement(model.superClass));
		}
		if (!model.interfaces.empty) {
			demandedImports.addAll(N4JSImportRequirementResolver.classifierReferencesToImportRequirements(model.interfaces));
		}
		demandedImports
	}

}
