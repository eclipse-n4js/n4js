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
package org.eclipse.n4js.validation.validators

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.VersionedElement
import org.eclipse.n4js.n4idl.versioning.VersionHelper
import org.eclipse.n4js.n4idl.versioning.VersionUtils
import org.eclipse.n4js.ts.typeRefs.VersionedReference
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

/**
 * Validate the use of version in N4IDL
 */
class N4IDLVersionValidator extends AbstractN4JSDeclarativeValidator {
	@Inject
	private VersionHelper versionHelper;

	@Inject
	private JavaScriptVariantHelper variantHelper;

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	override void register(EValidatorRegistrar registrar) {
		// nop
	}

	/** Check that the requested version does not exceed the maximal version of the current context. */
	@Check
	def checkVersion(VersionedReference ref) {
		if (ref === null)
			return
		val context = ref.eContainer
		if (context !== null) {
			val requestedVersion = if (ref.requestedVersion === null) 0 else ref.requestedVersion.intValue
			val maxVersion = versionHelper.computeMaximumVersion(context)
			// The referenced version cannot exceed the maximum version of the context
			if (requestedVersion > maxVersion) {
				val message = IssueCodes.getMessageForIDL_INVALID_VERSION(requestedVersion, maxVersion);
				addIssue(
					message,
					context,
					ref.eContainingFeature, IssueCodes.IDL_INVALID_VERSION
				);
			}
		}
	}

	/**
	 * Adds an issue in case of missing support for type versions in
	 * the current JavaScript variant.
	 *
	 * This validation only applies to {@link VersionedElement}s (e.g. classifiers, enums).
	 */
	@Check
	def checkVersionedElementsSupported(VersionedElement versionedElement) {
		if (!variantHelper.allowVersionedTypes(versionedElement) &&
			// check for non-zero version
			VersionUtils.isVersioned(versionedElement)
		) {
			addIssueForUnsupportedVersionedTypes(versionedElement);
		}
	}

	/**
	 * Adds an issue in case of missing support for type versions in
	 * the current JavaScript variant.
	 *
	 * This validation only applies to {@link VersionedReference}s (e.g. TypeRef, IdentifierRef).
	 */
	@Check
	def checkVersionedReferencesSupported(VersionedReference versionedReference) {
		if (!variantHelper.allowVersionedTypes(versionedReference) &&
			// check for explicitly requested version
			versionedReference.hasRequestedVersion
		) {
			addIssueForUnsupportedVersionedTypes(versionedReference);
		}
	}

	/**
	 * Adds an issue for the element, indicating that versioned types
	 * are not supported in the current JavaScript variant.
	 */
	private def void addIssueForUnsupportedVersionedTypes(EObject element) {
		val variantName = variantHelper.getVariantName(element);

		addIssue(IssueCodes.getMessageForIDL_VERSIONED_TYPES_NOT_SUPPORTED(variantName),
			element,
			IssueCodes.IDL_VERSIONED_TYPES_NOT_SUPPORTED
		)
	}

	/**
	 * Adds an issue in case of missing support for type versions and therefore migrations in
	 * the current JavaScript variant.
	 */
	@Check
	def checkMigrationAnnotationSupport(FunctionDeclaration migrationDeclaration) {
		if (AnnotationDefinition.MIGRATION.hasAnnotation(migrationDeclaration) &&
			// annotations are enabled in the current variant
			variantHelper.allowAnnotation(migrationDeclaration) &&
			// versioned types are disabled in the current variant
			!variantHelper.allowVersionedTypes(migrationDeclaration)
		) {
			val variantName = variantHelper.getVariantName(migrationDeclaration);

			addIssue(
				IssueCodes.getMessageForIDL_MIGRATIONS_NOT_SUPPORTED(variantName),
				migrationDeclaration,
				IssueCodes.IDL_MIGRATIONS_NOT_SUPPORTED
			);
		}
	}
}
