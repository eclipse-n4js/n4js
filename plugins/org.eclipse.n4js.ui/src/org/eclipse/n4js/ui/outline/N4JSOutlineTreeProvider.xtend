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

import com.google.inject.Inject
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.core.runtime.OperationCanceledException
import org.eclipse.emf.ecore.EObject
import org.eclipse.jface.resource.ImageDescriptor
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
import org.eclipse.n4js.ts.types.MemberAccessModifier
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ui.labeling.EObjectWithContext
import org.eclipse.n4js.ui.labeling.N4JSLabelProvider
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.xtext.ui.editor.model.IXtextDocument
import org.eclipse.xtext.ui.editor.outline.IOutlineNode
import org.eclipse.xtext.ui.editor.outline.IOutlineTreeProvider
import org.eclipse.xtext.ui.editor.outline.impl.BackgroundOutlineTreeProvider
import org.eclipse.xtext.ui.editor.outline.impl.DocumentRootNode
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode
import org.eclipse.xtext.ui.editor.outline.impl.OutlineMode
import org.eclipse.xtext.util.CancelIndicator

/**
 * Customization of the default outline structure.
 * We filter all null elements, which can only occur in case of syntax errors.
 * <p>
 * This outline runs in the background, e.g. in a non-UI thread, thus improving responsiveness.
 * That functionality requires that no operations are performed that must run in the UI thread.
 * In particular, {@link N4JSLabelProvider#doGetImage} shouldn't perform such operations.
 * A rule of thumb is "prefer {@link ImageDescriptor} over {@code Image}".
 * <p>
 * This outline tree provider supports different modes. There are two use cases:
 * <ul>
 * 	<li>Quick Outline View: In this case, modes are toggled via CMD/CTRL-O. This simply means that
 * 		{@link N4JSOutlineTreeProvider#getNextMode()} is called every time. These modes are defined in
 * 		{@link N4JSOutlineModes}. For that to work, the {@link IOutlineTreeProvider#ModeAware} protocol is used
 *  <li>Outline View: The normal outline view. In this case, toggle buttons are used to enable filtering. This
 * 		is achieved by the {@link N4JSShowInheritedMembersOutlineContribution}. After an object node has been
 * 		created, it is send to this contribution class which does the filtering.
 * </ul>
 * There is no direct way of detecting the current use case. Since the "normal" outline view does not call any
 * method of {@link IOutlineTreeProvider#ModeAware}, we initialize the corresponding field
 * {@link N4JSOutlineTreeProvider#modeAware} on demand. If it is null we can then conclude that we are in "normal"
 * outline view mode, other wise in the quick outline view.
 * 
 * @see http://www.eclipse.org/Xtext/documentation.html#outline
 */
class N4JSOutlineTreeProvider extends BackgroundOutlineTreeProvider implements IOutlineTreeProvider.ModeAware {

	private static final Logger logger = Logger.getLogger(N4JSOutlineTreeProvider);

	@Inject
	ContainerTypesHelper containerTypesHelper

