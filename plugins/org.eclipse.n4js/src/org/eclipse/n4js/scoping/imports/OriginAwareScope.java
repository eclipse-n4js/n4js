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
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.scoping.UsageAwareObjectDescription;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.util.IResourceScopeCache;

import com.google.common.collect.Iterables;

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
				// return usage aware description
				return getUsageAwareDescription(ret);
			}
		}
		return ret;
	}

	@Override
	public Iterable<IEObjectDescription> getElements(QualifiedName name) {
		return Iterables.transform(delegatee.getElements(name), this::getUsageAwareDescription);
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

	private IEObjectDescription getUsageAwareDescription(IEObjectDescription original) {
		ImportSpecifier origin = origins.get(original);
		if (origin != null) {
			EObject script = EcoreUtil.getRootContainer(origin);
			if ((script instanceof Script) && ((Script) script).isFlaggedUsageMarkingFinished()) {
				// do nothing as linking phase is over
			} else {
				// TODO: Mark Twin-Ambiguous as well as used.
				if (original instanceof AmbiguousImportDescription) {
					AmbiguousImportDescription ambiguousImportDescription = (AmbiguousImportDescription) original;
					return new UsageAwareAmbiguousImportDescription(ambiguousImportDescription, origin);
				} else if (original instanceof PlainAccessOfAliasedImportDescription) {
					PlainAccessOfAliasedImportDescription plainAccess = (PlainAccessOfAliasedImportDescription) original;
					return new UsageAwarePlainAccessOfAliasedImportDescription(plainAccess, origin);
				} else {
					if (IEObjectDescriptionWithError.isErrorDescription(original)) {
						return new UsageAwareImportDescriptionWithError<>(
								(IEObjectDescriptionWithError) original, origin);
					} else {
						return new UsageAwareImportDescription<>(original, origin);
					}

				}
			}
		}
		return original;
	}

	/**
	 * A {@link UsageAwareObjectDescription} that invokes
	 * {@link OriginAwareScope#markImportSpecifierAsUsed(ImportSpecifier)} in case a name is bound using this
	 * description.
	 */
	private class UsageAwareImportDescription<T extends IEObjectDescription> extends UsageAwareObjectDescription<T> {
		/**
		 *
		 */
		public UsageAwareImportDescription(T delegate, ImportSpecifier origin) {
			super(delegate, origin);
		}

		@Override
		public void markAsUsed() {
			markImportSpecifierAsUsed(getOrigin());
		}

		@Override
		public void recordOrigin(IdentifierRef identRef) {
			if (identRef.getOriginImport() != null) {
				return;
			}
			EcoreUtilN4.doWithDeliver(false, () -> {
				identRef.setOriginImport(getOrigin());
			}, identRef);
		}

		@Override
		public String toString() {
			return getDelegate().toString();
		}
	}

	private class UsageAwareImportDescriptionWithError<T extends IEObjectDescriptionWithError>
			extends UsageAwareImportDescription<T> implements IEObjectDescriptionWithError {

		/** */
		public UsageAwareImportDescriptionWithError(T delegate, ImportSpecifier origin) {
			super(delegate, origin);
		}

		@Override
		public String getMessage() {
			return getDelegate().getMessage();
		}

		@Override
		public String getIssueCode() {
			return getDelegate().getIssueCode();
		}

	}

	private class UsageAwarePlainAccessOfAliasedImportDescription
			extends UsageAwareImportDescriptionWithError<PlainAccessOfAliasedImportDescription> {

		/**
		 */
		public UsageAwarePlainAccessOfAliasedImportDescription(PlainAccessOfAliasedImportDescription delegate,
				ImportSpecifier origin) {
			super(delegate, origin);
		}

		@Override
		public void markAsUsed() {
			super.markAsUsed();

			ImportSpecifier plainImport = origins.get(this.getDelegate().delegate());
			if (plainImport != null)
				markImportSpecifierAsUsed(plainImport);
		}

	}

	private class UsageAwareAmbiguousImportDescription
			extends UsageAwareImportDescriptionWithError<AmbiguousImportDescription> {

		public UsageAwareAmbiguousImportDescription(AmbiguousImportDescription delegate, ImportSpecifier origin) {
			super(delegate, origin);
		}

		@Override
		public void markAsUsed() {
			super.markAsUsed();

			for (ImportSpecifier ispec : getDelegate().getOriginatingImports()) {
				markImportSpecifierAsUsed(ispec);
			}
		}

	}

	/**
	 * Mark the import specifier as used without throwing an event. This preserves the cached data in the
	 * {@link IResourceScopeCache}.
	 *
	 * @param origin
	 *            the import specific to be marked as used. May not be null.
	 */
	private void markImportSpecifierAsUsed(ImportSpecifier origin) {
		boolean wasDeliver = origin.eDeliver();
		try {
			origin.eSetDeliver(false);
			origin.setFlaggedUsedInCode(true);
		} finally {
			origin.eSetDeliver(wasDeliver);
		}
	}

	@Override
	public String toString() {
		return "OriginAwareScope -> " + delegatee.toString();
	}

}
