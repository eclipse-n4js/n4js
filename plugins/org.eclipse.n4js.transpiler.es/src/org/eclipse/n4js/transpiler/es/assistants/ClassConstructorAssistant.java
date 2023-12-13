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
package org.eclipse.n4js.transpiler.es.assistants;

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Argument;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._AssignmentExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._CallExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ConditionalExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._EqualityExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ExprStmnt;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Fpar;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._IdentRef;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._IfStmnt;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4MethodDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._OR;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ObjLit;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Parenthesis;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._PropertyAccessExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._PropertyNameValuePair;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._RelationalExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Snippet;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._StringLiteralForSTE;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._SuperLiteral;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._TRUE;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ThisLiteral;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._VariableDeclaration;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._VariableStatement;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.errorType;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.builtInOrProvidedByRuntime;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.builtInOrProvidedByRuntimeOrShape;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.ListExtensions.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.EqualityOperator;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.ModifierUtils;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.RelationalOperator;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.SuperLiteral;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatementKeyword;
import org.eclipse.n4js.transpiler.TransformationAssistant;
import org.eclipse.n4js.transpiler.assistants.TypeAssistant;
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryInternal;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * Modify or create the constructor of a class declaration.
 */
public class ClassConstructorAssistant extends TransformationAssistant {

	@Inject
	private TypeAssistant typeAssistant;
	@Inject
	private ClassifierAssistant classifierAssistant;

	private static final class SpecInfo {
		/**
		 * The <code>@Spec</code>-fpar in the intermediate model. Never <code>null</code>.
		 */
		public final FormalParameter fpar;
		/**
		 * Names of properties in the with-clause of the <code>@Spec</code>-fpar's type. For example, as in:
		 *
		 * <pre>
		 * constructor(@Spec specObj: ~i~this with { addProp: string }) { ... } </code>.
		 */
		public final ImmutableSet<String> additionalProps;

		public SpecInfo(FormalParameter fpar, Iterable<String> additionalProps) {
			this.fpar = Objects.requireNonNull(fpar);
			this.additionalProps = ImmutableSet.copyOf(additionalProps);
		}
	}

	/**
	 * Amend the constructor of the given class with implicit functionality (e.g. initialization of instance fields).
	 * Will create an implicit constructor declaration iff no constructor is defined in the N4JS source code AND an
	 * implicit constructor is actually required.
	 */
	public void amendConstructor(N4ClassDeclaration classDecl, SymbolTableEntry classSTE,
			SymbolTableEntryOriginal superClassSTE,
			LinkedHashSet<N4FieldDeclaration> fieldsRequiringExplicitDefinition) {

		N4MethodDeclaration explicitCtorDecl = classDecl.getOwnedCtor(); // the constructor defined in the N4JS source
																			// code or 'null' if none was defined
		N4MethodDeclaration ctorDecl = explicitCtorDecl != null ? explicitCtorDecl
				: _N4MethodDecl(N4JSLanguageConstants.CONSTRUCTOR);

		// amend formal parameters
		SpecInfo specInfo = amendFormalParametersOfConstructor(classDecl, ctorDecl, explicitCtorDecl);

		// amend body
		boolean isNonTrivial = amendBodyOfConstructor(classDecl, classSTE, superClassSTE, ctorDecl, explicitCtorDecl,
				specInfo, fieldsRequiringExplicitDefinition);

		// add constructor to classDecl (if necessary)
		if (ctorDecl.eContainer() == null && isNonTrivial) {
			classDecl.getOwnedMembersRaw().add(0, ctorDecl);
		}
	}

