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

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ArrLit;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ArrowFunc;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Block;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Fpar;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._IdentRef;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._LiteralOrComputedPropertyName;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4MethodDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ObjLit;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Parenthesis;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._PropertyAccessExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._PropertyAssignmentAnnotationList;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._PropertyGetterDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._PropertyNameValuePair;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ReturnStmnt;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Snippet;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._VariableDeclaration;
import static org.eclipse.n4js.transpiler.utils.TranspilerUtils.orContainingExportDeclaration;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getGlobalObjectScope;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.symbolObjectType;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.AdditiveExpression;
import org.eclipse.n4js.n4JS.AnnotablePropertyAssignment;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.AwaitExpression;
import org.eclipse.n4js.n4JS.BinaryBitwiseExpression;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.BooleanLiteral;
import org.eclipse.n4js.n4JS.CastExpression;
import org.eclipse.n4js.n4JS.CommaExpression;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.GetterDeclaration;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.MultiplicativeExpression;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.NullLiteral;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.PromisifyExpression;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration;
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.PropertySetterDeclaration;
import org.eclipse.n4js.n4JS.SetterDeclaration;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.YieldExpression;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.assistants.TypeAssistant;
import org.eclipse.n4js.transpiler.es.assistants.ClassifierAssistant;
import org.eclipse.n4js.transpiler.es.assistants.DelegationAssistant;
import org.eclipse.n4js.transpiler.es.assistants.ReflectionAssistant;
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.transpiler.utils.TranspilerUtils;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.ResourceNameComputer;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.inject.Inject;

/**
 */
public class InterfaceDeclarationTransformation extends Transformation {

	@Inject
	private ClassifierAssistant classifierAssistant;
	@Inject
	private ReflectionAssistant reflectionAssistant;
	@Inject
	private DelegationAssistant delegationAssistant;
	@Inject
	private TypeAssistant typeAssistant;
	@Inject
	private ResourceNameComputer resourceNameComputer;

	@Override
	public void assertPreConditions() {
		typeAssistant.assertClassifierPreConditions();
	}

	@Override
	public void assertPostConditions() {
		// none
	}

	@Override
	public void analyze() {
		// ignore
	}

	@Override
	public void transform() {
		for (N4InterfaceDeclaration id : collectNodes(getState().im, N4InterfaceDeclaration.class, false)) {
			transformInterfaceDecl(id);
		}
	}

	private void transformInterfaceDecl(N4InterfaceDeclaration ifcDecl) {
		if (ifcDecl.getTypingStrategy() == TypingStrategy.STRUCTURAL) {
			// structural interfaces are shapes, i.e. do only exist at compile time
			EObject ifcOrParent = ifcDecl.eContainer() instanceof ExportDeclaration ? ifcDecl.eContainer() : ifcDecl;
			remove(ifcOrParent);
			return;
		}
		SymbolTableEntry ifcSTE = findSymbolTableEntryForElement(ifcDecl, true);

		// add 'Symbol.hasInstance' function for supporting the 'instanceof' operator
		ifcDecl.getOwnedMembersRaw().add(createHasInstanceMethod(ifcDecl));

		reflectionAssistant.addN4TypeGetter(ifcDecl, ifcDecl);

		List<Statement> belowIfcDecl = new ArrayList<>();
		belowIfcDecl.addAll(createStaticFieldInitializations(ifcDecl, ifcSTE));
		insertAfter(orContainingExportDeclaration(ifcDecl), belowIfcDecl.toArray(new Statement[0]));

		ifcDecl.getOwnedMembersRaw().removeIf(m -> m.isStatic() && m instanceof N4FieldDeclaration);
		delegationAssistant.replaceDelegatingMembersByOrdinaryMembers(ifcDecl);

		ObjectLiteral ifcObjLit = createInterfaceObject(ifcDecl);
		VariableDeclaration varDecl = _VariableDeclaration(ifcDecl.getName(), ifcObjLit);
		getState().tracer.copyTrace(ifcDecl, varDecl);

		replace(ifcDecl, varDecl);
	}

