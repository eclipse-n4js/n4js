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

import static org.eclipse.n4js.validation.IssueCodes.AST_GLOBAL_JS_NAME_CONFLICT;
import static org.eclipse.n4js.validation.IssueCodes.AST_GLOBAL_NAME_CONFLICT;
import static org.eclipse.n4js.validation.IssueCodes.AST_GLOBAL_NAME_SHADOW_ERR;
import static org.eclipse.n4js.validation.IssueCodes.AST_NAME_DUPLICATE_ERR;
import static org.eclipse.n4js.validation.IssueCodes.AST_NAME_SHADOW_ERR;
import static org.eclipse.n4js.validation.IssueCodes.AST_NAME_SHADOW_WARN;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.flatMap;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isEmpty;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toIterable;
import static org.eclipse.xtext.xbase.lib.StringExtensions.toFirstUpper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.AbstractVariable;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.CatchVariable;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.N4ClassExpression;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.N4TypeDeclaration;
import org.eclipse.n4js.n4JS.N4TypeDefinition;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.PropertySetterDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.SetterDeclaration;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableEnvironmentElement;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.scoping.builtin.GlobalObjectScope;
import org.eclipse.n4js.scoping.builtin.N4Scheme;
import org.eclipse.n4js.scoping.utils.SourceElementExtensions;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueItem;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.n4js.validation.ValidatorMessageHelper;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;
import org.eclipse.xtext.xbase.lib.MapExtensions;

import com.google.common.base.Strings;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import com.google.inject.Inject;

/**
 */
