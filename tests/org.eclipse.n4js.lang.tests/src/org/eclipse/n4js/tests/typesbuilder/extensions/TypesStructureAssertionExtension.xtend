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
package org.eclipse.n4js.tests.typesbuilder.extensions

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.n4js.n4JS.TypedElement
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TFormalParameter
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TMigration
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TN4Classifier
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ts.types.TStructField
import org.eclipse.n4js.ts.types.TStructuralType
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypeAccessModifier
import org.eclipse.xtext.EcoreUtil2

import static org.junit.Assert.*

/**
 */
public class TypesStructureAssertionExtension {

	def assertTypeRef(String phase, TypedElement typedElement, Resource resource) {
		assertTrue(phase + ": Should have parameterized type ref", typedElement.declaredTypeRefInAST instanceof ParameterizedTypeRef)
		val parameterizedTypeRef = typedElement.declaredTypeRefInAST as ParameterizedTypeRef
		assertEquals(phase + ": expected resource content size", 2, resource.contents.size)
		// test whether reference can be resolved
		assertTypeRef(phase, parameterizedTypeRef, resource)
	}

	def assertTypeRef(String phase, TVariable variable, Resource resource) {
		assertTrue(phase + ": Should have parameterized type ref", variable.typeRef instanceof ParameterizedTypeRef)
		val parameterizedTypeRef = variable.typeRef as ParameterizedTypeRef
		assertEquals(phase + ": expected resource content size", 2, resource.contents.size)
		// test whether reference can be resolved
		assertTypeRef(phase, parameterizedTypeRef, resource)
	}

	def assertTypeRef(String phase, ParameterizedTypeRef parameterizedTypeRef, Resource resource) {
		val type = parameterizedTypeRef.declaredType;
		assertNotNull(phase + ": declaredType should not be null", type)
		type
	}

	def assertTypeRefOfTField(String phase, TField tField, Resource newN4jsResource) {
		assertTrue("Should have parameterized type ref", tField.typeRef instanceof ParameterizedTypeRef)
		val parameterizedTypeRef = tField.typeRef as ParameterizedTypeRef
		assertEquals(phase + ": expected resource content size", 2, newN4jsResource.contents.size)
		// test whether reference can be resolved
		val type = parameterizedTypeRef.declaredType;
		assertNotNull(phase + ": declaredType should not be null", type)
		type
	}

	def assertReturnTypeRef(String phase, TFunction tFunction, Resource newN4jsResource) {
		assertTrue("Should have parameterized type ref", tFunction.returnTypeRef instanceof ParameterizedTypeRef)
		val parameterizedTypeRef = tFunction.returnTypeRef as ParameterizedTypeRef
		assertEquals(phase + ": expected resource content size", 2, newN4jsResource.contents.size)
		// test whether reference can be resolved
		val type = parameterizedTypeRef.declaredType;
		assertNotNull(phase + ": declaredType should not be null", type)
		type
	}

	def assertParameter(String phase, TFunction tFunction, Resource newN4jsResource, String expectedName, boolean variadic) {
		val parameter = tFunction.fpars.filter[name == expectedName].head
		assertNotNull(phase + ": there should be a parameter with expected name " + expectedName, parameter)
		assertParameter(phase, parameter, newN4jsResource, expectedName, variadic)
	}

	def assertParameter(String phase, TSetter tSetter, Resource newN4jsResource, String expectedName, boolean variadic) {
		assertNotNull(phase + ": Should have a parameter at index 0", tSetter.fpar)
		val parameter = tSetter.fpar
		assertParameter(phase, parameter, newN4jsResource, expectedName, variadic)
	}

	def private assertParameter(String phase, TFormalParameter parameter, Resource newN4jsResource, String expectedName, boolean variadic) {
		assertEquals(phase + ": Should have the expected parameter name", expectedName, parameter.name)
		assertEquals(phase + ": Should have the expected variadic setup", variadic, parameter.variadic)
		assertTrue("Should have parameterized type ref", parameter.typeRef instanceof ParameterizedTypeRef)
		val parameterizedTypeRef = parameter.typeRef as ParameterizedTypeRef
		assertEquals(phase + ": expected resource content size", 2, newN4jsResource.contents.size)
		val type = parameterizedTypeRef.declaredType;
		assertNotNull(phase + ": declaredType should not be null", type)
		type
	}

