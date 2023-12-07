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
package org.eclipse.n4js.transpiler.es.transform;

import static com.google.common.collect.Iterables.toArray;
import static org.eclipse.n4js.tooling.organizeImports.DIUtility.findParentDIC;
import static org.eclipse.n4js.tooling.organizeImports.DIUtility.hasParentInjector;
import static org.eclipse.n4js.tooling.organizeImports.DIUtility.isBinder;
import static org.eclipse.n4js.tooling.organizeImports.DIUtility.isDIComponent;
import static org.eclipse.n4js.tooling.organizeImports.DIUtility.isInjectedClass;
import static org.eclipse.n4js.tooling.organizeImports.DIUtility.isSingleton;
import static org.eclipse.n4js.tooling.organizeImports.DIUtility.resolveBinders;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ArrLit;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ArrayElement;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._CallExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ExprStmnt;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._IdentRef;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ObjLit;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._PropertyAccessExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._PropertyGetterDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._PropertyNameValuePair;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ReturnStmnt;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._StringLiteral;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._StringLiteralForSTE;
import static org.eclipse.n4js.transpiler.utils.TranspilerUtils.orContainingExportDeclaration;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.objectType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.symbolObjectType;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.tooling.organizeImports.DIUtility;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TAnnotationTypeRefArgument;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TN4Classifier;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.DeclMergingHelper;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.inject.Inject;

/**
 * Generates DI meta information for the injector. Information is attached to the compiled type in the
 * {@link N4JSLanguageConstants#DI_SYMBOL_KEY DI_SYMBOL_KEY} property.
 *
 * TODO the DI code below makes heavy use of TModule (probably) without true need; refactor this
 */
public class DependencyInjectionTransformation extends Transformation {

	@Inject
	private DeclMergingHelper declMergingHelper;

	@Override
	public void assertPreConditions() {
		// true
	}

	@Override
	public void assertPostConditions() {
		// true
	}

	@Override
	public void analyze() {
		// ignore
	}

	@Override
	public void transform() {
		for (N4ClassDeclaration classDecl : collectNodes(getState().im, N4ClassDeclaration.class, false)) {
			TClass tClass = getState().info.getOriginalDefinedType(classDecl);
			if (tClass != null) {
				Statement codeForDI = generateCodeForDI(tClass, classDecl);
				if (codeForDI != null) {
					insertAfter(orContainingExportDeclaration(classDecl), codeForDI);
				}
			}
		}
	}

	/**
	 * Generates hooks for DI mechanisms. Mainly N4Injector (part of n4js libraries) depends on those hooks. Note that
	 * those in many cases require proper types to be imported, which makes those hooks indirectly depended on behavior
	 * of the script transpiler policy that decides to remove "unused" imported symbols. That one has to leave imported
	 * symbols that are refereed to in code generated here.
	 *
	 * Also, note second parameter passed is name or alias of the super type. passing it from caller, even if we don't
	 * use it, is a shortcut to avoid computing it again here.
	 */
	@SuppressWarnings("unchecked")
	private Statement generateCodeForDI(TClass tClass, N4ClassDeclaration classDecl) {
		List<Pair<String, Expression>> propertiesForDI = createPropertiesForDI(tClass);
		if (propertiesForDI.isEmpty()) {
			return null;
		}
		// Object.defineProperty(C, Symbol.for('<DI_SYMBOL_KEY>'), {value: { ... }}
		SymbolTableEntry classSTE = findSymbolTableEntryForElement(classDecl, true);
		SymbolTableEntryOriginal objectSTE = getSymbolTableEntryOriginal(objectType(getState().G), true);
		SymbolTableEntryOriginal definePropertySTE = getSymbolTableEntryForMember(objectType(getState().G),
				"defineProperty", false, true, true);
		SymbolTableEntryOriginal symbolObjectSTE = getSymbolTableEntryOriginal(symbolObjectType(getState().G), true);
		SymbolTableEntryOriginal forSTE = getSymbolTableEntryForMember(symbolObjectType(getState().G), "for", false,
				true, true);
		return _ExprStmnt(_CallExpr(
				_PropertyAccessExpr(objectSTE, definePropertySTE),
				_IdentRef(classSTE),
				_CallExpr(_PropertyAccessExpr(symbolObjectSTE, forSTE),
						_StringLiteral(N4JSLanguageConstants.DI_SYMBOL_KEY)),
				_ObjLit(Pair.of("value", _ObjLit(propertiesForDI.toArray(new Pair[0]))))));
	}

