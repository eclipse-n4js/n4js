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
package org.eclipse.n4js.tests.dirtystate.testdata

class TestFiles {

	def static class0() '''
		/**
		 * Class «moduleFolder».Class0.Class0
		 */
		import { Class1 } from "pr0_0pa0/Class1"
		export public class Class0 {
			@Internal public method0() {
				var localFieldAccess0 : Class1;
				var dummy = localFieldAccess0.field0;
			}
		}
	'''

	def static moduleFolder() {
		"pr0_0pa0"
	}

	def static class1() '''
			/**
			 * Class «moduleFolder».Class1.Class1
			 */
			export public class Class1 {
				@Internal public field0 : any;
				@Internal public field1 : any;
			}
	'''

	def static class1After() '''
			/**
			 * Class «moduleFolder».Class1.Class1
			 */
			export public class Class1 {
				@Internal public field23 : any;
				@Internal public field1 : any;
			}
	'''

	def static mutualModuleFolder() {
		"mutual"
	}

	def static classBrother() '''
		import { Sister as SisterObject } from "«mutualModuleFolder»/Sister";
		import { Child } from "«mutualModuleFolder»/Child";
		export public class Brother {
			@Internal public getSister() : SisterObject {return null;}
			@Internal public getChild() : Child {return null;}
		}
		var brother : Brother = new Brother;
		brother.getSister().getBrother()
		var sister : SisterObject /*= new SisterObject*/;
		brother = sister.getBrother();
	'''

	def static classSister() '''
		import { Brother as BrotherObject } from "«mutualModuleFolder»/Brother";
		import { Child } from "«mutualModuleFolder»/Child";
		export public class Sister {
			@Internal public getBrother() : BrotherObject {return null;}
			@Internal public getChild() : Child {return null;}
		}
		var sister : Sister = new Sister;
		brother.getSister().getBrother()
		var brother : BrotherObject = new BrotherObject();
		sister = brother.getSister();
	'''

	def static classSisterNew() '''
		import { Brother as BrotherObject } from "«mutualModuleFolder»/Brother";
		import { Child } from "«mutualModuleFolder»/Child";
		export public class Sister {
			@Internal public getStepBrother() : BrotherObject {return null;}
			@Internal public getChild() : Child {return null;}
		}
		var sister : Sister = new Sister;
		brother.getSister().getBrother()
		var brother : BrotherObject /*= new BrotherObject*/;
		sister = brother.getSister();
		var brotherChildAge = sister.getBrother().getChild().age;
	'''

	def static classChild() '''
		export public class Child {
			@Internal public age : any;
		}
	'''
}
