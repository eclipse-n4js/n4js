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
package org.eclipse.n4js.transpiler.es.assistants

import com.google.inject.Inject
import java.util.Collection
import java.util.LinkedHashSet
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.EqualityOperator
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.ModifierUtils
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.N4Modifier
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.RelationalOperator
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.n4JS.VariableStatementKeyword
import org.eclipse.n4js.transpiler.TransformationAssistant
import org.eclipse.n4js.transpiler.assistants.TypeAssistant
import org.eclipse.n4js.transpiler.es.transform.SuperLiteralTransformation
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.ts.types.MemberAccessModifier
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TObjectPrototype

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*
import static extension org.eclipse.n4js.utils.N4JSLanguageUtils.*

/**
 * Create the constructor function for classes (as a function declaration).
 */
class ClassConstructorAssistant extends TransformationAssistant {

	@Inject private TypeAssistant typeAssistant;


	/**
	 * Create the constructor function for classes (as a function declaration).
	 */
	def public FunctionDeclaration createCtorDecl(N4ClassDeclaration classDecl, SymbolTableEntryOriginal superClassSTE) {

		val funDecl = _FunDecl(classDecl.name);

		// -------------------------------------------------
		// formal parameters
		val ownedCtor = classDecl.ownedCtor;
		if(ownedCtor!==null) {
			// explicitly defined constructor
			funDecl.fpars += ownedCtor.fpars
		} else {
			// no constructor
			// --> create fpars using fpars of nearest constructor in hierarchy as template
			val templateCtor = getNearestConstructorInHierarchy(classDecl);
			if(templateCtor!==null) {
				funDecl.fpars += templateCtor.fpars.map[
					val typeRefIM = copyAlienElement(it.typeRef);
					_Fpar(it.name, it.variadic, typeRefIM, AnnotationDefinition.SPEC.hasAnnotation(it))
				];
			}
		}
		// within the (maybe newly created) fpars of our ctor, search the @Spec fpar
		val specFpar = funDecl.fpars.filter[AnnotationDefinition.SPEC.hasAnnotation(it)].head;

		// -------------------------------------------------
		// body
		val body = _Block();
		funDecl.body = body;

		val isDirectSubclassOfError = superClassSTE?.originalTarget===state.G.errorType;
		val hasExplicitCtor = ownedCtor?.body!==null;
		val superCallIndex = if(hasExplicitCtor) ownedCtor.superCallIndex else -1;
		val explicitSuperCall = if(superCallIndex>=0) ownedCtor.body.statements.get(superCallIndex);
		val hasExplicitSuperCall = superCallIndex>=0;

		// add preamble
		if(hasExplicitSuperCall) {
			// we have an ownedCtor with a body AND it has an explicit super call at index 'superCallIdx'
			// --> add all statements before the super call (but not the super call itself!)
			body.statements += ownedCtor.body.statements.subList(0, superCallIndex);
		}

		// add super call (either the explicit super call from the source code OR a default super call)
		if(isDirectSubclassOfError) {
			// special case: add oddities for sub-classing Error
			body.statements += createSubclassingErrorOddities(classDecl, funDecl.fpars, explicitSuperCall);
			if(hasExplicitSuperCall) {
				ownedCtor.body.statements.remove(0); // discard explicit super call
			}
		} else if(hasExplicitSuperCall) {
			// add explicit super call
			body.statements += ownedCtor.body.statements.head;
		} else {
			// no ownedCtor OR no body OR no explicit super call (and no direct subclass of Error)
			// --> add default super call (if required)
			if(classDecl.superClassRef!==null) {
				body.statements += createDefaultSuperCall(classDecl, superClassSTE, funDecl.fpars);
			}
		}

		// if we are in a spec-constructor: prepare a local variable for the spec-object and
		// ensure it is never 'undefined' or 'null'
		var specObjSTE = null as SymbolTableEntry;
		if (specFpar !== null) {
			// let $specObj = specFpar || {};
			val specFparSTE = findSymbolTableEntryForElement(specFpar, true);
			val specObjVarDecl = _VariableDeclaration("$specObj", _OR(_IdentRef(specFparSTE), _ObjLit));
			body.statements += _VariableStatement(VariableStatementKeyword.CONST, specObjVarDecl);

			specObjSTE = findSymbolTableEntryForElement(specObjVarDecl, true);
		}

		// add initialization code for fields
		body.statements += createFieldInitCode(classDecl, specFpar, specObjSTE);

		// add delegation to field initialization functions of all directly implemented interfaces
		body.statements += createDelegationToFieldInitOfImplementedInterfaces(classDecl, specObjSTE);

		// add main ctor code
		// (the code following the explicit super call OR the entire body of the explicit ctor if
		// no explicit super call given)
		if(hasExplicitCtor) {
			body.statements += ownedCtor.body.statements;
		}

		return funDecl;
	}

