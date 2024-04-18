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
package org.eclipse.n4js.typesbuilder;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.fold;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.BindingPattern;
import org.eclipse.n4js.n4JS.CatchVariable;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.TryStatement;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableDeclarationContainer;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.utils.ResourceType;

import com.google.inject.Inject;

class N4JSVariableStatementTypesBuilder {

	@Inject
	N4JSTypesBuilderHelper _n4JSTypesBuilderHelper;

	@Inject
	N4JSExportDefinitionTypesBuilder exportDefinitionTypesBuilder;

	int relinkVariableTypes(VariableDeclarationContainer n4VarDeclContainer, AbstractNamespace target,
			boolean preLinkingPhase, int start) {
		return relinkVariableTypes(n4VarDeclContainer.getVarDecl(), target, preLinkingPhase, start);
	}

	int relinkVariableTypes(TryStatement tryStmnt, AbstractNamespace target, boolean preLinkingPhase, int start) {
		CatchVariable catchVariable = tryStmnt.getCatch() == null ? null : tryStmnt.getCatch().getCatchVariable();
		if (catchVariable == null) {
			return start;
		}

		BindingPattern bindingPattern = catchVariable.getBindingPattern();
		if (bindingPattern != null) {
			return relinkVariableTypes(bindingPattern.getAllVariableDeclarations(), target, preLinkingPhase, start);
		} else {
			TVariable tVariable = createVariable(catchVariable);
			target.getLocalVariables().add(tVariable);
			return start;
		}
	}

	private int relinkVariableTypes(Iterable<VariableDeclaration> n4VarDecls, AbstractNamespace target,
			boolean preLinkingPhase, int start) {
		return fold(n4VarDecls, start, (idx, decl) -> {
			if (relinkVariableType(decl, target, preLinkingPhase, idx)) {
				return idx + 1;
			}
			return idx;
		});
	}

	private boolean relinkVariableType(VariableDeclaration n4VariableDeclaration, AbstractNamespace target,
			boolean preLinkingPhase, int idx) {
		if (n4VariableDeclaration.getName() == null) {
			return false;
		}
		if (!n4VariableDeclaration.isDirectlyExported()) {
			// local variables are not serialized, so we have to re-create them during re-linking
			TVariable tVariable = createVariable(n4VariableDeclaration, preLinkingPhase);
			target.getLocalVariables().add(tVariable);
			return false;
		}

		TVariable variable = target.getExportedVariables().get(idx);
		_n4JSTypesBuilderHelper.ensureEqualName(n4VariableDeclaration, variable);
		variable.setAstElement(n4VariableDeclaration);
		n4VariableDeclaration.setDefinedVariable(variable);
		return true;
	}

	void createVariableTypes(VariableDeclarationContainer n4VarDeclContainer, AbstractNamespace target,
			boolean preLinkingPhase) {
		EList<TVariable> expVars = target.getExportedVariables();
		EList<TVariable> locVars = target.getLocalVariables();

		boolean isExported = (n4VarDeclContainer instanceof VariableStatement)
				? ((VariableStatement) n4VarDeclContainer).isDirectlyExported()
				: false;

		for (VariableDeclaration varDecl : n4VarDeclContainer.getVarDecl()) {
			TVariable variable = createVariable(varDecl, preLinkingPhase);
			if (variable != null) {
				if (isExported) {
					String exportedName = varDecl.getDirectlyExportedName();
					exportDefinitionTypesBuilder.createExportDefinitionForDirectlyExportedElement(variable,
							exportedName, target, preLinkingPhase);
					_n4JSTypesBuilderHelper.setTypeAccessModifier(variable, (VariableStatement) n4VarDeclContainer);
					expVars.add(variable);
				} else {
					if (n4VarDeclContainer instanceof VariableStatement) { // could also be a ForStatement
						_n4JSTypesBuilderHelper.setTypeAccessModifier(variable, (VariableStatement) n4VarDeclContainer);
					}
					locVars.add(variable);
				}
			}
		}
	}

