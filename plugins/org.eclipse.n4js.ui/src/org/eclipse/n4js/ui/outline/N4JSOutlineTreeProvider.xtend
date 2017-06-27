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
package org.eclipse.n4js.ui.outline

import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration
import org.eclipse.n4js.n4JS.ExportedVariableStatement
import org.eclipse.n4js.n4JS.FieldAccessor
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4ClassifierDefinition
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ui.labeling.N4JSLabelProvider
import org.eclipse.emf.ecore.EObject
import org.eclipse.jface.resource.ImageDescriptor
import org.eclipse.xtext.ui.editor.model.IXtextDocument
import org.eclipse.xtext.ui.editor.outline.IOutlineNode
import org.eclipse.xtext.ui.editor.outline.impl.BackgroundOutlineTreeProvider
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.xtext.ui.editor.outline.impl.DocumentRootNode

/**
 * Customization of the default outline structure.
 * We filter all null elements, which can only occur in case of syntax errors.
 * <p>
 * This outline runs in the background, e.g. in a non-UI thread, thus improving responsiveness.
 * That functionality requires that no operations are performed that must run in the UI thread.
 * In particular, {@link N4JSLabelProvider#doGetImage} shouldn't perform such operations.
 * A rule of thumb is "prefer {@link ImageDescriptor} over {@code Image}".
 * <p>
 * @see http://www.eclipse.org/Xtext/documentation.html#outline
 */
class N4JSOutlineTreeProvider extends BackgroundOutlineTreeProvider  {


	/** casted access to the underlying label provider. */
	private def N4JSLabelProvider getN4JSLabelProvider() {
		return labelProvider as N4JSLabelProvider;
	}

	/**
	 * This override captures for the duration of the invocation the {@link CancelIndicator} argument, for use during validation.
	 * The superclass does the same (also captures it) but keeps it in private field.
	 * All accesses to that field are mediated by {@link BackgroundOutlineTreeProvider#checkCanceled},
	 * in our case besides checking-and-throwing as supported by that method we need to hand over the current cancel indicator.
	 * <p>
	 * For details on the protocol this method follows to hand it over see {@link N4JSLabelProvider#establishCancelIndicator}
	 */
	override IOutlineNode createRoot(IXtextDocument document, CancelIndicator cancelIndicator) {
		try {
			getN4JSLabelProvider.establishCancelIndicator(cancelIndicator);
			return super.createRoot(document, cancelIndicator);
		} finally {
			getN4JSLabelProvider.removeCancelIndicator();
		}
	}

	/** Overridden to use dispatch methods ending in underscore {#createChildren_(...)}.
	 * Dispatch call is wrapped in null/cancel check.*/
	override void createChildren(IOutlineNode parentNode, EObject modelElement) {
		checkCanceled()
		if (modelElement !== null && parentNode.hasChildren()) {
			createChildren_(parentNode,modelElement);
		}
	}

	def dispatch protected void createChildren_(DocumentRootNode parentNode, EObject modelElement) {
		// First entry should not dispatch but specifically create the root node
		super.createChildren(parentNode, modelElement)
	}

	def dispatch protected void createChildren_(IOutlineNode parentNode, EObject modelElement) {
		// TODO maybe we do not want create arbitrary nodes here,
		// then we should remove the following line:
		super.createChildren(parentNode, modelElement);
	}

	// only create entries on top level for elements listed in
	// isInstanceOfExpectedScriptChildren - so not exported functions
	// and not exported variables won't appear in the outline
	// for variable statements with one element only create one node
	def dispatch protected void createChildren_(IOutlineNode parentNode, Script script) {

		for (child : script.scriptElements.filterNull) {
			if (child instanceof ExportDeclaration) {
				val exportedElement = child.exportedElement
				if (exportedElement instanceof ExportedVariableStatement) {
					if (exportedElement.varDecl.size == 1) {
						parentNode.createNode(exportedElement.varDecl.head)
					} else {
						parentNode.createNode(exportedElement)
					}
				} else if (exportedElement !== null) {
					parentNode.createNode(exportedElement)
				}
			} else if (child.isInstanceOfExpectedScriptChildren && child.canCreateChildNode) {
				parentNode.createNode(child)
			}
		}
	}

	// only create nodes for members (methods, fields) and field accessors (getter, setter)
	def dispatch protected void createChildren_(IOutlineNode parentNode, N4ClassifierDefinition classifierDefinition) {
		for (child : classifierDefinition.eContents.filterNull) {
			if (isInstanceOfExpectedClassifierChildren(child)) {
				parentNode.createNode(child)
			}
		}
	}

	// create nodes for literals
	def dispatch protected void createChildren_(IOutlineNode parentNode, N4EnumDeclaration ed) {
		for (literal : ed.literals.filterNull) {
			parentNode.createNode(literal)
		}
	}

	def dispatch boolean canCreateChildNode(Object element) {
		true;
	}

	def dispatch boolean canCreateChildNode(N4ClassifierDefinition it) {
		null !== definedType
	}

	// top level elements in outline view
	def private boolean isInstanceOfExpectedScriptChildren(EObject child) {
		isInstanceOfOneOfTheTypes(
			child,
			ExportDeclaration,
			// ImportDeclaration,
			N4ClassifierDeclaration,
			N4EnumDeclaration,
			FunctionDeclaration
		)
	}

	def private boolean isInstanceOfExpectedClassifierChildren(EObject child) {
		child.isInstanceOfOneOfTheTypes(N4MemberDeclaration, FieldAccessor)
	}

	def private boolean isInstanceOfOneOfTheTypes(EObject eObject, Class<?>... classes) {
		classes.exists[it.isAssignableFrom(eObject.class)]
	}

	/** Overridden to enable the dispatch-method mechanism of Xtend */
	def dispatch protected boolean isLeaf(EObject modelElement) {
		// route through to default implementation.
		return super.isLeaf(modelElement);
	}

	def dispatch protected boolean isLeaf(Void _null) {
		return true;
	}

	// suppress + symbol in outline when N4JS file is empty
	def dispatch protected boolean isLeaf(Script script) {
		!script.eContents.exists[isInstanceOfExpectedScriptChildren]
	}

	// suppress + symbol in outline when classifier has no members
	// or we have a broken AST and the defined type of the classifier is not available yet
	def dispatch protected boolean isLeaf(N4ClassifierDefinition it) {
		null === definedType || !eContents.exists[isInstanceOfExpectedClassifierChildren]
	}

	// fields should have never children
	def dispatch protected boolean isLeaf(N4FieldDeclaration md) {
		true
	}

	// functions and methods should have never children
	def dispatch protected boolean isLeaf(FunctionOrFieldAccessor fa) {
		true
	}

	// variables should have never children
	def dispatch protected boolean isLeaf(ExportedVariableDeclaration vd) {
		true
	}

	// import declarations with one element (respectively wildcard import) should have no
	// children as label provider makes the one child part of the import declaration node
	def dispatch protected boolean isLeaf(ImportDeclaration id) {
		id.importSpecifiers.size == 1
	}
}
