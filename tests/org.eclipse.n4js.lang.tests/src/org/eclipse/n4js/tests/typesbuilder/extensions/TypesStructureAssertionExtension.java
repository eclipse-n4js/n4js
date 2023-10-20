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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.TypedElement;
import org.eclipse.n4js.scoping.builtin.N4Scheme;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TN4Classifier;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAccessModifier;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("unused")
public class TypesStructureAssertionExtension {

	public Type assertTypeRef(String phase, TypedElement typedElement, Resource resource) {
		assertTrue(phase + ": Should have parameterized type ref",
				typedElement.getDeclaredTypeRefInAST() instanceof ParameterizedTypeRef);
		ParameterizedTypeRef parameterizedTypeRef = (ParameterizedTypeRef) typedElement.getDeclaredTypeRefInAST();
		assertEquals(phase + ": expected resource content size", 2, resource.getContents().size());
		// test whether reference can be resolved
		return assertTypeRef(phase, parameterizedTypeRef, resource);
	}

	public Type assertTypeRef(String phase, TVariable variable, Resource resource) {
		assertTrue(phase + ": Should have parameterized type ref",
				variable.getTypeRef() instanceof ParameterizedTypeRef);
		ParameterizedTypeRef parameterizedTypeRef = (ParameterizedTypeRef) variable.getTypeRef();
		assertEquals(phase + ": expected resource content size", 2, resource.getContents().size());
		// test whether reference can be resolved
		return assertTypeRef(phase, parameterizedTypeRef, resource);
	}

	public Type assertTypeRef(String phase, ParameterizedTypeRef parameterizedTypeRef, Resource resource) {
		Type type = parameterizedTypeRef.getDeclaredType();
		assertNotNull(phase + ": declaredType should not be null", type);
		return type;
	}

	public Type assertTypeRefOfTField(String phase, TField tField, Resource newN4jsResource) {
		assertTrue("Should have parameterized type ref", tField.getTypeRef() instanceof ParameterizedTypeRef);
		ParameterizedTypeRef parameterizedTypeRef = (ParameterizedTypeRef) tField.getTypeRef();
		assertEquals(phase + ": expected resource content size", 2, newN4jsResource.getContents().size());
		// test whether reference can be resolved
		Type type = parameterizedTypeRef.getDeclaredType();
		assertNotNull(phase + ": declaredType should not be null", type);
		return type;
	}

	public Type assertReturnTypeRef(String phase, TFunction tFunction, Resource newN4jsResource) {
		assertTrue("Should have parameterized type ref", tFunction.getReturnTypeRef() instanceof ParameterizedTypeRef);
		ParameterizedTypeRef parameterizedTypeRef = (ParameterizedTypeRef) tFunction.getReturnTypeRef();
		assertEquals(phase + ": expected resource content size", 2, newN4jsResource.getContents().size());
		// test whether reference can be resolved
		Type type = parameterizedTypeRef.getDeclaredType();
		assertNotNull(phase + ": declaredType should not be null", type);
		return type;
	}

	public Type assertParameter(String phase, TFunction tFunction, Resource newN4jsResource, String expectedName,
			boolean variadic) {
		TFormalParameter parameter = head(
				filter(tFunction.getFpars(), om -> Objects.equals(om.getName(), expectedName)));
		assertNotNull(phase + ": there should be a parameter with expected name " + expectedName, parameter);
		return assertParameter(phase, parameter, newN4jsResource, expectedName, variadic);
	}

	public Type assertParameter(String phase, TSetter tSetter, Resource newN4jsResource, String expectedName,
			boolean variadic) {
		assertNotNull(phase + ": Should have a parameter at index 0", tSetter.getFpar());
		TFormalParameter parameter = tSetter.getFpar();
		return assertParameter(phase, parameter, newN4jsResource, expectedName, variadic);
	}