	def private Statement[] createFieldInitCode(N4ClassDeclaration classDecl, FormalParameter specFpar, SymbolTableEntry specObjSTE) {
		val allFields = classDecl.ownedFields.filter[!isStatic && !isConsumedFromInterface].toList;
		if(specFpar!==null) {
			// we have a spec-parameter -> we are in a spec-style constructor
			val result = <Statement>newArrayList;
			val structFields = specFpar.declaredTypeRef.structuralMembers;

			// step #1: initialize all fields either with data from the specFpar OR or their initializer expression
			val currFields = <N4FieldDeclaration>newArrayList;
			var currFieldsAreSpecced = false;
			for(field : allFields) {
				val isSpecced = isPublic(field) || structFields.exists[name == field.name];
				if (isSpecced === currFieldsAreSpecced) {
					currFields += field;
				} else {
					result += createFieldInitCodeForSeveralFields(currFields, currFieldsAreSpecced, specObjSTE);
					currFields.clear();
					currFields += field;
					currFieldsAreSpecced = isSpecced;
				}
			}
			result += createFieldInitCodeForSeveralFields(currFields, currFieldsAreSpecced, specObjSTE);

			// step #2: invoke setters with data from specFpar
			// (note: in case of undefined specFpar at runtime, setters should not be invoked, so we wrap in if statement)
			val speccedSetters = classDecl.ownedSetters.filter[!isStatic && declaredModifiers.contains(N4Modifier.PUBLIC)].toList;
			result += speccedSetters.map[createFieldInitCodeForSingleSpeccedSetter(specObjSTE)];

			return result;
		} else {
			// simple: just initialize fields with data from their initializer expression
			return allFields.map[createFieldInitCodeForSingleField];
		}
	}

	def private Statement[] createFieldInitCodeForSeveralFields(Collection<N4FieldDeclaration> fieldDecls, boolean fieldsAreSpecced, SymbolTableEntry specObjSTE) {
		if (fieldDecls.empty) {
			return #[];
		}
		if (!fieldsAreSpecced) {
			return fieldDecls.map[createFieldInitCodeForSingleField];
		}
		if (fieldDecls.size === 1) {
			return #[ fieldDecls.head.createFieldInitCodeForSingleSpeccedField(specObjSTE) ];
		}
		return #[ fieldDecls.createFieldInitCodeForSeveralSpeccedFields(specObjSTE) ];
	}

	def private Statement createFieldInitCodeForSingleField(N4FieldDeclaration fieldDecl) {
		// here we create:
		//
		//     this.fieldName = <INIT_EXPRESSION>;
		// or
		//     this.fieldName = undefined;
		//
		// NOTE: we set the field to 'undefined' even in the second case, because ...
		// 1) it makes a difference when #hasOwnProperty(), etc. is used after the constructor returns,
		// 2) for consistency with the method #createFieldInitCodeForSeveralSpeccedFields(), because with
		//    destructuring as used by that method, the property will always be assigned (even if the
		//    value is 'undefined' and there is no default).
		//
		val fieldSTE = findSymbolTableEntryForElement(fieldDecl, true);
		return _ExprStmnt(_AssignmentExpr(
			_PropertyAccessExpr(_ThisLiteral, fieldSTE),
			if(fieldDecl.expression!==null) {
				fieldDecl.expression // reusing the expression here
			} else {
				undefinedRef()
			}
		));
	}

	def private Statement createFieldInitCodeForSeveralSpeccedFields(Iterable<N4FieldDeclaration> fieldDecls, SymbolTableEntry specObjSTE) {
		// here we create:
		//
		//     ({
		//         fieldName: this.fieldName = <INIT_EXPRESSION>,
		//         ...
		//     } = spec);
		//
		return _ExprStmnt(
			_Parenthesis(
				_AssignmentExpr(
					_ObjLit(
						fieldDecls.map[fieldDecl|
							val fieldSTE = findSymbolTableEntryForElement(fieldDecl, true);
							val thisFieldName = _PropertyAccessExpr(_ThisLiteral, fieldSTE);
							return fieldDecl.name -> if (fieldDecl.hasNonTrivialInitExpression) {
								_AssignmentExpr(thisFieldName, fieldDecl.expression) // FIXME need to copy???
							} else {
								thisFieldName
							};
						]
					),
					_IdentRef(specObjSTE)
				)
			)
		);
	}

