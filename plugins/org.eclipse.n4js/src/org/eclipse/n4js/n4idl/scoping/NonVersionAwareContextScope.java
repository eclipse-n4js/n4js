/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4idl.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4idl.N4IDLGlobals;
import org.eclipse.n4js.n4idl.versioning.VersionUtils;
import org.eclipse.n4js.scoping.utils.AbstractDescriptionWithError;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.n4js.validation.ValidatorMessageHelper;
import org.eclipse.n4js.xtext.scoping.FilteringScope;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.inject.Inject;

/**
 * A {@link NonVersionAwareContextScope} which wraps all parent elements that are considered to be {@code @VersionAware}
 * (cf. {@link VersionUtils#isVersionAwareContext(EObject)}), with an issue indicating, that the current context does
 * not allow references to elements in version-aware contexts.
 *
 * This scope wraps filtered description either with
 * {@link IssueCodes#IDL_ELEMENT_CAN_ONLY_BE_REFERENCED_FROM_VA_CONTEXT} if the reference is made from a type-version
 * supporting language variant or with {@link IssueCodes#IDL_INVALID_REFERENCE_TO_VERSION_AWARE_ELEMENT} otherwise.
 *
 * For instantiation please see {@link NonVersionAwareContextScope.Provider}.
 */
public class NonVersionAwareContextScope extends FilteringScope {

	/**
	 * Injectable provider for {@link NonVersionAwareContextScope}.
	 */
	public static final class Provider {
		@Inject
		JavaScriptVariantHelper variantHelper;
		@Inject
		ValidatorMessageHelper messageHelper;

		/**
		 * Returns a new instance of {@link NonVersionAwareContextScope} for the given context wrapping around the given
		 * parent scope.
		 *
		 * @param context
		 *            The context element to provide a scope for.
		 * @param parent
		 *            The parent scope to base this scope on.
		 */
		public NonVersionAwareContextScope get(EObject context, IScope parent) {
			return new NonVersionAwareContextScope(parent, variantHelper.allowVersionedTypes(context),
					variantHelper.getVariantName(context), messageHelper);
		}
	}

	private ValidatorMessageHelper messageHelper;
	private final String variantName;
	/**
	 * Flag which indicates whether the context of this scope supports the declaration of <code>@VersionAware</code>
	 * contexts (cf. {@link N4IDLGlobals#VERSION_AWARENESS_ANNOTATIONS}).
	 */
	private final boolean versionAwareContextSupport;

	/** {@link IEObjectDescriptionWithError} for {@link IssueCodes#IDL_INVALID_REFERENCE_TO_VERSION_AWARE_ELEMENT} */
	public final class InvalidReferenceToVersionAwareElementDescription extends AbstractDescriptionWithError {
		/** Instantiates a new error description based on the given delegate. */
		protected InvalidReferenceToVersionAwareElementDescription(IEObjectDescription delegate) {
			super(delegate);
		}

		@Override
		public String getMessage() {
			EObject element = this.getEObjectOrProxy();
			return IssueCodes.getMessageForIDL_INVALID_REFERENCE_TO_VERSION_AWARE_ELEMENT(
					messageHelper.description(element), variantName);
		}

		@Override
		public String getIssueCode() {
			return IssueCodes.IDL_INVALID_REFERENCE_TO_VERSION_AWARE_ELEMENT;
		}

	}

	/**
	 * {@link IEObjectDescriptionWithError} for {@link IssueCodes#IDL_ELEMENT_CAN_ONLY_BE_REFERENCED_FROM_VA_CONTEXT}
	 */
	public final class ElementOnlyReferenceFromVAContextDescription extends AbstractDescriptionWithError {
		/** Instantiates a new error description based on the given delegate. */
		protected ElementOnlyReferenceFromVAContextDescription(IEObjectDescription delegate) {
			super(delegate);
		}

		@Override
		public String getMessage() {
			EObject element = this.getEObjectOrProxy();
			return IssueCodes.getMessageForIDL_ELEMENT_CAN_ONLY_BE_REFERENCED_FROM_VA_CONTEXT(
					messageHelper.description(element));
		}

		@Override
		public String getIssueCode() {
			return IssueCodes.IDL_ELEMENT_CAN_ONLY_BE_REFERENCED_FROM_VA_CONTEXT;
		}

	}

	/**
	 * For external instantiation, please see {@link NonVersionAwareContextScope.Provider}.
	 *
	 * Instantiates a new {@link FailedToInferContextVersionWrappingScope} that wraps around the given scope and
	 * decorates all of its descriptions.
	 *
	 * @param parent
	 *            The parent scope to wrap around
	 * @param versionAwareContextSupport
	 *            Specifies whether the scope context allows for <code>@VersionAware</code> contexts (e.g. N4IDL).
	 * @param variantName
	 *            The user-facing name of the current language variant
	 * @param messageHelper
	 *            An injected instance of {@link ValidatorMessageHelper}
	 *
	 */
	private NonVersionAwareContextScope(IScope parent, boolean versionAwareContextSupport,
			String variantName, ValidatorMessageHelper messageHelper) {
		super(parent, d -> {
			EObject element = d.getEObjectOrProxy();
			if (!element.eIsProxy()) {
				// wrap all elements that are in a version-aware context
				return !VersionUtils.isVersionAwareContext(element);
			} else {
				// leave all non-version-aware descriptions un-decorated
				return true;
			}
		});

		// store service dependencies in fields
		this.messageHelper = messageHelper;
		this.variantName = variantName;

		// store whether context supports versioned types
		this.versionAwareContextSupport = versionAwareContextSupport;

	}

	@Override
	protected IEObjectDescriptionWithError wrapFilteredDescription(IEObjectDescription description) {
		// if there is no support for versioned types
		if (!versionAwareContextSupport) {
			return new InvalidReferenceToVersionAwareElementDescription(description);
		} else { // otherwise give a more specific error message
			return new ElementOnlyReferenceFromVAContextDescription(description);
		}
	}
}
