/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.typesystem;

import static org.eclipse.n4js.ts.utils.TypeExtensions.ref;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.addThisType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.argumentsTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.arrayType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.booleanTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.bottomTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.functionTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.isNumeric;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.iterableTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.n4EnumTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.nullTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.numberTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.objectTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.stringType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.stringTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.topTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.voidTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.wrap;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.AdditiveExpression;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.AssignmentOperator;
import org.eclipse.n4js.n4JS.AwaitExpression;
import org.eclipse.n4js.n4JS.BinaryBitwiseExpression;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.DestructureUtils;
import org.eclipse.n4js.n4JS.EqualityExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ImportCallExpression;
import org.eclipse.n4js.n4JS.JSXElement;
import org.eclipse.n4js.n4JS.JSXPropertyAttribute;
import org.eclipse.n4js.n4JS.MultiplicativeExpression;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.PostfixExpression;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.PropertySpread;
import org.eclipse.n4js.n4JS.RelationalExpression;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.ShiftExpression;
import org.eclipse.n4js.n4JS.SuperLiteral;
import org.eclipse.n4js.n4JS.TaggedTemplateString;
import org.eclipse.n4js.n4JS.TemplateLiteral;
import org.eclipse.n4js.n4JS.TemplateSegment;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.YieldExpression;
import org.eclipse.n4js.n4JS.util.N4JSSwitch;
import org.eclipse.n4js.n4jsx.ReactHelper;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.utils.PromisifyHelper;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.xtext.EcoreUtil2;

import com.google.inject.Inject;

