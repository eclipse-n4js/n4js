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
package org.eclipse.n4js.validation.validators;

import static org.eclipse.n4js.utils.N4JSLanguageUtils.isNonStaticPolyfill;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.isStaticPolyfill;
import static org.eclipse.n4js.validation.IssueCodes.UNSUPPORTED;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.BindingPattern;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.N4ClassDefinition;
import org.eclipse.n4js.n4JS.N4ClassExpression;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.NamespaceElement;
import org.eclipse.n4js.n4JS.NewTarget;
import org.eclipse.n4js.n4JS.PropertySpread;
import org.eclipse.n4js.utils.ResourceType;
import org.eclipse.n4js.validation.ASTStructureValidator;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.inject.Inject;

/**
 * Validations to show an error for unsupported language features, mostly ECMAScript6 features. These validations will
 * be removed over time once the corresponding features are implemented.
 */
@SuppressWarnings("javadoc")
public class UnsupportedFeatureValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	JavaScriptVariantHelper jsVariantHelper;

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	@Override
	public void register(EValidatorRegistrar registrar) {
		// nop
	}

	@Check
	public void checkAnonymousDefaultExport(ExportableElement decl) {
		if (decl instanceof NamedElement) {
			if (((NamedElement) decl).getName() == null && decl.isExportedAsDefault()) {
				unsupported("anonymous default export", decl);
			}
		}
	}

	@Check
	public void checkSeparateExport(ExportDeclaration exportDecl) {
		if (exportDecl.getExportedElement() == null) {
			if (exportDecl.isDefaultExport() && exportDecl.getDefaultExportedExpression() != null) {
				unsupported(
						"exporting values (only declared classes, interfaces, enums, functions and variables can be exported)",
						exportDecl);
			} else {
				boolean isDTS = ResourceType.getResourceType(exportDecl) == ResourceType.DTS;
				if (!isDTS) {
					unsupported(
							"separate export statements (add keyword 'export' directly before a class, interface, enum, function or variable declaration)",
							exportDecl);
				}
			}
		}
	}

	// TODO when removing this method, remove flag 'classExpressionsAreAllowed' as well!
	@Check
	public void checkClassExpression(N4ClassExpression classExpr) {
		if (!classExpressionsAreAllowed) {
			if (classExpr.getName() != null) {
				unsupported("class expressions", classExpr, N4JSPackage.eINSTANCE.getN4ClassExpression_Name());
			} else {
				unsupported("class expressions", classExpr, NodeModelUtils.getNode(classExpr).getOffset(), 5); // the
																												// first
																												// 5
																												// characters
																												// are
																												// always
																												// the
																												// 'class'
																												// keyword
			}
		}
	}

	private static boolean classExpressionsAreAllowed = false;

	@Check
	public void checkNewTarget(NewTarget newTarget) {
		unsupported("new.target", newTarget);
	}

	@Check
	public void checkExtendsExpression(N4ClassDefinition classDef) {
		if (classDef.getSuperClassExpression() != null) {
			unsupported("extends <expression>", classDef,
					N4JSPackage.eINSTANCE.getN4ClassDefinition_SuperClassExpression());
		}
	}

	@Check
	public void checkBindingPatternAsFpar(BindingPattern pattern) {
		if (pattern.eContainer() instanceof FormalParameter) {
			unsupported("destructuring patterns as formal parameter", pattern);
		}
	}

	/**
	 * NOTE: in addition to the errors produced by this and the following method, spread operator in <em>array
	 * literals</em> is also unsupported; but that is checked in
	 * {@link ASTStructureValidator#validateSpreadInArrayLiteral(org.eclipse.n4js.n4JS.ArrayElement, org.eclipse.n4js.validation.ASTStructureDiagnosticProducer)}
	 */
	@Check
	public void checkSpreadOperatorInNewAndCallExpressions(Argument argument) {
		if (argument.isSpread()) {
			unsupported("spread operator in new and call expressions (only allowed in array destructuring patterns)",
					argument, N4JSPackage.eINSTANCE.getArgument_Spread());
		}
	}

	@Check
	public void checkSpreadOperatorInObjectLiteral(PropertySpread propertySpread) {
		unsupported("spread operator in object literals (only allowed in array destructuring patterns)",
				propertySpread);
	}

	@Check
	public void checkNamespaceOutsideN4JSD(N4NamespaceDeclaration namespace) {
		if (!jsVariantHelper.isExternalMode(namespace)) {
			EObject markElem = (namespace.eContainer() instanceof ExportDeclaration) ? namespace.eContainer()
					: namespace;
			unsupported("namespaces in n4js/n4jsx files", markElem);
		}
	}

	@Check
	public void checkPolyfillInNamespace(NamespaceElement elem) {
		if (elem instanceof AnnotableElement) {
			AnnotableElement anElem = (AnnotableElement) elem;
			if (elem.getNamespace() != null && (isStaticPolyfill(anElem) || isNonStaticPolyfill(anElem))) {
				unsupported("polyfills in namespaces", elem);
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------------------
	// UTILITY METHODS

	private void unsupported(String msg, EObject source) {
		unsupported(msg, source, null);
	}

	private void unsupported(String msg, EObject source, EStructuralFeature feature) {
		addIssue(
				source, feature,
				UNSUPPORTED.toIssueItem(msg));
	}

	private void unsupported(String msg, EObject source, int offset, int length) {
		addIssue(
				source,
				offset, length,
				UNSUPPORTED.toIssueItem(msg));
	}

	/**
	 * Turns off unsupported feature validation for class expressions, invokes given function, and turns validation back
	 * on (for testing of class expressions).
	 */
	public static void allowClassExpressions(Runnable r) {
		try {
			classExpressionsAreAllowed = true;
			r.run();
		} finally {
			classExpressionsAreAllowed = false;
		}
	}
}
