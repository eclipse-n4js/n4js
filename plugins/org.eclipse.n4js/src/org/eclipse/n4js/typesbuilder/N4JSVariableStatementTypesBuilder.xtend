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
import org.eclipse.n4js.N4JSLanguageConstants
import org.eclipse.n4js.n4JS.ArrowFunction
import org.eclipse.n4js.n4JS.CatchVariable
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor
import org.eclipse.n4js.n4JS.NewExpression
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.TryStatement
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableDeclarationContainer
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.types.AbstractNamespace
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.types.utils.TypeUtils
import org.eclipse.n4js.utils.ResourceType

package class N4JSVariableStatementTypesBuilder {

	@Inject extension N4JSTypesBuilderHelper

	def package int relinkVariableTypes(VariableDeclarationContainer n4VarDeclContainer, AbstractNamespace target, boolean preLinkingPhase, int start) {
		return relinkVariableTypes(n4VarDeclContainer.varDecl, target, preLinkingPhase, start);
	}

	def package int relinkVariableTypes(TryStatement tryStmnt, AbstractNamespace target, boolean preLinkingPhase, int start) {
		val catchVariable = tryStmnt.^catch?.catchVariable;
		if (catchVariable === null) {
			return start;
		}

		val bindingPattern = catchVariable.bindingPattern;
		if (bindingPattern !== null) {
			return relinkVariableTypes(bindingPattern.allVariableDeclarations, target, preLinkingPhase, start);
		} else {
			val tVariable = createVariable(catchVariable, preLinkingPhase);
			target.containingRootModule.localVariables += tVariable;
			return start;
		}
	}

	def private int relinkVariableTypes(Iterable<VariableDeclaration> n4VarDecls, AbstractNamespace target, boolean preLinkingPhase, int start) {
		return n4VarDecls.fold(start) [ idx, decl |
			if (decl.relinkVariableType(target, preLinkingPhase, idx)) {
				return idx + 1;
			}
			return idx;
		];
	}

	def private boolean relinkVariableType(VariableDeclaration n4VariableDeclaration, AbstractNamespace target, boolean preLinkingPhase, int idx) {
		if(n4VariableDeclaration.name === null) {
			return false
		}
		if (!n4VariableDeclaration.exported) {
			// local variables are not serialized, so we have to re-create them during re-linking
			val tVariable = createVariable(n4VariableDeclaration, preLinkingPhase);
			target.containingRootModule.localVariables += tVariable;
			return false;
		}

		val variable = target.variables.get(idx);
		ensureEqualName(n4VariableDeclaration, variable);
		variable.astElement = n4VariableDeclaration
		n4VariableDeclaration.definedVariable = variable;
		return true
	}

	def package void createVariableTypes(VariableDeclarationContainer n4VarDeclContainer, AbstractNamespace target, boolean preLinkingPhase) {
		val expVars = target.variables;
		val locVars = target.containingRootModule.localVariables;

		val isExported = if (n4VarDeclContainer instanceof VariableStatement) n4VarDeclContainer.exported else false;

		for (varDecl : n4VarDeclContainer.varDecl) {
			val variable = createVariable(varDecl, preLinkingPhase);
			if (variable !== null) {
				if (isExported) {
					variable.exportedName = varDecl.exportedName;
					variable.setTypeAccessModifier(n4VarDeclContainer as VariableStatement);
					expVars += variable;
				} else {
					locVars += variable;
				}
			}
		}
	}

	def package void createVariableTypes(TryStatement tryStmnt, AbstractNamespace target, boolean preLinkingPhase) {
		val locVars = target.containingRootModule.localVariables;

		val catchVariable = tryStmnt.^catch?.catchVariable;
		if (catchVariable === null) {
			return;
		}

		val bindingPattern = catchVariable.bindingPattern;
		if (bindingPattern !== null) {
			for (varDecl : bindingPattern.allVariableDeclarations) {
				val variable = createVariable(varDecl, preLinkingPhase);
				if (variable !== null) {
					locVars += variable;
				}
			}
		} else {
			val variable = createVariable(catchVariable, preLinkingPhase);
			if (variable !== null) {
				locVars += variable;
			}
		}
	}

	def private TVariable createVariable(VariableDeclaration n4VariableDeclaration, boolean preLinkingPhase) {
		if(n4VariableDeclaration.name === null) {
			return null
		}

		val variable = TypesFactory.eINSTANCE.createTVariable
		variable.name = n4VariableDeclaration.name;
		variable.const = n4VariableDeclaration.const;
		variable.objectLiteral = n4VariableDeclaration.expression instanceof ObjectLiteral;
		variable.newExpression = n4VariableDeclaration.expression instanceof NewExpression;

		variable.copyAnnotations(n4VariableDeclaration, preLinkingPhase)
		variable.declaredProvidedByRuntime = AnnotationDefinition.PROVIDED_BY_RUNTIME.hasAnnotation(n4VariableDeclaration)

		// set declared type (if any), otherwise type will be inferred in phase 2
		setVariableType(variable, n4VariableDeclaration, preLinkingPhase)

		variable.astElement = n4VariableDeclaration
		n4VariableDeclaration.definedVariable = variable;

		return variable
	}

	def private TVariable createVariable(CatchVariable catchVariable, boolean preLinkingPhase) {
		if (catchVariable.name === null) {
			return null;
		}

		val variable = TypesFactory.eINSTANCE.createTVariable
		variable.name = catchVariable.name;

		variable.astElement = catchVariable;
		catchVariable.definedVariable = variable;

		return variable
	}

	def private void setVariableType(TVariable variable, VariableDeclaration n4VariableDeclaration, boolean preLinkingPhase) {
		if(n4VariableDeclaration.declaredTypeRefInAST!==null) {
			if (!preLinkingPhase)
			// 	type of field was declared explicitly
				variable.typeRef = TypeUtils.copyWithProxies(n4VariableDeclaration.declaredTypeRefInAST);
		}
		else {
			// in all other cases:
			// leave it to the TypingASTWalker to infer the type (e.g. from the initializer expression, if given)
			variable.typeRef = TypeUtils.createDeferredTypeRef
		}
	}


	def package TVariable createImplicitArgumentsVariable(FunctionOrFieldAccessor funOrAccDecl, AbstractNamespace target, BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {
		if (funOrAccDecl instanceof ArrowFunction) {
			return null; // not available in arrow functions
		}
		val resourceType = ResourceType.getResourceType(funOrAccDecl);
		if (resourceType !== ResourceType.N4JS && resourceType !== ResourceType.N4JSX) {
			return null; // not required in definition files
		}

		val formalParameterType = TypesFactory::eINSTANCE.createTVariable();
		formalParameterType.name = N4JSLanguageConstants.LOCAL_ARGUMENTS_VARIABLE_NAME;

		if (!preLinkingPhase) {
			formalParameterType.typeRef = TypeUtils.createTypeRef(builtInTypeScope.argumentsType);
		}

		funOrAccDecl.implicitArgumentsVariable = formalParameterType;
		formalParameterType.astElement = funOrAccDecl;

		target.containingRootModule.localVariables += formalParameterType;

		return formalParameterType;
	}
}
