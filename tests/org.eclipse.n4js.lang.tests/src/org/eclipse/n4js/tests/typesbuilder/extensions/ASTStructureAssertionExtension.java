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
package org.eclipse.n4js.tests.typesbuilder.extensions;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration;
import org.eclipse.n4js.n4JS.PropertyNameKind;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.PropertySetterDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.inject.Inject;

@SuppressWarnings("unused")
public class ASTStructureAssertionExtension {

	@Inject
	private TypesStructureAssertionExtension typeStructX;

	public Script assertScript(String phase, Resource resource, int elementCount) {
		EObject root = resource.getContents().get(0);
		assertTrue(phase + ": Script expected", root instanceof Script);
		Script script = (Script) root;
		assertEquals(phase + ": Script should contain expected element count", elementCount,
				script.getScriptElements().size());
		return script;
	}

	public ExportableElement assertExportDeclaration(String phase, Script script, int index) {
		ScriptElement element = script.getScriptElements().get(index);
		assertFalse(phase + ": Element should be no proxy", element.eIsProxy());
		assertTrue(phase + ": Element should be a ExportDeclaration", element instanceof ExportDeclaration);
		ExportDeclaration exportDeclaration = (ExportDeclaration) element;
		ExportableElement exportableElement = exportDeclaration.getExportedElement();
		assertFalse(phase + ": Exported element should be no proxy", exportableElement.eIsProxy());
		return exportableElement;
	}

	public N4ClassDeclaration assertN4ClassDeclaration(String phase, ExportableElement exportableElement, String name,
			int memberCount) {
		assertTrue(phase + ": Exported element should be a N4ClassDeclaration",
				exportableElement instanceof N4ClassDeclaration);
		N4ClassDeclaration n4Class = (N4ClassDeclaration) exportableElement;
		assertEquals(phase + ": Class should be named", name, n4Class.getName());
		assertEquals(phase + ": Class should contain expected member count", memberCount,
				n4Class.getOwnedMembers().size());
		return n4Class;
	}

	public N4InterfaceDeclaration assertN4InterfaceDeclaration(String phase, ExportableElement exportableElement,
			String name,
			int memberCount) {
		assertTrue(phase + ": Exported element should be a N4InterfaceDeclaration",
				exportableElement instanceof N4InterfaceDeclaration);
		N4InterfaceDeclaration n4Interface = (N4InterfaceDeclaration) exportableElement;
		assertEquals(phase + ": Interface should be named", name, n4Interface.getName());
		assertEquals(phase + ": Interface should contain expected member count", memberCount,
				n4Interface.getOwnedMembers().size());
		return n4Interface;
	}

	public N4InterfaceDeclaration assertN4RoleDeclaration(String phase, ExportableElement exportableElement,
			String name,
			int memberCount) {
		assertTrue(phase + ": Exported element should be a N4InterfaceDeclaration",
				exportableElement instanceof N4InterfaceDeclaration);
		N4InterfaceDeclaration n4Role = (N4InterfaceDeclaration) exportableElement;
		assertEquals(phase + ": Role should be named", name, n4Role.getName());
		assertEquals(phase + ": Role should contain expected member count", memberCount,
				n4Role.getOwnedMembers().size());
		return n4Role;
	}

	public N4FieldDeclaration assertN4FieldDeclaration(String phase, N4ClassDeclaration n4Class, String name) {
		assertTrue(phase + ": Element should be a N4FieldDeclaration",
				head(filter(n4Class.getOwnedMembers(),
						m -> Objects.equals(m.getName(), name))) instanceof N4FieldDeclaration);
		N4FieldDeclaration n4ClassField = (N4FieldDeclaration) head(
				filter(n4Class.getOwnedMembers(), m -> Objects.equals(m.getName(), name)));
		assertEquals(phase + ": Field should be named", name, n4ClassField.getName());
		return n4ClassField;
	}

	public FunctionDeclaration assertN4Function(String phase, Resource newN4jsResource,
			ExportableElement exportableElement, String name,
			int parameterCount) {
		assertTrue(phase + ": Exported element should be a FunctionDeclaration",
				exportableElement instanceof FunctionDeclaration);
		FunctionDeclaration n4Function = (FunctionDeclaration) exportableElement;
		assertEquals(phase + ": Function should be named", name, n4Function.getName());
		assertEquals(phase + ": Function should have expected parameter count", parameterCount,
				n4Function.getFpars().size());
		return n4Function;
	}

