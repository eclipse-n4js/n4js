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

import com.google.common.collect.ArrayListMultimap
import com.google.inject.Inject
import java.util.List
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.AnnotationDefinition.RetentionPolicy
import org.eclipse.n4js.N4JSLanguageConstants
import org.eclipse.n4js.n4JS.AnnotableElement
import org.eclipse.n4js.n4JS.ArrayLiteral
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FieldAccessor
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor
import org.eclipse.n4js.n4JS.GetterDeclaration
import org.eclipse.n4js.n4JS.ModifiableElement
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4ClassDefinition
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.N4Modifier
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.n4JS.N4TypeDeclaration
import org.eclipse.n4js.n4JS.NewExpression
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.n4JS.SetterDeclaration
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.n4JS.TypeDefiningElement
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableStatementKeyword
import org.eclipse.n4js.n4JS.VersionedElement
import org.eclipse.n4js.n4idl.N4IDLGlobals
import org.eclipse.n4js.transpiler.TransformationAssistant
import org.eclipse.n4js.transpiler.TranspilerBuilderBlocks
import org.eclipse.n4js.transpiler.assistants.TypeAssistant
import org.eclipse.n4js.transpiler.es.transform.ClassDeclarationTransformation
import org.eclipse.n4js.transpiler.es.transform.EnumDeclarationTransformation
import org.eclipse.n4js.transpiler.es.transform.InterfaceDeclarationTransformation
import org.eclipse.n4js.transpiler.im.DelegatingMember
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.n4js.ts.types.TAnnotableElement
import org.eclipse.n4js.ts.types.TAnnotation
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TN4Classifier
import org.eclipse.n4js.ts.types.TObjectPrototype
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.ts.types.util.SuperInterfacesIterable
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.utils.ResourceNameComputer
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.xtext.util.Strings

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*
import static org.eclipse.n4js.transpiler.utils.TranspilerUtils.*

import static extension org.eclipse.n4js.ts.utils.TypeUtils.*
import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * An {@link TransformationAssistant assistant} to create call expressions for invoking <code>$makeClass</code>,
 * <code>$makeInterface</code>, and such. Used by
 * <ul>
 * <li>{@link ClassDeclarationTransformation},
 * <li>{@link InterfaceDeclarationTransformation},
 * <li>{@link EnumDeclarationTransformation}
 * </ul>
 */
class BootstrapCallAssistant extends TransformationAssistant {

	@Inject private DelegationAssistant delegationAssistant;
	@Inject private TypeAssistant typeAssistant;
	@Inject private ResourceNameComputer resourceNameComputer;
	@Inject private JavaScriptVariantHelper jsVariantHelper;

