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
package org.eclipse.n4js.transpiler.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ThisArgProvider;
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM;
import org.eclipse.n4js.transpiler.im.Script_IM;
import org.eclipse.n4js.ts.types.TN4Classifier;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.xtext.EcoreUtil2;

/**
 */
public class TranspilerUtils {

	/**
	 * Method {@link N4JSLanguageUtils#isASTNode(EObject)} will return <code>true</code> for elements in both the
	 * original AST and the intermediate model. This method can be used to check if a node is actually contained in the
	 * original AST (will return <code>false</code> for nodes of the intermediate model).
	 */
	public static boolean isOriginalASTNode(EObject eobj) {
		final Script script = EcoreUtil2.getContainerOfType(eobj, Script.class);
		return script != null && !(script instanceof Script_IM);
	}

	/** see {@link #isOriginalASTNode(EObject)} */
	public static final void assertOriginalASTNode(EObject originalASTNode) {
		if (!TranspilerUtils.isOriginalASTNode(originalASTNode)) {
			throw new IllegalArgumentException("not a node from the original AST");
		}
	}

	/**
	 * Method {@link N4JSLanguageUtils#isASTNode(EObject)} will return <code>true</code> for elements in both the
	 * original AST and the intermediate model. This method can be used to check if a node is actually contained in the
	 * intermediate model (will return <code>false</code> for nodes of the original AST).
	 */
	public static boolean isIntermediateModelElement(EObject eobj) {
		return EcoreUtil2.getContainerOfType(eobj, Script_IM.class) != null;
	}

	/** see {@link #isIntermediateModelElement(EObject)} */
	public static final void assertIntermediateModelElement(EObject elementInIntermediateModel) {
		if (!TranspilerUtils.isIntermediateModelElement(elementInIntermediateModel)) {
			throw new IllegalArgumentException("not an element from the intermediate model");
		}
	}

	/**
	 * Search entire containment tree below 'root' for objects of type 'cls'. If last argument is <code>false</code>,
	 * then sub trees below a matching node won't be searched.
	 */
	public static final <T extends EObject> List<T> collectNodes(EObject root, Class<T> cls,
			boolean searchForNestedNodes) {
		final List<T> result = new ArrayList<>();
		final TreeIterator<EObject> iter = root.eAllContents();
		while (iter.hasNext()) {
			final EObject obj = iter.next();
			if (cls.isAssignableFrom(obj.getClass())) {
				@SuppressWarnings("unchecked")
				final T objCasted = (T) obj;
				result.add(objCasted);
				if (!searchForNestedNodes)
					iter.prune();
			}
		}
		return result;
	}

	/**
	 * root usually a function or other ThisProviding environment.
	 *
	 * @param root
	 *            function or method.
	 * @param cls
	 *            Type of element to report.
	 * @return nodes of (sub-)type cls in the same this-environment
	 */
	public static final <T extends EObject> List<T> collectNodesWithinSameThisEnvironment(EObject root, Class<T> cls) {
		final List<T> result = new ArrayList<>();
		final TreeIterator<EObject> iter = root.eAllContents();
		while (iter.hasNext()) {
			final EObject obj = iter.next();
			if (cls.isAssignableFrom(obj.getClass())) {
				@SuppressWarnings("unchecked")
				final T objCasted = (T) obj;
				result.add(objCasted);
			}
			// check for same environment
			if (obj instanceof ThisArgProvider) {
				iter.prune();
			}
		}
		return result;
	}

	/**
	 * Will return container of given object if the container is an {@link ExportDeclaration} or otherwise the given
	 * object itself.
	 */
	public static final EObject orContainingExportDeclaration(EObject eobj) {
		final EObject parent = eobj.eContainer();
		if (parent instanceof ExportDeclaration) {
			return parent;
		} else {
			return eobj;
		}
	}

	/**
	 * Escapes illegal characters in given identifier. Used for function names, etc.
	 */
	public static String sanitizeIdentifierName(String input) {
		if (input == null || input.isEmpty()) {
			return input;
		}
		final StringBuilder result = new StringBuilder();
		char ch = input.charAt(0);
		if (Character.isJavaIdentifierStart(ch)) {
			result.append(ch);
		} else {
			result.append("_" + Character.codePointAt(input, 0) + "$");
		}
		int i = 1;
		while (i < input.length()) {
			ch = input.charAt(i);
			if (Character.isJavaIdentifierPart(ch)) {
				result.append(ch);
			} else {
				result.append("_" + Character.codePointAt(input, i) + "$");
			}
			i = i + 1;
		}
		return result.toString();
	}

	/**
	 * A good-enough approximation: is the argument a legal identifier in Java?
	 */
	public static boolean isLegalIdentifier(String name) {
		if (name.isEmpty()) {
			return false;
		}
		if (!Character.isJavaIdentifierStart(name.charAt(0))) {
			return false;
		}
		int i = 1;
		while (i < name.length()) {
			if (!Character.isJavaIdentifierPart(name.charAt(i))) {
				return false;
			}
			i = i + 1;
		}
		return true;
	}

	/**
	 * Tells if the given classifier (TModule element) is declared to be structural (on definition site).
	 */
	public static boolean isDefSiteStructural(TN4Classifier type) {
		final TypingStrategy ts = type.getTypingStrategy();
		return ts != TypingStrategy.NOMINAL && ts != TypingStrategy.DEFAULT;
	}

	/**
	 * Tells if the given field declaration has a non-trivial init expression (default value expression), i.e. if it has
	 * an init expression other than 'undefined'.
	 */
	public static boolean hasNonTrivialInitExpression(RuleEnvironment G, N4FieldDeclaration fieldDecl) {
		final Expression expr = fieldDecl != null ? fieldDecl.getExpression() : null;
		if (expr instanceof IdentifierRef_IM) {
			if (((IdentifierRef_IM) expr).getOriginalTargetOfRewiredTarget() == RuleEnvironmentExtensions
					.getGlobalObjectScope(G).getFieldUndefined()) {
				return false;
			}
		}
		return expr != null && !N4JSLanguageUtils.isUndefinedLiteral(G, expr);
	}
}
