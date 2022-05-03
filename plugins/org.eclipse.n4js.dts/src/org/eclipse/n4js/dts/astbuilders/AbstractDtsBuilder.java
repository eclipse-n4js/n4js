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

import java.util.Collections;
import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.dts.DtsParseTreeNodeInfo;
import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.ManualParseTreeWalker;
import org.eclipse.n4js.dts.NestedResourceAdapter;
import org.eclipse.n4js.dts.TypeScriptParserBaseListener;
import org.eclipse.n4js.dts.astbuilders.AbstractDtsFormalParametersBuilder.DtsFormalParametersBuilder;
import org.eclipse.n4js.dts.astbuilders.AbstractDtsFormalParametersBuilder.DtsTFormalParametersBuilder;
import org.eclipse.n4js.dts.astbuilders.AbstractDtsNamespaceBuilder.DtsGlobalScopeAugmentationBuilder;
import org.eclipse.n4js.dts.astbuilders.AbstractDtsNamespaceBuilder.DtsModuleBuilder;
import org.eclipse.n4js.dts.astbuilders.AbstractDtsNamespaceBuilder.DtsNamespaceBuilder;
import org.eclipse.n4js.dts.astbuilders.AbstractDtsTypeVariablesBuilder.DtsN4TypeVariablesBuilder;
import org.eclipse.n4js.dts.astbuilders.AbstractDtsTypeVariablesBuilder.DtsTypeVariablesBuilder;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class AbstractDtsBuilder<T extends ParserRuleContext, R>
		extends TypeScriptParserBaseListener {

	/** Parent builder or <code>null</code>. */
	protected final AbstractDtsBuilder<?, ?> parent;
	/** Token stream for access to other lexer channels */
	protected final DtsTokenStream tokenStream;
	/** Current resource */
	protected final LazyLinkingResource resource;
	/** Rule IDs of parser rules to visit as default */
	protected final Set<Integer> visitChildrenRuleIDs = getVisitChildrenOfRules();

	/** walker */
	protected ManualParseTreeWalker walker;
	/** result value of {@link #consume(ParserRuleContext)} */
	protected R result = getDefaultResult();

	/** Creates a new child builder for the given parent. */
	public AbstractDtsBuilder(AbstractDtsBuilder<?, ?> parent) {
		this(parent, parent.tokenStream, parent.resource);
	}

	/** Creates a new root builder without parent. */
	public AbstractDtsBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		this(null, tokenStream, resource);
	}

	private AbstractDtsBuilder(AbstractDtsBuilder<?, ?> parent, DtsTokenStream tokenStream,
			LazyLinkingResource resource) {
		this.parent = parent;
		this.tokenStream = tokenStream;
		this.resource = resource;
	}

	/** @return the root {@link DtsScriptBuilder} of this builder hierarchy. */
	protected DtsScriptBuilder getScriptBuilder() {
		AbstractDtsBuilder<?, ?> curr = this;
		while (curr.parent != null) {
			curr = curr.parent;
		}
		return (DtsScriptBuilder) curr;
	}

	/** Defines the subclass' rule ids of parser rules to visit as default */
	protected Set<Integer> getVisitChildrenOfRules() {
		return Collections.emptySet();
	}

	/** Defines the subclass' default result */
	protected R getDefaultResult() {
		return null;
	}

	/** Consumes the given context and all its children. */
	public R consume(T ctx) {
		return doConsume(ctx);
	}

	/**
	 * Actually consumes the given context. Subclasses may provide more consume methods, in addition to the default
	 * method {@code #consume(T)}, by delegating here.
	 */
	protected R doConsume(ParserRuleContext ctx) {
		if (ctx == null) {
			return result;
		}

		walker = new ManualParseTreeWalker(this, ctx);
		walker.start(); // eventually this call causes 'result' to be set
		walker = null; // reset for fail fast

		if (result instanceof EObject) {
			addLocationInfo((EObject) result, ctx);
		}

		try {
			return result;
		} finally {
			// reset for fail fast
			resetResult();
		}
	}

	/** Clear the result and set it back to the {@link #getDefaultResult() default}. */
	protected void resetResult() {
		result = getDefaultResult();
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		if (visitChildrenRuleIDs.contains(ctx.getRuleIndex()) && ctx.children != null) {
			for (ParseTree pt : ctx.children) {
				if (pt instanceof RuleNode) {
					RuleNode rn = (RuleNode) pt;
					walker.enqueue((ParserRuleContext) rn.getRuleContext());
				}
			}
		}
	}

	/**  */
	protected void addLocationInfo(EObject obj, ParserRuleContext ctx) {
		if (obj == null) {
			return;
		}
		for (Adapter adapter : obj.eAdapters()) {
			if (adapter instanceof DtsParseTreeNodeInfo) {
				return;
			}
		}
		obj.eAdapters().add(new DtsParseTreeNodeInfo(tokenStream, ctx));
	}

	/** Returns true iff this resource is nested/virtual */
	protected boolean isNested() {
		return NestedResourceAdapter.isInstalled(resource);
	}

	/***/
	protected final DtsImportBuilder newImportBuilder() {
		return new DtsImportBuilder(this);
	}

	/***/
	protected final DtsExportBuilder newExportBuilder() {
		return new DtsExportBuilder(this);
	}

	/***/
	protected final DtsClassBuilder newClassBuilder() {
		return new DtsClassBuilder(this);
	}

	/***/
	protected final DtsInterfaceBuilder newInterfaceBuilder() {
		return new DtsInterfaceBuilder(this);
	}

	/***/
	protected final DtsEnumBuilder newEnumBuilder() {
		return new DtsEnumBuilder(this);
	}

	/***/
	protected final DtsNamespaceBuilder newNamespaceBuilder() {
		return new DtsNamespaceBuilder(this);
	}

	/***/
	protected final DtsModuleBuilder newModuleBuilder() {
		return new DtsModuleBuilder(this);
	}

	/***/
	protected final DtsGlobalScopeAugmentationBuilder newGlobalScopeAugmentationBuilder() {
		return new DtsGlobalScopeAugmentationBuilder(this);
	}

	/***/
	protected final DtsTypeAliasBuilder newTypeAliasBuilder() {
		return new DtsTypeAliasBuilder(this);
	}

	/***/
	protected final DtsFunctionBuilder newFunctionBuilder() {
		return new DtsFunctionBuilder(this);
	}

	/***/
	protected final DtsVariableBuilder newVariableBuilder() {
		return new DtsVariableBuilder(this);
	}

	/***/
	protected final DtsTypeVariablesBuilder newTypeVariablesBuilder() {
		return new DtsTypeVariablesBuilder(this);
	}

	/***/
	protected final DtsN4TypeVariablesBuilder newN4TypeVariablesBuilder() {
		return new DtsN4TypeVariablesBuilder(this);
	}

	/***/
	protected final DtsFormalParametersBuilder newFormalParametersBuilder() {
		return new DtsFormalParametersBuilder(this);
	}

	/***/
	protected final DtsTFormalParametersBuilder newTFormalParametersBuilder() {
		return new DtsTFormalParametersBuilder(this);
	}

	/***/
	protected final DtsPropertyNameBuilder newPropertyNameBuilder() {
		return new DtsPropertyNameBuilder(this);
	}

	/***/
	protected final DtsBindingPatternBuilder newBindingPatternBuilder() {
		return new DtsBindingPatternBuilder(this);
	}

	/***/
	protected final DtsExpressionBuilder newExpressionBuilder() {
		return new DtsExpressionBuilder(this);
	}

	/***/
	protected final DtsTypeRefBuilder newTypeRefBuilder() {
		return new DtsTypeRefBuilder(this);
	}

	/***/
	protected final DtsTStructBodyBuilder newTStructBodyBuilder() {
		return new DtsTStructBodyBuilder(this);
	}

}