	public N4MethodDeclaration assertN4Method(String phase, N4ClassifierDeclaration n4Classifier, String name,
			int parameterCount) {
		assertTrue(phase + ": Exported element should be a N4MethodDeclaration",
				head(filter(n4Classifier.getOwnedMembers(),
						m -> Objects.equals(m.getName(), name))) instanceof N4MethodDeclaration);
		N4MethodDeclaration n4Method = (N4MethodDeclaration) head(
				filter(n4Classifier.getOwnedMembers(), m -> Objects.equals(m.getName(), name)));
		assertEquals(phase + ": Method should be named", name, n4Method.getName());
		assertEquals(phase + ": Method should have expected parameter count", parameterCount,
				n4Method.getFpars().size());
		return n4Method;
	}

	public N4GetterDeclaration assertN4Getter(String phase, N4ClassifierDeclaration n4Classifier, String name) {
		N4GetterDeclaration n4Getter = head(filter(filter(n4Classifier.getOwnedMembers(), N4GetterDeclaration.class),
				m -> Objects.equals(m.getName(), name)));
		assertNotNull(phase + ": Member should be a getter", n4Getter);
		assertEquals(phase + ": Getter should be named", name, n4Getter.getName());
		assertTrue(phase + ": Getter should be collected as getter", n4Classifier.getOwnedGetters().contains(n4Getter));
		return n4Getter;
	}

	public N4SetterDeclaration assertN4Setter(String phase, N4ClassifierDeclaration n4Classifier, String name) {
		N4SetterDeclaration n4Setter = head(filter(filter(n4Classifier.getOwnedMembers(), N4SetterDeclaration.class),
				m -> Objects.equals(m.getName(), name)));
		assertNotNull(phase + ": Member should be a setter", n4Setter);
		assertEquals(phase + ": Setter should be named", name, n4Setter.getName());
		assertNotNull(phase + ": Setter should have exactly one parameter", n4Setter.getFpar());
		assertTrue(phase + ": Setter should be collected as setter", n4Classifier.getOwnedSetters().contains(n4Setter));
		return n4Setter;
	}

	public PropertyGetterDeclaration assertPropertyGetter(String phase, ObjectLiteral objectLiteral, String name) {
		PropertyGetterDeclaration n4Getter = head(
				filter(filter(objectLiteral.getPropertyAssignments(), PropertyGetterDeclaration.class),
						m -> Objects.equals(m.getName(), name)));
		assertNotNull(phase + ": Getter should be found for name " + name, n4Getter);
		return n4Getter;
	}

	public PropertySetterDeclaration assertPropertySetter(String phase, ObjectLiteral objectLiteral, String name,
			String expectedPropertyName) {
		PropertySetterDeclaration n4Setter = head(
				filter(filter(objectLiteral.getPropertyAssignments(), PropertySetterDeclaration.class),
						m -> Objects.equals(m.getName(), name)));
		assertNotNull(phase + ": Setter should be found for name " + name, n4Setter);
		assertEquals(phase + ": Setter parameter should be named", expectedPropertyName, n4Setter.getFpar().getName());
		return n4Setter;
	}

	public Type assertReturnTypeRef(String phase, FunctionDefinition function, Resource newN4jsResource) {
		assertTrue("Should have parameterized type ref",
				function.getDeclaredReturnTypeRefInAST() instanceof ParameterizedTypeRef);
		ParameterizedTypeRef parameterizedTypeRef = (ParameterizedTypeRef) function.getDeclaredReturnTypeRef();
		assertEquals(phase + ": expected resource content size", 2, newN4jsResource.getContents().size());

		// test whether reference can be resolved
		Type type = parameterizedTypeRef.getDeclaredType();
		assertNotNull(phase + ": declaredType should not be null", type);
		return type;
	}

	public Type assertReturnTypeRef(String phase, TGetter getter, Resource newN4jsResource) {
		assertTrue("Should have parameterized type ref", getter.getTypeRef() instanceof ParameterizedTypeRef);
		ParameterizedTypeRef parameterizedTypeRef = (ParameterizedTypeRef) getter.getTypeRef();
		assertEquals(phase + ": expected resource content size", 2, newN4jsResource.getContents().size());

		// test whether reference can be resolved
		Type type = parameterizedTypeRef.getDeclaredType();
		assertNotNull(phase + ": declaredType should not be null", type);
		return type;
	}

