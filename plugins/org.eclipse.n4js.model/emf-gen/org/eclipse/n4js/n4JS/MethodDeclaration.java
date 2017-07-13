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

import org.eclipse.n4js.ts.types.TMember;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Method Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getMethodDeclaration()
 * @model abstract="true"
 * @generated
 */
public interface MethodDeclaration extends FunctionDefinition, GenericDeclaration, TypedElement, PropertyNameOwner {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns true if an explicit call to the super constructor is found.
	 * Of course, this is only allowed in constructors. Note that this method searches for ALL
	 * statements in the body, not only the first one (which is the only valid place).
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.n4JS.ExpressionStatement%>, <%org.eclipse.n4js.n4JS.Expression%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.n4JS.ExpressionStatement%>, <%org.eclipse.n4js.n4JS.Expression%>>()\n{\n\tpublic <%org.eclipse.n4js.n4JS.Expression%> apply(final <%org.eclipse.n4js.n4JS.ExpressionStatement%> it)\n\t{\n\t\treturn it.getExpression();\n\t}\n};\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.n4JS.ParameterizedCallExpression%>, <%java.lang.Boolean%>> _function_1 = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.n4JS.ParameterizedCallExpression%>, <%java.lang.Boolean%>>()\n{\n\tpublic <%java.lang.Boolean%> apply(final <%org.eclipse.n4js.n4JS.ParameterizedCallExpression%> it)\n\t{\n\t\t<%org.eclipse.n4js.n4JS.Expression%> _target = it.getTarget();\n\t\treturn <%java.lang.Boolean%>.valueOf((_target instanceof <%org.eclipse.n4js.n4JS.SuperLiteral%>));\n\t}\n};\nfinal boolean existsSuperCall = <%org.eclipse.xtext.xbase.lib.IteratorExtensions%>.<<%org.eclipse.n4js.n4JS.ParameterizedCallExpression%>>exists(<%com.google.common.collect.Iterators%>.<<%org.eclipse.n4js.n4JS.ParameterizedCallExpression%>>filter(<%org.eclipse.xtext.xbase.lib.IteratorExtensions%>.<<%org.eclipse.n4js.n4JS.ExpressionStatement%>, <%org.eclipse.n4js.n4JS.Expression%>>map(<%com.google.common.collect.Iterators%>.<<%org.eclipse.n4js.n4JS.ExpressionStatement%>>filter(<%org.eclipse.n4js.utils.EcoreUtilN4%>.<<%org.eclipse.n4js.n4JS.Statement%>>getAllDirectlyFoundContentsOfType(this.getBody(), <%org.eclipse.n4js.n4JS.Statement%>.class), <%org.eclipse.n4js.n4JS.ExpressionStatement%>.class), _function), <%org.eclipse.n4js.n4JS.ParameterizedCallExpression%>.class), _function_1);\nreturn existsSuperCall;'"
	 * @generated
	 */
	boolean existsExplicitSuperCall();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.n4js.ts.types.TMember%> _xifexpression = null;\n<%org.eclipse.n4js.ts.types.Type%> _definedType = this.getDefinedType();\nboolean _tripleEquals = (_definedType == null);\nif (_tripleEquals)\n{\n\t_xifexpression = null;\n}\nelse\n{\n\t<%org.eclipse.n4js.ts.types.TMember%> _xifexpression_1 = null;\n\t<%org.eclipse.n4js.ts.types.Type%> _definedType_1 = this.getDefinedType();\n\tif ((_definedType_1 instanceof <%org.eclipse.n4js.ts.types.TMember%>))\n\t{\n\t\t<%org.eclipse.n4js.ts.types.Type%> _definedType_2 = this.getDefinedType();\n\t\t_xifexpression_1 = ((<%org.eclipse.n4js.ts.types.TMember%>) _definedType_2);\n\t}\n\telse\n\t{\n\t\tthrow new <%java.lang.IllegalArgumentException%>(\n\t\t\t\"\");\n\t}\n\t_xifexpression = _xifexpression_1;\n}\nreturn _xifexpression;'"
	 * @generated
	 */
	TMember getDefinedTypeElement();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return false;'"
	 * @generated
	 */
	boolean isStatic();

} // MethodDeclaration
