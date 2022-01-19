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

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_typeBody;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_typeMember;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_typeMemberList;

import java.util.List;
import java.util.Set;

import org.eclipse.n4js.dts.TypeScriptParser.InterfaceDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.InterfaceExtendsClauseContext;
import org.eclipse.n4js.dts.TypeScriptParser.ParameterizedTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertySignaturContext;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class DtsInterfaceBuilder extends AbstractDtsSubBuilder<InterfaceDeclarationContext, N4InterfaceDeclaration> {
	private final DtsTypeRefBuilder typeRefBuilder = new DtsTypeRefBuilder(resource);
	private final DtsTypeVariablesBuilder typeVariablesBuilder = new DtsTypeVariablesBuilder(resource);

	/** Constructor */
	public DtsInterfaceBuilder(LazyLinkingResource resource) {
		super(resource);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return java.util.Set.of(
				RULE_typeBody,
				RULE_typeMemberList,
				RULE_typeMember);
	}

	@Override
	public void enterInterfaceDeclaration(InterfaceDeclarationContext ctx) {
		result = N4JSFactory.eINSTANCE.createN4InterfaceDeclaration();
		result.setName(ctx.identifierName().getText());
		result.getDeclaredModifiers().add(N4Modifier.EXTERNAL);

		List<N4TypeVariable> typeVars = typeVariablesBuilder.consume(ctx.typeParameters());
		result.getTypeVars().addAll(typeVars);

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
			result.getSuperInterfaceRefs().add(typeRefNode);
		}

		walker.enqueue(ctx.typeBody());
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

		result.getOwnedMembersRaw().add(fd);
	}
}