@SuppressWarnings("javadoc")
public class N4JSDeclaredNameValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	ValidatorMessageHelper messageHelper;

	@Inject
	WorkspaceAccess workspaceAccess;

	@Inject
	SourceElementExtensions sourceElementExtensions;

	@Inject
	JavaScriptVariantHelper jsVariantHelper;

	@Inject
	OperationCanceledManager operationCanceledManager;

	public static Set<String> BASE_JS_TYPES = Set.of("Object", "Function", "Array", "String", "Boolean", "Number",
			"Math", "Date", "RegExp", "Error", "JSON");
	public static Set<String> BASE_GLOBAL_NAMES = Set.of("number", "string", "boolean", "any", "pathSelector",
			"i18nKey", "typeName", "N4Object", "N4Class", "N4Enum");

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
	 * Check duplicated names of declared properties in the {@link ObjectLiteral}s. Similar to check of duplicates in
	 * scope.
	 */
	@Check
	public void checkNameConflictsInObjectLiteral(ObjectLiteral objectLiteral) {
		List<PropertyAssignment> pas = toList(
				filter(objectLiteral.getPropertyAssignments(), it -> !Strings.isNullOrEmpty(findName(it))));

		Map<String, List<PropertyAssignment>> map2 = pas.stream().collect(Collectors.groupingBy(it -> findName(it)));

		map2 = MapExtensions.filter(map2, (name, entry) -> entry.size() > 1);

		for (String name : map2.keySet()) {
			List<PropertyAssignment> lstEO = map2.get(name);
			// appearance order
			lstEO.sort(
					(e1, e2) -> Integer.valueOf(NodeModelUtils.getNode(e1).getOffset()).compareTo(
							Integer.valueOf(NodeModelUtils.getNode(e2).getOffset())));

			Iterator<PropertyAssignment> iter = lstEO.listIterator();
			EObject baseEO = iter.next();

			while (iter.hasNext()) {
				EObject dupeEO = iter.next();

				// getter-setter pair
				if ((baseEO instanceof PropertyGetterDeclaration && dupeEO instanceof PropertySetterDeclaration) ||
						(baseEO instanceof PropertySetterDeclaration && dupeEO instanceof PropertyGetterDeclaration)) {
					return;
				}

				IssueItem issueItem = AST_NAME_DUPLICATE_ERR.toIssueItem(
						toFirstUpper(messageHelper.description(dupeEO, name)),
						messageHelper.descriptionWithLine(baseEO, name));
				addIssue(dupeEO, findNameEAttribute(dupeEO), issueItem);
			}
		}
	}

	/**
	 * Constraints 126 (Global Definitions), except polyfills
	 */
	@Check
	public void checkExportableNameConflictsWithBuiltIn(ExportableElement exportableElement) {
		if (exportableElement instanceof AnnotableElement) {
			AnnotableElement ae = (AnnotableElement) exportableElement;
			if (AnnotationDefinition.GLOBAL.hasAnnotation(ae)) {
				if (N4Scheme.isFromResourceWithN4Scheme(ae)) {
					return; // this validation does not apply to built-in types (i.e. builtin_js.n4jsd, etc.)
				}
				if (N4JSLanguageUtils.isNonStaticPolyfill(ae)) {
					return; // of course it is possible to fill predefined types
				}
				String name = getDeclaredName(ae);
				if (name != null) {
					if (BASE_JS_TYPES.contains(name)) {
						N4JSProjectConfigSnapshot project = workspaceAccess.findProjectContaining(ae);
						if (project == null || project.getType() != ProjectType.RUNTIME_ENVIRONMENT) {
							addIssue(ae, AST_GLOBAL_JS_NAME_CONFLICT.toIssueItem(name));
						}
					} else if (BASE_GLOBAL_NAMES.contains(name)) {
						addIssue(ae, AST_GLOBAL_NAME_CONFLICT.toIssueItem(name));
					}
				}
			}
		}
	}

	/**
	 * Check name conflicts in script. For every scope ({@link VariableEnvironmentElement} instance) will - check for
	 * name conflict with members of global object - check for name conflict with implicit function arguments - check
	 * for name conflicts with outer (containing) scope
	 */
	@Check
	public void checkNameConflicts(Script script) {
		ListMultimap<String, TMember> globalNames = getGlobalNames(script.eResource());

		checkNameConflicts(script, new HashSet<>(), globalNames, getCancelIndicator());
	}

	/**
	 * Delegates proper checks for a given scope. After checks are done for current scope, calls itself recursively on
	 * children. Recursive call is passing set of used names in current scope, allowing child scope to perform proper
	 * comparison checks. Only names are passed, actual {@link EObject}s are resolved only if name clash is detected.
	 */
	private void checkNameConflicts(VariableEnvironmentElement vee, Set<String> outerNames,
			ListMultimap<String, TMember> globalNames, CancelIndicator ci) {
		ListMultimap<String, EObject> localNames = getLocalNames(vee);

		checkGlobalNamesConflict(localNames, globalNames, ci);

		checkLocalScopeNamesConflict(vee, localNames, ci);

		// (1) add 'localNames' to 'outerNames'
		// (note: for performance reasons, we change 'outerNames' in place and restore its state after processing the
		// nested VEEs)
		ArrayList<String> localNamesWithConflictToOuterNames = new ArrayList<>(localNames.size());
		ArrayList<String> localNamesAddedToOuterNames = new ArrayList<>(localNames.size());
		for (String localName : localNames.keySet()) {
			boolean wasAdded = outerNames.add(localName);
			if (wasAdded) {
				// 'outerNames' did not contain 'localName' -> no conflict
				// remember that 'localName' has to be removed from 'outerNames' after processing nested VEEs
				localNamesAddedToOuterNames.add(localName);
			} else {
				// 'outerNames' did already contain 'localName' -> conflict!
				localNamesWithConflictToOuterNames.add(localName);
			}
		}

		checkOuterScopesNamesConflict(vee, localNames, localNamesWithConflictToOuterNames, ci);

		// (2) process nested VEEs
		for (VariableEnvironmentElement veeNested : toIterable(getNestedScopes(vee))) {
			operationCanceledManager.checkCanceled(ci);
			checkNameConflicts(veeNested, outerNames, globalNames, ci);
		}

		// (3) restore state of 'outerNames'
		outerNames.removeAll(localNamesAddedToOuterNames);
	}

	/**
	 * checks if provided localNames of given scope are not conflicting with names in global object
	 */
	private void checkGlobalNamesConflict(ListMultimap<String, EObject> localNames,
			ListMultimap<String, TMember> globalNames, CancelIndicator ci) {

		for (Entry<String, Collection<TMember>> globalEntry : globalNames.asMap().entrySet()) {
			operationCanceledManager.checkCanceled(ci);
			String name = globalEntry.getKey();
			if (!"eval".equals(name)) { // already validated by the AST Structure validator
				TMember globalObjectMember = head(globalEntry.getValue());
				List<EObject> localObjects = localNames.get(name);
				for (EObject innerScopeObject : localObjects) {
					IssueItem issueItem = AST_GLOBAL_NAME_SHADOW_ERR.toIssueItem(
							toFirstUpper(messageHelper.description(innerScopeObject, name)),
							messageHelper.description(globalObjectMember, name));
					addIssue(innerScopeObject, findNameEAttribute(innerScopeObject), issueItem);
				}
			}
		}
	}

	/**
	 * check (pre-computed) localNames of the given scope against each other. When conflict is found EObjects that
	 * create conflict are analyzed and if appropriate error marker is issued.
	 */
	private void checkLocalScopeNamesConflict(VariableEnvironmentElement vee, ListMultimap<String, EObject> localNames,
			CancelIndicator ci) {

		if (vee instanceof Block
				&& vee.eContainer() instanceof FunctionOrFieldAccessor
				&& ((FunctionOrFieldAccessor) vee.eContainer()).getBody() == vee) {
			checkLocalScopeNamesConflict_letConstSpecialCase((FunctionOrFieldAccessor) vee.eContainer());
		}

		// search for duplicates in local scope
		if (localNames.keySet().size() < localNames.size()) {

			// found 1 or more duplicate names

			for (Entry<String, Collection<EObject>> entry : filter(localNames.asMap().entrySet(),
					e -> e.getValue().size() > 1)) {
				operationCanceledManager.checkCanceled(ci);
				String name = entry.getKey();
				Collection<EObject> eObjects = entry.getValue();
				if (eObjects.size() > 1) {
					// appearance order
					List<EObject> lstEO = new ArrayList<>(eObjects);
					lstEO.sort(
							(EObject e1, EObject e2) -> {
								ICompositeNode n1 = NodeModelUtils.getNode(e1);
								ICompositeNode n2 = NodeModelUtils.getNode(e2);
								// null-node will happen if artificially enriched AST,
								// for example instance of {@link LocalArgumentsVariable} is encountered.
								return (n1 == null || n2 == null) ? 0 : n1.getOffset() - n2.getOffset();
							});
					ListIterator<EObject> iter = lstEO.listIterator();
					EObject baseEO = iter.next();

					while (iter.hasNext()) {
						EObject dupeEO = iter.next();

						if (baseEO instanceof N4ClassExpression || dupeEO instanceof N4ClassExpression) {
							return;
						}

						// in case of when we duplicate element creating given scope (like function names)
						// then we issue shadowing
						if (baseEO.equals(vee)) {

							if (dupeEO instanceof FormalParameter) {
								IssueItem issueItem = AST_NAME_SHADOW_ERR.toIssueItem(
										toFirstUpper(messageHelper.description(dupeEO, name)),
										messageHelper.description(baseEO, name));
								addIssue(dupeEO, findNameEAttribute(dupeEO), issueItem);
							} else {
								IssueItem issueItem = AST_NAME_SHADOW_ERR.toIssueItem(
										toFirstUpper(messageHelper.description(dupeEO, name)),
										messageHelper.descriptionWithLine(baseEO, name));
								addIssue(dupeEO, findNameEAttribute(dupeEO), issueItem);
							}
							return;
						}

						// otherwise mark duplicates
						if (dupeEO instanceof FormalParameter) {
							IssueItem issueItem = AST_NAME_DUPLICATE_ERR.toIssueItem(
									toFirstUpper(messageHelper.description(dupeEO, name)),
									messageHelper.description(baseEO, name));
							addIssue(dupeEO, findNameEAttribute(dupeEO), issueItem);
						} else if ((dupeEO instanceof NamedImportSpecifier &&
								baseEO instanceof NamedImportSpecifier) ||
								(dupeEO instanceof NamespaceImportSpecifier &&
										baseEO instanceof NamespaceImportSpecifier)) {

							/*
							 * Duplication only between ImportSpecifiers is handled in N4JSImportValidator
							 */
							return;
						} else {
							if (jsVariantHelper.isExternalMode(baseEO)) {
								// allow name sharing only in N4JSD files
								if (!isEqualNameDuplicate(baseEO, dupeEO)) {
									return;
								}
							}

							if (!( // do not create issues for polyfills conflicting with imports, as they might fill
									// them
							dupeEO instanceof N4ClassifierDeclaration && baseEO instanceof ImportSpecifier &&
									N4JSLanguageUtils.isNonStaticPolyfill((N4ClassifierDeclaration) dupeEO)
							// TODO IDE-1735 does this check need to be activated for static polyfills?
							)) {
								IssueItem issueItem = AST_NAME_DUPLICATE_ERR.toIssueItem(
										toFirstUpper(messageHelper.description(dupeEO, name)),
										messageHelper.descriptionWithLine(baseEO, name));
								addIssue(dupeEO, findNameEAttribute(dupeEO), issueItem);
							}
						}
					}
				}
			}
		}
	}

	private boolean isEqualNameDuplicate(EObject baseEO, EObject dupeEO) {
		if (isValueOnly(baseEO) && isHollow(dupeEO)) {
			return false;
		} else if (isValueOnly(dupeEO) && isHollow(baseEO)) {
			return false;
		}
		return true;
	}

	private boolean isValueOnly(EObject eo) {
		EObject eo2 = eo;
		if (eo instanceof NamedImportSpecifier) {
			eo2 = ((NamedImportSpecifier) eo).getImportedElement();
		}

		return (eo2 instanceof TypableElement && N4JSLanguageUtils.isValueOnlyElement((TypableElement) eo2));
	}

	private boolean isHollow(EObject eo) {
		EObject eo2 = eo;
		if (eo instanceof NamedImportSpecifier) {
			eo2 = ((NamedImportSpecifier) eo).getImportedElement();
		}
		return (eo2 instanceof TypableElement && N4JSLanguageUtils.isHollowElement((TypableElement) eo2));
	}

	/**
	 * This method handles a special case related to let/const:
	 *
	 * <pre>
	 * function foo1(param) {
	 *     let param; // <-- ES6 engines will throw error at runtime!
	 * }
	 * function foo2(param) {
	 *     {
	 *         let param; // <-- valid ES6!
	 *     }
	 * }
	 * </pre>
	 *
	 * This method takes care of producing a validation error in the first case above.
	 */
	private void checkLocalScopeNamesConflict_letConstSpecialCase(FunctionOrFieldAccessor fun) {
		Block block = fun.getBody();
		List<FormalParameter> fpars = Collections.emptyList();
		if (fun instanceof FunctionDefinition) {
			fpars = ((FunctionDefinition) fun).getFpars();
		}
		if (fun instanceof SetterDeclaration) {
			fpars = List.of(((SetterDeclaration) fun).getFpar());
		}

		Set<String> fparNames = toSet(map(fpars, fp -> fp.getName()));
		Iterable<VariableDeclaration> declaredLetConst = filter(
				filter(getNameDeclarations(block), VariableDeclaration.class), it -> N4JSASTUtils.isBlockScoped(it));
		Iterable<VariableDeclaration> dupes = filter(declaredLetConst, vd -> fparNames.contains(getDeclaredName(vd)));

		for (VariableDeclaration dupeEO : dupes) {
			String name = getDeclaredName(dupeEO);
			FormalParameter baseEO = head(filter(fpars, it -> Objects.equals(it.getName(), name)));
			IssueItem issueItem = AST_NAME_DUPLICATE_ERR.toIssueItem(
					toFirstUpper(messageHelper.description(dupeEO, name)),
					messageHelper.description(baseEO, name));
			addIssue(dupeEO, findNameEAttribute(dupeEO), issueItem);
		}
	}

	/**
	 * check (pre-computed) localNames of the given scope with (pre-computed) all names used in parent scopes. If
	 * conflict is detected traverse containers of the scope, until {@link EObject} creating using the name is found. If
	 * it meets error conditions we issue proper error, otherwise ast is traversed further up. If no {@link EObject}
	 * that meets error conditions is found, no error is issued. AST traversing stops at first {@link EObject} that
	 * meets error conditions, so no multiple errors should be issued on a given declaration.
	 */
	private void checkOuterScopesNamesConflict(VariableEnvironmentElement scope,
			ListMultimap<String, EObject> localNames,
			List<String> localNamesWithConflictToOuterNames, CancelIndicator ci) {

		for (String n : localNamesWithConflictToOuterNames) {
			operationCanceledManager.checkCanceled(ci);

			boolean isExceptionCase = n.equals("arguments"); // special case, separate handling
			if (!isExceptionCase) {

				List<EObject> innerScopeObjects = localNames.get(n);
				for (EObject innerScopeObject : innerScopeObjects) {
					EObject conflict = null;
					EObject conflictContainer = scope.eContainer();
					while (conflict == null) {
						if (conflictContainer == null) {
							return; // no conflict found in AST, allowed shadowing
						}

						Iterable<EObject> z = findOuterDeclaration(conflictContainer, n);
						if (isEmpty(z)) {

							// no conflicts in this parent, loop over its parent
							conflictContainer = conflictContainer.eContainer();
						} else {
							conflict = head(z);

							EObject outerScopeObject = conflict;
							String name = n;

							if (outerScopeObject instanceof N4ClassExpression ||
									innerScopeObject instanceof N4ClassExpression) {
								return;
							}

							// only FD and FE, we don't want N4Methods, thus don't check against FunctionDefinition
							if (outerScopeObject instanceof FunctionDeclaration ||
									outerScopeObject instanceof FunctionExpression) {

								if (innerScopeObject instanceof FunctionExpression ||
										innerScopeObject instanceof N4ClassExpression) {

									// named expression don't introduce name in outer scope
									return;
								}

								if (innerScopeObject instanceof FormalParameter
										&& outerScopeObject instanceof FunctionDefinition
										&& innerScopeObject.eContainer() == outerScopeObject) {

									// formal parameters hides containing function name
									IssueItem issueItem = AST_NAME_SHADOW_ERR.toIssueItem(
											toFirstUpper(messageHelper.description(innerScopeObject, name)),
											messageHelper.description(outerScopeObject, name));
									addIssue(innerScopeObject, findNameEAttribute(innerScopeObject), issueItem);
									return;
								}

								if (outerScopeObject.equals(scope) == false) {

									// you can shadow function
									return;
								}

								// but not containing function
								IssueItem issueItem = AST_NAME_SHADOW_ERR.toIssueItem(
										toFirstUpper(messageHelper.description(innerScopeObject, name)),
										messageHelper.descriptionWithLine(outerScopeObject, name));
								addIssue(innerScopeObject, findNameEAttribute(innerScopeObject), issueItem);
								return;
							}

							if (innerScopeObject instanceof CatchVariable) {
								if (outerScopeObject instanceof CatchVariable) {
									// both http://jslint.com/ and http://jshint.com/
									// produce warning here, additionally
									// this does not work in some JS environments, e.g. IE8
									// adding a warning, consider removing if it gets annoying
									// in our platform code
									if (jsVariantHelper.isN4JSMode(innerScopeObject)) {
										IssueItem issueItem = AST_NAME_SHADOW_WARN.toIssueItem(
												toFirstUpper(messageHelper.description(innerScopeObject, name)),
												messageHelper.descriptionWithLine(outerScopeObject, name));
										addIssue(innerScopeObject, findNameEAttribute(innerScopeObject), issueItem);
										return;
									}
									// if js mode
									return;
								}
								IssueItem issueItem = AST_NAME_SHADOW_ERR.toIssueItem(
										toFirstUpper(messageHelper.description(innerScopeObject, name)),
										messageHelper.descriptionWithLine(outerScopeObject, name));
								addIssue(innerScopeObject, findNameEAttribute(innerScopeObject), issueItem);
								return;
							}
							return;

						}
					}
				}
			}
		}
	}

	private Iterable<EObject> findOuterDeclaration(EObject scope, String name) {
		Iterable<EObject> decl = filter(scope.eContents(), it -> Objects.equals(name, getDeclaredName(it)));
		return decl;
	}

	/**
	 * returns {@link List} of names declared in the global object.
	 */
	public ListMultimap<String, TMember> getGlobalNames(Resource resource) {
		ListMultimap<String, TMember> result = MultimapBuilder.linkedHashKeys().linkedListValues().build();
		for (TMember member : EcoreUtil2.getAllContentsOfType(getGlobalObject(resource), TMember.class)) {
			String name = member.getName();
			if (!Strings.isNullOrEmpty(name)) {
				result.put(name, member);
			}
		}
		return Multimaps.unmodifiableListMultimap(result);
	}

	/**
	 * returns {@link EObject} instance of the global object Returned data is cached, resolved only if new (or changed)
	 * {@link Resource}
	 */
	public TClass getGlobalObject(Resource resource) {
		return GlobalObjectScope.get(resource.getResourceSet()).getGlobalObject();
	}

	// TODO after cleanup, the following methods should be put into the N4JS.xcore (or put in N4JSASTUtils and add
	// convenience methods to N4JS.xcore ??)
	/**
	 * Returns all elements that declare a name in scope 'scope' without(!) considering nested scopes.
	 */
	private Iterable<EObject> getNameDeclarations(VariableEnvironmentElement scope) {
		Resource resource = scope.eResource();
		List<EObject> namedEOs = new ArrayList<>();

		// in some cases, the scopes we obtain from ordinary scoping are not sufficient for the purpose of this
		// validation, so add a few additional elements:
		if (scope instanceof Script) {
			namedEOs.addAll(toList(flatMap(filter(((Script) scope).getScriptElements(), ImportDeclaration.class),
					it -> it.getImportSpecifiers())));
		}
		if (scope instanceof FunctionOrFieldAccessor) {
			namedEOs.add(((FunctionOrFieldAccessor) scope).getImplicitArgumentsVariable());
		}

		// add all elements from the scope as computed by ordinary scoping:
		namedEOs.addAll(toList(
				map(sourceElementExtensions.collectVisibleIdentifiableElements(scope), it -> backToAST(it, resource))));

		return filter(namedEOs, it -> !Strings.isNullOrEmpty(getDeclaredName(it)));
	}

	/**
	 * Returns list of names declared in scope 'scope' without(!) considering nested scopes.
	 */
	private ListMultimap<String, EObject> getLocalNames(VariableEnvironmentElement scope) {
		ListMultimap<String, EObject> result = MultimapBuilder.linkedHashKeys().linkedListValues().build();
		for (EObject nameDecl : getNameDeclarations(scope)) {
			String name = getDeclaredName(nameDecl);
			result.put(name, nameDecl);
		}
		return Multimaps.unmodifiableListMultimap(result);
	}

	/**
	 * Returns nested scopes of 'scope'. Only direct sub-scopes of 'scope' are returned, no sub-sub-scopes, i.e.
	 * sub-scopes of sub-scopes.
	 */
	private Iterator<VariableEnvironmentElement> getNestedScopes(VariableEnvironmentElement scope) {
		return filter(EcoreUtilN4.getAllContentsFiltered(scope,
				it -> !(createsScope(it.eContainer()) && it.eContainer() != scope)
						&& createsScope(it)),
				VariableEnvironmentElement.class);
	}

	private boolean createsScope(EObject eo) {
		return eo instanceof VariableEnvironmentElement;
	}

	/**
	 * Resolves name of given {@link EObject} if possible, null otherwise. Will not return actual name for all objects,
	 * only the ones interesting from duplicates/shadowing point of view. (so variable declarations, function
	 * definitions, type definitions...). In case of named import specifiers returns alias if possible, otherwise name
	 * of imported element. returns null in other cases. Does not check value of the returned name, so it can be null or
	 * empty string.
	 */
	private String getDeclaredName(EObject eo) {

		if (eo instanceof FunctionDeclaration || eo instanceof FunctionExpression || eo instanceof N4TypeDefinition ||
				eo instanceof AbstractVariable) {
			return findName(eo);
		}

		if (eo instanceof NamedImportSpecifier) {
			NamedImportSpecifier namedIS = (NamedImportSpecifier) eo;
			if (!Strings.isNullOrEmpty(namedIS.getAlias())) {
				return namedIS.getAlias();
			} else {
				return namedIS.getImportedElementAsText();
			}
		}

		if (eo instanceof NamespaceImportSpecifier) {
			// practical approach : ignoring names in the namespace, as it is the only case of name with dot in the name
			// proper approach: check against all elements contributed by the namespace
			return ((NamespaceImportSpecifier) eo).getAlias();
		}

		return null;
	}

	/** helper dispatch because we lack one uniform interface for getName */
	private String findName(final EObject it) {
		if (it instanceof IdentifiableElement) {
			return ((IdentifiableElement) it).getName();
		} else if (it instanceof NamedElement) {
			return ((NamedElement) it).getName();
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " +
					Arrays.asList(it).toString());
		}
	}

	/**
	 * returns attribute holding name for given EObject. Throws error if provided EObject does not define name
	 * attribute.
	 */
	private EStructuralFeature findNameEAttribute(EObject eo) {
		if (eo instanceof N4NamespaceDeclaration) {
			return N4JSPackage.Literals.N4_NAMESPACE_DECLARATION__NAME;
		}

		if (eo instanceof N4TypeDeclaration) {
			return N4JSPackage.Literals.N4_TYPE_DECLARATION__NAME;
		}

		if (eo instanceof N4ClassExpression) {
			return N4JSPackage.Literals.N4_CLASS_EXPRESSION__NAME;
		}

		if (eo instanceof FunctionExpression) {
			return N4JSPackage.Literals.FUNCTION_EXPRESSION__NAME;
		}

		if (eo instanceof FunctionDeclaration) {
			return N4JSPackage.Literals.FUNCTION_DECLARATION__NAME;
		}

		if (eo instanceof AbstractVariable) {
			return N4JSPackage.Literals.ABSTRACT_VARIABLE__NAME;
		}

		if (eo instanceof N4TypeVariable) {
			return N4JSPackage.Literals.N4_TYPE_VARIABLE__NAME;
		}

		if (eo instanceof NamedImportSpecifier) {
			NamedImportSpecifier nis = (NamedImportSpecifier) eo;
			if (nis.getAlias() != null) {
				return N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER__ALIAS;
			}
			if (nis.getImportedElement().getName() != null) {
				return TypesPackage.Literals.IDENTIFIABLE_ELEMENT__NAME;
			}
		}

		if (eo instanceof NamespaceImportSpecifier) {
			NamespaceImportSpecifier nis = (NamespaceImportSpecifier) eo;
			if (nis.getAlias() != null) {
				return N4JSPackage.Literals.NAMESPACE_IMPORT_SPECIFIER__ALIAS;
			}
		}

		if (eo instanceof IdentifiableElement) {
			return TypesPackage.Literals.IDENTIFIABLE_ELEMENT__NAME;
		}

		if (eo instanceof PropertyNameValuePair && ((PropertyNameValuePair) eo).getProperty() != null) {
			return N4JSPackage.Literals.PROPERTY_NAME_VALUE_PAIR__PROPERTY;
		}

		if (eo instanceof PropertyAssignment) {
			return N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME;
		}

		throw new RuntimeException("cannot obtain name attribute for " + eo);
	}

	/**
	 * If <code>potentialTModuleElement</code> is a TModule element that has a related AST node within
	 * <code>resource</code>, then return this AST node, otherwise return <code>potentialTModuleElement</code> itself.
	 */
	private EObject backToAST(EObject potentialTModuleElement, Resource resource) {
		if (potentialTModuleElement instanceof SyntaxRelatedTElement) {
			if (potentialTModuleElement.eResource() == resource) {
				return ((SyntaxRelatedTElement) potentialTModuleElement).getAstElement();
			}
		}
		return potentialTModuleElement;
	}
}