	private List<Pair<String, Expression>> createPropertiesForDI(TClass tClass) {
		List<Pair<String, Expression>> result = new ArrayList<>();
		if (isBinder(tClass)) {
			result.add(Pair.of("bindings", (Expression) generateBindingPairs(tClass)));
			result.add(Pair.of("methodBindings", (Expression) generateMethodBindings(tClass)));
			result.addAll(injectionPointsMetaInfo(tClass));
		} else if (isDIComponent(tClass)) {
			if (hasParentInjector(tClass)) {
				SymbolTableEntryOriginal parentDIC_STE = getSymbolTableEntryOriginal(findParentDIC(tClass), true);
				result.add(Pair.of("parent", (Expression) __NSSafe_IdentRef(parentDIC_STE)));
			}
			result.add(Pair.of("binders", (Expression) generateBinders(tClass)));
			result.addAll(injectionPointsMetaInfo(tClass));
		} else if (isInjectedClass(tClass, declMergingHelper)) {
			result.addAll(injectionPointsMetaInfo(tClass));
		}
		return result;
	}

	/**
	 * Generate DI hooks for scopes, super type, injected ctor, injected fields
	 */
	private List<Pair<String, Expression>> injectionPointsMetaInfo(TClass tClass) {
		ArrayLiteral fieldInjectionValue = fieldInjection(tClass);
		List<Pair<String, Expression>> result = new ArrayList<>();
		if (isSingleton(tClass)) {
			result.add(Pair.of("scope", _StringLiteral("Singleton")));
		}
		TMethod ownedCtor = tClass.getOwnedCtor();
		if (ownedCtor != null && AnnotationDefinition.INJECT.hasAnnotation(ownedCtor)) {
			result.add(Pair.of("injectCtorParams", methodInjectedParams(ownedCtor)));
		}
		if (!fieldInjectionValue.getElements().isEmpty()) {
			result.add(Pair.of("fieldsInjectedTypes", fieldInjection(tClass)));
		}
		return result;
	}

	/**
	 * Generate injection info for method annotated with {@link AnnotationDefinition#INJECT}. If method has no method
	 * parameters returned value is empty string, otherwise description of parameters is returned.
	 */
	private ArrayLiteral methodInjectedParams(TMethod tMethod) {
		ArrayLiteral result = _ArrLit();
		for (TFormalParameter fpar : tMethod.getFpars()) {
			SymbolTableEntryOriginal fparSTE = getSymbolTableEntryOriginal(fpar, true);
			List<PropertyAssignment> pAs = new ArrayList<>();
			pAs.add(_PropertyNameValuePair("name", _StringLiteralForSTE(fparSTE)));
			pAs.addAll(generateTypeInfo(fpar.getTypeRef()));
			result.getElements().add(_ArrayElement(_ObjLit(pAs.toArray(new PropertyAssignment[0]))));
		}
		return result;
	}

	/**
	 * Generate injection info for fields annotated with {@link AnnotationDefinition#INJECT}.
	 */
	private ArrayLiteral fieldInjection(TClass tClass) {
		ArrayLiteral result = _ArrLit();
		for (TField field : getOwnedInejctedFields(tClass)) {
			SymbolTableEntryOriginal fieldSTE = getSymbolTableEntryOriginal(field, true);
			List<PropertyAssignment> pAs = new ArrayList<>();
			pAs.add(_PropertyNameValuePair("name", _StringLiteralForSTE(fieldSTE)));
			pAs.addAll(generateTypeInfo(field.getTypeRef()));
			result.getElements().add(_ArrayElement(_ObjLit(pAs.toArray(new PropertyAssignment[0]))));
		}
		return result;
	}

	/**
	 * Generate injection info from {@link AnnotationDefinition#BIND} annotations on the provided class.
	 */
	@SuppressWarnings("unchecked")
	private ArrayLiteral generateBindingPairs(TClass tClass) {
		ArrayLiteral result = _ArrLit();
		for (Pair<TN4Classifier, TN4Classifier> binding : getBindingPairs(tClass)) {
			SymbolTableEntryOriginal keySTE = getSymbolTableEntryOriginal(binding.getKey(), true);
			SymbolTableEntryOriginal valueSTE = getSymbolTableEntryOriginal(binding.getValue(), true);
			result.getElements().add(_ArrayElement(_ObjLit(
					Pair.of("from", __NSSafe_IdentRef(keySTE)),
					Pair.of("to", __NSSafe_IdentRef(valueSTE)))));
		}
		return result;
	}

