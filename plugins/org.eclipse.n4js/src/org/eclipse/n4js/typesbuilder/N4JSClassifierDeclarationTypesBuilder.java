/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.typesbuilder;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filterNull;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.compileTime.CompileTimeEvaluator;
import org.eclipse.n4js.compileTime.CompileTimeValue;
import org.eclipse.n4js.compileTime.CompileTimeValue.ValueValid;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.utils.N4JSLanguageUtils;

import com.google.inject.Inject;

/**
 * Abstract base class for N4JSClassDeclarationTypesBuilder and N4JSInterfaceDeclarationTypesBuilder to provide reusable
 * bits and pieces.
 */
abstract class N4JSClassifierDeclarationTypesBuilder {

	@Inject
	protected N4JSTypesBuilderHelper _n4JSTypesBuilderHelper;
	@Inject
	protected N4JSTypeVariableTypesBuilder _n4JSTypeVariableTypesBuilder;
	@Inject
	protected N4JSFieldTypesBuilder _n4JSFieldTypesBuilder;
	@Inject
	protected N4JSMethodTypesBuilder _n4JSMethodTypesBuilder;
	@Inject
	protected N4JSGetterTypesBuilder _n4JSGetterTypesBuilder;
	@Inject
	protected N4JSSetterTypesBuilder _n4JSSetterTypesBuilder;
	@Inject
	protected CompileTimeEvaluator compileTimeEvaluator;

	protected void addFields(TClassifier classifier, N4ClassifierDefinition definition, boolean preLinkingPhase) {
		Iterable<N4FieldDeclaration> n4Fields = filter(definition.getOwnedMembers(), N4FieldDeclaration.class);
		List<TField> fields = toList(
				filterNull(map(n4Fields, f -> _n4JSFieldTypesBuilder.createField(f, classifier, preLinkingPhase))));
		classifier.getOwnedMembers().addAll(fields);
	}

	protected void addMethods(TClassifier classifier, N4ClassifierDefinition definition, AbstractNamespace target,
			boolean preLinkingPhase) {
		// note: won't include call/construct signatures
		Iterable<N4MethodDeclaration> n4Methods = filter(definition.getOwnedMembers(), N4MethodDeclaration.class);
		List<TMethod> methods = toList(
				filterNull(map(n4Methods, m -> _n4JSMethodTypesBuilder.createMethod(m, target, preLinkingPhase))));
		classifier.getOwnedMembers().addAll(methods);

		N4MethodDeclaration cs = definition.getOwnedCallSignature();
		N4MethodDeclaration css = definition.getOwnedConstructSignature();
		classifier.setCallSignature(
				cs == null ? null : _n4JSMethodTypesBuilder.createMethod(cs, target, preLinkingPhase));
		classifier.setConstructSignature(
				css == null ? null : _n4JSMethodTypesBuilder.createMethod(css, target, preLinkingPhase));
	}

	protected void addGetters(TClassifier classifier, N4ClassifierDefinition definition, AbstractNamespace target,
			boolean preLinkingPhase) {
		// create also getters for all non private fields without explicit getter
		Iterable<N4GetterDeclaration> n4Getters = filter(definition.getOwnedMembers(), N4GetterDeclaration.class);
		List<TGetter> getters = toList(filterNull(
				map(n4Getters, g -> _n4JSGetterTypesBuilder.createGetter(g, classifier, target, preLinkingPhase))));
		classifier.getOwnedMembers().addAll(getters);
	}

	protected void addSetters(TClassifier classifier, N4ClassifierDefinition definition, AbstractNamespace target,
			boolean preLinkingPhase) {
		// create also getters for all non private fields without explicit getter
		Iterable<N4SetterDeclaration> n4Setters = filter(definition.getOwnedMembers(), N4SetterDeclaration.class);
		List<TSetter> setters = toList(filterNull(
				map(n4Setters, s -> _n4JSSetterTypesBuilder.createSetter(s, classifier, target, preLinkingPhase))));
		classifier.getOwnedMembers().addAll(setters);
	}

	void relinkClassifierAndMembers(TClassifier classifier, N4ClassifierDeclaration declaration,
			boolean preLinkingPhase) {
		_n4JSTypesBuilderHelper.ensureEqualName(declaration, classifier);

		// members
		N4MethodDeclaration astCallSig = declaration.getOwnedCallSignature();
		if (astCallSig != null) {
			_n4JSMethodTypesBuilder.relinkMethod(astCallSig, classifier.getCallSignature(), preLinkingPhase);
		}
		N4MethodDeclaration astConstructSig = declaration.getOwnedConstructSignature();
		if (astConstructSig != null) {
			_n4JSMethodTypesBuilder.relinkMethod(astConstructSig, classifier.getConstructSignature(), preLinkingPhase);
		}

		// OWNED members
		Map<String, N4MemberDeclaration> memberByName = new HashMap<>();
		for (N4MemberDeclaration member : declaration.getOwnedMembersRaw()) {
			PropertyNameOwner pno = (PropertyNameOwner) member;
			if (member.getName() != null) {
				memberByName.put(member.getName(), member);
			} else if (pno.hasComputedPropertyName()) {
				Expression expr = pno.getDeclaredName().getExpression();
				if (N4JSLanguageUtils.isProcessedAsCompileTimeExpression(expr)) {
					RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(pno);
					CompileTimeValue ctv = compileTimeEvaluator.evaluateCompileTimeExpression(G, expr);
					if (ctv.isValid()) {
						String ctvName = ((ValueValid<?>) ctv).getValue().toString();
						memberByName.put(ctvName, member);
					}
				}
			}
		}

		for (TMember tMember : classifier.getOwnedMembers()) {
			N4MemberDeclaration member = memberByName.get(tMember.getName());
			if (tMember instanceof TField && member instanceof N4FieldDeclaration) {
				_n4JSFieldTypesBuilder.relinkField((N4FieldDeclaration) member, (TField) tMember, preLinkingPhase);
			}
			if (tMember instanceof TMethod && member instanceof N4MethodDeclaration) {
				N4MethodDeclaration method = (N4MethodDeclaration) member;
				if (!method.isConstructSignature() && !method.isCallSignature()) {
					_n4JSMethodTypesBuilder.relinkMethod(method, (TMethod) tMember, preLinkingPhase);
				}
			}
			if (tMember instanceof TGetter && member instanceof N4GetterDeclaration) {
				_n4JSGetterTypesBuilder.relinkGetter((N4GetterDeclaration) member, (TGetter) tMember, preLinkingPhase);
			}
			if (tMember instanceof TSetter && member instanceof N4SetterDeclaration) {
				_n4JSSetterTypesBuilder.relinkSetter((N4SetterDeclaration) member, (TSetter) tMember, preLinkingPhase);
			}
		}

		// TODO proxy resolve vs setter invocation?
		classifier.setAstElement(declaration);
		// setter is ok here
		declaration.setDefinedType(classifier);
	}

}