	/** Creates an object literal for the object representing the interface of the given <code>ifcDecl</code>. */
	private ObjectLiteral createInterfaceObject(N4InterfaceDeclaration ifcDecl) {
		List<PropertyAssignment> props = new ArrayList<>();
		ArrayLiteral extendedInterfaces = createDirectlyImplementedOrExtendedInterfacesArgument(ifcDecl);
		if (!extendedInterfaces.getElements().isEmpty()) {
			String $extends = steFor_$extendsInterfaces().getName();
			props.add(_PropertyGetterDecl(
					$extends,
					_ReturnStmnt(extendedInterfaces)));
		}
		props.addAll(createInstanceFieldDefaultsProperty(ifcDecl));
		props.addAll(createInstanceMemberPropertiesExceptFields(ifcDecl));
		props.addAll(createStaticMemberPropertiesExceptFields(ifcDecl));

		return _ObjLit(props.toArray(new PropertyAssignment[0]));
	}

	private ArrayLiteral createDirectlyImplementedOrExtendedInterfacesArgument(N4ClassifierDeclaration typeDecl) {
		List<SymbolTableEntryOriginal> interfaces = typeAssistant.getSuperInterfacesSTEs(typeDecl);

		// the return value of this method is intended for default method patching; for this purpose, we have to
		// filter out some of the directly implemented interfaces:
		Iterable<SymbolTableEntryOriginal> directlyImplementedInterfacesFiltered = TranspilerUtils
				.filterNominalInterfaces(interfaces);
		return _ArrLit(
				toList(map(directlyImplementedInterfacesFiltered, i -> _IdentRef(i))).toArray(new IdentifierRef[0]));
	}

	@SuppressWarnings("unchecked")
	private List<PropertyNameValuePair> createInstanceFieldDefaultsProperty(N4InterfaceDeclaration ifcDecl) {
		// $fieldInits: {
		// fieldName1: undefined,
		// fieldName2: 42,
		// fieldName3: () => <INIT_EXPRESSION>,
		// ...
		// }
		List<N4FieldDeclaration> instanceFields = toList(
				filter(ifcDecl.getOwnedFields(), f -> !f.isStatic() && f.getName() != null));
		if (instanceFields.isEmpty()) {
			return Collections.emptyList();
		}
		List<Pair<String, Expression>> nameValuePairs = new ArrayList<>();
		for (N4FieldDeclaration fd : instanceFields) {
			nameValuePairs.add(Pair.of(fd.getName(),
					hasNonTrivialInitExpression(fd)
							? (canSkipFunctionWrapping(fd.getExpression()) ? fd.getExpression()
									: _ArrowFunc(false, new FormalParameter[0],
											wrapInParenthesesIfNeeded(fd.getExpression())))
							: undefinedRef()));
		}

		return List.of(
				_PropertyNameValuePair(
						steFor_$fieldInits().getName(),
						_ObjLit(nameValuePairs.toArray(new Pair[0]))));
	}

	private List<PropertyNameValuePair> createInstanceMemberPropertiesExceptFields(N4InterfaceDeclaration ifcDecl) {
		// $defaultMembers: {
		// get getter() {
		// },
		// set setter(value) {
		// },
		// method() {
		// },
		// ...
		// }

		List<N4MemberDeclaration> instanceMembersExceptFields = toList(filter(ifcDecl.getOwnedMembers(),
				m -> !m.isStatic() && !m.isAbstract() && m.getName() != null && !(m instanceof N4FieldDeclaration)));

		if (instanceMembersExceptFields.isEmpty()) {
			return Collections.emptyList();
		}
		return List.of(
				_PropertyNameValuePair(
						steFor_$defaultMembers().getName(),
						_ObjLit(
								toList(map(instanceMembersExceptFields, m -> convertMemberToProperty(m)))
										.toArray(new PropertyAssignment[0]))));
	}