	/**
	 * Generate injection info for methods annotated with {@link AnnotationDefinition#PROVIDES}. Returned information
	 * contains returned type, name and formal parameters of the method.
	 */
	private ArrayLiteral generateMethodBindings(TClass tClass) {
		ArrayLiteral result = _ArrLit();
		for (TMethod method : getOwnedProviderMethods(tClass)) {
			result.getElements().add(_ArrayElement(_ObjLit(
					generateTypeInfo(method.getReturnTypeRef(), "to").get(0),
					_PropertyNameValuePair("name", _StringLiteral(method.getName())),
					_PropertyNameValuePair("args", methodInjectedParams(method)))));
		}
		return result;
	}

	private ArrayLiteral generateBinders(TClass tClass) {
		ArrayLiteral result = _ArrLit();
		for (Type binderType : resolveBinders(tClass)) {
			SymbolTableEntryOriginal binderTypeSTE = getSymbolTableEntryOriginal(binderType, true);
			result.getElements().add(_ArrayElement(
					__NSSafe_IdentRef(binderTypeSTE)));
		}
		return result;
	}

	/**
	 * Generate type information for DI. Mainly FQN of the {@link TypeRef}, or composed information if given type is
	 * generic.
	 */
	private List<PropertyAssignment> generateTypeInfo(TypeRef typeRef) {
		return generateTypeInfo(typeRef, "type");
	}

	private List<PropertyAssignment> generateTypeInfo(TypeRef typeRef, String propertyName) {
		if (!DIUtility.isProviderType(typeRef)) {
			SymbolTableEntryOriginal declaredTypeSTE = getSymbolTableEntryOriginal(typeRef.getDeclaredType(), true);
			return List.of(
					_PropertyGetterDecl(propertyName, _ReturnStmnt(__NSSafe_IdentRef(declaredTypeSTE))));
		} else if (typeRef instanceof ParameterizedTypeRef) {
			// typeRef should be N4Provider<X>, fqn needs to be obtained at runtime
			SymbolTableEntryOriginal declaredTypeSTE = getSymbolTableEntryOriginal(typeRef.getDeclaredType(), true);
			return List.of(
					_PropertyGetterDecl(propertyName, _ReturnStmnt(__NSSafe_IdentRef(declaredTypeSTE))),
					_PropertyNameValuePair("typeVar", _ObjLit(
							toArray(generateTypeInfo(head(filter(typeRef.getDeclaredTypeArgs(), TypeRef.class))),
									PropertyAssignment.class))));
		} else {
			String msg = typeRef == null ? ""
					: (typeRef.getDeclaredType() == null ? "" : typeRef.getDeclaredType().getName());
			throw new IllegalStateException("cannot generate type info for " + msg);
			// note: at this point, the old transpiler did only log the error and returned something like:
			// return #[];
		}
	}

	/**
	 * Get list of {@link Pair}s of first and second argument of the {@link AnnotationDefinition#BIND} annotation.
	 */
	private Iterable<Pair<TN4Classifier, TN4Classifier>> getBindingPairs(TClass clazz) {
		return map(AnnotationDefinition.BIND.getAllAnnotations(clazz), a -> Pair.of(
				(TN4Classifier) (((TAnnotationTypeRefArgument) a.getArgs().get(0)).getTypeRef().getDeclaredType()),
				(TN4Classifier) ((TAnnotationTypeRefArgument) last(a.getArgs())).getTypeRef().getDeclaredType()));
	}

	private Iterable<TMethod> getOwnedProviderMethods(TClass clazz) {
		return filter(filter(clazz.getOwnedMembers(), TMethod.class),
				m -> AnnotationDefinition.PROVIDES.hasAnnotation(m));
	}

	private Iterable<TField> getOwnedInejctedFields(TN4Classifier clazz) {
		return filter(filter(clazz.getOwnedMembers(), TField.class), f -> AnnotationDefinition.INJECT.hasAnnotation(f));
	}
}