	def assertTypeFragmentURI(String phase, Resource newN4jsResource, Type type, String expectedFragment) {
		assertEquals(2, newN4jsResource.contents.size)
		val fragment = EcoreUtil2.getURI(type).fragment
		assertEquals(expectedFragment, fragment)
		assertFalse("Declared type of parameterized type ref should not be a proxy", type.eIsProxy)
		val fetchedEO = newN4jsResource.getEObject(fragment);
		assertSame(phase + ": type", type, fetchedEO)
		assertSame(phase + ": type container", type.eContainer, newN4jsResource.contents.get(1))
	}

	def assertBuiltinTypeFragmentURI(String phase, Resource newN4jsResource, Type type, String expectedFragment) {
		assertEquals(2, newN4jsResource.contents.size)
		val uri = EcoreUtil2.getURI(type)
		assertTrue(phase + ": built-in scheme", N4Scheme.isN4Scheme(uri))
		val fragment = uri.fragment
		assertEquals(phase + ": expected fragment", expectedFragment, fragment)
		val fetchedResource = newN4jsResource.resourceSet.getResource(uri, true)
		assertNotNull(phase + ": Resource should be resolvable for built-in type URI: " + uri, fetchedResource)
		val fetchedEO = newN4jsResource.resourceSet.getEObject(uri, true);
		assertNotNull(phase + ": Element should be resolvable for built-in type URI: " + uri, fetchedEO)
		assertFalse(phase + ": built-in type shouldn't be a proxy anymore: " + uri, type.eIsProxy)
	}

	def assertTClass(String phase, Resource newN4jsResource, String name, int memberCount) {
		val type = assertTypeForName(newN4jsResource, name, phase)
		assertTClass(phase, newN4jsResource, type, name, memberCount)
	}

	def assertTypeForName(Resource newN4jsResource, String name, String phase) {
		val type = (newN4jsResource.contents.get(1) as TModule).topLevelTypes.filter[it.name == name].head
		assertNotNull(phase + ": there should be a type with expected name " + name, type)
		type
	}

	def <T extends IdentifiableElement> T assertMemberForNameAndType(TClassifier classifier, String name, Class<T> type, String phase) {
		val member = classifier.ownedMembers.filter(type).filter[it.name == name].head
		assertNotNull(phase + ": there should be a member with expected name " + name + " and type " + type, member)
		member
	}

	def assertTClass(String phase, Resource newN4jsResource, Type type, String name, int memberCount) {
		assertTrue(phase + ": TClass expected", type instanceof TClass)
		val tClass = type as TClass
		assertEquals(phase + ": Should be named", name, tClass.name)
		assertEquals(phase + ": TClass should contain member count", memberCount, tClass.ownedMembers.size)
		tClass
	}

	def TInterface assertTInterface(String phase, Resource newN4jsResource, String name, int memberCount) {
		val type = assertTypeForName(newN4jsResource, name, phase)
		assertTInterface(phase, newN4jsResource, type, name, memberCount)
	}

	def TInterface assertTInterface(String phase, Resource newN4jsResource, Type type, String name, int memberCount) {
		assertTrue(phase + ": TInterface expected", type instanceof TInterface)
		val tInterface = type as TInterface
		assertEquals(phase + ": Should be named", name, tInterface.name)
		assertEquals(phase + ": TInterface should contain member count", memberCount, tInterface.ownedMembers.size)
		tInterface
	}

	def TInterface assertTRole(String phase, Resource newN4jsResource, String name, int memberCount) {
		val type = assertTypeForName(newN4jsResource, name, phase)
		assertTRole(phase, newN4jsResource, type, name, memberCount)
	}

	def TInterface assertTRole(String phase, Resource newN4jsResource, Type type, String name, int memberCount) {
		assertTrue(phase + ": TInterface expected", type instanceof TInterface)
		val tRole = type as TInterface
		assertEquals(phase + ": Should be named", name, tRole.name)
		assertEquals(phase + ": TInterface should contain member count", memberCount, tRole.ownedMembers.size)
		tRole
	}

	def assertTField(String phase, TClassifier classifier, String name) {
		assertMemberForNameAndType(classifier, name, TField, phase)
	}

	def assertTMethod(String phase, TClassifier classifier, String name, int parameterCount) {
		val tMethod = assertMemberForNameAndType(classifier, name, TMethod, phase)
		assertEquals(phase + ": Method should have expected parameter count", parameterCount, tMethod.fpars.size)
		tMethod
	}

	def assertTGetter(String phase, TClassifier classifier, String name) {
		val tGetter = assertMemberForNameAndType(classifier, name, TGetter, phase)
		assertTrue(phase + ": Getter should be collected as getter", classifier.ownedMembers.contains(tGetter))
		tGetter
	}

