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

import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TypableElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Assignment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A PropertyAssignment is either a simple {@link PropertyNameValuePair},
 * or a property accessor, that is a {@link PropertyGetterDeclaration} or
 * {@link PropertySetterDeclaration}.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getPropertyAssignment()
 * @model abstract="true"
 * @generated
 */
public interface PropertyAssignment extends AnnotableElement, VariableEnvironmentElement, PropertyNameOwner, TypableElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TStructMember getDefinedMember();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Properties may not be called 'prototype' or 'constructor' (except for computed names).
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%java.lang.String%&gt; _name = this.getName();\nboolean _equals = &lt;%com.google.common.base.Objects%&gt;.equal(\"prototype\", _name);\nif (_equals)\n{\n\treturn false;\n}\nboolean _and = false;\n&lt;%java.lang.String%&gt; _name_1 = this.getName();\nboolean _equals_1 = &lt;%com.google.common.base.Objects%&gt;.equal(\"constructor\", _name_1);\nif (!_equals_1)\n{\n\t_and = false;\n} else\n{\n\t&lt;%org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName%&gt; _declaredName = this.getDeclaredName();\n\t&lt;%org.eclipse.n4js.n4JS.PropertyNameKind%&gt; _kind = null;\n\tif (_declaredName!=null)\n\t{\n\t\t_kind=_declaredName.getKind();\n\t}\n\tboolean _tripleNotEquals = (_kind != &lt;%org.eclipse.n4js.n4JS.PropertyNameKind%&gt;.COMPUTED);\n\t_and = _tripleNotEquals;\n}\nif (_and)\n{\n\treturn false;\n}\nreturn true;'"
	 * @generated
	 */
	boolean isValidName();

} // PropertyAssignment
