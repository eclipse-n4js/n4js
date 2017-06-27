/**
 * Copyright (c) 2016 NumberFour AG.
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
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4ClassDefinition
import org.eclipse.n4js.n4JS.N4ClassExpression
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypeAccessModifier
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.ts.types.TypingStrategy
import java.util.List

import static extension org.eclipse.n4js.utils.N4JSLanguageUtils.*

public class N4JSClassDeclarationTypesBuilder {

	@Inject extension N4JSTypesBuilderHelper
	@Inject extension N4JSFieldTypesBuilder
	@Inject extension N4JSMethodTypesBuilder
	@Inject extension N4JSGetterTypesBuilder
	@Inject extension N4JSSetterTypesBuilder

	def protected TClass createTClass(N4ClassDeclaration n4Class, TModule target, boolean preLinkingPhase) {
		if (n4Class.name === null) { // may be null due to syntax errors
			return null;
		}

		val TClass tclass = n4Class.createTClass;

		// modifiers
		tclass.setTypeAccessModifier(n4Class);
		tclass.setProvidedByRuntime(n4Class, preLinkingPhase);
		tclass.declaredStaticPolyfill = n4Class.isStaticPolyfill;
		tclass.declaredPolyfill = n4Class.isPolyfill || tclass.declaredStaticPolyfill;
		tclass.declaredCovariantConstructor = n4Class.isDeclaredCovariantConstructor;
		tclass.addTypeParameters(n4Class, preLinkingPhase);

		// super types etc
		tclass.setSuperType(n4Class, preLinkingPhase);
		tclass.addImplementedInterfaces(n4Class, preLinkingPhase);

		// members
		tclass.addFields(n4Class, preLinkingPhase);
		tclass.addMethods(n4Class, preLinkingPhase);

		tclass.addGetters(n4Class, preLinkingPhase);
		tclass.addSetters(n4Class, preLinkingPhase);


		tclass.copyAnnotations(n4Class, preLinkingPhase);

		// + set "bindings" (derived refs from ast to types and vice versa)
		tclass.astElement = n4Class;

		n4Class.definedType = tclass;

		target.topLevelTypes += tclass;

		return tclass;
	}

	def package createTClass(N4ClassExpression n4Class, TModule target, boolean preLinkingPhase) {
		val tclass = n4Class.createTClass;

		// super types etc
		tclass.setSuperType(n4Class, preLinkingPhase);
		tclass.addImplementedInterfaces(n4Class, preLinkingPhase);

		// members
		tclass.addFields(n4Class, preLinkingPhase);
		tclass.addMethods(n4Class, preLinkingPhase);

		tclass.addGetters(n4Class, preLinkingPhase);
		tclass.addSetters(n4Class, preLinkingPhase);

		tclass.copyAnnotations(n4Class, preLinkingPhase);

		// + set "bindings" (derived refs from ast to types and vice versa)
		tclass.astElement = n4Class;

		n4Class.definedType = tclass;

		target.internalTypes += tclass;
	}

	def private createTClass(N4ClassDeclaration classDecl) {
		val tclass = TypesFactory::eINSTANCE.createTClass();
		tclass.name = classDecl.name;
		tclass.exportedName = classDecl.exportedName;
		tclass.external = classDecl.external;
		tclass.declaredAbstract = classDecl.abstract;
		tclass.declaredFinal = AnnotationDefinition.FINAL.hasAnnotation(classDecl);
		tclass.observable = AnnotationDefinition.OBSERVABLE.hasAnnotation(classDecl);
		tclass.declaredN4JS = AnnotationDefinition.N4JS.hasAnnotation(classDecl);

		tclass.setTypingStrategy(
			if (classDecl.typingStrategy === TypingStrategy.DEFAULT) {
				TypingStrategy.DEFAULT;
			} else { // STRUCTURAL_FIELD is not allowed on def site, but maybe we got a wrong input
				TypingStrategy.STRUCTURAL;
			}
		);

		return tclass;
	}

	def private createTClass(N4ClassExpression classExpr) {
		val tclass = TypesFactory::eINSTANCE.createTClass();
		tclass.name = classExpr.name;
		return tclass;
	}

	def private setTypeAccessModifier(TClass tclass, N4ClassDeclaration classDecl) {
		setTypeAccessModifier(classDecl, [TypeAccessModifier modifier|tclass.declaredTypeAccessModifier = modifier],
			classDecl.declaredModifiers, getAllAnnotations(classDecl));
	}

	def private addTypeParameters(TClass tclass, N4ClassDeclaration classDecl, boolean preLinkingPhase) {
		addCopyOfReferences([params|tclass.typeVars += params], classDecl.typeVars, preLinkingPhase);
	}

	def private addFields(TClass tclass, N4ClassDefinition classDecl, boolean preLinkingPhase) {
		tclass.ownedMembers.addAll(
			classDecl.ownedMembers.filter(N4FieldDeclaration).map[createField(tclass, preLinkingPhase)].filterNull);
	}

	def private addMethods(TClass tclass, N4ClassDefinition n4Class, boolean preLinkingPhase) {
		tclass.ownedMembers.addAll(
			n4Class.ownedMembers.filter(N4MethodDeclaration).map[createMethod(preLinkingPhase)].filterNull);
		tclass.callableCtor = n4Class.ownedCallableCtor?.createMethod(preLinkingPhase);
	}

	def private addGetters(TClass tClass, N4ClassDefinition n4Class, boolean preLinkingPhase) {

		// create also getters for all non private fields without explicit getter
		val n4Getters = n4Class.ownedMembers.filter(N4GetterDeclaration);
		val getters = n4Getters.map[createGetter(tClass, preLinkingPhase)].filterNull;
		tClass.ownedMembers.addAll(getters);
	}

	def private addSetters(TClass tClass, N4ClassDefinition n4Class, boolean preLinkingPhase) {

		// create also getters for all non private fields without explicit getter
		val n4Setters = n4Class.ownedMembers.filter(N4SetterDeclaration);
		val setters = n4Setters.map[createSetter(tClass, preLinkingPhase)].filterNull;
		tClass.ownedMembers.addAll(setters);
	}

	def private setSuperType(TClass tclass, N4ClassDefinition classDecl, boolean preLinkingPhase) {
		setCopyOfReference([ParameterizedTypeRef typeRef|tclass.superClassRef = typeRef], classDecl.superClassRef,
			preLinkingPhase);
	}

	def private addImplementedInterfaces(TClass tclass, N4ClassDefinition classDecl, boolean preLinkingPhase) {
		addCopyOfReferences([List<ParameterizedTypeRef> interfaces|tclass.implementedInterfaceRefs += interfaces],
			classDecl.implementedInterfaceRefs, preLinkingPhase);
	}
}