	/**
	 * Create a <code>$makeClass</code> call, including all its required arguments.
	 * <pre>
	 * $makeClass(C, Object,
	 *     {
	 *         m: {
	 *             value: function m___n4(){}
	 *         }
	 *     },
	 *     {},
	 *     function(instanceProto, staticProto) {}
	 * );
	 * </pre>
	 */
	def public ExpressionStatement createMakeClassCall(N4ClassDeclaration classDecl, SymbolTableEntry superClassSTE) {

		return _ExprStmnt(
			_CallExpr => [
				target = _IdentRef(steFor_$makeClass);
				arguments += #[
					_IdentRef(findSymbolTableEntryForElement(classDecl, false)),
					__NSSafe_IdentRef(superClassSTE),
					createDirectlyImplementedOrExtendedInterfacesArgument(classDecl),
					createMemberDefinitions(classDecl, false),
					createMemberDefinitions(classDecl, true),
					createN4TypeMetaInfoFactoryFunction(classDecl, superClassSTE)
				].map[_Argument(it)];
			]
		);
	}

	/**
	 * Unfortunately, <code>$makeClass</code> and <code>$makeInterface</code> are inconsistent. Member definitions are
	 * not passed to <code>$makeInterface</code>, but are instead processed by the generated code directly. This
	 * processing code is created here.
	 * <p>
	 * TODO align $makeInterface with $makeClass
	 */
	def public Statement[] createInterfaceMemberDefinitionSection(N4InterfaceDeclaration ifcDecl) {

		val result = <Statement>newArrayList

		val ifcSTE = findSymbolTableEntryForElement(ifcDecl, true);
		val $methodsSTE = steFor_$methods;
		val objectSTE = getSymbolTableEntryOriginal(state.G.objectType, true);
		val definePropertiesSTE = getSymbolTableEntryForMember(state.G.objectType, "defineProperties", false, true, true);

		// part #1: default methods/accessors

		// I.$methods = {
		//     m: {
		//         value: function m___n4() {}
		//     }
		// };

		result += _ExprStmnt(_AssignmentExpr() => [
			lhs = _PropertyAccessExpr(ifcSTE, $methodsSTE);
			rhs = createMemberDefinitions(ifcDecl, false);
		]);

		// part #2: static methods/accessors

		// Object.defineProperties(I, staticMemberDefinitions);

		val staticMemberDefinitions = createMemberDefinitions(ifcDecl, true);
		if(!staticMemberDefinitions.propertyAssignments.empty) {
			result += _ExprStmnt(_CallExpr(
				_PropertyAccessExpr(objectSTE, definePropertiesSTE),
				_IdentRef(ifcSTE),
				staticMemberDefinitions
			));
		}

		return result;
	}

	/**
	 * Create a <code>$makeInterface</code> call, including all its required arguments.
	 * <pre>
	 * $makeInterface(I,
	 *     function(instanceProto, staticProto) {}
	 * );
	 * </pre>
	 */
	def public ExpressionStatement createMakeInterfaceCall(N4InterfaceDeclaration ifcDecl) {
		val extendedInterfaces = createDirectlyImplementedOrExtendedInterfacesArgument(ifcDecl);
		val extendedInterfacesFn = if(!extendedInterfaces.elements.empty) {
			_ArrowFunc(false, #[], extendedInterfaces)
		} else {
			undefinedRef()
		};
		return _ExprStmnt(
			_CallExpr => [
				target = _IdentRef( steFor_$makeInterface );
				arguments += #[
					_IdentRef(findSymbolTableEntryForElement(ifcDecl, false)),
					extendedInterfacesFn,
					createN4TypeMetaInfoFactoryFunction(ifcDecl, null)
				].map[_Argument(it)];
			]
		);
	}

	/**
	 * Create a <code>$makeEnum</code> call, including all its required arguments.
	 * <pre>
	 * $makeEnum(E, false,
	 *     [
	 *         ['Literal0', 'Value0'],
	 *         ['Literal1', 'Value1']
	 *     ],
	 *     function(instanceProto, staticProto) {}
	 * );
	 * </pre>
	 */
	def public ExpressionStatement createMakeEnumCall(N4EnumDeclaration enumDecl) {
		val isStringBased = AnnotationDefinition.STRING_BASED.hasAnnotation(enumDecl);
		if(isStringBased) {
			throw new IllegalArgumentException("must not create $makeEnum() call for @StringBased enums (they are no longer represented in output code)");
		}
		val enumLiteralArray = _ArrLit();
		for(literal : enumDecl.literals) {
			enumLiteralArray.elements += _ArrayElement(_ArrLit(
				_StringLiteral(literal.name),
				_StringLiteral(literal.value ?: literal.name)
			));
		}

		return _ExprStmnt(
			_CallExpr => [
				target = _IdentRef(steFor_$makeEnum);
				arguments += #[
					_IdentRef(findSymbolTableEntryForElement(enumDecl, false)),
					enumLiteralArray,
					createN4TypeMetaInfoFactoryFunction(enumDecl, null)
				].map[_Argument(it)];
			]
		);
	}

	def private ObjectLiteral createMemberDefinitions(N4ClassifierDeclaration classDecl, boolean _static) {
		// choose which members to include
		var membersToProcess = classDecl.ownedMembers.filter[static===_static && !isAbstract && !isConstructor];
		if(classDecl instanceof N4InterfaceDeclaration) {
			// for interfaces: include only methods/accessors with a body
			membersToProcess = membersToProcess.filter[
				(it instanceof FunctionOrFieldAccessor && (it as FunctionOrFieldAccessor).body!==null)
				|| it instanceof DelegatingMember && !(it as DelegatingMember).isDelegationTargetIsAbstract
			];
		}

		// collect members per kind
		val fields = newArrayList;
		val accessors = ArrayListMultimap.create();
		val methods = newArrayList;
		for(m : membersToProcess) {
			switch(m) {
				N4FieldDeclaration: fields += m
				FieldAccessor: accessors.put(m.name, m)
				N4MethodDeclaration: methods += m
				default: throw new IllegalStateException("unsupported type of member: "+m.eClass.name)
			};
		}
		
		// special static field wrt migrations for versioned classifiers in N4IDL
		val staticMigrationsField = if (_static
			&& classDecl instanceof VersionedElement 
			&& (classDecl as VersionedElement).hasDeclaredVersion) {
			 #[createEmptyObjectMemberForName(N4IDLGlobals.MIGRATIONS_STATIC_FIELD)]
		} else {
			#[]
		}

		return _ObjLit(
			(
				methods.map[createMemberDefinitionForMethod]
				+ accessors.keys.toSet.map[accName | createMemberDefinitionForAccessor(accessors.get(accName))]
				+ fields.map[createMemberDefinitionForField]
				+ staticMigrationsField
			).filterNull // note: #createMemberDefinitionForXYZ() is allowed to return 'null' if member should be ignored
		);
	}
	
	def private PropertyNameValuePair createEmptyObjectMemberForName(String name) {
		// field: {
        //     value: {},
        //     false: true,
        // 	   configurable: false,
        // 	   enumerable: false
        // }
		return _PropertyNameValuePair(
			name,
			_ObjLit(
				"value" -> TranspilerBuilderBlocks._ObjLit, // empty object literal
				"writable" -> _TRUE,
				"configurable" -> _FALSE,
				"enumerable" -> _FALSE
			)
		);
	}

	def private PropertyNameValuePair createMemberDefinitionForField(N4FieldDeclaration fieldDecl) {
        // field: {
        //     value: undefined,
        //     writable: true
        // }
		return _PropertyNameValuePair(
			fieldDecl.name,
			_ObjLit(
				// note: use 'undefined' here even if initializer expression exists (field be initialized elsewhere)
				"value" -> undefinedRef(),
				"writable" -> _TRUE
			)
		);
	}
	def private PropertyNameValuePair createMemberDefinitionForAccessor(List<FieldAccessor> accDecls) {
		// name: {
		//     get: function getName() {return null;}
		// }
		// OR
		// name: {
		//     set: function setName(p) {}
		// }
		// OR
		// name: {
		//     get: function getName() {return null;}
		//     set: function setName(p) {}
		// }
		val getter = accDecls.filter(GetterDeclaration).head;
		val setter = accDecls.filter(SetterDeclaration).head;
		if(getter===null && setter===null)
			throw new IllegalArgumentException("must provide a getter, a setter or both");
		val name = getter?.name ?: setter.name;
		return _PropertyNameValuePair(
			name,
			_ObjLit=>[
				if(getter!==null) {
					propertyAssignments += _PropertyNameValuePair("get",
						if(getter instanceof DelegatingMember) {
							delegationAssistant.createDelegationCode(getter)
						} else {
							createMemberFunction(getter)
						}
					)
				}
				if(setter!==null) {
					propertyAssignments += _PropertyNameValuePair("set",
						if(setter instanceof DelegatingMember) {
							delegationAssistant.createDelegationCode(setter)
						} else {
							createMemberFunction(setter)
						}
					)
				}
			]
		);
	}
	def private PropertyNameValuePair createMemberDefinitionForMethod(N4MethodDeclaration methDecl) {
		// m: {
		//     value: function m___n4() {}
		// }
		return _PropertyNameValuePair(
			methDecl.name,
			_ObjLit(
				_PropertyNameValuePair("value",
					if(methDecl instanceof DelegatingMember) {
						delegationAssistant.createDelegationCode(methDecl)
					} else {
						createMemberFunction(methDecl)
					}
				)
			)
		);
	}

	def private FunctionExpression createMemberFunction(FunctionOrFieldAccessor template) {
		val result = _FunExpr(template.async);
		result.name = getMemberFunctionName(template);
		result.fpars += switch(template) {
			FunctionDefinition: template.fpars // reuse existing fpars!
			SetterDeclaration: #[ template.fpar ] // reuse existing fpar!
			default: #[]
		};
		result._lok = template._lok; // reuse existing LocalArgumentsVariable (if existent)
		result.body = template.body; // reuse existing body!
		result.generator = if(template instanceof FunctionDefinition) template.generator else false;
		if( ! template.annotations.isEmpty  ) {
			result.annotationList = _ExprAnnoList( template.annotations ) // reuse existing annotations
		}
		return result;
	}

	// TODO avoid need of a function expression for creating N4Type meta information
	def private FunctionExpression createN4TypeMetaInfoFactoryFunction(N4TypeDeclaration typeDecl, SymbolTableEntry superClassSTE) {
		// function(instanceProto, staticProto) {
		//     ...
		// }

		val varDeclMetaClass = createMetaClassVariable(typeDecl, superClassSTE);

		return _FunExpr(false, null, #[_Fpar("instanceProto"), _Fpar("staticProto")], _Block()=>[
				statements += #[
					_VariableStatement(varDeclMetaClass),
					_SnippetAsStmnt("return metaClass;")
				]
			]
		);
	}