	private Type assertParameter(String phase, TFormalParameter parameter, Resource newN4jsResource,
			String expectedName, boolean variadic) {
		assertEquals(phase + ": Should have the expected parameter name", expectedName, parameter.getName());
		assertEquals(phase + ": Should have the expected variadic setup", variadic, parameter.isVariadic());
		assertTrue("Should have parameterized type ref", parameter.getTypeRef() instanceof ParameterizedTypeRef);
		ParameterizedTypeRef parameterizedTypeRef = (ParameterizedTypeRef) parameter.getTypeRef();
		assertEquals(phase + ": expected resource content size", 2, newN4jsResource.getContents().size());
		Type type = parameterizedTypeRef.getDeclaredType();
		assertNotNull(phase + ": declaredType should not be null", type);
		return type;
	}

	public void assertTypeFragmentURI(String phase, Resource newN4jsResource, Type type, String expectedFragment) {
		assertEquals(2, newN4jsResource.getContents().size());
		String fragment = EcoreUtil.getURI(type).fragment();
		assertEquals(expectedFragment, fragment);
		assertFalse("Declared type of parameterized type ref should not be a proxy", type.eIsProxy());
		EObject fetchedEO = newN4jsResource.getEObject(fragment);
		assertSame(phase + ": type", type, fetchedEO);
		assertSame(phase + ": type container", type.eContainer(), newN4jsResource.getContents().get(1));
	}

	public void assertBuiltinTypeFragmentURI(String phase, Resource newN4jsResource, Type type,
			String expectedFragment) {
		assertEquals(2, newN4jsResource.getContents().size());
		URI uri = EcoreUtil.getURI(type);
		assertTrue(phase + ": built-in scheme", N4Scheme.isN4Scheme(uri));
		String fragment = uri.fragment();
		assertEquals(phase + ": expected fragment", expectedFragment, fragment);
		Resource fetchedResource = newN4jsResource.getResourceSet().getResource(uri, true);
		assertNotNull(phase + ": Resource should be resolvable for built-in type URI: " + uri, fetchedResource);
		EObject fetchedEO = newN4jsResource.getResourceSet().getEObject(uri, true);
		assertNotNull(phase + ": Element should be resolvable for built-in type URI: " + uri, fetchedEO);
		assertFalse(phase + ": built-in type shouldn't be a proxy anymore: " + uri, type.eIsProxy());
	}

	public TClass assertTClass(String phase, Resource newN4jsResource, String name, int memberCount) {
		Type type = assertTypeForName(newN4jsResource, name, phase);
		return assertTClass(phase, newN4jsResource, type, name, memberCount);
	}

	public TFunction assertFunctionForName(Resource newN4jsResource, String name, String phase) {
		TFunction type = head(filter(((TModule) newN4jsResource.getContents().get(1)).getFunctions(),
				om -> Objects.equals(om.getName(), name)));
		assertNotNull(phase + ": there should be a type with expected name " + name, type);
		return type;
	}

	public Type assertTypeForName(Resource newN4jsResource, String name, String phase) {
		Type type = head(filter(((TModule) newN4jsResource.getContents().get(1)).getTypes(),
				om -> Objects.equals(om.getName(), name)));
		assertNotNull(phase + ": there should be a type with expected name " + name, type);
		return type;
	}

	public <T extends IdentifiableElement> T assertMemberForNameAndType(TClassifier classifier, String name,
			Class<T> type, String phase) {
		T member = head(filter(filter(classifier.getOwnedMembers(), type), om -> Objects.equals(om.getName(), name)));
		assertNotNull(phase + ": there should be a member with expected name " + name + " and type " + type, member);
		return member;
	}

	public TClass assertTClass(String phase, Resource newN4jsResource, Type type, String name, int memberCount) {
		assertTrue(phase + ": TClass expected", type instanceof TClass);
		TClass tClass = (TClass) type;
		assertEquals(phase + ": Should be named", name, tClass.getName());
		assertEquals(phase + ": TClass should contain member count", memberCount, tClass.getOwnedMembers().size());
		return tClass;
	}

