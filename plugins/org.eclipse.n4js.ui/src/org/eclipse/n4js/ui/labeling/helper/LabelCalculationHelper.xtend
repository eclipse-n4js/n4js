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
package org.eclipse.n4js.ui.labeling.helper

import org.eclipse.n4js.n4JS.ExportedVariableStatement
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.n4JS.NamedElement
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4idl.N4IDLGlobals
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ui.labeling.EObjectWithContext
import org.eclipse.n4js.ui.labeling.N4JSLabelProvider
import org.eclipse.n4js.ui.outline.N4JSOutlineTreeProvider
import org.eclipse.n4js.ui.typesearch.TypeSearchKind
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.ui.label.AbstractLabelProvider

/**
 * This helper class serves as replacement for the polymorphic dispatch done
 * by {@link AbstractLabelProvider} in favor of
 * Xtend dispatch methods. Here the dispatch of labels to be shown e.g. in
 * the outline view is done. It is called in {@link N4JSLabelProvider#doGetText}.
 * <br /><br />
 * General pattern is to delegate from an AST element to the types element
 * as in the types model the required information to calculate the name
 * is better provided.
 * <br /><br />
 * In general always the name is used, so most cases should be handled by
 * NamedElement (AST) resp. IdentifiableElement (types).
 * <br /><br />
 * This label calculation assumes a certain structure produced by the
 * {@link N4JSOutlineTreeProvider}, e.g. a single element import and
 * wild card imports will produce only one outline node while multiple
 * imports in one import declaration will produce a sub tree with the root
 * node stating the import from module and sub nodes stating each import with
 * name and alias if used.
 * <br /><br />
 * For all unexpected elements the label "\<unknow\>" is created.
 * <br /><br />
 * Please note, that if you want to created styled labels, you have to
 * use {@link StyledTextCalculationHelper}. As styled text application
 * kicks in after the label calculation has been done you can here create
 * an initial label and then append the styled parts in the styled text
 * calculation.
 */
class LabelCalculationHelper {

	/* dispatchDoGetText AST -> delegates to types model, as information is easier to retrieve there */
	def dispatch String dispatchDoGetText(Void _null) {
		return "*null*";
	}

	def dispatch String dispatchDoGetText(EObjectWithContext objectWithContext) {
		var label = dispatchDoGetText(objectWithContext.obj);

		val TMember member = if (objectWithContext.obj instanceof N4MemberDeclaration) {
				(objectWithContext.obj as N4MemberDeclaration).definedTypeElement
			} else if (objectWithContext.obj instanceof TMember) {
				objectWithContext.obj as TMember
			} else
				null;

		if (member !== null && member.containingType !== null && member.containingType != objectWithContext.context) {
			label += " from " + dispatchDoGetText(member.containingType);
		}

		return label;

	}

	def dispatch String dispatchDoGetText(Script script) {
		return dispatchDoGetText(script.module)
	}

	// for single element imports the element name is appended after a colon
	// e.g. imports from mypack/MyModule/A or imports from mypack/MyModule/A: A
	def dispatch String dispatchDoGetText(ImportDeclaration importDelaration) {
		return "imports from " + getModuleSpecifier(importDelaration.module) +
			(if (importDelaration.importSpecifiers.size == 1 &&
				!(importDelaration.importSpecifiers.head instanceof NamespaceImportSpecifier))
				": " + importDelaration.importSpecifiers.head.dispatchDoGetText
			else
				"")
	}

	// name and if given alias, e.g. A as B
	def dispatch String dispatchDoGetText(NamedImportSpecifier namedImportSpecifier) {
		return namedImportSpecifier.importedElement?.name +
			(if (namedImportSpecifier.alias !== null) " as " + namedImportSpecifier.alias else "")
	}

	def dispatch String dispatchDoGetText(N4ClassifierDeclaration it) {
		return if (null === definedType) "<unknown>" else dispatchDoGetText(definedType)
	}

	def dispatch String dispatchDoGetText(N4GetterDeclaration n4GetterDeclaration) {
		return dispatchDoGetText(n4GetterDeclaration.definedGetter)
	}

	def dispatch String dispatchDoGetText(N4SetterDeclaration n4SetterDeclaration) {
		return dispatchDoGetText(n4SetterDeclaration.definedSetter)
	}

	/**
	 * comma separated list of all contained variable names
	 */
	def dispatch String dispatchDoGetText(ExportedVariableStatement vs) {
		return vs.varDecl.map[name].join(", ")
	}

	def dispatch String dispatchDoGetText(NamedElement namedElement) {
		namedElement.name
	}

	/**
	 * dispatchDoGetText types model
	 * the fully qualified module specifier, e.g. mypack/MyFile
	 */
	def dispatch String dispatchDoGetText(TModule tModule) {
		return getModuleSpecifier(tModule)
	}

	/**
	 * name + optional type variables, e.g. A<T, U>
	 */
	def dispatch String dispatchDoGetText(TClassifier tClassifier) {
		return tClassifier.name + getTypeVersionDescription(tClassifier) + getTypeVarDescriptions(tClassifier)
	}

	def private getTypeVarDescriptions(TClassifier tClassifier) {
		if (tClassifier.typeVars.size > 0)
			"<" + tClassifier.typeVars.map[name].join(", ") + ">"
		else
			""
	}

	def private getTypeVersionDescription(TClassifier tClassifier) {
		if (tClassifier.declaredVersion != 0) {
			return N4IDLGlobals.VERSION_SEPARATOR + Integer.toString(tClassifier.declaredVersion)
		}
		return "";
	}

	def dispatch String dispatchDoGetText(TGetter tGetter) {
		return tGetter.name
	}

	def dispatch String dispatchDoGetText(TSetter tSetter) {
		return tSetter.name
	}

	def dispatch String dispatchDoGetText(IdentifiableElement identifiableElement) {
		return identifiableElement.name
	}

	def dispatch String dispatchDoGetText(IEObjectDescription d) {
		if (TypeSearchKind.EVERYTHING.matches(d.EClass)) {
			return d.qualifiedName.lastSegment;
		} else {
			return "<unknown>"
		}
	}

	// fallback
	def dispatch String dispatchDoGetText(Object object) {
		return "<unknown>"
	}

	def private getModuleSpecifier(TModule tModule) {
		if (tModule?.qualifiedName !== null) tModule.moduleSpecifier else "<unknown>"
	}
}