	private SpecInfo amendFormalParametersOfConstructor(N4ClassDeclaration classDecl, N4MethodDeclaration ctorDecl,
			N4MethodDeclaration explicitCtorDecl) {
		FormalParameter specFpar = null;
		TypeRef specFparTypeRef = null;

		boolean hasExplicitCtor = explicitCtorDecl != null;
		if (hasExplicitCtor) {
			// explicitly defined constructor
			// --> nothing to be changed (use fpars from N4JS source code)

			specFpar = head(filter(ctorDecl.getFpars(), fpar -> AnnotationDefinition.SPEC.hasAnnotation(fpar)));
			if (specFpar != null) {
				TypeReferenceNode<TypeRef> typeRefNode = specFpar.getDeclaredTypeRefNode();
				if (typeRefNode != null) {
					specFparTypeRef = getState().info.getOriginalProcessedTypeRef(typeRefNode);
				}
			}
		} else {
			// implicit constructor
			// --> create fpars using fpars of nearest constructor in hierarchy as template

			TMethod templateCtor = getNearestConstructorInHierarchy(classDecl);
			if (templateCtor != null) {
				for (TFormalParameter templateFpar : templateCtor.getFpars()) {
					boolean isSpecFpar = AnnotationDefinition.SPEC.hasAnnotation(templateFpar);
					FormalParameter newFpar = _Fpar(templateFpar.getName(), templateFpar.isVariadic(), isSpecFpar);
					ctorDecl.getFpars().add(newFpar);

					if (isSpecFpar && specFpar == null) {
						specFpar = newFpar;
						specFparTypeRef = TypeUtils.copy(templateFpar.getTypeRef());
					}
				}
			}
		}

		if (specFpar != null) {
			Iterable<String> additionalProps = Collections.emptyList();
			if (specFparTypeRef != null && specFparTypeRef.getStructuralMembers() != null) {
				additionalProps = map(specFparTypeRef.getStructuralMembers(), m -> m.getName());
			}
			return new SpecInfo(specFpar, additionalProps);
		}
		return null;
	}

