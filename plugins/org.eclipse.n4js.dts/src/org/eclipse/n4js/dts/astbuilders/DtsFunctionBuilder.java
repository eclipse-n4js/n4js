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
import org.eclipse.n4js.dts.TypeScriptParser.FunctionDeclarationContext;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class DtsFunctionBuilder extends AbstractDtsBuilderWithHelpers<FunctionDeclarationContext, FunctionDeclaration> {

	/** Constructor */
	public DtsFunctionBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return java.util.Set.of(
				RULE_typeParameters,
				RULE_typeParameterList);
	}

	@Override
	public void enterFunctionDeclaration(FunctionDeclarationContext ctx) {
		result = N4JSFactory.eINSTANCE.createFunctionDeclaration();
		result.setName(ctx.identifierName().getText());
		result.getDeclaredModifiers().add(N4Modifier.EXTERNAL);

		result.setGenerator(ctx.Multiply() != null);
		TypeRef typeRef = newTypeRefBuilder().consume(ctx.callSignature().typeRef());
		result.setDeclaredReturnTypeRefNode(ParserContextUtil.wrapInTypeRefNode(orAnyPlus(typeRef)));
		List<N4TypeVariable> typeVars = newN4TypeVariablesBuilder().consume(ctx.callSignature().typeParameters());
		result.getTypeVars().addAll(typeVars);
		List<FormalParameter> fPars = newFormalParametersBuilder().consume(ctx.callSignature().parameterBlock());
		result.getFpars().addAll(fPars);
	}

}