	def private Statement createFieldInitCodeForSingleSpeccedField(N4FieldDeclaration fieldDecl, SymbolTableEntry specObjSTE) {
		val fieldSTE = findSymbolTableEntryForElement(fieldDecl, true);
		if (fieldDecl.hasNonTrivialInitExpression) {
			// here we create:
			//
			//     this.fieldName = spec.fieldName === undefined ? <INIT_EXPRESSION> : spec.fieldName;
			//
			// NOTE: don't use something like "'fieldName' in spec" as the condition above, because that would
			// not have the same behavior as destructuring in method #createFieldInitCodeForSeveralSpeccedFields()
			// in case the property is present but has value 'undefined'!
			//
			return _ExprStmnt(_AssignmentExpr(
				_PropertyAccessExpr(_ThisLiteral, fieldSTE),
				// ? :
				_ConditionalExpr(
					// spec.fieldName === undefined
					_EqualityExpr(_PropertyAccessExpr(specObjSTE, fieldSTE), EqualityOperator.SAME, undefinedRef()),
					// <INIT_EXPRESSION>
					if(fieldDecl.expression!==null) {
						copy(fieldDecl.expression) // need to copy expression here, because it will be used again!
					} else {
						undefinedRef()
					},
					// spec.fieldName
					_PropertyAccessExpr(specObjSTE, fieldSTE)
				)
			));
		} else {
			// we have a trivial init-expression ...

			// here we create:
			//
			//     this.fieldName = spec.fieldName;
			//
			return _ExprStmnt(_AssignmentExpr(
				_PropertyAccessExpr(_ThisLiteral, fieldSTE),
				_PropertyAccessExpr(specObjSTE, fieldSTE)
			));
		}
	}

	def private Statement createFieldInitCodeForSingleSpeccedSetter(N4SetterDeclaration setterDecl, SymbolTableEntry specObjSTE) {
		// here we create:
		//
		// 	if ('fieldName' in spec) {
		//		this.fieldName = spec.fieldName;
		// 	}
		//

		val setterSTE = findSymbolTableEntryForElement(setterDecl, true);
		return _IfStmnt(
			_RelationalExpr(
				_StringLiteralForSTE(setterSTE), RelationalOperator.IN, _IdentRef(specObjSTE)
			),
			_ExprStmnt(
				_AssignmentExpr(
					_PropertyAccessExpr(_ThisLiteral, setterSTE),
					_PropertyAccessExpr(specObjSTE, setterSTE)
		)));
	}

	// NOTE: compare this to the super calls generated from SuperLiterals in SuperLiteralTransformation
	def private ExpressionStatement createDefaultSuperCall(N4ClassDeclaration classDecl, SymbolTableEntry superClassSTE,
		FormalParameter[] fpars
	) {
		// S.prototype.constructor.call(this)
		// (with S being the immediate super class)
		//
		// special case if ctor of S ends in a variadic c:
		// S.prototype.constructor.apply(this, [a,b].concat(c));

		val variadicCase = ! fpars.empty && fpars.last.isVariadic;
		val genericMethodName = if(variadicCase) {"apply"} else {"call"};

		val prototypeSTE = getSymbolTableEntryForMember(state.G.objectType, "prototype", false, true, true);
		val constructorSTE = getSymbolTableEntryForMember(state.G.objectType, "constructor", false, false, true);
		val genericCallSTE = getSymbolTableEntryForMember(state.G.functionType, genericMethodName, false, false, true);

		return _ExprStmnt(_CallExpr()=>[
			target = __NSSafe_PropertyAccessExpr(superClassSTE, prototypeSTE, constructorSTE, genericCallSTE);
			arguments += _Argument(_ThisLiteral());
			if( variadicCase) {
				val concatSTE = getSymbolTableEntryForMember(state.G.arrayType, "concat", false, false, true);

				arguments += _Argument(_CallExpr(
					_PropertyAccessExpr(
						_ArrLit(fpars.take(fpars.size-1)
								.map[findSymbolTableEntryForElement(it , true)]
								.map[_IdentRef(it)]) // target of PropAcc
								, concatSTE ), // end PropAcc
					_IdentRef(fpars.last.findSymbolTableEntryForElement(true))
				)); // end CallExpr
			} else {
				arguments += fpars.map[findSymbolTableEntryForElement(it , true)].map[_Argument(_IdentRef(it))];
			}
		]);
	}