	/**
	 * Returns <code>true</code> iff the constructor is non-trivial, i.e. non-empty and containing more than just the
	 * default super call.
	 */
	private boolean amendBodyOfConstructor(N4ClassDeclaration classDecl, SymbolTableEntry classSTE,
			SymbolTableEntryOriginal superClassSTE,
			N4MethodDeclaration ctorDecl, N4MethodDeclaration explicitCtorDecl, SpecInfo specInfo,
			LinkedHashSet<N4FieldDeclaration> fieldsRequiringExplicitDefinition) {

		boolean hasExplicitCtor = explicitCtorDecl != null;
		Block body = ctorDecl.getBody();

		boolean isDirectSubclassOfError = superClassSTE == null ? null
				: superClassSTE.getOriginalTarget() == errorType(getState().G);
		int superCallIndex = (explicitCtorDecl != null && explicitCtorDecl.getBody() != null)
				? getSuperCallIndex(explicitCtorDecl)
				: -1;
		boolean hasExplicitSuperCall = superCallIndex >= 0;
		ExpressionStatement defaultSuperCall = null;

		int idx = (hasExplicitSuperCall) ? superCallIndex + 1 : 0;

		// add/replace/modify super call
		if (hasExplicitSuperCall) {
			// keep existing, explicit super call unchanged
		} else {
			// no explicitCtorDecl OR no body OR no explicit super call (and no direct subclass of Error)
			// --> add default super call (if required)
			if (superClassSTE != null) {
				List<FormalParameter> fparsOfSuperCtor = (hasExplicitCtor) ?
				// explicit ctor without an explicit super call: only allowed if the super constructor
				// does not have any arguments, so we can simply assume empty fpars here, without actually
				// looking up the super constructor with #getNearestConstructorInHierarchy():
						Collections.emptyList()
						:
						// no explicit ctor: the already created implicit constructor in 'ctorDecl' has the
						// same fpars as the super constructor, so we can use those as a template:
						ctorDecl.getFpars();
				defaultSuperCall = createDefaultSuperCall(fparsOfSuperCtor);
				idx = insertAt(body.getStatements(), idx, defaultSuperCall);
			}
		}
		if (isDirectSubclassOfError) {
			// special case: add oddities for sub-classing Error
			idx = insertAt(body.getStatements(), idx, createSubclassingErrorOddities());
		}

		// if we are in a spec-constructor: prepare a local variable for the spec-object and
		// ensure it is never 'undefined' or 'null'
		SymbolTableEntry specObjSTE = null;
		if (specInfo != null) {
			// let $specObj = specFpar || {};
			SymbolTableEntry specFparSTE = findSymbolTableEntryForElement(specInfo.fpar, true);
			VariableDeclaration specObjVarDecl = _VariableDeclaration("$specObj",
					_OR(_IdentRef(specFparSTE), _ObjLit()));
			idx = insertAt(body.getStatements(), idx,
					_VariableStatement(VariableStatementKeyword.CONST, specObjVarDecl));

			specObjSTE = findSymbolTableEntryForElement(specObjVarDecl, true);
		}

		// add explicit definitions of instance fields (only for fields that actually require this)
		idx = insertAt(body.getStatements(), idx,
				classifierAssistant.createExplicitFieldDefinitions(classSTE, false, fieldsRequiringExplicitDefinition));

		// add initialization code for instance fields
		idx = insertAt(body.getStatements(), idx,
				createInstanceFieldInitCode(classDecl, specInfo, specObjSTE, fieldsRequiringExplicitDefinition));

		// add delegation to field initialization functions of all directly implemented interfaces
		idx = insertAt(body.getStatements(), idx,
				createDelegationToFieldInitOfImplementedInterfaces(classDecl, specObjSTE));

		// check if constructor is non-trivial
		EList<Statement> ctorDeclStmnts = ctorDecl.getBody().getStatements();
		boolean bodyContainsOnlyDefaultSuperCall = defaultSuperCall != null
				&& ctorDeclStmnts.size() == 1
				&& ctorDeclStmnts.get(0) == defaultSuperCall;
		boolean isNonTrivialCtor = !ctorDeclStmnts.isEmpty() && !bodyContainsOnlyDefaultSuperCall;

		return isNonTrivialCtor;
	}

	private List<Statement> createInstanceFieldInitCode(N4ClassDeclaration classDecl, SpecInfo specInfo,
			SymbolTableEntry specObjSTE, Set<N4FieldDeclaration> fieldsWithExplicitDefinition) {
		List<N4FieldDeclaration> allFields = toList(filter(classDecl.getOwnedFields(), f -> !f.isStatic()
				&& !isConsumedFromInterface(f)
				&& !builtInOrProvidedByRuntime(getState().info.getOriginalDefinedMember(f))));
		if (specInfo != null) {
			// we have a spec-parameter -> we are in a spec-style constructor
			List<Statement> result = new ArrayList<>();

			// step #1: initialize all fields either with data from the specFpar OR or their initializer expression
			List<N4FieldDeclaration> currFields = new ArrayList<>();
			boolean currFieldsAreSpecced = false;
			for (N4FieldDeclaration field : allFields) {
				boolean isSpecced = isPublic(field) || specInfo.additionalProps.contains(field.getName());
				if (isSpecced == currFieldsAreSpecced) {
					currFields.add(field);
				} else {
					result.addAll(createFieldInitCodeForSeveralFields(currFields, currFieldsAreSpecced, specObjSTE));
					currFields.clear();
					currFields.add(field);
					currFieldsAreSpecced = isSpecced;
				}
			}
			result.addAll(createFieldInitCodeForSeveralFields(currFields, currFieldsAreSpecced, specObjSTE));

			// step #2: invoke setters with data from specFpar
			// (note: in case of undefined specFpar at runtime, setters should not be invoked, so we wrap in if
			// statement)
			Iterable<N4SetterDeclaration> speccedSetters = filter(classDecl.getOwnedSetters(),
					sd -> !sd.isStatic() && sd.getDeclaredModifiers().contains(N4Modifier.PUBLIC));
			result.addAll(toList(map(speccedSetters, sd -> createFieldInitCodeForSingleSpeccedSetter(sd, specObjSTE))));

			return result;
		} else {
			// simple: just initialize fields with data from their initializer expression
			return toList(map(filter(allFields,
					f -> !(f.getExpression() == null && fieldsWithExplicitDefinition.contains(f))),
					f -> createFieldInitCodeForSingleField(f)));
		}
	}

