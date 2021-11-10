/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.scoping.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.n4JS.VariableEnvironmentElement;
import org.eclipse.n4js.n4JS.util.N4JSSwitch;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.xtext.util.IResourceScopeCache;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Helper to create scopes for {@link VariableEnvironmentElement}s.
 */
@Singleton
public class SourceElementExtensions {

	@Inject
	IResourceScopeCache cache;

	@Inject
	JavaScriptVariantHelper jsVariantHelper;

	/**
	 * Collects all elements visible from the given element. This will include the element itself, if it is either a
	 * {@link FunctionExpression} or a {@link IdentifiableElement}. Note that a {@code N4Class} is not variable
	 * environment, so you cannot scope elements in its content. All the children of given element will also be searched
	 * for visible identifiable elements.
	 *
	 * @param element
	 *            the given element that will be walked through
	 * @return the list of EObjects visible
	 */
	public List<IdentifiableElement> collectVisibleIdentifiableElements(VariableEnvironmentElement element) {
		Pair<String, VariableEnvironmentElement> key = Pair.of("collectVisibleIdentifiableElements", element);
		return cache.get(key, element.eResource(), () -> doCollectVisibleIdentifiableElements(element));
	}

	/**
	 * collect only the local arguments variable - if available.
	 *
	 * @param element
	 *            a possible provider of an arguments variable (function,function-expression, getter/setter, method)
	 * @return list with single entry of an arguments variable or empty list.
	 */
	public List<IdentifiableElement> collectLocalArguments(VariableEnvironmentElement element) {
		Pair<String, VariableEnvironmentElement> key = Pair.of("collectLocalArguments", element);
		return cache.get(key, element.eResource(), () -> doCollectLocalArguments(element));
	}

	private List<IdentifiableElement> doCollectVisibleIdentifiableElements(VariableEnvironmentElement element) {
		List<IdentifiableElement> result = new ArrayList<>();
		if (element instanceof IdentifiableElement) {
			result.add((IdentifiableElement) element);

		} else if (element instanceof FunctionExpression && ((FunctionExpression) element).getName() != null) {
			collectVisibleTypedElement((FunctionExpression) element, result);
		}

		List<IdentifiableElement> visibleIdentifiableElements = doCollectVisibleIdentifiableElements(element,
				element, true);

		result.addAll(visibleIdentifiableElements);

		return result;
	}

	private List<IdentifiableElement> doCollectLocalArguments(VariableEnvironmentElement element) {
		List<IdentifiableElement> result = new ArrayList<>();
		if (element instanceof FunctionOrFieldAccessor) {
			if (!(element instanceof ArrowFunction)) {
				result.add(((FunctionOrFieldAccessor) element).getLocalArgumentsVariable());
			}
		}
		return result;
	}

	/**
	 * Walk through all contents list of the given element. If the entry in the list is a N4ClassDefinition, a
	 * N4EnumDeclaration, a ExportedVariableDeclaration or a FunctionDefinition its children are walked, too. If the
	 * entry in the list is a IdentifiableElement this is also collected. If the entry in the list is a before mentioned
	 * element or a Expression the iterator is pruned as it is clear that below these elements no further visible
	 * identifiable elements can be found.
	 */
	private List<IdentifiableElement> doCollectVisibleIdentifiableElements(VariableEnvironmentElement start,
			EObject element, boolean includeBlockScopedElements) {

		if (includeBlockScopedElements) {
			return doCollectVisibleIdentifiableElementsUncached(start, element, includeBlockScopedElements);

		} else {
			return cache.get(Pair.of("doCollectVisibleIdentifiableElements?includeBlockScopedElements=false", element),
					element.eResource(), () -> {
						return doCollectVisibleIdentifiableElementsUncached(start, element, includeBlockScopedElements);
					});
		}
	}

	private List<IdentifiableElement> doCollectVisibleIdentifiableElementsUncached(VariableEnvironmentElement start,
			EObject element, boolean includeBlockScopedElements) {

		List<IdentifiableElement> validIEs = new ArrayList<>();
		List<IdentifiableElement> invalidIEs = new ArrayList<>();
		TreeIterator<EObject> allContents = element.eAllContents();
		VEESwitch veeSwitch = new VEESwitch(start, includeBlockScopedElements, allContents, validIEs, invalidIEs);
		while (allContents.hasNext()) {
			EObject next = allContents.next();
			veeSwitch.doSwitch(next);
		}
		validIEs.addAll(invalidIEs);
		return validIEs;
	}

	private class VEESwitch extends N4JSSwitch<Boolean> {
		final VariableEnvironmentElement start;
		final boolean includeBlockScopedElements;
		final TreeIterator<EObject> allContents;
		final List<? super IdentifiableElement> validIEs;
		final List<? super IdentifiableElement> invalidIEs;

		VEESwitch(VariableEnvironmentElement start, boolean includeBlockScopedElements,
				TreeIterator<EObject> allContents, List<? super IdentifiableElement> validIEs,
				List<? super IdentifiableElement> invalidIEs) {

			this.start = start;
			this.includeBlockScopedElements = includeBlockScopedElements;
			this.allContents = allContents;
			this.validIEs = validIEs;
			this.invalidIEs = invalidIEs;
		}