	/** To be inserted where the explicit or implicit super call would be located, normally. */
	def private Statement[] createSubclassingErrorOddities(N4ClassDeclaration classDecl, FormalParameter[] fpars, Statement explicitSuperCall) {
		val ErrorSTE = getSymbolTableEntryOriginal(state.G.errorType, true);

		// var err = new Error(<<arguments>>);
		// this.message = err.message;
		// this.name = this.constructor.n4type.name;
		// Object.defineProperty(this, 'stack', { get: function() { return err.stack; }, set: function(value) { err.stack = value; } });

		val firstLine = _VariableStatement(
			_VariableDeclaration("err", _NewExpr(
				_IdentRef(ErrorSTE),
				if(explicitSuperCall!==null) {
					// if we have an explicit super call, then pass its arguments to "new Error()"
					SuperLiteralTransformation.getArgumentsFromExplicitSuperCall(explicitSuperCall)
				} else {
					// otherwise, pass through the parameters of classDecl's ctor (may be the explicit or implicit ctor)
					fpars.map[findSymbolTableEntryForElement(it, true)].map[_IdentRef(it)]
				}
			))
		);
		val remainingLines = _ExprStmnt(_Snippet('''
			this.message = err.message;
			this.name = this.constructor.n4type.name;
			Object.defineProperty(this, 'stack', { get: function() { return err.stack; }, set: function(value) { err.stack = value; } });
		'''));

		return #[firstLine, remainingLines];
	}

	def private Statement[] createDelegationToFieldInitOfImplementedInterfaces(N4ClassDeclaration classDecl, SymbolTableEntry /*nullable*/ specObjSTE ) {

		val implementedIfcSTEs = typeAssistant.getSuperInterfacesSTEs(classDecl).filter [
			// regarding the cast to TInterface: see preconditions of ClassDeclarationTransformation
			// regarding the entire line: generate $fieldInit call only if the interface is neither built-in nor provided by runtime nor external without @N4JS
			!(originalTarget as TInterface).builtInOrProvidedByRuntimeOrExternalWithoutN4JSAnnotation;
		];
		if (implementedIfcSTEs.empty) {
			return #[];
		}

		val LinkedHashSet<String> ownedInstanceDataFieldsSupressMixin = newLinkedHashSet
		ownedInstanceDataFieldsSupressMixin.addAll(classDecl.ownedGetters.filter[!isConsumedFromInterface].map[name])
		ownedInstanceDataFieldsSupressMixin.addAll(classDecl.ownedSetters.filter[!isConsumedFromInterface].map[name])

		val $initFieldsFromInterfacesSTE = steFor_$initFieldsFromInterfaces;

		return #[ _ExprStmnt(
			_CallExpr(
				_IdentRef($initFieldsFromInterfacesSTE),
				_ThisLiteral,
				_ArrLit(implementedIfcSTEs.map[_IdentRef(it)]),
				if (specObjSTE !== null) {
					_IdentRef(specObjSTE)
				} else {
					undefinedRef()
				},
				_ObjLit(
					ownedInstanceDataFieldsSupressMixin.map[
						_PropertyNameValuePair(it, _TRUE)
					]
				)
			)
		)];
	}

	// ################################################################################################################
	// UTILITY STUFF

	def private int getSuperCallIndex(N4MethodDeclaration ownedCtor) {
		val stmnts = ownedCtor?.body?.statements;
		if(stmnts!==null && !stmnts.empty) {
			for(i : 0..<ownedCtor.body.statements.size) {
				val stmnt = stmnts.get(i);
				if(stmnt instanceof ExpressionStatement) {
					val expr = stmnt.expression;
					if(expr instanceof ParameterizedCallExpression) {
						if(state.info.isExplicitSuperCall(expr)) {
							return i;
						}
					}
				}
			}
		}
		return -1;
	}

	def private TMethod getNearestConstructorInHierarchy(N4ClassDeclaration classDecl) {
		val tClass = state.info.getOriginalDefinedType(classDecl);
		return getNearestConstructorInHierarchy(tClass);
	}
	def private TMethod getNearestConstructorInHierarchy(TClassifier clazz) {
		val ownedCtor = if (clazz instanceof TClass) {
			clazz.ownedCtor
		} else if (clazz instanceof TObjectPrototype) {
			clazz.ownedCtor
		};

		if (ownedCtor !== null) {
			return ownedCtor
		} else {
			val superType = if (clazz instanceof TClass) {
				clazz.getSuperClassRef?.declaredType
			} else if (clazz instanceof TObjectPrototype) {
				clazz.superType?.declaredType
			};
			if (superType instanceof TClassifier) {
				return superType.getNearestConstructorInHierarchy;
			}
		}
		return null;
	}

	def private boolean isConsumedFromInterface(N4MemberDeclaration memberDecl) {
		return state.info.isConsumedFromInterface(memberDecl);
	}

	def private boolean isPublic(N4MemberDeclaration memberDecl) {
		// no need to bother with default accessibility, here, because 'public' is never the default
		// (not ideal; would be better if the utility method handled default accessibility)
		val accessModifier = ModifierUtils.convertToMemberAccessModifier(memberDecl.declaredModifiers,
			memberDecl.annotations);
		return accessModifier === MemberAccessModifier.PUBLIC;
	}
}