/* package */ class ExpectedTypeJudgment extends AbstractJudgment {

	@Inject
	private PromisifyHelper promisifyHelper;
	@Inject
	private JavaScriptVariantHelper javaScriptVariantHelper;
	@Inject
	private ReactHelper reactHelper;

	/** See {@link N4JSTypeSystem#expectedType(RuleEnvironment, EObject, Expression)}. */
	public TypeRef apply(RuleEnvironment G, EObject container, Expression expression) {
		return expression != null ? new ExpectedTypeJudgmentSwitch(G, expression).doSwitch(container) : null;
	}

	private final class ExpectedTypeJudgmentSwitch extends N4JSSwitch<TypeRef> {

		private final RuleEnvironment G;
		private final Expression expression;

		private ExpectedTypeJudgmentSwitch(RuleEnvironment G, Expression expression) {
			this.G = G;
			this.expression = expression;
		}

		@Override
		public TypeRef defaultCase(EObject container) {
			return null; // null is the default and means 'no type expectation' (even void is allowed)
		}

		@Override
		public TypeRef caseFormalParameter(FormalParameter fpar) {
			final TypeRef declTypeRef = fpar.getDeclaredTypeRef();
			if (declTypeRef != null) {
				return declTypeRef;
			} else {
				// Other than in expectedTypeOfRightSideInVariableDeclaration,
				// here we need to return the typeRef of the TModule, since it
				// might have been inferred by the PolyProcessor.
				final TFormalParameter tFpar = fpar.getDefinedTypeElement();
				final TypeRef tFparTypeRef = tFpar != null ? tFpar.getTypeRef() : null;
				if (tFparTypeRef != null) {
					return tFparTypeRef;
				}
			}
			return anyTypeRef(G);
		}

		@Override
		public TypeRef caseArgument(Argument argument) {
			final EObject exprPlain = argument.eContainer();
			if (exprPlain instanceof NewExpression) {
				final NewExpression expr = (NewExpression) exprPlain;

				// ############################################################################################################
				// CODE FROM OLD RULE expectedTypeOfArgumentInNewExpression:

				// since the callee is contained in the NewExpression
				// the validator calls this method also passing the callee as argument
				if (!expr.getArguments().contains(argument)) {
					// expected type of the callee

					// callee must be any constructor
					// --> no type available for this
					// --> must be checked as a validation
					return unknown();
				} else {
					// expected type of argument

					// compute ctor
					final TypeRef ctorTypeRefPlain = ts.type(G, expr.getCallee());
					if (!(ctorTypeRefPlain instanceof TypeTypeRef)) {
						return unknown();
					}
					final TypeTypeRef ctorTypeRef = (TypeTypeRef) ctorTypeRefPlain;

					// add type variable mappings based on the type
					// of the instance to be created
					// --> for this, create a ParameterizedTypeRef taking the staticType from
					// the CtorTypeRef and the type arguments from the NewExpression
					final TypeRef typeRefOfInstanceToCreate = typeSystemHelper.createTypeRefFromStaticType(
							G, ctorTypeRef, expr.getTypeArgs().toArray(new TypeArgument[0]));
					final Type typeOfInstanceToCreatePlain = typeRefOfInstanceToCreate.getDeclaredType();
					if (!(typeOfInstanceToCreatePlain instanceof ContainerType<?>)) {
						return unknown();
					}
					final ContainerType<?> typeOfInstanceToCreate = (ContainerType<?>) typeOfInstanceToCreatePlain;
					final RuleEnvironment G2 = wrap(G);
					typeSystemHelper.addSubstitutions(G2, typeRefOfInstanceToCreate);
					addThisType(G2, typeRefOfInstanceToCreate); // required if we refer to a ctor with a parameter of
																// type [~]~this (esp. default ctor)

					final TMethod ctor = containerTypesHelper.fromContext(expr.eResource())
							.findConstructor(typeOfInstanceToCreate);

					final TFormalParameter fpar = ctor != null
							? ctor.getFparForArgIdx(ECollections.indexOf(expr.getArguments(), argument, 0))
							: null;
					if (fpar == null) {
						// consequential error or ignored:
						return unknown();
					} else {
						final TypeRef paramType = fpar.getTypeRef();
						if (paramType == null) {
							return anyTypeRef(G2);
						} else {
							// bind type variables in function type's parameter type
							return ts.substTypeVariables(G2, paramType);
						}
					}
				}
				// ############################################################################################################
			} else if (exprPlain instanceof ParameterizedCallExpression) {
				final ParameterizedCallExpression expr = (ParameterizedCallExpression) exprPlain;

				// ############################################################################################################
				// CODE FROM OLD RULE expectedTypeOfArgumentInCallExpression:

				// since the target is contained in the CallExpression
				// the validator calls this method also passing the target as argument
				// but this rule is intended only for arguments of the call expression
				if (!expr.getArguments().contains(argument)) {
					return unknown();
				}

				final TypeRef targetTypeRef = ts.type(G, expr.getTarget());
				if (targetTypeRef instanceof FunctionTypeExprOrRef) {
					final FunctionTypeExprOrRef F = (FunctionTypeExprOrRef) targetTypeRef;
					final int argIndex = ECollections.indexOf(expr.getArguments(), argument, 0);
					final TFormalParameter fpar = F.getFparForArgIdx(argIndex);
					if (fpar == null) {
						// consequential error or ignored:
						return unknown();
					} else {
						final TypeRef paramTypeRef = fpar.getTypeRef();
						if (paramTypeRef == null) {
							return anyTypeRef(G);
						} else {
							final RuleEnvironment G2 = wrap(G);

							typeSystemHelper.addSubstitutions(G2, expr, F);

							if (expr.getTarget() instanceof SuperLiteral) {
								// required only in case of super(...) referring to a ctor with a parameter of type
								// [~]~this (esp. default ctor)
								// although we must be inside a TClass, implemented robustly in order to minimize errors
								// TODO can/should this be moved to one of the #addSubstitutions() methods?
								final N4ClassDeclaration containingClassDecl = EcoreUtil2.getContainerOfType(expr,
										N4ClassDeclaration.class);
								final Type containingClass = containingClassDecl != null
										? containingClassDecl.getDefinedType()
										: null;
								if (containingClass instanceof TClass) {
									addThisType(G2, ref(containingClass));
									final TypeRef superClassRef = ((TClass) containingClass).getSuperClassRef();
									if (superClassRef != null) {
										typeSystemHelper.addSubstitutions(G2, superClassRef);
										// IDEBUG-262 special handling of spec-style constructor fpars:
										if (paramTypeRef instanceof ThisTypeRefStructural) {
											// needs this-type Binding to parent-class
											addThisType(G2, superClassRef);
										}
									}
								}
							}

							// bind type variables in function type's parameter type
							final TypeRef paramTypeRefSubst = ts.substTypeVariables(G2, paramTypeRef);

							return paramTypeRefSubst;
						}
					}
				} else {
					return unknown();
				}
				// ############################################################################################################
			} else if (exprPlain instanceof ImportCallExpression) {
				final ImportCallExpression expr = (ImportCallExpression) exprPlain;
				final int argIdx = expr.getArguments().indexOf(argument);
				if (argIdx == 0) {
					return stringTypeRef(G);
				} else {
					return unknown();
				}
			} else {
				return unknown();
			}
		}

		@Override
		public TypeRef casePostfixExpression(PostfixExpression e) {
			if (javaScriptVariantHelper.isTypeAware(e)) {
				return numberTypeRef(G);
			}
			return anyTypeRef(G);
		}

		@Override
		public TypeRef caseUnaryExpression(UnaryExpression e) {
			if (javaScriptVariantHelper.isTypeAware(e)) { // e.g. in N4JS
				switch (e.getOp()) {
				case DELETE:
					return TypeUtils.createNonSimplifiedUnionType(anyTypeRef(G), voidTypeRef(G));
				case VOID:
					return TypeUtils.createNonSimplifiedUnionType(anyTypeRef(G), voidTypeRef(G));
				case TYPEOF:
					return TypeUtils.createNonSimplifiedUnionType(anyTypeRef(G), voidTypeRef(G));
				case INC:
					return numberTypeRef(G);
				case DEC:
					return numberTypeRef(G);
				case POS:
					return numberTypeRef(G);
				case NEG:
					return numberTypeRef(G);
				case INV:
					return numberTypeRef(G);
				case NOT:
					return anyTypeRef(G);
				default:
					return anyTypeRef(G);
				}
			} else { // e.g. plain ECMAScript
				switch (e.getOp()) {
				case DELETE:
					return TypeUtils.createNonSimplifiedUnionType(anyTypeRef(G), voidTypeRef(G));
				case VOID:
					return TypeUtils.createNonSimplifiedUnionType(anyTypeRef(G), voidTypeRef(G));
				case TYPEOF:
					return TypeUtils.createNonSimplifiedUnionType(anyTypeRef(G), voidTypeRef(G));
				default:
					return anyTypeRef(G);
				}
			}
		}

		@Override
		public TypeRef caseMultiplicativeExpression(MultiplicativeExpression e) {
			// Note: symbol type is not allowed, this is checked in validation
			return anyTypeRef(G);
		}

		@Override
		public TypeRef caseAdditiveExpression(AdditiveExpression e) {
			// Note: symbol type is not allowed, this is checked in validation
			return anyTypeRef(G);
		}

		@Override
		public TypeRef caseShiftExpression(ShiftExpression e) {
			// Note: symbol type is not allowed, this is checked in validation
			return anyTypeRef(G);
		}

		@Override
		public TypeRef caseRelationalExpression(RelationalExpression e) {
			switch (e.getOp()) {
			case INSTANCEOF:
				if (expression == e.getRhs()) {
					return TypeUtils.createNonSimplifiedUnionType(functionTypeRef(G),
							TypeUtils.createTypeTypeRef(objectTypeRef(G), false),
							TypeUtils.createTypeTypeRef(n4EnumTypeRef(G), false));
				} else {
					return anyTypeRef(G);
				}
			case IN:
				if (expression == e.getRhs()) {
					return objectTypeRef(G);
				} else {
					if (javaScriptVariantHelper.isTypeAware(e)) {
						return TypeUtils.createNonSimplifiedUnionType(numberTypeRef(G), stringTypeRef(G));
					} else {
						return anyTypeRef(G);
					}
				}
			default: // <, <=, >, >=
				if (javaScriptVariantHelper.isTypeAware(e)) {
					// TODO this looks expensive...
					final UnionTypeExpression primsTR = TypeUtils.createNonSimplifiedUnionType(
							numberTypeRef(G), stringTypeRef(G), booleanTypeRef(G));
					final Expression otherSide = expression == e.getLhs() ? e.getRhs() : e.getLhs();
					final TypeRef otherSideTR = ts.type(G, otherSide);
					if (otherSideTR == null) {
						return unknown();
					}
					if (ts.subtype(G, otherSideTR, primsTR).isSuccess()
							&& !ts.subtype(G, otherSideTR, nullTypeRef(G)).isSuccess()) {
						return otherSideTR;
					} else {
						return primsTR;
					}
				} else {
					return anyTypeRef(G);
				}
			}// end switch
		}

		/**
		 * Always returns any. Note that it is not possible to require both sides to have the same type, as e.g., in
		 * {@code Object === any} the concrete types for both sides could be subtypes of {@code Object}.
		 * <p>
		 * However, in validation there should be a warning created if the two types are not compatible, that is, if
		 * neither lhs &lt;: rhs nor rhs &lt;: lhs.
		 */
		@Override
		public TypeRef caseEqualityExpression(EqualityExpression e) {
			return anyTypeRef(G);
		}

		@Override
		public TypeRef caseBinaryBitwiseExpression(BinaryBitwiseExpression e) {
			if (javaScriptVariantHelper.isTypeAware(e)) {
				return numberTypeRef(G);
			} else {
				return anyTypeRef(G);
			}
		}

		@Override
		public TypeRef caseBinaryLogicalExpression(BinaryLogicalExpression e) {
			return anyTypeRef(G);
		}

		@Override
		public TypeRef caseAssignmentExpression(AssignmentExpression expr) {
			final Expression operand = expression;

			if (!javaScriptVariantHelper.isTypeAware(expr)) {
				if (operand == expr.getLhs()) {
					// left-hand side:
					return bottomTypeRef(G); // no expectation
				} else {
					// right-hand side:
					return topTypeRef(G); // no expectation
				}
			} else if (DestructureUtils.isTopOfDestructuringAssignment(expr)) {
				if (operand == expr.getLhs()) {
					// left-hand side:
					return bottomTypeRef(G); // no expectation
				} else {
					// right-hand side:
					// we do have an expectation of Iterable<?> (for array destructuring) and Object (for object
					// destructuring), but this is checked in N4JSDestructureValidator in separate code to better
					// support nesting of destructuring patterns
					return topTypeRef(G); // no expectation
				}
			} else if (expr.getOp() == AssignmentOperator.ASSIGN) {
				if (operand == expr.getLhs()) {
					// left-hand side:
					return bottomTypeRef(G); // no expectation
				} else {
					// right-hand side:
					// right-hand side is expected to be of same type (or subtype) as left-hand side
					return ts.type(G, expr.getLhs()); // note: this gives us the type for write access on LHS
				}
			} else if (expr.getOp() == AssignmentOperator.ADD_ASSIGN) {
				if (operand == expr.getLhs()) {
					// left-hand side:
					// NOTE: we would actually have to return two different types here:
					// 1) the expected type for write access (for storing the result of the add operation)
					// 2) the expected type for read access (to obtaining the first operand of the add operation)
					// Usually these types are identical, but can be different if we have a getter/setter pair. For
					// consistency with the type judgment, we here only return the expected type for write access
					// (case 1 above); case 2 will be handled by a special validation method, see
					// N4JSTypeValidator#checkCompoundAssignmentGetterSetterClashOnLhs(AssignmentExpression)
					return TypeUtils.createNonSimplifiedIntersectionType(numberTypeRef(G), stringTypeRef(G));
				} else {
					// right hand side:
					final TypeRef lhsTypeRef = ts.type(G, expr.getLhs());
					if (lhsTypeRef == null) {
						return unknown();
					}
					if (lhsTypeRef.getDeclaredType() == stringType(G)) {
						return anyTypeRef(G); // assume string concatenation: any type can be turned into a string
					} else if (isNumeric(G, lhsTypeRef.getDeclaredType())) {
						return numberTypeRef(G);
					} else {
						// assume string concatenation, so same as for string above
						return anyTypeRef(G);
					}
				}
			} else {
				// MUL_ASSIGN, DIV_ASSIGN, MOD_ASSIGN: 6.1.13. Multiplicative Expression
				// SUB_ASSIGN, ADD_ASSIGN (of numbers/boolean): 6.1.14 Additive Expression
				// SHL_ASSIGN, SHR_ASSIGN, USHR_ASSIGN: 6.1.15. Bitwise Shift Expression
				// AND_ASSIGN, XOR_ASSIGN, OR_ASSIGN: 6.1.18. Binary Bitwise Expression

				// in principle, we have the same distinction of cases as above, but because the type is 'number' in all
				// cases (LHS, RHS, etc.) it all boils down to:
				return numberTypeRef(G);
			}
		}

		@Override
		public TypeRef caseVariableDeclaration(VariableDeclaration vdecl) {
			// only an explicitly declared type may serve as expected type (inferred types do not introduce a type
			// expectation because they were derived from the initializer expression, etc.)
			final TypeRef declTypeRef = vdecl.getDeclaredTypeRef();
			if (declTypeRef != null) {
				return declTypeRef;
			} else {
				return topTypeRef(G);
			}
		}

		@Override
		public TypeRef caseVariableBinding(VariableBinding binding) {
			// we do have an expectation of Iterable<?> (for array destructuring) and Object (for object destructuring),
			// but this is checked in N4JSDestructureValidator in separate code to better support nesting of
			// destructuring patterns
			return topTypeRef(G);
		}

		@Override
		public TypeRef caseN4FieldDeclaration(N4FieldDeclaration fdecl) {
			final TypeRef declTypeRef = fdecl.getDeclaredTypeRef();
			if (declTypeRef != null) {
				return declTypeRef;
			} else {
				return topTypeRef(G);
			}
		}

		@Override
		public TypeRef casePropertyNameValuePair(PropertyNameValuePair pnvp) {
			final TypeRef declTypeRef = pnvp.getDeclaredTypeRef();
			if (declTypeRef != null) {
				return declTypeRef;
			} else {
				return topTypeRef(G);
			}
		}

		@Override
		public TypeRef casePropertySpread(PropertySpread object) {
			return unknown(); // TODO GH-1337 add support for spread operator
		}

		@Override
		public TypeRef caseArrayElement(ArrayElement arrElem) {
			if (arrElem.isSpread()
					&& !DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(arrElem.eContainer())) {
				// NOTE: string (lower case!) is not a subtype of Iterable<?> and no auto-boxing happening here, so we
				// have to explicitly allow type 'string' (conversely, type 'String' is a subtype of Iterable<?> and
				// need not be mentioned explicitly).
				return TypeUtils.createNonSimplifiedUnionType(
						iterableTypeRef(G, TypeUtils.createWildcard()),
						stringTypeRef(G));
			}
			return null;
		}

		@Override
		public TypeRef caseReturnStatement(ReturnStatement stmnt) {
			return typeSystemHelper.getExpectedTypeOfReturnValueExpression(G, expression);
		}

		@Override
		public TypeRef caseYieldExpression(YieldExpression yieldExpr) {
			final TypeRef exprTypeRef = ts.type(G, expression);
			if (exprTypeRef == null) {
				return unknown();
			}
			return typeSystemHelper.getExpectedTypeOfYieldValueExpression(G, yieldExpr, exprTypeRef);
		}

		@Override
		public TypeRef caseExpressionStatement(ExpressionStatement exprStmnt) {
			if (N4JSASTUtils.getContainingSingleExpressionArrowFunction(expression) != null) {
				// special case: we are in a single-expression arrow function
				// --> so 'expression' is to be treated as if it were the expression of a return statement
				return typeSystemHelper.getExpectedTypeOfReturnValueExpression(G, expression);
			}
			// default case:
			return null; // null means: no type expectation
		}

		@Override
		public TypeRef caseForStatement(ForStatement forStmnt) {
			if (forStmnt.isForOf() && expression == forStmnt.getExpression()) {
				// expected type of the 'of' part in a for...of statement
				// is "Iterable<?>" and *not* "union{Iterable<?>,Iterator<?>}"
				// (see http://www.2ality.com/2013/06/iterators-generators.html:
				// "ECMAScript 6 has a new loop, for-of. That loop works with iterables.
				// Before we can use it with createArrayIterator(), we need to turn the
				// result into an iterable.")

				Wildcard wildThing = TypeRefsFactory.eINSTANCE.createWildcard();

				if (DestructureUtils.isTopOfDestructuringForStatement(forStmnt)) {
					// special case: destructuring pattern in for..of
					// e.g. for(var [a,b] of myListOfArrays) {}
					// -> do nothing (keep wildThing on the default)
				} else {
					// obtain expected element type
					// note:
					// - if the variable is declared in the for..of, ONLY derive an expected upper bound if the var-decl
					// has a declared type
					// - if the variable is declared outside, we always derive an expected upper bound from the variable
					// type (declared or inferred)
					final VariableDeclaration varDeclInFor = // case: for(var x of myList) {}
							!forStmnt.getVarDecl().isEmpty()
									? forStmnt.getVarDecl().get(0) // syntax does not allow more than 1 varDecl here
									: null;
					final VariableDeclaration varDeclOutside = // case: var x; for(x of myList) {}
							forStmnt.getInitExpr() instanceof IdentifierRef
									&& ((IdentifierRef) forStmnt.getInitExpr()).getId() instanceof VariableDeclaration
											? (VariableDeclaration) ((IdentifierRef) forStmnt.getInitExpr()).getId()
											: null;
					if ((varDeclInFor != null && varDeclInFor.getDeclaredTypeRef() != null)
							|| varDeclOutside != null) {
						final VariableDeclaration varDecl = varDeclOutside != null ? varDeclOutside : varDeclInFor;
						final TypeRef varTypeRef = ts.type(G, varDecl);
						if (varTypeRef == null) {
							return unknown();
						}
						wildThing.setDeclaredUpperBound(TypeUtils.copyIfContained(varTypeRef));
					}
				}
				return iterableTypeRef(G, wildThing);
			} else if (forStmnt.isForIn() && expression == forStmnt.getExpression()) {
				// expected type of the 'in' part in a for...in statement: object
				return TypeUtils.createNonSimplifiedUnionType(objectTypeRef(G), stringTypeRef(G), argumentsTypeRef(G));
			} else {
				return null;
			}
		}

		@Override
		public TypeRef caseAwaitExpression(AwaitExpression awaitExpr) {
			if (promisifyHelper.isAutoPromisify(awaitExpr)) {
				// special case: short syntax of
				return null; // no type expectation at all (even void is acceptable)
			} else {
				return anyTypeRef(G);
			}
		}

		@Override
		public TypeRef caseTaggedTemplateString(TaggedTemplateString taggedTemplate) {
			if (expression == taggedTemplate.getTarget()) {
				final TemplateLiteral template = taggedTemplate.getTemplate();
				if (template != null) {
					final List<TypeRef> argumentTypeRefs = new ArrayList<>();
					// first argument is an Array<string> (containing the string segments)
					argumentTypeRefs.add(TypeUtils.createTypeRef(arrayType(G), stringTypeRef(G)));
					// all following arguments are the result of the ${} expression segments
					for (Expression segment : template.getSegments()) {
						if (!(segment instanceof TemplateSegment)) {
							final TypeRef expressionSegmentTypeRef = ts.type(G, segment);
							argumentTypeRefs.add(expressionSegmentTypeRef);
						}
					}
					return TypeUtils.createFunctionTypeExpression(argumentTypeRefs, anyTypeRef(G));
				}
			}
			return null; // null means: no type expectation
		}

		@Override
		public TypeRef caseJSXPropertyAttribute(JSXPropertyAttribute container) {
			final EObject jsxElem = container.eContainer();
			if (jsxElem instanceof JSXElement) {
				final TypeRef propsTypeRef = reactHelper.getPropsType((JSXElement) jsxElem);
				if (propsTypeRef != null) {
					final RuleEnvironment G2 = wrap(G);
					typeSystemHelper.addSubstitutions(G2, propsTypeRef);
					addThisType(G2, propsTypeRef);
					final TypeRef propertyTypeRef = ts.type(G2, container.getProperty());
					if (propertyTypeRef == null) {
						return unknown();
					}
					final TypeRef propertyTypeRefSubst = ts.substTypeVariables(G2, propertyTypeRef);
					return propertyTypeRefSubst;
				}
			}
			return unknown();
		}
	}
}
