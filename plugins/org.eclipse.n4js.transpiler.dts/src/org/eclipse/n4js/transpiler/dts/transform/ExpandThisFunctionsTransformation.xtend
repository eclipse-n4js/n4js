/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.dts.transform

import com.google.inject.Inject
import java.util.ArrayList
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 */
class ExpandThisFunctionsTransformation extends Transformation {

	@Inject
	private N4JSTypeSystem ts;


	override assertPreConditions() {
	}

	override assertPostConditions() {
	}

	override analyze() {
		// ignore
	}

	override transform() {
		makeInferredTypesExplicit();
	}

	def private void makeInferredTypesExplicit() {
		val classes = collectNodes(state.im, true, N4ClassDeclaration);
		
		for (clazz : classes) {
			val clazzOrig = state.tracer.getOriginalASTNode(clazz) as N4ClassDeclaration;
			val specConstructor = findSpecConstructorInSuperclass(clazzOrig);
			if (specConstructor !== null) {
				addOverwritingSpecConstructor(specConstructor, clazz);
			}
		}
	}
	
	def TMethod findSpecConstructorInSuperclass(N4ClassDeclaration clazz) {
		var constructor = getConstructor(clazz);
		if (constructor !== null) {
			// there can be only one constructor
			return null;
		}
		
		
		var curClass = clazz.definedTypeAsClass;
		do {
			if (curClass.superClass === null) {
				return null;
			}
			curClass = curClass.superClass;
			val ctor = curClass.ownedCtor
			
			if (isSpecConstructor(ctor)) {
				return ctor;
			} else if (ctor !== null) {
				return null;
			}
		} while (true);
	}
	
	def N4MethodDeclaration getConstructor(N4ClassDeclaration clazz) {
		val methods = collectNodes(clazz, false, N4MethodDeclaration);
		for (method : methods) {
			if (method.isConstructor) {
				return method;
			}
		}
		return null;
	}
	
	def boolean isSpecConstructor(TMethod method) {
		if (method === null) {
			return false;
		}
		if (method.fpars.empty) {
			return false;
		}
		for (fpar : method.fpars) {
			if (AnnotationDefinition.SPEC.hasAnnotation(fpar)) {
				return true;
			}
		}
		return false;
	}

	def N4MethodDeclaration addOverwritingSpecConstructor(TMethod constructor, N4ClassDeclaration clazz) {
		val clazzOrig = state.tracer.getOriginalASTNode(clazz) as N4ClassDeclaration;
		val newConstructor = _N4MethodDecl("constructor", new ArrayList());
		clazz.ownedMembersRaw += newConstructor;
		for (fpar: constructor.fpars) {
			val newFPar = _FormalParameter(fpar.name);
			newConstructor.fpars += newFPar;
			var typeRef = fpar.typeRef;
			if (AnnotationDefinition.SPEC.hasAnnotation(fpar)) {
				val thisTypeRef = TypeUtils.createTypeRefWithParamsAsArgs(clazzOrig.definedType);
				val localG = state.G.wrap;
				localG.setThisBinding(thisTypeRef);
				typeRef = ts.substTypeVariables(localG, typeRef);
				typeRef = ts.upperBoundWithReopen(state.G, typeRef);
			}
			
			newFPar.declaredTypeRefNode = _TypeReferenceNode(state, typeRef);
			state.info.setOriginalProcessedTypeRef(newFPar.declaredTypeRefNode, typeRef);
		}
		return newConstructor;
	}
}
