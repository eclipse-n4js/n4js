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

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.n4js.n4JS.AnnotableElement
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.ExportableElement
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.N4Modifier
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration
import org.eclipse.n4js.n4JS.PropertyNameKind
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.n4JS.PropertySetterDeclaration
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.StringLiteral
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TInterface

import static org.junit.Assert.*

/**
 */
class ASTStructureAssertionExtension {

	@Inject
	private extension TypesStructureAssertionExtension

	def assertScript(String phase, Resource resource, int elementCount) {
		val root = resource.contents.get(0)
		assertTrue(phase + ": Script expected", root instanceof Script)
		val script = root as Script
		assertEquals(phase + ": Script should contain expected element count", elementCount, script.scriptElements.size)
		script
	}

	def assertExportDeclaration(String phase, Script script, int index) {
		val element = script.scriptElements.get(index)
		assertFalse(phase + ": Element should be no proxy", element.eIsProxy)
		assertTrue(phase + ": Element should be a ExportDeclaration", element instanceof ExportDeclaration)
		val exportDeclaration = element as ExportDeclaration
		val exportableElement = exportDeclaration.exportedElement
		assertFalse(phase + ": Exported element should be no proxy", exportableElement.eIsProxy)
		exportableElement
	}

	def assertN4ClassDeclaration(String phase, ExportableElement exportableElement, String name, int memberCount) {
		assertTrue(phase + ": Exported element should be a N4ClassDeclaration",
			exportableElement instanceof N4ClassDeclaration)
		val n4Class = exportableElement as N4ClassDeclaration
		assertEquals(phase + ": Class should be named", name, n4Class.name)
		assertEquals(phase + ": Class should contain expected member count", memberCount, n4Class.ownedMembers.size)
		n4Class
	}

	def N4InterfaceDeclaration assertN4InterfaceDeclaration(String phase, ExportableElement exportableElement, String name,
		int memberCount) {
		assertTrue(phase + ": Exported element should be a N4InterfaceDeclaration",
			exportableElement instanceof N4InterfaceDeclaration)
		val n4Interface = exportableElement as N4InterfaceDeclaration
		assertEquals(phase + ": Interface should be named", name, n4Interface.name)
		assertEquals(phase + ": Interface should contain expected member count", memberCount,
			n4Interface.ownedMembers.size)
		n4Interface
	}

	def N4InterfaceDeclaration assertN4RoleDeclaration(String phase, ExportableElement exportableElement, String name,
		int memberCount) {
		assertTrue(phase + ": Exported element should be a N4InterfaceDeclaration",
			exportableElement instanceof N4InterfaceDeclaration)
		val n4Role = exportableElement as N4InterfaceDeclaration
		assertEquals(phase + ": Role should be named", name, n4Role.name)
		assertEquals(phase + ": Role should contain expected member count", memberCount, n4Role.ownedMembers.size)
		n4Role
	}

	def assertN4FieldDeclaration(String phase, N4ClassDeclaration n4Class, String name) {
		assertTrue(phase + ": Element should be a N4FieldDeclaration",
			n4Class.ownedMembers.filter[it.name == name].head instanceof N4FieldDeclaration)
		val n4ClassField = n4Class.ownedMembers.filter[it.name == name].head as N4FieldDeclaration
		assertEquals(phase + ": Field should be named", name, n4ClassField.name)
		n4ClassField
	}

	def assertN4Function(String phase, Resource newN4jsResource, ExportableElement exportableElement, String name,
		int parameterCount) {
		assertTrue(phase + ": Exported element should be a FunctionDeclaration",
			exportableElement instanceof FunctionDeclaration)
		val n4Function = exportableElement as FunctionDeclaration
		assertEquals(phase + ": Function should be named", name, n4Function.name)
		assertEquals(phase + ": Function should have expected parameter count", parameterCount, n4Function.fpars.size)
		n4Function
	}

	def assertN4Method(String phase, N4ClassifierDeclaration n4Classifier, String name, int parameterCount) {
		assertTrue(phase + ": Exported element should be a N4MethodDeclaration",
			n4Classifier.ownedMembers.filter[it.name == name].head instanceof N4MethodDeclaration)
		val n4Method = n4Classifier.ownedMembers.filter[it.name == name].head as N4MethodDeclaration
		assertEquals(phase + ": Method should be named", name, n4Method.name)
		assertEquals(phase + ": Method should have expected parameter count", parameterCount, n4Method.fpars.size)
		n4Method
	}

	def assertN4Getter(String phase, N4ClassifierDeclaration n4Classifier, String name) {
		val n4Getter = n4Classifier.ownedMembers.filter(N4GetterDeclaration).filter[it.name == name].head
		assertNotNull(phase + ": Member should be a getter", n4Getter)
		assertEquals(phase + ": Getter should be named", name, n4Getter.name)
		assertTrue(phase + ": Getter should be collected as getter", n4Classifier.ownedGetters.contains(n4Getter))
		n4Getter
	}