	public TInterface assertTInterface(String phase, Resource newN4jsResource, String name, int memberCount) {
		Type type = assertTypeForName(newN4jsResource, name, phase);
		return assertTInterface(phase, newN4jsResource, type, name, memberCount);
	}

	public TInterface assertTInterface(String phase, Resource newN4jsResource, Type type, String name,
			int memberCount) {
		assertTrue(phase + ": TInterface expected", type instanceof TInterface);
		TInterface tInterface = (TInterface) type;
		assertEquals(phase + ": Should be named", name, tInterface.getName());
		assertEquals(phase + ": TInterface should contain member count", memberCount,
				tInterface.getOwnedMembers().size());
		return tInterface;
	}

	public TInterface assertTRole(String phase, Resource newN4jsResource, String name, int memberCount) {
		Type type = assertTypeForName(newN4jsResource, name, phase);
		return assertTRole(phase, newN4jsResource, type, name, memberCount);
	}

	public TInterface assertTRole(String phase, Resource newN4jsResource, Type type, String name, int memberCount) {
		assertTrue(phase + ": TInterface expected", type instanceof TInterface);
		TInterface tRole = (TInterface) type;
		assertEquals(phase + ": Should be named", name, tRole.getName());
		assertEquals(phase + ": TInterface should contain member count", memberCount, tRole.getOwnedMembers().size());
		return tRole;
	}

	public TField assertTField(String phase, TClassifier classifier, String name) {
		return assertMemberForNameAndType(classifier, name, TField.class, phase);
	}

	public TMethod assertTMethod(String phase, TClassifier classifier, String name, int parameterCount) {
		TMethod tMethod = assertMemberForNameAndType(classifier, name, TMethod.class, phase);
		assertEquals(phase + ": Method should have expected parameter count", parameterCount,
				tMethod.getFpars().size());
		return tMethod;
	}

	public TGetter assertTGetter(String phase, TClassifier classifier, String name) {
		TGetter tGetter = assertMemberForNameAndType(classifier, name, TGetter.class, phase);
		assertTrue(phase + ": Getter should be collected as getter", classifier.getOwnedMembers().contains(tGetter));
		return tGetter;
	}

	public TSetter assertTSetter(String phase, TClassifier classifier, String name) {
		TSetter tSetter = assertMemberForNameAndType(classifier, name, TSetter.class, phase);
		assertNotNull(phase + ": Setter should have exactly one parameter", tSetter.getFpar());
		assertTrue(phase + ": Setter should be collected as setter", classifier.getOwnedMembers().contains(tSetter));
		return tSetter;
	}

	public TStructMember assertTGetter(String phase, TStructuralType structType, String name) {
		TStructMember tGetter = head(filter(structType.getOwnedMembers(), om -> Objects.equals(om.getName(), name)));
		assertNotNull(phase + ": there should be a tGetter with expected name " + name, tGetter);
		return tGetter;
	}

	public TSetter assertTSetter(String phase, TStructuralType structType, String name, String parameterName,
			String parameterTypeFragmentURI) {
		TSetter tSetter = head(
				filter(filter(structType.getOwnedMembers(), om -> Objects.equals(om.getName(), name)), TSetter.class));
		assertNotNull(phase + ": there should be a tSetter with expected name " + name, tSetter);
		assertNotNull(phase + ": Setter should have exactly one parameter", tSetter.getFpar());
		assertEquals(phase + ": Setter should have expected parameter name", parameterName,
				tSetter.getFpar().getName());
		assertBuiltinTypeFragmentURI(phase, structType.eResource(),
				((ParameterizedTypeRef) tSetter.getFpar().getTypeRef()).getDeclaredType(), parameterTypeFragmentURI);
		return tSetter;
	}