	private List<Statement> createFieldInitCodeForSeveralFields(List<N4FieldDeclaration> fieldDecls,
			boolean fieldsAreSpecced, SymbolTableEntry specObjSTE) {
		if (fieldDecls.isEmpty()) {
			return Collections.emptyList();
		}
		if (!fieldsAreSpecced) {
			return toList(map(fieldDecls, fd -> createFieldInitCodeForSingleField(fd)));
		}
		if (fieldDecls.size() == 1) {
			return List.of(createFieldInitCodeForSingleSpeccedField(fieldDecls.get(0), specObjSTE));
		}
		return List.of(createFieldInitCodeForSeveralSpeccedFields(fieldDecls, specObjSTE));
	}

	private Statement createFieldInitCodeForSingleField(N4FieldDeclaration fieldDecl) {
		// here we create:
		//
		// this.fieldName = <INIT_EXPRESSION>;
		// or
		// this.fieldName = undefined;
		//
		// NOTE: we set the field to 'undefined' even in the second case, because ...
		// 1) it makes a difference when #hasOwnProperty(), etc. is used after the constructor returns,
		// 2) for consistency with the method #createFieldInitCodeForSeveralSpeccedFields(), because with
		// destructuring as used by that method, the property will always be assigned (even if the
		// value is 'undefined' and there is no default).
		//
		SymbolTableEntry fieldSTE = findSymbolTableEntryForElement(fieldDecl, true);
		return _ExprStmnt(_AssignmentExpr(
				_PropertyAccessExpr(_ThisLiteral(), fieldSTE),
				(fieldDecl.getExpression() != null) ? fieldDecl.getExpression() // reusing the expression here
						: undefinedRef()));
	}

	@SuppressWarnings("unchecked")
	private Statement createFieldInitCodeForSeveralSpeccedFields(Iterable<N4FieldDeclaration> fieldDecls,
			SymbolTableEntry specObjSTE) {
		// here we create:
		//
		// ({
		// fieldName: this.fieldName = <INIT_EXPRESSION>,
		// ...
		// } = spec);
		//

		Iterable<Pair<String, Expression>> pairs = map(fieldDecls, fieldDecl -> {
			SymbolTableEntry fieldSTE = findSymbolTableEntryForElement(fieldDecl, true);
			ParameterizedPropertyAccessExpression_IM thisFieldName = _PropertyAccessExpr(_ThisLiteral(), fieldSTE);
			Expression expr = hasNonTrivialInitExpression(fieldDecl)
					? _AssignmentExpr(thisFieldName, fieldDecl.getExpression()) // reusing the expression here
					: thisFieldName;
			return Pair.of(fieldDecl.getName(), expr);
		});

		return _ExprStmnt(
				_Parenthesis(
						_AssignmentExpr(
								_ObjLit(
										Iterables.toArray(pairs, Pair.class)),
								_IdentRef(specObjSTE))));
	}

