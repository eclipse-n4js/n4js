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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final &lt;%java.lang.String%&gt; definedName = this.getDefinedName();\nif ((definedName != null))\n{\n\treturn definedName;\n}\nfinal &lt;%java.lang.Object%&gt; containingFeature = this.eContainer().eGet(this.eContainingFeature());\nif ((containingFeature instanceof &lt;%java.util.List%&gt;&lt;?&gt;))\n{\n\t&lt;%java.lang.Object%&gt; _eGet = this.eContainer().eGet(this.eContainingFeature());\n\tfinal int index = ((&lt;%java.util.List%&gt;&lt;?&gt;) _eGet).indexOf(this);\n\treturn (\"_par_\" + &lt;%java.lang.Integer%&gt;.valueOf(index));\n}\nelse\n{\n\treturn \"_par_\";\n}'"
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