	public void assertTEnum(String phase, Type type, String expectedName, String... expectedLiteralNames) {
		assertTrue(phase + ": TEnum expected as declaredType", type instanceof TEnum);
		TEnum enumType = (TEnum) type;
		assertEquals(phase + ": TEnum should have expected name", expectedName, enumType.getName());
		assertEquals(phase + ": TEnum should have expected literals count", expectedLiteralNames.length,
				enumType.getLiterals().size());
		List<String> nameList = Arrays.asList(expectedLiteralNames);
		for (Pair<Integer, String> pair : map(nameList, ln -> Pair.of(nameList.indexOf(ln), ln))) {
			assertEquals(enumType.getLiterals().get(pair.getKey()).getName(), pair.getValue());
		}
	}

	public TFunction assertTFunction(String phase, Resource newN4jsResource, String name, int parameterCount) {
		TFunction tFunction = assertFunctionForName(newN4jsResource, name, phase);
		assertNotNull(phase + ": TFunction expected", tFunction);
		assertEquals(phase + ": Should be named", name, tFunction.getName());
		assertEquals(phase + ": TFunction should have expected parameter count", parameterCount,
				tFunction.getFpars().size());
		return tFunction;
	}

	public void assertTypeVariables(String phase, TFunction function, Resource resource,
			String... expectedTypeVarNames) {
		assertEquals(phase + ": Should have expected type vars count", function.getTypeVars().size(),
				expectedTypeVarNames.length);
		List<String> nameList = Arrays.asList(expectedTypeVarNames);
		for (Pair<Integer, String> pair : map(nameList, ln -> Pair.of(nameList.indexOf(ln), ln))) {
			assertEquals(phase + ": Should have expected type var name at index " + pair.getKey(),
					function.getTypeVars().get(pair.getKey()).getName(), pair.getValue());
			// TODO assert upper bounds as well (and lower bounds)
		}
	}

	public TVariable assertTVariable(String phase, Resource newN4jsResource, int variablesIndex, String name,
			boolean _const) {
		EList<TVariable> variables = ((TModule) newN4jsResource.getContents().get(1)).getExportedVariables();
		assertTrue(phase + ": Should have a variable at the index", variables.size() > variablesIndex);
		TVariable tVariable = variables.get(variablesIndex);
		assertEquals(phase + ": Should be named", name, tVariable.getName());
		assertEquals(phase + ": TVariable should have expected const value", _const, tVariable.isConst());
		return tVariable;
	}

	public void assertSuperClass(String phase, TClass tClass, Resource resource, String expectedSuperClassName) {
		assertNotNull(phase + ": Should have a super type", tClass.getSuperClassRef());
		Type superType = assertTypeRef(phase, tClass.getSuperClassRef(), resource);
		assertTrue(phase + ": Should have a TClass as super type", superType instanceof TClass);
		assertEquals(phase + ": Should have the expected super class name", expectedSuperClassName,
				superType.getName());
	}

	public void assertConsumedRoles(String phase, TClass tClass, Resource resource,
			String... expectedConsumedRoleNames) {
		List<String> nameList = Arrays.asList(expectedConsumedRoleNames);
		for (Pair<Integer, String> pair : map(nameList, ln -> Pair.of(nameList.indexOf(ln), ln))) {
			assertConsumedRole(phase, tClass, resource, pair.getKey(), pair.getValue());
		}
	}

	public void assertConsumedRoles(String phase, TInterface tRole, Resource resource,
			String... expectedConsumedRoleNames) {
		List<String> nameList = Arrays.asList(expectedConsumedRoleNames);
		for (Pair<Integer, String> pair : map(nameList, ln -> Pair.of(nameList.indexOf(ln), ln))) {
			assertConsumedRole(phase, tRole, resource, pair.getKey(), pair.getValue());
		}
	}

