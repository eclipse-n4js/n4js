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
package org.eclipse.n4js.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.resource.XpectAwareFileExtensionCalculator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * This class provides the base implementation for {@link JavaScriptVariantHelper} following chain of responsibility
 * pattern. For a Javascript sub-language/variant with new file extension, a subclass should extend this class and
 * defines the validations for that should be activated or activated for that file extension. This can be done by
 * calling the method {@link BaseJavaScriptVariantHelper#addEntry(String, ValidationFeature, Object)} in the
 * constructor.
 */
@Singleton
public class BaseJavaScriptVariantHelper implements JavaScriptVariantHelper {

	/**
	 * Literal value to indicate strict mode: "use strict" (or 'use strict')
	 */
	public final static String STRICT_MODE_LITERAL_VALUE = "use strict";

	/**
	 * Default variant mode: JS
	 */
	public static final String EXT_JS = N4JSGlobals.JS_FILE_EXTENSION;

	/**
	 * This file extension calculator calculates file extensions of resource
	 */
	@Inject
	protected XpectAwareFileExtensionCalculator fileExtensionCalculator;

	/**
	 * This class defines all validation features.
	 */
	private static class ValidationFeatureBase {
		// This empty class allows for defining validation features as classes instead of enums.
	}

	static class ValidationFeature<T> extends ValidationFeatureBase {
		private final T defaultValue;

		public ValidationFeature(T defaultValue) {
			this.defaultValue = defaultValue;
		}

		public T getDefaultValue() {
			return this.defaultValue;
		}

		@SuppressWarnings("unchecked")
		public T get(Map<FileExtensionValidationFeaturePair, Object> table, String extension) {
			return (T) table.get(new FileExtensionValidationFeaturePair(extension, this));
		}
	}

	/**
	 * A user-faced name for this JavaScript variant.
	 */
	public static final ValidationFeature<String> VARIANT_NAME = new ValidationFeature<>(
			"<Unknown JavaScript Variant>");

	/**
	 * Dynamic pseudo scope should be activated?
	 */
	public static final ValidationFeature<Boolean> DYNAMIC_PSEUDO_SCOPE = new ValidationFeature<>(true);
	/**
	 * Missing implementation is allowed?
	 */
	public static final ValidationFeature<Boolean> ALLOW_MISSING_IMPLEMENTATION = new ValidationFeature<>(false);
	/**
	 * Override annotation should be checked?
	 */
	public static final ValidationFeature<Boolean> CHECK_OVERRIDE_ANNOTATION = new ValidationFeature<>(false);
	/**
	 * Type declaration should be checked?
	 */
	public static final ValidationFeature<Boolean> CHECK_TYPE_DECLARATION = new ValidationFeature<>(false);
	/**
	 * Member declaration should be checked?
	 */
	public static final ValidationFeature<Boolean> CHECK_MEMBER_DECLARATION = new ValidationFeature<>(false);
	/**
	 * Variable should be checked?
	 */
	public static final ValidationFeature<Boolean> CHECK_VARIABLE = new ValidationFeature<>(false);
	/**
	 * Method reference should be checked?
	 */
	public static final ValidationFeature<Boolean> CHECK_METHOD_REFERENCE = new ValidationFeature<>(false);
	/**
	 * Call expression should be checked?
	 */
	public static final ValidationFeature<Boolean> CHECK_CALL_EXPRESSION = new ValidationFeature<>(false);
	/**
	 * New expression should be checked?
	 */
	public static final ValidationFeature<Boolean> CHECK_NEW_EXPRESSION = new ValidationFeature<>(false);
	/**
	 * Indexed access expression should be checked?
	 */
	public static final ValidationFeature<Boolean> CHECK_INDEXED_ACCESS_EXPRESSION = new ValidationFeature<>(false);
	/**
	 * Function name should be checked?
	 */
	public static final ValidationFeature<Boolean> CHECK_FUNCTION_NAME = new ValidationFeature<>(false);
	/**
	 * Function return should be checked?
	 */
	public static final ValidationFeature<Boolean> CHECK_FUNCTION_RETURN = new ValidationFeature<>(false);
	/**
	 * Function expression in expression statement should be checked?
	 */
	public static final ValidationFeature<Boolean> CHECK_FUNCTION_EXPRESSION_IN_EXRESSION_STATEMENTMEMBER_DECLARATION = new ValidationFeature<>(
			true);
	/**
	 * Constant has initializer?
	 */
	public static final ValidationFeature<Boolean> CONSTANT_HAS_INITIALIZER = new ValidationFeature<>(false);
	/**
	 * Wrong read/write should be allowed?
	 */
	public static final ValidationFeature<Boolean> ALLOW_WRONG_READ_WRITE = new ValidationFeature<>(true);
	/**
	 * Type inference should be doomed
	 */
	public static final ValidationFeature<Boolean> DOOM_TYPE_INTERFENCE = new ValidationFeature<>(true);
	/**
	 * Annotation should be allowed
	 */
	public static final ValidationFeature<Boolean> ALLOW_ANNOTATION = new ValidationFeature<>(false);
	/**
	 * Final fields has initializer?
	 */
	public static final ValidationFeature<Boolean> CHECK_FINAL_FIELDS_IS_INITIALIZED = new ValidationFeature<>(false);
	/**
	 * Name start with dollar should be checked?
	 */
	public static final ValidationFeature<Boolean> CHECK_NAME_START_WITH_DOLLAR = new ValidationFeature<>(false);
	/**
	 * Missing body should be checked?
	 */
	public static final ValidationFeature<Boolean> CHECK_MISSING_BODY = new ValidationFeature<>(true);
	/**
	 * Dynamic types should be enforced?
	 */
	public static final ValidationFeature<Boolean> ENFORCE_DYNAMIC_TYPES = new ValidationFeature<>(true);
	/**
	 * The variant is type aware?
	 */
	public static final ValidationFeature<Boolean> TYPE_AWARE = new ValidationFeature<>(false);
	/**
	 * Exported elements with visibility higher than private should be checked?
	 */
	public static final ValidationFeature<Boolean> CHECK_EXPORTED_WHEN_VISIBILITY_HIGHER_THAN_PRIVATE = new ValidationFeature<>(
			false);
	/**
	 * Variant is external mode?
	 */
	public static final ValidationFeature<Boolean> EXTERNAL_MODE = new ValidationFeature<>(false);
	/**
	 * Variant is N4JS mode?
	 */
	public static final ValidationFeature<Boolean> IS_N4JS_MODE = new ValidationFeature<>(false);
	/**
	 * Variant is plain JS mode?
	 */
	public static final ValidationFeature<Boolean> IS_PLAIN_JS = new ValidationFeature<>(false);
	/**
	 * String representation of variant mode, e.g. "n4js", "js"
	 */
	public static final ValidationFeature<String> VARIANT_MODE_STRINGREP = new ValidationFeature<>(EXT_JS);

	/**
	 * Variant allows for multiple elements with the same qualified name in one scope.
	 */
	public static final ValidationFeature<Boolean> MULTI_QN_SCOPE = new ValidationFeature<>(false);

	/**
	 * Variant allows for the declaration and reference of versioned types and the corresponding declaration of
	 * migrations.
	 */
	public static final ValidationFeature<Boolean> VERSIONED_TYPES = new ValidationFeature<>(false);

	/**
	 * Variant allows for top-level statements in modules.
	 */
	public static final ValidationFeature<Boolean> TOP_LEVEL_STATEMENTS = new ValidationFeature<>(true);

	/**
	 * This class encapsulates a pair of file extension and validation feature and should serve as keys for
	 * {@link JavaScriptVariantHelper}
	 */
	private static class FileExtensionValidationFeaturePair {
		private final String fileExtension;
		private final ValidationFeatureBase validationFeature;

		/**
		 * Constructor
		 *
		 * @param fileExtension
		 *            the file extension in the key
		 * @param validationFeature
		 *            the validation feature in the key
		 */
		public FileExtensionValidationFeaturePair(String fileExtension, ValidationFeatureBase validationFeature) {
			this.fileExtension = fileExtension;
			this.validationFeature = validationFeature;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof FileExtensionValidationFeaturePair) {
				FileExtensionValidationFeaturePair otherKey = (FileExtensionValidationFeaturePair) obj;
				return Objects.equals(fileExtension, otherKey.fileExtension)
						&& Objects.equals(validationFeature, otherKey.validationFeature);
			}
			return false;
		}

		@Override
		public int hashCode() {
			return Objects.hash(fileExtension, validationFeature);
		}
	}

	private final Map<FileExtensionValidationFeaturePair, Object> table = new HashMap<>();
	/**
	 * Next element in the chain of responsibility
	 */
	protected BaseJavaScriptVariantHelper next;

	/**
	 * This constructor registers file extension ".js" as plain JS mode Constructor of sub-classes must call super() for
	 * ".js" to be recognized as plain JS
	 */
	protected BaseJavaScriptVariantHelper() {
		this.addEntry(EXT_JS, IS_PLAIN_JS, true);
	}

	/**
	 * Add maps (file extension, validation feature) -> value
	 *
	 */
	protected <T> void addEntry(String fileExtension, ValidationFeature<T> feature, T value) {
		Objects.requireNonNull(fileExtension);
		Objects.requireNonNull(feature);
		Objects.requireNonNull(value);
		table.put(new FileExtensionValidationFeaturePair(fileExtension, feature), value);
	}

	/**
	 * Return the validation feature
	 *
	 */
	protected <T> T get(String fileExtension, ValidationFeature<T> feature) {
		T result = feature.get(table, fileExtension);
		if (result != null) {
			return result;
		}
		if (next != null) {
			return next.get(fileExtension, feature);
		}
		return feature.getDefaultValue();
	}

	/**
	 * Return true if dynamic pseudo scope should be activated.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean activateDynamicPseudoScope(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), DYNAMIC_PSEUDO_SCOPE);
	}

	/**
	 * Return true if missing implementation is allowed, for instance in external mode.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean allowMissingImplementation(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), ALLOW_MISSING_IMPLEMENTATION);
	}

	/**
	 * Return true if override annotation should be checked, e.g. if mode is N4JS.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean checkOverrideAnnotation(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), CHECK_OVERRIDE_ANNOTATION);
	}

	/**
	 * Return true if type declaration should be checked, e.g. if the mode is N4JS.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean checkTypeDeclaration(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), CHECK_TYPE_DECLARATION);
	}

	/**
	 * Return true if type declaration should be checked, e.g. if the mode is N4JS.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean checkMemberDeclaration(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), CHECK_MEMBER_DECLARATION);
	}

	/**
	 * Return true if variable declaration should be checked, e.g. if the mode is N4JS.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean checkVariable(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), CHECK_VARIABLE);
	}

	/**
	 * Return true if method reference should be checked.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean checkMethodReference(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), CHECK_METHOD_REFERENCE);
	}

	/**
	 * Return true if call expression should be checked.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean checkCallExpression(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), CHECK_CALL_EXPRESSION);
	}

	/**
	 * Return true if new expression should be checked.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean requireCheckNewExpression(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), CHECK_NEW_EXPRESSION);
	}

	/**
	 * Return true if indexed access expression should be checked, only in N4JS mode.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean requireCheckIndexedAccessExpression(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), CHECK_INDEXED_ACCESS_EXPRESSION);
	}

	/**
	 * Return true if function name should be checked.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean requireCheckFunctionName(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), CHECK_FUNCTION_NAME);
	}

	/**
	 * Return true if function return should be checked.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean requireCheckFunctionReturn(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), CHECK_FUNCTION_RETURN);
	}

	/**
	 * Return true if function expression in expression statement should be checked.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean requireCheckFunctionExpressionInExpressionStatement(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj),
				CHECK_FUNCTION_EXPRESSION_IN_EXRESSION_STATEMENTMEMBER_DECLARATION);
	}

	/**
	 * Return true if a constant declaration has an initializer.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean constantHasInitializer(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), CONSTANT_HAS_INITIALIZER);
	}

	/**
	 * Return true if wrong read/write should be allowed, e.g. in plain JS mode.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean allowWrongReadWrite(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), ALLOW_WRONG_READ_WRITE);
	}

	/**
	 * Return true if type inference should be doomed.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean doomTypeInference(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), DOOM_TYPE_INTERFENCE);
	}

	/**
	 * Return true if annotation should be allowed.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean allowAnnotation(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), ALLOW_ANNOTATION);
	}

	/**
	 * Return true if it must be checked that a final field is initialized.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean requireCheckFinalFieldIsInitialized(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), CHECK_FINAL_FIELDS_IS_INITIALIZED);
	}

	/**
	 * Return true if it must be checked if a name starts with dollar.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean requireCheckNameStartsWithDollar(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), CHECK_NAME_START_WITH_DOLLAR);
	}

	/**
	 * Return true if it is required to check if body of a member is missing.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean requireCheckForMissingBody(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), CHECK_MISSING_BODY);
	}

	/**
	 * Return true if it is required to check type matches.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean requireCheckTypeMatchesExpectedType(EObject eobj) {
		return !isUnrestrictedMode(eobj);
	}

	/**
	 * Enforce dynamic types in call cases even without explicit modifier. This is usually the case for plain
	 * ECMAScript.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean enforceDynamicTypes(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), ENFORCE_DYNAMIC_TYPES);
	}

	/**
	 * Returns true if the variant is type aware, N4JS is type aware, plain JS is not.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean isTypeAware(EObject eobj) { // e.g. in N4JS
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), TYPE_AWARE);
	}

	/**
	 * Returns true if the variant has global object.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean hasGlobalObject(EObject eobj) { // e.g. in unrestricted ECMAScript mode
		return isUnrestrictedMode(eobj);
	}

	/**
	 * Return true exported should be checked in case the visibility is higher than private. This is not true for plain
	 * JS files.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean requireCheckExportedWhenVisibilityHigherThanPrivate(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj),
				CHECK_EXPORTED_WHEN_VISIBILITY_HIGHER_THAN_PRIVATE);
	}

	/**
	 * Returns true if the mode is unrestricted.
	 */
	@Override
	public boolean isUnrestrictedMode(EObject eobj) {
		return !isStrictMode(eobj);
	}

	/**
	 * Returns true if the script is defined in a N4JSD module (external mode).
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean isExternalMode(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), EXTERNAL_MODE);
	}

	/**
	 * Returns true if the script is defined in a N4JS module.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 */
	@Override
	public boolean isN4JSMode(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), IS_N4JS_MODE);
	}

	/**
	 * Return true if the script is a plain JS.
	 *
	 * @param eobj
	 *            The EObject providing the context for the check.
	 *
	 */
	@Override
	public boolean isPlainJS(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), IS_PLAIN_JS);
	}

	/**
	 * Returns the variant mode.
	 *
	 * @param eobj
	 *            The EObject providing the context in to find out the variant mode.
	 */
	@Override
	public String variantMode(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), VARIANT_MODE_STRINGREP);
	}

	/**
	 * Returns true if "use strict" is declared. Override this method for sub-languages!
	 */
	@Override
	public boolean isStrictMode(EObject eobj) {
		return JavaScriptVariant.isContainedInStrictFunctionOrScript(eobj);
	}

	/**
	 * Returns {@code true} if the script allows for distinct elements with the same qualified name in a scope.
	 */
	@Override
	public boolean isMultiQNScope(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), MULTI_QN_SCOPE);
	}

	/**
	 * Returns {@code true} if the script allows for the declaration and reference of versioned types as well as
	 * corresponding migrations.
	 */
	@Override
	public boolean allowVersionedTypes(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), VERSIONED_TYPES);
	}

	/**
	 * Returns {@code true} if the script allows for top-level statements as opposed to just type and function
	 * declarations.
	 */
	@Override
	public boolean allowTopLevelStatements(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), TOP_LEVEL_STATEMENTS);
	}

	@Override
	public String getVariantName(EObject eobj) {
		return get(fileExtensionCalculator.getXpectAwareFileExtension(eobj), VARIANT_NAME);
	}

	@Override
	public String getVariantName(String fileExtension) {
		return get(fileExtension, VARIANT_NAME);
	}
}