	private List<PropertyAssignment> createStaticMemberPropertiesExceptFields(N4InterfaceDeclaration ifcDecl) {
		// get staticGetter() {
		// },
		// set staticSetter(value) {
		// },
		// staticMethod() {
		// },
		// ...

		List<N4MemberDeclaration> staticMembersExceptFields = toList(filter(ifcDecl.getOwnedMembers(),
				m -> m.isStatic() && !m.isAbstract() && m.getName() != null && !(m instanceof N4FieldDeclaration)));

		if (staticMembersExceptFields.isEmpty()) {
			return Collections.emptyList();
		}
		return toList(map(staticMembersExceptFields, m -> convertMemberToProperty(m)));
	}

	/**
	 * Converts getter, setter, and method declarations to the corresponding declarations in object literals. Does not
	 * support conversion of field declarations!
	 */
	private PropertyAssignment convertMemberToProperty(N4MemberDeclaration memberDecl) {
		// fields:
		if (memberDecl instanceof N4FieldDeclaration) {
			N4FieldDeclaration fieldDecl = (N4FieldDeclaration) memberDecl;
			return _PropertyNameValuePair(
					// reuse existing name
					fieldDecl.getDeclaredName(),
					// reuse existing initializer expression (if any)
					fieldDecl.getExpression() != null ? fieldDecl.getExpression() : undefinedRef());
		}
		// getters, setters, and methods:
		AnnotablePropertyAssignment result = null;
		if (memberDecl instanceof GetterDeclaration) {
			PropertyGetterDeclaration gd = N4JSFactory.eINSTANCE.createPropertyGetterDeclaration();
			gd.setBody(((GetterDeclaration) memberDecl).getBody()); // reuse existing body
			result = gd;
		} else if (memberDecl instanceof SetterDeclaration) {
			PropertySetterDeclaration sd = N4JSFactory.eINSTANCE.createPropertySetterDeclaration();
			sd.setFpar(((SetterDeclaration) memberDecl).getFpar()); // reuse existing fpar
			sd.setBody(((SetterDeclaration) memberDecl).getBody()); // reuse existing body
			result = sd;
		} else if (memberDecl instanceof FunctionDefinition) {
			FunctionDefinition fd = (FunctionDefinition) memberDecl;
			PropertyMethodDeclaration pmd = N4JSFactory.eINSTANCE.createPropertyMethodDeclaration();
			pmd.getFpars().addAll(fd.getFpars()); // reuse existing fpar
			pmd.setBody(fd.getBody()); // reuse existing body
			pmd.setGenerator(fd.isGenerator());
			pmd.setDeclaredAsync(fd.isAsync());
			result = pmd;
		} else {
			throw new IllegalArgumentException("not a getter, setter, or method declaration");
		}

		result.setDeclaredName(((PropertyNameOwner) memberDecl).getDeclaredName()); // reuse existing name
		if (!memberDecl.getAnnotations().isEmpty()) {
			// reuse existing annotations
			result.setAnnotationList(
					_PropertyAssignmentAnnotationList(memberDecl.getAnnotations().toArray(new Annotation[0])));
		}
		return result;
	}

	private Expression wrapInParenthesesIfNeeded(Expression expr) {
		if (expr instanceof CommaExpression
				|| expr instanceof ObjectLiteral
				|| expr instanceof AwaitExpression
				|| expr instanceof YieldExpression
				|| expr instanceof PromisifyExpression) {
			return _Parenthesis(expr);
		}
		return expr;
	}

