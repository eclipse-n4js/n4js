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
package org.eclipse.n4js.transpiler.es.transform

import com.google.inject.Inject
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.N4JSLanguageConstants
import org.eclipse.n4js.n4JS.ArrayLiteral
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.assistants.TypeAssistant
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.TAnnotationTypeRefArgument
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TN4Classifier

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.organize.imports.DIUtility.*
import static extension org.eclipse.n4js.transpiler.utils.TranspilerUtils.*
import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Generates DI meta information for the injector.
 * Information is attached to the compiled type in the {@link N4JSLanguageConstants#DI_SYMBOL_KEY DI_SYMBOL_KEY}
 * property.
 *
 * TODO the DI code below makes heavy use of TModule (probably) without true need; refactor this
 */
class DependencyInjectionTransformation extends Transformation {

	@Inject private TypeAssistant typeAssistant;


	override assertPreConditions() {
		// true
	}

	override assertPostConditions() {
		// true
	}

	override analyze() {
		// ignore
	}

	override transform() {
		collectNodes(state.im, N4ClassDeclaration, false).forEach[classDecl|
			val tClass = state.info.getOriginalDefinedType(classDecl);
			if( tClass !== null ) {
				val superClassSTE = typeAssistant.getSuperClassSTE(classDecl);
				val codeForDI = generateCodeForDI(tClass, classDecl, superClassSTE);
				if(codeForDI!==null) {
					insertAfter(classDecl.orContainingExportDeclaration, codeForDI);
				}
			}
		];
	}

	/**
	 * Generates hooks for DI mechanisms. Mainly N4Injector (part of n4js libraries)
	 * depends on those hooks.
	 * Note that those in many cases require proper types to be imported, which makes those
	 * hooks indirectly depended on behavior of the script transpiler policy that decides to
	 * remove "unused" imported symbols. That one has to leave imported symbols that are refereed to
	 * in code generated here.
	 *
	 * Also, note second parameter passed is name or alias of the super type.
	 * passing it from caller, even if we don't use it, is a shortcut to avoid computing it again here.
	 */
	def private Statement generateCodeForDI(TClass it, N4ClassDeclaration classDecl, SymbolTableEntry superClassSTE) {
		val propertiesForDI = createPropertiesForDI(classDecl, superClassSTE);
		if(propertiesForDI.empty) {
			return null;
		}
		// Object.defineProperty(C, Symbol.for('<DI_SYMBOL_KEY>'), {value: { ... }}
		val classSTE = findSymbolTableEntryForElement(classDecl, true);
		val objectSTE = getSymbolTableEntryOriginal(state.G.objectType, true);
		val definePropertySTE = getSymbolTableEntryForMember(state.G.objectType, "defineProperty", false, true, true);
		val symbolObjectSTE = getSymbolTableEntryOriginal(state.G.symbolObjectType, true);
		val forSTE = getSymbolTableEntryForMember(state.G.symbolObjectType, "for", false, true, true);
		return _ExprStmnt(_CallExpr(
			_PropertyAccessExpr(objectSTE, definePropertySTE),
			_IdentRef(classSTE),
			_CallExpr(_PropertyAccessExpr(symbolObjectSTE, forSTE), _StringLiteral(N4JSLanguageConstants.DI_SYMBOL_KEY)),
			_ObjLit(
				"value" -> _ObjLit(
					propertiesForDI
				)
			)
		));
	}

	def private Pair<String,Expression>[] createPropertiesForDI(TClass it, N4ClassDeclaration classDecl, SymbolTableEntry superClassSTE) {
		val result = <Pair<String,Expression>>newArrayList;
		if(isBinder) {
			result += "bindings" -> generateBindingPairs as Expression;
			result += "methodBindings" -> generateMethodBindings as Expression;
			result += injectionPointsMetaInfo(superClassSTE);
		} else if(isDIComponent) {
			if(hasParentInjector) {
				val parentDIC_STE = getSymbolTableEntryOriginal(findParentDIC, true);
				result += "parent" -> __NSSafe_IdentRef(parentDIC_STE) as Expression;
			}
			result += "binders" -> generateBinders as Expression;
			result += injectionPointsMetaInfo(superClassSTE);
		} else if(isInjectedClass) {
			result += injectionPointsMetaInfo(superClassSTE);
		}
		return result;
	}

	/**
	 * Generate DI hooks for scopes, super type, injected ctor, injected fields
	 */
	def private Pair<String,Expression>[] injectionPointsMetaInfo(TClass it, SymbolTableEntry superClassSTE) {
		val fieldInjectionValue = it.fieldInjection;
		val result = <Pair<String,Expression>>newArrayList;
		if(isSingleton) {
			result += "scope" -> _StringLiteral("Singleton") as Expression;
		}
		if(ownedCtor!==null && AnnotationDefinition.INJECT.hasAnnotation(ownedCtor)) {
			result += "injectCtorParams" -> ownedCtor.methodInjectedParams as Expression;
		}
		if(!fieldInjectionValue.elements.empty) {
			result += "fieldsInjectedTypes" -> fieldInjection as Expression;
		}
		return result;
	}

	/**
	 * Generate injection info for method annotated with {@link AnnotationDefinition#INJECT}.
	 * If method has no method parameters returned value is empty string,
	 * otherwise description of parameters is returned.
	 */
	def private ArrayLiteral methodInjectedParams(TMethod it) {
		val result = _ArrLit();
		for(fpar : fpars) {
			val fparSTE = getSymbolTableEntryOriginal(fpar, true);
			result.elements += _ArrayElement(_ObjLit(
				#["name" -> _StringLiteralForSTE(fparSTE) as Expression]
				+ fpar.typeRef.generateTypeInfo
			));
		}
		return result;
	}

	/**
	 * Generate injection info for fields annotated with {@link AnnotationDefinition#INJECT}.
	 */
	def private ArrayLiteral fieldInjection(TClass it) {
		val result = _ArrLit();
		for(field : ownedInejctedFields) {
			val fieldSTE = getSymbolTableEntryOriginal(field, true);
			result.elements += _ArrayElement(_ObjLit(
				#["name" -> _StringLiteralForSTE(fieldSTE) as Expression]
				+ field.typeRef.generateTypeInfo
			));
		}
		return result;
	}

	/**
	 * Generate injection info from {@link AnnotationDefinition#BIND} annotations on the provided class.
	 */
	private def ArrayLiteral generateBindingPairs(TClass it) {
		val result = _ArrLit();
		for(binding : getBindingPairs) {
			val keySTE = getSymbolTableEntryOriginal(binding.key, true);
			val valueSTE = getSymbolTableEntryOriginal(binding.value, true);
			result.elements += _ArrayElement(_ObjLit(
				"from" -> __NSSafe_IdentRef(keySTE),
				"to" -> __NSSafe_IdentRef(valueSTE)
			));
		}
		return result;
	}

	/**
	 * Generate injection info for methods annotated with {@link AnnotationDefinition#PROVIDES}.
	 * Returned information contains returned type, name and formal parameters of the method.
	 */
	def private ArrayLiteral generateMethodBindings(TClass it) {
		val result = _ArrLit();
		for(method : ownedProviderMethods) {
			result.elements += _ArrayElement(_ObjLit(
				method.returnTypeRef.generateTypeInfo("to").head,
				"name" -> _StringLiteral(method.name),
				"args" -> method.methodInjectedParams
			));
		}
		return result;
	}

	def private ArrayLiteral generateBinders(TClass it) {
		val result = _ArrLit();
		for(binderType : resolveBinders) {
			val binderTypeSTE = getSymbolTableEntryOriginal(binderType, true);
			result.elements += _ArrayElement(
				__NSSafe_IdentRef(binderTypeSTE)
			);
		}
		return result;
	}

	/**
	 * Generate type information for DI. Mainly FQN of the {@link TypeRef}, or composed information
	 * if given type is generic.
	 */
	def private Pair<String,Expression>[] generateTypeInfo(TypeRef typeRef) {
		typeRef.generateTypeInfo("type")
	}
	def private Pair<String,Expression>[] generateTypeInfo(TypeRef typeRef, String propertyName) {
		if (!typeRef.providerType) {
			val declaredTypeSTE = getSymbolTableEntryOriginal(typeRef.declaredType, true);
			return #[
				propertyName -> __NSSafe_IdentRef(declaredTypeSTE)
			];
		} else if (typeRef instanceof ParameterizedTypeRef) {
			// typeRef should be N4Provider<X>, fqn needs to be obtained at runtime
			val declaredTypeSTE = getSymbolTableEntryOriginal(typeRef.declaredType, true);
			return #[
				propertyName -> __NSSafe_IdentRef(declaredTypeSTE),
				"typeVar" -> _ObjLit(
					typeRef.typeArgs.filter(TypeRef).head.generateTypeInfo
				)
			];
		} else {
			throw new IllegalStateException("cannot generate type info for " + typeRef?.declaredType?.name);
// note: at this point, the old transpiler did only log the error and returned something like:
//			return #[];
		}
	}




	/**
	 * Get list of {@link Pair}s of first and second argument of the {@link AnnotationDefinition#BIND} annotation.
	 */
	def private getBindingPairs(TClass clazz) {
		AnnotationDefinition.BIND.getAllAnnotations(clazz).map [
			((args.head as TAnnotationTypeRefArgument).getTypeRef.declaredType) as TN4Classifier
				-> ((args.last as TAnnotationTypeRefArgument).getTypeRef.declaredType) as TN4Classifier
		]
	}

	def private getOwnedProviderMethods(TClass clazz) {
		clazz.ownedMembers.filter(TMethod).filter[AnnotationDefinition.PROVIDES.hasAnnotation(it)];
	}

	def private getOwnedInejctedFields(TN4Classifier clazz) {
		clazz.ownedMembers.filter(TField).filter[AnnotationDefinition.INJECT.hasAnnotation(it)]
	}
}