	def assertTSetter(String phase, TClassifier classifier, String name) {
		val tSetter = assertMemberForNameAndType(classifier, name, TSetter, phase)
		assertNotNull(phase + ": Setter should have exactly one parameter", tSetter.fpar)
		assertTrue(phase + ": Setter should be collected as setter", classifier.ownedMembers.contains(tSetter))
		tSetter
	}

	def assertTGetter(String phase, TStructuralType structType, String name) {
		val tGetter = structType.ownedMembers.filter[it.name == name].head
		assertNotNull(phase + ": there should be a tGetter with expected name " + name, tGetter)
		tGetter
	}

	def assertTSetter(String phase, TStructuralType structType, String name, String parameterName, String parameterTypeFragmentURI) {
		val tSetter = structType.ownedMembers.filter[it.name == name].filter(TSetter).head
		assertNotNull(phase + ": there should be a tSetter with expected name " + name, tSetter)
		assertNotNull(phase + ": Setter should have exactly one parameter", tSetter.fpar)
		assertEquals(phase + ": Setter should have expected parameter name", parameterName, tSetter.fpar.name)
		assertBuiltinTypeFragmentURI(phase, structType.eResource, (tSetter.fpar.typeRef as ParameterizedTypeRef).declaredType, parameterTypeFragmentURI)
		tSetter
	}

	def assertTEnum(String phase, Type type, String expectedName, String... expectedLiteralNames) {
		assertTrue(phase + ": TEnum expected as declaredType", type instanceof TEnum)
		val enumType = type as TEnum
		assertEquals(phase + ": TEnum should have expected name", expectedName, enumType.name)
		assertEquals(phase + ": TEnum should have expected literals count", expectedLiteralNames.size, enumType.literals.size)
		expectedLiteralNames.map[expectedLiteralNames.indexOf(it) -> it].forEach[
			enumType.literals.get(it.key).name == it.value
		]
	}

	def assertTFunction(String phase, Resource newN4jsResource, String name, int parameterCount) {
		val type = assertTypeForName(newN4jsResource, name, phase)
		assertTrue(phase + ": TFunction expected", type instanceof TFunction)
		val tFunction = type as TFunction
		assertEquals(phase + ": Should be named", name, tFunction.name)
		assertEquals(phase + ": TFunction should have expected parameter count", parameterCount, tFunction.fpars.size)
		tFunction
	}
	
	def assertTMigration(String phase, Resource newN4jsResource, String name,
		int sourceVersion, int targetVersion, 
		int sourceTypeRefCount, int targetTypeRefCount,
		boolean hasDeclaredSourceAndTargetVersion) {
		val type = assertTypeForName(newN4jsResource, name, phase)
		assertTrue(phase + ": TMigration expected", type instanceof TMigration)
		val tMigration = type as TMigration
		assertEquals(phase + ": Should be named", name, tMigration.name)
		
		val sourceTypeRefs = tMigration.sourceTypeRefs;
		val targetTypeRefs = tMigration.targetTypeRefs;
		
		assertEquals(phase + ": TMigration " + name +  " source type ref count", sourceTypeRefCount, sourceTypeRefs.size)
		assertEquals(phase + ": TMigration " + name +  " target type ref count", targetTypeRefCount, targetTypeRefs.size)
		
		assertEquals(phase + ": TMigration " + name +  " target version", targetVersion, tMigration.targetVersion)
		assertEquals(phase + ": TMigration " + name +  " source version", sourceVersion, tMigration.sourceVersion)
		
		assertEquals(phase + ": TMigration " + name + " has an explicitly declared source and target version", hasDeclaredSourceAndTargetVersion, tMigration.hasDeclaredSourceAndTargetVersion);
		
		tMigration
	}

	def assertTypeVariables(String phase, TFunction function, Resource resource, String... expectedTypeVarNames) {
		assertEquals(phase + ": Should have expected type vars count", function.typeVars.size, expectedTypeVarNames.size)
		expectedTypeVarNames.map[expectedTypeVarNames.indexOf(it) -> it].forEach[
			assertEquals(phase + ": Should have expected type var name at index " + it.key, function.typeVars.get(it.key).name, it.value)
			// TODO assert upper bounds as well (and lower bounds)
		]
	}

	def assertTVariable(String phase, Resource newN4jsResource, int variablesIndex, String name, boolean const) {
		val variables = (newN4jsResource.contents.get(1) as TModule).variables
		assertTrue(phase + ": Should have a variable at the index", variables.size > variablesIndex)
		val tVariable = variables.get(variablesIndex)
		assertEquals(phase + ": Should be named", name, tVariable.name)
		assertEquals(phase + ": TVariable should have expected const value", const, tVariable.const)
		tVariable
	}

