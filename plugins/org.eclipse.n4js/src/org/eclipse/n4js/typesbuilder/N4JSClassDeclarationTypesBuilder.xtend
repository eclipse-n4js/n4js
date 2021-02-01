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

import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4ClassDefinition
import org.eclipse.n4js.n4JS.N4ClassExpression
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.ts.utils.TypeUtils

import static extension org.eclipse.n4js.utils.N4JSLanguageUtils.*

public class N4JSClassDeclarationTypesBuilder extends N4JSClassifierDeclarationTypesBuilder {

	def protected boolean relinkTClass(N4ClassDeclaration n4Class, TModule target, boolean preLinkingPhase, int idx) {
		if (n4Class.name === null) { // may be null due to syntax errors
			return false;
		}

		val TClass tclass = target.topLevelTypes.get(idx) as TClass

		tclass.relinkClassifierAndMembers(n4Class, preLinkingPhase);
		return true;
	}

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
		tclass.addCopyOfTypeParameters(n4Class, preLinkingPhase);

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
		
		VersionedTypesBuilderUtil.setTypeVersion(tclass, classDecl);

		return tclass;
	}

	def private createTClass(N4ClassExpression classExpr) {
		val tclass = TypesFactory::eINSTANCE.createTClass();
		tclass.name = classExpr.name;
		return tclass;
	}

	def private setSuperType(TClass tclass, N4ClassDefinition classDecl, boolean preLinkingPhase) {
		if (!preLinkingPhase)
			tclass.superClassRef = TypeUtils.copyWithProxies(classDecl.superClassRef?.typeRefInAST);
	}

	def private addImplementedInterfaces(TClass tclass, N4ClassDefinition classDecl, boolean preLinkingPhase) {
		if (!preLinkingPhase)
			addCopyOfReferences(tclass.implementedInterfaceRefs, classDecl.implementedInterfaceRefs.map[typeRefInAST]);
	}
}