	void createVariableTypes(TryStatement tryStmnt, AbstractNamespace target, boolean preLinkingPhase) {
		EList<TVariable> locVars = target.getLocalVariables();

		CatchVariable catchVariable = tryStmnt.getCatch() == null ? null : tryStmnt.getCatch().getCatchVariable();
		if (catchVariable == null) {
			return;
		}

		BindingPattern bindingPattern = catchVariable.getBindingPattern();
		if (bindingPattern != null) {
			for (VariableDeclaration varDecl : bindingPattern.getAllVariableDeclarations()) {
				TVariable variable = createVariable(varDecl, preLinkingPhase);
				if (variable != null) {
					locVars.add(variable);
				}
			}
		} else {
			TVariable variable = createVariable(catchVariable);
			if (variable != null) {
				locVars.add(variable);
			}
		}
	}

	private TVariable createVariable(VariableDeclaration n4VariableDeclaration, boolean preLinkingPhase) {
		if (n4VariableDeclaration.getName() == null) {
			return null;
		}

		TVariable variable = TypesFactory.eINSTANCE.createTVariable();
		variable.setName(n4VariableDeclaration.getName());
		variable.setConst(n4VariableDeclaration.isConst());
		variable.setObjectLiteral(n4VariableDeclaration.getExpression() instanceof ObjectLiteral);
		variable.setNewExpression(n4VariableDeclaration.getExpression() instanceof NewExpression);

		_n4JSTypesBuilderHelper.copyAnnotations(variable, n4VariableDeclaration, preLinkingPhase);
		variable.setDeclaredProvidedByRuntime(
				AnnotationDefinition.PROVIDED_BY_RUNTIME.hasAnnotation(n4VariableDeclaration));

		// set declared type (if any), otherwise type will be inferred in phase 2
		setVariableType(variable, n4VariableDeclaration, preLinkingPhase);

		variable.setAstElement(n4VariableDeclaration);
		n4VariableDeclaration.setDefinedVariable(variable);

		return variable;
	}

	private TVariable createVariable(CatchVariable catchVariable) {
		if (catchVariable.getName() == null) {
			return null;
		}

		TVariable variable = TypesFactory.eINSTANCE.createTVariable();
		variable.setName(catchVariable.getName());

		variable.setAstElement(catchVariable);
		catchVariable.setDefinedVariable(variable);

		return variable;
	}

	private void setVariableType(TVariable variable, VariableDeclaration n4VariableDeclaration,
			boolean preLinkingPhase) {
		if (n4VariableDeclaration.getDeclaredTypeRefInAST() != null) {
			if (!preLinkingPhase)
				// type of field was declared explicitly
				variable.setTypeRef(TypeUtils.copyWithProxies(n4VariableDeclaration.getDeclaredTypeRefInAST()));
		} else {
			// in all other cases:
			// leave it to the TypingASTWalker to infer the type (e.g. from the initializer expression, if given)
			variable.setTypeRef(TypeUtils.createDeferredTypeRef());
		}
	}

	TVariable createImplicitArgumentsVariable(FunctionOrFieldAccessor funOrAccDecl, AbstractNamespace target,
			BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {
		if (funOrAccDecl instanceof ArrowFunction) {
			return null; // not available in arrow functions
		}
		ResourceType resourceType = ResourceType.getResourceType(funOrAccDecl);
		if (resourceType != ResourceType.N4JS && resourceType != ResourceType.N4JSX) {
			return null; // not required in definition files
		}

		TVariable formalParameterType = TypesFactory.eINSTANCE.createTVariable();
		formalParameterType.setName(N4JSLanguageConstants.LOCAL_ARGUMENTS_VARIABLE_NAME);

		if (!preLinkingPhase) {
			formalParameterType.setTypeRef(TypeUtils.createTypeRef(builtInTypeScope.getArgumentsType()));
		}

		funOrAccDecl.setImplicitArgumentsVariable(formalParameterType);
		formalParameterType.setAstElement(funOrAccDecl);

		target.getLocalVariables().add(formalParameterType);

		return formalParameterType;
	}
}