		@Override
		public Boolean caseN4ClassDeclaration(N4ClassDeclaration feature) {
			N4ClassDeclaration nonNullClassDecl = feature;
			Type polyfilledOrOriginalType = getTypeOrPolyfilledType(feature);
			if (polyfilledOrOriginalType instanceof TClass) {
				TClass polyfilledOrOriginalTypeCasted = (TClass) polyfilledOrOriginalType;
				validIEs.add(polyfilledOrOriginalTypeCasted);
			} else {
				collectVisibleTypedElement(nonNullClassDecl, validIEs);
			}

			allContents.prune();
			return true;
		}

		@Override
		public Boolean caseN4InterfaceDeclaration(N4InterfaceDeclaration feature) {
			collectVisibleTypedElement(feature, invalidIEs); // shapes will be validated to errors in VeeScopeValidator
			allContents.prune();
			return true;
		}

		@Override
		public Boolean caseN4EnumDeclaration(N4EnumDeclaration feature) {
			collectVisibleTypedElement(feature, validIEs);
			allContents.prune();
			return true;
		}

		@Override
		public Boolean caseN4TypeAliasDeclaration(N4TypeAliasDeclaration feature) {
			collectVisibleTypedElement(feature, invalidIEs); // will be validated to errors in VeeScopeValidator
			allContents.prune();
			return true;
		}

		@Override
		public Boolean caseFunctionDeclaration(FunctionDeclaration feature) {
			collectVisibleTypedElement(feature, validIEs);
			allContents.prune();
			return true;
		}

		@Override
		public Boolean caseExportedVariableDeclaration(ExportedVariableDeclaration feature) {
			if (feature.getName() == null) {
				return true;
			}

			// this special case is required (in addition to case for IdentifiableElement below), to make sure
			// the TModule element is added to 'addHere', not the AST node (as is done for non-exported or
			// non-top-level variables)
			collectVisibleVariable(feature, validIEs);
			allContents.prune();
			return true;
		}

		@Override
		public Boolean caseN4TypeVariable(N4TypeVariable feature) {
			// exclude type variables here, they can only be referred to as types
			return true;
		}

		@Override
		public Boolean caseIdentifiableElement(IdentifiableElement feature) {
			if (N4JSASTUtils.isBlockScoped(feature)) {
				// let, const
				if (includeBlockScopedElements) {
					validIEs.add(feature);
				}
			} else {
				// var
				if (belongsToScope(feature, start)) {
					validIEs.add(feature);
				}
			}
			allContents.prune();
			return true;
		}

		@Override
		public Boolean caseBlock(Block feature) {
			// continue inside block, but without adding block-scoped elements
			if (includeBlockScopedElements) {
				validIEs.addAll(doCollectVisibleIdentifiableElements(start, feature, false));
				allContents.prune();
			}
			return true;
		}

		@Override
		public Boolean caseVariableEnvironmentElement(VariableEnvironmentElement feature) {
			// ForStatement, WithStatement, CatchBlock (note: CatchBlock is not a Block!)
			// a new, nested variable environment starts at this point
			// -> continue with children, but without adding block-scoped elements
			if (includeBlockScopedElements) {
				validIEs.addAll(doCollectVisibleIdentifiableElements(start, feature, false));
				allContents.prune();
			}
			return true;
		}

		@Override
		public Boolean caseExpression(Expression feature) {
			// optimization:
			// variable declarations are statements and expression don't ever contain statements
			allContents.prune();
			return true;
		}
	}

	/** @return the defined type of the given element or the declared type of the corresponding polyfilled class */
	public Type getTypeOrPolyfilledType(TypeDefiningElement tde) {
		if (tde instanceof N4ClassifierDeclaration) {
			N4ClassifierDeclaration n4cd = (N4ClassifierDeclaration) tde;
			if (N4JSLanguageUtils.isNonStaticPolyfill(n4cd) || N4JSLanguageUtils.isStaticPolyfill(n4cd)) {
				// in polyfill? delegate to filled type and its type variables
				TClassifier filledType = (TClassifier) n4cd.getDefinedType();
				Iterator<? extends TClassifier> superClassifiers = filledType.getSuperClassifiers().iterator();
				if (superClassifiers.hasNext()) {
					return superClassifiers.next();
				}
			}
		}
		return tde.getDefinedType();
	}

	static private void collectVisibleTypedElement(TypeDefiningElement element,
			List<? super IdentifiableElement> addHere) {

		collectVisibleIdentifiableElement(element, addHere, e -> e.getDefinedType());
	}

	static private void collectVisibleVariable(ExportedVariableDeclaration element,
			List<? super IdentifiableElement> addHere) {

		collectVisibleIdentifiableElement(element, addHere, e -> e.getDefinedVariable());
	}

	/**
	 * Determines the defined for the given element, if there is no one, it is tried to infer it via
	 * {@link N4JSTypeSystem#tau(TypableElement)}. If a type could be inferred or was already there it will be
	 * collected.
	 */
	static private <T extends TypableElement> void collectVisibleIdentifiableElement(
			T element, List<? super IdentifiableElement> addHere, Function<T, IdentifiableElement> calculateType) {

		if (calculateType != null) {
			IdentifiableElement ie = calculateType.apply(element);
			if (ie != null) {
				addHere.add(ie);
			}
		}
	}

	static private boolean belongsToScope(IdentifiableElement elem, VariableEnvironmentElement scope) {
		return N4JSASTUtils.getScope(elem) == scope;
	}
}
