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
package org.eclipse.n4js.dts;

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_classElement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_classElementList;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_declarationStatement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_declareStatement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_exportStatement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_exportStatementTail;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_statement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_statementList;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_typeMember;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_typeMemberList;

import java.util.Set;
import java.util.Stack;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.eclipse.n4js.dts.TypeScriptParser.ClassDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.InterfaceDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.InterfaceExtendsClauseContext;
import org.eclipse.n4js.dts.TypeScriptParser.NamespaceDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.ParameterizedTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.ProgramContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyMemberDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertySignaturContext;
import org.eclipse.n4js.dts.TypeScriptParser.VariableStatementContext;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.NamespaceElement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;

/**
 * Builder to create {@link Script} elements and all its children from d.ts parse tree elements
 */
public class DtsAstBuilder extends TypeScriptParserBaseListener {
	final static Set<Integer> VISIT_CHILDREN_OF_RULES = java.util.Set.of(
			RULE_statement,
			RULE_statementList,
			RULE_declareStatement,
			RULE_declarationStatement,
			RULE_exportStatement,
			RULE_exportStatementTail,
			RULE_classElementList,
			RULE_classElement,
			RULE_typeMember,
			RULE_typeMemberList);

	private final ManualParseTreeWalker walker;
	private final DtsTypeRefBuilder typeRefBuilder;
	private final Stack<N4NamespaceDeclaration> currentNamespace = new Stack<>();

	private Script script = null;
	private N4ClassifierDefinition currentClassifierDefinition = null;

	/** Constructor */
	public DtsAstBuilder(ManualParseTreeWalker walker) {
		this.walker = walker;
		this.typeRefBuilder = new DtsTypeRefBuilder();
		walker.setParseTreeListener(this);
	}

	/** @return the script that was created during visiting the parse tree */
	public Script getScript() {
		return script;
	}

	void addToScriptOrNamespace(NamespaceElement elem) {
		if (currentNamespace.empty()) {
			script.getScriptElements().add(elem);
		} else {
			currentNamespace.peek().getOwnedElementsRaw().add(elem);
		}
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		if (VISIT_CHILDREN_OF_RULES.contains(ctx.getRuleIndex())) {
			for (ParseTree pt : ctx.children) {
				if (pt instanceof RuleNode) {
					RuleNode rn = (RuleNode) pt;
					walker.enqueue((ParserRuleContext) rn.getRuleContext());
				}
			}
		}
	}

	@Override
	public void enterProgram(ProgramContext ctx) {
		script = N4JSFactory.eINSTANCE.createScript();
		if (ctx.statementList() != null) {
			walker.enqueue(ctx.statementList().statement());
		}
	}

	@Override
	public void enterNamespaceDeclaration(NamespaceDeclarationContext ctx) {
		N4NamespaceDeclaration nd = N4JSFactory.eINSTANCE.createN4NamespaceDeclaration();
		nd.setName(ctx.namespaceName().getText());

		boolean isExported = ParserContextUtil.isExported(ctx);
		if (isExported) {
			nd.getDeclaredModifiers().add(N4Modifier.PUBLIC);
		}
		if (isExported && currentNamespace.empty()) {
			ExportDeclaration ed = N4JSFactory.eINSTANCE.createExportDeclaration();
			ed.setExportedElement(nd);
			addToScriptOrNamespace(ed);
		} else {
			addToScriptOrNamespace(nd);
		}

		currentNamespace.push(nd);

		walker.enqueue(ctx.block().statementList());
	}

	@Override
	public void exitNamespaceDeclaration(NamespaceDeclarationContext ctx) {
		currentNamespace.pop();
	}

	@Override
	public void enterVariableStatement(VariableStatementContext ctx) {
	}

	@Override
	public void exitVariableStatement(VariableStatementContext ctx) {
	}

