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

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_typeParameterList;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_typeParameters;

import java.util.List;
import java.util.Set;

import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.TypeScriptParser.TypeAliasDeclarationContext;
import org.eclipse.n4js.dts.astbuilders.AbstractDtsTypeVariablesBuilder.DtsN4TypeVariablesBuilder;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class DtsTypeAliasBuilder extends AbstractDtsBuilder<TypeAliasDeclarationContext, N4TypeAliasDeclaration> {
	private final DtsTypeRefBuilder typeRefBuilder = new DtsTypeRefBuilder(tokenStream, resource);
	private final DtsN4TypeVariablesBuilder typeVariablesBuilder = new DtsN4TypeVariablesBuilder(tokenStream, resource);

	/** Constructor */
	public DtsTypeAliasBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return java.util.Set.of(
				RULE_typeParameters,
				RULE_typeParameterList);
	}

	@Override
	public void enterTypeAliasDeclaration(TypeAliasDeclarationContext ctx) {
		result = N4JSFactory.eINSTANCE.createN4TypeAliasDeclaration();
		result.setName(ctx.identifierName().getText());
		result.getDeclaredModifiers().add(N4Modifier.EXTERNAL);

		TypeRef typeRef = typeRefBuilder.consume(ctx.typeRef());
		result.setDeclaredTypeRefNode(ParserContextUtil.wrapInTypeRefNode(typeRef));
		List<N4TypeVariable> typeVars = typeVariablesBuilder.consume(ctx.typeParameters());
		result.getTypeVars().addAll(typeVars);
	}

}
