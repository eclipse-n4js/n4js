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
import org.eclipse.n4js.scoping.accessModifiers.InvisibleTypeOrVariableDescription;
import org.eclipse.n4js.scoping.accessModifiers.TypeVisibilityChecker;
import org.eclipse.n4js.scoping.accessModifiers.VariableVisibilityChecker;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TModule;
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

	/**
	 * Returns an iterable of all top-level elements of the given module, given that they are accessed from the given
	 * context resource.
	 *
	 * @param module
	 *            The module
	 * @param contextResource
	 *            The context resource
	 */
	public Iterable<IEObjectDescription> getTopLevelElements(TModule module, Resource contextResource) {
		List<IEObjectDescription> visible = new ArrayList<>();
		List<IEObjectDescription> invisible = new ArrayList<>();

		module.getTopLevelTypes().forEach(it -> {
			TypeVisibility typeVisiblity = typeVisibilityChecker.isVisible(contextResource, it);
			if (typeVisiblity.visibility) {
				visible.add(createObjectDescription(it));
			} else {
				invisible.add(
						new InvisibleTypeOrVariableDescription(createObjectDescription(it),
								typeVisiblity.accessModifierSuggestion));
			}
		});

		module.getVariables().forEach(it -> {
			TypeVisibility typeVisiblity = variableVisibilityChecker.isVisible(contextResource, it);
			if (typeVisiblity.visibility) {
				visible.add(createObjectDescription(it));
			} else {
				invisible.add(
						new InvisibleTypeOrVariableDescription(createObjectDescription(it),
								typeVisiblity.accessModifierSuggestion));
			}
		});

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
