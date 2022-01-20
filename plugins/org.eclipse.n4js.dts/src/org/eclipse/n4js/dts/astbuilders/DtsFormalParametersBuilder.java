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

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_parameter;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_parameterBlock;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_parameterList;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_parameterListTrailingComma;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.TypeScriptParser.ColonSepTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.IdentifierOrPatternContext;
import org.eclipse.n4js.dts.TypeScriptParser.OptionalParameterContext;
import org.eclipse.n4js.dts.TypeScriptParser.ParameterBlockContext;
import org.eclipse.n4js.dts.TypeScriptParser.RequiredParameterContext;
import org.eclipse.n4js.dts.TypeScriptParser.RestParameterContext;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class DtsFormalParametersBuilder
		extends AbstractDtsSubBuilder<ParameterBlockContext, List<FormalParameter>> {

	private final DtsTypeRefBuilder typeRefBuilder = new DtsTypeRefBuilder(tokenStream, resource);
	private final DtsExpressionBuilder expressionBuilder = new DtsExpressionBuilder(tokenStream, resource);

	/** Constructor */
	public DtsFormalParametersBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return Set.of(
				RULE_parameterBlock,
				RULE_parameterListTrailingComma,
				RULE_parameterList,
				RULE_parameter);
	}

	@Override
	protected List<FormalParameter> getDefaultResult() {
		return Collections.emptyList();
	}

	@Override
	public void enterRequiredParameter(RequiredParameterContext ctx) {
		result = new ArrayList<>();
		buildBaseFormalParameter(ctx.identifierOrPattern(), ctx.colonSepTypeRef());
	}

	@Override
	public void enterOptionalParameter(OptionalParameterContext ctx) {
		FormalParameter fPar = buildBaseFormalParameter(ctx.identifierOrPattern(), ctx.colonSepTypeRef());

		fPar.setHasInitializerAssignment(true);
		Expression expr = expressionBuilder.consume(ctx.initializer().singleExpression());
		fPar.setInitializer(expr);
	}

	@Override
	public void enterRestParameter(RestParameterContext ctx) {
		FormalParameter fPar = buildBaseFormalParameter(ctx.identifierOrPattern(), ctx.colonSepTypeRef());

		fPar.setVariadic(true);
	}

	private FormalParameter buildBaseFormalParameter(IdentifierOrPatternContext iop, ColonSepTypeRefContext cstr) {
		FormalParameter fPar = N4JSFactory.eINSTANCE.createFormalParameter();
		fPar.setName(iop.identifierName().getText()); // TODO: bindingPattern
		TypeReferenceNode<TypeRef> trn = typeRefBuilder.consume(cstr);
		fPar.setDeclaredTypeRefNode(trn);
		result.add(fPar);
		return fPar;
	}
}
