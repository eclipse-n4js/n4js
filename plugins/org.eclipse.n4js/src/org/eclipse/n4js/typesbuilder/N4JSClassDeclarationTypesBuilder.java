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
package org.eclipse.n4js.typesbuilder;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDefinition;
import org.eclipse.n4js.n4JS.N4ClassExpression;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.utils.N4JSLanguageUtils;

/***/
public class N4JSClassDeclarationTypesBuilder extends N4JSClassifierDeclarationTypesBuilder {

	/***/
	protected boolean relinkTClass(N4ClassDeclaration n4Class, AbstractNamespace target, boolean preLinkingPhase,
			int idx) {
		if (n4Class.getName() == null) { // may be null due to syntax errors
			return false;
		}

		TClass tclass = (TClass) target.getTypes().get(idx);

		relinkClassifierAndMembers(tclass, n4Class, preLinkingPhase);
		return true;
	}

	/***/
	protected TClass createTClass(N4ClassDeclaration n4Class, AbstractNamespace target, boolean preLinkingPhase) {
		if (n4Class.getName() == null) { // may be null due to syntax errors
			return null;
		}

		TClass tclass = createTClass(n4Class);
		// modifiers
		_n4JSTypesBuilderHelper.setTypeAccessModifier(tclass, n4Class);
		_n4JSTypesBuilderHelper.setProvidedByRuntime(tclass, n4Class, preLinkingPhase);
		tclass.setDeclaredNonStaticPolyfill(N4JSLanguageUtils.isNonStaticPolyfill(n4Class));
		tclass.setDeclaredStaticPolyfill(N4JSLanguageUtils.isStaticPolyfill(n4Class));
		tclass.setDeclaredCovariantConstructor(_n4JSTypesBuilderHelper.isDeclaredCovariantConstructor(n4Class));
		_n4JSTypeVariableTypesBuilder.addTypeParameters(tclass, n4Class, preLinkingPhase);

		// super types etc
		setSuperType(tclass, n4Class, preLinkingPhase);
		addImplementedInterfaces(tclass, n4Class, preLinkingPhase);

		// members
		addFields(tclass, n4Class, preLinkingPhase);
		addMethods(tclass, n4Class, target, preLinkingPhase);

		addGetters(tclass, n4Class, target, preLinkingPhase);
		addSetters(tclass, n4Class, target, preLinkingPhase);

		_n4JSTypesBuilderHelper.copyAnnotations(tclass, n4Class, preLinkingPhase);

		// + set "bindings" (derived refs from ast to types and vice versa)
		tclass.setAstElement(n4Class);
		n4Class.setDefinedType(tclass);

		target.getTypes().add(tclass);

		return tclass;
	}

	void createTClass(N4ClassExpression n4Class, AbstractNamespace target, boolean preLinkingPhase) {
		TClass tclass = createTClass(n4Class);

		// super types etc
		setSuperType(tclass, n4Class, preLinkingPhase);
		addImplementedInterfaces(tclass, n4Class, preLinkingPhase);

		// members
		addFields(tclass, n4Class, preLinkingPhase);
		addMethods(tclass, n4Class, target, preLinkingPhase);

		addGetters(tclass, n4Class, target, preLinkingPhase);
		addSetters(tclass, n4Class, target, preLinkingPhase);

		_n4JSTypesBuilderHelper.copyAnnotations(tclass, n4Class, preLinkingPhase);

		// + set "bindings" (derived refs from ast to types and vice versa)
		tclass.setAstElement(n4Class);
		n4Class.setDefinedType(tclass);

		target.getContainingModule().getInternalTypes().add(tclass);
	}

	private TClass createTClass(N4ClassDeclaration classDecl) {
		TClass tclass = TypesFactory.eINSTANCE.createTClass();
		tclass.setName(classDecl.getName());
		tclass.setExternal(classDecl.isExternal());
		tclass.setDeclaredAbstract(classDecl.isAbstract());
		tclass.setDeclaredFinal(AnnotationDefinition.FINAL.hasAnnotation(classDecl));
		tclass.setObservable(AnnotationDefinition.OBSERVABLE.hasAnnotation(classDecl));
		tclass.setDeclaredEcmaScript(AnnotationDefinition.ECMASCRIPT.hasAnnotation(classDecl));

		return tclass;
	}

	private TClass createTClass(N4ClassExpression classExpr) {
		TClass tclass = TypesFactory.eINSTANCE.createTClass();
		tclass.setName(classExpr.getName());
		return tclass;
	}

	private void setSuperType(TClass tclass, N4ClassDefinition classDecl, boolean preLinkingPhase) {
		if (!preLinkingPhase) {
			TypeReferenceNode<ParameterizedTypeRef> scr = classDecl.getSuperClassRef();
			tclass.setSuperClassRef(TypeUtils.copyWithProxies(scr == null ? null : scr.getTypeRefInAST()));
		}
	}

	private void addImplementedInterfaces(TClass tclass, N4ClassDefinition classDecl, boolean preLinkingPhase) {
		if (!preLinkingPhase) {
			_n4JSTypesBuilderHelper.addCopyOfReferences(tclass.getImplementedInterfaceRefs(),
					toList(map(classDecl.getImplementedInterfaceRefs(), ir -> ir.getTypeRefInAST())));
		}
	}
}
