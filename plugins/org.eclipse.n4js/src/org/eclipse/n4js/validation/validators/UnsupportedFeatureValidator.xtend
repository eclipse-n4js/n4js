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
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.n4js.n4JS.AnnotableElement
import org.eclipse.n4js.n4JS.Argument
import org.eclipse.n4js.n4JS.BindingPattern
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.ExportableElement
import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.N4ClassDefinition
import org.eclipse.n4js.n4JS.N4ClassExpression
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration
import org.eclipse.n4js.n4JS.NamedElement
import org.eclipse.n4js.n4JS.NamespaceElement
import org.eclipse.n4js.n4JS.NewTarget
import org.eclipse.n4js.n4JS.PropertySpread
import org.eclipse.n4js.utils.ResourceType
import org.eclipse.n4js.validation.ASTStructureValidator
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static org.eclipse.n4js.utils.N4JSLanguageUtils.*
import static org.eclipse.n4js.validation.IssueCodes.*

/**
 * Validations to show an error for unsupported language features, mostly ECMAScript6 features.
 * These validations will be removed over time once the corresponding features are implemented.
 */
class UnsupportedFeatureValidator extends AbstractN4JSDeclarativeValidator {
	
	@Inject
	JavaScriptVariantHelper jsVariantHelper;

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	override register(EValidatorRegistrar registrar) {
		// nop
	}


	@Check
	def void checkAnonymousDefaultExport(ExportableElement decl) {
		if(decl instanceof NamedElement) {
			if(decl.name===null && decl.exportedAsDefault) {
				unsupported("anonymous default export", decl);
			}
		}
	}
	@Check
	def void checkSeparateExport(ExportDeclaration exportDecl) {
		if(exportDecl.exportedElement===null) {
			if(exportDecl.defaultExport && exportDecl.defaultExportedExpression!==null) {
				unsupported(
					"exporting values (only declared classes, interfaces, enums, functions and variables can be exported)",
					exportDecl);
			} else {
				val isDTS = ResourceType.getResourceType(exportDecl) === ResourceType.DTS;
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
	def void checkClassExpression(N4ClassExpression classExpr) {
		if(!classExpressionsAreAllowed) {
			if(classExpr.name!==null) {
				unsupported("class expressions", classExpr, N4JSPackage.eINSTANCE.n4ClassExpression_Name);
			} else {
				unsupported("class expressions", classExpr, NodeModelUtils.getNode(classExpr).offset, 5); // the first 5 characters are always the 'class' keyword
			}
		}
	}
	private static boolean classExpressionsAreAllowed = false;


	@Check
	def void checkNewTarget(NewTarget newTarget) {
		unsupported("new.target", newTarget);
	}


	@Check
	def void checkExtendsExpression(N4ClassDefinition classDef) {
		if(classDef.superClassExpression!==null) {
			unsupported("extends <expression>", classDef, N4JSPackage.eINSTANCE.n4ClassDefinition_SuperClassExpression);
		}
	}


	@Check
	def void checkBindingPatternAsFpar(BindingPattern pattern) {
		if(pattern.eContainer instanceof FormalParameter) {
			unsupported("destructuring patterns as formal parameter", pattern);
		}
	}


	/**
	 * NOTE: in addition to the errors produced by this and the following method, spread operator
	 * in <em>array literals</em> is also unsupported; but that is checked in
	 * {@link ASTStructureValidator#validateSpreadInArrayLiteral(org.eclipse.n4js.n4JS.ArrayElement,
	 * org.eclipse.n4js.validation.ASTStructureDiagnosticProducer)}
	 */
	@Check
	def void checkSpreadOperatorInNewAndCallExpressions(Argument argument) {
		if(argument.spread) {
			unsupported("spread operator in new and call expressions (only allowed in array destructuring patterns)",
				argument, N4JSPackage.eINSTANCE.argument_Spread);
		}
	}

	@Check
	def void checkSpreadOperatorInObjectLiteral(PropertySpread propertySpread) {
		unsupported("spread operator in object literals (only allowed in array destructuring patterns)", propertySpread)
	}


	@Check
	def void checkNamespaceOutsideN4JSD(N4NamespaceDeclaration namespace) {
		if (!jsVariantHelper.isExternalMode(namespace)) {
			val markElem = if (namespace.eContainer instanceof ExportDeclaration) namespace.eContainer else namespace;
			unsupported("namespaces in n4js/n4jsx files", markElem)
		}
	}
	
	@Check
	def void checkPolyfillInNamespace(NamespaceElement elem) {
		if (elem instanceof AnnotableElement) {
			if (elem.getNamespace !== null && (isStaticPolyfill(elem) || isNonStaticPolyfill(elem))) {
				unsupported("polyfills in namespaces", elem)
			}
		}
	}
	

	// ----------------------------------------------------------------------------------------------------------------
	// UTILITY METHODS

	def private void unsupported(String msg, EObject source) {
		unsupported(msg, source, null);
	}
	def private void unsupported(String msg, EObject source, EStructuralFeature feature) {
		addIssue(
			source, feature,
			UNSUPPORTED.toIssueItem(msg));
	}
	def private void unsupported(String msg, EObject source, int offset, int length) {
		addIssue(
			source,
			offset, length,
			UNSUPPORTED.toIssueItem(msg));
	}

	/**
	 * Turns off unsupported feature validation for class expressions, invokes given function, and turns validation
	 * back on (for testing of class expressions).
	 */
	def public static void allowClassExpressions(()=>void r) {
		try {
			classExpressionsAreAllowed = true;
			r.apply();
		}
		finally {
			classExpressionsAreAllowed = false;
		}
	}
}