	public Type assertParameter(String phase, FunctionDefinition function, Resource newN4jsResource, String name,
			boolean variadic) {
		FormalParameter parameter = head(filter(function.getFpars(), m -> Objects.equals(m.getName(), name)));
		assertNotNull(phase + ": Parameter should be found for name " + name, parameter);
		assertEquals(phase + ": Should have the expected variadic setup", variadic, parameter.isVariadic());
		assertTrue("Should have parameterized type ref",
				parameter.getDeclaredTypeRefInAST() instanceof ParameterizedTypeRef);
		ParameterizedTypeRef parameterizedTypeRef = (ParameterizedTypeRef) parameter.getDeclaredTypeRefInAST();
		assertEquals(phase + ": expected resource content size", 2, newN4jsResource.getContents().size());
		Type type = parameterizedTypeRef.getDeclaredType();
		assertNotNull(phase + ": declaredType should not be null", type);
		return type;
	}

	public void assertTypeVariables(String phase, FunctionDeclaration declaration, Resource resource,
			String... expectedTypeVarNames) {

		assertEquals(phase + ": Should have expected type vars count", declaration.getTypeVars().size(),
				expectedTypeVarNames.length);

		List<String> nameList = Arrays.asList(expectedTypeVarNames);

		Iterable<Pair<Integer, String>> pairs = map(nameList, name -> Pair.of(nameList.indexOf(name), name));
		for (Pair<Integer, String> pair : pairs) {
			assertEquals(phase + ": Should have expected type var name at index " + pair.getKey(),
					declaration.getTypeVars().get(pair.getKey()).getName(), pair.getValue());
			// TODO assert upper bounds as well (and lower bounds)
		}
	}

	public VariableStatement assertVariableStatement(String phase, EObject exportableElement, int variableCount) {
		assertTrue(phase + ": VariableStatement expected", exportableElement instanceof VariableStatement);
		VariableStatement variableStatement = (VariableStatement) exportableElement;
		assertEquals(phase + ": Script should contain expected element count", variableCount,
				variableStatement.getVarDecl().size());
		return variableStatement;
	}

	public VariableDeclaration assertVariable(String phase, VariableStatement variableStatement, String name,
			boolean isConst) {
		VariableDeclaration n4Variable = head(
				filter(variableStatement.getVarDecl(), m -> Objects.equals(m.getName(), name)));
		assertNotNull(phase + ": Variable should be found for name " + name, n4Variable);
		assertEquals(phase + ": Variable should have correct const value", isConst, n4Variable.isConst());
		return n4Variable;
	}

	public void assertDefinedVariable(String phase, VariableDeclaration variableDeclaration, Resource resource,
			Class<?> expressionType) {

		TVariable tAbstractVariable = variableDeclaration.getDefinedVariable();
		assertEquals(phase + ": expected name", variableDeclaration.getName(), tAbstractVariable.getName());
		assertEquals(phase + ": expected const", variableDeclaration.isConst(), tAbstractVariable.isConst());
		if (expressionType == null) {
			assertNull(phase + ": no assignment expected: " + variableDeclaration, variableDeclaration.getExpression());
		} else {
			assertNotNull(phase + ": expected expression type", expressionType);
			assertNotNull(phase + ": expected expression not null", variableDeclaration.getExpression());
			assertTrue(phase + ": expected expression type",
					expressionType.isAssignableFrom(variableDeclaration.getExpression().getClass()));
		}
	}

	public void assertPropertyNameValuePair(String phase, ObjectLiteral objectLiteral, String name,
			PropertyNameKind expectedKind, String expectedValue,
			Function<PropertyNameValuePair, String> valueCalculation) {

		PropertyNameValuePair propertyNameValuePair = head(
				filter(filter(objectLiteral.getPropertyAssignments(), PropertyNameValuePair.class),
						m -> Objects.equals(m.getName(), name)));
		assertNotNull(phase + ": Property assignment should be found for name " + name, propertyNameValuePair);
		assertEquals(phase + ": expected value", expectedValue, valueCalculation.apply(propertyNameValuePair));
		if (expectedKind == PropertyNameKind.IDENTIFIER) {
			assertNotNull(propertyNameValuePair.getProperty());
		} else {
			assertEquals(phase + ": expected property kind", propertyNameValuePair.getDeclaredName().getKind(),
					expectedKind);
		}
	}

	public Function<PropertyNameValuePair, String> stringLiteralValueCalculation(String phase) {
		return new Function<>() {

			@Override
			public String apply(PropertyNameValuePair vp) {
				assertTrue(phase + ": should be string literal but was " + vp.getExpression(),
						vp.getExpression() instanceof StringLiteral);
				return ((StringLiteral) vp.getExpression()).getValue();
			}
		};

	}