	def assertN4Setter(String phase, N4ClassifierDeclaration n4Classifier, String expectedName) {
		val n4Setter = n4Classifier.ownedMembers.filter(N4SetterDeclaration).filter[it.name == expectedName].head
		assertNotNull(phase + ": Member should be a setter", n4Setter)
		assertEquals(phase + ": Setter should be named", expectedName, n4Setter.name)
		assertNotNull(phase + ": Setter should have exactly one parameter", n4Setter.fpar)
		assertTrue(phase + ": Setter should be collected as setter", n4Classifier.ownedSetters.contains(n4Setter))
		n4Setter
	}

	def assertPropertyGetter(String phase, ObjectLiteral objectLiteral, String expectedName) {
		val n4Getter = objectLiteral.propertyAssignments.filter(PropertyGetterDeclaration).filter[
			it.name == expectedName].head
		assertNotNull(phase + ": Getter should be found for name " + expectedName, n4Getter)
		n4Getter
	}

	def assertPropertySetter(String phase, ObjectLiteral objectLiteral, String expectedName, String expectedPropertyName) {
		val n4Setter = objectLiteral.propertyAssignments.filter(PropertySetterDeclaration).filter[
			it.name == expectedName].head
		assertNotNull(phase + ": Setter should be found for name " + expectedName, n4Setter)
		assertEquals(phase + ": Setter parameter should be named", expectedPropertyName, n4Setter.fpar.name)
		n4Setter
	}

	def assertReturnTypeRef(String phase, FunctionDefinition function, Resource newN4jsResource) {
		assertTrue("Should have parameterized type ref", function.declaredReturnTypeRefInAST instanceof ParameterizedTypeRef)
		val parameterizedTypeRef = function.declaredReturnTypeRef as ParameterizedTypeRef
		assertEquals(phase + ": expected resource content size", 2, newN4jsResource.contents.size)

		// test whether reference can be resolved
		val type = parameterizedTypeRef.declaredType;
		assertNotNull(phase + ": declaredType should not be null", type)
		type
	}

	def assertReturnTypeRef(String phase, TGetter getter, Resource newN4jsResource) {
		assertTrue("Should have parameterized type ref", getter.typeRef instanceof ParameterizedTypeRef)
		val parameterizedTypeRef = getter.typeRef as ParameterizedTypeRef
		assertEquals(phase + ": expected resource content size", 2, newN4jsResource.contents.size)

		// test whether reference can be resolved
		val type = parameterizedTypeRef.declaredType;
		assertNotNull(phase + ": declaredType should not be null", type)
		type
	}

	def assertParameter(String phase, FunctionDefinition function, Resource newN4jsResource, String expectedName,
		boolean variadic) {
		val parameter = function.fpars.filter[it.name == expectedName].head
		assertNotNull(phase + ": Parameter should be found for name " + expectedName, parameter)
		assertEquals(phase + ": Should have the expected variadic setup", variadic, parameter.variadic)
		assertTrue("Should have parameterized type ref", parameter.declaredTypeRefInAST instanceof ParameterizedTypeRef)
		val parameterizedTypeRef = parameter.declaredTypeRefInAST as ParameterizedTypeRef
		assertEquals(phase + ": expected resource content size", 2, newN4jsResource.contents.size)
		val type = parameterizedTypeRef.declaredType;
		assertNotNull(phase + ": declaredType should not be null", type)
		type
	}

	def assertTypeVariables(String phase, FunctionDeclaration declaration, Resource resource,
		String... expectedTypeVarNames) {
		assertEquals(phase + ": Should have expected type vars count", declaration.typeVars.size,
			expectedTypeVarNames.size)
		expectedTypeVarNames.map[expectedTypeVarNames.indexOf(it) -> it].forEach [
			assertEquals(phase + ": Should have expected type var name at index " + it.key,
				declaration.typeVars.get(it.key).name, it.value)
		// TODO assert upper bounds as well (and lower bounds)
		]
	}

	def assertVariableStatement(String phase, EObject exportableElement, int variableCount) {
		assertTrue(phase + ": VariableStatement expected", exportableElement instanceof VariableStatement)
		val variableStatement = exportableElement as VariableStatement
		assertEquals(phase + ": Script should contain expected element count", variableCount,
			variableStatement.varDecl.size)
		variableStatement
	}

	def assertVariable(String phase, VariableStatement variableStatement, String name, boolean const) {
		val n4Variable = variableStatement.varDecl.filter[it.name == name].head
		assertNotNull(phase + ": Variable should be found for name " + name, n4Variable)
		assertEquals(phase + ": Variable should have correct const value", const, n4Variable.const)
		n4Variable
	}

