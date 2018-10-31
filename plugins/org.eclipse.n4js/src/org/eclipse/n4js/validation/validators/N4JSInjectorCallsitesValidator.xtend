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
package org.eclipse.n4js.validation.validators

import com.google.inject.Inject
import java.util.List
import org.eclipse.n4js.n4JS.CastExpression
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.types.NullType
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.UndefinedType
import org.eclipse.n4js.ts.types.util.AllSuperTypesCollector
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.utils.ResourceNameComputer
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static org.eclipse.n4js.AnnotationDefinition.*
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*
import static org.eclipse.n4js.validation.IssueCodes.*

/**
 * Validations related to callsites targeting N4Injector methods.
 * <p>
 * For other DI-related validations see {@link N4JSDependencyInjectionValidator}
 */
class N4JSInjectorCallsitesValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	private N4JSTypeSystem ts;

	@Inject
	private ResourceNameComputer resourceNameComputer;

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	override register(EValidatorRegistrar registrar) {
		// nop
	}


	/**
	 * Detects callsites targeting either N4Injector.of() or N4Injector.create()
	 * and forwards to appropriate validator.
	 */
	@Check
	def checkCallExpression(ParameterizedCallExpression callExpression) {
		if (null === callExpression) {
			return;
		}
		if (!(callExpression.target instanceof ParameterizedPropertyAccessExpression)) {
			return; // invalid or not the AST we're looking for
		}
		if (callExpression.arguments.nullOrEmpty) {
			return; // invalid or not the AST we're looking for
		}
		val propAccess = callExpression.target as ParameterizedPropertyAccessExpression;
		if (null === propAccess) {
			return; // invalid or not the AST we're looking for
		}
		if (!(propAccess.property instanceof TMethod)) {
			return; // invalid or not the AST we're looking for
		}
		val property = propAccess.property as TMethod;
		if (!(property.eContainer instanceof TClass)) {
			return; // invalid or not the AST we're looking for
		}
		val tclassOfReceiver = property.eContainer as TClass;
		if (isInjectorType(tclassOfReceiver)) {
			if (property.name == 'of') {
				internalCheckInjectorOfCallsite(callExpression)
			} else if (property.name == 'create') {
				internalCheckInjectorCreateCallsite(callExpression)
			}
		}
	}

	/**
	 * This method validates callsites of the form:
	 * <p/>
	 * <code>
	 * public static N4Injector of(constructor{N4Object} ctorOfDIC, N4Injector? parentDIC, N4Object... providedBinders)
	 * </code>
	 * <p/>
	 * At runtime:
	 * <p/>
	 * <ul>
	 * <li>(1) the first argument denotes a DIC constructor</li>
	 * <li>(2) the second (optional) argument is an injector</li>
	 * <li>(3) lastly, the purpose of providedBinders is as follows:
	 * <ul>
	 * <li>the DIC above in (1) is marked with one or more (at)UseBinder.</li>
	 * <li>Some of those binders may require injection (to recap, a classifier declaring injected members is said to require injection).</li>
	 * <li>Some of those binders may have constructor(s) taking params.</li>
	 * <li>The set of binders described above should match the providedBinders.</li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	private def void internalCheckInjectorOfCallsite(ParameterizedCallExpression callExpression) {
		// partition arguments into ctorOfDIC, parentDIC, providedBinders
		val args = callExpression.arguments.map[expression];
		var Expression ctorOfDICArg = null;
		if (args.get(0) instanceof CastExpression) {
			// TODO why is this needed in connection with tauWithContextAnalysed?
			// see method testProvidersWithCtorInjection in n4js/runtime/n4/di/TestCustomProviderInjection.n4js
			ctorOfDICArg = (args.get(0) as CastExpression).expression
		} else {
			ctorOfDICArg = args.get(0)
		}
		// (1) the first argument denotes a DIC constructor
		val TClass dicTClass = holdsDenotesDICConstructor(ctorOfDICArg)
		if (null === dicTClass) {
			return
		}
		// all binders used by the injector of interest
		val usedBinders = N4JSDependencyInjectionValidator.usedBindersOf(dicTClass)
		val bindersForWhichInstancesAreNeeded = usedBinders.filter[binder|
			N4JSDependencyInjectionValidator.requiresInjection(binder) || hasNonEmptyCtor(binder)
		]
		if (args.size == 1) {
			holdsNoProvidedBindersNeeded(callExpression, bindersForWhichInstancesAreNeeded)
			return
		}
		var Expression parentDIC = args.get(1);
		// (2) the second (optional) argument is an injector
		holdsDenotesInjector(parentDIC)
		if (args.size == 2) {
			holdsNoProvidedBindersNeeded(callExpression, bindersForWhichInstancesAreNeeded)
			return
		}
		var List<Expression> providedBinders = args.subList(2, args.size);
		// (3) checks for providedBinders
		holdsAllNeededBinderInstancesAreProvided(callExpression, providedBinders, bindersForWhichInstancesAreNeeded)
	}

	/**
	 * Does the argument declare (or inherit) a ctor requiring parameters?
	 */
	private static def boolean hasNonEmptyCtor(TClass tClass) {
		// note: AllSuperTypesCollector ignores implicit super types and polyfills, but that is ok, because
		// we assume that members of implicit super types and polyfilled members won't have an @Inject
		// TODO use an 'all super types' iterator instead of collector
		AllSuperTypesCollector.collect(tClass)
		.exists[t|
			t.ownedMembers.exists[ctor|
				ctor.constructor && lacksParams(ctor as TMethod)
			]
		]
	}

	private static def boolean lacksParams(TMethod m) {
		m.fpars.isEmpty
	}

	/**
	 * A callsite target N4Injector.of(ctorOfDIC, ...) but lacks providedBinders.
	 * If one or more are required (as dictated by "ctorOfDIC") this method raises an issue positioned at the callsite.
	 *
	 * TODO is it possible for a parent injector to also require provided-binders? should we also issue an error for them?
	 */
	private def void holdsNoProvidedBindersNeeded(ParameterizedCallExpression callExpression, Iterable<TClass> bindersForWhichInstancesAreNeeded) {
		if (bindersForWhichInstancesAreNeeded.isEmpty) {
			return
		}
		val missing = bindersForWhichInstancesAreNeeded.map[binder| binder.typeAsString].join(", ")
		val msg = getMessageForDI_ANN_MISSING_PROVIDED_BINDERS(missing);
		addIssue(msg, callExpression, DI_ANN_MISSING_PROVIDED_BINDERS);
	}

	/**
	 * Expression must denote a DIC constructor. If so, return the TClass of the DIC. Otherwise return null.
	 */
	private def TClass holdsDenotesDICConstructor(Expression ctorOfDICArg) {
		val ctorOfDICTypeRef = ts.tau(ctorOfDICArg)
		if (ctorOfDICTypeRef instanceof TypeTypeRef) {
			if (ctorOfDICTypeRef.getTypeArg instanceof TypeRef) {
				val dicTClass = dicTClassOf(ctorOfDICTypeRef.getTypeArg as TypeRef)
				if (null !== dicTClass) {
					return dicTClass
				}
			}
		}
		addIssue(getMessageForDI_ANN_INJECTOR_REQUIRED(), ctorOfDICArg, DI_ANN_INJECTOR_REQUIRED);
		return null
	}

	/**
	 * In case the argument refers to class marked (at)GenerateInjector, returns its TClass.
	 * Otherwise null.
	 */
	private static def TClass dicTClassOf(TypeRef ref) {
		val injtorClassDecl = N4JSDependencyInjectionValidator.tClassOf(ref);
		if ((null !== injtorClassDecl) && GENERATE_INJECTOR.hasAnnotation(injtorClassDecl)) {
			return injtorClassDecl
		}
		return null
	}

	/**
	 * This method validates callsites of the form:
	 * <p/>
	 * <code>
	 * public static N4Injector create(type{T} ctor)
	 * </code>
	 * <p/>
	 * Validation: <code>type{T}</code> should be injectable (in particular, it may be an N4Provider).
	 */
	private def void internalCheckInjectorCreateCallsite(ParameterizedCallExpression callExpression) {
		val ctorArg = callExpression.arguments.head?.expression
		val ctorArgTypeRef = ts.tau(ctorArg)
		if (ctorArgTypeRef instanceof TypeTypeRef) {
			if(N4JSDependencyInjectionValidator.isInjectableType(ctorArgTypeRef.getTypeArg)) {
				return
			}
		}
		addIssue(getMessageForDI_NOT_INJECTABLE(ctorArgTypeRef.typeRefAsString, ''), ctorArg, DI_NOT_INJECTABLE);
	}

	/**
	 * Is type one of: N4Injector, NullType, or UndefinedType?
	 */
	private def boolean isAllowedAsInjectorInstance(Type type) {
		// allowed: N4Injector.of(DIC1, null /*ie validator accepts null injector here*/, b1);
		// allowed: N4Injector.of(DIC1, undefined, b1);
		return isNullOrUndefinedType(type) || isInjectorType(type);
	}

	/**
	 * Is the argument (a non-null, non-undefined) N4Injector?
	 */
	private def boolean isInjectorType(Type type) {
		// N4Injector is final, no need to search for sub-types
		if (!(type instanceof TClass)) {
			return false
		}
		if (!(type.name == 'N4Injector')) {
			return false
		}
		val fqn = resourceNameComputer.getFullyQualifiedTypeName(type)
		return (fqn == 'runtime.n4.N4Injector.N4Injector');
	}

	private static def boolean isNullOrUndefinedType(Type type) {
		return (type instanceof NullType) || (type instanceof UndefinedType);
	}

	/**
	 * Expression must denote at runtime an N4Injector instance, null, or undefined.
	 */
	private def void holdsDenotesInjector(Expression expr) {
		val tr = ts.tau(expr)
		if (isAllowedAsInjectorInstance(tr.declaredType)) {
			return
		}
		addIssue(getMessageForDI_ANN_INJECTOR_MISSING(), expr, DI_ANN_INJECTOR_MISSING);
	}

	/**
	 * This method checks that instances are provided for the binders that are in use and that moreover:
	 * <ul>
	 * <li>require injection (to recap, a classifier declaring injected members is said to require injection)</li>
	 * <li>or, have constructor(s) taking params.</li>
	 * </ul>
	 */
	private def void holdsAllNeededBinderInstancesAreProvided(
		ParameterizedCallExpression callExpression,
		List<Expression> providedBinders,
		Iterable<TClass> bindersForWhichInstancesAreNeeded
	) {
		val G = newRuleEnvironment(providedBinders.get(0));
		val availableTypeRefs = providedBinders.map[expr| ts.tau(expr) ].filter[tr| !isNullOrUndefinedType(tr.declaredType) ]
		val missingBinders = bindersForWhichInstancesAreNeeded.filter[requirement|
			!someBinderSatisfies(G, availableTypeRefs, requirement)
		]
		// TODO what if "too many binder instances" are provided? (ie, an instance that's not needed to satisfy any binderTClass)
		if (missingBinders.isEmpty) {
			return
		}
		val missing = missingBinders.map[binder| binder.typeAsString].join(", ")
		val msg = getMessageForDI_ANN_MISSING_NEEDED_BINDERS(missing);
		addIssue(msg, callExpression, DI_ANN_MISSING_NEEDED_BINDERS);
	}

	/**
	 * Does one of the available instances serve as binder for the required binder TClass?
	 */
	private def boolean someBinderSatisfies(
		RuleEnvironment G,
		Iterable<? extends TypeRef> availableTypeRefs,
		TClass requirement
	) {
		availableTypeRefs.exists[availableTR| ts.subtypeSucceeded(G, availableTR, TypeUtils.createTypeRef(requirement))]
	}

}