	@Override
	public void enterInterfaceDeclaration(InterfaceDeclarationContext ctx) {
		N4InterfaceDeclaration id = N4JSFactory.eINSTANCE.createN4InterfaceDeclaration();
		id.setName(ctx.identifierName().getText());
		id.getDeclaredModifiers().add(N4Modifier.EXTERNAL);
		boolean isExported = ParserContextUtil.isExported(ctx);
		if (isExported) {
			id.getDeclaredModifiers().add(N4Modifier.PUBLIC);
		}
		if (isExported && currentNamespace.empty()) {
			ExportDeclaration ed = N4JSFactory.eINSTANCE.createExportDeclaration();
			ed.setExportedElement(id);
			addToScriptOrNamespace(ed);
		} else {
			addToScriptOrNamespace(id);
		}
		currentClassifierDefinition = id;

		InterfaceExtendsClauseContext extendsClause = ctx.interfaceExtendsClause();
		if (extendsClause != null && extendsClause.classOrInterfaceTypeList() != null
				&& extendsClause.classOrInterfaceTypeList().parameterizedTypeRef() != null
				&& !extendsClause.classOrInterfaceTypeList().parameterizedTypeRef().isEmpty()) {

			ParameterizedTypeRefContext extendsTypeRefCtx = extendsClause.classOrInterfaceTypeList()
					.parameterizedTypeRef().get(0);

			ParameterizedTypeRef pTypeRef = TypeRefsFactory.eINSTANCE.createParameterizedTypeRef();
			pTypeRef.setDeclaredTypeAsText(extendsTypeRefCtx.typeName().getText());
			TypeReferenceNode<ParameterizedTypeRef> typeRefNode = N4JSFactory.eINSTANCE.createTypeReferenceNode();
			typeRefNode.setTypeRefInAST(pTypeRef);
			id.getSuperInterfaceRefs().add(typeRefNode);
		}

		walker.enqueue(ctx.typeBody().typeMemberList());
	}

	@Override
	public void exitInterfaceDeclaration(InterfaceDeclarationContext ctx) {
		currentClassifierDefinition = null;
	}

	@Override
	public void enterClassDeclaration(ClassDeclarationContext ctx) {
		N4ClassDeclaration cd = N4JSFactory.eINSTANCE.createN4ClassDeclaration();
		cd.setName(ctx.identifierOrKeyWord().getText());
		cd.getDeclaredModifiers().add(N4Modifier.EXTERNAL);
		boolean isExported = ParserContextUtil.isExported(ctx);
		if (isExported) {
			cd.getDeclaredModifiers().add(N4Modifier.PUBLIC);
		}
		if (isExported && currentNamespace.empty()) {
			ExportDeclaration ed = N4JSFactory.eINSTANCE.createExportDeclaration();
			ed.setExportedElement(cd);
			addToScriptOrNamespace(ed);
		} else {
			addToScriptOrNamespace(cd);
		}
		currentClassifierDefinition = cd;

		walker.enqueue(ctx.classTail().classElementList());
	}

	@Override
	public void exitClassDeclaration(ClassDeclarationContext ctx) {
		currentClassifierDefinition = null;
	}

	@Override
	public void enterPropertySignatur(PropertySignaturContext ctx) {
		// this is a property
		N4FieldDeclaration fd = N4JSFactory.eINSTANCE.createN4FieldDeclaration();
		LiteralOrComputedPropertyName locpn = N4JSFactory.eINSTANCE.createLiteralOrComputedPropertyName();
		locpn.setLiteralName(ctx.propertyName().getText());
		fd.setDeclaredName(locpn);
		fd.setDeclaredOptional(ctx.QuestionMark() != null);

		TypeReferenceNode<TypeRef> trn = typeRefBuilder.consume(ctx.colonSepTypeRef());
		fd.setDeclaredTypeRefNode(trn);

		currentClassifierDefinition.getOwnedMembersRaw().add(fd);
	}

	@Override
	public void exitPropertySignatur(PropertySignaturContext ctx) {
	}

	@Override
	public void enterPropertyMemberDeclaration(PropertyMemberDeclarationContext ctx) {
		if (ctx.propertyMemberBase() != null && ctx.propertyName() != null) {
			// this is a property
			N4FieldDeclaration fd = N4JSFactory.eINSTANCE.createN4FieldDeclaration();
			LiteralOrComputedPropertyName locpn = N4JSFactory.eINSTANCE.createLiteralOrComputedPropertyName();
			locpn.setLiteralName(ctx.propertyName().getText());
			fd.setDeclaredName(locpn);
			fd.setDeclaredOptional(ctx.QuestionMark() != null);
			currentClassifierDefinition.getOwnedMembersRaw().add(fd);
		}
	}

	@Override
	public void exitPropertyMemberDeclaration(PropertyMemberDeclarationContext ctx) {
	}

}
