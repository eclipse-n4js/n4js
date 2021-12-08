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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.utils.ResourceType;
import org.eclipse.xtext.EcoreUtil2;

/**
 * JavaScript variant to adjust validation. See ECMAScript Spec, 10.2.1 Strict Mode Code.
 * <p>
 * Note that the variants are mutually exclusive. There is a precedence of the variants: n4js precedes strict precedes
 * unrestricted. That is, even if a script in a file with extension ".n4js" contains strict mode literals ("use strict")
 * the mode is always n4js. This class is used by {@link JavaScriptVariantHelper}
 *
 * @see <a href=
 *      "https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Functions_and_function_scope/Strict_mode">MDN
 *      </a>
 */
public enum JavaScriptVariant {

	/** JavaScript (non-strict) mode */
	unrestricted,
	/** JavaScript (strict) mode */
	strict,
	/** N4JS mode */
	n4js,
	/** external mode */
	external;

	private final static Logger LOGGER = Logger.getLogger(JavaScriptVariant.class);

	/**
	 * Literal value to indicate strict mode: "use strict" (or 'use strict')
	 */
	public final static String STRICT_MODE_LITERAL_VALUE = "use strict";

	/** @return all {@link JavaScriptVariant}s that are not annotated with {@code @Depricated} */
	static public Set<JavaScriptVariant> nonDepricatedValues() {
		Set<JavaScriptVariant> nonDepricated = new HashSet<>();
		JavaScriptVariant[] enumConstants = JavaScriptVariant.class.getEnumConstants();
		for (JavaScriptVariant enumConstant : enumConstants) {
			try {
				Field field = JavaScriptVariant.class.getDeclaredField(enumConstant.name());
				Annotation[] annotations = field.getAnnotations();
				for (Annotation annotation : annotations) {
					Class<? extends Annotation> annotationType = annotation.annotationType();
					if (!Deprecated.class.equals(annotationType)) {
						nonDepricated.add(enumConstant);
					}
				}
			} catch (Exception e) {
				LOGGER.error("Error when collecting all non-@Deprecated JavaScriptVariants", e);
			}
		}
		return nonDepricated;
	}

	/**
	 * Returns the variant at the given code element.
	 */
	public static JavaScriptVariant getVariant(EObject eobj) {
		ResourceType resourceType = ResourceType.getResourceType(eobj);

		if (ResourceType.N4JS.equals(resourceType)) {
			return n4js;
		}
		if (isContainedInStrictFunctionOrScript(eobj)) {
			return strict;
		}
		if (ResourceType.N4JSD.equals(resourceType)) {
			return external;
		}
		return unrestricted;
	}

	/**
	 * Returns true, if the variant is active for the given model element. Note that the variants are mutually exclusive
	 * and that there is a variant precedence defined.
	 */
	public boolean isActive(EObject eobj) {
		return getVariant(eobj) == this;
	}

	/**
	 * Return true if an EObject is contained within a strict function or script, e.g. a script starting with "use
	 * strict"
	 *
	 * @param eobj
	 *            the input EObject
	 */
	public static boolean isContainedInStrictFunctionOrScript(EObject eobj) {
		if (eobj == null) {
			return false;
		}
		FunctionDefinition functionDef = EcoreUtil2.getContainerOfType(eobj, FunctionDefinition.class);
		if (functionDef != null) {
			Block block = functionDef.getBody();
			if (block != null && startsWithStrictMode(block.getStatements())) {
				return true;
			}
			return isContainedInStrictFunctionOrScript(functionDef.eContainer());
		}
		Script script = EcoreUtil2.getContainerOfType(eobj, Script.class);
		if (script != null) {
			if (startsWithStrictMode(script.getScriptElements())) {
				return true;
			}
		}
		return false;
	}

	private static boolean startsWithStrictMode(EList<? extends EObject> eList) {
		if (eList == null || eList.isEmpty()) {
			return false;
		}
		EObject first = eList.get(0);
		if (first instanceof ExpressionStatement) {
			Expression expr = ((ExpressionStatement) first).getExpression();
			if (expr instanceof StringLiteral) {
				boolean equalsStrictLiteral = STRICT_MODE_LITERAL_VALUE.equals(((StringLiteral) expr).getValue());
				return equalsStrictLiteral;
			}
		}
		return false;
	}

	/**
	 * Returns true, if current variant is either unrestricted or strict, that is, if no N4JS mode is active.
	 */
	public boolean isECMAScript() {
		return this == strict || this == unrestricted;
	}
}
