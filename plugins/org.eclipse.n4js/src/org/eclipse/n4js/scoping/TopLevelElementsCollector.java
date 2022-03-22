/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.scoping;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.resource.N4JSEObjectDescription;
import org.eclipse.n4js.scoping.accessModifiers.AbstractTypeVisibilityChecker.TypeVisibility;
import org.eclipse.n4js.scoping.accessModifiers.HollowTypeOrValueDescription;
import org.eclipse.n4js.scoping.accessModifiers.InvisibleTypeOrVariableDescription;
import org.eclipse.n4js.scoping.accessModifiers.TypeVisibilityChecker;
import org.eclipse.n4js.scoping.accessModifiers.VariableVisibilityChecker;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * This class can be used to collect all top-level elements of a given module.
 *
 * While collecting a visibility check for each of the elements is performed and the returned
 * {@link IEObjectDescription} are decorated with visibility errors if applicable. (See
 * {@link InvisibleTypeOrVariableDescription})
 */
public class TopLevelElementsCollector {

	@Inject
	private TypeVisibilityChecker typeVisibilityChecker;

	@Inject
	private VariableVisibilityChecker variableVisibilityChecker;

	@Inject
	private JavaScriptVariantHelper variantHelper;

	/**
	 * Returns an iterable of all top-level elements of the given module, given that they are accessed from the given
	 * context resource.
	 *
	 * @param module
	 *            The module
	 * @param contextResource
	 *            The context resource
	 */
	public Iterable<IEObjectDescription> getTopLevelElements(AbstractNamespace module, Resource contextResource,
			boolean includeHollows, boolean includeVariables) {

		List<IEObjectDescription> visible = new ArrayList<>();
		List<IEObjectDescription> invisible = new ArrayList<>();

		Iterable<Type> tltAndNamespaces = Iterables.concat(module.getTypes(), module.getNamespaces());
		for (Type type : tltAndNamespaces) {
			boolean include = includeHollows || !N4JSLanguageUtils.isHollowElement(type, variantHelper);
			if (include) {
				TypeVisibility typeVisiblity = typeVisibilityChecker.isVisible(contextResource, type);
				if (typeVisiblity.visibility) {
					visible.add(createObjectDescription(type));
				} else {
					invisible.add(
							new InvisibleTypeOrVariableDescription(createObjectDescription(type),
									typeVisiblity.accessModifierSuggestion));
				}
			} else {
				invisible.add(new HollowTypeOrValueDescription(createObjectDescription(type), "type"));
			}
		}

		if (includeVariables) {
			/*
			 * Note this is handled differently (no InvisibleTypeOrVariableDescription are created when includeVariables
			 * is false) compared to includeHollows because: ParameterizedTypeRef#declaredType is of type Type. Since
			 * TVariable is no subtype of Type, it cannot be linked to that property 'declaredType'.
			 */
			for (TVariable var : module.getExportedVariables()) {
				TypeVisibility typeVisiblity = variableVisibilityChecker.isVisible(contextResource, var);
				if (typeVisiblity.visibility) {
					visible.add(createObjectDescription(var));
				} else {
					invisible.add(
							new InvisibleTypeOrVariableDescription(createObjectDescription(var),
									typeVisiblity.accessModifierSuggestion));
				}
			}
		}

		return Iterables.concat(visible, invisible);
	}

	/**
	 * Helper methods to create an {@link IEObjectDescription} from a given {@link TExportableElement}.
	 *
	 * If available, the exported name is used otherwise the regular name is used as a fallback.
	 */
	private IEObjectDescription createObjectDescription(TExportableElement element) {
		String exportedName = element.getExportedName();
		if (null != exportedName) {
			return N4JSEObjectDescription.create(exportedName, element);
		} else {
			return N4JSEObjectDescription.create(element.getName(), element);
		}
	}
}
