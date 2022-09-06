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
import org.eclipse.n4js.n4JS.MemberAccess;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.scoping.accessModifiers.AbstractTypeVisibilityChecker.TypeVisibility;
import org.eclipse.n4js.scoping.accessModifiers.HollowTypeOrValueDescription;
import org.eclipse.n4js.scoping.accessModifiers.InvisibleTypeOrVariableDescription;
import org.eclipse.n4js.scoping.accessModifiers.NonExportedElementDescription;
import org.eclipse.n4js.scoping.accessModifiers.TypeVisibilityChecker;
import org.eclipse.n4js.scoping.accessModifiers.VariableVisibilityChecker;
import org.eclipse.n4js.scoping.members.MemberScopingHelper;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.ElementExportDefinition;
import org.eclipse.n4js.ts.types.ExportDefinition;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.ModuleExportDefinition;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TNamespace;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.DeclMergingHelper;
import org.eclipse.n4js.utils.DeclMergingUtils;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.RecursionGuard;
import org.eclipse.n4js.utils.ResourceType;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.common.base.Optional;
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

	@Inject
	private MemberScopingHelper memberScopingHelper;

	@Inject
	private DeclMergingHelper declMergingHelper;

	private static final class CollectionInfo {

		final AbstractNamespace start;
		final N4JSResource contextResource;
		final Optional<MemberAccess> memberAccess;
		final boolean includeHollows;
		final boolean includeValueOnlyElements;

		final List<IEObjectDescription> visible = new ArrayList<>();
		final List<IEObjectDescription> invisible = new ArrayList<>();

		private RecursionGuard<AbstractNamespace> guard;

		public CollectionInfo(AbstractNamespace start, N4JSResource contextResource,
				Optional<MemberAccess> memberAccess,
				boolean includeHollows, boolean includeVariables) {

			this.start = start;
			this.contextResource = contextResource;
			this.memberAccess = memberAccess;
			this.includeHollows = includeHollows;
			this.includeValueOnlyElements = includeVariables;
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
	 * @param contextResource
	 *            The context resource, i.e. the resource importing the exported elements returned from this method.
	 *            Used for visibility checks, among other things.
	 * @param memberAccess
	 *            The {@link MemberAccess} AST node as provided in scoping requests as context (optional). Used only to
	 *            support the rare special case of exporting variables with "export =" from .d.ts files:
	 *
	 *            <pre>
	 *            // lib.d.ts
	 *            declare interface Ifc {
	 *                m(): void;
	 *            }
	 *            declare const c: Ifc;
	 *            export = c;
	 *            </pre>
	 *
	 *            In such a case, the members of the type of variable {@code c} are directly available as exported
	 *            elements and computing those members requires an instance of {@link MemberAccess} to be passed to
	 *            {@link MemberScopingHelper#createMemberScope(TypeRef, MemberAccess, boolean, boolean, boolean)
	 *            MemberScopingHelper}. If this argument is absent, support for this special case will be turned off.
	 */
	public Iterable<IEObjectDescription> getExportedElements(AbstractNamespace namespace, N4JSResource contextResource,
			Optional<MemberAccess> memberAccess, boolean includeHollows, boolean includeValueOnlyElements) {

		CollectionInfo info = new CollectionInfo(namespace, contextResource, memberAccess, includeHollows,
				includeValueOnlyElements);

		doCollectElements(namespace, info);

		if (DeclMergingUtils.mayBeMerged(namespace)) {
			List<AbstractNamespace> mergedNamespaces = declMergingHelper.getMergedElements(info.contextResource,
					namespace);
			for (AbstractNamespace mergedNamespace : mergedNamespaces) {
				doCollectElements(mergedNamespace, info);
			}
		}

		if (ResourceType.getResourceType(namespace) == ResourceType.DTS) {
			return Iterables.concat(
					declMergingHelper.chooseRepresentatives(info.visible),
					declMergingHelper.chooseRepresentatives(info.invisible));
		}
		return Iterables.concat(info.visible, info.invisible);
	}

	private void doCollectElements(AbstractNamespace namespace, CollectionInfo info) {

		if (namespace instanceof TModule) {
			handleExportEquals((TModule) namespace, info);
		}

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
					if (DeclMergingUtils.mayBeMerged(exportedModule)) {
						List<AbstractNamespace> mergedNamespaces = declMergingHelper.getMergedElements(
								info.contextResource, exportedModule);
						for (AbstractNamespace mergedNamespace : mergedNamespaces) {
							doCollectElements(mergedNamespace, info);
						}
					}
				}
			}
		}

		// special handling of non-exported elements
		if (namespace instanceof TNamespace && namespace.eResource() == info.contextResource) {
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

		boolean include = N4JSLanguageUtils.checkInclude(exportedElem,
				info.includeHollows, info.includeValueOnlyElements, variantHelper);

		if (include) {
			TypeVisibility visibility = isVisible(info.contextResource, exportedElem);
			if (visibility.visibility) {
				info.visible.add(createObjectDescription(exportedName, exportedElem));
			} else {
				info.invisible.add(new InvisibleTypeOrVariableDescription(
						createObjectDescription(exportedName, exportedElem),
						visibility.accessModifierSuggestion));
			}
		} else {
			if (exportedElem instanceof Type && !(exportedElem instanceof TFunction)) {
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

	private void handleExportEquals(TModule module, CollectionInfo info) {
		Optional<List<IdentifiableElement>> exportEqualsElems = ExportedElementsUtils
				.getElementsExportedViaExportEquals(module);
		if (exportEqualsElems.isPresent()) {

			boolean haveDefaultExport = false;

			for (IdentifiableElement elem : exportEqualsElems.get()) {
				if (elem instanceof TNamespace) {

					doCollectElements((TNamespace) elem, info);

				} else {

					boolean isFunction = elem instanceof TFunction;
					boolean isVariable = !isFunction && elem instanceof TVariable;
					if (isFunction || isVariable) {

						if (!haveDefaultExport) {
							doCollectElement("default", (TExportableElement) elem, info);
							haveDefaultExport = true;
						}

						if (isVariable) {
							doCollectMembersOfVariableAsElements((TVariable) elem, info);
						}
					}
				}
			}
		}
	}

	/**
	 * See the documentation of parameter {@code memberAccess} in
	 * {@link #getExportedElements(AbstractNamespace, N4JSResource, Optional, boolean, boolean) #getExportedElements()}.
	 */
	private void doCollectMembersOfVariableAsElements(TVariable variable, CollectionInfo info) {
		if (!info.memberAccess.isPresent()) {
			return; // support for this case is turned off
		}
		if (!info.includeValueOnlyElements) {
			// fields/accessors are like variables and methods are like declared functions,
			// so we are about to add value-only elements
			// -> bail out if value-only elements are not desired
			return;
		}
		TypeRef typeRef = variable.getTypeRef();
		if (typeRef != null) {
			IScope scope = memberScopingHelper.createMemberScope(typeRef, info.memberAccess.get(), false, false, false);
			if (scope != null) {
				Iterables.addAll(info.visible, scope.getAllElements());
			}
		}
	}

	private TypeVisibility isVisible(Resource contextResource, TExportableElement elem) {
		if (elem instanceof TMember && ResourceType.getResourceType(elem) == ResourceType.DTS) {
			return new TypeVisibility(true);
		} else if (elem instanceof Type) {
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
