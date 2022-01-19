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

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_arrayTypeExpression;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_conditionalTypeRef;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_intersectionTypeExpression;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_operatorTypeRef;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_primaryTypeExpression;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_typeRef;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_typeRefWithModifiers;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_unionTypeExpression;

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.n4js.dts.TypeScriptParser.ColonSepTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.ParameterizedTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeRefContext;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.xtext.XtextFactory;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.nodemodel.BidiTreeIterable;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.SyntaxErrorMessage;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.ITextRegionWithLineInformation;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class DtsTypeRefBuilder extends AbstractDtsSubBuilder<TypeRefContext, TypeReferenceNode<TypeRef>> {

	/** Constructor */
	public DtsTypeRefBuilder(LazyLinkingResource resource) {
		super(resource);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return java.util.Set.of(
				RULE_typeRef,
				RULE_conditionalTypeRef,
				RULE_unionTypeExpression,
				RULE_intersectionTypeExpression,
				RULE_operatorTypeRef,
				RULE_arrayTypeExpression,
				RULE_primaryTypeExpression,
				RULE_typeRefWithModifiers);
	}

	/** @return a {@link TypeReferenceNode} from the given context. Consumes the given context and all its children. */
	public TypeReferenceNode<TypeRef> consume(ColonSepTypeRefContext ctx) {
		if (ctx == null) {
			return null;
		}
		return consume(ctx.typeRef());
	}

	@Override
	public void enterParameterizedTypeRef(ParameterizedTypeRefContext ctx) {
		ParameterizedTypeRef pTypeRef = TypeRefsFactory.eINSTANCE.createParameterizedTypeRef();
		String text = ctx.typeName().getText();
		pTypeRef.setDeclaredTypeAsText(text);
		pTypeRef.setDeclaredType(null);
		if (!isPrimitive(pTypeRef)) {
			pTypeRef.setDefinedTypingStrategy(TypingStrategy.STRUCTURAL);
		}

		Type typeProxy = TypesFactory.eINSTANCE.createType();
		EReference eRef = TypeRefsPackage.eINSTANCE.getParameterizedTypeRef_DeclaredType();
		int fragmentNumber = resource.addLazyProxyInformation(pTypeRef, eRef, new PseudoLeafNode(text));
		URI encodedLink = resource.getURI().appendFragment("|" + fragmentNumber);
		((InternalEObject) typeProxy).eSetProxyURI(encodedLink);
		pTypeRef.setDeclaredType(typeProxy);

		result = N4JSFactory.eINSTANCE.createTypeReferenceNode();
		result.setTypeRefInAST(pTypeRef);
	}

	private boolean isPrimitive(ParameterizedTypeRef pTypeRef) {
		switch (pTypeRef.getDeclaredTypeAsText()) {
		case "number":
		case "string":
			return true;
		default:
			return false;
		}
	}

	static class PseudoLeafNode implements ILeafNode {
		final String text;

		PseudoLeafNode(String text) {
			this.text = text;
		}

		@Override
		public String getText() {
			return text;
		}

		// Note that all methods below are irrelevant since they won't be used later on instances of this class

		@Override
		public ICompositeNode getParent() {
			return null;
		}

		@Override
		public boolean hasSiblings() {
			return false;
		}

		@Override
		public boolean hasPreviousSibling() {
			return false;
		}

		@Override
		public boolean hasNextSibling() {
			return false;
		}

		@Override
		public INode getPreviousSibling() {
			return null;
		}

		@Override
		public INode getNextSibling() {
			return null;
		}

		@Override
		public ICompositeNode getRootNode() {
			return null;
		}

		@Override
		public Iterable<ILeafNode> getLeafNodes() {
			return null;
		}

		@Override
		public int getTotalOffset() {
			return 0;
		}

		@Override
		public int getOffset() {
			return 0;
		}

		@Override
		public int getTotalLength() {
			return 0;
		}

		@Override
		public int getLength() {
			return 0;
		}

		@Override
		public int getTotalEndOffset() {
			return 0;
		}

		@Override
		public int getEndOffset() {
			return 0;
		}

		@Override
		public int getTotalStartLine() {
			return 0;
		}

		@Override
		public int getStartLine() {
			return 0;
		}

		@Override
		public int getTotalEndLine() {
			return 0;
		}

		@Override
		public int getEndLine() {
			return 0;
		}

		@Override
		public EObject getGrammarElement() {
			return XtextFactory.eINSTANCE.createKeyword();
		}

		@Override
		public EObject getSemanticElement() {
			return null;
		}

		@Override
		public boolean hasDirectSemanticElement() {
			return false;
		}

		@Override
		public SyntaxErrorMessage getSyntaxErrorMessage() {
			return null;
		}

		@Override
		public BidiTreeIterable<INode> getAsTreeIterable() {
			return null;
		}

		@Override
		public ITextRegion getTextRegion() {
			return null;
		}

		@Override
		public ITextRegion getTotalTextRegion() {
			return null;
		}

		@Override
		public ITextRegionWithLineInformation getTextRegionWithLineInformation() {
			return null;
		}

		@Override
		public ITextRegionWithLineInformation getTotalTextRegionWithLineInformation() {
			return null;
		}

		@Override
		public boolean isHidden() {
			return false;
		}

	}
}
