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
package org.eclipse.n4js.validation.validators;

import static org.eclipse.n4js.AnnotationDefinition.GENERATE_INJECTOR;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.n4js.validation.IssueCodes.DI_ANN_INJECTOR_MISSING;
import static org.eclipse.n4js.validation.IssueCodes.DI_ANN_INJECTOR_REQUIRED;
import static org.eclipse.n4js.validation.IssueCodes.DI_ANN_MISSING_NEEDED_BINDERS;
import static org.eclipse.n4js.validation.IssueCodes.DI_ANN_MISSING_PROVIDED_BINDERS;
import static org.eclipse.n4js.validation.IssueCodes.DI_NOT_INJECTABLE;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isEmpty;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isNullOrEmpty;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.List;

import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.CastExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.types.NullType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.UndefinedType;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.AllSuperTypesCollector;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.utils.DeclMergingHelper;
import org.eclipse.n4js.utils.ResourceNameComputer;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.inject.Inject;

/**
 * Validations related to call sites targeting N4Injector methods.
 * <p>
 * For other DI-related validations see {@link N4JSDependencyInjectionValidator}
 */
public class N4JSInjectorCallsitesValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	private N4JSTypeSystem ts;

	@Inject
	private ResourceNameComputer resourceNameComputer;

	@Inject
	private DeclMergingHelper declMergingHelper;

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	@Override
	public void register(EValidatorRegistrar registrar) {
		// nop
	}

	/**
	 * Detects callsites targeting either N4Injector.of() or N4Injector.create() and forwards to appropriate validator.
	 */
	@Check
	public void checkCallExpression(ParameterizedCallExpression callExpression) {
		if (null == callExpression) {
			return;
		}
		if (!(callExpression.getTarget() instanceof ParameterizedPropertyAccessExpression)) {
			return; // invalid or not the AST we're looking for
		}
		if (isNullOrEmpty(callExpression.getArguments())) {
			return; // invalid or not the AST we're looking for
		}
		ParameterizedPropertyAccessExpression propAccess = (ParameterizedPropertyAccessExpression) callExpression
				.getTarget();
		if (null == propAccess) {
			return; // invalid or not the AST we're looking for
		}
		if (!(propAccess.getProperty() instanceof TMethod)) {
			return; // invalid or not the AST we're looking for
		}
		TMethod property = (TMethod) propAccess.getProperty();
		if (!(property.eContainer() instanceof TClass)) {
			return; // invalid or not the AST we're looking for
		}
		TClass tclassOfReceiver = (TClass) property.eContainer();
		if (isInjectorType(tclassOfReceiver)) {
			if ("of".equals(property.getName())) {
				internalCheckInjectorOfCallsite(callExpression);
			} else if ("create".equals(property.getName())) {
				internalCheckInjectorCreateCallsite(callExpression);
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
	 * <li>Some of those binders may require injection (to recap, a classifier declaring injected members is said to
	 * require injection).</li>
	 * <li>Some of those binders may have constructor(s) taking params.</li>
	 * <li>The set of binders described above should match the providedBinders.</li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	private void internalCheckInjectorOfCallsite(ParameterizedCallExpression callExpression) {
		// partition arguments into ctorOfDIC, parentDIC, providedBinders
		List<Expression> args = toList(map(callExpression.getArguments(), a -> a.getExpression()));
		Expression ctorOfDICArg = null;
		if (args.get(0) instanceof CastExpression) {
			// TODO why is this needed in connection with tauWithContextAnalysed?
			// see method testProvidersWithCtorInjection in n4js/runtime/n4/di/TestCustomProviderInjection.n4js
			ctorOfDICArg = ((CastExpression) args.get(0)).getExpression();
		} else {
			ctorOfDICArg = args.get(0);
		}
		// (1) the first argument denotes a DIC constructor
		TClass dicTClass = holdsDenotesDICConstructor(ctorOfDICArg);
		if (null == dicTClass) {
			return;
		}
		// all binders used by the injector of interest
		Iterable<TClass> usedBinders = N4JSDependencyInjectionValidator.usedBindersOf(dicTClass);
		Iterable<TClass> bindersForWhichInstancesAreNeeded = filter(usedBinders,
				binder -> N4JSDependencyInjectionValidator.requiresInjection(binder, declMergingHelper)
						|| hasNonEmptyCtor(binder));
		if (args.size() == 1) {
			holdsNoProvidedBindersNeeded(callExpression, bindersForWhichInstancesAreNeeded);
			return;
		}
		Expression parentDIC = args.get(1);
		// (2) the second (optional) argument is an injector
		holdsDenotesInjector(parentDIC);
		if (args.size() == 2) {
			holdsNoProvidedBindersNeeded(callExpression, bindersForWhichInstancesAreNeeded);
			return;
		}
		List<Expression> providedBinders = args.subList(2, args.size());
		// (3) checks for providedBinders
		holdsAllNeededBinderInstancesAreProvided(callExpression, providedBinders, bindersForWhichInstancesAreNeeded);
	}

	/**
	 * Does the argument declare (or inherit) a ctor requiring parameters?
	 */
	private boolean hasNonEmptyCtor(TClass tClass) {
		// note: AllSuperTypesCollector ignores implicit super types and polyfills, but that is ok, because
		// we assume that members of implicit super types and polyfilled members won't have an @Inject
		// TODO use an 'all super types' iterator instead of collector
		return exists(AllSuperTypesCollector.collect(tClass, declMergingHelper),
				t -> exists(t.getOwnedMembers(), ctor -> ctor.isConstructor() && lacksParams((TMethod) ctor)));
	}

	private static boolean lacksParams(TMethod m) {
		return m.getFpars().isEmpty();
	}

	/**
	 * A callsite target N4Injector.of(ctorOfDIC, ...) but lacks providedBinders. If one or more are required (as
	 * dictated by "ctorOfDIC") this method raises an issue positioned at the callsite.
	 *
	 * TODO is it possible for a parent injector to also require provided-binders? should we also issue an error for
	 * them?
	 */
	private void holdsNoProvidedBindersNeeded(ParameterizedCallExpression callExpression,
			Iterable<TClass> bindersForWhichInstancesAreNeeded) {
		if (isEmpty(bindersForWhichInstancesAreNeeded)) {
			return;
		}
		String missing = Strings.join(", ", map(bindersForWhichInstancesAreNeeded, binder -> binder.getTypeAsString()));
		addIssue(callExpression, DI_ANN_MISSING_PROVIDED_BINDERS.toIssueItem(missing));
	}

	/**
	 * Expression must denote a DIC constructor. If so, return the TClass of the DIC. Otherwise return null.
	 */
	private TClass holdsDenotesDICConstructor(Expression ctorOfDICArg) {
		TypeRef ctorOfDICTypeRef = ts.tau(ctorOfDICArg);
		if (ctorOfDICTypeRef instanceof TypeTypeRef) {
			if (((TypeTypeRef) ctorOfDICTypeRef).getTypeArg() instanceof TypeRef) {
				TClass dicTClass = dicTClassOf((TypeRef) ((TypeTypeRef) ctorOfDICTypeRef).getTypeArg());
				if (null != dicTClass) {
					return dicTClass;
				}
			}
		}
		addIssue(ctorOfDICArg, DI_ANN_INJECTOR_REQUIRED.toIssueItem());
		return null;
	}

	/**
	 * In case the argument refers to class marked (at)GenerateInjector, returns its TClass. Otherwise null.
	 */
	private static TClass dicTClassOf(TypeRef ref) {
		TClass injtorClassDecl = N4JSDependencyInjectionValidator.tClassOf(ref);
		if ((null != injtorClassDecl) && GENERATE_INJECTOR.hasAnnotation(injtorClassDecl)) {
			return injtorClassDecl;
		}
		return null;
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
	private void internalCheckInjectorCreateCallsite(ParameterizedCallExpression callExpression) {
		Argument firstArg = head(callExpression.getArguments());
		Expression ctorArg = firstArg == null ? null : firstArg.getExpression();
		TypeRef ctorArgTypeRef = ts.tau(ctorArg);
		if (ctorArgTypeRef instanceof TypeTypeRef) {
			if (N4JSDependencyInjectionValidator.isInjectableType(((TypeTypeRef) ctorArgTypeRef).getTypeArg())) {
				return;
			}
		}
		addIssue(ctorArg, DI_NOT_INJECTABLE.toIssueItem(ctorArgTypeRef.getTypeRefAsString(), ""));
	}

	/**
	 * Is type one of: N4Injector, NullType, or UndefinedType?
	 */
	private boolean isAllowedAsInjectorInstance(Type type) {
		// allowed: N4Injector.of(DIC1, null /*ie validator accepts null injector here*/, b1);
		// allowed: N4Injector.of(DIC1, undefined, b1);
		return isNullOrUndefinedType(type) || isInjectorType(type);
	}

	/**
	 * Is the argument (a non-null, non-undefined) N4Injector?
	 */
	private boolean isInjectorType(Type type) {
		// N4Injector is final, no need to search for sub-types
		if (!(type instanceof TClass)) {
			return false;
		}
		if (!"N4Injector".equals(type.getName())) {
			return false;
		}
		String fqn = resourceNameComputer.getFullyQualifiedTypeName(type);
		return "runtime.n4.N4Injector.N4Injector".equals(fqn);
	}

	private static boolean isNullOrUndefinedType(Type type) {
		return (type instanceof NullType) || (type instanceof UndefinedType);
	}

	/**
	 * Expression must denote at runtime an N4Injector instance, null, or undefined.
	 */
	private void holdsDenotesInjector(Expression expr) {
		TypeRef tr = ts.tau(expr);
		if (isAllowedAsInjectorInstance(tr.getDeclaredType())) {
			return;
		}
		addIssue(expr, DI_ANN_INJECTOR_MISSING.toIssueItem());
	}

	/**
	 * This method checks that instances are provided for the binders that are in use and that moreover:
	 * <ul>
	 * <li>require injection (to recap, a classifier declaring injected members is said to require injection)</li>
	 * <li>or, have constructor(s) taking params.</li>
	 * </ul>
	 */
	private void holdsAllNeededBinderInstancesAreProvided(
			ParameterizedCallExpression callExpression,
			List<Expression> providedBinders,
			Iterable<TClass> bindersForWhichInstancesAreNeeded) {
		RuleEnvironment G = newRuleEnvironment(providedBinders.get(0));
		Iterable<TypeRef> availableTypeRefs = filter(map(providedBinders, expr -> ts.tau(expr)),
				tr -> !isNullOrUndefinedType(tr.getDeclaredType()));
		Iterable<TClass> missingBinders = filter(bindersForWhichInstancesAreNeeded,
				requirement -> !someBinderSatisfies(G, availableTypeRefs, requirement));
		// TODO what if "too many binder instances" are provided? (ie, an instance that's not needed to satisfy any
		// binderTClass)
		if (isEmpty(missingBinders)) {
			return;
		}
		String missing = Strings.join(", ", map(missingBinders, binder -> binder.getTypeAsString()));
		addIssue(callExpression, DI_ANN_MISSING_NEEDED_BINDERS.toIssueItem(missing));
	}

	/**
	 * Does one of the available instances serve as binder for the required binder TClass?
	 */
	private boolean someBinderSatisfies(
			RuleEnvironment G,
			Iterable<? extends TypeRef> availableTypeRefs,
			TClass requirement) {
		return exists(availableTypeRefs,
				availableTR -> ts.subtypeSucceeded(G, availableTR, TypeUtils.createTypeRef(requirement)));
	}

}