	public Function<PropertyNameValuePair, String> identifierRefValueCalculation(String phase) {

		return new Function<>() {

			@Override
			public String apply(PropertyNameValuePair vp) {
				assertTrue(phase + ": should be identifier ref but was " + vp.getExpression(),
						vp.getExpression() instanceof IdentifierRef);
				return ((IdentifierRef) vp.getExpression()).getId().getName();
			}
		};
	}

	public void assertSuperClass(String phase, N4ClassDeclaration n4Class, Resource resource,
			String expectedSuperClassName) {
		assertNotNull(phase + ": Should have a super type", n4Class.getSuperClassRef());
		TypeRef superClassTypeRef = n4Class.getSuperClassRef().getTypeRef();
		assertNotNull("expected superClassTypeRef to be non-null", superClassTypeRef);
		assertTrue("expected superClassTypeRef to be a ParameterizedTypeRef",
				superClassTypeRef instanceof ParameterizedTypeRef);
		Type superType = typeStructX.assertTypeRef(phase, (ParameterizedTypeRef) superClassTypeRef, resource);
		assertTrue(phase + ": Should have a TClass as super type", superType instanceof TClass);
		assertEquals(phase + ": Should have the expected super class name", expectedSuperClassName,
				superType.getName());
	}

	public void assertConsumedRoles(String phase, N4ClassDeclaration tClass, Resource resource,
			String... expectedConsumedRoleNames) {

		List<String> nameList = Arrays.asList(expectedConsumedRoleNames);
		Iterable<Pair<Integer, String>> pairs = map(nameList, name -> Pair.of(nameList.indexOf(name), name));
		for (Pair<Integer, String> pair : pairs) {
			assertConsumedRole(phase, tClass, resource, pair.getKey(), pair.getValue());
		}

	}

	public void assertConsumedRoles(String phase, N4InterfaceDeclaration tRole, Resource resource,
			String... expectedConsumedRoleNames) {

		List<String> nameList = Arrays.asList(expectedConsumedRoleNames);
		Iterable<Pair<Integer, String>> pairs = map(nameList, name -> Pair.of(nameList.indexOf(name), name));
		for (Pair<Integer, String> pair : pairs) {
			assertConsumedRole(phase, tRole, resource, pair.getKey(), pair.getValue());
		}
	}

	public void assertConsumedRole(String phase, N4ClassDeclaration n4Class, Resource resource, int index,
			String expectedConsumedRoleName) {
		assertTrue(phase + ": Should have an interface at the index",
				n4Class.getImplementedInterfaceRefs().size() > index);
		TypeRef implIfcRef = n4Class.getImplementedInterfaceRefs().get(index).getTypeRef();
		assertNotNull("expected implementedInterfaceRefs[index] to be non-null", implIfcRef);
		assertTrue("expected implementedInterfaceRefs[index] to be a ParameterizedTypeRef",
				implIfcRef instanceof ParameterizedTypeRef);
		Type consumedType = typeStructX.assertTypeRef(phase, (ParameterizedTypeRef) implIfcRef, resource);
		assertTrue(phase + ": Should have a TInterface as consumed type", consumedType instanceof TInterface);
		assertEquals(phase + ": Should have the expected super class name", expectedConsumedRoleName,
				consumedType.getName());
	}

	public void assertConsumedRole(String phase, N4InterfaceDeclaration n4Role, Resource resource, int index,
			String expectedConsumedRoleName) {
		assertTrue(phase + ": Should have an interface at the index", n4Role.getSuperInterfaceRefs().size() > index);
		TypeRef superIfcRef = n4Role.getSuperInterfaceRefs().get(index).getTypeRef();
		assertNotNull("expected superInterfaceRefs[index] to be non-null", superIfcRef);
		assertTrue("expected superInterfaceRefs[index] to be a ParameterizedTypeRef",
				superIfcRef instanceof ParameterizedTypeRef);
		Type consumedType = typeStructX.assertTypeRef(phase, (ParameterizedTypeRef) superIfcRef, resource);
		assertTrue(phase + ": Should have a TInterface as consumed type", consumedType instanceof TInterface);
		assertEquals(phase + ": Should have the expected super class name", expectedConsumedRoleName,
				consumedType.getName());
	}

