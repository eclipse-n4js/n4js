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
package org.eclipse.n4js.typesbuilder

import com.google.inject.Inject
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration
import org.eclipse.n4js.n4JS.ExportedVariableStatement
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.n4js.ts.types.TypeAccessModifier
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.n4JS.NewExpression

package class N4JSVariableStatementTypesBuilder {

	@Inject extension N4JSTypesBuilderHelper

	def package void createVariableTypes(VariableStatement n4VariableStatement, TModule target, boolean preLinkingPhase) {
		val variables = n4VariableStatement.createVariables(preLinkingPhase)
		target.variables += variables
	}

	def private createVariables(VariableStatement n4VariableStatement, boolean preLinkingPhase) {
		n4VariableStatement.varDecl.filter(ExportedVariableDeclaration).map[createVariable(n4VariableStatement, preLinkingPhase)].filterNull
	}

	def private TVariable createVariable(ExportedVariableDeclaration n4VariableDeclaration, VariableStatement n4VariableStatement, boolean preLinkingPhase) {
		if(n4VariableDeclaration.name === null) {
			return null
		}

		val variable = TypesFactory.eINSTANCE.createTVariable
		variable.name = n4VariableDeclaration.name;
		variable.const = n4VariableDeclaration.const;
		variable.objectLiteral = n4VariableDeclaration.expression instanceof ObjectLiteral;
		variable.newExpression = n4VariableDeclaration.expression instanceof NewExpression;
		if (n4VariableStatement instanceof ExportedVariableStatement) {
			variable.exportedName = n4VariableStatement.exportedName
														?: n4VariableDeclaration.name // FIXME temporary hack to work around broken inheritance structure in n4js.xcore!!!!
			variable.setTypeAccessModifier(n4VariableStatement)
		} else {
			variable.exportedName = null
		}

		variable.copyAnnotations(n4VariableDeclaration, preLinkingPhase)
		variable.declaredProvidedByRuntime = AnnotationDefinition.PROVIDED_BY_RUNTIME.hasAnnotation(n4VariableDeclaration)

		// set declared type (if any), otherwise type will be inferred in phase 2
		setVariableType(variable, n4VariableDeclaration, preLinkingPhase)

		variable.astElement = n4VariableDeclaration
		n4VariableDeclaration.definedVariable = variable;

		return variable
	}

	def private setVariableType(TVariable variable, ExportedVariableDeclaration n4VariableDeclaration, boolean preLinkingPhase) {
		if(n4VariableDeclaration.declaredTypeRef!==null) {
			// type of field was declared explicitly
			setCopyOfReference([TypeRef typeRef | variable.typeRef = typeRef], n4VariableDeclaration.declaredTypeRef, preLinkingPhase)
		}
		else {
			// in all other cases:
			// leave it to the TypingASTWalker to infer the type (e.g. from the initializer expression, if given)
			variable.typeRef = TypeUtils.createDeferredTypeRef
		}
	}

	def private setTypeAccessModifier(TVariable variable, ExportedVariableStatement stmt) {
		setTypeAccessModifier(stmt, [TypeAccessModifier modifier|
			variable.declaredTypeAccessModifier = modifier], stmt.declaredModifiers,
			getAllAnnotations(stmt))
	}
}
