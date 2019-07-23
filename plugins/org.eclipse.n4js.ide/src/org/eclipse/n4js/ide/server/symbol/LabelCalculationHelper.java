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
package org.eclipse.n4js.ide.server.symbol;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.n4js.n4JS.ExportedVariableStatement;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4idl.N4IDLGlobals;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/** */
public class LabelCalculationHelper {

	/** */
	public String getSymbolLabel(Object obj) {
		if (obj == null) {
			return "*null*";
		}
		if (obj instanceof Script) {
			return getSymbolLabel(((Script) obj).getModule());
		}
		if (obj instanceof ImportDeclaration) {
			ImportDeclaration importDelaration = (ImportDeclaration) obj;
			String text = "imports from " + getModuleSpecifier(importDelaration.getModule());

			EList<ImportSpecifier> importSpecifiers = importDelaration.getImportSpecifiers();
			if (importSpecifiers.size() == 1 && !(importSpecifiers.get(0) instanceof NamespaceImportSpecifier)) {
				NamespaceImportSpecifier namespaceImportSpecifier = (NamespaceImportSpecifier) importSpecifiers.get(0);
				text += ": " + getSymbolLabel(namespaceImportSpecifier);
			}

			return text;
		}
		if (obj instanceof NamedImportSpecifier) {
			NamedImportSpecifier namedImportSpecifier = (NamedImportSpecifier) obj;
			String text = "";
			if (namedImportSpecifier.getImportedElement() != null) {
				text += namedImportSpecifier.getImportedElement().getName();
			}
			if (namedImportSpecifier.getAlias() != null) {
				text += " as " + namedImportSpecifier.getAlias();
			}
			return text;
		}
		if (obj instanceof N4ClassifierDeclaration) {
			N4ClassifierDeclaration classifierDeclaration = (N4ClassifierDeclaration) obj;
			if (classifierDeclaration.getDefinedType() != null) {
				getSymbolLabel(classifierDeclaration.getDefinedType());
			}
			return "<unknown>";
		}
		if (obj instanceof N4GetterDeclaration) {
			N4GetterDeclaration getterDeclaration = (N4GetterDeclaration) obj;
			return getSymbolLabel(getterDeclaration.getDefinedGetter());
		}
		if (obj instanceof N4SetterDeclaration) {
			N4SetterDeclaration setterDeclaration = (N4SetterDeclaration) obj;
			return getSymbolLabel(setterDeclaration.getDefinedSetter());
		}
		if (obj instanceof ExportedVariableStatement) {
			// comma separated list of all contained variable names
			ExportedVariableStatement exportedVariableStatement = (ExportedVariableStatement) obj;
			String text = IterableExtensions.join(exportedVariableStatement.getVarDecl(), ", ", vd -> vd.getName());
			return text;
		}
		if (obj instanceof NamedElement) {
			NamedElement namedElement = (NamedElement) obj;
			return namedElement.getName();
		}
		if (obj instanceof TModule) {
			// dispatchDoGetText types model the fully qualified module specifier, e.g. mypack/MyFile
			TModule tModule = (TModule) obj;
			return getModuleSpecifier(tModule);
		}
		if (obj instanceof TClassifier) {
			// name + optional type variables, e.g. A<T, U>
			TClassifier tClassifier = (TClassifier) obj;
			return tClassifier.getName() + getTypeVersionDescription(tClassifier) + getTypeVarDescriptions(tClassifier);
		}
		if (obj instanceof TGetter) {
			TGetter tGetter = (TGetter) obj;
			return tGetter.getName();
		}
		if (obj instanceof TSetter) {
			TSetter tSetter = (TSetter) obj;
			return tSetter.getName();
		}
		if (obj instanceof IdentifiableElement) {
			IdentifiableElement identifiableElement = (IdentifiableElement) obj;
			return identifiableElement.getName();
		}
		if (obj instanceof IEObjectDescription) {
			IEObjectDescription ieObjectDescription = (IEObjectDescription) obj;
			EClass eClass = ieObjectDescription.getEClass();
			if (TypesPackage.eINSTANCE.getTN4Classifier().isSuperTypeOf(eClass) ||
					TypesPackage.eINSTANCE.getTEnum().isSuperTypeOf(eClass)) {
				return ieObjectDescription.getQualifiedName().getLastSegment();
			}
		}
		return "<unknown>";
	}

	private String getTypeVarDescriptions(TClassifier tClassifier) {
		if (tClassifier.getTypeVars().size() > 0) {
			String text = IterableExtensions.join(tClassifier.getTypeVars(), ", ", tv -> tv.getName());
			return "<" + text + ">";
		}
		return "";
	}

	private String getTypeVersionDescription(TClassifier tClassifier) {
		if (tClassifier.getDeclaredVersion() != 0) {
			return N4IDLGlobals.VERSION_SEPARATOR + Integer.toString(tClassifier.getDeclaredVersion());
		}
		return "";
	}

	private String getModuleSpecifier(TModule tModule) {
		if (tModule != null && tModule.getQualifiedName() != null) {
			return tModule.getModuleSpecifier();
		}
		return "<unknown>";
	}
}