	private Statement createFieldInitCodeForSingleSpeccedField(N4FieldDeclaration fieldDecl,
			SymbolTableEntry specObjSTE) {
		SymbolTableEntry fieldSTE = findSymbolTableEntryForElement(fieldDecl, true);
		if (hasNonTrivialInitExpression(fieldDecl)) {
			// here we create:
			//
			// this.fieldName = spec.fieldName == undefined ? <INIT_EXPRESSION> : spec.fieldName;
			//
			// NOTE: don't use something like "'fieldName' in spec" as the condition above, because that would
			// not have the same behavior as destructuring in method #createFieldInitCodeForSeveralSpeccedFields()
			// in case the property is present but has value 'undefined'!
			//
			return _ExprStmnt(_AssignmentExpr(
					_PropertyAccessExpr(_ThisLiteral(), fieldSTE),
					// ? :
					_ConditionalExpr(
							// spec.fieldName == undefined
							_EqualityExpr(_PropertyAccessExpr(specObjSTE, fieldSTE), EqualityOperator.SAME,
									undefinedRef()),
							// <INIT_EXPRESSION>
							(fieldDecl.getExpression() != null) ? copy(fieldDecl.getExpression()) // need to copy
																									// expression here,
																									// because it will
																									// be used again!
									: undefinedRef(),
							// spec.fieldName
							_PropertyAccessExpr(specObjSTE, fieldSTE))));
		} else {
			// we have a trivial init-expression ...

			// here we create:
			//
			// this.fieldName = spec.fieldName;
			//
			return _ExprStmnt(_AssignmentExpr(
					_PropertyAccessExpr(_ThisLiteral(), fieldSTE),
					_PropertyAccessExpr(specObjSTE, fieldSTE)));
		}
	}

	private Statement createFieldInitCodeForSingleSpeccedSetter(N4SetterDeclaration setterDecl,
			SymbolTableEntry specObjSTE) {
		// here we create:
		//
		// if ('fieldName' in spec) {
		// this.fieldName = spec.fieldName;
		// }
		//

		SymbolTableEntry setterSTE = findSymbolTableEntryForElement(setterDecl, true);
		return _IfStmnt(
				_RelationalExpr(
						_StringLiteralForSTE(setterSTE), RelationalOperator.IN, _IdentRef(specObjSTE)),
				_ExprStmnt(
						_AssignmentExpr(
								_PropertyAccessExpr(_ThisLiteral(), setterSTE),
								_PropertyAccessExpr(specObjSTE, setterSTE))));
	}

	// NOTE: compare this to the super calls generated from SuperLiterals in SuperLiteralTransformation
	private ExpressionStatement createDefaultSuperCall(List<FormalParameter> fpars) {

		boolean variadicCase = !fpars.isEmpty() && last(fpars).isVariadic();
		List<Argument> args = map(fpars, fpar -> _Argument(
				_IdentRef(findSymbolTableEntryForElement(fpar, true))));

		if (variadicCase) {
			last(args).setSpread(true);
		}

		ParameterizedCallExpression callExpr = _CallExpr();
		callExpr.setTarget(_SuperLiteral());
		callExpr.getArguments().addAll(args);
		return _ExprStmnt(callExpr);
	}

	/** To be inserted where the explicit or implicit super call would be located, normally. */
	private List<Statement> createSubclassingErrorOddities() {
		return List.of(_ExprStmnt(_Snippet("""
					this.name = this.constructor.n4type.name;
					if (Error.captureStackTrace) {
					    Error.captureStackTrace(this, this.name);
					}
				""")));
	}

