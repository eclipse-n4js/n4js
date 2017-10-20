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
package org.eclipse.n4js.scoping.imports;

import java.util.HashMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.util.IResourceScopeCache;

/**
 * Delegating Scope holding a map of {@link IEObjectDescription} to {@link ImportSpecifier}. Queries via
 * {@link #getSingleElement(QualifiedName)} are assumed to come from linking and as such the corresponding
 * {@link ImportSpecifier} gets marked as used ({@link ImportSpecifier#isFlaggedUsedInCode()}).
 * <p>
 * The scope is transparent, that is, the "parent" scope of this scope is the delegate's parent. Thus it also "inherits"
 * the specific scoping semantic of its delegate.
 *
 * All other Methods just delegate.
 */
public class OriginAwareScope implements IScope {

	private final IScope delegatee;

	private final HashMap<IEObjectDescription, ImportSpecifier> origins;

	/**
	 *
	 * @param scope
	 *            to delegate to
	 * @param map
	 *            Map of {@link ImportSpecifier}-origins for {@link IEObjectDescription}'s
	 */
	OriginAwareScope(IScope scope, HashMap<IEObjectDescription, ImportSpecifier> map) {
		this.delegatee = scope;
		this.origins = map;
	}

	/**
	 * Enhanced query-Method marking the originating import as used.
	 *
	 * @see org.eclipse.xtext.scoping.IScope#getSingleElement(org.eclipse.xtext.naming.QualifiedName)
	 */
	@Override
	public IEObjectDescription getSingleElement(QualifiedName name) {
		IEObjectDescription ret = delegatee.getSingleElement(name);
		if (ret == null)
			return null;

		ImportSpecifier origin = origins.get(ret);
		if (origin != null) {
			EObject script = EcoreUtil.getRootContainer(origin);
			if ((script instanceof Script) && ((Script) script).isFlaggedUsageMarkingFinished()) {
				// do nothing as linking phase is over
			} else {
				markAsUsed(origin);

				// TODO: Mark Twin-Ambiguous as well as used.
				if (ret instanceof AmbiguousImportDescription) {
					AmbiguousImportDescription ambiguousImportDescription = (AmbiguousImportDescription) ret;
					for (ImportSpecifier ispec : ambiguousImportDescription.getOriginatingImports()) {
						markAsUsed(ispec);
					}
				} else if (ret instanceof PlainAccessOfAliasedImportDescription) {
					PlainAccessOfAliasedImportDescription plainAccess = (PlainAccessOfAliasedImportDescription) ret;
					ImportSpecifier plainImport = origins.get(plainAccess.delegate());
					if (plainImport != null)
						markAsUsed(plainImport);
				}
			}
		}
		return ret;
	}

	/**
	 * Mark the import specifier as used without throwing an event. This preserves the cached data in the
	 * {@link IResourceScopeCache}.
	 *
	 * @param origin
	 *            the import specific to be marked as used. May not be null.
	 */
	private void markAsUsed(ImportSpecifier origin) {
		boolean wasDeliver = origin.eDeliver();
		try {
			origin.eSetDeliver(false);
			origin.setFlaggedUsedInCode(true);
		} finally {
			origin.eSetDeliver(wasDeliver);
		}
	}

	@Override
	public Iterable<IEObjectDescription> getElements(QualifiedName name) {
		return delegatee.getElements(name);
	}

	@Override
	public IEObjectDescription getSingleElement(EObject object) {
		return delegatee.getSingleElement(object);
	}

	@Override
	public Iterable<IEObjectDescription> getElements(EObject object) {
		return delegatee.getElements(object);
	}

	@Override
	public Iterable<IEObjectDescription> getAllElements() {
		return delegatee.getAllElements();
	}

	@Override
	public String toString() {
		return "OriginAwareScope -> " + delegatee.toString();
	}

}
