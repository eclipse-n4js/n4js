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
package org.eclipse.n4js.n4JS;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Exportable Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * TODO add support for separated export statements, i.e. code like this:
 * <pre>
 * class C {}
 * export {C as X}
 * </pre>
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getExportableElement()
 * @model abstract="true"
 * @generated
 */
public interface ExportableElement extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.ecore.EObject%> _eContainer = this.eContainer();\nreturn (_eContainer instanceof <%org.eclipse.n4js.n4JS.ExportDeclaration%>);'"
	 * @generated
	 */
	boolean isExported();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return (this.isExported() && ((<%org.eclipse.n4js.n4JS.ExportDeclaration%>) this.eContainer()).isDefaultExport());'"
	 * @generated
	 */
	boolean isExportedAsDefault();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isExported = this.isExported();\nif (_isExported)\n{\n\t<%org.eclipse.emf.ecore.EObject%> _eContainer = this.eContainer();\n\tfinal <%org.eclipse.n4js.n4JS.ExportDeclaration%> exportDecl = ((<%org.eclipse.n4js.n4JS.ExportDeclaration%>) _eContainer);\n\tboolean _isDefaultExport = exportDecl.isDefaultExport();\n\tif (_isDefaultExport)\n\t{\n\t\treturn \"default\";\n\t}\n\tfinal <%org.eclipse.n4js.n4JS.ExportableElement%> me = this;\n\t<%java.lang.String%> _switchResult = null;\n\tboolean _matched = false;\n\tif (me instanceof <%org.eclipse.n4js.n4JS.NamedElement%>)\n\t{\n\t\t_matched=true;\n\t\t_switchResult = ((<%org.eclipse.n4js.n4JS.NamedElement%>)me).getName();\n\t}\n\tif (!_matched)\n\t{\n\t\tif (me instanceof <%org.eclipse.n4js.ts.types.IdentifiableElement%>)\n\t\t{\n\t\t\t_matched=true;\n\t\t\t_switchResult = ((<%org.eclipse.n4js.ts.types.IdentifiableElement%>)me).getName();\n\t\t}\n\t}\n\treturn _switchResult;\n}\nreturn null;'"
	 * @generated
	 */
	String getExportedName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true if element is a top-level declaration in the script, this is also true if element is actually exported
	 * (and is a child of an export statements). This should be true for most declarations, however some (as function declaration) may be nested (and
	 * transpiled to expressions later).
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.ecore.EObject%> _eContainer = this.eContainer();\nif ((_eContainer instanceof <%org.eclipse.n4js.n4JS.ExportDeclaration%>))\n{\n\t<%org.eclipse.emf.ecore.EObject%> _eContainer_1 = this.eContainer().eContainer();\n\treturn (_eContainer_1 instanceof <%org.eclipse.n4js.n4JS.Script%>);\n}\n<%org.eclipse.emf.ecore.EObject%> _eContainer_2 = this.eContainer();\nreturn (_eContainer_2 instanceof <%org.eclipse.n4js.n4JS.Script%>);'"
	 * @generated
	 */
	boolean isToplevel();

} // ExportableElement
