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
package org.eclipse.n4js.transpiler.es.n4idl

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.transpiler.es.n4idl.assistants.MigrationTransformationAssistant
import org.eclipse.n4js.transpiler.es.n4idl.assistants.N4IDLClassifierTransformationAssistant
import org.eclipse.n4js.transpiler.es.transform.InterfaceDeclarationTransformation
import org.eclipse.n4js.transpiler.im.SymbolTableEntry

/**
 * N4IDL-specific {@link InterfaceDeclarationTransformation} which additionally
 * initializes an interface declaration with migration-support related static fields. 
 */
class N4IDLInterfaceDeclarationTransformation extends InterfaceDeclarationTransformation {
	
	@Inject private extension MigrationTransformationAssistant;
	@Inject private extension N4IDLClassifierTransformationAssistant;
	
	override protected createStaticFieldInitializations(N4InterfaceDeclaration interfaceDecl, SymbolTableEntry interfaceSTE) {
		val statements = super.createStaticFieldInitializations(interfaceDecl, interfaceSTE);
		
		statements.add(createMigrationSupportInitializer(interfaceSTE, interfaceDecl));
		statements.add(createImplementedInterfaceStaticInitializer(interfaceSTE, interfaceDecl));
		
		return statements;
	}
}
