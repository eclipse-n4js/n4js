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
package org.eclipse.n4js.transpiler.es.n4idl.tests

import java.util.Map
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.IfStatement
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.n4idl.N4IDLGlobals
import org.eclipse.n4js.transpiler.TranspilerState
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Transpiler tests which make sure that the generated structure of the {@link N4IDLGlobals#MIGRATIONS_STATIC_FIELD}
 * looks as expected.
 * 
 * This includes the number of generated type switch functions (one per target version) and the number 
 * of if-statements in those type switches (one per registered migration).
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class MigrationTransformationTranspilerTest extends AbstractN4IDLTranspilerTest {
	@Test
	def void testSimpleMigrationTransformation() {
		val state = '''
			class A#1 {}
			class A#2 {}
			
			@Migration function m(a1 : A#1) : A#2 { return null; }
		'''.createTranspilerState().transform;

		val switchesForA1 = state.findTypeSwitches("A$1");
		val conditions = switchesForA1.get("2").body.statements.filter(IfStatement);
		Assert.assertEquals("Exactly one type switch IfStatement is generated for the migration m wrt A#1.", 1, conditions.size)
		
		val switchesForA2 = state.findTypeSwitches("A$2");
		Assert.assertEquals("No type switch has been generated for migrations wrt A#2.", 0, switchesForA2.size);
	}
	
	@Test
	def void testMultipleMigrationsTransformation() {
		val state = '''
			class A#1 {}
			class A#2 {}
			
			@Migration function m1(a1 : A#1) : A#2 { return null; }
			@Migration function m2(a1 : A#1, i : int) : A#2 { return null; }
			@Migration function m3(s : string, a1 : A#1) : A#2 { return null; }
			
			@Migration function m4(a1 : A#1) : A#3 { return null; }
			
			@Migration function <T> genericMigration(a1 : A#1, t : T) : A#2 { return null; }
			
			@Migration function m5(a2 : A#2) : A#1 { return null; }
		'''.createTranspilerState().transform;

		val switchesForA1 = state.findTypeSwitches("A$1");
		val switchesForA2 = state.findTypeSwitches("A$2");
		
		val conditionsA1 = switchesForA1.get("2").body.statements.filter(IfStatement);
		Assert.assertEquals("Exactly three type switch IfStatement are generated for A#1 and target version 2.", 3, conditionsA1.size)
		
		Assert.assertEquals("The migrations field of A#1 has two entries.", 2, switchesForA1.size);
		Assert.assertTrue("The migrations field for A#1 has an entry for target version 2.", switchesForA1.get("2") !== null);
		Assert.assertTrue("The migrations field for A#1 has an entry for target version 4.", switchesForA1.get("3") !== null);
		
		Assert.assertEquals("The migrations field of A#2 has one entry.", 1, switchesForA2.size)
		val conditionsA2 = switchesForA2.get("1").body.statements.filter(IfStatement);
		Assert.assertEquals("Exactly 1 type switch IfStatement is generated for A#2 and target version 1.", 1, conditionsA2.size);
		
	}
	
	
	/**
	 * Finds all registered type-switches for the type with the given compiled name (e.g. "A#1" -> "A$1").
	 */
	private def Map<String, FunctionExpression> findTypeSwitches(TranspilerState state, String typeName) {
		try {
			val migrationRegistrationAccess = findFirstInIM(state, ParameterizedPropertyAccessExpression_IM, 
				[a | a?.rewiredTarget.name.equals(N4IDLGlobals.MIGRATIONS_STATIC_FIELD)
					&& a?.target instanceof IdentifierRef_IM 
					&& (a?.target as IdentifierRef_IM).id_IM.name.equals(typeName)]);
			
			if (null === migrationRegistrationAccess || !(migrationRegistrationAccess.eContainer instanceof AssignmentExpression)) {
				Assert.fail("Failed to locate the migration registration assignment.")
			}
			val assignment = migrationRegistrationAccess.eContainer as AssignmentExpression;
			val typeSwitches = assignment.rhs as ObjectLiteral
			
			return typeSwitches.propertyAssignments
				.filter(PropertyNameValuePair)
				.toMap([prop | prop.declaredName.literalName], [prop | prop.expression as FunctionExpression])			
		} catch (ClassCastException e) {
			throw new AssertionError(String.format("Failed to find type-switches for %s. Encountered malformed IM structure.", typeName), e);
		} catch (NullPointerException e) {
			throw new AssertionError(String.format("Failed to find type-switches for %s. Encountered malformed IM structure.", typeName), e);
		}	
	}
}
