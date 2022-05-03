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

import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.dts.TypeScriptParser.ColonSepTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.IdentifierOrPatternContext;
import org.eclipse.n4js.dts.TypeScriptParser.InitializerContext;
import org.eclipse.n4js.dts.TypeScriptParser.OptionalParameterContext;
import org.eclipse.n4js.dts.TypeScriptParser.ParameterBlockContext;
import org.eclipse.n4js.dts.TypeScriptParser.RequiredParameterContext;
import org.eclipse.n4js.dts.TypeScriptParser.RestParameterContext;
import org.eclipse.n4js.dts.utils.ParserContextUtils;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TAnnotableElement;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TypesFactory;

/**
 * Abstract base class for builders creating formal parameters from parse tree elements.
 */
public abstract class AbstractDtsFormalParametersBuilder<T extends EObject, AE extends EObject>
		extends AbstractDtsBuilderWithHelpers<ParameterBlockContext, List<T>> {

	/**
	 * Builds {@link FormalParameter}s.
	 */
	public static final class DtsFormalParametersBuilder
			extends AbstractDtsFormalParametersBuilder<FormalParameter, AnnotableElement> {

		/** Constructor */
		public DtsFormalParametersBuilder(AbstractDtsBuilder<?, ?> parent) {
			super(parent);
		}

		@Override
		protected FormalParameter createFormalParameter(String name, TypeRef typeRef) {
			FormalParameter fPar = N4JSFactory.eINSTANCE.createFormalParameter();
			fPar.setName(name); // TODO: bindingPattern
			fPar.setDeclaredTypeRefNode(ParserContextUtils.wrapInTypeRefNode(orAnyPlus(typeRef)));
			return fPar;

		}

		@Override
		protected void setOptional(FormalParameter fPar, InitializerContext initCtx) {
			fPar.setHasInitializerAssignment(true);
			if (initCtx != null) {
				Expression expr = newExpressionBuilder().consume(initCtx.singleExpression());
				fPar.setInitializer(expr);
			}
		}

		@Override
		protected void setVariadic(FormalParameter fPar) {
			fPar.setVariadic(true);
		}

		@Override
		protected void setDeclThisType(AnnotableElement annotableElem, TypeRef declThisTypeRef) {
			ParserContextUtils.setDeclThisType(annotableElem, declThisTypeRef);
		}
	}

	/**
	 * Builds {@link TFormalParameter}s.
	 */
	public static final class DtsTFormalParametersBuilder
			extends AbstractDtsFormalParametersBuilder<TFormalParameter, TAnnotableElement> {

		/** Constructor */
		public DtsTFormalParametersBuilder(AbstractDtsBuilder<?, ?> parent) {
			super(parent);
		}

		@Override
		protected TFormalParameter createFormalParameter(String name, TypeRef typeRef) {
			TFormalParameter fPar = TypesFactory.eINSTANCE.createTFormalParameter();
			fPar.setName(name);
			fPar.setTypeRef(orAnyPlus(typeRef));
			return fPar;

		}

		@Override
		protected void setOptional(TFormalParameter fPar, InitializerContext initCtx) {
			fPar.setHasInitializerAssignment(true);
		}

		@Override
		protected void setVariadic(TFormalParameter fPar) {
			fPar.setVariadic(true);
		}

		@Override
		protected void setDeclThisType(TAnnotableElement annotableElem, TypeRef declThisTypeRef) {
			ParserContextUtils.setDeclThisType(annotableElem, declThisTypeRef);
		}
	}

	/** Constructor */
	protected AbstractDtsFormalParametersBuilder(AbstractDtsBuilder<?, ?> parent) {
		super(parent);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return Set.of(
				RULE_parameterBlock,
				RULE_parameterListTrailingComma,
				RULE_parameterList,
				RULE_parameter);
	}

	/**
	 * Same as {@link #consume(ParserRuleContext)}, but also adds an {@code @This()} annotation to 'elem' with the
	 * declared this type, iff a "this" parameter is provided in 'ctx'.
	 */
	public List<T> consumeWithDeclThisType(ParameterBlockContext ctx, AE elem) {
		if (ctx.This() != null && ctx.colonSepTypeRef() != null) {
			TypeRef declThisTypeRef = newTypeRefBuilder().consume(ctx.colonSepTypeRef());
			if (declThisTypeRef != null) {
				setDeclThisType(elem, declThisTypeRef);
			}
		}
		return consume(ctx);
	}

	/**
	 * Same as {@link #consume(ParserRuleContext)}, but also sets the declared this type in the given function type
	 * expression, iff a "this" parameter is provided in 'ctx'.
	 */
	public List<T> consumeWithDeclThisType(ParameterBlockContext ctx, FunctionTypeExpression fte) {
		if (ctx.This() != null && ctx.colonSepTypeRef() != null) {
			TypeRef declThisTypeRef = newTypeRefBuilder().consume(ctx.colonSepTypeRef());
			if (declThisTypeRef != null) {
				fte.setDeclaredThisType(declThisTypeRef);
			}
		}
		return consume(ctx);

	}

	@Override
	protected List<T> getDefaultResult() {
		return Collections.emptyList();
	}

	@Override
	public void enterParameterBlock(ParameterBlockContext ctx) {
		result = new ArrayList<>();
	}

	@Override
	public void enterRequiredParameter(RequiredParameterContext ctx) {
		buildBaseFormalParameter(ctx.identifierOrPattern(), ctx.colonSepTypeRef());
	}

	@Override
	public void enterOptionalParameter(OptionalParameterContext ctx) {
		T fPar = buildBaseFormalParameter(ctx.identifierOrPattern(), ctx.colonSepTypeRef());

		if (fPar != null) {
			setOptional(fPar, ctx.initializer());
		}
	}

	@Override
	public void enterRestParameter(RestParameterContext ctx) {
		T fPar = buildBaseFormalParameter(ctx.identifierOrPattern(), ctx.colonSepTypeRef());

		if (fPar != null) {
			setVariadic(fPar);
		}
	}

	private T buildBaseFormalParameter(IdentifierOrPatternContext iop, ColonSepTypeRefContext cstr) {
		if (iop.identifierName() == null) {
			return null;
		}
		String name = iop.identifierName().getText();
		T fPar = createFormalParameter(name, newTypeRefBuilder().consume(cstr));
		result.add(fPar);
		return fPar;
	}

	/** Both arguments may be <code>null</code>. */
	protected abstract T createFormalParameter(String name, TypeRef typeRef);

	/** Initializer may be <code>null</code>. */
	protected abstract void setOptional(T fPar, InitializerContext initCtx);

	/** Argument will never be <code>null</code>. */
	protected abstract void setVariadic(T fPar);

	/** Arguments will never be <code>null</code>. */
	protected abstract void setDeclThisType(AE annotableElem, TypeRef declThisTypeRef);
}
