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
package org.eclipse.n4js.typesbuilder

import com.google.inject.Inject
import java.util.HashMap
import java.util.Map
import org.eclipse.n4js.compileTime.CompileTimeEvaluator
import org.eclipse.n4js.compileTime.CompileTimeValue.ValueValid
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4ClassifierDefinition
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.n4JS.PropertyNameOwner
import org.eclipse.n4js.ts.types.AbstractNamespace
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.n4js.utils.N4JSLanguageUtils

/**
 * Abstract base class for N4JSClassDeclarationTypesBuilder and N4JSInterfaceDeclarationTypesBuilder
 * to provide reusable bits and pieces.
 */
package abstract class N4JSClassifierDeclarationTypesBuilder {
	
	@Inject protected extension N4JSTypesBuilderHelper
	@Inject protected extension N4JSTypeVariableTypesBuilder
	@Inject protected extension N4JSFieldTypesBuilder
	@Inject protected extension N4JSMethodTypesBuilder
	@Inject protected extension N4JSGetterTypesBuilder
	@Inject protected extension N4JSSetterTypesBuilder
	@Inject	protected CompileTimeEvaluator compileTimeEvaluator;

	def protected void addFields(TClassifier classifier, N4ClassifierDefinition definition, boolean preLinkingPhase) {
		val n4Fields = definition.ownedMembers.filter(N4FieldDeclaration);
		val fields = n4Fields.map[createField(classifier, preLinkingPhase)].filterNull
		classifier.ownedMembers.addAll(fields);
	}

	def protected void addMethods(TClassifier classifier, N4ClassifierDefinition definition, AbstractNamespace target, boolean preLinkingPhase) {
		val n4Methods = definition.ownedMembers.filter(N4MethodDeclaration); // note: won't include call/construct signatures
		val methods = n4Methods.map[createMethod(target, preLinkingPhase)].filterNull; 
		classifier.ownedMembers.addAll(methods);
		classifier.callSignature = definition.ownedCallSignature?.createMethod(target, preLinkingPhase);
		classifier.constructSignature = definition.ownedConstructSignature?.createMethod(target, preLinkingPhase);
	}

	def protected void addGetters(TClassifier classifier, N4ClassifierDefinition definition, AbstractNamespace target, boolean preLinkingPhase) {
		// create also getters for all non private fields without explicit getter
		val n4Getters = definition.ownedMembers.filter(N4GetterDeclaration)
		val getters = n4Getters.map[createGetter(classifier, target, preLinkingPhase)].filterNull
		classifier.ownedMembers.addAll(getters);
	}

	def protected void addSetters(TClassifier classifier, N4ClassifierDefinition definition, AbstractNamespace target, boolean preLinkingPhase) {
		// create also getters for all non private fields without explicit getter
		val n4Setters = definition.ownedMembers.filter(N4SetterDeclaration)
		val setters = n4Setters.map[createSetter(classifier, target, preLinkingPhase)].filterNull
		classifier.ownedMembers.addAll(setters);
	}

	def package void relinkClassifierAndMembers(TClassifier classifier, N4ClassifierDeclaration declaration, boolean preLinkingPhase) {
		ensureEqualName(declaration, classifier);

		// members
		val astCallSig = declaration.ownedCallSignature;
		if (astCallSig !== null ) {
			relinkMethod(astCallSig, classifier.callSignature, preLinkingPhase)
		}
		val astConstructSig = declaration.ownedConstructSignature;
		if (astConstructSig !== null) {
			relinkMethod(astConstructSig, classifier.constructSignature, preLinkingPhase)
		}

		// OWNED members
		val Map<String, N4MemberDeclaration> memberByName = new HashMap();
		for (member : declaration.ownedMembersRaw) {
			val pno = member as PropertyNameOwner;
			if (member.name !== null) {
				memberByName.put(member.name, member);
			} else if (pno.hasComputedPropertyName) {
				val expr = pno.declaredName.expression;
				if (N4JSLanguageUtils.isProcessedAsCompileTimeExpression(expr)) {
					val G = RuleEnvironmentExtensions.newRuleEnvironment(pno);
					val ctv = compileTimeEvaluator.evaluateCompileTimeExpression(G, expr);
					if (ctv.valid) {
						val ctvName = (ctv as ValueValid<?>).value.toString;
						memberByName.put(ctvName, member);
					}
				}
			}
		}
		
		var idx = 0;
		for (tMember : classifier.ownedMembers) {
			val member = memberByName.get(tMember.name);
			if (tMember instanceof TField && member instanceof N4FieldDeclaration) {
				relinkField(member as N4FieldDeclaration, tMember as TField, preLinkingPhase);
			}
			if (tMember instanceof TMethod && member instanceof N4MethodDeclaration) {
				val method = member as N4MethodDeclaration;
				if (!method.isConstructSignature && !method.isCallSignature) {
					relinkMethod(method, tMember as TMethod, preLinkingPhase);
				}
			}
			if (tMember instanceof TGetter && member instanceof N4GetterDeclaration) {
				relinkGetter(member as N4GetterDeclaration, tMember as TGetter, preLinkingPhase);
			}
			if (tMember instanceof TSetter && member instanceof N4SetterDeclaration) {
				relinkSetter(member as N4SetterDeclaration, tMember as TSetter, preLinkingPhase);
			}
			idx++;
		}

		// TODO proxy resolve vs setter invocation?
		classifier.astElement = declaration;
		// setter is ok here
		declaration.definedType = classifier;
	}

}