	public void assertConsumedRole(String phase, TClass tClass, Resource resource, int index,
			String expectedConsumedRoleName) {
		assertTrue(phase + ": Should have  an interface at the index",
				tClass.getImplementedInterfaceRefs().size() > index);
		Type consumedType = assertTypeRef(phase, tClass.getImplementedInterfaceRefs().get(index), resource);
		assertTrue(phase + ": Should have a TInterface as consumed type", consumedType instanceof TInterface);
		assertEquals(phase + ": Should have the expected consumed interface name", expectedConsumedRoleName,
				consumedType.getName());
	}

	public void assertConsumedRole(String phase, TInterface tRole, Resource resource, int index,
			String expectedConsumedRoleName) {
		assertTrue(phase + ": Should have  an interface at the index", tRole.getSuperInterfaceRefs().size() > index);
		Type consumedType = assertTypeRef(phase, tRole.getSuperInterfaceRefs().get(index), resource);
		assertTrue(phase + ": Should have a TInterface as consumed type", consumedType instanceof TInterface);
		assertEquals(phase + ": Should have the expected consumed interface name", expectedConsumedRoleName,
				consumedType.getName());
	}

	public void assertSuperInterfaces(String phase, TInterface tInterface, Resource resource,
			String... expectedSuperInterfaceNames) {
		assertEquals(phase + ": " + tInterface.getName() + " should have expected super interfaces count",
				expectedSuperInterfaceNames.length, tInterface.getSuperInterfaceRefs().size());
		List<String> nameList = Arrays.asList(expectedSuperInterfaceNames);
		for (Pair<Integer, String> pair : map(nameList, ln -> Pair.of(nameList.indexOf(ln), ln))) {
			TypeRef typeRef = tInterface.getSuperInterfaceRefs().get(pair.getKey());
			Type type = typeRef.getDeclaredType();
			assertNotNull(phase + ": declaredType should not be null", type);
			assertTrue(phase + ": declaredType should not a TInterface", type instanceof TInterface);
			assertEquals(phase + ": Should have expected super interface name at index " + pair.getKey(),
					type.getName(), pair.getValue());
		}
	}

	public void assertImplementedInterfaces(String phase, TInterface tRole, Resource resource,
			String... expectedImplementedInterfaceNames) {
		assertEquals(phase + ": Role " + tRole.getName() + " should have expected implemented interfaces count",
				expectedImplementedInterfaceNames.length, tRole.getSuperInterfaceRefs().size());

		List<String> nameList = Arrays.asList(expectedImplementedInterfaceNames);
		for (Pair<Integer, String> pair : map(nameList, ln -> Pair.of(nameList.indexOf(ln), ln))) {
			TypeRef typeRef = tRole.getSuperInterfaceRefs().get(pair.getKey());
			Type type = typeRef.getDeclaredType();
			assertNotNull(phase + ": declaredType should not be null", type);
			assertTrue(phase + ": declaredType should not a TInterface", type instanceof TInterface);
			assertEquals(phase + ": Should have expected implemented interface name at index " + pair.getKey(),
					type.getName(), pair.getValue());
		}
	}

	public void assertAccessModifier(String phase, TN4Classifier tClassifier, Resource resource,
			TypeAccessModifier expectedAccessModifier) {
		assertEquals(phase + ": Should have the expected access modifier", expectedAccessModifier,
				tClassifier.getTypeAccessModifier());
	}

	public TStructField assertTStructField(String phase, TStructuralType structType, String expectedName,
			String expectedTypeURIFragment) {
		TStructField attribute = head(filter(filter(
				structType.getOwnedMembers(), om -> Objects.equals(om.getName(), expectedName)),
				TStructField.class));
		assertNotNull(phase + ": there should be a structural member with expected name " + expectedName, attribute);
		assertBuiltinTypeFragmentURI(phase, structType.eResource(),
				((ParameterizedTypeRef) attribute.getTypeRef()).getDeclaredType(), expectedTypeURIFragment);
		return attribute;
	}
}