def public N4GetterDeclaration createN4TypeGetter(N4TypeDeclaration typeDecl, SymbolTableEntry superClassSTE) {
	val symbolObjectSTE = getSymbolTableEntryOriginal(state.G.symbolObjectType, true);
	val forSTE = getSymbolTableEntryForMember(state.G.symbolObjectType, "for", false, true, true);
	val hasOwnPropertySTE = getSymbolTableEntryForMember(state.G.objectType, "hasOwnProperty", false, false, true);
	
	val $symVarDecl = _VariableDeclaration("$sym", _CallExpr(_PropertyAccessExpr(symbolObjectSTE, forSTE), _StringLiteral("org.eclipse.n4js/reflectionInfo")));
	val $symSTE = findSymbolTableEntryForElement($symVarDecl, true);

	return _N4GetterDecl(
		_LiteralOrComputedPropertyName("n4type"), // FIXME use a constant
		_Block(
			// const $sym = Symbol.for('org.eclipse.n4js/reflectionInfo');
			_VariableStatement(VariableStatementKeyword.CONST,
				$symVarDecl
			),
			// if (this.hasOwnProperty($sym)) {
			//     return this[$sym];
			// }
			_IfStmnt(
				_CallExpr(
					_PropertyAccessExpr(
						_ThisLiteral,
						hasOwnPropertySTE
					),
					_IdentRef($symSTE)
				),
				_ReturnStmnt(
					_IndexAccessExpr(_ThisLiteral, _IdentRef($symSTE))
				)
			),
			// return this[$sym] = new N4Class() { ...
			_VariableStatement(VariableStatementKeyword.CONST,
				_VariableDeclaration("instanceProto", _Snippet("this.prototype")), // FIXME remove!
				_VariableDeclaration("staticProto", _ThisLiteral) // FIXME remove!
			),
			_ReturnStmnt(
				_AssignmentExpr(
					_IndexAccessExpr(_ThisLiteral, _IdentRef($symSTE)),
					createMetaClassVariable(typeDecl, superClassSTE).expression
				)
			)
		)
	) => [
		declaredModifiers += N4Modifier.STATIC
	];
}

	def private VariableDeclaration createMetaClassVariable(N4TypeDeclaration typeDecl, SymbolTableEntry superClassSTE) {
		// var metaClass = new N4Class({
		//     name: 'A',
		//     origin: 'IDE1920',
		//     fqn: 'A.A',
		//     n4superType: N4Object.n4type, // 'undefined' for interfaces and enums
		//     allImplementedInterfaces: [],
		//     ownedMembers : [],
		//     consumedMembers : [],
		//     annotations : []
		// });

		val typeSTE = findSymbolTableEntryForElement(typeDecl, true);
		val type = state.info.getOriginalDefinedType(typeDecl);

		// prepare n4superType
		val n4superType = createN4SuperTypeRef(typeDecl, superClassSTE);

		// prepare allImplementedInterfaces
		val Iterable<TInterface> allImplementedInterfaces = if(type instanceof TClassifier) {
			SuperInterfacesIterable.of(type).filter[!isStructurallyTyped]
		} else {
			emptyList()
		};

		// prepare ownedMembers
		val Iterable<N4MemberDeclaration> ownedMembers = if(typeDecl instanceof N4ClassifierDeclaration) {
			typeDecl.ownedMembers.filter[ ! state.info.isConsumedFromInterface(it)]
		} else {
			#[]
		};

		// prepare consumedMembers
		val Iterable<N4MemberDeclaration> consumedMembers = if(typeDecl instanceof N4ClassifierDeclaration) {
			typeDecl.ownedMembers.filter[ state.info.isConsumedFromInterface(it)]
		} else {
			#[]
		};

		// put everything together
		val metaClassSTE = switch(typeDecl) {
			N4ClassDefinition: steFor_N4Class
			N4InterfaceDeclaration: steFor_N4Interface
			N4EnumDeclaration: steFor_N4EnumType
			default: throw new IllegalArgumentException("cannot handle declarations of type " + typeDecl.eClass.name)
		};


		val origin = resourceNameComputer.generateProjectDescriptor(state.resource.URI);
		val fqn = resourceNameComputer.getFullyQualifiedTypeName(type);

		return _VariableDeclaration("metaClass")=>[
			expression = _NewExpr(_IdentRef(metaClassSTE), _ObjLit(
				"name" -> _StringLiteralForSTE(typeSTE),
				"origin" -> _StringLiteral(origin),
				"fqn" -> _StringLiteral(fqn),
				"n4superType" -> n4superType,
				"allImplementedInterfaces" -> _ArrLit(
					allImplementedInterfaces.map[resourceNameComputer.getFullyQualifiedTypeName(it)].map[_StringLiteral(it)]
				),
				"ownedMembers" -> _ArrLit(
					ownedMembers.map[createMemberDescriptor]
				),
				"consumedMembers" -> _ArrLit(
					consumedMembers.map[createMemberDescriptor]
				),
				"annotations" -> _ArrLit(createRuntimeAnnotations(typeDecl))
			));
		];
	}

	def private ArrayLiteral createDirectlyImplementedOrExtendedInterfacesArgument(N4ClassifierDeclaration typeDecl) {
		val interfaces = typeAssistant.getSuperInterfacesSTEs(typeDecl);

		// the return value of this method is intended for default method patching; for this purpose, we have to
		// filter out some of the directly implemented interfaces:
		val directlyImplementedInterfacesFiltered = interfaces.filter[ifcSTE|
			val tIfc = ifcSTE.originalTarget;
			if(tIfc instanceof TInterface) {
				return !tIfc.builtIn // built-in types are not defined in Api/Impl projects -> no patching required
					&& !(tIfc.inN4JSD && !AnnotationDefinition.N4JS.hasAnnotation(tIfc)) // interface in .n4jsd file only patched in if marked @N4JS
			}
			return false;
		];

		return _ArrLit( directlyImplementedInterfacesFiltered.map[ _IdentRef(it) ] );
	}

	/**
	 * Creates the member descriptor for the n4type meta-information. Don't confuse with the member definition created
	 * by <code>#createMemberDefinitionForXYZ()</code> methods.
	 */
	def private Expression createMemberDescriptor(N4MemberDeclaration memberDecl) {
		val memberSTE = findSymbolTableEntryForElement(memberDecl, true);
		val n4MemberClassSTE = switch(memberDecl) {
			N4MethodDeclaration: steFor_N4Method
			FieldAccessor:  steFor_N4Accessor
			N4FieldDeclaration:  steFor_N4DataField
			default:  steFor_N4Member
		}

		return _NewExpr(_IdentRef(n4MemberClassSTE), _ObjLit(
			"name" -> _StringLiteralForSTE(memberSTE),
			if(memberDecl instanceof FieldAccessor) {
				"getter" -> _BooleanLiteral(memberDecl instanceof GetterDeclaration)
			},
			"isStatic" -> _BooleanLiteral(memberDecl.static),
			if(memberDecl instanceof N4MethodDeclaration) {
				val memberName = memberDecl.name;
				val memberIsSymbol = memberName!==null && memberName.startsWith(N4JSLanguageUtils.SYMBOL_IDENTIFIER_PREFIX);
				val memberExpr = if(!memberIsSymbol) {
					_StringLiteralForSTE(memberSTE)
				} else {
					typeAssistant.getMemberNameAsSymbol(memberName)
				};
				if(memberDecl.static) {
					"jsFunction" -> _IndexAccessExpr(_Snippet("staticProto"), memberExpr)
				} else {
					"jsFunction" -> _IndexAccessExpr(_Snippet("instanceProto"), memberExpr)
				}
			},
			"annotations" -> _ArrLit(createRuntimeAnnotations(memberDecl))
		));
	}

	def private NewExpression[] createRuntimeAnnotations(AnnotableElement annElem) {
		val tAnnElem = switch(annElem) {
			DelegatingMember: annElem.delegationTarget.originalTarget as TAnnotableElement
			N4TypeDeclaration: state.info.getOriginalDefinedType(annElem)
			N4MemberDeclaration: state.info.getOriginalDefinedMember(annElem)
			TypeDefiningElement: {
				// going via original AST as a fall back
				val original = state.tracer.getOriginalASTNode(annElem);
				if(original instanceof TypeDefiningElement) {
					original.definedType
				}
			}
		}
		return createRuntimeAnnotations(tAnnElem);
	}

	def private NewExpression[] createRuntimeAnnotations(TAnnotableElement tAnnElem) {
		if(tAnnElem===null) {
			return #[]; // note that this covers the case of a N4ClassifierDeclaration without a type in the TModule
		}
		val runtimeAnnotations = tAnnElem.annotations.filter[
			val retention = AnnotationDefinition.find(it.name).retention;
			return retention===RetentionPolicy.RUNTIME || retention===RetentionPolicy.RUNTIME_TYPEFIELD;
		];
		return runtimeAnnotations.map[createRuntimeAnnotation];
	}

	def private NewExpression createRuntimeAnnotation(TAnnotation ann) {
		// new N4Annotation({name : 'name', details :[ 'arg1', 'arg2' ]})
		val n4AnnotationSTE =  steFor_N4Annotation;
		_NewExpr(_IdentRef(n4AnnotationSTE), _ObjLit(
			"name" -> _StringLiteral(ann.name),
			"details" -> _ArrLit(
				ann.args.map[argAsString].map[_StringLiteral(it)]
			)
		));
	}

	/**
	 * Create reference to n4type meta-object of the super type of given typeDefinition or 'undefined' if unavailable.
	 */
	def private Expression createN4SuperTypeRef(N4TypeDeclaration typeDecl, SymbolTableEntry superClassSTE) {
		if (typeDecl instanceof N4ClassDeclaration) {
			// NOTE: this is a simple but still good example why it is not easy to avoid going via the TModule
			// we could use superClassSTE here, but then we would have to duplicate logic in #getDeclaredOrImplicitSuperType()
			val type = state.info.getOriginalDefinedType(typeDecl);
			val superType = state.G.getDeclaredOrImplicitSuperType(type);
			if(superType === state.G.n4ObjectType) {
				return _Snippet("N4Object.n4type");
			}
			else if(superType instanceof TClass) {
				if (superType.external && !AnnotationDefinition.N4JS.hasAnnotation(superType)) {
					return undefinedRef(); // external classes do not have reflective information
				}
				val superTypeSTE = getSymbolTableEntryOriginal(superType, true);
				val n4typeSTE = getSymbolTableEntryForMember(state.G.n4ObjectType, "n4type", false, true, true);
				return __NSSafe_PropertyAccessExpr(superTypeSTE, n4typeSTE);
			}
			else if(superType instanceof TObjectPrototype) {
				// super type is a TObjectPrototype, i.e. built-in type like Object or Error
				// -> no 'n4superType' in these cases
				return undefinedRef();
			}
		}
		return undefinedRef(); // applies to interfaces and enums
	}

	// ################################################################################################################
	// UTILITY STUFF

	private def boolean isStructurallyTyped(TN4Classifier n4Classifier) {
		val ts = n4Classifier.typingStrategy;
		return ts===TypingStrategy.STRUCTURAL || ts===TypingStrategy.STRUCTURAL_FIELDS;
	}

	/**
	 * In the output code, methods and field accessors (not fields) are represented as function expressions.
	 * This method returns the name of this internal function expression for the given member.
	 */
	def private String getMemberFunctionName(FunctionOrFieldAccessor m) {
		return switch(m) {
			N4GetterDeclaration: getMemberFunctionName("get" + Strings.toFirstUpper(m.getName()))
			N4SetterDeclaration: getMemberFunctionName("set" + Strings.toFirstUpper(m.getName()))
			N4MethodDeclaration: getMemberFunctionName(m.getName())
			default:
				throw new IllegalArgumentException("unsupported subclass of N4MemberDeclaration: " + m.eClass.name)
		};
	}

	/**
	 * Same as {@link #getMemberFunctionName(FunctionOrFieldAccessor)}, but operates on raw method name.
	 *
	 * @param name
	 *            raw method name.
	 */
	def private static String getMemberFunctionName(String name) {
		// note: we no longer have to make sure that "name" isn't a keyword or a reserved word, because we now add
		// the METHOD_STACKTRACE_SUFFIX in *all* cases (see below)
		if (!isLegalIdentifier(name)) {
			return sanitizeIdentifierName(name) + N4JSLanguageConstants.METHOD_STACKTRACE_SUFFIX;
		}
		return name + N4JSLanguageConstants.METHOD_STACKTRACE_SUFFIX;
	}

	def private boolean inN4JSD(Type type) {
		return jsVariantHelper.isExternalMode(type);
	}

	def private boolean isAbstract(ModifiableElement element) {
		return element.declaredModifiers.contains(N4Modifier.ABSTRACT);
	}
}
