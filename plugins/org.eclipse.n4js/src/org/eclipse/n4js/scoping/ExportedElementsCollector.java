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
import org.eclipse.n4js.scoping.accessModifiers.AbstractTypeVisibilityChecker.TypeVisibility;
import org.eclipse.n4js.scoping.accessModifiers.HollowTypeOrValueDescription;
import org.eclipse.n4js.scoping.accessModifiers.InvisibleTypeOrVariableDescription;
import org.eclipse.n4js.scoping.accessModifiers.NonExportedElementDescription;
import org.eclipse.n4js.scoping.accessModifiers.TypeVisibilityChecker;
import org.eclipse.n4js.scoping.accessModifiers.VariableVisibilityChecker;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.ElementExportDefinition;
import org.eclipse.n4js.ts.types.ExportDefinition;
import org.eclipse.n4js.ts.types.ModuleExportDefinition;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TNamespace;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.RecursionGuard;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * This class can be used to collect all elements exported from a given {@link AbstractNamespace}.
 * <p>
 * While collecting a visibility check for each of the elements is performed and the returned
 * {@link IEObjectDescription} are decorated with visibility errors if applicable. (See
 * {@link InvisibleTypeOrVariableDescription})
 */
public class ExportedElementsCollector {

	@Inject
	private TypeVisibilityChecker typeVisibilityChecker;

	@Inject
	private VariableVisibilityChecker variableVisibilityChecker;

	@Inject
	private JavaScriptVariantHelper variantHelper;

	private static final class CollectionInfo {

		final AbstractNamespace start;
		final Resource context;
		final boolean includeHollows;
		final boolean includeVariables;

		final List<IEObjectDescription> visible = new ArrayList<>();
		final List<IEObjectDescription> invisible = new ArrayList<>();

		private RecursionGuard<AbstractNamespace> guard;

		public CollectionInfo(AbstractNamespace start, Resource context, boolean includeHollows,
				boolean includeVariables) {
			this.start = start;
			this.context = context;
			this.includeHollows = includeHollows;
			this.includeVariables = includeVariables;
		}

		public boolean tryNext(AbstractNamespace next) {
			if (next == null || next == start) {
				return false;
			}
			if (guard == null) {
				guard = new RecursionGuard<>();
			}
			return guard.tryNext(next);
		}
	}

	/**
	 * Returns an iterable of all exported elements of the given namespace, which may include elements from other
	 * namespaces that are re-exported by the given namespace.
	 * <p>
	 * Visibility checks are performed based on the assumptions that the elements will be accessed from the given
	 * context resource.
	 *
	 * @param namespace
	 *            The namespace.
	 * @param context
	 *            The context resource for visibility checks.
	 */
	public Iterable<IEObjectDescription> getExportedElements(AbstractNamespace namespace, Resource context,
			boolean includeHollows, boolean includeVariables) {

		CollectionInfo info = new CollectionInfo(namespace, context, includeHollows, includeVariables);
		doCollectElements(namespace, info);
		return Iterables.concat(info.visible, info.invisible);
	}

	private void doCollectElements(AbstractNamespace namespace, CollectionInfo info) {

		for (ExportDefinition exportDef : Lists.reverse(namespace.getExportDefinitions())) {
			if (exportDef instanceof ElementExportDefinition) {
				ElementExportDefinition exportDefCasted = (ElementExportDefinition) exportDef;
				TExportableElement exportedElem = exportDefCasted.getExportedElement();
				if (exportedElem == null || exportedElem.eIsProxy()) {
					continue;
				}
				String exportedName = exportDefCasted.getExportedName();
				if (exportedName == null) {
					continue;
				}
				doCollectElement(exportedName, exportedElem, info);

			} else if (exportDef instanceof ModuleExportDefinition) {
				ModuleExportDefinition exportDefCasted = (ModuleExportDefinition) exportDef;
				TModule exportedModule = exportDefCasted.getExportedModule();
				if (exportedModule == null || exportedModule.eIsProxy()) {
					continue;
				}
				if (info.tryNext(exportedModule)) {
					doCollectElements(exportedModule, info);
				}
			}
		}

		// special handling of non-exported elements
		if (namespace instanceof TNamespace && namespace.eResource() == info.context) {
			// non-exported elements of namespaces are accessible from everywhere within the containing file
			// (even outside their containing namespace)
			for (TExportableElement elem : namespace.getExportableElements()) {
				if (!elem.isDirectlyExported()) {
					doCollectElement(elem.getName(), elem, info);
				}
			}
		} else {
			// non-exported elements are added as well to obtain better error messages
			for (TExportableElement elem : namespace.getExportableElements()) {
				if (!elem.isDirectlyExported()) {
					info.invisible.add(new NonExportedElementDescription(
							createObjectDescription(elem.getName(), elem)));
				}
			}
		}
	}

	private void doCollectElement(String exportedName, TExportableElement exportedElem, CollectionInfo info) {

		boolean include = (info.includeHollows || !N4JSLanguageUtils.isHollowElement(exportedElem, variantHelper))
				&& (info.includeVariables || !(exportedElem instanceof TVariable));
		if (include) {
			TypeVisibility visibility = isVisible(info.context, exportedElem);
			if (visibility.visibility) {
				info.visible.add(createObjectDescription(exportedName, exportedElem));
			} else {
				info.invisible.add(new InvisibleTypeOrVariableDescription(
						createObjectDescription(exportedName, exportedElem),
						visibility.accessModifierSuggestion));
			}
		} else {
			if (exportedElem instanceof Type) {
				info.invisible.add(new HollowTypeOrValueDescription(
						createObjectDescription(exportedName, exportedElem), "type"));
			} else {
				/*
				 * Note this is handled differently (no HollowTypeOrValueDescription are created when includeVariables
				 * is false) compared to includeHollows because: ParameterizedTypeRef#declaredType is of type Type.
				 * Since TVariable is no subtype of Type, it cannot be linked to that property 'declaredType'.
				 */
			}
		}
	}

	private TypeVisibility isVisible(Resource contextResource, TExportableElement elem) {
		if (elem instanceof Type) {
			return typeVisibilityChecker.isVisible(contextResource, (Type) elem);
		} else if (elem instanceof TVariable) {
			return variableVisibilityChecker.isVisible(contextResource, (TVariable) elem);
		}
		return new TypeVisibility(false);
	}

	/**
	 * Helper method to create an {@link IEObjectDescription} for a given {@link TExportableElement}.
	 */
	private IEObjectDescription createObjectDescription(String exportedName, TExportableElement element) {
		return EObjectDescription.create(exportedName, element);
	}
}