	def dispatch assertDefinedVariable(String phase, ExportedVariableDeclaration variableDeclaration, Resource resource,
		Class<?> expressionType) {
		val tVariable = variableDeclaration.definedVariable
		assertEquals(phase + ": expected name", variableDeclaration.name, tVariable.name)
		assertEquals(phase + ": expected const", variableDeclaration.const, tVariable.const)
		if (expressionType === null) {
			assertNull(phase + ": no assignment expected: " + variableDeclaration, variableDeclaration.expression)
		} else {
			assertNotNull(phase + ": expected expression type", expressionType)
			assertTrue(phase + ": expected expression type",
				expressionType.isAssignableFrom(variableDeclaration.expression?.class))
		}
	}

	def dispatch assertDefinedVariable(String phase, VariableDeclaration variableDeclaration, Resource resource,
		Class<?> expressionType) {
		if (expressionType === null) {
			assertNull(phase + ": no assignment expected: " + variableDeclaration, variableDeclaration.expression)
		} else {
			assertNotNull(phase + ": expected expression type", expressionType)
			assertTrue(phase + ": expected expression type",
				expressionType.isAssignableFrom(variableDeclaration.expression?.class))
		}
	}

	def assertPropertyNameValuePair(String phase, ObjectLiteral objectLiteral, String expectedName,
		PropertyNameKind expectedKind, String expectedValue, (PropertyNameValuePair)=>Object valueCalculation) {
		val propertyNameValuePair = objectLiteral.propertyAssignments.filter(PropertyNameValuePair).filter[
			it.name == expectedName].head
		assertNotNull(phase + ": Property assignment should be found for name " + expectedName, propertyNameValuePair)
		assertEquals(phase + ": expected property kind", propertyNameValuePair.declaredName.kind, expectedKind)
		assertEquals(phase + ": expected value", expectedValue, valueCalculation.apply(propertyNameValuePair))
	}

	def stringLiteralValueCalculation(String phase) {
		[ PropertyNameValuePair vp |
			assertTrue(phase + ": should be string literal but was " + vp.expression,
				vp.expression instanceof StringLiteral)
			(vp.expression as StringLiteral).value
		]
	}

	def identifierRefValueCalculation(String phase) {
		[ PropertyNameValuePair vp |
			assertTrue(phase + ": should be identifier ref but was " + vp.expression,
				vp.expression instanceof IdentifierRef)
			(vp.expression as IdentifierRef).id?.name
		]
	}

	def assertSuperClass(String phase, N4ClassDeclaration n4Class, Resource resource, String expectedSuperClassName) {
		assertNotNull(phase + ": Should have a super type", n4Class.getSuperClassRef)
		val superClassTypeRef = n4Class.getSuperClassRef?.typeRef;
		assertNotNull("expected superClassTypeRef to be non-null", superClassTypeRef);
		assertTrue("expected superClassTypeRef to be a ParameterizedTypeRef", superClassTypeRef instanceof ParameterizedTypeRef);
		val superType = assertTypeRef(phase, superClassTypeRef as ParameterizedTypeRef, resource)
		assertTrue(phase + ": Should have a TClass as super type", superType instanceof TClass)
		assertEquals(phase + ": Should have the expected super class name", expectedSuperClassName, superType.name)
	}

	def assertConsumedRoles(String phase, N4ClassDeclaration tClass, Resource resource,
		String... expectedConsumedRoleNames) {
		expectedConsumedRoleNames.map[expectedConsumedRoleNames.indexOf(it) -> it].forEach [
			assertConsumedRole(phase, tClass, resource, it.key, it.value)
		]
	}

	def assertConsumedRoles(String phase, N4InterfaceDeclaration tRole, Resource resource,
		String... expectedConsumedRoleNames) {
		expectedConsumedRoleNames.map[expectedConsumedRoleNames.indexOf(it) -> it].forEach [
			assertConsumedRole(phase, tRole, resource, it.key, it.value)
		]
	}

	def assertConsumedRole(String phase, N4ClassDeclaration n4Class, Resource resource, int index,
		String expectedConsumedRoleName) {
		assertTrue(phase + ": Should have an interface at the index", n4Class.implementedInterfaceRefs.size > index)
		val implIfcRef = n4Class.implementedInterfaceRefs.get(index).typeRef;
		assertNotNull("expected implementedInterfaceRefs[index] to be non-null", implIfcRef);
		assertTrue("expected implementedInterfaceRefs[index] to be a ParameterizedTypeRef", implIfcRef instanceof ParameterizedTypeRef);
		val consumedType = assertTypeRef(phase, implIfcRef as ParameterizedTypeRef, resource)
		assertTrue(phase + ": Should have a TInterface as consumed type", consumedType instanceof TInterface)
		assertEquals(phase + ": Should have the expected super class name", expectedConsumedRoleName, consumedType.name)
	}

