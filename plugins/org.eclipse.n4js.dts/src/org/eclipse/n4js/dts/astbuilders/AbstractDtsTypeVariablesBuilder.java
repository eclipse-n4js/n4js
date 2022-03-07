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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.TypeScriptParser.TypeParameterContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeParametersContext;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Abstract base class for builders creating type variables from parse tree elements.
 */
public abstract class AbstractDtsTypeVariablesBuilder<T extends EObject>
		extends AbstractDtsBuilderWithHelpers<TypeParametersContext, List<T>> {

	private final DtsTypeRefBuilder typeRefBuilder = new DtsTypeRefBuilder(tokenStream, resource);

	/**
	 * Builds {@link N4TypeVariable}s.
	 */
	public static final class DtsN4TypeVariablesBuilder extends AbstractDtsTypeVariablesBuilder<N4TypeVariable> {

		/** Constructor */
		public DtsN4TypeVariablesBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
			super(tokenStream, resource);
		}

		@Override
		protected N4TypeVariable createTypeVariable(String name) {
			N4TypeVariable typeVar = N4JSFactory.eINSTANCE.createN4TypeVariable();
			typeVar.setName(name);
			return typeVar;
		}

		@Override
		protected void setUpperBound(N4TypeVariable typeVar, TypeRef upperBound) {
			typeVar.setDeclaredUpperBoundNode(ParserContextUtil.wrapInTypeRefNode(upperBound));
		}

		@Override
		protected void setDefaultArgument(N4TypeVariable typeVar, TypeRef defaultArg) {
			typeVar.setDeclaredOptional(true);
			typeVar.setDeclaredDefaultArgumentNode(ParserContextUtil.wrapInTypeRefNode(defaultArg));
		}
	}

	/**
	 * Builds {@link TypeVariable}s.
	 */
	public static final class DtsTypeVariablesBuilder extends AbstractDtsTypeVariablesBuilder<TypeVariable> {

		/** Constructor */
		public DtsTypeVariablesBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
			super(tokenStream, resource);
		}

		@Override
		protected TypeVariable createTypeVariable(String name) {
			TypeVariable typeVar = TypesFactory.eINSTANCE.createTypeVariable();
			typeVar.setName(name);
			return typeVar;
		}

		@Override
		protected void setUpperBound(TypeVariable typeVar, TypeRef upperBound) {
			typeVar.setDeclaredUpperBound(upperBound);
		}

		@Override
		protected void setDefaultArgument(TypeVariable typeVar, TypeRef defaultArg) {
			typeVar.setDefaultArgument(defaultArg);
		}
	}

	/** Constructor */
	public AbstractDtsTypeVariablesBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return java.util.Set.of(
				RULE_typeParameters,
				RULE_typeParameterList);
	}

	@Override
	protected List<T> getDefaultResult() {
		return Collections.emptyList();
	}

	@Override
	public void enterTypeParameters(TypeParametersContext ctx) {
		result = new ArrayList<>();
	}

	@Override
	public void enterTypeParameter(TypeParameterContext ctx) {
		T typeVar = createTypeVariable(ctx.identifierName().getText());
		if (ctx.constraint() != null && ctx.constraint().typeRef() != null) {
			TypeRef typeRef = typeRefBuilder.consume(ctx.constraint().typeRef());
			if (typeRef != null) {
				setUpperBound(typeVar, typeRef);
			}
		}
		if (ctx.defaultType() != null && ctx.defaultType().typeRef() != null) {
			TypeRef typeRef = typeRefBuilder.consume(ctx.defaultType().typeRef());
			setDefaultArgument(typeVar, typeRef);
		}
		result.add(typeVar);
	}

	/** The argument may be <code>null</code>. */
	protected abstract T createTypeVariable(String name);

	/** Arguments will never be <code>null</code>. */
	protected abstract void setUpperBound(T typeVar, TypeRef upperBound);

	/** Arguments will never be <code>null</code>. */
	protected abstract void setDefaultArgument(T typeVar, TypeRef defaultArg);
}