	private boolean canSkipFunctionWrapping(Expression initExpression) {
		if (initExpression instanceof BooleanLiteral
				|| initExpression instanceof NullLiteral
				|| initExpression instanceof NumericLiteral
				|| initExpression instanceof StringLiteral) {
			return true;
		}
		if (initExpression instanceof UnaryExpression) {
			UnaryExpression unaryExpr = (UnaryExpression) initExpression;
			// WARNING: some unary operators have a side effect, so they have to be wrapped in a function!
			if (canSkipFunctionWrapping(unaryExpr.getExpression())) {
				switch (unaryExpr.getOp()) {
				case INC:
				case DEC:
				case DELETE:
					return false;
				case POS:
				case NEG:
				case INV:
				case NOT:
				case TYPEOF:
				case VOID:
					return true;
				}
			}
		}
		if (initExpression instanceof AdditiveExpression) {
			AdditiveExpression addExpr = (AdditiveExpression) initExpression;
			if (canSkipFunctionWrapping(addExpr.getLhs())
					&& canSkipFunctionWrapping(addExpr.getRhs())) {

				switch (addExpr.getOp()) {
				case ADD:
				case SUB:
					return true;
				}
			}
		}
		if (initExpression instanceof MultiplicativeExpression) {
			MultiplicativeExpression mulExpr = (MultiplicativeExpression) initExpression;
			if (canSkipFunctionWrapping(mulExpr.getLhs())
					&& canSkipFunctionWrapping(mulExpr.getRhs())) {

				switch (mulExpr.getOp()) {
				case TIMES:
				case DIV:
				case MOD:
					return true;
				}
			}
		}
		if (initExpression instanceof BinaryBitwiseExpression) {
			BinaryBitwiseExpression bbExpr = (BinaryBitwiseExpression) initExpression;
			if (canSkipFunctionWrapping(bbExpr.getLhs())
					&& canSkipFunctionWrapping(bbExpr.getRhs())) {

				switch (bbExpr.getOp()) {
				case OR:
				case XOR:
				case AND:
					return true;
				}
			}
		}
		if (initExpression instanceof BinaryLogicalExpression) {
			BinaryLogicalExpression blExpr = (BinaryLogicalExpression) initExpression;
			if (canSkipFunctionWrapping(blExpr.getLhs())
					&& canSkipFunctionWrapping(blExpr.getRhs())) {

				switch (blExpr.getOp()) {
				case OR:
				case AND:
					return true;
				}
			}
		}
		if (initExpression instanceof CastExpression) {
			return canSkipFunctionWrapping(((CastExpression) initExpression).getExpression());
		}
		if (initExpression instanceof ParenExpression) {
			return canSkipFunctionWrapping(((ParenExpression) initExpression).getExpression());
		}
		if (initExpression instanceof IdentifierRef) {
			IdentifierRef idRef = (IdentifierRef) initExpression;
			return idRef.getId() == getGlobalObjectScope(getState().G).getFieldUndefined();
		}

		return false;
	}

	private List<Statement> createStaticFieldInitializations(N4InterfaceDeclaration ifcDecl,
			SymbolTableEntry ifcSTE) {
		return classifierAssistant.createStaticFieldInitializations(ifcDecl, ifcSTE, Collections.emptySet());
	}

	private N4MethodDeclaration createHasInstanceMethod(N4InterfaceDeclaration ifcDecl) {
		TClass symbolObjectType = symbolObjectType(getState().G);
		SymbolTableEntryOriginal symbolSTE = getSymbolTableEntryOriginal(symbolObjectType, true);
		SymbolTableEntryOriginal hasInstanceSTE = getSymbolTableEntryForMember(symbolObjectType, "hasInstance", false,
				true, true);
		ParameterizedPropertyAccessExpression_IM hasInstanceExpr = _PropertyAccessExpr(symbolSTE, hasInstanceSTE);
		LiteralOrComputedPropertyName declaredName = _LiteralOrComputedPropertyName(hasInstanceExpr,
				N4JSLanguageUtils.SYMBOL_IDENTIFIER_PREFIX + "hasInstance");

		TInterface ifcType = getState().info.getOriginalDefinedType(ifcDecl);
		String fqn = resourceNameComputer.getFullyQualifiedTypeName(ifcType);

		N4MethodDeclaration result = _N4MethodDecl(true, declaredName, new FormalParameter[] { _Fpar("instance") },
				_Block(
						_ReturnStmnt(
								_Snippet("instance && instance.constructor && instance.constructor.n4type "
										// required because we cannot be sure "instance.constructor.n4type" is of type
										// N4Classifier
										+ "&& instance.constructor.n4type.allImplementedInterfaces "
										+ "&& instance.constructor.n4type.allImplementedInterfaces.indexOf('" + fqn
										+ "') !== -1"))));

		getState().info.markAsHiddenFromReflection(result);

		return result;
	}
}