	def assertSuperClass(String phase, TClass tClass, Resource resource, String expectedSuperClassName) {
		assertNotNull(phase + ": Should have a super type", tClass.getSuperClassRef)
		val superType = assertTypeRef(phase, tClass.getSuperClassRef, resource)
		assertTrue(phase + ": Should have a TClass as super type", superType instanceof TClass)
		assertEquals(phase + ": Should have the expected super class name", expectedSuperClassName, superType.name)
	}

	def assertConsumedRoles(String phase, TClass tClass, Resource resource, String... expectedConsumedRoleNames) {
		expectedConsumedRoleNames.map[expectedConsumedRoleNames.indexOf(it) -> it].forEach[
			assertConsumedRole(phase, tClass, resource, it.key, it.value)
		]
	}

	def assertConsumedRoles(String phase, TInterface tRole, Resource resource, String... expectedConsumedRoleNames) {
		expectedConsumedRoleNames.map[expectedConsumedRoleNames.indexOf(it) -> it].forEach[
			assertConsumedRole(phase, tRole, resource, it.key, it.value)
		]
	}

	def assertConsumedRole(String phase, TClass tClass, Resource resource, int index, String expectedConsumedRoleName) {
		assertTrue(phase + ": Should have  an interface at the index", tClass.implementedInterfaceRefs.size > index)
		val consumedType = assertTypeRef(phase, tClass.implementedInterfaceRefs.get(index), resource)
		assertTrue(phase + ": Should have a TInterface as consumed type", consumedType instanceof TInterface)
		assertEquals(phase + ": Should have the expected consumed interface name", expectedConsumedRoleName, consumedType.name)
	}

	def assertConsumedRole(String phase, TInterface tRole, Resource resource, int index, String expectedConsumedRoleName) {
		assertTrue(phase + ": Should have  an interface at the index", tRole.superInterfaceRefs.size > index)
		val consumedType = assertTypeRef(phase, tRole.superInterfaceRefs.get(index), resource)
		assertTrue(phase + ": Should have a TInterface as consumed type", consumedType instanceof TInterface)
		assertEquals(phase + ": Should have the expected consumed interface name", expectedConsumedRoleName, consumedType.name)
	}

	def assertSuperInterfaces(String phase, TInterface tInterface, Resource resource, String... expectedSuperInterfaceNames) {
		assertEquals(phase + ": "+ tInterface.name + " should have expected super interfaces count", expectedSuperInterfaceNames.size, tInterface.superInterfaceRefs.size)
		expectedSuperInterfaceNames.map[expectedSuperInterfaceNames.indexOf(it) -> it].forEach[
			val typeRef = tInterface.superInterfaceRefs.get(it.key)
			val type = typeRef.declaredType;
			assertNotNull(phase + ": declaredType should not be null", type)
			assertTrue(phase + ": declaredType should not a TInterface", type instanceof TInterface)
			assertEquals(phase + ": Should have expected super interface name at index " + it.key, type.name, it.value)
		]
	}


	def assertImplementedInterfaces(String phase, TInterface tRole, Resource resource, String... expectedImplementedInterfaceNames) {
		assertEquals(phase + ": Role " + tRole.name + " should have expected implemented interfaces count", expectedImplementedInterfaceNames.size, tRole.superInterfaceRefs.size)
		expectedImplementedInterfaceNames.map[expectedImplementedInterfaceNames.indexOf(it) -> it].forEach[
			val typeRef = tRole.superInterfaceRefs.get(it.key)
			val type = typeRef.declaredType;
			assertNotNull(phase + ": declaredType should not be null", type)
			assertTrue(phase + ": declaredType should not a TInterface", type instanceof TInterface)
			assertEquals(phase + ": Should have expected implemented interface name at index " + it.key, type.name, it.value)
		]
	}

	def assertAccessModifier(String phase, TN4Classifier tClassifier, Resource resource, TypeAccessModifier expectedAccessModifier) {
		assertEquals(phase + ": Should have the expected access modifier", expectedAccessModifier, tClassifier.typeAccessModifier)
	}

	def assertTStructField(String phase, TStructuralType structType, String expectedName, String expectedTypeURIFragment) {
		val attribute = structType.ownedMembers.filter[it.name == expectedName].filter(TStructField).head
		assertNotNull(phase + ": there should be a structural member with expected name " + expectedName, attribute)
		assertBuiltinTypeFragmentURI(phase, structType.eResource, (attribute.typeRef as ParameterizedTypeRef).declaredType, expectedTypeURIFragment)
		attribute
	}
}