	def assertConsumedRole(String phase, N4InterfaceDeclaration n4Role, Resource resource, int index,
		String expectedConsumedRoleName) {
		assertTrue(phase + ": Should have an interface at the index", n4Role.superInterfaceRefs.size > index)
		val superIfcRef = n4Role.superInterfaceRefs.get(index).typeRef;
		assertNotNull("expected superInterfaceRefs[index] to be non-null", superIfcRef);
		assertTrue("expected superInterfaceRefs[index] to be a ParameterizedTypeRef", superIfcRef instanceof ParameterizedTypeRef);
		val consumedType = assertTypeRef(phase, superIfcRef as ParameterizedTypeRef, resource)
		assertTrue(phase + ": Should have a TInterface as consumed type", consumedType instanceof TInterface)
		assertEquals(phase + ": Should have the expected super class name", expectedConsumedRoleName, consumedType.name)
	}

	def assertSuperInterfaces(String phase, N4InterfaceDeclaration n4Interface, Resource resource,
		String... expectedSuperInterfaceNames) {
		assertEquals(phase + ": " + n4Interface.name + " should have expected super interfaces count",
			expectedSuperInterfaceNames.size, n4Interface.superInterfaceRefs.size)
		expectedSuperInterfaceNames.map[expectedSuperInterfaceNames.indexOf(it) -> it].forEach [
			val typeRef = n4Interface.superInterfaceRefs.get(it.key).typeRef;
			val type = typeRef.declaredType;
			assertNotNull(phase + ": declaredType should not be null", type)
			assertTrue(phase + ": declaredType should not a TInterface", type instanceof TInterface)
			assertEquals(phase + ": Should have expected super interface name at index " + it.key, type.name, it.value)
		]
	}

	def assertImplementedInterfaces(String phase, N4InterfaceDeclaration n4Role, Resource resource,
		String... expectedImplementedInterfaceNames) {
		assertEquals(phase + ": " + n4Role.name + " should have expected implemented interfaces count",
			expectedImplementedInterfaceNames.size, n4Role.superInterfaceRefs.size)
		expectedImplementedInterfaceNames.map[expectedImplementedInterfaceNames.indexOf(it) -> it].forEach [
			val typeRef = n4Role.superInterfaceRefs.get(it.key).typeRef;
			val type = typeRef.declaredType;
			assertNotNull(phase + ": declaredType should not be null", type)
			assertTrue(phase + ": declaredType should not a TInterface", type instanceof TInterface)
			assertEquals(phase + ": Should have expected implemented interface name at index " + it.key, type.name,
				it.value)
		]
	}

	def assertDeclaredAccessModifier(String phase, N4ClassifierDeclaration n4Classifier, Resource resource,
		N4Modifier expectedAccessModifier) {
		if(expectedAccessModifier===null || expectedAccessModifier===N4Modifier.UNDEFINED) {
			assertTrue(phase + ": Should have no declared access modifiers",
				!n4Classifier.declaredModifiers.contains(N4Modifier.PRIVATE) &&
				!n4Classifier.declaredModifiers.contains(N4Modifier.PROJECT) &&
				!n4Classifier.declaredModifiers.contains(N4Modifier.PROTECTED) &&
				!n4Classifier.declaredModifiers.contains(N4Modifier.PUBLIC))
		}
		else {
			assertTrue(phase + ": Should have the expected declared access modifier: "+expectedAccessModifier,
				n4Classifier.declaredModifiers.contains(N4Modifier.PRIVATE)===(expectedAccessModifier===N4Modifier.PRIVATE) &&
				n4Classifier.declaredModifiers.contains(N4Modifier.PROJECT)===(expectedAccessModifier===N4Modifier.PROJECT) &&
				n4Classifier.declaredModifiers.contains(N4Modifier.PROTECTED)===(expectedAccessModifier===N4Modifier.PROTECTED) &&
				n4Classifier.declaredModifiers.contains(N4Modifier.PUBLIC)===(expectedAccessModifier===N4Modifier.PUBLIC))
		}
	}

	def assertAnnotations(String phase, AnnotableElement annotableElement, Resource resource,
		String... expectedAnnotationNames) {
		expectedAnnotationNames.map[expectedAnnotationNames.indexOf(it) -> it].forEach [
			assertAnnotation(phase, annotableElement, resource, it.key, it.value)
		]
	}

	def assertAnnotation(String phase, AnnotableElement element, Resource resource, Integer index,
		String expectedAnnotationName) {
		assertTrue(phase + ": Should have an interface at the index", element.annotations.size > index)
		val annotation = element.annotations.get(index)
		assertEquals(phase + ": Should have the annotation name", expectedAnnotationName, annotation.name)
	}
}
