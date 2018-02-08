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
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.ScriptElement
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.n4JS.VersionedElement
import org.eclipse.n4js.n4idl.versioning.VersionHelper
import org.eclipse.n4js.n4idl.versioning.VersionUtils
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.typeRefs.VersionedReference
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

/**
 * Validate the use of version in N4IDL
 */
class N4IDLValidator extends AbstractN4JSDeclarativeValidator {
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

			// if no context-version can be determined, do not validate anything
			if (!maxVersion.present) {
				return;
			}
			// The referenced version cannot exceed the maximum version of the context
			if (requestedVersion > maxVersion.get()) {
				val message = IssueCodes.getMessageForIDL_INVALID_VERSION(requestedVersion, maxVersion.get());
				addIssue(
					message,
					context,
					ref.eContainingFeature, IssueCodes.IDL_INVALID_VERSION
				);
			}
		}
	}

	/** Check that the current context allows for the explicit declaration of versions in references. */
	@Check
	def checkExplicitVersionDeclaration(VersionedReference ref) {
		// this validation is only active for variants that actually support versioned types
		if (!variantHelper.allowVersionedTypes(ref)) {
			return;
		}

		val containerFunctionDeclaration = EcoreUtil2.getContainerOfType(ref, FunctionDeclaration);
		if (null === containerFunctionDeclaration ||
			!AnnotationDefinition.MIGRATION.hasAnnotation(containerFunctionDeclaration)
		) {
			val message = IssueCodes.messageForIDL_EXPLICIT_VERSION_DECLARATION_NOT_ALLOWED;
			addIssue(message, ref, TypeRefsPackage.Literals.VERSIONED_REFERENCE__REQUESTED_VERSION,
				IssueCodes.IDL_EXPLICIT_VERSION_DECLARATION_NOT_ALLOWED);
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

	/**
	 * Checks whether the current {@link JavaScriptVariant} allows for top-level statements and issues errors
	 * accordingly.
	 */
	@Check
	public def void checkTopLevelElements(Script script) {
		if (!variantHelper.allowTopLevelStatements(script)) {
			script.getScriptElements().stream().forEach[handleScriptElement(it)];
		}
	}

	/**
	 * Adds an issue for the given element if it is considered a top-level statement (also see {@link #isStatement}).
	 */
	private def void handleScriptElement(ScriptElement element) {
		if (isStatement(element) && element.eContainer().eContainer() === null) {
			val variantName = variantHelper.getVariantName(element);
			addIssue(IssueCodes.getMessageForAST_TOP_LEVEL_STATEMENTS(variantName), element,
					IssueCodes.AST_TOP_LEVEL_STATEMENTS);
		}
	}

	/**
	 * Returns {@code true} if the given element is a considered a statement.
	 */
	private def boolean isStatement(EObject element) {
		return element instanceof Statement && !(element instanceof FunctionDeclaration);
	}
}
