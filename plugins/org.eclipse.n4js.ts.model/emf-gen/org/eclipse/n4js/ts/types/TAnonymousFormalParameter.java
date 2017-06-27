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
package org.eclipse.n4js.ts.types;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TAnonymous Formal Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTAnonymousFormalParameter()
 * @model
 * @generated
 */
public interface TAnonymousFormalParameter extends TFormalParameter {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the name of the anonymous parameter, maybe an artificial name
	 * (_par_n with n is the index of the parameter in the parameter list, or just _par_ in case of setter).
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final <%java.lang.String%> definedName = this.getDefinedName();\nif ((definedName != null))\n{\n\treturn definedName;\n}\n<%org.eclipse.emf.ecore.EObject%> _eContainer = this.eContainer();\n<%org.eclipse.emf.ecore.EStructuralFeature%> _eContainingFeature = this.eContainingFeature();\nfinal <%java.lang.Object%> containingFeature = _eContainer.eGet(_eContainingFeature);\nif ((containingFeature instanceof <%java.util.List%><?>))\n{\n\t<%org.eclipse.emf.ecore.EObject%> _eContainer_1 = this.eContainer();\n\t<%org.eclipse.emf.ecore.EStructuralFeature%> _eContainingFeature_1 = this.eContainingFeature();\n\t<%java.lang.Object%> _eGet = _eContainer_1.eGet(_eContainingFeature_1);\n\tfinal int index = ((<%java.util.List%><?>) _eGet).indexOf(this);\n\treturn (\"_par_\" + <%java.lang.Integer%>.valueOf(index));\n}\nelse\n{\n\treturn \"_par_\";\n}'"
	 * @generated
	 */
	String getName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the declared name, may be null since parameter is anonymous and name is optional
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return super.getName();'"
	 * @generated
	 */
	String getDefinedName();

} // TAnonymousFormalParameter