	public void assertSuperInterfaces(String phase, N4InterfaceDeclaration n4Interface, Resource resource,
			String... expectedSuperInterfaceNames) {

		List<String> nameList = Arrays.asList(expectedSuperInterfaceNames);

		assertEquals(phase + ": " + n4Interface.getName() + " should have expected super interfaces count",
				nameList.size(), n4Interface.getSuperInterfaceRefs().size());

		Iterable<Pair<Integer, String>> pairs = map(nameList, name -> Pair.of(nameList.indexOf(name), name));
		for (Pair<Integer, String> pair : pairs) {
			TypeRef typeRef = n4Interface.getSuperInterfaceRefs().get(pair.getKey()).getTypeRef();
			Type type = typeRef.getDeclaredType();
			assertNotNull(phase + ": declaredType should not be null", type);
			assertTrue(phase + ": declaredType should not a TInterface", type instanceof TInterface);
			assertEquals(phase + ": Should have expected super interface name at index " + pair.getKey(),
					type.getName(), pair.getValue());
		}
	}

	public void assertImplementedInterfaces(String phase, N4InterfaceDeclaration n4Role, Resource resource,
			String... expectedImplementedInterfaceNames) {

		List<String> nameList = Arrays.asList(expectedImplementedInterfaceNames);
		assertEquals(phase + ": " + n4Role.getName() + " should have expected implemented interfaces count",
				nameList.size(), n4Role.getSuperInterfaceRefs().size());

		Iterable<Pair<Integer, String>> pairs = map(nameList, name -> Pair.of(nameList.indexOf(name), name));
		for (Pair<Integer, String> pair : pairs) {
			TypeRef typeRef = n4Role.getSuperInterfaceRefs().get(pair.getKey()).getTypeRef();
			Type type = typeRef.getDeclaredType();
			assertNotNull(phase + ": declaredType should not be null", type);
			assertTrue(phase + ": declaredType should not a TInterface", type instanceof TInterface);
			assertEquals(phase + ": Should have expected implemented interface name at index " + pair.getKey(),
					type.getName(),
					pair.getValue());
		}
	}

	public void assertDeclaredAccessModifier(String phase, N4ClassifierDeclaration n4Classifier, Resource resource,
			N4Modifier expectedAccessModifier) {
		if (expectedAccessModifier == null || expectedAccessModifier == N4Modifier.UNDEFINED) {
			assertTrue(phase + ": Should have no declared access modifiers",
					!n4Classifier.getDeclaredModifiers().contains(N4Modifier.PRIVATE) &&
							!n4Classifier.getDeclaredModifiers().contains(N4Modifier.PROJECT) &&
							!n4Classifier.getDeclaredModifiers().contains(N4Modifier.PROTECTED) &&
							!n4Classifier.getDeclaredModifiers().contains(N4Modifier.PUBLIC));
		} else {
			assertTrue(phase + ": Should have the expected declared access modifier: " + expectedAccessModifier,
					n4Classifier.getDeclaredModifiers()
							.contains(N4Modifier.PRIVATE) == (expectedAccessModifier == N4Modifier.PRIVATE) &&
							n4Classifier.getDeclaredModifiers()
									.contains(N4Modifier.PROJECT) == (expectedAccessModifier == N4Modifier.PROJECT)
							&&
							n4Classifier.getDeclaredModifiers()
									.contains(N4Modifier.PROTECTED) == (expectedAccessModifier == N4Modifier.PROTECTED)
							&&
							n4Classifier.getDeclaredModifiers()
									.contains(N4Modifier.PUBLIC) == (expectedAccessModifier == N4Modifier.PUBLIC));
		}
	}

	public void assertAnnotations(String phase, AnnotableElement annotableElement, Resource resource,
			String... expectedAnnotationNames) {

		List<String> nameList = Arrays.asList(expectedAnnotationNames);
		Iterable<Pair<Integer, String>> pairs = map(nameList, name -> Pair.of(nameList.indexOf(name), name));
		for (Pair<Integer, String> pair : pairs) {
			assertAnnotation(phase, annotableElement, resource, pair.getKey(), pair.getValue());
		}
	}

	public void assertAnnotation(String phase, AnnotableElement element, Resource resource, Integer index,
			String expectedAnnotationName) {
		assertTrue(phase + ": Should have an interface at the index", element.getAnnotations().size() > index);
		Annotation annotation = element.getAnnotations().get(index);
		assertEquals(phase + ": Should have the annotation name", expectedAnnotationName, annotation.getName());
	}
}
