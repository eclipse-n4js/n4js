/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.dts.astbuilders;

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_arrayLiteral;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_elementList;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_objectLiteral;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_variableDeclarationList;

import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.ParserContextUtil;
import org.eclipse.n4js.dts.TypeScriptParser.ArrayElementContext;
import org.eclipse.n4js.dts.TypeScriptParser.ArrayLiteralContext;
import org.eclipse.n4js.dts.TypeScriptParser.BindingElementContext;
import org.eclipse.n4js.dts.TypeScriptParser.BindingPatternBlockContext;
import org.eclipse.n4js.dts.TypeScriptParser.BindingPatternContext;
import org.eclipse.n4js.dts.TypeScriptParser.MethodPropertyContext;
import org.eclipse.n4js.dts.TypeScriptParser.ObjectLiteralContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyExpressionAssignmentContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyGetterContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertySetterContext;
import org.eclipse.n4js.dts.TypeScriptParser.RestParameterInObjectContext;
import org.eclipse.n4js.dts.TypeScriptParser.VariableDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.VariableStatementContext;
import org.eclipse.n4js.n4JS.ArrayBindingPattern;
import org.eclipse.n4js.n4JS.BindingElement;
import org.eclipse.n4js.n4JS.BindingPattern;
import org.eclipse.n4js.n4JS.BindingProperty;
import org.eclipse.n4js.n4JS.ExportedVariableStatement;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.ObjectBindingPattern;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.n4JS.VariableStatementKeyword;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class DtsVariableBuilder extends AbstractDtsSubBuilder<VariableStatementContext, VariableStatement> {
	private final DtsTypeRefBuilder typeRefBuilder = new DtsTypeRefBuilder(tokenStream, resource);

	private boolean parentIsNamespace;

	/** Constructor */
	public DtsVariableBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return java.util.Set.of(
				RULE_variableDeclarationList,
				RULE_arrayLiteral,
				RULE_elementList,
				RULE_objectLiteral);
	}

	public VariableStatement consumeInScript(VariableStatementContext ctx) {
		this.parentIsNamespace = false;
		return consume(ctx);
	}

	public ExportedVariableStatement consumeInNamespace(VariableStatementContext ctx) {
		this.parentIsNamespace = true;
		return (ExportedVariableStatement) consume(ctx);
	}

	@Override
	public void enterVariableStatement(VariableStatementContext ctx) {
		boolean exported = ParserContextUtil.isExported(ctx);
		if (exported || parentIsNamespace) {
			result = N4JSFactory.eINSTANCE.createExportedVariableStatement();
			EList<N4Modifier> declaredModifiers = ((ExportedVariableStatement) result).getDeclaredModifiers();
			declaredModifiers.add(N4Modifier.EXTERNAL);
			declaredModifiers.add(N4Modifier.PUBLIC);
		} else {
			result = N4JSFactory.eINSTANCE.createVariableStatement();
			// TODO: missing modifier N4Modifier.EXTERNAL
		}
		VariableStatementKeyword keyword = VariableStatementKeyword.VAR;
		if (ctx.varModifier().Const() != null) {
			keyword = VariableStatementKeyword.CONST;
		} else if (ctx.varModifier().Let() != null) {
			keyword = VariableStatementKeyword.LET;
		}
		result.setVarStmtKeyword(keyword);

		walker.enqueue(ctx.bindingPatternBlock());
		walker.enqueue(ctx.variableDeclarationList());
	}

	@Override
	public void enterBindingPatternBlock(BindingPatternBlockContext ctx) {
		boolean exported = ParserContextUtil.isExported(ctx);
		VariableBinding varBinding = exported ? N4JSFactory.eINSTANCE.createExportedVariableBinding()
				: N4JSFactory.eINSTANCE.createVariableBinding();

		BindingPattern bindingPattern = new DtsBindingPatternBuilder().consume(ctx.bindingPattern());

		varBinding.setPattern(bindingPattern);

		result.getVarDeclsOrBindings().add(varBinding);
	}

	@Override
	public void enterVariableDeclaration(VariableDeclarationContext ctx) {
		boolean exported = ParserContextUtil.isExported(ctx);
		VariableDeclaration varDecl = exported ? N4JSFactory.eINSTANCE.createExportedVariableDeclaration()
				: N4JSFactory.eINSTANCE.createVariableDeclaration();
		varDecl.setName(ctx.identifierName().getText());

		TypeReferenceNode<TypeRef> typeRef = typeRefBuilder.consume(ctx.colonSepTypeRef());
		varDecl.setDeclaredTypeRefNode(typeRef);

		varDecl.setExpression(new DtsExpressionBuilder(tokenStream, resource).consume(ctx.singleExpression()));

		result.getVarDeclsOrBindings().add(varDecl);
	}

	class DtsBindingPatternBuilder extends AbstractDtsSubBuilder<BindingPatternContext, BindingPattern> {

		/** Constructor */
		public DtsBindingPatternBuilder() {
			super(DtsVariableBuilder.this.tokenStream, DtsVariableBuilder.this.resource);
		}

		@Override
		protected Set<Integer> getVisitChildrenOfRules() {
			return java.util.Set.of(
					RULE_arrayLiteral,
					RULE_elementList,
					RULE_objectLiteral);
		}

		@Override
		public void enterArrayLiteral(ArrayLiteralContext ctx) {
			result = N4JSFactory.eINSTANCE.createArrayBindingPattern();
		}

		@Override
		public void enterObjectLiteral(ObjectLiteralContext ctx) {
			result = N4JSFactory.eINSTANCE.createObjectBindingPattern();
		}

		@Override
		public void enterArrayElement(ArrayElementContext ctx) {
			BindingElement bindingElem = N4JSFactory.eINSTANCE.createBindingElement();
			bindingElem.setRest(ctx.Ellipsis() != null);
			BindingElementContext bindingElemCtx = ctx.bindingElement();

			if (bindingElemCtx.Identifier() != null) {
				VariableDeclaration varDecl = N4JSFactory.eINSTANCE.createVariableDeclaration();
				varDecl.setName(bindingElemCtx.Identifier().getText());
				bindingElem.setVarDecl(varDecl);
			}
			if (bindingElemCtx.bindingPattern() != null) {
				BindingPattern nestedBP = new DtsBindingPatternBuilder().consume(bindingElemCtx.bindingPattern());
				bindingElem.setNestedPattern(nestedBP);
			}
			((ArrayBindingPattern) result).getElements().add(bindingElem);
		}

		@Override
		public void enterPropertyExpressionAssignment(PropertyExpressionAssignmentContext ctx) {
			BindingProperty bindingProp = N4JSFactory.eINSTANCE.createBindingProperty();
			BindingElement bindingElem = N4JSFactory.eINSTANCE.createBindingElement();
			LiteralOrComputedPropertyName name = N4JSFactory.eINSTANCE.createLiteralOrComputedPropertyName();
			name.setLiteralName(ctx.propertyName().getText());
			bindingProp.setDeclaredName(name);
			bindingProp.setValue(bindingElem);

			if (ctx.bindingPattern() != null) {
				BindingPattern bindingPattern = new DtsBindingPatternBuilder().consume(ctx.bindingPattern());
				bindingElem.setNestedPattern(bindingPattern);
			} else {
				VariableDeclaration varDecl = N4JSFactory.eINSTANCE.createVariableDeclaration();
				varDecl.setName(ctx.identifierOrKeyWord().getText());
				bindingElem.setVarDecl(varDecl);
			}

			((ObjectBindingPattern) result).getProperties().add(bindingProp);
		}

		@Override
		public void enterPropertyGetter(PropertyGetterContext ctx) {
			// still unsupported
		}

		@Override
		public void enterPropertySetter(PropertySetterContext ctx) {
			// still unsupported
		}

		@Override
		public void enterMethodProperty(MethodPropertyContext ctx) {
			// still unsupported
		}

		@Override
		public void enterRestParameterInObject(RestParameterInObjectContext ctx) {
			// still unsupported
		}

	}
}