	/**
	 * This is used for cycling through the modes in the quick outline view. It is set on demand to detect whether
	 * we run in quick outline view or in normal outline view.
	 */
	private IOutlineTreeProvider.ModeAware modeAware = null;

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
			try {
				createChildren_(parentNode, modelElement);
			} catch (Exception ex) {
				// cancel this operation in case something went wrong
				logger.error("Error creating nodes for children", ex);
				throw new OperationCanceledException("Canceled due to internal error: " + ex);
			}
		}
	}

	/**
	 * First entry should not dispatch but specifically create the root node
	 */
	def dispatch protected void createChildren_(DocumentRootNode parentNode, EObject modelElement) {
		super.createChildren(parentNode, modelElement)
	}

	def dispatch protected void createChildren_(IOutlineNode parentNode, EObject modelElement) {
		// TODO maybe we do not want create arbitrary nodes here,
		// then we should remove the following line:
		super.createChildren(parentNode, modelElement);
	}

	/** 
	 * only create entries on top level for elements listed in
	 * isInstanceOfExpectedScriptChildren - so not exported functions
	 * and not exported variables won't appear in the outline
	 * for variable statements with one element only create one node
	 */
	def dispatch protected void createChildren_(IOutlineNode parentNode, Script script) {

		var EObjectNode node = null
		for (child : script.scriptElements.filterNull) {
			node = null;
			if (child instanceof ExportDeclaration) {
				val exportedElement = child.exportedElement
				if (exportedElement instanceof ExportedVariableStatement) {
					if (exportedElement.varDecl.size == 1) {
						node = parentNode.createNode(exportedElement.varDecl.head)
					} else {
						node = parentNode.createNode(exportedElement)
					}
				} else if (exportedElement !== null) {
					node = parentNode.createNode(exportedElement)
				}
			} else if (child.isInstanceOfExpectedScriptChildren && child.canCreateChildNode) {
				node = parentNode.createNode(child);
				if (node instanceof N4JSEObjectNode) {
					node.isLocal = true;
				}
			}
		}
	}

	/**
	 * Create nodes for members (methods, fields) and field accessors (getter, setter).
	 * If not turned off, also inherited (and consumed or polyfilled) members are shown.
	 */
	def dispatch protected void createChildren_(IOutlineNode parentNode, N4ClassifierDefinition classifierDefinition) {
		val tclassifier = classifierDefinition.definedType as TClassifier;
		if (tclassifier !== null && showInherited) {
			val members = containerTypesHelper.fromContext(tclassifier).members(tclassifier, false, true);

			for (TMember tchild : members.filterNull) {
				val node = createNodeForObjectWithContext(parentNode, new EObjectWithContext(tchild, tclassifier));
				if (node instanceof N4JSEObjectNode) {
					if (tchild.containingType !== null && tchild.containingType != tclassifier) {
						node.isInherited = true;
					}
					node.isMember = true;
					node.isStatic = tchild.isStatic;
					node.isPublic = tchild.memberAccessModifier == MemberAccessModifier.PUBLIC ||
						tchild.memberAccessModifier == MemberAccessModifier.PUBLIC_INTERNAL
					node.isConstructor = tchild.isConstructor;
				}

			}
		} else {
			for (child : classifierDefinition.eContents.filterNull) {
				if (isInstanceOfExpectedClassifierChildren(child)) {
					parentNode.createNode(child)
				}
			}
		}

	}
	
	/**
	 * Creates a node with context to be able to distinguish between owned and inherited/consumed members
	 */
	def EObjectNode createNodeForObjectWithContext(IOutlineNode parentNode, EObjectWithContext objectWithContext) {
		checkCanceled();
		val Object text = getText(objectWithContext);
		val boolean isLeaf = isLeaf(objectWithContext.obj);
		if (text === null && isLeaf)
			return null;
		val ImageDescriptor image = getImageDescriptor(objectWithContext);
		return getOutlineNodeFactory().createEObjectNode(parentNode, objectWithContext.obj, image, text, isLeaf);
	}

	/**
	 * create nodes for literals
	 */
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

	/**
	 * top level elements in outline view
	 */
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

	/**
	 * Suppress + symbol in outline when classifier has no members
	 * or we have a broken AST and the defined type of the classifier is not available yet.
	 * 
	 * This method directly works on AST, because the outline is called with the AST.
	 */
	def dispatch protected boolean isLeaf(N4ClassifierDefinition classifierDefinition) {
		val tclassifier = classifierDefinition.definedType as TClassifier;
		if (tclassifier === null) {
			return true;
		}
		if (showInherited) {
			val members = containerTypesHelper.fromContext(tclassifier).members(tclassifier, false, true);
			return members.isNullOrEmpty;
		} else {
			return !classifierDefinition.eContents.exists[isInstanceOfExpectedClassifierChildren]
		}
	}

	/**
	 * Type model members are always leaves. 
	 */
	def dispatch protected boolean isLeaf(TMember member) {
		return true;
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

	protected def boolean showInherited() {
		if (modeAware !== null) {
			return modeAware.getCurrentMode() == N4JSOutlineModes.SHOW_INHERITED_MODE;
		}
		return true; // use filter in normal outline
	}

	override List<OutlineMode> getOutlineModes() {
		getOrCreateModeAware.getOutlineModes();
	}

	override OutlineMode getCurrentMode() {
		getOrCreateModeAware.getCurrentMode();
	}

	override OutlineMode getNextMode() {
		getOrCreateModeAware.getNextMode();
	}

	override void setCurrentMode(OutlineMode outlineMode) {
		getOrCreateModeAware.setCurrentMode(outlineMode);
	}

	/**
	 * Create lazy in order to be able to distinguish between quick and non-quick outline mode.
	 */
	def private ModeAware getOrCreateModeAware() {
		if (modeAware === null) {
			modeAware = new N4JSOutlineModes();
		}
		return modeAware;
	}

}