	private List<Statement> createDelegationToFieldInitOfImplementedInterfaces(N4ClassDeclaration classDecl,
			SymbolTableEntry /* nullable */ specObjSTE) {

		List<SymbolTableEntryOriginal> implementedIfcSTEs = toList(
				filter(typeAssistant.getSuperInterfacesSTEs(classDecl), intf ->
				// regarding the cast to TInterface: see preconditions of ClassDeclarationTransformation
				// regarding the entire line: generate $fieldInit call only if the interface is neither built-in nor
				// provided by runtime nor shape
				!builtInOrProvidedByRuntimeOrShape((TInterface) intf.getOriginalTarget())));
		if (implementedIfcSTEs.isEmpty()) {
			return Collections.emptyList();
		}

		LinkedHashSet<String> ownedInstanceDataFieldsSupressMixin = new LinkedHashSet<>();
		ownedInstanceDataFieldsSupressMixin.addAll(
				toList(map(filter(classDecl.getOwnedGetters(), g -> !isConsumedFromInterface(g)), g -> g.getName())));
		ownedInstanceDataFieldsSupressMixin.addAll(
				toList(map(filter(classDecl.getOwnedSetters(), s -> !isConsumedFromInterface(s)), s -> s.getName())));

		SymbolTableEntry classSTE = findSymbolTableEntryForElement(classDecl, false);
		SymbolTableEntryInternal $initFieldsFromInterfacesSTE = steFor_$initFieldsFromInterfaces();

		return List.of(_ExprStmnt(
				_CallExpr(
						_IdentRef($initFieldsFromInterfacesSTE),
						_ThisLiteral(),
						_IdentRef(classSTE),
						(specObjSTE != null) ? _IdentRef(specObjSTE)
								: undefinedRef(),
						_ObjLit(
								Iterables.toArray(map(ownedInstanceDataFieldsSupressMixin,
										str -> _PropertyNameValuePair(str, _TRUE())), PropertyNameValuePair.class)))));
	}

	// ################################################################################################################
	// UTILITY STUFF

	private int getSuperCallIndex(N4MethodDeclaration ownedCtor) {
		if (ownedCtor != null && ownedCtor.getBody() != null && ownedCtor.getBody().getStatements() != null) {
			EList<Statement> stmnts = ownedCtor.getBody().getStatements();

			for (int i = 0; i < stmnts.size(); i++) {
				Statement stmnt = stmnts.get(i);
				if (stmnt instanceof ExpressionStatement) {
					Expression expr = ((ExpressionStatement) stmnt).getExpression();
					if (expr instanceof ParameterizedCallExpression) {
						if (((ParameterizedCallExpression) expr).getTarget() instanceof SuperLiteral) {
							return i;
						}
					}
				}
			}
		}
		return -1;
	}

	private TMethod getNearestConstructorInHierarchy(N4ClassDeclaration classDecl) {
		TClass tClass = getState().info.getOriginalDefinedType(classDecl);
		return getNearestConstructorInHierarchy(tClass);
	}

	private TMethod getNearestConstructorInHierarchy(TClassifier clazz) {
		TMethod ownedCtor = null;
		if (clazz instanceof TClass) {
			ownedCtor = clazz.getOwnedCtor();
		}

		if (ownedCtor != null) {
			return ownedCtor;
		} else {
			Type superType = null;
			if (clazz instanceof TClass) {
				ParameterizedTypeRef superClassRef = ((TClass) clazz).getSuperClassRef();
				superType = superClassRef == null ? null : superClassRef.getDeclaredType();
			}

			if (superType instanceof TClassifier) {
				return getNearestConstructorInHierarchy((TClassifier) superType);
			}
		}
		return null;
	}

	private boolean isConsumedFromInterface(N4MemberDeclaration memberDecl) {
		return getState().info.isConsumedFromInterface(memberDecl);
	}

	// TODO GH-2153 use reusable utility method for computing actual accessibility
	private boolean isPublic(N4MemberDeclaration memberDecl) {
		// no need to bother with default accessibility, here, because 'public' is never the default
		// (not ideal; would be better if the utility method handled default accessibility)
		MemberAccessModifier accessModifier = ModifierUtils.convertToMemberAccessModifier(
				memberDecl.getDeclaredModifiers(),
				memberDecl.getAnnotations());
		return accessModifier == MemberAccessModifier.PUBLIC;
	}

	private static <T> int insertAt(List<T> list, int index, T element) {
		list.add(index, element);
		return index + 1;
	}

	private static <T> int insertAt(List<T> list, int index, Collection<? extends T> elements) {
		list.addAll(index, elements);
		return index + elements.size();
	}
}
